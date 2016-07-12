package com.gaiakeeper.minecraftjs;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class JS_Command extends CommandBase {
	
    private final MinecraftJS minecraftjs;

    /**
     * Create a new instance.
     *
     * @param worldEdit reference to WorldEdit
     */
    public JS_Command(MinecraftJS mjs) {
        minecraftjs = mjs;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
	@Override
	public String getCommandName() {
		return "js";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/js <javascript> or /js <javascript file> <args...>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		EntityPlayerMP player;
		if ( sender instanceof EntityPlayerMP ){
			player = (EntityPlayerMP)sender;
		}
		else {
			player = getPlayer(sender, "@p");		// nearest player
		}
		
		if ( args[0].endsWith(".js") )
			minecraftjs.runScript(player, args[0], args);
		else {
            String s = "";
            for (int i=0; i < args.length; i++) {
                s += " ";
            	s += args[i];
			}
            minecraftjs.runScript(player, s);
		}
	}

}
