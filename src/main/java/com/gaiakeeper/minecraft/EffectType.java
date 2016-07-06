package com.gaiakeeper.minecraft;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Effect types.
 */
public enum EffectType {
	SPEED(EffectID.SPEED, "speed", "speed"),
	SLOWNESS(EffectID.SLOWNESS, "slowness", "slowness"),
	HASTE(EffectID.HASTE, "haste", "haste"),
	MINING_FATIGUE(EffectID.MINING_FATIGUE, "mining fatigue", "miningfatigue"),
	STRENGTH(EffectID.STRENGTH, "strength", "strength"),
	INSTANT_HEALTH(EffectID.INSTANT_HEALTH, "instant health", "instanthealth"),
	INSTANT_DAMAGE(EffectID.INSTANT_DAMAGE, "instant damage", "instantdamage"),
	JUMP_BOOST(EffectID.JUMP_BOOST, "jump boost", "jumpboost"),
	NAUSEA(EffectID.NAUSEA, "nausea", "nausea"),
	REGENERATION(EffectID.REGENERATION, "regeneration", "regeneration"),
	RESISTANCE(EffectID.RESISTANCE, "resistance", "resistance"),
	FIRE_RESISTANCE(EffectID.FIRE_RESISTANCE, "fire resistance", "fireresistance"),
	WATER_BREATHING(EffectID.WATER_BREATHING, "water breathing", "waterbreathing"),
	INVISIBILITY(EffectID.INVISIBILITY, "invisibility", "invisibility"),
	BLINDNESS(EffectID.BLINDNESS, "blindness", "blindness"),
	NIGHT_VISION(EffectID.NIGHT_VISION, "night vision", "nightvision"),
	HUNGER(EffectID.HUNGER, "hunger", "hunger"),
	WEAKNESS(EffectID.WEAKNESS, "weakness", "weakness"),
	POISON(EffectID.POISON, "poison", "poison"),
	WITHER(EffectID.WITHER, "wither", "wither"),
	HEALTH_BOOST(EffectID.HEALTH_BOOST, "health boost", "healthboost"),
	ABSORPTION(EffectID.ABSORPTION, "absorption", "absorption"),
	SATURATION(EffectID.SATURATION, "saturation", "saturation"),
	GLOWING(EffectID.GLOWING, "glowing", "glowing"),
	LEVITATION(EffectID.LEVITATION, "levitation", "levitation"),
	LUCK(EffectID.LUCK, "luck", "luck");

    /**
     * Stores a map of the IDs for fast access.
     */
    private static final Map<Integer, EffectType> ids = new HashMap<Integer, EffectType>();
    /**
     * Stores a map of the names for fast access.
     */
    private static final Map<String, EffectType> lookup = new HashMap<String, EffectType>();

    private final int id;
    private final String name;
    private final String[] lookupKeys;

    static {
        for (EffectType type : EnumSet.allOf(EffectType.class)) {
            ids.put(type.id, type);
            for (String key : type.lookupKeys) {
                lookup.put(key, type);
            }
        }
    }


    /**
     * Construct the type.
     *
     * @param id the ID of the effect
     * @param name the name of the effect
     * @param lookupKey a name to reference the effect by
     */
    EffectType(int id, String name, String lookupKey) {
        this.id = id;
        this.name = name;
        this.lookupKeys = new String[] { lookupKey };
    }

    /**
     * Construct the type.
     *
     * @param id the ID of the effect
     * @param name the name of the effect
     * @param lookupKeys an array of keys to reference the effect by
     */
    EffectType(int id, String name, String... lookupKeys) {
        this.id = id;
        this.name = name;
        this.lookupKeys = lookupKeys;
    }

    /**
     * Return type from ID. May return null.
     *
     * @param id the type ID
     * @return a effect type, otherwise null
     */
    @Nullable
    public static EffectType fromID(int id) {
        return ids.get(id);
    }

    /**
     * Return type from name. May return null.
     *
     * @param name the name to search
     * @return a effect type or null
     */
    @Nullable
    public static EffectType lookup(String name) {
    	return lookup.get(name.toLowerCase().replaceAll(" ", ""));
    }

    /**
     * Get effect numeric ID.
     *
     * @return the effect ID
     */
    public int getID() {
        return id;
    }

    /**
     * Get user-friendly effect name.
     *
     * @return the effect name
     */
    public String getName() {
        return name;
    }
}
