package com.marquez.marsk.expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;

import org.bukkit.event.*;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import javax.annotation.*;

public class ExprHealthRegenCause extends SimpleExpression<String>{

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)EntityRegainHealthEvent.class)) {
			Skript.error("\'Regen Cause\' �� on entity health regen �̺�Ʈ������ ����� �����մϴ�.");
			return false;
		}
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "regen cause";
	}

	@Nullable
	protected String[] get(final Event arg0) {
		return new String[] { ((EntityRegainHealthEvent)arg0).getRegainReason().toString() };
	}
}