package com.gaiakeeper.minecraft;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity types.
 */
public enum MobType {
	CREEPER(MobID.CREEPER, "Creeper", "creeper"),
	SKELETON(MobID.SKELETON, "Skeleton", "skeleton"),
	SPIDER(MobID.SPIDER, "Spider", "spider"),
	GIANT(MobID.GIANT, "Giant", "giant"),
	ZOMBIE(MobID.ZOMBIE, "Zombie", "zombie"),
	SLIME(MobID.SLIME, "Slime", "slime"),
	GHAST(MobID.GHAST, "Ghast", "ghast"),
	ZOMBIE_PIGMAN(MobID.ZOMBIE_PIGMAN, "Zombie pigman", "zombiepigman", "pigzombie"),
	ENDERMAN(MobID.ENDERMAN, "Enderman", "enderman"),
	CAVE_SPIDER(MobID.CAVE_SPIDER, "Cave spider", "cavespider"),
	SLIVERFISH(MobID.SILVERFISH, "Silverfish", "silverfish"),
	BLAZE(MobID.BLAZE, "Blaze", "blaze"),
	MAGMA_CUBE(MobID.MAGMA_CUBE, "Magma cube", "magmacube"),
	ENDER_DRAGON(MobID.ENDER_DRAGON, "Ender dragon", "enderdragon", "dragon"),
	WITHER(MobID.WITHER, "Wither", "wither"),
	BAT(MobID.BAT, "Bat", "bat"),
	WITCH(MobID.WITCH, "Witch", "witch"),
	ENDERMITE(MobID.ENDERMITE, "Endermite", "endermite"),
	GUARDIAN(MobID.GUARDIAN, "Guardian", "guardian"),
	SHULKER(MobID.SHULKER, "Shulker", "shulker"),
	PIG(MobID.PIG, "Pig", "pig"),
	SHEEP(MobID.SHEEP, "Sheep", "sheep"),
	COW(MobID.COW, "Cow", "cow"),
	CHICKEN(MobID.CHICKEN, "Chicken", "chicken"),
	SQUID(MobID.SQUID, "Squid", "squid"),
	WOLF(MobID.WOLF, "Wolf", "wolf"),
	MOOSHROOM(MobID.MOOSHROOM, "Mooshroom", "mooshroom"),
	SNOW_GOLEM(MobID.SNOW_GOLEM, "Snow golem", "snowgolem", "snowman"),
	OCELOT(MobID.OCELOT, "Ocelot", "ocelot"),
	IRON_GOLEM(MobID.IRON_GOLEM, "Iron golem", "irongolem"),
	HORSE(MobID.HORSE, "Horse", "horse"),
	RABBIT(MobID.RABBIT, "rabbit", "Rabbit"),
	POLAR_BEAR(MobID.POLAR_BEAR, "Polar bear", "polarbear"),
	VILLAGER(MobID.VILLAGER, "Villager", "villager");

    /**
     * Stores a map of the IDs for fast access.
     */
    private static final Map<Integer, MobType> ids = new HashMap<Integer, MobType>();
    /**
     * Stores a map of the names for fast access.
     */
    private static final Map<String, MobType> lookup = new HashMap<String, MobType>();

    private final int id;
    private final String name;
    private final String[] lookupKeys;

    static {
        for (MobType type : EnumSet.allOf(MobType.class)) {
            ids.put(type.id, type);
            for (String key : type.lookupKeys) {
                lookup.put(key, type);
            }
        }
    }


    /**
     * Construct the type.
     *
     * @param id the ID of the entity
     * @param name the name of the entity
     * @param lookupKey a name to reference the entity by
     */
    MobType(int id, String name, String lookupKey) {
        this.id = id;
        this.name = name;
        this.lookupKeys = new String[] { lookupKey };
    }

    /**
     * Construct the type.
     *
     * @param id the ID of the entity
     * @param name the name of the entity
     * @param lookupKeys an array of keys to reference the entity by
     */
    MobType(int id, String name, String... lookupKeys) {
        this.id = id;
        this.name = name;
        this.lookupKeys = lookupKeys;
    }

    /**
     * Return type from ID. May return null.
     *
     * @param id the type ID
     * @return a entity type, otherwise null
     */
    @Nullable
    public static MobType fromID(int id) {
        return ids.get(id);
    }

    /**
     * Return type from name. May return null.
     *
     * @param name the name to search
     * @return a entity type or null
     */
    @Nullable
    public static MobType lookup(String name) {
    	return lookup.get(name.toLowerCase().replaceAll(" ", ""));
    }

    /**
     * Get entity numeric ID.
     *
     * @return the entity ID
     */
    public int getID() {
        return id;
    }

    /**
     * Get user-friendly entity name.
     *
     * @return the entity name
     */
    public String getName() {
        return name;
    }
}
