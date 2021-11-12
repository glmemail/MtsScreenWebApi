package com.mtsscreen.webapi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.mtsscreen.webapi.common.CommonUtil;
import com.mtsscreen.webapi.common.GetConfigMsg;

import net.sf.json.JSONObject;

public class Notification {
	
	public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();
	
	/**
	 * @param content
	 * @param json
	 * @return
	 */
	public String getPersons(String content, JSONObject json) {
		String result = null;
		
        Iterator iterator = json.keys();
		while(iterator.hasNext()){
	        String key = (String) iterator.next();
	        String value = json.getString(key);
	        if ("state".equals(key) && "1".equals(value)) {
	        	sendNotice(content);
	        	result = "1"; 
	        	break;
	        }
	        
		}
		return result;
	}
	
	/**
	 * @param content
	 * @return
	 */
	public String sendNotice(String content) {
		String r = null;
		String noticeUrl = getConfigMsg.getConfigvalueAndPar("noticeUrl");
		String noticeMethod = getConfigMsg.getConfigvalueAndPar("noticeMethod");
		String noticeContent = getConfigMsg.getConfigvalueAndPar("noticeContent");
		noticeContent = noticeContent.replace("MSG_BODY_REPLACE", content);
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
		noticeContent = noticeContent.replace("TIMESTAMP_REPLACE", sdf.format(date));
		r = CommonUtil.httpsRequest(noticeUrl, noticeMethod, noticeContent);
		return r;
	}
	
}
