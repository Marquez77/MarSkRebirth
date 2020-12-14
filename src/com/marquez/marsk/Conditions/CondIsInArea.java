package com.marquez.marsk.Conditions;

import javax.annotation.*;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.marquez.marsk.AreaFile;
import com.marquez.marsk.Locations;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsInArea extends Condition{

	private Expression<Player> player;
	private Expression<String> area;
	
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		String area = ((Expression<String>)arg0[1]).getSingle(null);
		if(AreaFile.findArea((area)) == -1) {
			Skript.error("\'" + area + "\' 은(는) 존재하지 않는 이름입니다.");
			return false;
		}
		this.player = (Expression<Player>)arg0[0];
		this.area = (Expression<String>)arg0[1];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return this.player.getSingle(arg0).getName() + " is in area " + this.area;
	}

	public boolean check(Event arg0) {
		final String area = this.area.getSingle(arg0);
		final Player player = this.player.getSingle(arg0);
    	if(AreaFile.findArea((area)) == -1) {
			return false;
		}
		if(Locations.isInPosition(new Locations(AreaFile.foundArea(area)), player.getLocation())) {
			return true;
    	}
		return false;
	}
}
