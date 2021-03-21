package com.marquez.marsk;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.marquez.marsk.area.AreaManager;
import com.marquez.marsk.area.events.PlayerEnterAreaEvent;
import com.marquez.marsk.area.events.PlayerLeaveAreaEvent;
import com.marquez.marsk.area.listeners.AreaSelector;
import com.marquez.marsk.area.listeners.PlayerAreaListener;
import com.marquez.marsk.armorequip.ArmorEquipEvent;
import com.marquez.marsk.armorequip.ArmorEquipListener;
import com.marquez.marsk.armorequip.ArmorUnEquipEvent;
import com.marquez.marsk.armorequip.ArmorUnEquipListener;
import com.marquez.marsk.cmds.AreaNameComplete;
import com.marquez.marsk.cmds.MCommand;
import com.marquez.marsk.cmds.ScriptsComplete;
import com.marquez.marsk.conditions.CondIsInArea;
import com.marquez.marsk.conditions.CondIsSymbols;
import com.marquez.marsk.effects.EffCreateArea;
import com.marquez.marsk.effects.EffRemoveArea;
import com.marquez.marsk.effects.EffSpawnStaticItem;
import com.marquez.marsk.events.EvtEnterArea;
import com.marquez.marsk.events.EvtLeaveArea;
import com.marquez.marsk.expressions.ExprAllAreas;
import com.marquez.marsk.expressions.ExprCharAt;
import com.marquez.marsk.expressions.ExprClickNumber;
import com.marquez.marsk.expressions.ExprDecimal;
import com.marquez.marsk.expressions.ExprEnchantLevel;
import com.marquez.marsk.expressions.ExprEventArea;
import com.marquez.marsk.expressions.ExprGetWebSource;
import com.marquez.marsk.expressions.ExprHealthRegenCause;
import com.marquez.marsk.expressions.ExprInPlayers;
import com.marquez.marsk.expressions.ExprPlayerAreaName;
import com.marquez.marsk.expressions.ExprSortDown;
import com.marquez.marsk.expressions.ExprSortUp;
import com.marquez.marsk.jump.EvtJump;
import com.marquez.marsk.jump.JumpListener;
import com.marquez.marsk.jump.PlayerJumpEvent;

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

	public void onEnable() {
		instance = this;
		version = getDescription().getVersion();
		Bukkit.getConsoleSender().sendMessage(prefix + " §a플러그인 활성화 " + "§ev" + getDescription().getVersion() + " §8-Made by Mar(마르)");
		File file = new File(getDataFolder(), "config.yml");
		if(!file.exists()) {
			getConfig().options().copyDefaults(true);
			saveDefaultConfig();
		}
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new AreaSelector(), this);
		getServer().getPluginManager().registerEvents(new PlayerAreaListener(), this);
		Skript.registerAddon((JavaPlugin)this);
		this.registerEvents();
		this.registerEffects();
		this.registerExpressions();
		this.registerConditions();
		registercommand();
		Bukkit.getScheduler().runTask(this, () -> { AreaManager.loadArea(); });
		Bukkit.getConsoleSender().sendMessage(prefix + " §a에드온 연결 완료 §8-Made by Mar(마르)");
	}

	public void onDisable() {
		AreaManager.saveArea();
		Bukkit.getConsoleSender().sendMessage(prefix + " §c플러그인 비활성화 " + "§ev" + getDescription().getVersion() + " §8-Made by Mar(마르)");
	}

	public void registercommand() {
		command = new MCommand();
		getCommand("ska").setExecutor(command);
		if(getOptions("Command.SkaAutoComplete")) getCommand("ska").setTabCompleter(new AreaNameComplete());
		if(getOptions("UpdateCheck")) getCommand("marsk").setExecutor(command);
		if(getOptions("Command.SkriptAutoComplete")) { 
			JavaPlugin skript = (JavaPlugin)Bukkit.getPluginManager().getPlugin("Skript");
			ScriptsComplete sc = new ScriptsComplete();
			skript.getCommand("sk").setTabCompleter(sc);
			skript.getCommand("skript").setTabCompleter(sc);
		}
	}

	public static boolean getOptions(String option) {
		if(!instance.getConfig().isSet(option)) {
			instance.getConfig().set(option, true);
			instance.saveConfig();
		}
		return instance.getConfig().getBoolean(option);
	}

	public void registerEvents() {
		if(getOptions("Events.onHealthRegen")) {
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
		}
		if(getOptions("Events.onEnterArea")) {
			Skript.registerEvent("enter area", (Class)EvtEnterArea.class, (Class)PlayerEnterAreaEvent.class, new String[] { "(enter|join) area[ at %string%]" });
			EventValues.registerEventValue((Class)PlayerEnterAreaEvent.class, (Class)Player.class, (Getter)new Getter<Player, PlayerEnterAreaEvent>() {
				public Player get(final PlayerEnterAreaEvent e) {
					final Player p = (Player)e.getPlayer();
					return p;
				}
			}, 0);
		}
		if(getOptions("Events.onLeaveArea")) {
			Skript.registerEvent("quit area", (Class)EvtLeaveArea.class, (Class)PlayerLeaveAreaEvent.class, new String[] { "(exit|quit|leave) area[ at %string%]" });
			EventValues.registerEventValue((Class)PlayerLeaveAreaEvent.class, (Class)Player.class, (Getter)new Getter<Player, PlayerLeaveAreaEvent>() {
				public Player get(final PlayerLeaveAreaEvent e) {
					final Player p = (Player)e.getPlayer();
					return p;
				}
			}, 0);
		}
		if(getOptions("Events.onJump")) {
			Skript.registerEvent("mar.jump", (Class)EvtJump.class, (Class)PlayerJumpEvent.class, new String[] { "[mar.]jump [air %boolean%]" });
			EventValues.registerEventValue((Class)PlayerJumpEvent.class, (Class)Player.class, (Getter)new Getter<Player, PlayerJumpEvent>() {
				public Player get(final PlayerJumpEvent e) {
					final Player p = (Player)e.getPlayer();
					return p;
				}
			}, 0);
			new JumpListener(this);
		}
		if(getOptions("Events.onArmorEquip")) {
			Skript.registerEvent("armor equip", (Class)SimpleEvent.class, (Class)ArmorEquipEvent.class, new String[] { "[mar.](armor|armour) equip" });
			EventValues.registerEventValue((Class)ArmorEquipEvent.class, (Class)ItemStack.class, (Getter)new Getter<ItemStack, ArmorEquipEvent>() {
				public ItemStack get(final ArmorEquipEvent e) {
					final ItemStack item = e.getItem();
					return item;
				}
			}, 0);
			new ArmorEquipListener(null, (Plugin)this);
		}
		if(getOptions("Events.onArmorUnequip")) {
			Skript.registerEvent("armor unequip", (Class)SimpleEvent.class, (Class)ArmorUnEquipEvent.class, new String[] { "[mar.](armor|armour) unequip" });
			EventValues.registerEventValue((Class)ArmorUnEquipEvent.class, (Class)ItemStack.class, (Getter)new Getter<ItemStack, ArmorUnEquipEvent>() {
				public ItemStack get(final ArmorUnEquipEvent e) {
					final ItemStack item = e.getItem();
					return item;
				}
			}, 0);
			new ArmorUnEquipListener(null, (Plugin)this);
		}
	}

	public void registerEffects() {
		if(getOptions("Effects.CreateArea")) Skript.registerEffect((Class)EffCreateArea.class, new String[] { "create area of %string% at %location%, %location%" });
		if(getOptions("Effects.RemoveArea")) Skript.registerEffect((Class)EffRemoveArea.class, new String[] { "(remove|delete) area of %string%" });
		if(getOptions("Effects.SpawnStaticItem")) Skript.registerEffect((Class)EffSpawnStaticItem.class, new String[] { "spawn static item %itemstacks% at %location%" });
	}

	public void registerExpressions() {
		if(getOptions("Events.onHealthRegen")) Skript.registerExpression((Class)ExprHealthRegenCause.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "regen cause" });
		if(getOptions("Expressions.AreaInPlayers")) Skript.registerExpression((Class)ExprInPlayers.class, (Class)Player.class, ExpressionType.PROPERTY, new String[] { "players in area %string%" });
		if(getOptions("Expressions.EnteredAreaName")) Skript.registerExpression((Class)ExprPlayerAreaName.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "[entered ]area of %location%" });
		if(getOptions("Events.onEnterArea") || getOptions("Events.onLeaveArea")) Skript.registerExpression((Class)ExprEventArea.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "event-area" });
		if(getOptions("Expressions.AllAreas")) Skript.registerExpression((Class)ExprAllAreas.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "all areas" });
		if(getOptions("Expressions.SortUp")) Skript.registerExpression((Class)ExprSortUp.class, (Class)Number.class, ExpressionType.PROPERTY, new String[] { "sort up %numbers%" });
		if(getOptions("Expressions.SortDown")) Skript.registerExpression((Class)ExprSortDown.class, (Class)Number.class, ExpressionType.PROPERTY, new String[] { "sort down %numbers%" });
		if(getOptions("Expressions.Decimal")) Skript.registerExpression((Class)ExprDecimal.class, (Class)Number.class, ExpressionType.PROPERTY, new String[] { "decimal with %integer% in %number%" });
		if(getOptions("Expressions.CharAt")) Skript.registerExpression((Class)ExprCharAt.class, (Class)Character.class, ExpressionType.PROPERTY, new String[] { "char at %number% in %string%"});
		if(getOptions("Expressions.EnchantLevel")) Skript.registerExpression((Class)ExprEnchantLevel.class, (Class)Integer.class, ExpressionType.PROPERTY, new String[] { "enchant level of %enchantment% of %itemstacks%" });
		if(getOptions("Expressions.WebSource")) Skript.registerExpression((Class)ExprGetWebSource.class, (Class)String.class, ExpressionType.PROPERTY, new String[] { "web source of %string%"});
		if(getOptions("Expressions.ClickHotbarNumber")) Skript.registerExpression((Class)ExprClickNumber.class, (Class)Integer.class, ExpressionType.PROPERTY, new String[] { "click hotbar number" });
	}

	public void registerConditions() {
		if(getOptions("Conditions.ContainsSymbols")) Skript.registerCondition((Class)CondIsSymbols.class, new String[] { "%string% contains symbols" });
		if(getOptions("Conditions.IsInArea")) Skript.registerCondition((Class)CondIsInArea.class, new String[] { "%location% is in area %string%" });
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