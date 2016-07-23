/*
 * Minecraft.js, JavaScript extension for Minecraft Forge
 */

package com.gaiakeeper.minecraftjs;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.MultiPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Start;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;

import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Global;

import com.gaiakeeper.minecraft.Player;
import com.gaiakeeper.minecraft.WorldEdit;

import org.apache.logging.log4j.Logger;

@Mod(modid = MinecraftJS.MODID, name = "Minecraft.js", version = MinecraftJS.VERSION)
public class MinecraftJS
{
    public static final String MODID = "minecraftjs";
    public static final String VERSION = "0.1";
    
    public static final String BOOT_JS = "boot.js";
    
    @Instance(MODID)
    public static MinecraftJS inst;
    
    public Logger logger; 
    public File workingDir;
    public File recordFile;
    public PrintStream recorder;

    Global global = null;		// global scope for Rhino
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info( MODID + " (" + VERSION + ") is loading.");
        logger.info("Entering preInit...");
        
        workingDir = new File(event.getModConfigurationDirectory() + File.separator + "javascript");
        workingDir.mkdir();
        
        File recordDir = new File(workingDir + File.separator + "logs");
        recordDir.mkdir();
        recordFile = new File(recordDir, "recorder.txt");
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info("Entering init...");
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Entering postInit...");
        logger.info( MODID + " (" + VERSION + ") is loaded.");        
    }
    
    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        logger.info("Entering serverAboutToStart...");
        MinecraftServer server = MinecraftServer.getServer();
        ServerCommandManager manager = (ServerCommandManager)server.getCommandManager();
        manager.registerCommand(new JS_Command(this));
        manager.registerCommand(new JSC_Command(this));
        
        if(recordFile.exists()){
        	recordFile.renameTo(new File(recordFile.getParent(), "record_" + recordFile.lastModified() + ".txt"));
        }
        try {
        	recorder = new PrintStream(recordFile);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        logger.info("Entering serverStopping...");
        try {
        	recorder.close();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
//    	logger.info("Entering onCommand...");
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
//    	logger.info("Entering onPlayerInteract...");
   	}

    /*
// player event
    @SubscribeEvent
    public void onPlayerPickupXp(PlayerPickupXpEvent event) { callEventHandler("onPlayerPickupXp", event); }
    
    @SubscribeEvent
    public void onPlayerAchievement(AchievementEvent event) { callEventHandler("onPlayerAchievement", event); }
    
    @SubscribeEvent
    public void onPlayerPickUpItem(ItemPickupEvent event) { callEventHandler("onPlayerPickUpItem", event); }
    
    @SubscribeEvent
    public void onPlayerCraftItem(ItemCraftedEvent event) { callEventHandler("onPlayerCraftItem", event); }
    
    @SubscribeEvent
    public void onPlayerSmeltItem(ItemSmeltedEvent event) { callEventHandler("onPlayerSmeltItem", event); }
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent event) { callEventHandler("onPlayerRespawn", event); }
    
    @SubscribeEvent
    public void onPlayerUseItem(Start event) { callEventHandler("onPlayerUseItem", event); }
    
    @SubscribeEvent
    public void onPlayerDestoryItem(PlayerDestroyItemEvent event) { callEventHandler("onPlayerDestoryItem", event); }
    
    @SubscribeEvent
    public void onPlayerSleepInBed(PlayerSleepInBedEvent event) { callEventHandler("onPlayerSleepInBed", event); }
    
    @SubscribeEvent
    public void onPlayerWakeUp(PlayerWakeUpEvent event) { callEventHandler("onPlayerWakeUp", event); }
    
    
// world event
    @SubscribeEvent
    public void onPlaceBlock(PlaceEvent event){ callEventHandler("onPlaceBlock", event); }
    
    @SubscribeEvent
    public void onMultiPlaceBlock(MultiPlaceEvent event){ callEventHandler("onMultiPlaceBlock", event); }
    
    @SubscribeEvent
    public void onBreakBlock(BreakEvent event){ callEventHandler("onBreakBlock", event); }
    
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){ callEventHandler("onLivingDeath", event); }
    
    public void callEventHandler(String handler, Event event) {
        if ( global == null ) initJS();			// initialize JavaScript context
        
        Context cx = Context.enter();
        try {
	        Object fObj = global.get(handler, global);
	    	if (fObj instanceof Function){
	    		Object args[] = { event };
	    		Function f = (Function)fObj;
	    		f.call(cx, global, global, args);
	    		logger.info("EventHandler for " + handler + " is called");    		
	    	}
	    	else {
	            logger.info("no EventHandler for " + handler);    		
	    	}
        }
        catch (Exception e) {
			e.printStackTrace();
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
    }
    */
    
    @SubscribeEvent
    public void onPlaceBlock(PlaceEvent event){
    	if (!WorldEdit.placeBlockEnabled)
    		event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void onMultiPlaceBlock(MultiPlaceEvent event){
    	if (!WorldEdit.placeBlockEnabled)
    		event.setCanceled(true);
    }

    @SubscribeEvent
    public void onBreakBlock(BreakEvent event){
		if (!WorldEdit.breakBlockEnabled)
			event.setCanceled(true);

    	if (WorldEdit.hoeCommand != null){
    		EntityPlayerMP player = (EntityPlayerMP)event.getPlayer();
    		ItemStack item = player.getHeldItem();
    		
    		if ( item != null && item.getItem() instanceof ItemHoe){
        		event.setCanceled(true);
        		String cmd = WorldEdit.hoeCommand.replaceAll("@", "new Location(" + event.x + ", " + (event.y+1) + ", " + event.z +")");
        		recorder.println(cmd);
        		runScript(player, cmd);
    		}
    	}
	}
    
    @SubscribeEvent
    public void onExplosion(Start event){
		if (!WorldEdit.explosionEnabled)
			event.setCanceled(true);
    }
    
    protected void initJS(){
    	WorldEdit we = new WorldEdit(MinecraftServer.getServer().getEntityWorld(), logger);
    	
        Context cx = Context.enter();
        try {
    	    global = new Global();		// global scope for Rhino
        	global.initStandardObjects(cx, false);
        	List<String> paths = Arrays.asList(workingDir.toURI() + "/modules");
        	global.installRequire(cx,  paths, false);
        	
        	ScriptableObject.putProperty(global, "worldedit", we);
            ScriptableObject.putProperty(global, "recorder", recorder);
        	
        	File f = new File(workingDir, BOOT_JS);
        	if (f.exists()){
        		cx.evaluateReader(global, new InputStreamReader(new FileInputStream(f)), BOOT_JS, 1, null);
	        }
    	}
        catch (Exception e) {
			e.printStackTrace();
			we.log("Script error: " + e.getMessage());
		}
    	finally {
            // Exit from the context.
            Context.exit();
        }
    }
    
    public void runScript(EntityPlayerMP player, String script) {
        if ( global == null ) initJS();			// initialize JavaScript context
        
    	Player p = new Player((EntityPlayerMP)player, logger);
    	WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    	we.log("js> " + script);

    	Context cx = Context.enter();
        try {
            ScriptableObject.putProperty(global, "worldedit", we);
        	if ( p != null ){
                ScriptableObject.putProperty(global, "player", p);
        	}
            ScriptableObject.putProperty(global, "recorder", recorder);
       	
            // Now evaluate the string we've collected.
            Object result = cx.evaluateString(global, script, script, 1, null);

            if(!(result instanceof org.mozilla.javascript.Undefined))
            	we.log(result.toString());
            else we.log("Done.");
    	}
        catch (Exception e) {
			e.printStackTrace();
			we.log("Script error: " + e.getMessage());
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
   }

    public void runScript(EntityPlayerMP player, String file, String[] args) {
        if ( global == null ) initJS();			// initialize JavaScript context
        
    	Player p = new Player((EntityPlayerMP)player, logger);
    	WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    	we.log("js> run_script(" + file + ")");

        Context cx = Context.enter();
        try {
            ScriptableObject.putProperty(global, "worldedit", we);
            ScriptableObject.putProperty(global, "player", p);
            ScriptableObject.putProperty(global, "recorder", recorder);
            ScriptableObject.putProperty(global, "args", args);

            Object result = cx.evaluateReader(global, new InputStreamReader(new FileInputStream(new File(workingDir, file))), file, 1, null);

            if(!(result instanceof org.mozilla.javascript.Undefined))
            	we.log(result.toString());
            else we.log("done.");
    	}
        catch (Exception e) {
			e.printStackTrace();
			we.log("Script error: " + e.getMessage());
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
    }
    
    public void runCommand(EntityPlayerMP player, String[] args) {
    	if (args[0].equals("reload")){
    		initJS();
    		
    		WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    		we.log("Javascript modules reloaded...");
    	}
    	else if (args[0].equals("test")){
    		WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    		testKorean(we);
    	}
    }
    
    public void testKorean(WorldEdit we) {
		 // JAVA에서도 "한글 test"는 잘 출력되지 안혹, "\uD55C\uAE00test"는 잘 출력된다.
		 // 변환 방법은 아직 찾지 못했다.
		 we.log(Charset.defaultCharset().name());
		
		 String t1 = new String("한글test");
		 we.log(t1);
		 for(char c: t1.toCharArray()) we.log(Integer.toHexString(c));
		 // 5360 c2fc ae4d c619 74 65 73 74
		 
		 String t2 = new String("\uD55C\uAE00test");
		 we.log(t2);
		 for(char c: t2.toCharArray()) we.log(Integer.toHexString(c));
		 // d55c ae00 75 65 73 74
		
		 try {
			 String[] charsets = { "ISO8859-1", "UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "UTF-32", "EUC-KR", "x-windows-949" };
			 for(String cs1 : charsets){
			 	for(String cs2 : charsets){
			 		we.log(cs1 + "_" + cs2 + ": " + new String(t1.getBytes(cs1), cs2));
			 	}
			 }
			 for(String cs2 : charsets){
			 	we.log("def_" + cs2 + ": " + new String(t1.getBytes(), cs2));
			 }
			 /*
			 for(String cs1 : charsets){
			 	for(String cs2 : charsets){
			 		we.log(cs1 + "_" + cs2 + ": " + new String(t2.getBytes(cs1), cs2));
			 	}
			 }
			 for(String cs2 : charsets){
			 	we.log("def_" + cs2 + ": " + new String(t2.getBytes(), cs2));
			 }
			 */

		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 /*
		 String t3 = "";
		 for(int i = 0; i < t1.length(); i ++){
		 	char c = t1.charAt(i);
		 	we.log(Integer.toHexString(c));
		 	t3 += c;
		 }
		 we.log(t3);
		
		 String t4 = "";
		 for(int i = 0; i < t2.length(); i ++){
		 	char c = t2.charAt(i);
		 	we.log(Integer.toHexString(c));
		 	t4 += c;
		 }
		 we.log(t4);
		
		 we.log("한글test2");
		 we.log("\uD55C\uAE00test1");
		 logger.info("한글test2");
		 logger.info("\uD55C\uAE00test1");
		
		 String test = new String("한글 test");
		 we.log(new String(test.getBytes("EUC-KR")));
		 we.log(new String(test.getBytes("UTF-16")));
		 we.log(new String(test.getBytes("UTF-16BE")));
		 we.log(new String(test.getBytes("UTF-16LE")));
		 we.log(new String(test.getBytes("UTF-8")));
		
		 byte[] data = new byte[test.length() * 2];
		 for(int i = 0; i < test.length(); i ++){
		 	int c = (int)test.charAt(i);
		 	data[2*i] = (byte)((c & 0xFF00) >> 8);
		 	data[2*i + 1] = (byte)(c & 0xFF);
		 }
		 we.log(new String(data));
		 */
		 /*
		 StringBuffer str = new StringBuffer();
		
		 for (int i = 0; i < test.length(); i++) {
		  if(((int) test.charAt(i) == 32)) {
		   str.append(" ");
		   continue;
		  }
		  str.append("\\u");
		  str.append(Integer.toHexString((int) test.charAt(i)));
		  
		 }
		
		 we.log(str.toString());
		 */
		 /*
		 we.log(new String(test.getBytes(), "EUC-KR"));
		 we.log(new String(test.getBytes(), "UTF-16"));
		 we.log(new String(test.getBytes(), "UTF-16BE"));
		 we.log(new String(test.getBytes(), "UTF-16LE"));
		 we.log(new String(test.getBytes(), "UTF-8"));
		 */
    }
}
