package com.gaiakeeper.minecraft;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.world.World;

public class WorldEdit {
	// direction - 0: east, 1: south, 2: west,3: north
	public static final int facing_east  = 0;
	public static final int facing_west  = 2;
	public static final int facing_south = 1;
	public static final int facing_north = 3;
	
	protected World _world;
	protected Logger _logger;
	protected GameRules _gamerules;
	
	public WorldEdit(World world, Logger logger){
		_logger = logger;

		_world = world;
		_gamerules = new GameRules(_world.getGameRules());
	};
	
	public World getWorld(){
		return _world;
	};
	
	public Mob createMob(int mob){
		Entity e = EntityList.createEntityByID(mob, _world);
		if (e != null){
			return new Mob((EntityLivingBase)e);
		}
		return null;
	}
	
	public Mob createMob(String name){
		int mob = MobType.lookup(name).getID();
		return createMob(mob);
	}
	
	public void spawnMob(Mob m, int x, int y, int z){
		Entity entity = m.getEntity();
		entity.setPosition(x, y, z);
		_world.spawnEntityInWorld(entity);
	}
	
	public void spawnMob(Mob m, Location loc){
		spawnMob(m, loc.x, loc.y, loc.z);
	}
	
	public Block createBlock(int block){
		return Block.getBlockById(block);
	}
	
	public Block createBlock(String name){
		int block = BlockType.lookup(name).getID();
		return createBlock(block);
	}
	
	public Block createUnbreakableBlock(int block){
		Block b = Block.getBlockById(block);
		if ( b != null){
			b.setBlockUnbreakable();
		}
		
		return b;
	}
	
	public Block createUnbreakableBlock(String name){
		int block = BlockType.lookup(name).getID();
		return createUnbreakableBlock(block);
	}
	
	public Block getBlock(int x, int y, int z){
		return _world.getBlock(x, y, z);
	}
	
	public Block getBlock(Location loc){
		return _world.getBlock(loc.x, loc.y, loc.z);
	}
	
	public void setBlock(int x, int y, int z, Block b){
		_world.setBlock(x, y, z, b);
	}

	public void setBlock(Location loc, Block b){
		_world.setBlock(loc.x, loc.y, loc.z, b);
	}
	
	public void setBlockIfAir(int x, int y, int z, Block b){
		Block p = _world.getBlock(x, y, z);
		if (p instanceof BlockAir)
			_world.setBlock(x, y, z, b);
	}

	public void setBlockIfAir(Location loc, Block b){
		setBlockIfAir(loc.x, loc.y, loc.z, b);
	}
	
	// direction - 0: east, 1: south, 2: west,3: north
	public void setDoor(int x, int y, int z, Block b, int direction){
		_world.setBlock(x, y, z, b);
		_world.setBlockMetadataWithNotify(x,  y,  z, direction, 3);
		_world.setBlock(x, y+1, z, b);
		_world.setBlockMetadataWithNotify(x,  y+1,  z, 0x08, 3);
	}

	public void setDoor(Location loc, Block b, int direction){
		setDoor(loc.x, loc.y, loc.z, b, direction);
	}
	
	public void setCommandBlock(int x, int y, int z, String command){
		Block b = Block.getBlockById(BlockID.COMMAND_BLOCK);
		_world.setBlock(x, y, z, b);
		TileEntityCommandBlock te = (TileEntityCommandBlock)_world.getTileEntity(x, y, z);
		te.func_145993_a().func_145752_a(command);
	}
	
	public void setCommandBlock(Location loc, String command){
		setCommandBlock(loc.x, loc.y, loc.z, command);
	}
	
	public void setMobSpawner(int x, int y, int z, String name){
		Block b = Block.getBlockById(BlockID.MOB_SPAWNER);
		_world.setBlock(x, y, z, b);
		TileEntityMobSpawner te = (TileEntityMobSpawner)_world.getTileEntity(x, y, z);
		MobSpawnerBaseLogic logic = te.func_145881_a();
		logic.setEntityName(name);
	}

	public void setMobSpawner(Location loc, String name){
		setMobSpawner(loc.x, loc.y, loc.z, name);
	}

	public void fill(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		if (sx > ex){ int t = sx; sx = ex; ex = t; }
		if (sy > ey){ int t = sy; sy = ey; ey = t; }
		if (sz > ez){ int t = sz; sz = ez; ez = t; }
		
		for (int y=sy; y<=ey; y++){
			for (int z=sz; z<=ez; z++){
				for (int x=sx; x<ex; x++){
					_world.setBlock(x, y, z, b);
				}
			}
		}
	}
	
