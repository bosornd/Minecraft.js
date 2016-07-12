package com.gaiakeeper.minecraft;

import org.apache.logging.log4j.Logger;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;

public class Player extends Mob {
	protected EntityPlayerMP _player;
	protected PlayerInventory _inventory;
	protected Logger _logger;
	
	public Player(EntityPlayerMP player, Logger logger){
		super(player);
		
		_player = player;
		_inventory = new PlayerInventory(_player.inventory);
		_logger = logger;
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
	
	public int getDirection(){
		Vec3 v = _player.getLookVec();

		double ax = Math.abs(v.xCoord);
		double az = Math.abs(v.zCoord);
		
		if ( ax > az ){
			if ( v.xCoord > 0 ) return WorldEdit.facing_east;
			return WorldEdit.facing_west;
		}
		
		if ( v.zCoord > 0 ) return WorldEdit.facing_south;
		return WorldEdit.facing_north;
	}
	
	public int getOppositeDirection(){
		switch(getDirection()){
			case WorldEdit.facing_east : return WorldEdit.facing_west;
			case WorldEdit.facing_west : return WorldEdit.facing_east;
			case WorldEdit.facing_north : return WorldEdit.facing_south;
			case WorldEdit.facing_south : return WorldEdit.facing_north;
			case WorldEdit.facing_up : return WorldEdit.facing_down;
			case WorldEdit.facing_down : return WorldEdit.facing_up;
		}
		return WorldEdit.facing_east;
	}
	
	public Location getDirectedLocation(int width, int height, int depth){
		Location loc = getLocation();

		Vec3 v = _player.getLookVec();
		
		int t = height;
		if (v.yCoord > 0.9){ // up
			height = depth;
			depth = t;
		}
		else if (v.yCoord < -0.9){ // down
			height = -depth;
			depth = t;
		}
		
		int dir = getDirection();
		if (dir == WorldEdit.facing_east){
			loc = loc.add(depth, height, width);
		}
		else if (dir == WorldEdit.facing_west){
			loc = loc.add(-depth, height, -width);
		}
		else if (dir == WorldEdit.facing_north){
			loc = loc.add(width, height, -depth);
		}
		else if (dir == WorldEdit.facing_south){
			loc = loc.add(-width, height, depth);
		}
		
		return loc;
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

	public void chat(String msg){
		ChatComponentText chat = new ChatComponentText(msg);
		_player.addChatMessage(chat);
	}
}
