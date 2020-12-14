package com.marquez.marsk.Expressions;

import ch.njol.skript.lang.util.*;
import ch.njol.util.*;
import ch.njol.skript.lang.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;

import ch.njol.skript.*;
import org.bukkit.event.*;

import javax.annotation.*;

public class ExprSlotType extends SimpleExpression<String>{
    public Class<? extends String> getReturnType() {
        return String.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
        if (!ScriptLoader.isCurrentEvent((Class)InventoryClickEvent.class)) {
            Skript.error("\'Slot Type\' 은 on inventory click 이벤트에서만 사용이 가능합니다.");
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
    	String slottype = "";
    	if(e.getSlotType().equals(SlotType.ARMOR))
    		slottype = "Armour";
    	else if(e.getSlotType().equals(SlotType.CONTAINER))
    		slottype = "Container";
    	else if(e.getSlotType().equals(SlotType.CRAFTING))
    		slottype = "Crafting";
    	else if(e.getSlotType().equals(SlotType.FUEL))
    		slottype = "Fuel";
    	else if(e.getSlotType().equals(SlotType.OUTSIDE))
    		slottype = "Outside";
    	else if(e.getSlotType().equals(SlotType.QUICKBAR))
    		slottype = "Quickbar";
    	else if(e.getSlotType().equals(SlotType.RESULT))
    		slottype = "Result ";
        return new String[] { slottype };
    }
}