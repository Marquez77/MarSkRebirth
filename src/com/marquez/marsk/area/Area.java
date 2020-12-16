package com.marquez.marsk.area;

import org.bukkit.Location;

public class Area {
	private String name;
	private Location[] locations;
	
	public Area(String name, Location[] locations) {
		this.name = name;
		this.locations = locations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location[] getLocations() {
		return locations;
	}

	public void setLocations(Location[] locations) {
		this.locations = locations;
	}
}
