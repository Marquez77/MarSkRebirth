package com.marquez.marsk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class AreaNameComplete implements TabCompleter{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args[0].equals("delete")) {
			final List<String> completions = new ArrayList<>();
	        StringUtil.copyPartialMatches(args[0], AreaManager.getAreaNames(), completions);
	        Collections.sort(completions);
	        return completions;
		}
		return null;
	}
	
	

}
