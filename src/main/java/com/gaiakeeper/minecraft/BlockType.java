package com.gaiakeeper.minecraft;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Block types.
 */
public enum BlockType {

    AIR(BlockID.AIR, "Air", "air"),
    STONE(BlockID.STONE, "Stone", "stone", "rock"),
    GRASS(BlockID.GRASS, "Grass", "grass"),
    DIRT(BlockID.DIRT, "Dirt", "dirt"),
    COBBLESTONE(BlockID.COBBLESTONE, "Cobblestone", "cobblestone", "cobble"),
    WOOD(BlockID.WOOD, "Wood", "wood", "woodplank", "plank", "woodplanks", "planks"),
    SAPLING(BlockID.SAPLING, "Sapling", "sapling", "seedling"),
    BEDROCK(BlockID.BEDROCK, "Bedrock", "adminium", "bedrock"),
    WATER(BlockID.WATER, "Water", "watermoving", "movingwater", "flowingwater", "waterflowing"),
    STATIONARY_WATER(BlockID.STATIONARY_WATER, "Water (stationary)", "water", "waterstationary", "stationarywater", "stillwater"),
    LAVA(BlockID.LAVA, "Lava", "lavamoving", "movinglava", "flowinglava", "lavaflowing"),
    STATIONARY_LAVA(BlockID.STATIONARY_LAVA, "Lava (stationary)", "lava", "lavastationary", "stationarylava", "stilllava"),
    SAND(BlockID.SAND, "Sand", "sand"),
    GRAVEL(BlockID.GRAVEL, "Gravel", "gravel"),
    GOLD_ORE(BlockID.GOLD_ORE, "Gold ore", "goldore"),
    IRON_ORE(BlockID.IRON_ORE, "Iron ore", "ironore"),
    COAL_ORE(BlockID.COAL_ORE, "Coal ore", "coalore"),
    LOG(BlockID.LOG, "Log", "log", "tree", "pine", "oak", "birch", "redwood"),
    LEAVES(BlockID.LEAVES, "Leaves", "leaves", "leaf"),
    SPONGE(BlockID.SPONGE, "Sponge", "sponge"),
    GLASS(BlockID.GLASS, "Glass", "glass"),
    LAPIS_LAZULI_ORE(BlockID.LAPIS_LAZULI_ORE, "Lapis lazuli ore", "lapislazuliore", "blueore", "lapisore"),
    LAPIS_LAZULI(BlockID.LAPIS_LAZULI_BLOCK, "Lapis lazuli", "lapislazuli", "lapislazuliblock", "bluerock"),
    DISPENSER(BlockID.DISPENSER, "Dispenser", "dispenser"),
    SANDSTONE(BlockID.SANDSTONE, "Sandstone", "sandstone"),
    NOTE_BLOCK(BlockID.NOTE_BLOCK, "Note block", "musicblock", "noteblock", "note", "music", "instrument"),
    BED(BlockID.BED, "Bed", "bed"),
    POWERED_RAIL(BlockID.POWERED_RAIL, "Powered Rail", "poweredrail", "boosterrail", "poweredtrack", "boostertrack", "booster"),
    DETECTOR_RAIL(BlockID.DETECTOR_RAIL, "Detector Rail", "detectorrail", "detector"),
    PISTON_STICKY_BASE(BlockID.PISTON_STICKY_BASE, "Sticky Piston", "stickypiston"),
    WEB(BlockID.WEB, "Web", "web", "spiderweb"),
    LONG_GRASS(BlockID.LONG_GRASS, "Long grass", "longgrass", "tallgrass"),
    DEAD_BUSH(BlockID.DEAD_BUSH, "Shrub", "deadbush", "shrub", "deadshrub", "tumbleweed"),
    PISTON_BASE(BlockID.PISTON_BASE, "Piston", "piston"),
    PISTON_EXTENSION(BlockID.PISTON_EXTENSION, "Piston extension", "pistonextendsion", "pistonhead"),
    CLOTH(BlockID.CLOTH, "Wool", "cloth", "wool"),
    PISTON_MOVING_PIECE(BlockID.PISTON_MOVING_PIECE, "Piston moving piece", "movingpiston"),
    YELLOW_FLOWER(BlockID.YELLOW_FLOWER, "Yellow flower", "yellowflower", "flower"),
    RED_FLOWER(BlockID.RED_FLOWER, "Red rose", "redflower", "redrose", "rose"),
    BROWN_MUSHROOM(BlockID.BROWN_MUSHROOM, "Brown mushroom", "brownmushroom", "mushroom"),
    RED_MUSHROOM(BlockID.RED_MUSHROOM, "Red mushroom", "redmushroom"),
    GOLD_BLOCK(BlockID.GOLD_BLOCK, "Gold block", "gold", "goldblock"),
    IRON_BLOCK(BlockID.IRON_BLOCK, "Iron block", "iron", "ironblock"),
    DOUBLE_STEP(BlockID.DOUBLE_STEP, "Double step", "doubleslab", "doublestoneslab", "doublestep"),
    STEP(BlockID.STEP, "Step", "slab", "stoneslab", "step", "halfstep"),
    BRICK(BlockID.BRICK, "Brick", "brick", "brickblock"),
    TNT(BlockID.TNT, "TNT", "tnt", "c4", "explosive"),
    BOOKCASE(BlockID.BOOKCASE, "Bookcase", "bookshelf", "bookshelves", "bookcase", "bookcases"),
    MOSSY_COBBLESTONE(BlockID.MOSSY_COBBLESTONE, "Cobblestone (mossy)", "mossycobblestone", "mossstone", "mossystone", "mosscobble", "mossycobble", "moss", "mossy", "sossymobblecone"),
    OBSIDIAN(BlockID.OBSIDIAN, "Obsidian", "obsidian"),
    TORCH(BlockID.TORCH, "Torch", "torch", "light", "candle"),
    FIRE(BlockID.FIRE, "Fire", "fire", "flame", "flames"),
    MOB_SPAWNER(BlockID.MOB_SPAWNER, "Mob spawner", "mobspawner", "spawner"),
    WOODEN_STAIRS(BlockID.OAK_WOOD_STAIRS, "Wooden stairs", "woodstair", "woodstairs", "woodenstair", "woodenstairs"),
    CHEST(BlockID.CHEST, "Chest", "chest", "storage", "storagechest"),
    REDSTONE_WIRE(BlockID.REDSTONE_WIRE, "Redstone wire", "redstone", "redstoneblock"),
    DIAMOND_ORE(BlockID.DIAMOND_ORE, "Diamond ore", "diamondore"),
    DIAMOND_BLOCK(BlockID.DIAMOND_BLOCK, "Diamond block", "diamond", "diamondblock"),
    WORKBENCH(BlockID.WORKBENCH, "Workbench", "workbench", "table", "craftingtable", "crafting"),
    CROPS(BlockID.CROPS, "Crops", "crops", "crop", "plant", "plants"),
    SOIL(BlockID.SOIL, "Soil", "soil", "farmland"),
    FURNACE(BlockID.FURNACE, "Furnace", "furnace"),
    BURNING_FURNACE(BlockID.BURNING_FURNACE, "Furnace (burning)", "burningfurnace", "litfurnace"),
    SIGN_POST(BlockID.SIGN_POST, "Sign post", "sign", "signpost"),
    WOODEN_DOOR(BlockID.WOODEN_DOOR, "Wooden door", "wooddoor", "woodendoor", "door"),
    LADDER(BlockID.LADDER, "Ladder", "ladder"),
    MINECART_TRACKS(BlockID.MINECART_TRACKS, "Minecart tracks", "track", "tracks", "minecrattrack", "minecarttracks", "rails", "rail"),
    COBBLESTONE_STAIRS(BlockID.COBBLESTONE_STAIRS, "Cobblestone stairs", "cobblestonestair", "cobblestonestairs", "cobblestair", "cobblestairs"),
    WALL_SIGN(BlockID.WALL_SIGN, "Wall sign", "wallsign"),
    LEVER(BlockID.LEVER, "Lever", "lever", "switch", "stonelever", "stoneswitch"),
    STONE_PRESSURE_PLATE(BlockID.STONE_PRESSURE_PLATE, "Stone pressure plate", "stonepressureplate", "stoneplate"),
    IRON_DOOR(BlockID.IRON_DOOR, "Iron Door", "irondoor"),
    WOODEN_PRESSURE_PLATE(BlockID.WOODEN_PRESSURE_PLATE, "Wooden pressure plate", "woodpressureplate", "woodplate", "woodenpressureplate", "woodenplate", "plate", "pressureplate"),
    REDSTONE_ORE(BlockID.REDSTONE_ORE, "Redstone ore", "redstoneore"),
    GLOWING_REDSTONE_ORE(BlockID.GLOWING_REDSTONE_ORE, "Glowing redstone ore", "glowingredstoneore"),
    REDSTONE_TORCH_OFF(BlockID.REDSTONE_TORCH_OFF, "Redstone torch (off)", "redstonetorchoff", "rstorchoff"),
    REDSTONE_TORCH_ON(BlockID.REDSTONE_TORCH_ON, "Redstone torch (on)", "redstonetorch", "redstonetorchon", "rstorchon", "redtorch"),
    STONE_BUTTON(BlockID.STONE_BUTTON, "Stone Button", "stonebutton", "button"),
    SNOW(BlockID.SNOW, "Snow", "snow"),
    ICE(BlockID.ICE, "Ice", "ice"),
    SNOW_BLOCK(BlockID.SNOW_BLOCK, "Snow block", "snowblock"),
    CACTUS(BlockID.CACTUS, "Cactus", "cactus", "cacti"),
    CLAY(BlockID.CLAY, "Clay", "clay"),
    SUGAR_CANE(BlockID.REED, "Reed", "reed", "cane", "sugarcane", "sugarcanes", "vine", "vines"),
    JUKEBOX(BlockID.JUKEBOX, "Jukebox", "jukebox", "stereo", "recordplayer"),
    FENCE(BlockID.FENCE, "Fence", "fence"),
    PUMPKIN(BlockID.PUMPKIN, "Pumpkin", "pumpkin"),
    NETHERRACK(BlockID.NETHERRACK, "Netherrack", "redmossycobblestone", "redcobblestone", "redmosstone", "redcobble", "netherstone", "netherrack", "nether", "hellstone"),
    SOUL_SAND(BlockID.SLOW_SAND, "Soul sand", "slowmud", "mud", "soulsand", "hellmud"),
    GLOWSTONE(BlockID.LIGHTSTONE, "Glowstone", "brittlegold", "glowstone", "lightstone", "brimstone", "australium"),
    PORTAL(BlockID.PORTAL, "Portal", "portal"),
    JACK_O_LANTERN(BlockID.JACKOLANTERN, "Pumpkin (on)", "pumpkinlighted", "pumpkinon", "litpumpkin", "jackolantern"),
    CAKE(BlockID.CAKE_BLOCK, "Cake", "cake", "cakeblock"),
    REDSTONE_REPEATER_OFF(BlockID.REDSTONE_REPEATER_OFF, "Redstone repeater (off)", "diodeoff", "redstonerepeater", "repeateroff", "delayeroff"),
    REDSTONE_REPEATER_ON(BlockID.REDSTONE_REPEATER_ON, "Redstone repeater (on)", "diodeon", "redstonerepeateron", "repeateron", "delayeron"),
    @Deprecated LOCKED_CHEST(BlockID.LOCKED_CHEST, "thisblockisinvalidusedstainedglassinstead"),
    STAINED_GLASS(BlockID.STAINED_GLASS, "Stained Glass", "stainedglass"),
    TRAP_DOOR(BlockID.TRAP_DOOR, "Trap door", "trapdoor", "hatch", "floordoor"),
    SILVERFISH_BLOCK(BlockID.SILVERFISH_BLOCK, "Silverfish block", "silverfish", "silver"),
    STONE_BRICK(BlockID.STONE_BRICK, "Stone brick", "stonebrick", "sbrick", "smoothstonebrick"),
    RED_MUSHROOM_CAP(BlockID.RED_MUSHROOM_CAP, "Red mushroom cap", "giantmushroomred", "redgiantmushroom", "redmushroomcap"),
    BROWN_MUSHROOM_CAP(BlockID.BROWN_MUSHROOM_CAP, "Brown mushroom cap", "giantmushroombrown", "browngiantmushoom", "brownmushroomcap"),
    IRON_BARS(BlockID.IRON_BARS, "Iron bars", "ironbars", "ironfence"),
    GLASS_PANE(BlockID.GLASS_PANE, "Glass pane", "window", "glasspane", "glasswindow"),
    MELON_BLOCK(BlockID.MELON_BLOCK, "Melon (block)", "melonblock"),
    PUMPKIN_STEM(BlockID.PUMPKIN_STEM, "Pumpkin stem", "pumpkinstem"),
    MELON_STEM(BlockID.MELON_STEM, "Melon stem", "melonstem"),
    VINE(BlockID.VINE, "Vine", "vine", "vines", "creepers"),
    FENCE_GATE(BlockID.FENCE_GATE, "Fence gate", "fencegate", "gate"),
    BRICK_STAIRS(BlockID.BRICK_STAIRS, "Brick stairs", "brickstairs", "bricksteps"),
    STONE_BRICK_STAIRS(BlockID.STONE_BRICK_STAIRS, "Stone brick stairs", "stonebrickstairs", "smoothstonebrickstairs"),
    MYCELIUM(BlockID.MYCELIUM, "Mycelium", "mycelium", "fungus", "mycel"),
    LILY_PAD(BlockID.LILY_PAD, "Lily pad", "lilypad", "waterlily"),
    NETHER_BRICK(BlockID.NETHER_BRICK, "Nether brick", "netherbrick"),
    NETHER_BRICK_FENCE(BlockID.NETHER_BRICK_FENCE, "Nether brick fence", "netherbrickfence", "netherfence"),
    NETHER_BRICK_STAIRS(BlockID.NETHER_BRICK_STAIRS, "Nether brick stairs", "netherbrickstairs", "netherbricksteps", "netherstairs", "nethersteps"),
    NETHER_WART(BlockID.NETHER_WART, "Nether wart", "netherwart", "netherstalk"),
    ENCHANTMENT_TABLE(BlockID.ENCHANTMENT_TABLE, "Enchantment table", "enchantmenttable", "enchanttable"),
    BREWING_STAND(BlockID.BREWING_STAND, "Brewing Stand", "brewingstand"),
    CAULDRON(BlockID.CAULDRON, "Cauldron"),
    END_PORTAL(BlockID.END_PORTAL, "End Portal", "endportal", "blackstuff", "airportal", "weirdblackstuff"),
    END_PORTAL_FRAME(BlockID.END_PORTAL_FRAME, "End Portal Frame", "endportalframe", "airportalframe", "crystalblock"),
    END_STONE(BlockID.END_STONE, "End Stone", "endstone", "enderstone", "endersand"),
    DRAGON_EGG(BlockID.DRAGON_EGG, "Dragon Egg", "dragonegg", "dragons"),
    REDSTONE_LAMP_OFF(BlockID.REDSTONE_LAMP_OFF, "Redstone lamp (off)", "redstonelamp", "redstonelampoff", "rslamp", "rslampoff", "rsglow", "rsglowoff"),
    REDSTONE_LAMP_ON(BlockID.REDSTONE_LAMP_ON, "Redstone lamp (on)", "redstonelampon", "rslampon", "rsglowon"),
    DOUBLE_WOODEN_STEP(BlockID.DOUBLE_WOODEN_STEP, "Double wood step", "doublewoodslab", "doublewoodstep"),
    WOODEN_STEP(BlockID.WOODEN_STEP, "Wood step", "woodenslab", "woodslab", "woodstep", "woodhalfstep"),
    COCOA_PLANT(BlockID.COCOA_PLANT, "Cocoa plant", "cocoplant", "cocoaplant"),
    SANDSTONE_STAIRS(BlockID.SANDSTONE_STAIRS, "Sandstone stairs", "sandstairs", "sandstonestairs"),
    EMERALD_ORE(BlockID.EMERALD_ORE, "Emerald ore", "emeraldore"),
    ENDER_CHEST(BlockID.ENDER_CHEST, "Ender chest", "enderchest"),
    TRIPWIRE_HOOK(BlockID.TRIPWIRE_HOOK, "Tripwire hook", "tripwirehook"),
    TRIPWIRE(BlockID.TRIPWIRE, "Tripwire", "tripwire", "string"),
    EMERALD_BLOCK(BlockID.EMERALD_BLOCK, "Emerald block", "emeraldblock", "emerald"),
    SPRUCE_WOOD_STAIRS(BlockID.SPRUCE_WOOD_STAIRS, "Spruce wood stairs", "sprucestairs", "sprucewoodstairs"),
    BIRCH_WOOD_STAIRS(BlockID.BIRCH_WOOD_STAIRS, "Birch wood stairs", "birchstairs", "birchwoodstairs"),
    JUNGLE_WOOD_STAIRS(BlockID.JUNGLE_WOOD_STAIRS, "Jungle wood stairs", "junglestairs", "junglewoodstairs"),
    COMMAND_BLOCK(BlockID.COMMAND_BLOCK, "Command block", "commandblock", "cmdblock", "command", "cmd"),
    BEACON(BlockID.BEACON, "Beacon", "beacon", "beaconblock"),
    COBBLESTONE_WALL(BlockID.COBBLESTONE_WALL, "Cobblestone wall", "cobblestonewall", "cobblewall"),
    FLOWER_POT(BlockID.FLOWER_POT, "Flower pot", "flowerpot", "plantpot", "pot"),
    CARROTS(BlockID.CARROTS, "Carrots", "carrots", "carrotsplant", "carrotsblock"),
    POTATOES(BlockID.POTATOES, "Potatoes", "potatoes", "potatoesblock"),
    WOODEN_BUTTON(BlockID.WOODEN_BUTTON, "Wooden button", "woodbutton", "woodenbutton"),
    HEAD(BlockID.HEAD, "Head", "head", "skull"),
    ANVIL(BlockID.ANVIL, "Anvil", "anvil", "blacksmith"),
    TRAPPED_CHEST(BlockID.TRAPPED_CHEST, "Trapped Chest", "trappedchest", "redstonechest"),
    PRESSURE_PLATE_LIGHT(BlockID.PRESSURE_PLATE_LIGHT, "Weighted Pressure Plate (Light)", "lightpressureplate"),
    PRESSURE_PLATE_HEAVY(BlockID.PRESSURE_PLATE_HEAVY, "Weighted Pressure Plate (Heavy)", "heavypressureplate"),
    COMPARATOR_OFF(BlockID.COMPARATOR_OFF, "Redstone Comparator (inactive)", "redstonecomparator", "comparator"),
    COMPARATOR_ON(BlockID.COMPARATOR_ON, "Redstone Comparator (active)", "redstonecomparatoron", "comparatoron"),
    DAYLIGHT_SENSOR(BlockID.DAYLIGHT_SENSOR, "Daylight Sensor", "daylightsensor", "lightsensor", "daylightdetector"),
    REDSTONE_BLOCK(BlockID.REDSTONE_BLOCK, "Block of Redstone", "redstoneblock", "blockofredstone"),
    QUARTZ_ORE(BlockID.QUARTZ_ORE, "Nether Quartz Ore", "quartzore", "netherquartzore"),
    HOPPER(BlockID.HOPPER, "Hopper", "hopper"),
    QUARTZ_BLOCK(BlockID.QUARTZ_BLOCK, "Block of Quartz", "quartzblock", "quartz"),
    QUARTZ_STAIRS(BlockID.QUARTZ_STAIRS, "Quartz Stairs", "quartzstairs"),
    ACTIVATOR_RAIL(BlockID.ACTIVATOR_RAIL, "Activator Rail", "activatorrail", "tntrail", "activatortrack"),
    DROPPER(BlockID.DROPPER, "Dropper", "dropper"),
    STAINED_CLAY(BlockID.STAINED_CLAY, "Stained Clay", "stainedclay", "stainedhardenedclay"),
    STAINED_GLASS_PANE(BlockID.STAINED_GLASS_PANE, "Stained Glass Pane", "stainedglasspane"),
    LEAVES2(BlockID.LEAVES2, "Leaves", "leaves2", "acacialeaves", "darkoakleaves"),
    LOG2(BlockID.LOG2, "Log", "log2", "acacia", "darkoak"),
    ACACIA_STAIRS(BlockID.ACACIA_STAIRS, "Acacia Wood Stairs", "acaciawoodstairs", "acaciastairs"),
    DARK_OAK_STAIRS(BlockID.DARK_OAK_STAIRS, "Dark Oak Wood Stairs", "darkoakwoodstairs", "darkoakstairs"),
    SLIME(BlockID.SLIME, "SLime", "slimeblock"),
    BARRIER(BlockID.BARRIER, "Barrier", "barrier", "wall", "worldborder", "edge"),
    IRON_TRAP_DOOR(BlockID.IRON_TRAP_DOOR, "Iron Trap Door", "irontrapdoor"),
    PRISMARINE(BlockID.PRISMARINE, "Prismarine", "prismarine"),
    SEA_LANTERN(BlockID.SEA_LANTERN, "Sea Lantern", "sealantern"),
    HAY_BLOCK(BlockID.HAY_BLOCK, "Hay Block", "hayblock", "haybale", "wheatbale"),
    CARPET(BlockID.CARPET, "Carpet", "carpet"),
    HARDENED_CLAY(BlockID.HARDENED_CLAY, "Hardened Clay", "hardenedclay", "hardclay"),
    COAL_BLOCK(BlockID.COAL_BLOCK, "Block of Coal", "coalblock", "blockofcoal"),
    PACKED_ICE(BlockID.PACKED_ICE, "Packed Ice", "packedice", "hardice"),
    DOUBLE_PLANT(BlockID.DOUBLE_PLANT, "Large Flowers", "largeflowers", "doubleflowers"),
    STANDING_BANNER(BlockID.STANDING_BANNER, "Standing Banner", "standingbannear", "banner"),
    WALL_BANNER(BlockID.WALL_BANNER, "Wall Banner", "wallbanner"),
    DAYLIGHT_SENSOR_INVERTED(BlockID.DAYLIGHT_SENSOR_INVERTED, "Inverted Daylight Sensor", "inverteddaylight", "inverteddaylightsensor"),
    RED_SANDSTONE(BlockID.RED_SANDSTONE, "Red Sandstone", "redsandstone"),
    RED_SANDSTONE_STAIRS(BlockID.RED_SANDSTONE_STAIRS, "Red Sandstone Stairs", "redsandstonestairs"),
    DOUBLE_STEP2(BlockID.DOUBLE_STEP2, "Double Step 2", "doublestep2", "doubleslab2", "doublestoneslab2", "doublestonestep2"),
    STEP2(BlockID.STEP2, "Step 2", "step2", "slab2", "stonestep2", "stoneslab2"),
    SPRUCE_FENCE_GATE(BlockID.SPRUCE_FENCE_GATE, "Spruce Fence Gate", "spurcefencegate"),
    BIRCH_FENCE_GATE(BlockID.BIRCH_FENCE_GATE, "Birch Fence Gate", "birchfencegate"),
    JUNGLE_FENCE_GATE(BlockID.JUNGLE_FENCE_GATE, "Jungle Fence Gate", "junglefencegate"),
    DARK_OAK_FENCE_GATE(BlockID.DARK_OAK_FENCE_GATE, "Dark Oak Fence Gate", "darkoakfencegate"),
    ACACIA_FENCE_GATE(BlockID.ACACIA_FENCE_GATE, "Acacia Fence Gate", "acaciafencegate"),
    SPRUCE_FENCE(BlockID.SPRUCE_FENCE, "Spruce Fence", "sprucefence"),
    BIRCH_FENCE(BlockID.BIRCH_FENCE, "Birch Fence", "birchfence"),
    JUNGLE_FENCE(BlockID.JUNGLE_FENCE, "Jungle Fence", "junglefence"),
    DARK_OAK_FENCE(BlockID.DARK_OAK_FENCE, "Dark Oak Fence", "darkoakfence"),
    ACACIA_FENCE(BlockID.ACACIA_FENCE, "Acacia Fence", "acaciafence"),
    SPRUCE_DOOR(BlockID.SPRUCE_DOOR, "Spruce Door", "sprucedoor"),
    BIRCH_DOOR(BlockID.BIRCH_DOOR, "Birch Door", "birchdoor"),
    JUNGLE_DOOR(BlockID.JUNGLE_DOOR, "Jungle Door", "jungledoor"),
    ACACIA_DOOR(BlockID.ACACIA_DOOR, "Acacia Door", "acaciadoor"),
    DARK_OAK_DOOR(BlockID.DARK_OAK_DOOR, "Dark Oak Door", "darkoakdoor");

