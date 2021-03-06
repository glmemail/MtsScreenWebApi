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
@WebServlet("/getfluentd")
public class Fluentd extends HttpServlet{
	

    public static Logger logger = Logger.getLogger(Fluentd.class);
	
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();;
		SQL sql = new SQL();
        Map parm = new HashMap();
        JSONObject dbresult = sql.select_Fluentd(parm);
        JSONArray data = dbresult.getJSONArray("data");
        for (int i = 0;i < data.size();i++) {
            JSONObject jsonObject = (JSONObject) data.get(i);
            String fluentdid = jsonObject.getString("fluentdid");
            String fluentdkey = jsonObject.getString("fluentdkey");
            String name = jsonObject.getString("name");
            String in = jsonObject.getString("in");
            String out = jsonObject.getString("out");
            String type = jsonObject.getString("type");

            JSONArray sub_json = (JSONArray) result.get(type);
            String updated_at = jsonObject.getString("updated_at");
            if (sub_json != null) {
            	JSONObject json = new JSONObject();
            	json.put("id", fluentdkey);
            	json.put("name", name);
            	json.put("io", in + "/" + out);
            	json.put("updated_at", updated_at);
            	sub_json.add(json);
            } else {
            	JSONObject json = new JSONObject();
            	sub_json = new JSONArray();
            	json.put("id", fluentdkey);
            	json.put("name", name);
            	json.put("io", in + "/" + out);
            	json.put("updated_at", updated_at);
            	sub_json.add(json);
            }
            result.put(type, sub_json);
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
