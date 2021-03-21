package com.marquez.marsk.jump;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class EvtJump extends SkriptEvent{
	
	private Literal<Boolean> airJump;

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "on jump";
	}

	@Override
	public boolean check(final Event arg0) {
		PlayerJumpEvent e = (PlayerJumpEvent)arg0;
		boolean airJump = false;
		if(this.airJump != null) airJump = this.airJump.getSingle(arg0);
		return e.isAirJump() == true ? airJump : true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		this.airJump = (Literal<Boolean>)arg0[0];
		return true;
	}
}