    /**
     * Stores a map of the IDs for fast access.
     */
    private static final Map<Integer, BlockType> ids = new HashMap<Integer, BlockType>();
    /**
     * Stores a map of the names for fast access.
     */
    private static final Map<String, BlockType> lookup = new HashMap<String, BlockType>();

    private final int id;
    private final String name;
    private final String[] lookupKeys;

    static {
        for (BlockType type : EnumSet.allOf(BlockType.class)) {
            ids.put(type.id, type);
            for (String key : type.lookupKeys) {
                lookup.put(key, type);
            }
        }
    }


    /**
     * Construct the type.
     *
     * @param id the ID of the block
     * @param name the name of the block
     * @param lookupKey a name to reference the block by
     */
    BlockType(int id, String name, String lookupKey) {
        this.id = id;
        this.name = name;
        this.lookupKeys = new String[] { lookupKey };
    }

    /**
     * Construct the type.
     *
     * @param id the ID of the block
     * @param name the name of the block
     * @param lookupKeys an array of keys to reference the block by
     */
    BlockType(int id, String name, String... lookupKeys) {
        this.id = id;
        this.name = name;
        this.lookupKeys = lookupKeys;
    }

    /**
     * Return type from ID. May return null.
     *
     * @param id the type ID
     * @return a block type, otherwise null
     */
    @Nullable
    public static BlockType fromID(int id) {
        return ids.get(id);
    }

    /**
     * Return type from name. May return null.
     *
     * @param name the name to search
     * @return a block type or null
     */
    @Nullable
    public static BlockType lookup(String name) {
    	return lookup.get(name.toLowerCase().replaceAll(" ", ""));
    }

    /**
     * Get block numeric ID.
     *
     * @return the block ID
     */
    public int getID() {
        return id;
    }

    /**
     * Get user-friendly block name.
     *
     * @return the block name
     */
    public String getName() {
        return name;
    }
}
