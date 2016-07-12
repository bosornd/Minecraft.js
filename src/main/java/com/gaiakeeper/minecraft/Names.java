package com.gaiakeeper.minecraft;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public final class Names {
	protected static final Names defaultNames = new Names();
	public static Names getDefaultNames(){ return defaultNames; };
	
    protected Map<String, String> table = new HashMap<String, String>();
    
    protected static String key(String name){
    	return name.toLowerCase().replaceAll(" ", "").replaceAll("_", "");
    }

    public void add(String name, String value){
    	String key = key(name);
    	if ( table.get(key) == null )
    		table.put(key,  value);
    }
    
    public void add(String... names){
    	String value = names[names.length - 1];		// value = last parameter
    	for (int i = 0; i < names.length - 1; i ++)
    		add(names[i], value);
    }
    
    public String remove(String name){
    	return table.remove(key(name));
    }
    
    @Nullable
    public String lookup(String name) {
    	return table.get(key(name));
    }
};
