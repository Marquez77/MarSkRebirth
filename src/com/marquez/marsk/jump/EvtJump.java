package com.marquez.marsk.jump;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class EvtJump extends SkriptEvent{

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "on jump";
	}

	@Override
	public boolean check(final Event arg0) {
		return true;
	}

	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		return true;
	}
}
