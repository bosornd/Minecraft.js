package com.gaiakeeper.minecraft;

import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;

public class Player extends Mob {
	protected EntityPlayerMP _player;
	protected PlayerInventory _inventory;
	protected Logger _logger;
	
	protected static ChatStyle chatStyle = new ChatStyle();
	
	public Player(EntityPlayerMP player, Logger logger){
		super(player);
		
		_player = player;
		_inventory = new PlayerInventory(_player.inventory);
		_logger = logger;
		chatStyle.setColor(EnumChatFormatting.GRAY);
	};

	public EntityPlayerMP getPlayer(){
		return _player;
	};
	
	public String getName(){
		return _player.getDisplayName();
	}

	public final float getHealth(){
		return _player.getHealth();
	}

	public void setHealth(float amount){
		_player.setHealth(amount);
	}

	public final float getMaxHealth(){
		return _player.getMaxHealth();
	}

	public void heal(float amount){
		_player.heal(amount);
	}

	public Location getLocation(){
		ChunkCoordinates loc = _player.getPlayerCoordinates();
		return new Location(loc.posX, loc.posY, loc.posZ);
	}
	
	public void setLocation(int x, int y, int z){
		_player.setPositionAndUpdate(x, y, z);
	}

	public void setLocation(Location loc){
		_player.setPositionAndUpdate(loc.x, loc.y, loc.z);
	}
	
	public Location getSpawnPoint(){
		ChunkCoordinates loc = _player.getBedLocation(0);
		return new Location(loc.posX, loc.posY, loc.posZ);
	}

	public void setSpawnPoint(int x, int y, int z){
		ChunkCoordinates chunk = new ChunkCoordinates(x, y, z);
		_player.setSpawnChunk(chunk, false);
	}
	
	public void setSpawnPoint(Location loc){
		ChunkCoordinates chunk = new ChunkCoordinates(loc.x, loc.y, loc.z);
		_player.setSpawnChunk(chunk, true);
	}

	public float getAbsorptionAmount(){
		return _player.getAbsorptionAmount();
	}

	public void setAbsorptionAmount(float amount){
		_player.setAbsorptionAmount(amount);
	}

	public boolean getInvisible(){
		return _player.isInvisible();
	}

	public void setInvisible(boolean invisible){
		_player.setInvisible(invisible);
	}

	public int getScore(){
		return _player.getScore();
	}

	public void setScore(int score){
		_player.setScore(score);
	}

	public void addScore(int score){
		_player.addScore(score);
	}

	public float getExperience(){
		return _player.experience;
	}
	
	public void setExperience(int exp){
		_player.experience = exp;
	}
	
	public int getExperienceLevel(){
		return _player.experienceLevel;
	}
	
	public void setExperienceLevel(int level){
		_player.experienceLevel = level;
	}
	
	public int getExperienceTotal(){
		return _player.experienceTotal;
	}
	
	public void addExperience(int exp){
		_player.addExperience(exp);
	}

	public void addExperienceLevel(int level){
		_player.addExperienceLevel(level);
	}
	
	public PlayerInventory getInventory(){
		return _inventory;
	}
		
	public int getInventorySlotInHand(){
		return _inventory.getSlotInHand();
	}
	
	public void setInventorySlotInHand(int slot){
		_inventory.setSlotInHand(slot);
	}

	public void print(String msg){
		ChatComponentText chat = new ChatComponentText(msg);
		chat.setChatStyle(chatStyle);
		_player.addChatMessage(chat);
	}

	public void chat(String msg){
		ChatComponentText chat = new ChatComponentText(msg);
		_player.addChatMessage(chat);
	}
}
