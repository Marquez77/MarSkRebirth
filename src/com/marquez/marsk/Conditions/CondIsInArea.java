package com.marquez.marsk.Conditions;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.Locations;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsInArea extends Condition{

	private Expression<?> object;
	private Expression<String> area;
	
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.object = arg0[0];
		this.area = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return this.object.getSingle(arg0).toString() + " is in area " + this.area.getSingle(arg0);
	}

	public boolean check(Event arg0) {
		final String area = this.area.getSingle(arg0);
		final Object obj = this.object.getSingle(arg0);
    	if(AreaManager.findArea((area)) == -1) {
			return false;
		}
    	Location loc = null;
    	if(obj instanceof Entity) {
    		loc = ((Entity)obj).getLocation();
    	}else if(obj instanceof Location) {
    		loc = (Location)obj;
    	}
    	if(loc == null) return false;
		return Locations.isInPosition(new Locations(AreaManager.foundArea(area)), loc);
	}
}
