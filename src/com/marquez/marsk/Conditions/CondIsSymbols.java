package com.marquez.marsk.Conditions;

import javax.annotation.*;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondIsSymbols extends Condition{

	private Expression<String> text;
	
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.text = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "contains Symbols";
	}

	public boolean check(Event arg0) {
		String text = (String)this.text.getSingle(arg0);
		if(text == null) {
			return false;
		}
		if(!text.equals(StringReplace(text))) {
			return true;
		}
		return false;
	}	
	public static String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s\u3131-\u3163]";
		str = str.replaceAll(match, "");
		return str;
	}
}
