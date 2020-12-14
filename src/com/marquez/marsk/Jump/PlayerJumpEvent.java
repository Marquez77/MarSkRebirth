package com.marquez.marsk.Jump;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerJumpEvent extends Event implements Cancellable {
    private static final HandlerList handlers;
    private boolean isCancelled;
    private Player player;
    
    static {
        handlers = new HandlerList();
    }
    
    public PlayerJumpEvent(final boolean b, final Player player) {
        this.isCancelled = false;
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    public void setCancelled(final boolean e) {
        this.isCancelled = e;
    }
    
    public HandlerList getHandlers() {
        return PlayerJumpEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerJumpEvent.handlers;
    }
}
