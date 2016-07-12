package com.gaiakeeper.minecraft;

import javax.annotation.Nullable;

public class Location implements Comparable<Location> {
	public int x, y, z;
	
	public Location(int _x, int _y, int _z){
		x = _x; y = _y; z = _z;
	};
	
	public Location(Location other){
		x = other.x;
		y = other.y;
		z = other.z;
	}
	
	public Location add(int _x, int _y, int _z){
		return new Location(x + _x, y + _y, z + _z);
	}
	
	public Location add(Location other){
		return new Location(x + other.x, y + other.y, z + other.z);
	}

	public Location sub(int _x, int _y, int _z){
		return new Location(x - _x, y - _y, z - _z);
	}
	
	public Location sub(Location other){
		return new Location(x - other.x, y - other.y, z - other.z);
	}
	
	public Location mid(int _x, int _y, int _z){
		return new Location((x + _x) / 2, (y + _y) / 2, (z + _z) / 2);
	}
	
	public static Location mid(Location a, Location b){
		return new Location((a.x + b.x) / 2, (a.y + b.y) / 2, (a.z + b.z) / 2);
	}

	public static Location min(Location a, Location b){
		return new Location( (a.x < b.x) ? a.x : b.x, (a.y < b.y) ? a.y : b.y, (a.z < b.z) ? a.z : b.z);
	}
	
	public static Location max(Location a, Location b){
		return new Location( (a.x > b.x) ? a.x : b.x, (a.y > b.y) ? a.y : b.y, (a.z > b.z) ? a.z : b.z);
	}
	
    @Override
    public int compareTo(@Nullable Location other) {
        if (other == null) {
            throw new IllegalArgumentException("null not supported");
        }
        if (y != other.y) return Integer.compare(y, other.y);
        if (z != other.z) return Integer.compare(z, other.z);
        if (x != other.x) return Integer.compare(x, other.x);
        
        return 0;
    }
	
    @Override
    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
