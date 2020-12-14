package com.marquez.marsk.Events;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;

import com.marquez.marsk.AreaManager;
import com.marquez.marsk.Locations;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;

public class EvtEnterArea extends SkriptEvent{
	
	private Expression<String> area;

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "on enter area at " + this.area.getSingle(arg0);
	}

	@Override
	public boolean check(final Event arg0) {
		String area = (String)this.area.getSingle(arg0);
		if(AreaManager.findArea(area) == -1) {
			return false;
		}
		return this.area.check(arg0, new Checker<String>() {
			@Override
			public boolean check(String area) {
				return LocContains(area, (((PlayerMoveEvent)arg0).getPlayer()));
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		this.area = (Expression<String>)arg0[0];
		return true;
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
}