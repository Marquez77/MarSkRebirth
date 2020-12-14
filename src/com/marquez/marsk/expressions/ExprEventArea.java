package com.marquez.marsk.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.marsk.area.events.PlayerAreaEvent;
import com.marquez.marsk.area.events.PlayerEnterAreaEvent;
import com.marquez.marsk.area.events.PlayerLeaveAreaEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprEventArea extends SimpleExpression<String>{
	
    public Class<? extends String> getReturnType() {
        return String.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		if (!(ScriptLoader.isCurrentEvent(PlayerEnterAreaEvent.class) || ScriptLoader.isCurrentEvent(PlayerLeaveAreaEvent.class))) {
            return false;
        }
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "event-area";
    }
    
	@Nullable
    protected String[] get(final Event arg0) {
		PlayerAreaEvent e = (PlayerAreaEvent)arg0;
		return new String[] {e.getAreaName()};
    }
	
}