	public void fill(Location start, Location end, Block b){
		fill(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}

	public void drawXRect(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		if (sx > ex){ int t = sx; sx = ex; ex = t; }

		double deltaY = (sy > ey) ? (sy - ey) : (ey - sy);
		double deltaZ = (sz > ez) ? (sz - ez) : (ez - sz);

		// Bresenham's line algorithm
		if (deltaY > deltaZ){
			if (sy > ey){
				 int t = sy; sy = ey; ey = t;
				     t = sz; sz = ez; ez = t;
			}
			int dz = (sz > ez) ? (-1) : 1;

			double error = -1.;
			double delta = deltaZ / deltaY;
			for(int z = sz, y = sy; y <= ey; y++){
				for(int x = sx; x <= ex; x++){
					_world.setBlock(x, y, z, b);
				}
				error += delta;
				if (error >= 0){
					z += dz;
					error -= 1.;
				}
			}
		}
		else {
			if (sz > ez){
				 int t = sy; sy = ey; ey = t;
				     t = sz; sz = ez; ez = t;
			}
			int dy = (sy > ey) ? (-1) : 1;

			double error = -1.;
			double delta = deltaY / deltaZ;
			for(int z = sz, y = sy; z <= ez; z++){
				for(int x = sx; x <= ex; x++){
					_world.setBlock(x, y, z, b);
				}
				error += delta;
				if (error >= 0){
					y += dy;
					error -= 1.;
				}
			}
		}
	}
	
	public void drawXRect(Location start, Location end, Block b){
		drawXRect(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}

	public void drawZRect(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		if (sz > ez){ int t = sz; sz = ez; ez = t; }

		double deltaY = (sy > ey) ? (sy - ey) : (ey - sy);
		double deltaX = (sx > ex) ? (sx - ex) : (ex - sx);

		// Bresenham's line algorithm
		if (deltaY > deltaX){
			if (sy > ey){
				 int t = sy; sy = ey; ey = t;
				     t = sx; sx = ex; ex = t;
			}
			int dx = (sx > ex) ? (-1) : 1;

			double error = -1.;
			double delta = deltaX / deltaY;
			for(int x = sx, y = sy; y <= ey; y++){
				for(int z = sz; z <= ez; z++){
					_world.setBlock(x, y, z, b);
				}
				error += delta;
				if (error >= 0){
					x += dx;
					error -= 1.;
				}
			}
		}
		else {
			if (sx > ex){
				 int t = sy; sy = ey; ey = t;
				     t = sx; sx = ex; ex = t;
			}
			int dy = (sy > ey) ? (-1) : 1;

			double error = -1.;
			double delta = deltaY / deltaX;
			for(int x = sx, y = sy; x <= ex; x++){
				for(int z = sz; z <= ez; z++){
					_world.setBlock(x, y, z, b);
				}
				error += delta;
				if (error >= 0){
					y += dy;
					error -= 1.;
				}
			}
		}
	}

	public void drawZRect(Location start, Location end, Block b){
		drawZRect(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}
	
	public TileEntity getTileEntity(int x, int y, int z){
		return _world.getTileEntity(x, y, z);
	}

	public TileEntity getTileEntity(Location loc){
		return _world.getTileEntity(loc.x, loc.y, loc.z);
	}
	
	public Inventory getChest(int x, int y, int z){
		return new Inventory((IInventory)_world.getTileEntity(x, y, z));
	}
	
	public Inventory getChest(Location loc){
		return getChest(loc.x, loc.y, loc.z);
	}
    
	public Item createItem(int item){
		return createItem(item, 1, 0);
	}

	public Item createItem(int item, int number){
		return createItem(item, number, 0);
	}

	public Item createItem(int item, int number, int damage){
		return new Item(new ItemStack(net.minecraft.item.Item.getItemById(item), number, damage));
	}

	public Item createItem(String name){
		return createItem(name, 1, 0);
	}

	public Item createItem(String name, int number){
		return createItem(name, number, 0);
	}

	public Item createItem(String name, int number, int damage){
		int item = ItemType.lookup(name).getID();
		return createItem(item, number, damage);
	}
	
	public void printAllItems(){
		_logger.info("---------------------------- printAllItems() BEGIN -----------------------");
		RegistryNamespaced registry = net.minecraft.item.Item.itemRegistry;
		Iterator itr = registry.iterator();
		while( itr.hasNext() ){
			Object obj = itr.next();
			
			int id = registry.getIDForObject(obj);
			String name = registry.getNameForObject(obj);
			_logger.info("Item [id: " + id + ", name: " + name);
		}
		_logger.info("---------------------------- printAllItems() END -----------------------");
	}
	
	public void printAllBlocks(){
		_logger.info("---------------------------- printAllBlocks() BEGIN -----------------------");
		RegistryNamespaced registry = Block.blockRegistry;
		Iterator itr = registry.iterator();
		while( itr.hasNext() ){
			Object obj = itr.next();
			
			int id = registry.getIDForObject(obj);
			String name = registry.getNameForObject(obj);
			_logger.info("Block [id: " + id + ", name: " + name);
		}
		_logger.info("---------------------------- printAllBlocks() END -----------------------");
	}
	
	public void printAllEntities(){
		_logger.info("---------------------------- printAllEntities() BEGIN -----------------------");
		Map IDtoClassMapping = EntityList.IDtoClassMapping;
		Map classToStringMapping = EntityList.classToStringMapping;
		
		Set IDs = IDtoClassMapping.keySet();
		Iterator itr = IDs.iterator();
		while( itr.hasNext() ){
			Integer id = (Integer)itr.next();
			Class c = (Class)IDtoClassMapping.get(id);
			String name = (String)classToStringMapping.get(c);

			_logger.info("Entity [id: " + id + ", name: " + name);
		}
		_logger.info("---------------------------- printAllEntities() END -----------------------");
	}
	
	public GameRules getGameRules(){
		return _gamerules;
	}
}
