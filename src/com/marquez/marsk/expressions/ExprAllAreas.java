package com.marquez.marsk.expressions;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.marsk.area.AreaManager;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprAllAreas extends SimpleExpression<String>{
	
    public Class<? extends String> getReturnType() {
        return String.class;
    }
    
    public boolean isSingle() {
        return false;
    }
    
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "all areas";
    }
    
	@Nullable
    protected String[] get(final Event arg0) {
		List<String> array = AreaManager.getAreaNames();
    	return array.toArray(new String[array.size()]);
    }
}