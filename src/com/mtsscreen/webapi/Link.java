package com.mtsscreen.webapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
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
@WebServlet("/getlink")
public class Link extends HttpServlet{
	
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();

    public static Logger logger = Logger.getLogger(Link.class);
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		SQL sql = new SQL();
		Map parm = new HashMap();
        JSONObject link_result = sql.select_link(parm);
        JSONArray data = link_result.getJSONArray("data");
        JSONObject link = null;
        for (int i = 0;i < data.size();i++) {
            link = new JSONObject();
            JSONObject jsonObject = (JSONObject) data.get(i);
            String key = jsonObject.getString("key");
            String state = jsonObject.getString("state");
            String updated_at = jsonObject.getString("updated_at");
            link.put("state", state);
            link.put("updated_at", updated_at);
            result.put(key, link);
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
	    	        	notice.sendNotice("\\r\\n链路发生异常![" + key + "]");
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
}
