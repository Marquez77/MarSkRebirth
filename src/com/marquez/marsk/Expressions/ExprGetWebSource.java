package com.marquez.marsk.Expressions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetWebSource extends SimpleExpression<String>{
	private Expression<String> location;
	
	public Class<? extends String> getReturnType() {
        return String.class;
    }

	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		this.location = (Expression<String>)arg0[0];
		return true;
	}

	public String toString(@Nullable Event arg0, boolean arg1) {
		return "readf";
	}

	@Nullable
	protected String[] get(Event arg0) {
		final String address = (String)this.location.getSingle(arg0);
		String[] str = null;
		try {
			URL url = new URL(address);
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			uc.setRequestMethod("GET");
			uc.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader r = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
			List<String> text = new ArrayList<String>();
			String s;
			while((s = r.readLine()) != null) {
				text.add(s);
			}
			str = new String[text.size()];
			for(int i = 0; i < text.size(); i++) {
				str[i] = text.get(i);
			}
		}catch (MalformedURLException e) {
			Skript.error("URL 포로토콜의 형식이 잘못되었습니다.");
		} catch (ProtocolException e) {
			Skript.error("데이터 전송 프로토콜이 일치하지 않아 원격 상대방과 통신할 수 없습니다.");
		} catch (IOException e) {
			Skript.error("I/O에 오류가 발생하였습니다.");
		}
		return str;
	}
}