/*
 * Minecraft.js, JavaScript extension for Minecraft Forge
 */

package com.gaiakeeper.minecraftjs;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
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

    Global global = null;		// global scope for Rhino
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info( MODID + " (" + VERSION + ") is loading.");
        logger.info("Entering preInit...");
        
        workingDir = new File(event.getModConfigurationDirectory() + File.separator + "javascript");
        workingDir.mkdir();
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
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        logger.info("Entering serverStopping...");
    }
    
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
    	logger.info("Entering onCommand...");
    }

    /*
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
   	}
    
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
        	
        	File f = new File(workingDir, BOOT_JS);
        	if (f.exists()){
        		cx.evaluateReader(global, new InputStreamReader(new FileInputStream(f)), BOOT_JS, 1, null);
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
            ScriptableObject.putProperty(global, "args", args);

            Object result = cx.evaluateReader(global, new InputStreamReader(new FileInputStream(new File(workingDir, file))), file, 1, null);

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
    
    public void runCommand(EntityPlayerMP player, String[] args) {
    	if (args[0].equals("reload")){
    		initJS();
    		
    		WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    		we.log("Javascript modules reloaded...");
    	}    
    }
}
