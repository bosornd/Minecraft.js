package com.gaiakeeper.minecraftjs;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class JSC_Command extends CommandBase {
	
    private final MinecraftJS minecraftjs;

    /**
     * Create a new instance.
     *
     * @param worldEdit reference to WorldEdit
     */
    public JSC_Command(MinecraftJS mjs) {
        minecraftjs = mjs;
    }

	@Override
	public String getCommandName() {
		return "jsc";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/jsc <javascript command>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		minecraftjs.runCommand(sender, args);
	}

}
