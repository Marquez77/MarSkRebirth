package com.marquez.marsk.effects;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class EffSpawnStaticItem extends Effect{
	private Expression<ItemStack> item;
	private Expression<Location> location;

	@SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.item = (Expression<ItemStack>)arg0[0];
		this.location = (Expression<Location>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "spawn static item";
	}

	protected void execute(final Event arg0) {
		final ItemStack item = (ItemStack)this.item.getSingle(arg0);
		Location loc = (Location)this.location.getSingle(arg0);
		loc = new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY() + 0.9, loc.getBlockZ() + 0.5);
		Item i = loc.getWorld().dropItem(loc, item);
		i.setVelocity(new Vector(0, 0, 0));
	}
}