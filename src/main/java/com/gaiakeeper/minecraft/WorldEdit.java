package com.gaiakeeper.minecraft;

import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.RegistryNamespaced;
import net.minecraft.world.World;

public class WorldEdit {
	public static final int facing_east  = 0;
	public static final int facing_west  = 1;
	public static final int facing_south = 2;
	public static final int facing_north = 3;
	public static final int facing_up    = 4;
	public static final int facing_down  = 5;
	
	public static boolean breakBlockEnabled = true;
	public static boolean placeBlockEnabled = true;
	public static boolean explosionEnabled = true;
	public static String hoeCommand = null;
	
	public static Names nameTable = Names.getDefaultNames();

	protected static ChatStyle chatStyle = new ChatStyle();
	
	static {
		initalizeDefaultNames();
	};
	
	protected World _world;
	protected Logger _logger;
	protected GameRules _gamerules;
	
	
	public WorldEdit(World world, Logger logger){
		_logger = logger;

		_world = world;
		_gamerules = new GameRules(_world);
		
		chatStyle.setColor(EnumChatFormatting.GRAY);
	};
	
	public World getWorld(){
		return _world;
	}
	
	public void log(String msg){
		MinecraftServer server = MinecraftServer.getServer();
		ServerConfigurationManager manager = server.getConfigurationManager();
		
		ChatComponentText chat = new ChatComponentText(msg);
		chat.setChatStyle(chatStyle);

		manager.sendChatMsg(chat);
	}
	
	public void setHoeCommand(String cmd){
		hoeCommand = cmd;
	}
	
	public Mob createMob(String name){
		String n = nameTable.lookup(name);
		if (n == null) n = name;
		return _createMob(n);
	}
		
	protected Mob _createMob(String name){
		Entity e = EntityList.createEntityByName(name, _world);
		if (e instanceof EntityLivingBase){
			if (e instanceof EntityLiving)
				((EntityLiving)e).onSpawnWithEgg(null);
			return new Mob((EntityLivingBase)e);
		}
		return null;		
	}
	
	public Mob createMob(String name, String namespace){
		return _createMob(namespace+name);
	}
	
	public void spawnMob(int x, int y, int z, Mob m){
		Entity entity = m.getEntity();
		entity.setPosition(x, y, z);
		_world.spawnEntityInWorld(entity);
	}
	
	public void spawnMob(Location loc, Mob m){
		spawnMob(loc.x, loc.y, loc.z, m);
	}

	public Block createBlock(String name){
		return _createBlock(name, "minecraft");
	}

	protected Block _createBlock(String name, String namespace){
		String n = nameTable.lookup(name);
		if (n == null) n = name;
		
		return createBlock(n, namespace);
	}

	public Block createBlock(String name, String namespace){
		String namespace_name = namespace + ":" + name;
		return Block.getBlockFromName(namespace_name);
	}
	
	public Block createUnbreakableBlock(String name){
		return _createUnbreakableBlock(name, "minecraft");
	}

	public Block _createUnbreakableBlock(String name, String namespace){
		String n = nameTable.lookup(name);
		if (n == null) n = name;
		
		return createUnbreakableBlock(n, namespace);
	}

	public Block createUnbreakableBlock(String name, String namespace){
		String namespace_name = namespace + ":" + name;
		Block b = Block.getBlockFromName(namespace_name);
		if ( b != null){
			b.setBlockUnbreakable();
		}
		return b;
	}
	
	public Block getBlock(int x, int y, int z){
		return _world.getBlock(x, y, z);
	}
	
	public Block getBlock(Location loc){
		return _world.getBlock(loc.x, loc.y, loc.z);
	}
	
	public boolean checkBlock(int x, int y, int z, Block b){
		Block p = _world.getBlock(x, y, z);
		return (Block.getIdFromBlock(p) == Block.getIdFromBlock(b));
	}
	
	public boolean checkBlock(Location loc, Block b){
		return checkBlock(loc.x, loc.y, loc.z, b);
	}
	
	public boolean checkBlockIfAir(int x, int y, int z){
		Block p = _world.getBlock(x, y, z);
		return (p instanceof BlockAir);
	}
	
	public boolean checkBlockIfAir(Location loc){
		return checkBlockIfAir(loc.x, loc.y, loc.z);
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
	public void setDoor(int x, int y, int z, int direction, String name){
		int map[] = { 0, 2, 1, 3, 0, 0 };		// east, west, south, north, up, down
		
		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
			_world.setBlock(x, y+1, z, b);
			_world.setBlockMetadataWithNotify(x,  y+1,  z, 0x08, 3);
		}
	}

	public void setDoor(Location loc, int direction, String name){
		setDoor(loc.x, loc.y, loc.z, direction, name);
	}
	
