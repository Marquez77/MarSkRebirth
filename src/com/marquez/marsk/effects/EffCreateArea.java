package com.marquez.marsk.effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.marquez.marsk.area.AreaManager;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffCreateArea extends Effect{
	private Expression<String> name;
	private Expression<Location> pos1;
	private Expression<Location> pos2;
    
    @SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
    	this.name = (Expression<String>)arg0[0];
        this.pos1 = (Expression<Location>)arg0[1];
        this.pos2 = (Expression<Location>)arg0[2];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "create area";
    }
    
    protected void execute(final Event arg0) {
    	final String name = (String)this.name.getSingle(arg0);
    	final Location pos1 = (Location)this.pos1.getSingle(arg0);
    	final Location pos2 = (Location)this.pos2.getSingle(arg0);
    	AreaManager.createArea(name, new Location[] { pos1, pos2 });
    }
}