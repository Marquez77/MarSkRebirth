package com.marquez.marsk.area.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.marquez.marsk.area.Area;
import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.Locations;
import com.marquez.marsk.area.events.PlayerEnterAreaEvent;
import com.marquez.marsk.area.events.PlayerLeaveAreaEvent;

public class PlayerAreaListener implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		for(Area area : AreaManager.getAreas()) {
			if(LocContains(area.getName(), p)) {
				PlayerEnterAreaEvent event = new PlayerEnterAreaEvent(p, area.getName());
				Bukkit.getPluginManager().callEvent(event);
			}else if(LocContainsInverse(area.getName(), p)) {
				PlayerLeaveAreaEvent event = new PlayerLeaveAreaEvent(p, area.getName());
				Bukkit.getPluginManager().callEvent(event);
			}
		}
	}

	public boolean LocContains(String area, Player p) {
		if(Locations.playerArea_a.get(p) == null) {
			Locations.playerArea_a.put(p, new ArrayList<String>());
		}
		if(AreaManager.findArea(area) == -1) {
			return false;
		}
		List<String> arealist = Locations.playerArea_a.get(p);
		if(Locations.isInPosition(new Locations(AreaManager.foundArea(area)), p.getLocation())) {
			if(!Locations.playerArea_a.get(p).contains(area)) {
				arealist.add(area);
				Locations.playerArea_a.put(p, arealist);
				return true;
			}
		}else if(Locations.playerArea_a.get(p).contains(area)) {
			arealist.remove(area);
			Locations.playerArea_a.put(p, arealist);
		}
		return false;
	}
	
	public boolean LocContainsInverse(String area, Player p) {
		if(Locations.playerArea_b.get(p) == null) {
			Locations.playerArea_b.put(p, new ArrayList<String>());
		}
		if(AreaManager.findArea(area) == -1) {
			return false;
		}
		List<String> arealist = Locations.playerArea_b.get(p);
		if(!Locations.isInPosition(new Locations(AreaManager.foundArea(area)), p.getLocation())) {
			if(Locations.playerArea_b.get(p).contains(area)) {
				arealist.remove(area);
				Locations.playerArea_b.put(p, arealist);
				return true;
			}
		}else if(!Locations.playerArea_b.get(p).contains(area)) {
			arealist.add(area);
			Locations.playerArea_b.put(p, arealist);
		}
		return false;
	}
}
