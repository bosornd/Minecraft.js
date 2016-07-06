package com.gaiakeeper.minecraft;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enchant types.
 */
public enum EnchantType {
	PROTECTION(EnchantID.PROTECTION, "Protection", "protection"),
	FIRE_PROTECTION(EnchantID.FIRE_PROTECTION, "Fire protection", "fireprotection"),
	FEATHER_FALLING(EnchantID.FEATHER_FALLING, "Feather falling", "featherfalling"),
	BLAST_PROTECTION(EnchantID.BLAST_PROTECTION, "Blast protection", "blastprotection"),
	PROJECTILE_PROTECTION(EnchantID.PROJECTILE_PROTECTION, "Projectile protection", "projectileprotection"),
	RESPIRATION(EnchantID.RESPIRATION, "Respiration", "respiration"),
	AQUA_AFFINITY(EnchantID.AQUA_AFFINITY, "Aqua affinity", "aquaaffinity"),
	THORNS(EnchantID.THORNS, "Thorns", "thorns"),
	DEPTH_STRIDER(EnchantID.DEPTH_STRIDER, "Depth strider", "depthstrider"),
	FROST_WALKER(EnchantID.FROST_WALKER, "Frost walker", "frostwalker"),
	SHARPNESS(EnchantID.SHARPNESS, "Sharpness", "sharpness"),
	SMITE(EnchantID.SMITE, "Smite", "smite"),
	BANE_OF_ARTHROPODS(EnchantID.BANE_OF_ARTHROPODS, "Bane of arthropods", "baneofarthropods"),
	KNOCKBACK(EnchantID.KNOCKBACK, "Knockback", "knockback"),
	FIRE_ASPECT(EnchantID.FIRE_ASPECT, "Fire aspect", "fireaspect"),
	LOOTING(EnchantID.LOOTING, "Looting", "looting"),
	EFFICIENCY(EnchantID.EFFICIENCY, "Efficiency", "efficiency"),
	SILK_TOUCH(EnchantID.SILK_TOUCH, "Silk touch", "silktouch"),
	UNBREAKING(EnchantID.UNBREAKING, "Unbreaking", "unbreaking"),
	FORTUNE(EnchantID.FORTUNE, "Fortune", "fortune"),
	POWER(EnchantID.POWER, "Power", "power"),
	PUNCH(EnchantID.PUNCH, "Punch", "punch"),
	FLAME(EnchantID.FLAME, "Flame", "flame"),
	INFINITY(EnchantID.INFINITY, "Infinity", "infinity"),
	LUCK_OF_THE_SEA(EnchantID.LUCK_OF_THE_SEA, "Luck of the sea", "luckofthesea");

    /**
     * Stores a map of the IDs for fast access.
     */
    private static final Map<Integer, EnchantType> ids = new HashMap<Integer, EnchantType>();
    /**
     * Stores a map of the names for fast access.
     */
    private static final Map<String, EnchantType> lookup = new HashMap<String, EnchantType>();

    private final int id;
    private final String name;
    private final String[] lookupKeys;

    static {
        for (EnchantType type : EnumSet.allOf(EnchantType.class)) {
            ids.put(type.id, type);
            for (String key : type.lookupKeys) {
                lookup.put(key, type);
            }
        }
    }


    /**
     * Construct the type.
     *
     * @param id the ID of the enchant
     * @param name the name of the enchant
     * @param lookupKey a name to reference the enchant by
     */
    EnchantType(int id, String name, String lookupKey) {
        this.id = id;
        this.name = name;
        this.lookupKeys = new String[] { lookupKey };
    }

    /**
     * Construct the type.
     *
     * @param id the ID of the enchant
     * @param name the name of the enchant
     * @param lookupKeys an array of keys to reference the enchant by
     */
    EnchantType(int id, String name, String... lookupKeys) {
        this.id = id;
        this.name = name;
        this.lookupKeys = lookupKeys;
    }

    /**
     * Return type from ID. May return null.
     *
     * @param id the type ID
     * @return a enchant type, otherwise null
     */
    @Nullable
    public static EnchantType fromID(int id) {
        return ids.get(id);
    }

    /**
     * Return type from name. May return null.
     *
     * @param name the name to search
     * @return a enchant type or null
     */
    @Nullable
    public static EnchantType lookup(String name) {
    	return lookup.get(name.toLowerCase().replaceAll(" ", ""));
    }

    /**
     * Get enchant numeric ID.
     *
     * @return the enchant ID
     */
    public int getID() {
        return id;
    }

    /**
     * Get user-friendly enchant name.
     *
     * @return the enchant name
     */
    public String getName() {
        return name;
    }
}
