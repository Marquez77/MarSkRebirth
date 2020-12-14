package com.marquez.marsk.armorequip;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public final class ArmorEquipEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final EquipMethod equipType;
    private final ArmorType type;
    private ItemStack item;
    
    static {
        handlers = new HandlerList();
    }
    
    public ArmorEquipEvent(final Player player, final EquipMethod equipType, final ArmorType type, final ItemStack item) {
        super(player);
        this.cancel = false;
        this.equipType = equipType;
        this.type = type;
        this.item = item;
    }
    
    public static final HandlerList getHandlerList() {
        return ArmorEquipEvent.handlers;
    }
    
    public final HandlerList getHandlers() {
        return ArmorEquipEvent.handlers;
    }
    
    public final void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public final boolean isCancelled() {
        return this.cancel;
    }
    
    public final ArmorType getType() {
        return this.type;
    }
    
    public final ItemStack getItem() {
        return this.item;
    }
    
    public EquipMethod getMethod() {
        return this.equipType;
    }
    
    public enum EquipMethod
    {
        SHIFT_CLICK("SHIFT_CLICK", 0), 
        DRAG("DRAG", 1), 
        HOTBAR("HOTBAR", 2), 
        HOTBAR_SWAP("HOTBAR_SWAP", 3), 
        DISPENSER("DISPENSER", 4), 
        BROKE("BROKE", 5), 
        DEATH("DEATH", 6);
        
        private EquipMethod(final String s, final int n) {
        }
    }
}
