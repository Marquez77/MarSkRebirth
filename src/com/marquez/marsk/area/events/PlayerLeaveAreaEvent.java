package com.marquez.marsk.area.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerLeaveAreaEvent extends PlayerAreaEvent{
	
	public static HandlerList handlers;
	
	static {
		handlers = new HandlerList();
	}

	public PlayerLeaveAreaEvent(Player who, String areaName) {
		super(who, areaName);
	}

	@Override
	public HandlerList getHandlers() {
		return PlayerLeaveAreaEvent.handlers;
	}
	
	public static HandlerList getHandlerList() {
		return PlayerLeaveAreaEvent.handlers;
	}
}
