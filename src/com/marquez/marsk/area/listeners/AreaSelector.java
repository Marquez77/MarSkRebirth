package com.marquez.marsk.area.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.cmds.MCommand;

public class AreaSelector implements Listener{
	
	private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();
	
	@EventHandler
	public void select(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(MCommand.hash.containsKey(p)) {
			if(p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				e.setCancelled(true);
				if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					Location[] locations = MCommand.hash.get(p);
					locations[0] = e.getClickedBlock().getLocation();
					MCommand.hash.put(p, locations);
					p.sendMessage(MCommand.prefix + "§e위치 1: " + AreaManager.locationToString(e.getClickedBlock().getLocation()));
				}else if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					if(cooldown.containsKey(p)) {
						if(cooldown.get(p) > System.currentTimeMillis()) return;
					}
					cooldown.put(p, System.currentTimeMillis()+50);
					Location[] locations = MCommand.hash.get(p);
					locations[1] = e.getClickedBlock().getLocation();
					MCommand.hash.put(p, locations);
					p.sendMessage(MCommand.prefix + "§e위치 2: " + AreaManager.locationToString(e.getClickedBlock().getLocation()));
				}
			}
		}
	}
	
}
