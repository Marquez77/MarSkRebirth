package com.marquez.marsk.ArmorEquip;

import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.*;
import org.bukkit.*;

public class ArmorUnEquipListener implements Listener
{
    public ArmorUnEquipListener(final List<String> blockedMaterials, final Plugin main) {
        main.getServer().getPluginManager().registerEvents((Listener)this, main);
    }
    
    @EventHandler
    public final void onInventoryClick(final InventoryClickEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (!e.getInventory().equals(InventoryType.CREATIVE)) {
            if (e.getClick().equals((Object)ClickType.SHIFT_LEFT) || e.getClick().equals((Object)ClickType.SHIFT_RIGHT)) {
            }
            if (e.getClick().equals((Object)ClickType.NUMBER_KEY)) {
            }
            final ArmorUnEquipEvent.EquipMethod method = ArmorUnEquipEvent.EquipMethod.DRAG;
            if (ArmorType.matchType(e.getCurrentItem()) == null) {
                return;
            }
            final ArmorType newArmorType = ArmorType.matchType(e.getCurrentItem());
            final ArmorUnEquipEvent armorunEquipEvent = new ArmorUnEquipEvent((Player)e.getWhoClicked(), method, newArmorType, e.getCurrentItem());
            if (e.getRawSlot() != newArmorType.getSlot()) {
                return;
            }
            Bukkit.getServer().getPluginManager().callEvent((Event)armorunEquipEvent);
            if (armorunEquipEvent.isCancelled()) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void itemBreakEvent(final PlayerItemBreakEvent e) {
        final ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            final Player p = e.getPlayer();
            final ArmorUnEquipEvent ArmorUnEquipEvent = new ArmorUnEquipEvent(p, com.marquez.marsk.ArmorEquip.ArmorUnEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem());
            Bukkit.getServer().getPluginManager().callEvent((Event)ArmorUnEquipEvent);
            if (ArmorUnEquipEvent.isCancelled()) {
                final ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short)(i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                }
                else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                }
                else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                }
                else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }
    }
    
    @EventHandler
    public void playerDeathEvent(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        ItemStack[] armorContents;
        for (int length = (armorContents = p.getInventory().getArmorContents()).length, j = 0; j < length; ++j) {
            final ItemStack i = armorContents[j];
            if (i != null && !i.getType().equals((Object)Material.AIR)) {
                Bukkit.getServer().getPluginManager().callEvent((Event)new ArmorUnEquipEvent(p, ArmorUnEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i));
            }
        }
    }
}
