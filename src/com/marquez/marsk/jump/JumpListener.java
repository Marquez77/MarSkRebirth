package com.marquez.marsk.jump;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.marquez.marsk.MarSk;

public class JumpListener implements Listener
{
    MarSk main;
    public HashMap<Player, Long> cooldown;
    
    public JumpListener(final MarSk core) {
        this.cooldown = new HashMap<Player, Long>();
        core.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)core);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onJump(final PlayerMoveEvent event) {
        if (!event.getPlayer().isFlying() && event.getPlayer().getGameMode() != GameMode.CREATIVE && event.getFrom().getY() + 0.5 != event.getTo().getY() && event.getFrom().getY() + 0.419 < event.getTo().getY()) {
            final Location loc = event.getFrom();
            loc.setY(event.getFrom().getY() - 1.0);
            if (loc.getBlock().getType() != Material.AIR) {
                if (!this.cooldown.containsKey(event.getPlayer())) {
                    this.cooldown.put(event.getPlayer(), System.currentTimeMillis() + 350L);
                    final PlayerJumpEvent evt = new PlayerJumpEvent(event.isCancelled(), event.getPlayer());
                    Bukkit.getPluginManager().callEvent((Event)evt);
                    if (evt.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
                else if (this.cooldown.get(event.getPlayer()) <= System.currentTimeMillis()) {
                    this.cooldown.put(event.getPlayer(), System.currentTimeMillis() + 350L);
                    final PlayerJumpEvent evt = new PlayerJumpEvent(event.isCancelled(), event.getPlayer());
                    Bukkit.getPluginManager().callEvent((Event)evt);
                    if (evt.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        this.cooldown.remove(event.getPlayer());
    }
}