	// direction - 0: down, 1: east, 2: west, 3: south, 4: north, 5: up
	public void setButton(int x, int y, int z, int direction, String name){
		int map[] = { 1, 2, 3, 4, 5, 0 };		// east, west, south, north, up, down

		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
		}
	}

	public void setButton(Location loc, int direction, String name){
		setButton(loc.x, loc.y, loc.z, direction, name);
	}
	
	// direction - 1: east, 2: west, 3: south, 4: north, 5: up
	public void setTorch(int x, int y, int z, int direction, String name){
		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, direction + 1, 3);
		}
	}

	public void setTrapDoor(Location loc, int direction, String name){
		setTrapDoor(loc.x, loc.y, loc.z, direction, name);
	}
	
	// direction - 0: south, 1: north, 2: east, 3: west
	public void setTrapDoor(int x, int y, int z, int direction, String name){
		int map[] = { 2, 3, 0, 1, 0, 0 };		// east, west, south, north, up, down
		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
		}
	}

	public void setTorch(Location loc, int direction, String name){
		setTorch(loc.x, loc.y, loc.z, direction, name);
	}
	
	// direction - 1: east, 2: west, 3: south, 4: north
	public void setLever(int x, int y, int z, int direction, String name){
		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, direction + 1, 3);
		}
	}

	public void setLever(Location loc, int direction, String name){
		setLever(loc.x, loc.y, loc.z, direction, name);
	}

	// direction - 2: north, 3: south, 4: west, 5: east
	public void setLadder(int x, int y, int z, int direction, String name){
		int map[] = { 5, 4, 3, 2, 2, 2 };		// east, west, south, north, up, down

		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
		}
	}

	public void setLadder(Location loc, int direction, String name){
		setLadder(loc.x, loc.y, loc.z, direction, name);
	}

	// direction - 2: north, 3: south, 4: west, 5: east
	public void setChest(int x, int y, int z, int direction, String name){
		int map[] = { 5, 4, 3, 2, 2, 2 };		// east, west, south, north, up, down

		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
		}
	}

	public void setChest(Location loc, int direction, String name){
		setChest(loc.x, loc.y, loc.z, direction, name);
	}
	
	public Inventory getChestInventory(int x, int y, int z){
		IInventory inventory = (IInventory)_world.getTileEntity(x, y, z);
		
		if ( inventory != null ) return new Inventory(inventory);
		return null;
	}
	
	public Inventory getChestInventory(Location loc){
		return getChestInventory(loc.x, loc.y, loc.z);
	}

	// direction - 2: north, 3: south, 4: west, 5: east
	public void setFurnace(int x, int y, int z, int direction, String name){
		int map[] = { 5, 4, 3, 2, 2, 2 };		// east, west, south, north, up, down

		Block b = createBlock(name);
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
		}
	}

	public void setFurnace(Location loc, int direction, String name){
		setFurnace(loc.x, loc.y, loc.z, direction, name);
	}

	// direction - 2: north, 3: south, 4: west, 5: east
	public void setWallSign(int x, int y, int z, int direction, String[] contents){
		int map[] = { 5, 4, 3, 2, 0, 0 };		// east, west, south, north, up, down

		Block b = createBlock("wall_sign");
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
			TileEntitySign te = (TileEntitySign)_world.getTileEntity(x, y, z);
			te.signText[0] = contents[0];
			te.signText[1] = contents[1];
			te.signText[2] = contents[2];
			te.signText[3] = contents[3];
		}
	}

	public void setWallSign(Location loc, int direction, String[] contents){
		setWallSign(loc.x, loc.y, loc.z, direction, contents);
	}
	
	// direction - 0: south, 4: west, 8: north, 12: east
	public void setStandingSign(int x, int y, int z, int direction, String[] contents){
		int map[] = { 12, 4, 0, 8, 0, 0 };		// east, west, south, north, up, down

		Block b = createBlock("standing_sign");
		if ( b != null ){
			_world.setBlock(x, y, z, b);
			_world.setBlockMetadataWithNotify(x,  y,  z, map[direction], 3);
			TileEntitySign te = (TileEntitySign)_world.getTileEntity(x, y, z);
			te.signText[0] = contents[0];
			te.signText[1] = contents[1];
			te.signText[2] = contents[2];
			te.signText[3] = contents[3];
		}
	}

	public void setStandingSign(Location loc, int direction, String[] contents){
		setStandingSign(loc.x, loc.y, loc.z, direction, contents);
	}
	
	public void setCommandBlock(int x, int y, int z, String command){
		Block b = createBlock("command_block");
		_world.setBlock(x, y, z, b);
		TileEntityCommandBlock te = (TileEntityCommandBlock)_world.getTileEntity(x, y, z);
		te.func_145993_a().func_145752_a(command);
	}
	
	public void setCommandBlock(Location loc, String command){
		setCommandBlock(loc.x, loc.y, loc.z, command);
	}
	
	public void setMobSpawner(int x, int y, int z, String name){
		Block b = createBlock("mob_spawner");
		_world.setBlock(x, y, z, b);
		TileEntityMobSpawner te = (TileEntityMobSpawner)_world.getTileEntity(x, y, z);
		MobSpawnerBaseLogic logic = te.func_145881_a();
		logic.setEntityName(name);
	}

	public void setMobSpawner(Location loc, String name){
		setMobSpawner(loc.x, loc.y, loc.z, name);
	}

	protected void rectY(int sx, int sz, int ex, int ez, int y, int thickness, Block b){
		for(int x = sx; x <= ex; x ++){
			for (int t = 0; t < thickness; t ++){
				_world.setBlock(x, y, sz + t, b);
				_world.setBlock(x, y, ez - t, b);
			}
		}
		for(int z = sz; z <= ez; z ++){
			for (int t = 0; t < thickness; t ++){
				_world.setBlock(sx + t, y, z, b);
				_world.setBlock(ex - t, y, z, b);
			}
		}
	}

	protected void rectY(int sx, int sz, int ex, int ez, int y, Block b){
		rectY(sx, sz, ex, ez, y, 1, b);
	}
	
	protected void planeY(int sx, int sz, int ex, int ez, int sy, int thickness, Block b){
		int ey = sy + thickness;
		for(int y = sy; y < ey; y++){
			for(int z = sz; z <= ez; z ++){
				for(int x = sx; x <= ex; x ++){
					_world.setBlock(x, y, z, b);
				}
			}
		}
	}

	protected void planeY(int sx, int sz, int ex, int ez, int y, Block b){
		planeY(sx, sz, ex, ez, y, 1, b);
	}
	
	public void fill(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		if (sx > ex){ int t = sx; sx = ex; ex = t; }
		if (sy > ey){ int t = sy; sy = ey; ey = t; }
		if (sz > ez){ int t = sz; sz = ez; ez = t; }
		
		for (int y=sy; y<=ey; y++){
			for (int z=sz; z<=ez; z++){
				for (int x=sx; x<=ex; x++){
					_world.setBlock(x, y, z, b);
				}
			}
		}
	}
	
	public void fill(Location start, Location end, Block b){
		fill(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}

	public void cube(int sx, int sy, int sz, int ex, int ey, int ez, int thickness, Block b){
		if (sx > ex){ int t = sx; sx = ex; ex = t; }
		if (sy > ey){ int t = sy; sy = ey; ey = t; }
		if (sz > ez){ int t = sz; sz = ez; ez = t; }
		
		// top plane
		planeY(sx, sz, ex, ez, ey - thickness + 1, thickness, b);
		
		// bottom plane
		planeY(sx, sz, ex, ez, sy, thickness, b);
		
		for (int y = sy + thickness; y <= ey - thickness; y ++){
			rectY(sx, sz, ex, ez, y, thickness, b);
		}
	}
	
	public void cube(Location start, Location end, int thickness, Block b){
		cube(start.x, start.y, start.z, end.x, end.y, end.z, thickness, b);
	}

	public void cube(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		cube(sx, sy, sz, ex, ey, ez, 1, b);
	}
	
	public void cube(Location start, Location end, Block b){
		cube(start.x, start.y, start.z, end.x, end.y, end.z, 1, b);
	}

	public void cross(int sx, int sy, int sz, int ex, int ey, int ez, int thickness, Block b){
		int mx = (sx + ex) / 2 - thickness / 2;
		int mz = (sz + ez) / 2 - thickness / 2;
		
		fill(mx, sy, sz, mx + thickness - 1, sy, ez, b);
		fill(sx, sy, mz, ex, sy, mz + thickness - 1, b);
	}
	
	public void cross(Location start, Location end, int thickness, Block b){
		cross(start.x, start.y, start.z, end.x, end.y, end.z, thickness, b);
	}

	public void cross(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		cross(sx, sy, sz, ex, ey, ez, 1, b);
	}
	
	public void cross(Location start, Location end, Block b){
		cross(start.x, start.y, start.z, end.x, end.y, end.z, 1, b);
	}

	public void wall(int sx, int sy, int sz, int ex, int ey, int ez, int thickness, Block b){
		for (int y=sy; y<=ey; y++){
			rectY(sx, sz, ex, ez, y, thickness, b);
		}
	}
	
	public void wall(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		wall(sx, sy, sz, ex, ey, ez, 1, b);
	}
	
	public void wall(Location start, Location end, int thickness, Block b){
		wall(start.x, start.y, start.z, end.x, end.y, end.z, thickness, b);
	}

	public void wall(Location start, Location end, Block b){
		wall(start.x, start.y, start.z, end.x, end.y, end.z, 1, b);
	}

	public void planeX(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
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
	
	public void planeX(Location start, Location end, Block b){
		planeX(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}

	public void planeZ(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
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
	
	public void planeZ(Location start, Location end, Block b){
		planeZ(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}
	
	public void bridgeX(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		int mz = (sz + ez) / 2;
		planeX(sx, sy, sz, ex, ey, mz, b);
		planeX(ex, ey, mz, sx, sy, ez, b);
	}

	public void bridgeX(Location start, Location end, Block b){
		bridgeX(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}

	public void bridgeZ(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		int mx = (sx + ex) / 2;
		planeZ(sx, sy, sz, mx, ey, ez, b);
		planeZ(mx, ey, ez, ex, sy, sz, b);
	}

	public void bridgeZ(Location start, Location end, Block b){
		bridgeZ(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}
	
	public void roof(int sx, int sy, int sz, int ex, int ey, int ez, Block b){
		if (sx > ex){ int t = sx; sx = ex; ex = t; }
		if (sy > ey){ int t = sy; sy = ey; ey = t; }
		if (sz > ez){ int t = sz; sz = ez; ez = t; }

		for (int y=sy; y<=ey; y++){
			rectY(sx, sz, ex, ez, y, b);
			if ( sx < ex - 1){
				sx ++; ex --;
			}
			if ( sz < ez - 1 ){
				sz ++; ez ++;
			}
		}
		
		if ( sx < ex  || sz < ez ) planeY(sx, sz, ex, ez, ey, b);
	}

	public void roof(Location start, Location end, Block b){
		roof(start.x, start.y, start.z, end.x, end.y, end.z, b);
	}

	public Item createItem(String name){
		return _createItem(name, "minecraft", 1, 0);
	}

	public Item createItem(String name, int number){
		return _createItem(name, "minecraft", number, 0);
	}

	public Item createItem(String name, int number, int damage){
		return _createItem(name, "minecraft", number, damage);
	}

	public Item createItem(String name, String namespace){
		return _createItem(name, namespace, 1, 0);
	}

	public Item createItem(String name, String namespace, int number){
		return _createItem(name, namespace, number, 0);
	}

	protected Item _createItem(String name, String namespace, int number, int damage){
		String n = nameTable.lookup(name);
		if (n == null) n = name;
		
		return createItem(n, namespace, number, damage);
	}

	public Item createItem(String name, String namespace, int number, int damage){
		String namespace_name = namespace + ":" + name;
		net.minecraft.item.Item item = (net.minecraft.item.Item)(net.minecraft.item.Item.itemRegistry.getObject(namespace_name));
		if (item != null){
			return new Item(new ItemStack(item, number, damage));
		}

		return null;
	}
	
	public TileEntity getTileEntity(int x, int y, int z){
		return _world.getTileEntity(x, y, z);
	}

	public TileEntity getTileEntity(Location loc){
		return _world.getTileEntity(loc.x, loc.y, loc.z);
	}
	
	public int countMob(String name){
		int count = 0;
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( name.equalsIgnoreCase(EntityList.getEntityString(e)) ){
				count ++;
			}
		}
		
		return count;
	}
	
	public int countMob(String name, String namespace){
		return countMob(namespace + name);
	}
	
	public int countAllMobs(){
		int count = 0;
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( (e != null) && (e instanceof EntityLiving)){
				count ++;
			}
		}
		
		return count;
	}
	
	public int countAllEntities(){
		int count = 0;
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( e != null && !(e instanceof EntityPlayer)){
				count ++;
			}
		}
		
		return count;
	}
	
	public int killMob(String name){
		int count = 0;
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( name.equalsIgnoreCase(EntityList.getEntityString(e)) ){
				_world.removeEntity(e);
				count ++;
			}
		}
		
		return count;
	}
	
	public int killMob(String name, String namespace){
		return killMob(namespace + name);
	}
	
	public int killAllMobs(){
		int count = 0;
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( (e != null) && (e instanceof EntityLiving)){
				_world.removeEntity(e);
				count ++;
			}
		}
		
		return count;
	}
	
	public int killAllEntities(){
		int count = 0;
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( e != null && !(e instanceof EntityPlayer)){
				_world.removeEntity(e);
				count ++;
			}
		}
		
		return count;
	}
	
	public Mob findMob(String name){
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( e != null && (e instanceof EntityLiving)){
_logger.info("---------------------------- findMob()" + ((EntityLiving)e).getCommandSenderName() + "...");
				if ( name.equalsIgnoreCase(((EntityLiving)e).getCommandSenderName())){
_logger.info("---------------------------- findMob() FOUND -----------------------");
					return new Mob((EntityLiving)e);
				}
			}
		}
		
		return null;
	}

	public GameRules getGameRules(){
		return _gamerules;
	}
	
	public void printAllMobs(){
		_logger.info("---------------------------- printAllMobs() BEGIN -----------------------");
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( (e != null) && (e instanceof EntityLiving) ){
				int id = e.getEntityId();
				_logger.info("Entity [id: " + id + ", entity: " + e
						+ ", name: " + ((EntityLiving)e).getCommandSenderName() + ", " + ((EntityLiving)e).getCustomNameTag());
			}
		}
		_logger.info("---------------------------- printAllMobs() END -----------------------");
	}
	
	public void printAllEntities(){
		_logger.info("---------------------------- printAllEntities() BEGIN -----------------------");
		Iterator itr = _world.loadedEntityList.iterator();
		while ( itr.hasNext() ){
			Entity e = (Entity)itr.next();
			if ( e != null){
				int id = e.getEntityId();
				_logger.info("Entity [id: " + id + ", entity: " + e);
			}
		}
		_logger.info("---------------------------- printAllEntities() END -----------------------");
	}	
	
	public void printAllItemTypes(){
		_logger.info("---------------------------- printAllItemTypes() BEGIN -----------------------");
		RegistryNamespaced registry = net.minecraft.item.Item.itemRegistry;
		Iterator itr = registry.iterator();
		while( itr.hasNext() ){
			Object obj = itr.next();
			
			int id = registry.getIDForObject(obj);
			String name = registry.getNameForObject(obj);
			_logger.info("Item [id: " + id + "], name: " + name);
		}
		_logger.info("---------------------------- printAllItemTypes() END -----------------------");
	}
	
	public void printAllBlockTypes(){
		_logger.info("---------------------------- printAllBlockTypes() BEGIN -----------------------");
		RegistryNamespaced registry = Block.blockRegistry;
		Iterator itr = registry.iterator();
		while( itr.hasNext() ){
			Object obj = itr.next();
			
			int id = registry.getIDForObject(obj);
			String name = registry.getNameForObject(obj);
			_logger.info("Block [id: " + id + "], name: " + name);
		}
		_logger.info("---------------------------- printAllBlockTypes() END -----------------------");
	}
	
	public void printAllEntityTypes(){
		_logger.info("---------------------------- printAllEntityTypes() BEGIN -----------------------");
		Map stringToClassMapping = EntityList.stringToClassMapping;
		
		Set Names = stringToClassMapping.keySet();
		Iterator itr = Names.iterator();
		while( itr.hasNext() ){
			String name = (String)itr.next();
			Class c = (Class)stringToClassMapping.get(name);

			_logger.info("Entity name: " + name);
		}
		/*
//		Map IDtoClassMapping = EntityList.IDtoClassMapping;
		Map classToStringMapping = EntityList.classToStringMapping;

		Set IDs = IDtoClassMapping.keySet();
		Iterator itr = IDs.iterator();
		while( itr.hasNext() ){
			Integer id = (Integer)itr.next();
			Class c = (Class)IDtoClassMapping.get(id);
			String name = (String)classToStringMapping.get(c);

			_logger.info("Entity [id: " + id + "], name: " + name);
		}
		*/
		_logger.info("---------------------------- printAllEntityTypes() END -----------------------");
	}
	
	private static void initalizeDefaultNames(){
		// blocks & items
		nameTable.add("acaciastairs", "acacia_stairs");
		nameTable.add("activatorrail", "activator_rail");
		nameTable.add("air", "air block", "air");
		nameTable.add("anvil", "anvil");
		nameTable.add("apple", "apple");
		nameTable.add("arrow", "arrow");
		nameTable.add("bakedpotato", "baked_potato");
		nameTable.add("beacon", "beacon");
		nameTable.add("bed", "bed");
		nameTable.add("bedrock", "bedrock");
		nameTable.add("beef", "beef");
		nameTable.add("birchstairs", "birch_stairs");
		nameTable.add("blazepowder", "blaze_powder");
		nameTable.add("blazerod", "blaze_rod");
		nameTable.add("boat", "boat");
		nameTable.add("bone", "bone");
		nameTable.add("book", "book");
		nameTable.add("bookshelf", "bookshelf");
		nameTable.add("bow", "bow");
		nameTable.add("bowl", "bowl");
		nameTable.add("bread", "bread");
		nameTable.add("brewingstand", "brewing_stand");
		nameTable.add("brick", "brick");
		nameTable.add("brickblock", "brick_block");
		nameTable.add("brickstairs", "brick_stairs");
		nameTable.add("brownmushroom", "brown_mushroom");
		nameTable.add("brownmushroomblock", "brown_mushroom_block");
		nameTable.add("bucket", "bucket");
		nameTable.add("cactus", "cactus");
		nameTable.add("cake", "cake");
		nameTable.add("carpet", "carpet");
		nameTable.add("carrot", "carrot");
		nameTable.add("carrotonastick", "carrot_on_a_stick");
		nameTable.add("carrots", "carrots");
		nameTable.add("cauldron", "cauldron");
		nameTable.add("chainmailboots", "chainmail_boots");
		nameTable.add("chainmailchestplate", "chainmail_chestplate");
		nameTable.add("chainmailhelmet", "chainmail_helmet");
		nameTable.add("chainmailleggings", "chainmail_leggings");
		nameTable.add("chest", "chest");
		nameTable.add("chestminecart", "chest_minecart");
		nameTable.add("chicken", "chicken");
		nameTable.add("clay", "clay");
		nameTable.add("clayball", "clay_ball");
		nameTable.add("clock", "clock");
		nameTable.add("coal", "coal");
		nameTable.add("coalblock", "coal_block");
		nameTable.add("coalore", "coal_ore");
		nameTable.add("cobblestone", "cobblestone");
		nameTable.add("cobblestonewall", "cobblestone_wall");
		nameTable.add("cocoa", "cocoa");
		nameTable.add("commandblock", "command_block");
		nameTable.add("commandblockminecart", "command_block_minecart");
		nameTable.add("comparator", "comparator");
		nameTable.add("compass", "compass");
		nameTable.add("cookedbeef", "cooked_beef");
		nameTable.add("cookedchicken", "cooked_chicken");
		nameTable.add("cookedfished", "cooked_fished");
		nameTable.add("cookedporkchop", "cooked_porkchop");
		nameTable.add("cookie", "cookie");
		nameTable.add("craftingtable", "crafting_table");
		nameTable.add("darkoakstairs", "dark_oak_stairs");
		nameTable.add("daylightdetector", "daylight_detector");
		nameTable.add("deadbush", "deadbush");
		nameTable.add("detectorrail", "detector_rail");
		nameTable.add("diamond", "diamond");
		nameTable.add("diamondaxe", "diamond_axe");
		nameTable.add("diamondblock", "diamond_block");
		nameTable.add("diamondboots", "diamond_boots");
		nameTable.add("diamondchestplate", "diamond_chestplate");
		nameTable.add("diamondhelmet", "diamond_helmet");
		nameTable.add("diamondhoe", "diamond_hoe");
		nameTable.add("diamondhorsearmor", "diamond_horse_armor");
		nameTable.add("diamondleggings", "diamond_leggings");
		nameTable.add("diamondore", "diamond_ore");
		nameTable.add("diamondpickaxe", "diamond_pickaxe");
		nameTable.add("diamondshovel", "diamond_shovel");
		nameTable.add("diamondsword", "diamond_sword");
		nameTable.add("dirt", "dirt block", "dirt");
		nameTable.add("dispenser", "dispenser");
		nameTable.add("doubleplant", "double_plant");
		nameTable.add("doublestoneslab", "double_stone_slab");
		nameTable.add("doublewoodenslab", "double_wooden_slab");
		nameTable.add("dragonegg", "dragon_egg");
		nameTable.add("dropper", "dropper");
		nameTable.add("dye", "dye");
		nameTable.add("egg", "egg");
		nameTable.add("emerald", "emerald");
		nameTable.add("emeraldblock", "emerald_block");
		nameTable.add("emeraldore", "emerald_ore");
		nameTable.add("enchantedbook", "enchanted_book");
		nameTable.add("enchantingtable", "enchanting_table");
		nameTable.add("endportal", "end_portal");
		nameTable.add("endportalframe", "end_portal_frame");
		nameTable.add("endstone", "end_stone");
		nameTable.add("enderchest", "ender_chest");
		nameTable.add("endereye", "ender_eye");
		nameTable.add("enderpearl", "ender_pearl");
		nameTable.add("experiencebottle", "experience_bottle");
		nameTable.add("farmland", "farmland");
		nameTable.add("feather", "feather");
		nameTable.add("fence", "fence");
		nameTable.add("fencegate", "fence_gate");
		nameTable.add("fermentedspidereye", "fermented_spider_eye");
		nameTable.add("filledmap", "filled_map");
		nameTable.add("fire", "fire");
		nameTable.add("firecharge", "fire_charge");
		nameTable.add("fireworkcharge", "firework_charge");
		nameTable.add("fireworks", "fireworks");
		nameTable.add("fish", "fish");
		nameTable.add("fishingrod", "fishing_rod");
		nameTable.add("flint", "flint");
		nameTable.add("flintandsteel", "flint_and_steel");
		nameTable.add("flowerpot", "flower_pot");
		nameTable.add("flowinglava", "flowing_lava");
		nameTable.add("flowingwater", "flowing_water");
		nameTable.add("furnace", "furnace");
		nameTable.add("furnaceminecart", "furnace_minecart");
		nameTable.add("ghasttear", "ghast_tear");
		nameTable.add("glass", "glass block",  "glass");
		nameTable.add("glassbottle", "glass_bottle");
		nameTable.add("glasspane", "glass_pane");
		nameTable.add("glowstone", "glowstone");
		nameTable.add("glowstonedust", "glowstone_dust");
		nameTable.add("goldblock", "gold", "gold_block");
		nameTable.add("goldingot", "gold_ingot");
		nameTable.add("goldnugget", "gold_nugget");
		nameTable.add("goldore", "gold_ore");
		nameTable.add("goldenapple", "golden_apple");
		nameTable.add("goldenaxe", "golden_axe");
		nameTable.add("goldenboots", "golden_boots");
		nameTable.add("goldencarrot", "golden_carrot");
		nameTable.add("goldenchestplate", "golden_chestplate");
		nameTable.add("goldenhelmet", "golden_helmet");
		nameTable.add("goldenhoe", "golden_hoe");
		nameTable.add("goldenhorsearmor", "golden_horse_armor");
		nameTable.add("goldenleggings", "golden_leggings");
		nameTable.add("goldenpickaxe", "golden_pickaxe");
		nameTable.add("goldenrail", "golden_rail");
		nameTable.add("goldenshovel", "golden_shovel");
		nameTable.add("goldensword", "golden_sword");
		nameTable.add("grass", "grass block", "grass");
		nameTable.add("gravel", "gravel");
		nameTable.add("gunpowder", "gunpowder");
		nameTable.add("hardenedclay", "hardened_clay");
		nameTable.add("hayblock", "hay_block");
		nameTable.add("heavyweightedpressureplate", "heavy_weighted_pressure_plate");
		nameTable.add("hopper", "hopper");
		nameTable.add("hopperminecart", "hopper_minecart");
		nameTable.add("ice", "ice");
		nameTable.add("ironaxe", "iron_axe");
		nameTable.add("ironbars", "iron_bars");
		nameTable.add("ironblock", "iron", "iron_block");
		nameTable.add("ironboots", "iron_boots");
		nameTable.add("ironchestplate", "iron_chestplate");
		nameTable.add("irondoor", "iron_door");
		nameTable.add("ironhelmet", "iron_helmet");
		nameTable.add("ironhoe", "iron_hoe");
		nameTable.add("ironhorsearmor", "iron_horse_armor");
		nameTable.add("ironingot", "iron_ingot");
		nameTable.add("ironleggings", "iron_leggings");
		nameTable.add("ironore", "iron_ore");
		nameTable.add("ironpickaxe", "iron_pickaxe");
		nameTable.add("ironshovel", "iron_shovel");
		nameTable.add("ironsword", "iron_sword");
		nameTable.add("itemframe", "item_frame");
		nameTable.add("jukebox", "jukebox");
		nameTable.add("junglestairs", "jungle_stairs");
		nameTable.add("ladder", "ladder");
		nameTable.add("lapisblock", "lapis_block");
		nameTable.add("lapisore", "lapis_ore");
		nameTable.add("lava", "lava");
		nameTable.add("lavabucket", "lava_bucket");
		nameTable.add("lead", "lead");
		nameTable.add("leather", "leather");
		nameTable.add("leatherboots", "leather_boots");
		nameTable.add("leatherchestplate", "leather_chestplate");
		nameTable.add("leatherhelmet", "leather_helmet");
		nameTable.add("leatherleggings", "leather_leggings");
		nameTable.add("leaves", "leaves");
		nameTable.add("leaves2", "leaves2");
		nameTable.add("lever", "lever");
		nameTable.add("lightweightedpressureplate", "light_weighted_pressure_plate");
		nameTable.add("litfurnace", "lit_furnace");
		nameTable.add("litpumpkin", "lit_pumpkin");
		nameTable.add("litredstonelamp", "lit_redstone_lamp");
		nameTable.add("litredstoneore", "lit_redstone_ore");
		nameTable.add("log", "log");
		nameTable.add("log2", "log2");
		nameTable.add("magmacream", "magma_cream");
		nameTable.add("map", "map");
		nameTable.add("melon", "melon");
		nameTable.add("melonblock", "melon_block");
		nameTable.add("melonseeds", "melon_seeds");
		nameTable.add("melonstem", "melon_stem");
		nameTable.add("milkbucket", "milk_bucket");
		nameTable.add("minecart", "minecart");
		nameTable.add("mobspawner", "mob_spawner");
		nameTable.add("monsteregg", "monster_egg");
		nameTable.add("mossycobblestone", "mossy_cobblestone");
		nameTable.add("mushroomstew", "mushroom_stew");
		nameTable.add("mycelium", "mycelium");
		nameTable.add("nametag", "name_tag");
		nameTable.add("netherbrick", "nether_brick");
		nameTable.add("netherbrickfence", "nether_brick_fence");
		nameTable.add("netherbrickstairs", "nether_brick_stairs");
		nameTable.add("netherstar", "nether_star");
		nameTable.add("netherwart", "nether_wart");
		nameTable.add("netherbrick", "netherbrick");
		nameTable.add("netherrack", "netherrack");
		nameTable.add("noteblock", "noteblock");
		nameTable.add("oakstairs", "oak_stairs");
		nameTable.add("obsidian", "obsidian");
		nameTable.add("packedice", "packed_ice");
		nameTable.add("painting", "painting");
		nameTable.add("paper", "paper");
		nameTable.add("piston", "piston");
		nameTable.add("pistonextension", "piston_extension");
		nameTable.add("pistonhead", "piston_head");
		nameTable.add("planks", "planks");
		nameTable.add("poisonouspotato", "poisonous_potato");
		nameTable.add("porkchop", "porkchop");
		nameTable.add("portal", "portal");
		nameTable.add("potato", "potato");
		nameTable.add("potatoes", "potatoes");
		nameTable.add("potion", "potion");
		nameTable.add("poweredcomparator", "powered_comparator");
		nameTable.add("poweredrepeater", "powered_repeater");
		nameTable.add("pumpkin", "pumpkin");
		nameTable.add("pumpkinpie", "pumpkin_pie");
		nameTable.add("pumpkinseeds", "pumpkin_seeds");
		nameTable.add("pumpkinstem", "pumpkin_stem");
		nameTable.add("quartz", "quartz");
		nameTable.add("quartzblock", "quartz_block");
		nameTable.add("quartzore", "quartz_ore");
		nameTable.add("quartzstairs", "quartz_stairs");
		nameTable.add("rail", "rail");
		nameTable.add("record11", "record_11");
		nameTable.add("record13", "record_13");
		nameTable.add("recordblocks", "record_blocks");
		nameTable.add("recordcat", "record_cat");
		nameTable.add("recordchirp", "record_chirp");
		nameTable.add("recordfar", "record_far");
		nameTable.add("recordmall", "record_mall");
		nameTable.add("recordmellohi", "record_mellohi");
		nameTable.add("recordstal", "record_stal");
		nameTable.add("recordstrad", "record_strad");
		nameTable.add("recordwait", "record_wait");
		nameTable.add("recordward", "record_ward");
		nameTable.add("redflower", "red_flower");
		nameTable.add("redmushroom", "red_mushroom");
		nameTable.add("redmushroomblock", "red_mushroom_block");
		nameTable.add("redstone", "redstone");
		nameTable.add("redstoneblock", "redstone_block");
		nameTable.add("redstonelamp", "redstone_lamp");
		nameTable.add("redstoneore", "redstone_ore");
		nameTable.add("redstonetorch", "redstone_torch");
		nameTable.add("redstonewire", "redstone_wire");
		nameTable.add("reeds", "reeds");
		nameTable.add("repeater", "repeater");
		nameTable.add("rottenflesh", "rotten_flesh");
		nameTable.add("saddle", "saddle");
		nameTable.add("sand", "sand block", "sand");
		nameTable.add("sandstone", "sandstone");
		nameTable.add("sandstonestairs", "sandstone_stairs");
		nameTable.add("sapling", "sapling");
		nameTable.add("shears", "shears");
		nameTable.add("sign", "sign");
		nameTable.add("skull", "skull");
		nameTable.add("slimeball", "slime_ball");
		nameTable.add("snow", "snow");
		nameTable.add("snowlayer", "snow_layer");
		nameTable.add("snowball", "snowball");
		nameTable.add("soulsand", "soul_sand");
		nameTable.add("spawnegg", "spawn_egg");
		nameTable.add("speckledmelon", "speckled_melon");
		nameTable.add("spidereye", "spider_eye");
		nameTable.add("sponge", "sponge");
		nameTable.add("sprucestairs", "spruce_stairs");
		nameTable.add("stainedglass", "stained_glass");
		nameTable.add("stainedglasspane", "stained_glass_pane");
		nameTable.add("stainedhardenedclay", "stained_hardened_clay");
		nameTable.add("standingsign", "standing_sign");
		nameTable.add("stick", "stick");
		nameTable.add("stickypiston", "sticky_piston");
		nameTable.add("stone", "stone block", "stone");
		nameTable.add("stoneaxe", "stone_axe");
		nameTable.add("stonebrickstairs", "stone_brick_stairs");
		nameTable.add("stonebutton", "stone_button");
		nameTable.add("stonehoe", "stone_hoe");
		nameTable.add("stonepickaxe", "stone_pickaxe");
		nameTable.add("stonepressureplate", "stone_pressure_plate");
		nameTable.add("stoneshovel", "stone_shovel");
		nameTable.add("stoneslab", "stone_slab");
		nameTable.add("stonestairs", "stone_stairs");
		nameTable.add("stonesword", "stone_sword");
		nameTable.add("stonebrick", "stonebrick");
		nameTable.add("string", "string");
		nameTable.add("sugar", "sugar");
		nameTable.add("tallgrass", "tallgrass");
		nameTable.add("tnt", "tnt");
		nameTable.add("tntminecart", "tnt_minecart");
		nameTable.add("torch", "torch");
		nameTable.add("trapdoor", "trapdoor");
		nameTable.add("trappedchest", "trapped_chest");
		nameTable.add("tripwire", "tripwire");
		nameTable.add("tripwirehook", "tripwire_hook");
		nameTable.add("unlitredstonetorch", "unlit_redstone_torch");
		nameTable.add("unpoweredcomparator", "unpowered_comparator");
		nameTable.add("unpoweredrepeater", "unpowered_repeater");
		nameTable.add("vine", "vine");
		nameTable.add("wallsign", "wall_sign");
		nameTable.add("water", "water");
		nameTable.add("waterbucket", "water_bucket");
		nameTable.add("waterlily", "waterlily");
		nameTable.add("web", "web");
		nameTable.add("wheat", "wheat");
		nameTable.add("wheatseeds", "wheat_seeds");
		nameTable.add("woodenaxe", "wooden_axe");
		nameTable.add("woodenbutton", "wooden_button");
		nameTable.add("woodendoor", "wooden_door");
		nameTable.add("woodenhoe", "wooden_hoe");
		nameTable.add("woodenpickaxe", "wooden_pickaxe");
		nameTable.add("woodenpressureplate", "wooden_pressure_plate");
		nameTable.add("woodenshovel", "wooden_shovel");
		nameTable.add("woodenslab", "wooden_slab");
		nameTable.add("woodensword", "wooden_sword");
		nameTable.add("wool", "wool");
		nameTable.add("writablebook", "writable_book");
		nameTable.add("writtenbook", "written_book");
		nameTable.add("yellowflower", "yellow_flower");
		nameTable.add("mob", "Mob");
		nameTable.add("monster", "Monster");
		nameTable.add("creeper", "Creeper");
		nameTable.add("skeleton", "Skeleton");
		nameTable.add("spider", "Spider");
		nameTable.add("giant", "Giant");
		nameTable.add("zombie", "Zombie");
		nameTable.add("slime", "Slime");
		nameTable.add("ghast", "Ghast");
		nameTable.add("pigzombie", "zombiepig", "PigZombie");
		nameTable.add("enderman", "Enderman");
		nameTable.add("cavespider", "CaveSpider");
		nameTable.add("silverfish", "Silverfish");
		nameTable.add("blaze", "Blaze");
		nameTable.add("lavaslime", "LavaSlime");
		nameTable.add("enderdragon", "dragon", "EnderDragon");
		nameTable.add("witherboss", "wither", "WitherBoss");
		nameTable.add("bat", "Bat");
		nameTable.add("witch", "Witch");
		nameTable.add("pig", "Pig");
		nameTable.add("sheep", "Sheep");
		nameTable.add("cow", "Cow");
		nameTable.add("chicken", "Chicken");
		nameTable.add("squid", "Squid");
		nameTable.add("wolf", "Wolf");
		nameTable.add("mushroomcow", "MushroomCow");
		nameTable.add("snowman", "SnowMan");
		nameTable.add("ozelot", "Ozelot");
		nameTable.add("villagergolem", "golem", "VillagerGolem");
		nameTable.add("entityhorse", "horse", "EntityHorse");
		nameTable.add("villager", "Villager");
		nameTable.add("endercrystal", "EnderCrystal");
	};
}
