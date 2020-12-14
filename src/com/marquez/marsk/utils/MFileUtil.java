package com.marquez.marsk.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MFileUtil {
	
	public static String encoding = "UTF-8";
	
	public static String[] readFiles(File file) throws IOException {
		String text = readFile(file);
		String[] list = text.split("\r\n");
		return list;
	}
	
	public static String readFile(File file) throws IOException {
		FileInputStream fileInput = new FileInputStream(file); 
		InputStreamReader inputReader = new InputStreamReader(fileInput, encoding); 
		BufferedReader bufferedTextFileReader = new BufferedReader(inputReader);
		StringBuilder contentReceiver = new StringBuilder();
		char[] buf = new char[1024];
		while (bufferedTextFileReader.read(buf) > 0) {
			contentReceiver.append(buf);
		}
		bufferedTextFileReader.close();
		return contentReceiver.toString();
	}
	
	public static void addFile(File file, String text) throws IOException {
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter writer = new PrintWriter(bufferedWriter, true);
		StringBuilder sb = new StringBuilder();
		sb.append(text).append("\r\n");
		writer.write(sb.toString());
		writer.close();
	}
	
	public static void writeFile(File file, String[] text) throws IOException {
		StringBuilder sb = new StringBuilder();
		for(String s : text) {
			if(s == null) continue;
			sb.append(s).append("\r\n");
		}
		writeFile(file, sb.toString());
	}
	
	public static void writeFile(File file, String text) throws IOException {
		FileOutputStream fileOutput = new FileOutputStream(file);
		OutputStreamWriter outputWriter = new OutputStreamWriter(fileOutput, encoding);
		BufferedWriter output = new BufferedWriter(outputWriter);
		output.write(text);
		output.flush();
		output.close();
	}
}
