package com.marquez.marsk.Expressions;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.marquez.marsk.AreaManager;
import com.marquez.marsk.Locations;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprInPlayers extends SimpleExpression<Player>{
	
	private Expression<String> area;
	
    public Class<? extends Player> getReturnType() {
        return Player.class;
    }
    
    public boolean isSingle() {
        return true;
    }
    
    @SuppressWarnings("unchecked")
	public boolean init(final Expression<?>[] arg0, final int arg1, final Kleenean arg2, final SkriptParser.ParseResult arg3) {
		this.area = (Expression<String>)arg0[0];
        return true;
    }
    
    public String toString(@Nullable final Event arg0, final boolean arg1) {
        return "players in area " + area;
    }
    
	@Nullable
    protected Player[] get(final Event arg0) {
    	final String area = this.area.getSingle(arg0);
    	if(AreaManager.findArea(area) == -1) {
			return null;
		}
    	List<Player> player = new ArrayList<Player>();
    	for(Player p : Bukkit.getOnlinePlayers()) {
    		if(Locations.isInPosition(new Locations(AreaManager.foundArea(area)), p.getLocation())) { 
    			player.add(p);
    		}
    	}
    	if(player.size() == 0) {
    		return null;
    	}
    	Player[] playerlist = new Player[player.size()];
    	for(int i = 0; i < player.size(); i++) {
    		playerlist[i] = player.get(i);
    	}
    	return playerlist;
    }
}