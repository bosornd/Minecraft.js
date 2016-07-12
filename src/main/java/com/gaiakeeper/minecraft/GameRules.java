package com.gaiakeeper.minecraft;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class GameRules {
	protected net.minecraft.world.World _world;
	protected net.minecraft.world.GameRules _gamerules;
	
	// difficulties
	public static int PEACEFUL	= 0;
	public static int EASY		= 1;
	public static int NORMAL	= 2;
	public static int HARD		= 3;
	
	public GameRules(World world){
		_world = world;
		_gamerules = _world.getGameRules();
	}

	public boolean get(String rule){
		return _gamerules.getGameRuleBooleanValue(rule);
	}
	
	public void set(String rule, boolean value){
		_gamerules.setOrCreateGameRule(rule, value ? "true" : "false");
	}
	
	public boolean getCommandBlockOutputEnabled(){
		return _gamerules.getGameRuleBooleanValue("commandBlockOutput");
	}
	
	public void setCommandBlockOutputEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("commandBlockOutput", enabled ? "true" : "false");
	}

	public boolean getDaylightCycleEnabled(){
		return _gamerules.getGameRuleBooleanValue("doDaylightCycle");
	}
	
	public void setDaylightCycleEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("doDaylightCycle", enabled ? "true" : "false");
	}

	public boolean getFireTickEnabled(){
		return _gamerules.getGameRuleBooleanValue("doFireTick");
	}
	
	public void setFireTickEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("doFireTick", enabled ? "true" : "false");
	}

	
	public boolean getMobLootEnabled(){
		return _gamerules.getGameRuleBooleanValue("doMobLoot");
	}
	
	public void setMobLootEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("doMobLoot", enabled ? "true" : "false");
	}

	public boolean getMobSpawningEnabled(){
		return _gamerules.getGameRuleBooleanValue("doMobSpawning");
	}
	
	public void setMobSpawningEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("doMobSpawning", enabled ? "true" : "false");
	}
	
	public boolean getAnimalSpawningEnabled(){
		MinecraftServer server = MinecraftServer.getServer();
		return server.getCanSpawnAnimals();
	}
	
	public void setAnimalSpawningEnabled(boolean enabled){
		MinecraftServer server = MinecraftServer.getServer();
		server.setCanSpawnAnimals(enabled);
	}
	
	public boolean getNPCSpawningEnabled(){
		MinecraftServer server = MinecraftServer.getServer();
		return server.getCanSpawnNPCs();
	}
	
	public void setNPCSpawningEnabled(boolean enabled){
		MinecraftServer server = MinecraftServer.getServer();
		server.setCanSpawnNPCs(enabled);
	}
	
	public boolean getPVPAllowed(){
		MinecraftServer server = MinecraftServer.getServer();
		return server.isPVPEnabled();
	}
	
	public void setPVPAllowed(boolean allowed){
		MinecraftServer server = MinecraftServer.getServer();
		server.setAllowPvp(allowed);
	}
	
	public boolean getTileDropsEnabled(){
		return _gamerules.getGameRuleBooleanValue("doTileDrops");
	}
	
	public void setTileDropsEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("doTileDrops", enabled ? "true" : "false");
	}

	public boolean getKeepInventoryEnabled(){
		return _gamerules.getGameRuleBooleanValue("keepInventory");
	}
	
	public void setKeepInventoryEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("keepInventory", enabled ? "true" : "false");
	}

	public boolean getMobGriefingEnabled(){
		return _gamerules.getGameRuleBooleanValue("mobGriefing");
	}
	
	public void setMobGriefingEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("mobGriefing", enabled ? "true" : "false");
	}

	public boolean getNaturalRegenerationEnabled(){
		return _gamerules.getGameRuleBooleanValue("naturalRegeneration");
	}
	
	public void setNaturalRegenerationEnabled(boolean enabled){
		_gamerules.setOrCreateGameRule("naturalRegeneration", enabled ? "true" : "false");
	}
	
	public boolean getBreakBlockEnabled(){
		return WorldEdit.breakBlockEnabled;
	}
	
	public void setBreakBlockEnabled(boolean enabled){
		WorldEdit.breakBlockEnabled = enabled;
	}
	
	public boolean getPlaceBlockEnabled(){
		return WorldEdit.placeBlockEnabled;
	}
	
	public void setPlaceBlockEnabled(boolean enabled){
		WorldEdit.placeBlockEnabled = enabled;
	}
	
	public boolean getExplosionEnabled(){
		return WorldEdit.explosionEnabled;
	}
	
	public void setExplosionEnabled(boolean enabled){
		WorldEdit.explosionEnabled = enabled;
	}
	
	public int getDifficulty(){
		return _world.difficultySetting.getDifficultyId();
	}
	
	public void setDifficulty(int difficulty){
		MinecraftServer server = MinecraftServer.getServer();
		server.func_147139_a(EnumDifficulty.getDifficultyEnum(difficulty));
	}
}
