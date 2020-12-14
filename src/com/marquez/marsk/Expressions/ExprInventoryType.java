package com.marquez.marsk.Expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.lang.*;
import org.bukkit.event.inventory.*;

import ch.njol.skript.*;
import org.bukkit.event.*;

import javax.annotation.*;

public class ExprInventoryType extends SimpleExpression<String>{
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		if (!ScriptLoader.isCurrentEvent((Class)InventoryClickEvent.class)) {
			Skript.error("\'Inventory Type\' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
			return false;
		}
		return true;
	}

	public String toString(@Nullable final Event arg0, final boolean arg1) {
		return "slot type";
	}

	@Nullable
	protected String[] get(final Event arg0) {
		final InventoryClickEvent e = (InventoryClickEvent)arg0;
		String inventorytype = "";
		if(e.getInventory().getType().equals(InventoryType.ANVIL))
			inventorytype = "Anvil";
		else if(e.getInventory().getType().equals(InventoryType.BEACON))
			inventorytype = "Beacon";
		else if(e.getInventory().getType().equals(InventoryType.BREWING))
			inventorytype = "Brewing";
		else if(e.getInventory().getType().equals(InventoryType.CHEST))
			inventorytype = "Chest";
		else if(e.getInventory().getType().equals(InventoryType.CRAFTING))
			inventorytype = "Crafting";
		else if(e.getInventory().getType().equals(InventoryType.CREATIVE))
			inventorytype = "Creative";
		else if(e.getInventory().getType().equals(InventoryType.DISPENSER))
			inventorytype = "Dispenser";
		else if(e.getInventory().getType().equals(InventoryType.DROPPER))
			inventorytype = "Dropper";
		else if(e.getInventory().getType().equals(InventoryType.ENCHANTING))
			inventorytype = "Enchanting";
		else if(e.getInventory().getType().equals(InventoryType.ENDER_CHEST))
			inventorytype = "EnderChest";
		else if(e.getInventory().getType().equals(InventoryType.FURNACE))
			inventorytype = "Furnace";
		else if(e.getInventory().getType().equals(InventoryType.HOPPER))
			inventorytype = "Hopper";
		else if(e.getInventory().getType().equals(InventoryType.MERCHANT))
			inventorytype = "Merchant";
		else if(e.getInventory().getType().equals(InventoryType.PLAYER))
			inventorytype = "Player";
		else if(e.getInventory().getType().equals(InventoryType.WORKBENCH))
			inventorytype = "Workbench";
		return new String[] { inventorytype };
	}
}