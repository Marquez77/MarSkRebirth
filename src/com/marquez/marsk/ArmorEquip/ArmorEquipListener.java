package com.marquez.marsk.ArmorEquip;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class ArmorEquipListener implements Listener
{
    public ArmorEquipListener(final List<String> blockedMaterials, final Plugin main) {
        main.getServer().getPluginManager().registerEvents((Listener)this, main);
    }
    
	@EventHandler
    public final void onInventoryClick(final InventoryClickEvent e) {
        if (e.getClick().equals((Object)ClickType.CREATIVE)) {
            final ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.DRAG;
            if (ArmorType.matchType(e.getCursor()) == null) {
                return;
            }
            final ArmorType newArmorType2 = ArmorType.matchType(e.getCurrentItem());
            if ((!e.getWhoClicked().getItemOnCursor().equals((Object)null) && e.getSlotType().equals((Object)InventoryType.SlotType.CONTAINER)) || e.getSlotType().equals((Object)InventoryType.SlotType.QUICKBAR)) {
                return;
            }
            final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), method, newArmorType2, e.getCursor());
            Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                e.setCancelled(true);
            }
        }
        else {
            boolean shift = false;
            boolean numberkey = false;
            if (e.isCancelled()) {
                return;
            }
            if (e.getClick().equals((Object)ClickType.SHIFT_LEFT) || e.getClick().equals((Object)ClickType.SHIFT_RIGHT)) {
                shift = true;
            }
            if (e.getClick().equals((Object)ClickType.NUMBER_KEY)) {
                numberkey = true;
            }
            if ((e.getSlotType() != InventoryType.SlotType.ARMOR || e.getSlotType() != InventoryType.SlotType.QUICKBAR) && !e.getInventory().getType().equals((Object)InventoryType.CRAFTING)) {
                return;
            }
            if (!(e.getWhoClicked() instanceof Player)) {
                return;
            }
            if (e.getCurrentItem() == null) {
                return;
            }
            ArmorType newArmorType3 = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
            if (!shift && newArmorType3 != null && e.getRawSlot() != newArmorType3.getSlot()) {
                return;
            }
            if (shift) {
                try {
                	Inventory inv = e.getView().getTopInventory();
                	if (e.getRawSlot() >= e.getInventory().getSize()*9) inv = e.getView().getBottomInventory();
                    if (!inv.getType().equals((Object)InventoryType.PLAYER)) {
                        return;
                    }
                }
                catch (NoSuchMethodError noSuchMethodError) {}
                newArmorType3 = ArmorType.matchType(e.getCurrentItem());
                if (newArmorType3 != null) {
                    if (e.getSlotType() == InventoryType.SlotType.ARMOR) {
                        return;
                    }
                    boolean equipping = true;
                    if (e.getRawSlot() == newArmorType3.getSlot()) {
                        equipping = false;
                    }
                    Label_0567: {
                        if (newArmorType3.equals(ArmorType.HELMET)) {
                            if (equipping) {
                                if (e.getWhoClicked().getInventory().getHelmet() == null) {
                                    break Label_0567;
                                }
                            }
                            else if (e.getWhoClicked().getInventory().getHelmet() != null) {
                                break Label_0567;
                            }
                        }
                        if (newArmorType3.equals(ArmorType.CHESTPLATE)) {
                            if (equipping) {
                                if (e.getWhoClicked().getInventory().getChestplate() == null) {
                                    break Label_0567;
                                }
                            }
                            else if (e.getWhoClicked().getInventory().getChestplate() != null) {
                                break Label_0567;
                            }
                        }
                        if (newArmorType3.equals(ArmorType.LEGGINGS)) {
                            if (equipping) {
                                if (e.getWhoClicked().getInventory().getLeggings() == null) {
                                    break Label_0567;
                                }
                            }
                            else if (e.getWhoClicked().getInventory().getLeggings() != null) {
                                break Label_0567;
                            }
                        }
                        if (!newArmorType3.equals(ArmorType.BOOTS)) {
                            return;
                        }
                        if (equipping) {
                            if (e.getWhoClicked().getInventory().getBoots() != null) {
                                return;
                            }
                        }
                        else if (e.getWhoClicked().getInventory().getBoots() == null) {
                            return;
                        }
                    }
                    final ArmorEquipEvent armorEquipEvent2 = new ArmorEquipEvent((Player)e.getWhoClicked(), ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType3, e.getCurrentItem());
                    Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent2);
                    if (armorEquipEvent2.isCancelled()) {
                        final Player p = (Player)e.getWhoClicked();
                        p.updateInventory();
                        e.setCancelled(true);
                    }
                }
            }
            else {
                ItemStack newArmorPiece = e.getCursor();
                Inventory inv = e.getView().getTopInventory();
            	if (e.getRawSlot() >= e.getInventory().getSize()*9) inv = e.getView().getBottomInventory();
                if (numberkey && inv.getType().equals((Object)InventoryType.PLAYER)) {
                    final ItemStack hotbarItem = inv.getItem(e.getHotbarButton());
                    if (hotbarItem != null) {
                        newArmorType3 = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                    }
                    else if (newArmorPiece.getType().equals((Object)Material.AIR)) {
                        return;
                    }
                }
                if (newArmorType3 != null && e.getRawSlot() == newArmorType3.getSlot()) {
                    ArmorEquipEvent.EquipMethod method2 = ArmorEquipEvent.EquipMethod.DRAG;
                    ArmorEquipEvent armorEquipEvent3 = new ArmorEquipEvent((Player)e.getWhoClicked(), method2, newArmorType3, e.getWhoClicked().getItemOnCursor());
                    if (e.getAction().equals((Object)InventoryAction.HOTBAR_SWAP) || numberkey) {
                        method2 = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                    }
                    if (e.getClick().equals((Object)ClickType.RIGHT) || e.getClick().equals((Object)ClickType.LEFT)) {
                        armorEquipEvent3 = new ArmorEquipEvent((Player)e.getWhoClicked(), method2, newArmorType3, e.getWhoClicked().getItemOnCursor());
                    }
                    else if (e.getClick().equals((Object)ClickType.NUMBER_KEY)) {
                        armorEquipEvent3 = new ArmorEquipEvent((Player)e.getWhoClicked(), method2, newArmorType3, newArmorPiece);
                    }
                    Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent3);
                    if (armorEquipEvent3.isCancelled()) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
	@EventHandler
    public void playerInteractEvent(final PlayerInteractEvent e) {
        if (e.getAction() == Action.PHYSICAL) {
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = e.getPlayer();
            if (ArmorType.matchType(e.getItem()) == null) {
                return;
            }
            final ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null && ((newArmorType.equals(ArmorType.HELMET) && e.getPlayer().getInventory().getHelmet() == null) || (newArmorType.equals(ArmorType.CHESTPLATE) && e.getPlayer().getInventory().getChestplate() == null) || (newArmorType.equals(ArmorType.LEGGINGS) && e.getPlayer().getInventory().getLeggings() == null) || (newArmorType.equals(ArmorType.BOOTS) && e.getPlayer().getInventory().getBoots() == null))) {
                if (newArmorType.equals(null)) {
                    return;
                }
                if (e.getClickedBlock() == null || (e.getClickedBlock() != null && !newArmorType.equals(null))) {
                    if (e.getClickedBlock() != null && (e.getClickedBlock().getType().equals((Object)Material.CHEST) || e.getClickedBlock().getType().equals((Object)Material.DISPENSER) || e.getClickedBlock().getType().equals((Object)Material.HOPPER) || e.getClickedBlock().getType().equals((Object)Material.STORAGE_MINECART) || e.getClickedBlock().getType().equals((Object)Material.HOPPER_MINECART) || e.getClickedBlock().getType().equals((Object)Material.ENCHANTMENT_TABLE) || e.getClickedBlock().getType().equals((Object)Material.FURNACE))) {
                        return;
                    }
                    final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, newArmorType, e.getItem());
                    Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                    if (!armorEquipEvent.isCancelled()) {
                        return;
                    }
                    e.setCancelled(true);
                    player.updateInventory();
                }
            }
        }
    }
    
    @EventHandler
    public void dispenserFireEvent(final BlockDispenseEvent e) {
        final ArmorType type = ArmorType.matchType(e.getItem());
        if (e.getBlock() instanceof Dropper) {
            return;
        }
        if (ArmorType.matchType(e.getItem()) != null) {
            final Location loc = e.getBlock().getLocation();
            for (final Player p : loc.getWorld().getPlayers()) {
                if (loc.getBlockY() - p.getLocation().getBlockY() >= -1 && loc.getBlockY() - p.getLocation().getBlockY() <= 1 && ((p.getInventory().getHelmet() == null && type.equals(ArmorType.HELMET)) || (p.getInventory().getChestplate() == null && type.equals(ArmorType.CHESTPLATE)) || (p.getInventory().getLeggings() == null && type.equals(ArmorType.LEGGINGS)) || (p.getInventory().getBoots() == null && type.equals(ArmorType.BOOTS)))) {
                    final Dispenser dispenser = (Dispenser)e.getBlock().getState();
                    final org.bukkit.material.Dispenser dis = (org.bukkit.material.Dispenser)dispenser.getData();
                    final BlockFace directionFacing = dis.getFacing();
                    if ((directionFacing == BlockFace.EAST && p.getLocation().getBlockX() != loc.getBlockX() && p.getLocation().getX() <= loc.getX() + 2.3 && p.getLocation().getX() >= loc.getX()) || (directionFacing == BlockFace.WEST && p.getLocation().getX() >= loc.getX() - 1.3 && p.getLocation().getX() <= loc.getX()) || (directionFacing == BlockFace.SOUTH && p.getLocation().getBlockZ() != loc.getBlockZ() && p.getLocation().getZ() <= loc.getZ() + 2.3 && p.getLocation().getZ() >= loc.getZ()) || (directionFacing == BlockFace.NORTH && p.getLocation().getZ() >= loc.getZ() - 1.3 && p.getLocation().getZ() <= loc.getZ())) {
                        final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DISPENSER, ArmorType.matchType(e.getItem()), e.getItem());
                        Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
                        if (armorEquipEvent.isCancelled()) {
                            e.setCancelled(true);
                        }
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    @EventHandler
    public void itemBreakEvent(final PlayerItemBreakEvent e) {
        final ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            final Player p = e.getPlayer();
            final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.BROKE, type, e.getBrokenItem());
            Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
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
                final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(p, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(i), i);
                Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
            }
        }
    }
    
    @EventHandler
    public void playerDragInventoryEvent(final InventoryDragEvent e) {
        final ArmorType newArmorType = ArmorType.matchType(e.getOldCursor());
        if (newArmorType == null) {
            return;
        }
        final ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player)e.getWhoClicked(), ArmorEquipEvent.EquipMethod.DRAG, newArmorType, e.getOldCursor());
        if (!e.getRawSlots().contains(newArmorType.getSlot())) {
            return;
        }
        Bukkit.getServer().getPluginManager().callEvent((Event)armorEquipEvent);
        if (armorEquipEvent.isCancelled()) {
            e.setCancelled(true);
        }
    }
}
