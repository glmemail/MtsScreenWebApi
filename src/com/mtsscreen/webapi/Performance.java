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
import com.mtsscreen.webapi.common.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/getperformance")
public class Performance extends HttpServlet{

    public static Logger logger = Logger.getLogger(Performance.class);
    
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = new JSONObject();
		String instanceId = request.getParameter("instanceId");
		SQL sql = new SQL();
        Map parm = new HashMap();
		try {
			if (!StringUtil.isEmpty(instanceId)) {
				parm.put("instanceId", instanceId);
			} else {
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
				PrintWriter out = response.getWriter();
				result.put("errcode", "108");
				result.put("errmsg", "instanceId is Empty!");
				logger.error("errcode : " + "108");
				logger.error("errmsg : " + "instanceId is Empty!");
				out.print(result);
				out.flush();
				out.close();
				return;
			}
			JSONObject performance_json = sql.select_performance(parm);
			if (performance_json == null) {
				response.setContentType("text/html;charset=UTF-8");
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
				PrintWriter out = response.getWriter();
				result.put("errcode", "109");
				result.put("errmsg", "No data obtained!");
				logger.error("errcode : " + "109");
				logger.error("errmsg : " + "No data obtained!");
				out.print(result);
				out.flush();
				out.close();
				return;
			}
			JSONArray performance_json_data = performance_json.getJSONArray("data");
			JSONObject performance = null;
			for (int i = 0; i < performance_json_data.size(); i++) {
				performance = new JSONObject();
				JSONObject performance_json_data_json = (JSONObject) performance_json_data.get(i);
				String type = performance_json_data_json.getString("type");
				String state = performance_json_data_json.getString("state");
				String value = performance_json_data_json.getString("value");
				String time = performance_json_data_json.getString("time");
				String updated_at = performance_json_data_json.getString("updated_at");
				JSONArray result_jsonarr = (JSONArray) result.get(type);
				;
				if (result_jsonarr == null) {
					result_jsonarr = new JSONArray();
					JSONObject json = new JSONObject();
					if ("IO".equals(type)) {
						String[] io_value = value.split("/");
						json.put("in_value", io_value[0]);
						json.put("out_value", io_value[1]);
					} else if ("DISK".equals(type)) {
						String[] disk_value = value.split("/");
						json.put("free_value", disk_value[0]);
						json.put("used_value", disk_value[1]);
					} else {
						json.put("value", value);
					}
					json.put("time", time);
					result_jsonarr.add(json);
				} else {
					JSONObject json = new JSONObject();
					if ("IO".equals(type)) {
						String[] io_value = value.split("/");
						json.put("in_value", io_value[0]);
						json.put("out_value", io_value[1]);
					} else if ("DISK".equals(type)) {
						String[] disk_value = value.split("/");
						json.put("free_value", disk_value[0]);
						json.put("used_value", disk_value[1]);
					} else {
						json.put("value", value);
					}
					json.put("time", time);
					result_jsonarr.add(json);
				}

				performance.put("updated_at", updated_at);
				result.put(type + "_state", state);
				result.put(type, result_jsonarr);
			}
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			PrintWriter out = response.getWriter();
			result.put("errcode", "0");
			result.put("errmsg", "ok");
//			logger.info("result : " + result.toString());
	        Iterator iterator = result.keys();
			while(iterator.hasNext()){
		        String key = (String) iterator.next();
		        Object value = result.get(key);
		        if (key.contains("state") && "1".equals(value)) {
    	        	Notification notice = new Notification();
    	            parm = new HashMap();
    	    		parm.put("instanceId", "instanceId");
    	            JSONObject ecs_tbl_result = sql.select_ecs(parm);
    	            JSONArray  ecs_tbl_result_data = ecs_tbl_result.getJSONArray("data");
	                String ip = "";
    	            for (int i = 0;i <  ecs_tbl_result_data.size();i++) {
    	                JSONObject ecs_tbl_result_data_json = (JSONObject)  ecs_tbl_result_data.get(i);
    	                String public_ip = ecs_tbl_result_data_json.getString("public_ip");
    	                String lan_ip = ecs_tbl_result_data_json.getString("lan_ip");
    	                if (!StringUtil.isEmpty(public_ip)) {
    	                	ip = public_ip;
    	                } else {
    	                	ip = lan_ip;
    	                }
    	            }
    	        	String content = "服务器性能发生异常![" + ip + "][" + key.replace("state", "").replace("_", "") + "]";
    				logger.warn(content);
    	        	notice.sendNotice(content);
		        }
			}
			out.print(result);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("Exception : " + e.toString());
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
