/*
 * Minecraft.js, JavaScript extension for Minecraft Forge
 */

package com.gaiakeeper.minecraftjs;

import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
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
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.mozilla.javascript.*;
import org.mozilla.javascript.tools.shell.Global;

import com.gaiakeeper.minecraft.BlockID;
import com.gaiakeeper.minecraft.ItemID;
import com.gaiakeeper.minecraft.Player;
import com.gaiakeeper.minecraft.WorldEdit;

import org.apache.logging.log4j.Logger;

@Mod(modid = MinecraftJS.MODID, name = "Minecraft.js", version = MinecraftJS.VERSION)
public class MinecraftJS
{
    public static final String MODID = "minecraftjs";
    public static final String VERSION = "0.1";
    
    @Instance(MODID)
    public static MinecraftJS inst;
    
    public Logger logger; 
    public File workingDir;

    Global global;		// global scope for Rhino
    
    public BlockID blockID = new BlockID();
    public ItemID itemID = new ItemID();
    
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

        Context cx = Context.enter();
        try {
    	    global = new Global();		// global scope for Rhino
        	global.initStandardObjects(cx, true);
        	List<String> paths = Arrays.asList(workingDir.toURI() + "/modules");
        	global.installRequire(cx,  paths, false);
    	}
        catch (Exception e) {
			e.printStackTrace();
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
        
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
        ICommandManager command = server.getCommandManager();
        ServerCommandManager manager = (ServerCommandManager) command;
        manager.registerCommand(new JS_Command(this));
        manager.registerCommand(new JSC_Command(this));
    }
    
    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        logger.info("Entering serverStopping...");
    }
    
    @SubscribeEvent
    public void onCommand(CommandEvent event) {
    }

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
    public void onExplosion(Start event){ callEventHandler("onExplosion", event); }
    
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event){ callEventHandler("onLivingDeath", event); }
    
    public void callEventHandler(String handler, Event event) {
    	Object fObj = global.get(handler, global);
    	if (fObj instanceof Function){
    		Object args[] = { event };
    		Function f = (Function)fObj;
            Context cx = Context.enter();
            try {
                logger.info("EventHandler for " + handler + " is called");    		
	    		f.call(cx, global, global, args);
            }
            catch (Exception e) {
    			e.printStackTrace();
            }
        	finally {
                // Exit from the context.
                Context.exit();
            }
    	}
    	else {
            logger.info("no EventHandler for " + handler);    		
    	}
    }
    
    public void runScript(ICommandSender player, String script) {
    	WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    	Player p = new Player((EntityPlayerMP)player, logger);
		p.print("js> " + script);
		
        Context cx = Context.enter();
        try {
            ScriptableObject.putProperty(global, "player", p);
            ScriptableObject.putProperty(global, "worldedit", we);
            ScriptableObject.putProperty(global, "BlockID", blockID);
            ScriptableObject.putProperty(global, "ItemID", itemID);

            // Now evaluate the string we've collected.
            Object result = cx.evaluateString(global, script, script, 1, null);
            if(!(result instanceof org.mozilla.javascript.Undefined))
            	p.print(result.toString());
            else p.print("Done.");
    	}
        catch (Exception e) {
			e.printStackTrace();
    		p.print("Script error: " + e.getMessage());
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
   }

    public void runScript(ICommandSender player, String file, String[] args) {
    	WorldEdit we = new WorldEdit(player.getEntityWorld(), logger);
    	Player p = new Player((EntityPlayerMP)player, logger);
		p.print("js> run_script(" + file + ")");

        Context cx = Context.enter();
        try {
            ScriptableObject.putProperty(global, "worldedit", we);
            ScriptableObject.putProperty(global, "player", p);
            ScriptableObject.putProperty(global, "args", args);
            ScriptableObject.putProperty(global, "BlockID", blockID);
            ScriptableObject.putProperty(global, "ItemID", itemID);

            Object result = cx.evaluateReader(global, new InputStreamReader(new FileInputStream(new File(workingDir, file))), file, 1, null);
            if(!(result instanceof org.mozilla.javascript.Undefined))
            	p.print(result.toString());
            else p.print("Done.");
    	}
        catch (Exception e) {
			e.printStackTrace();
    		p.print("Script error: " + e.getMessage());
        }
    	finally {
            // Exit from the context.
            Context.exit();
        }
    }
    
    public void runCommand(ICommandSender player, String[] args) {
    	if (args[0].equals("reload")){
    		player.addChatMessage(new ChatComponentText("Javascript modules reloaded..."));

    		Context cx = Context.enter();
            try {
        	    global = new Global();		// global scope for Rhino
            	global.initStandardObjects(cx, true);
            	List<String> paths = Arrays.asList(workingDir.toURI() + "/modules");
            	global.installRequire(cx,  paths, false);
        	}
            catch (Exception e) {
    			e.printStackTrace();
            }
        	finally {
                // Exit from the context.
                Context.exit();
            }
    	}    
    }
}
