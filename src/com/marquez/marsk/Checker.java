package com.marquez.marsk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Checker {
	
	public static String[] updateCheck() {
		try {
			URL url = new URL("http://marquezupdate.zz.am");
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			uc.setRequestMethod("GET");
			uc.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader r = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String version = r.readLine();
			String download_url = r.readLine();
			return new String[] {version, download_url};
		}catch (Exception ex) {
		}
		return null;
	}
}
