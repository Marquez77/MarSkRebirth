package com.marquez.marsk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCommand implements CommandExecutor{
	
	public static HashMap<Player, List<Location>> hash = new HashMap<Player, List<Location>>();
	public static String prefix = "��b��l[MarSK] ";
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			if(label.equalsIgnoreCase("marsk")) {
				String[] update = Main.updateCheck();
				if(update[0].equals(Main.version)) update = null;
				if(p.isOp() && update != null) {
					p.sendMessage(prefix + "��b��lMarSK�� �ֽŹ����� �����մϴ�!");
					p.sendMessage(prefix + "��a�ֽŹ�����7:��ev" + update[0] + " ��c���������7:��ev" + Main.version);
					p.sendMessage(prefix + "��6�ٿ�ε� ��ũ ��7: ��f" + update[1]);
					p.sendMessage(prefix + "��7(�ش� �޼����� OP���Ը� ��µ˴ϴ�)");
				}else {
					p.sendMessage(prefix + "��a���������7:��ev" + Main.version);
				}
			}
			if(label.equalsIgnoreCase("ska")) {
				if(p.isOp()) {
					if(args.length == 0) {
						p.sendMessage(prefix + "��f��l/ska select ��7��l- ������ �����մϴ�. [On/Off]");
						p.sendMessage(prefix + "��f��l/ska create [�̸�] ��7��l- ������ �����մϴ�.");
						p.sendMessage(prefix + "��f��l/ska delete [�̸�] ��7��l- ������ �����մϴ�.");
						p.sendMessage(prefix + "��f��l/ska list ��7��l- ����� Ȯ���մϴ�.");
						p.sendMessage(prefix + "��f��l/ska save ��7��l- �����͸� �����մϴ�.");
						p.sendMessage(prefix + "��f��l/ska load ��7��l- �����͸� �ҷ��ɴϴ�.");
					}else{
						switch(args[0]) {
						case "select":
							if(hash.get(p) == null) {
								List<Location> location = new ArrayList<Location>();
								location.add(null);
								location.add(null);
								hash.put(p, location);
								p.sendMessage(prefix + "��e�Ǽ����� ��Ŭ��, ��Ŭ���� �Ͽ� ������ �������ּ���.");
							}else{
								hash.remove(p);
								p.sendMessage(prefix + "��c���� ���� ��尡 �����Ǿ����ϴ�.");
							}
							break;
						case "create":
							if(args.length >= 2) {
								if(hash.get(p) == null) {
									p.sendMessage(prefix + "��c��ġ�� �������� �ʾҽ��ϴ�.");
									break;
								}
								if(hash.get(p).get(0) == null) {
									p.sendMessage(prefix + "��c��ġ 1�� �������� �ʾҽ��ϴ�.");
									break;
								}else if(hash.get(p).get(1) == null) {
									p.sendMessage(prefix + "��c��ġ 2�� �������� �ʾҽ��ϴ�.");
									break;
								}
								if(AreaFile.createArea(args[1], hash.get(p))) {
									p.sendMessage(prefix + "��a���� \'" + args[1] + "\' �� �����Ͽ����ϴ�.");
									hash.remove(p);
								}else{
									p.sendMessage(prefix + "��c�̸��� �̹� �����ϰų�, ���� ������ �ùٸ��� �ʽ��ϴ�.");
								}
							}else{
								p.sendMessage(prefix + "��c�̸��� �Է����ּ���.");
							}
							break;
						case "delete":
							if(args.length >= 2) {
								if(AreaFile.deleteArea(args[1])) {
									p.sendMessage(prefix + "��a������ �����Ͽ����ϴ�.");
								}else{
									p.sendMessage(prefix + "��c�������� �ʴ� �̸��Դϴ�.");
								}
							}else{
								p.sendMessage(prefix + "��c�̸��� �Է����ּ���.");
							}
							break;
						case "list":
							p.sendMessage("��f��l:: Area list ::");
							for(Area a : AreaFile.areaArray) {
								String name = a.getName();
								List<Location> location = a.getLoc();
								p.sendMessage("��e" + name + " ��7(world: " + location.get(0).getWorld().getName() + " " + AreaFile.locationToString(location.get(0)) + " ~ " + AreaFile.locationToString(location.get(1)) + ")");
							}
							break;
						case "save":
							AreaFile.saveArea();
							p.sendMessage(prefix + "��a���������� �����͸� �����Ͽ����ϴ�.");
							break;
						case "load":
							AreaFile.loadArea();
							p.sendMessage(prefix + "��a���������� �����͸� �ҷ��Խ��ϴ�.");
							break;
						default:
							p.sendMessage(prefix + "��c�ùٸ��� ���� ��ɾ��Դϴ�.");
							break;
						}
					}
				}
			}
		}
		return true;
	}
}
