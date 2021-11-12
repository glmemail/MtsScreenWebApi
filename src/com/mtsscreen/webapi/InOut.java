package com.mtsscreen.webapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.mtsscreen.webapi.common.GetConfigMsg;
import com.mtsscreen.webapi.common.SQL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/getInOut")
public class InOut extends HttpServlet{
	
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();

    public static Logger logger = Logger.getLogger(InOut.class);
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();;
		SQL sql = new SQL();
        Map parm = new HashMap();
        JSONObject state_result = sql.select_InOut(parm);
        JSONArray data = state_result.getJSONArray("data");
        JSONObject inout = null;
        for (int i = 0;i < data.size();i++) {
            inout = new JSONObject();
            JSONObject jsonObject = (JSONObject) data.get(i);
            String key = jsonObject.getString("key");
            String name = jsonObject.getString("name");
            JSONObject sub_json = (JSONObject) result.get(key);
            String state = jsonObject.getString("state");
            String value = jsonObject.getString("value");
            String updated_at = jsonObject.getString("updated_at");
            if (sub_json != null) {
            	JSONObject json = new JSONObject(); 
            	json.put("state", state);
            	json.put("count", value);
            	json.put("updated_at", updated_at);
            	sub_json.put(name, json);
            	inout.putAll(sub_json);
            } else {
            	JSONObject json = new JSONObject(); 
            	sub_json = new JSONObject(); 
            	json.put("state", state);
            	json.put("count", value);
            	json.put("updated_at", updated_at);
            	sub_json.put(name, json);
                inout.putAll(sub_json);
            }
            result.put(key, inout);
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
        out.print(result); 
        out.flush();
        out.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
