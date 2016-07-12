package com.gaiakeeper.minecraftjs;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

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
    public int getRequiredPermissionLevel() {
        return 0;
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
		EntityPlayerMP player;
		if ( sender instanceof EntityPlayerMP ){
			player = (EntityPlayerMP)sender;
		}
		else {
			player = getPlayer(sender, "@p");		// nearest player
		}

		minecraftjs.runCommand(player, args);
	}

}
