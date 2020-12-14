package com.marquez.marsk.conditions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.Locations;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsInArea extends Condition{

	private Expression<Location> location;
	private Expression<String> area;
	
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.location = (Expression<Location>)arg0[0];
		this.area = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "something is in area";
	}

	public boolean check(Event arg0) {
		final String area = this.area.getSingle(arg0);
		Location location = this.location.getSingle(arg0);
    	if(AreaManager.findArea((area)) == -1) {
			return false;
		}
    	if(location == null) return false;
		return Locations.isInPosition(new Locations(AreaManager.foundArea(area)), location);
	}
}
