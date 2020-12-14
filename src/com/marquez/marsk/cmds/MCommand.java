package com.marquez.marsk.cmds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.marquez.marsk.Checker;
import com.marquez.marsk.MarSk;
import com.marquez.marsk.area.Area;
import com.marquez.marsk.area.AreaManager;

public class MCommand implements CommandExecutor{

	public static HashMap<Player, List<Location>> hash = new HashMap<Player, List<Location>>();
	public static String prefix = "§b§l[MarSkRebirth] ";

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("marsk") && MarSk.getOptions("UpdateCheck")) {
			String[] update = Checker.updateCheck();
			if(update[0].equals(MarSk.version)) update = null;
			if(sender.isOp() && update != null) {
				sender.sendMessage(prefix + "§b§lMarSkRebirth§f의 최신버전이 존재합니다!");
				sender.sendMessage(prefix + "§a최신버전§7:§ev" + update[0] + " §c현재버전§7:§ev" + MarSk.version);
				sender.sendMessage(prefix + "§f다운로드 링크 §7: §f" + update[1]);
				sender.sendMessage(prefix + "§7(해당 메세지는 OP에게만 출력됩니다)");
			}else {
				sender.sendMessage(prefix + "§a현재버전§7:§ev" + MarSk.version);
			}
		}
		if(label.equalsIgnoreCase("ska")) {
			if(sender.isOp()) {
				if(args.length == 0) {
					sender.sendMessage(prefix + "§f/ska sel(ect) §7- 구역을 선택합니다. [On/Off]");
					sender.sendMessage(prefix + "§f/ska create [이름] §7- 구역을 생성합니다.");
					sender.sendMessage(prefix + "§f/ska del(ete) [이름] §7- 구역을 삭제합니다.");
					sender.sendMessage(prefix + "§f/ska list §7- 목록을 확인합니다.");
					sender.sendMessage(prefix + "§f/ska save §7- 데이터를 저장합니다.");
					sender.sendMessage(prefix + "§f/ska load §7- 데이터를 불러옵니다.");
				}else{
					switch(args[0]) {
					case "sel":
					case "select": {
						if(!(sender instanceof Player)) break;
						Player p = (Player)sender;
						if(hash.get(p) == null) {
							List<Location> location = new ArrayList<Location>();
							location.add(null);
							location.add(null);
							hash.put(p, location);
							p.sendMessage(prefix + "§e맨손으로 우클릭, 좌클릭을 하여 구역을 설정해주세요.");
						}else{
							hash.remove(p);
							p.sendMessage(prefix + "§c구역 선택 모드가 해제되었습니다.");
						}
						break;
					}
					case "create": {
						if(!(sender instanceof Player)) break;
						Player p = (Player)sender;
						if(args.length >= 2) {
							if(hash.get(p) == null) {
								p.sendMessage(prefix + "§c위치를 지정하지 않았습니다.");
								break;
							}
							if(hash.get(p).get(0) == null) {
								p.sendMessage(prefix + "§c위치 1이 지정되지 않았습니다.");
								break;
							}else if(hash.get(p).get(1) == null) {
								p.sendMessage(prefix + "§c위치 2가 지정되지 않았습니다.");
								break;
							}
							if(AreaManager.createArea(args[1], hash.get(p))) {
								p.sendMessage(prefix + "§a구역 \'" + args[1] + "\' 를 생성하였습니다.");
								hash.remove(p);
							}else{
								p.sendMessage(prefix + "§c이름이 이미 존재하거나, 구역 설정이 올바르지 않습니다.");
							}
						}else{
							p.sendMessage(prefix + "§c이름을 입력해주세요.");
						}
						break;
					}
					case "del":
					case "delete":
						if(args.length >= 2) {
							if(AreaManager.deleteArea(args[1])) {
								sender.sendMessage(prefix + "§a구역을 삭제하였습니다.");
							}else{
								sender.sendMessage(prefix + "§c존재하지 않는 이름입니다.");
							}
						}else{
							sender.sendMessage(prefix + "§c이름을 입력해주세요.");
						}
						break;
					case "list":
						if(AreaManager.getAreas().isEmpty()) {
							sender.sendMessage(prefix + "§c생성된 구역이 없습니다.");
						}else {
							sender.sendMessage("§f< 구역 목록 >");
							int i = 0;
							for(Area area : AreaManager.getAreas()) {
								String name = area.getName();
								List<Location> location = area.getLoc();
								sender.sendMessage("§f[" + ++i + "] §e" + name + " §7(world: " + location.get(0).getWorld().getName() + " " + AreaManager.locationToString(location.get(0)) + " ~ " + AreaManager.locationToString(location.get(1)) + ")");
							}
						}
						break;
					case "save":
						AreaManager.saveArea();
						sender.sendMessage(prefix + "§a성공적으로 데이터를 저장하였습니다.");
						break;
					case "load":
						AreaManager.loadArea();
						sender.sendMessage(prefix + "§a성공적으로 데이터를 불러왔습니다.");
						break;
					default:
						sender.sendMessage(prefix + "§c올바르지 않은 명령어입니다.");
						break;
					}
				}
			}
		}
		return true;
	}
}
