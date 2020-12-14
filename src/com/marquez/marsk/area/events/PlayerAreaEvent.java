package com.marquez.marsk.area.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerAreaEvent extends PlayerEvent{
	public static HandlerList handlers;
	private String areaName;
	
	static {
		handlers = new HandlerList();
	}

	public PlayerAreaEvent(Player who, String areaName) {
		super(who);
		this.areaName = areaName;
	}

	@Override
	public HandlerList getHandlers() {
		return PlayerAreaEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return PlayerAreaEvent.handlers;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName; 
	}
}
