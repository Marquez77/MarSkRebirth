package com.marquez.marsk.cmds;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.marquez.marsk.area.AreaManager;

public class AreaNameComplete implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args[0].equals("delete")) {
			List<String> completions = AreaManager.getAreaNames();
	        Collections.sort(completions);
	        return completions;
		}
		return null;
	}
	
	

}
