package com.marquez.marsk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.marquez.marsk.ArmorEquip.ArmorEquipEvent;
import com.marquez.marsk.ArmorEquip.ArmorEquipListener;
import com.marquez.marsk.ArmorEquip.ArmorUnEquipEvent;
import com.marquez.marsk.ArmorEquip.ArmorUnEquipListener;
import com.marquez.marsk.Conditions.CondIsInArea;
import com.marquez.marsk.Conditions.CondIsSymbols;
import com.marquez.marsk.Effects.EffCreateArea;
import com.marquez.marsk.Effects.EffRemoveArea;
import com.marquez.marsk.Effects.EffSpawnStaticItem;
import com.marquez.marsk.Events.EvtEnterArea;
import com.marquez.marsk.Events.EvtQuitArea;
import com.marquez.marsk.Expressions.ExprAllAreas;
import com.marquez.marsk.Expressions.ExprCharAt;
import com.marquez.marsk.Expressions.ExprClickNumber;
import com.marquez.marsk.Expressions.ExprDecimal;
import com.marquez.marsk.Expressions.ExprEnchantLevel;
import com.marquez.marsk.Expressions.ExprGetWebSource;
import com.marquez.marsk.Expressions.ExprHealthRegenCause;
import com.marquez.marsk.Expressions.ExprInPlayers;
import com.marquez.marsk.Expressions.ExprPlayerAreaName;
import com.marquez.marsk.Expressions.ExprSortDown;
import com.marquez.marsk.Expressions.ExprSortUp;
import com.marquez.marsk.Jump.EvtJump;
import com.marquez.marsk.Jump.JumpListener;
import com.marquez.marsk.Jump.PlayerJumpEvent;
import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.AreaSelector;
import com.marquez.marsk.cmds.AreaNameComplete;
import com.marquez.marsk.cmds.MCommand;
import com.marquez.marsk.cmds.ScriptsComplete;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.util.SimpleEvent;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MarSk extends JavaPlugin implements Listener {
	public static String prefix = "§f§l[MarSkRebirth]";

	public static MarSk instance;
	public static String version;
	public MCommand command;
	public String[] update;

	public void onEnable() {
		instance = this;
		update = Checker.updateCheck();
		version = getDescription().getVersion();
		Bukkit.getConsoleSender().sendMessage(prefix + " §a플러그인 활성화 " + "§ev" + getDescription().getVersion() + " §8-Made by Mar(마르)");
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new AreaSelector(), this);
		Skript.registerAddon((JavaPlugin)this);
		this.registerEvents();
		this.registerEffects();
		this.registerExpressions();
		this.registerConditions();
		registercommand();
		AreaManager.loadArea();
		Bukkit.getConsoleSender().sendMessage(prefix + " §a에드온 연결 완료 §8-Made by Mar(마르)");
	}

	public void onDisable() {
		AreaManager.saveArea();
		Bukkit.getConsoleSender().sendMessage(prefix + " §c플러그인 비활성화 " + "§ev" + getDescription().getVersion() + " §8-Made by Mar(마르)");
	}

	public void registercommand() {
		command = new MCommand();
		getCommand("ska").setExecutor(command);
		getCommand("ska").setTabCompleter(new AreaNameComplete());
		getCommand("marsk").setExecutor(command);
		JavaPlugin skript = (JavaPlugin)Bukkit.getPluginManager().getPlugin("Skript");
		ScriptsComplete sc = new ScriptsComplete();
		skript.getCommand("sk").setTabCompleter(sc);
		skript.getCommand("skript").setTabCompleter(sc);
	}

	public void registerEvents() {
		Skript.registerEvent("entity health regen", (Class)SimpleEvent.class, (Class)EntityRegainHealthEvent.class, new String[] { "entity health regen" });
		EventValues.registerEventValue((Class)EntityRegainHealthEvent.class, (Class)Entity.class, (Getter)new Getter<Entity, EntityRegainHealthEvent>() {
			public Entity get(final EntityRegainHealthEvent event) {
				final Entity e = event.getEntity();
				return e;
			}
		}, 0);
		EventValues.registerEventValue((Class)EntityRegainHealthEvent.class, (Class)Number.class, (Getter)new Getter<Number, EntityRegainHealthEvent>() {
			public Number get(final EntityRegainHealthEvent event) {
				final Number n = ((double)event.getAmount()/2.0);
				return n;
			}
		}, 0);
		Skript.registerEvent("enter area", (Class)EvtEnterArea.class, (Class)PlayerMoveEvent.class, new String[] { "(enter|join) area[ at %string%]" });
		Skript.registerEvent("quit area", (Class)EvtQuitArea.class, (Class)PlayerMoveEvent.class, new String[] { "(exit|quit|leave) area[ at %string%]" });
		Skript.registerEvent("mar.jump", (Class)EvtJump.class, (Class)PlayerJumpEvent.class, new String[] { "[mar.]jump" });
		EventValues.registerEventValue((Class)PlayerJumpEvent.class, (Class)Player.class, (Getter)new Getter<Player, PlayerJumpEvent>() {
			public Player get(final PlayerJumpEvent e) {
				final Player p = (Player)e.getPlayer();
				return p;
			}
		}, 0);
		new JumpListener(this);
		Skript.registerEvent("mar.inventory open", (Class)SimpleEvent.class, (Class)InventoryOpenEvent.class, new String[] { "[mar.]inventory open" });
		EventValues.registerEventValue((Class)InventoryOpenEvent.class, (Class)Player.class, (Getter)new Getter<Player, InventoryOpenEvent>() {
			public Player get(final InventoryOpenEvent e) {
				final Player p = (Player)e.getPlayer();
				return p;
			}
		}, 0);
		Skript.registerEvent("armor equip", (Class)SimpleEvent.class, (Class)ArmorEquipEvent.class, new String[] { "(armor|armour) equip" });
		EventValues.registerEventValue((Class)ArmorEquipEvent.class, (Class)ItemStack.class, (Getter)new Getter<ItemStack, ArmorEquipEvent>() {
			public ItemStack get(final ArmorEquipEvent e) {
				final ItemStack item = e.getItem();
				return item;
			}
		}, 0);
		new ArmorEquipListener(null, (Plugin)this);
		Skript.registerEvent("armor unequip", (Class)SimpleEvent.class, (Class)ArmorUnEquipEvent.class, new String[] { "(armor|armour) unequip" });
		EventValues.registerEventValue((Class)ArmorUnEquipEvent.class, (Class)ItemStack.class, (Getter)new Getter<ItemStack, ArmorUnEquipEvent>() {
			public ItemStack get(final ArmorUnEquipEvent e) {
				final ItemStack item = e.getItem();
				return item;
			}
		}, 0);
		new ArmorUnEquipListener(null, (Plugin)this);
	}

	public void registerEffects() {
		Skript.registerEffect((Class)EffCreateArea.class, new String[] { "create area of %string% at %location%, %location%" });
		Skript.registerEffect((Class)EffRemoveArea.class, new String[] { "(remove|delete) area of %string%" });
		Skript.registerEffect((Class)EffSpawnStaticItem.class, new String[] { "spawn static item %itemstacks% at %location%" });
	}

	public void registerExpressions() {
		Skript.registerExpression((Class)ExprHealthRegenCause.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "regen cause" });
		Skript.registerExpression((Class)ExprInPlayers.class, (Class)Player.class, ExpressionType.PROPERTY, new String[] { "players in area %string%" });
		Skript.registerExpression((Class)ExprPlayerAreaName.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "[entered ]area of (%entity%|%player%|%location%)" });
		Skript.registerExpression((Class)ExprAllAreas.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "all areas" });
		Skript.registerExpression((Class)ExprSortUp.class, (Class)Number.class, ExpressionType.PROPERTY, new String[] { "sort up %numbers%" });
		Skript.registerExpression((Class)ExprSortDown.class, (Class)Number.class, ExpressionType.PROPERTY, new String[] { "sort down %numbers%" });
		Skript.registerExpression((Class)ExprDecimal.class, (Class)Number.class, ExpressionType.PROPERTY, new String[] { "decimal with %integer% in %number%" });
		Skript.registerExpression((Class)ExprCharAt.class, (Class)Character.class, ExpressionType.PROPERTY, new String[] { "char at %number% in %string%"});
		Skript.registerExpression((Class)ExprEnchantLevel.class, (Class)Integer.class, ExpressionType.PROPERTY, new String[] { "enchant level of %enchantment% of %itemstacks%" });
		Skript.registerExpression((Class)ExprGetWebSource.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "web source of %string%"});
		Skript.registerExpression((Class)ExprClickNumber.class, (Class)Integer.class, ExpressionType.PROPERTY, new String[] { "click hotbar number" });
	}

	public void registerConditions() {
		Skript.registerCondition((Class)CondIsSymbols.class, new String[] { "%string% contains symbols" });
		Skript.registerCondition((Class)CondIsInArea.class, new String[] { "(%entity%|%player%|%location%) is in area %string%" });
	}
	
	@EventHandler
	public void check(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if(p.getName().contains("Marquez_")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, 
					new Runnable() {
				@Override
				public void run() {
					if(p.isOnline()) {
						p.sendMessage(prefix + " §b§l해당 서버는 MarSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
						p.sendMessage(prefix + " §b§l해당 서버는 MarSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
						p.sendMessage(prefix + " §b§l해당 서버는 MarSK v" + getDescription().getVersion() + " 플러그인을 사용중입니다.");
					}
				}
			}, 40L);
		}
	}
}