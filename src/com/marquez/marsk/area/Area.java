package com.marquez.marsk.area;

import java.util.List;

import org.bukkit.Location;

public class Area {
	private String name;
	private List<Location> loc;
	
	public Area(String name, List<Location> loc) {
		this.name = name;
		this.loc = loc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Location> getLoc() {
		return loc;
	}

	public void setLoc(List<Location> loc) {
		this.loc = loc;
	}
}
