package com.marquez.marsk.cmds;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ScriptsComplete implements TabCompleter{
	
	private File folder = new File("plugins/Skript/scripts/");
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) {
			List<String> completions = new ArrayList<String>();
			for(String s : Arrays.asList("reload", "enable", "disable", "update", "help")) {
				if(s.startsWith(args[0])) {
					completions.add(s);
				}
			}
			Collections.sort(completions);
	        return completions;
		}else {
			if(args[0].equals("reload") || args[0].equals("disable")) {
				List<String> completions = getFiles(false);
				for(String s : new ArrayList<String>(completions)) {
					if(!s.startsWith(args[1])) completions.remove(s);
				}
		        Collections.sort(completions);
		        return completions;
			}else if(args[0].equals("enable")) {
				List<String> completions = getFiles(true);
				for(String s : new ArrayList<String>(completions)) {
					if(!s.startsWith(args[1])) completions.remove(s);
				}
		        Collections.sort(completions);
		        return completions;
			}
		}
		return null;
	}
	
	public List<String> getFiles(boolean disabled) {
		List<String> result = new ArrayList<String>();
		List<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		int i = 0;
		while(i < files.size()) {
			File file = files.get(i++);
			if(file.isDirectory()) {
				result.add(file.getName() + "/");
				for(File inner : file.listFiles()) {
					files.add(inner);
				}
			}else {
				String name = file.getName().replace(".sk", "");
				if((name.startsWith("-") && disabled) || !name.startsWith("-") && !disabled) {
					if(file.getParentFile().equals(folder)) {
						result.add(name.replaceFirst("-", ""));
					}else {
						result.add(file.getParent().replace(folder.getPath() + "\\", "").replace("\\", "/") + "/" + file.getName().replace(".sk", "").replaceFirst("-", ""));
					}
				}
			}
		}
		return result;
	}

}
