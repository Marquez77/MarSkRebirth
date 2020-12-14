package com.marquez.marsk.Expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.lang.*;

import org.bukkit.event.*;

import java.text.DecimalFormat;
import javax.annotation.*;

public class ExprDecimal extends SimpleExpression<Number>{

	private Expression<Integer> line;
	private Expression<Number> number;

	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.line = (Expression<Integer>)arg0[0];
		this.number = (Expression<Number>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "decimal";
	}

	@Nullable
	protected Number[] get(final Event arg0) {
		final Integer line = this.line.getSingle(arg0);
		final Number number = this.number.getSingle(arg0);
		String lineStr = "";
		for(int i = 0; i < line; i++) {
			lineStr = lineStr + "#";
		}
		DecimalFormat format = new DecimalFormat("." + lineStr);
		String str = format.format(number);
		Number num = Float.valueOf(str);
		return new Number[] { num };
	}
}