package com.marquez.marsk.Expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.lang.*;

import org.bukkit.event.*;

import javax.annotation.*;

public class ExprCharAt extends SimpleExpression<Character>{

	private Expression<Number> number;
	private Expression<String> text;

	public Class<? extends Character> getReturnType() {
		return Character.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.number = (Expression<Number>)arg0[0];
		this.text = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "char at";
	}

	@Nullable
	protected Character[] get(final Event arg0) {
		final Number number = this.number.getSingle(arg0);
		final String text = this.text.getSingle(arg0);
		Character ch = text.charAt(number.intValue()-1);
		return new Character[] { ch };
	}
}