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
@WebServlet("/getnotice")
public class Notice extends HttpServlet{

    public static Logger logger = Logger.getLogger(Notice.class);
    
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	
	@SuppressWarnings("rawtypes")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		SQL sql = new SQL();
        Map parm = new HashMap();
        JSONObject notice_tbl_json = sql.select_Notice(parm);
        JSONArray data = notice_tbl_json.getJSONArray("data");
        for (int i = 0;i < data.size();i++) {
            JSONObject jsonObject = (JSONObject) data.get(i);
            String key = jsonObject.getString("key");
            String period = jsonObject.getString("period");
            String count = jsonObject.getString("count");
            JSONObject result_period = (JSONObject) result.get(period);
            if (result_period == null) {
            	result_period =  new JSONObject();
            	result_period.put(key, count);
            } else {
            	result_period.put(key, count);
            }
            result.put(period, result_period);
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
