package com.marquez.marsk.Expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.lang.*;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.*;
import org.bukkit.inventory.ItemStack;

import javax.annotation.*;

public class ExprEnchantLevel extends SimpleExpression<Integer>{

	private Expression<Enchantment> enchant;
	private Expression<ItemStack> item;

	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.enchant = (Expression<Enchantment>)arg0[0];
		this.item = (Expression<ItemStack>)arg0[1];
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "enchant level";
	}

	@Nullable
	protected Integer[] get(final Event arg0) {
		final ItemStack is = item.getSingle(arg0);
		final Enchantment e = enchant.getSingle(arg0);
		final int num = is.getItemMeta().getEnchantLevel(e);
		return new Integer[] { num };
	}
}