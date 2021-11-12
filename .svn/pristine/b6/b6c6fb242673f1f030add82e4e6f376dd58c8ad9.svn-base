package com.mtsscreen.webapi.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class Shell {
	/**
     * 运行shell脚本
     * @param shell �?要运行的shell脚本
     */
    public static void execShell(String shell){
        try {
        	Process process = null;
        	List<String> processResult = new ArrayList<String>();
        	process = Runtime.getRuntime().exec(shell);
        	BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        	String line = "";
        	while((line = input.readLine()) != null){
        		processResult.add(line);
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 运行shell脚本 new String[]方式
     * @param shell �?要运行的shell脚本
     */
    public static void execShellBin(String shell){
        try {
            Process process = null;
        	List<String> processResult = new ArrayList<String>();
        	process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell},null,null);
        	BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
        	String line = "";
        	while((line = input.readLine()) != null){
        		processResult.add(line);
        	}
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,�?定要按new String[]{"/bin/sh","-c",shStr}�?,才可以获得流
     * 
     * @param shStr
     *            �?要执行的shell
     * @return
     */
    public static List<String> runShell(String shStr) {
        List<String> strList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr},null,null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null){
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }
}
