package com.marquez.marsk.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.marquez.marsk.area.events.PlayerLeaveAreaEvent;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class EvtLeaveArea extends SkriptEvent{

	private Expression<String> area;

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		if(area == null) {
			return "on leave area";
		}
		return "on leave area at " + this.area.getSingle(arg0);
	}

	@Override
	public boolean check(final Event arg0) {
		PlayerLeaveAreaEvent e = (PlayerLeaveAreaEvent)arg0;
		if(this.area == null) {
			return true;
		}else {
			String area = (String)this.area.getSingle(arg0);
			return e.getAreaName().equals(area);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		this.area = (Expression<String>)arg0[0];
		return true;
	}
	
}