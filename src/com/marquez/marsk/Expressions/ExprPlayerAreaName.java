package com.marquez.marsk.Expressions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.marquez.marsk.area.Area;
import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.Locations;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprPlayerAreaName extends SimpleExpression<String>{
	
	private Expression<?> object;
	
    public Class<? extends String> getReturnType() {
        return String.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
    	object = arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "area of " + object.getSingle(arg0).toString();
    }
    
	@Nullable
    protected String[] get(final Event arg0) {
    	Object obj = object.getSingle(arg0);
    	Location loc = null;
    	if(obj instanceof Entity) {
    		loc = ((Entity)obj).getLocation();
    	}else if(obj instanceof Location) {
    		loc = (Location)obj;
    	}
    	if(loc == null) return null;
    	List<String> array = new ArrayList<String>();
    	for(Area area : AreaManager.getAreas()) {
    		if(Locations.isInPosition(new Locations(area), loc)) {
    			array.add(area.getName());
    		}
    	}
    	return array.isEmpty() ? null : array.toArray(new String[array.size()]);
    }
	
}