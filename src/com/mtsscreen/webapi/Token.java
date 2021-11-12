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
import javax.servlet.http.HttpSession;

import com.mtsscreen.webapi.common.GetConfigMsg;
import com.mtsscreen.webapi.common.JwtUtil;
import com.mtsscreen.webapi.common.SQL;
import com.mtsscreen.webapi.common.StringUtil;

import net.sf.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet("/gettoken")
public class Token extends HttpServlet{
	
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject result = null;
		String id = request.getParameter("id");
		String secret = request.getParameter("secret");
		//id空验证
        if (StringUtil.isEmpty(id)) {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
            PrintWriter out = response.getWriter();
            result = new JSONObject();
            result.put("errcode", "101");
            result.put("errmsg", "id is empty!");
            out.print(result.toString()); 
            out.flush();
            out.close();
            return;
        }
        //secret空验证
        if (StringUtil.isEmpty(secret)) {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
            PrintWriter out = response.getWriter();
            result = new JSONObject();
            result.put("errcode", "102");
            result.put("errmsg", "secret is empty!");
            out.print(result.toString()); 
            out.flush();
            out.close();
            return;
        }
        //id，secret正确性
        SQL sql = new SQL();
        Map parm = new HashMap();
        parm.put("id", id);
        parm.put("secret", secret);
        JSONObject user_json = sql.select_screen_user(parm);
        if (user_json == null) {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
            PrintWriter out = response.getWriter();
            result = new JSONObject();
            result.put("errcode", "105");
            result.put("errmsg", "id or secret authentication failed!");
            out.print(result.toString()); 
            out.flush();
            out.close();
            return;
        }
        Map<String, Object> claimMaps = new HashMap<String, Object>();
		String secondTimeOutStr = getConfigMsg.getConfigvalueAndPar("secondTimeOut");
		int secondTimeOut = 0;
		if (!StringUtil.isEmpty(secondTimeOutStr)) {
			secondTimeOut = Integer.valueOf(secondTimeOutStr);
		} else {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
            PrintWriter out = response.getWriter();
            result = new JSONObject();
            result.put("errcode", "106");
            result.put("errmsg", "secondtimeout is not configured!");
            out.print(result.toString()); 
            out.flush();
            out.close();
            return;
		}
        String token = JwtUtil.getTokenByJson(claimMaps, secret, secondTimeOut);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
        PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(secondTimeOut);
		session.setAttribute("token", token);
        result = new JSONObject();
        result.put("errcode", "0");
        result.put("errmsg", "ok");
        result.put("access_token", token);
        result.put("expires_in", secondTimeOut);
        out.print(result); 
        out.flush();
        out.close();
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}
}
