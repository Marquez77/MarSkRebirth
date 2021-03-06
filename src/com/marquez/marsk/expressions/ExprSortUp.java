package com.marquez.marsk.expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.lang.*;

import org.bukkit.event.*;

import java.util.Arrays;
import javax.annotation.*;

public class ExprSortUp extends SimpleExpression<Number>{
	
	private Expression<Number> numbers;
	
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    @SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
    	this.numbers = (Expression<Number>)arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "sort up";
    }
    
	@Nullable
    protected Number[] get(final Event arg0) {
    	Number[] array = this.numbers.getAll(arg0);
    	Arrays.sort(array);
    	return array;
    }
}