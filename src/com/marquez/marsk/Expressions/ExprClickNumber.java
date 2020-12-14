package com.marquez.marsk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprClickNumber extends SimpleExpression<Integer>{
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "click number";
    }
    
    @Nullable
    protected Integer[] get(final Event arg0) {
    	final InventoryClickEvent e = (InventoryClickEvent)arg0;
        return new Integer[] { e.getHotbarButton() };
    }
}