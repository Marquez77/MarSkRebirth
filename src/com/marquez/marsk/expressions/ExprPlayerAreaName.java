package com.marquez.marsk.expressions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.marquez.marsk.area.Area;
import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.Locations;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprPlayerAreaName extends SimpleExpression<String>{

	private Expression<Location> location;

	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.location = (Expression<Location>)arg0[0];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "entered area of something";
	}

	@Nullable
	protected String[] get(final Event arg0) {
		Location location = this.location.getSingle(arg0);
		System.out.println(this.location);
		List<String> array = new ArrayList<String>();
		for(Area area : AreaManager.getAreas()) {
			if(Locations.isInPosition(new Locations(area), location)) {
				array.add(area.getName());
			}
		}
		return array.isEmpty() ? null : array.toArray(new String[array.size()]);
	}

}