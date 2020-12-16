package com.marquez.marsk.area;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Locations{
	public static HashMap<Player, List<String>> playerArea_a = new HashMap<Player, List<String>>();
	public static HashMap<Player, List<String>> playerArea_b = new HashMap<Player, List<String>>();
	public Location pos1;
	public Location pos2;

	public Locations(Area area) {
		Location pos1 = area.getLocations()[0];
		Location pos2 = area.getLocations()[1];
		int[] x = swap((int) pos1.getX(), (int) pos2.getX());
		int[] y = swap((int) pos1.getY(), (int) pos2.getY());
		int[] z = swap((int) pos1.getZ(), (int) pos2.getZ());
		pos1.setX(x[0]);
		pos2.setX(x[1]);
		pos1.setY(y[0]);
		pos2.setY(y[1]);
		pos1.setZ(z[0]);
		pos2.setZ(z[1]);
		this.pos1 = pos1;
		this.pos2 = pos2;
	}

	public int[] swap(int i1, int i2) {
		int[] a = new int[] { i1, i2 };
		if (i1 >= i2) {
			a = new int[] { i2, i1 };
		}
		return a;
	}

	public static boolean isInPosition(Locations ls, Location loc) {
		if (ls.pos1.getWorld().getName().equals(loc.getWorld().getName())) {
			if (ls.pos1.getBlockX() <= loc.getBlockX() && ls.pos2.getBlockX() >= loc.getBlockX()) {
				if (ls.pos1.getBlockY() <= loc.getBlockY() && ls.pos2.getBlockY() >= loc.getBlockY()) {
					if (ls.pos1.getBlockZ() <= loc.getBlockZ() && ls.pos2.getBlockZ() >= loc.getBlockZ()) {
						return true;
					}
				}
			}
		}	
		return false;
	}
}
