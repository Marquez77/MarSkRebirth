package com.marquez.marsk.area;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.marquez.marsk.MarSk;

public class AreaManager {
	
	private static List<Area> areaArray = new ArrayList<Area>();
	
	public static List<Area> getAreas() {
		return new ArrayList<Area>(areaArray);
	}
	
	public static List<String> getAreaNames() {
		List<String> array = new ArrayList<String>();
		for(Area area : areaArray) {
			array.add(area.getName());
		}
		return array;
	}

	public static boolean createArea(String name, List<Location> location) {
		if(location.get(0).getWorld().equals(location.get(1).getWorld())
				&& findArea(name) == -1) {
			areaArray.add(new Area(name, location));
			return true;
		}
		return false;
	}
	
	public static boolean deleteArea(String name) {
		int num = findArea(name);
		if(num != -1) {
			areaArray.remove(num);
			return true;
		}
		return false;
	}
	
	public static int findArea(String name) {
		for(int i = 0; i< areaArray.size(); i++) {
			if(areaArray.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	public static Area foundArea(String name) {
		for(Area a : areaArray) {
			if(a.getName().equals(name)) {
				return a;
			}
		}
		return null;
	}
	
	public static void saveArea() {
		File filename = new File(MarSk.instance.getDataFolder(), "area.sk");
		try{
			if(filename.exists()) {
				filename.createNewFile();
			}
			BufferedWriter w = new BufferedWriter(new FileWriter(filename));
			StringBuilder sb = new StringBuilder();
			for(Area a : areaArray) {
				sb.append(areaToText(a.getName(), a.getLoc()));
			}
			w.append(sb);
			w.flush();
			w.close();
		}catch(IOException e) {}
	}
	
	public static void loadArea() {
		areaArray.clear();
		File filename = new File(MarSk.instance.getDataFolder(), "area.sk");
		if(filename.exists()) {
			try {
				BufferedReader r = new BufferedReader(new FileReader(filename));
				String s = "";
				List<String> list = new ArrayList<String>();
				while((s = r.readLine()) != null) {
					list.add(s.replace("\t", ""));
				}
				if(list.size() >= 3) {
					for(int i = 0; i < list.size()/3; i++) {
						String name = list.get(i*3).replace(":", "");
						World w = Bukkit.getWorld(list.get(i*3+1).replace("world: ", ""));
						String[] split = list.get(i*3+2).replace("location: ", "").split(" ~ ");
						List<Location> location = new ArrayList<Location>();
						location.add(stringToLocation(w, split[0]));
						location.add(stringToLocation(w, split[1]));
						areaArray.add(new Area(name, location));
					}
				}
				r.close();
			} catch (IOException e) {}
		}
	}
	
	public static String areaToText(String name, List<Location> location) {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(":\r\n");
		sb.append("\tworld: ").append(location.get(0).getWorld().getName()).append("\r\n");
		sb.append("\tlocation: ").append(locationToString(location.get(0))).append(" ~ ").append(locationToString(location.get(1))).append("\r\n");
		return sb.toString();
	}
	
	public static String locationToString(Location location) {
		StringBuilder sb = new StringBuilder();
		sb.append(location.getBlockX()).append(",");
		sb.append(location.getBlockY()).append(",");
		sb.append(location.getBlockZ());
		return sb.toString();
	}
	
	public static Location stringToLocation(World w, String s) {
		String[] split = s.split(",");
		Location location = new Location(w, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
		return location;
	}
}
