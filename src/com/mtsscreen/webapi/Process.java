package com.mtsscreen.webapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mtsscreen.webapi.common.GetConfigMsg;
import com.mtsscreen.webapi.common.SQL;
import com.mtsscreen.webapi.common.SshUtil;
import com.mtsscreen.webapi.common.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/getprocess")
public class Process extends HttpServlet{

    public static Logger logger = Logger.getLogger(Process.class);
    
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();;
		SQL sql = new SQL();
//		String shell = "";
//		Shell.runShell("");
        Map parm = new HashMap();
		parm.put("type", "LT1");
        JSONObject ecs_tbl_result = sql.select_ecs(parm);
        JSONArray  ecs_tbl_result_data = ecs_tbl_result.getJSONArray("data");
        for (int i = 0;i <  ecs_tbl_result_data.size();i++) {
            JSONObject ecs_tbl_result_data_json = (JSONObject)  ecs_tbl_result_data.get(i);
            String instanceid = ecs_tbl_result_data_json.getString("instanceid");
            String public_ip = ecs_tbl_result_data_json.getString("public_ip");
            String lan_ip = ecs_tbl_result_data_json.getString("lan_ip");
            String ip = "";
            if (!StringUtil.isEmpty(lan_ip)) {
            	ip = lan_ip;
            } else {
            	ip = public_ip;
            }
            parm = new HashMap();
            parm.put("ip", ip);
            parm.put("keyname", "process");
            JSONObject shell_tbl_result = sql.select_shell(parm);
            if (shell_tbl_result == null) {
    			continue;
            }
            JSONArray  shell_tbl_result_data = shell_tbl_result.getJSONArray("data");
            for (int ii = 0;ii <  shell_tbl_result_data.size();ii++) {
    			JSONObject process_json = new JSONObject();
                JSONObject shell_tbl_result_data_json = (JSONObject)  shell_tbl_result_data.get(ii);
                String cmd = shell_tbl_result_data_json.getString("cmd");
                String shell_ip = shell_tbl_result_data_json.getString("ip");
                String username = shell_tbl_result_data_json.getString("username");
                String password = shell_tbl_result_data_json.getString("password");
                String type = shell_tbl_result_data_json.getString("type");
                String shell_result = SshUtil.execute_shell(cmd, shell_ip, username, password);
                if (StringUtil.isEmpty(shell_result)) {
	    			process_json.put("state", "1");
	    			process_json.put("processId", "");
	    			result.put(type, process_json);
	    			continue;
                }
                String[]shell_result_lines = shell_result.split(System.getProperty("line.separator"));
                StringBuilder pid = new StringBuilder();
                for(String shell_result_line : shell_result_lines){
                	if (!StringUtil.isEmpty(shell_result_line) && isInteger(shell_result_line)) {
            			pid.append(shell_result_line).append(" ");
                	} else {
                        response.setContentType("text/html;charset=UTF-8");
                        response.setHeader("Access-Control-Allow-Origin", "*");
                        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
                        response.setHeader("Access-Control-Allow-Credentials", "true");
                        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
                        PrintWriter out = response.getWriter();
                        result.put("errcode", "1");
                        result.put("errmsg", "shell执行失败！获取pid失败！" + shell_result);
                        out.print(result); 
                        out.flush();
                        out.close();
                        return;
                	}
                }
                if (!StringUtil.isEmpty(pid.toString())) {
	    			process_json.put("state", "0");
	    			process_json.put("processId", pid.toString());
	    			result.put(type, process_json);
                } else {
	    			process_json.put("state", "1");
	    			process_json.put("processId", pid.toString());
	    			result.put(type, process_json);
                }
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        PrintWriter out = response.getWriter();
        result.put("errcode", "0");
        result.put("errmsg", "ok");
        logger.info("result : " + result.toString());
        Iterator iterator = result.keys();
		while(iterator.hasNext()){
	        String key = (String) iterator.next();
	        Object value = result.get(key);
	        if(value instanceof JSONObject) {
	        	JSONObject jsonObject = (JSONObject) value;
	            Iterator iterator_v = jsonObject.keys();
	    		while(iterator_v.hasNext()){
	    	        String key_v= (String) iterator_v.next();
	    	        String value_v = jsonObject.getString(key_v);
	    	        if ("state".equals(key_v) && "1".equals(value_v)) {
	    	        	Notification notice = new Notification();
	    	        	notice.sendNotice("进程发生异常![" + key + "]");
	    	        }
	    		}
	        	
	        }
		}
        out.print(result); 
        out.flush();
        out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
	
	/*
	  * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	*/

	  public static boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	  }
}
