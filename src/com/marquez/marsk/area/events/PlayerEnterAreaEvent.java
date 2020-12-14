package com.marquez.marsk.area.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerEnterAreaEvent extends PlayerAreaEvent{
	
	public static HandlerList handlers;
	
	static {
		handlers = new HandlerList();
	}

	public PlayerEnterAreaEvent(Player who, String areaName) {
		super(who, areaName);
	}

	@Override
	public HandlerList getHandlers() {
		return PlayerEnterAreaEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return PlayerEnterAreaEvent.handlers;
	}
}
