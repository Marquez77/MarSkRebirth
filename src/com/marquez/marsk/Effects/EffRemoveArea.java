package com.marquez.marsk.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.marsk.AreaFile;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffRemoveArea extends Effect{
	private Expression<String> name;
    
    @SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
    	this.name = (Expression<String>)arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "remove area";
    }
    
    protected void execute(final Event arg0) {
    	final String name = (String)this.name.getSingle(arg0);
    	AreaFile.deleteArea(name);
    }
}