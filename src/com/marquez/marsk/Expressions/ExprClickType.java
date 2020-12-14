package com.marquez.marsk.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprClickType extends SimpleExpression<String>{
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)InventoryClickEvent.class)) {
			Skript.error("\'Click Type\' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
			return false;
		}
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "click type";
	}

	@Nullable
	protected String[] get(final Event arg0) {
		final InventoryClickEvent e = (InventoryClickEvent)arg0;
		return new String[] { e.getClick().toString() };
	}
}