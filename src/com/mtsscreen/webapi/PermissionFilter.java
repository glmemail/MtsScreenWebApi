package com.mtsscreen.webapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.mtsscreen.webapi.common.GetConfigMsg;
import com.mtsscreen.webapi.common.JwtUtil;
import com.mtsscreen.webapi.common.SQL;
import com.mtsscreen.webapi.common.StringUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import net.sf.json.JSONObject;

public class PermissionFilter implements Filter {

	private static Logger logger = Logger.getLogger(PermissionFilter.class);

	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	public void init(FilterConfig config) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest rq = (HttpServletRequest) request;
		HttpServletResponse rp = (HttpServletResponse) response;
		String path = rq.getServletPath();
		
		if ("/gettoken".equals(path)) {
	        // 用户认证跳转
	        RequestDispatcher rd = rq.getRequestDispatcher(path);
	        rd.forward(rq, rp);
	        return;
		}
		JSONObject result = null;
		String id = rq.getParameter("id");
		String secret = rq.getParameter("secret");
		String token = rq.getHeader("Authorization");
		// id空验证
		if (StringUtil.isEmpty(id)) {
			rp.setContentType("text/html;charset=UTF-8");
			rp.setHeader("Access-Control-Allow-Origin", "*");
			rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "101");
			result.put("errmsg", "id is empty!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
		}

		// secret空验证
		if (StringUtil.isEmpty(secret)) {
			rp.setContentType("text/html;charset=UTF-8");
			rp.setHeader("Access-Control-Allow-Origin", "*");
			rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "102");
			result.put("errmsg", "secret is empty!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
		}
		SQL sql = new SQL();
		Map parm = new HashMap();
		parm.put("id", id);
		parm.put("secret", secret);
		JSONObject screen_user = sql.select_screen_user(parm);
		if (screen_user == null) {
			rp.setContentType("text/html;charset=UTF-8");
			rp.setHeader("Access-Control-Allow-Origin", "*");
			rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "102");
			result.put("errmsg", "id or secret is invalid!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
		}
//		HttpSession session = rq.getSession();
//		String token = (String) session.getAttribute("token");
		if (StringUtil.isEmpty(token)) {
			rp.setContentType("text/html;charset=UTF-8");
			rp.setHeader("Access-Control-Allow-Origin", "*");
			rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "103");
			result.put("errmsg", "Token is empty!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
		}
		Object object = null;
		// jwt验证
        try {
        	object = JwtUtil.getClaimsBody(token, secret);
        	if (JwtUtil.isExpiration(token, secret)) {
        		rp.setContentType("text/html;charset=UTF-8");
        		rp.setHeader("Access-Control-Allow-Origin", "*");
        		rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
    			rp.setHeader("Access-Control-Allow-Credentials", "true");
    			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
    			rp.setHeader("Access-Control-Max-Age", "1728000");
    			PrintWriter out = response.getWriter();
    			result = new JSONObject();
    			result.put("errcode", "104");
    			result.put("errmsg", "invalid token!");
    			out.print(result.toString());
    			out.flush();
    			out.close();
    			return;
        	}
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | UnsupportedJwtException e) {
        	rp.setContentType("text/html;charset=UTF-8");
			rp.setHeader("Access-Control-Allow-Origin", "*");
			rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "104");
			result.put("errmsg", "invalid token!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
        }  catch (Exception e) {
        	rp.setContentType("text/html;charset=UTF-8");
        	rp.setHeader("Access-Control-Allow-Origin", "*");
        	rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "104");
			result.put("errmsg", "invalid token!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
        }
		if (object == null) {
			rp.setContentType("text/html;charset=UTF-8");
			rp.setHeader("Access-Control-Allow-Origin", "*");
			rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			rp.setHeader("Access-Control-Allow-Credentials", "true");
			rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
			rp.setHeader("Access-Control-Max-Age", "1728000");
			PrintWriter out = response.getWriter();
			result = new JSONObject();
			result.put("errcode", "104");
			result.put("errmsg", "invalid token!");
			out.print(result.toString());
			out.flush();
			out.close();
			return;
		}
		RequestDispatcher rd = rq.getRequestDispatcher(path);
		rp.setContentType("text/html;charset=UTF-8");
		rp.setHeader("Access-Control-Allow-Origin", "*");
		rp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		rp.setHeader("Access-Control-Allow-Credentials", "true");
		rp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With");
		rp.setHeader("Access-Control-Max-Age", "1728000");
        rd.forward(rq, rp);
	}

	@Override
	public void destroy() {

	}

}
