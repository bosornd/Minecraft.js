package com.gaiakeeper.minecraftjs;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
	public String getCommandName() {
		return "js";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/js <javascript> or /js <javascript file> <args...>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if ( args[0].endsWith(".js") )
			minecraftjs.runScript(sender, args[0], args);
		else {
            String s = "";
            for (int i=0; i < args.length; i++) {
                s += " ";
            	s += args[i];
			}
            minecraftjs.runScript(sender, s);
		}
	}

}
