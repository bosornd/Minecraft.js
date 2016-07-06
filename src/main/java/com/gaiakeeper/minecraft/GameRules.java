package com.gaiakeeper.minecraft;

public class GameRules {
	protected net.minecraft.world.GameRules _gamerules;
	
	public GameRules(net.minecraft.world.GameRules gamerules){
		_gamerules = gamerules;
	}

	public boolean get(String rule){
		return _gamerules.getGameRuleBooleanValue(rule);
	}
	
	public void set(String rule, boolean value){
		_gamerules.setOrCreateGameRule(rule, value ? "true" : "false");
	}
}
