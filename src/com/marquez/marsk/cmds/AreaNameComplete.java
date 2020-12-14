package com.marquez.marsk.cmds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.marquez.marsk.area.AreaManager;

public class AreaNameComplete implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) {
			List<String> completions = new ArrayList<String>();
			for(String s : Arrays.asList("sel", "select", "create", "del", "delete", "list", "save", "load")) {
				if(s.startsWith(args[0])) {
					completions.add(s);
				}
			}
			Collections.sort(completions);
	        return completions;
		}else if(args[0].equals("delete") || args[0].equals("del")) {
			List<String> completions = AreaManager.getAreaNames();
			for(String s : new ArrayList<String>(completions)) {
				if(!s.startsWith(args[1])) completions.remove(s);
			}
	        Collections.sort(completions);
	        return completions;
		}
		return null;
	}
	
}
