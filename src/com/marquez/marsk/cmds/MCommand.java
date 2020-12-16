package com.marquez.marsk.cmds;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.marquez.marsk.Checker;
import com.marquez.marsk.MarSk;
import com.marquez.marsk.area.Area;
import com.marquez.marsk.area.AreaManager;

public class MCommand implements CommandExecutor{

	public static HashMap<CommandSender, Location[]> hash = new HashMap<CommandSender, Location[]>();
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
					sender.sendMessage(prefix + "§f/ska pos1 [x,y,z|x y z] (world) §7- 첫번째 지점을 선택합니다.");
					sender.sendMessage(prefix + "§f/ska pos2 [x,y,z|x y z] (world) §7- 두번째 지점을 선택합니다.");
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
						if(!hash.containsKey(p)) {
							hash.put(p, new Location[2]);
							p.sendMessage(prefix + "§e맨손으로 우클릭, 좌클릭을 하여 구역을 설정해주세요.");
						}else{
							hash.remove(p);
							p.sendMessage(prefix + "§c구역 선택 모드가 해제되었습니다.");
						}
						break;
					}
					case "pos1":
					case "pos2": {
						int nth = Integer.parseInt(args[0].replace("pos", ""))-1;
						Location[] locations;
						if(!hash.containsKey(sender)) {
							locations = new Location[2];
						}else locations = hash.get(sender);
						if(args.length > 3 && args.length < 6) {
							World world = sender instanceof Player && args.length == 4 ? ((Player)sender).getWorld() : args.length == 4 ? null : Bukkit.getWorld(args[4]);
							if(world == null) {
								sender.sendMessage(prefix + "§c존재하지 않는 월드입니다.");
								break;
							}
							locations[nth] = new Location(world, Integer.parseInt(args[1].contains(",") ? args[1].replace(",", "") : args[1]), Integer.parseInt(args[2].contains(",") ? args[2].replace(",", "") : args[2]), Integer.parseInt(args[3].contains(",") ? args[3].replace(",", "") : args[3]));
							hash.put(sender, locations);
						}else if(args.length > 1 && args[1].contains(",")) {
							String[] splited = args[1].split(",");
							World world = sender instanceof Player && args.length == 2 ? ((Player)sender).getWorld() : args.length == 2 ? null : Bukkit.getWorld(args[2]);
							if(world == null) {
								sender.sendMessage(prefix + "§c존재하지 않는 월드입니다.");
								break;
							}
							locations[nth] = new Location(world, Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
							hash.put(sender, locations);
						}else {
							sender.sendMessage(prefix + "§c명령어 인자가 올바르지 않습니다.");
							break;
						}
						sender.sendMessage(MCommand.prefix + "§e위치 " + (nth+1) + ": " + AreaManager.locationToString(locations[nth]));
						break;
					}
					case "create": {
						if(!(sender instanceof Player)) break;
						Player p = (Player)sender;
						if(args.length >= 2) {
							if(!hash.containsKey(p)) {
								p.sendMessage(prefix + "§c위치를 지정하지 않았습니다.");
								break;
							}
							if(hash.get(p)[0] == null) {
								p.sendMessage(prefix + "§c위치 1이 지정되지 않았습니다.");
								break;
							}else if(hash.get(p)[1] == null) {
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
								Location[] locations = area.getLocations();
								sender.sendMessage("§f[" + ++i + "] §e" + name + " §7(world: " + locations[0].getWorld().getName() + " " + AreaManager.locationToString(locations[0]) + " ~ " + AreaManager.locationToString(locations[1]) + ")");
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
