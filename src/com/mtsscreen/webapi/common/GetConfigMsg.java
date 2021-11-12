package com.mtsscreen.webapi.common;

/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * @Title: GetConfigMsg.java 
 * @Package com.bis.mtsSnder.common 
 * @author 杨玉�?  
 * @date 2016�?4�?11�? 上午11:48:28 
 * @version V1.0   
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @ClassName: GetConfigMsg
 * @Description: 取得log信息的共�?
 * @author 杨玉�?
 * @date 2016�?4�?11�? 上午11:48:28
 * @version 1.0
 */
public class GetConfigMsg
{
    private static Properties language = null;

    private static GetConfigMsg getConfigMsg = null;
    
    public static Logger logger = Logger.getLogger(GetConfigMsg.class);

    public static Properties config = null;
    
    private static String MTSSCREEN = null;
    
    /**
     * 取得MTSHOME
     * 
     * @return
     */
    public String getMTSHOME() {
    	return MTSSCREEN;
    }
    
    public GetConfigMsg()
    {

    	MTSSCREEN = System.getenv("MTSS_SCREEN");
    	if(MTSSCREEN == null) {
    		logger.error("[E0000][MTSS_HOME does not exist or error]");
    	}

        String configPath = "";
        if ("1".equals(System.getenv("MTSS_DEBUG"))) {
            configPath = "C:\\work\\workspace\\MtsScreenWebApi\\config/config.properties";
        } else {
            configPath = MTSSCREEN + "/conf/MtsScreenWebApi/config.properties";
        }
        File fileInfo = new File(configPath);
        InputStreamReader reader = null;
        config = new Properties();
        try
        {
            reader = new InputStreamReader(new FileInputStream(fileInfo),
                    "utf-8");
            config.load(reader);
            reader.close();

        }
        catch(IOException e)
        {
            logger.error("Exception" + e);
            logger.error("[E0000][config.properties does not exist or error]");
            System.exit(0);
        }
    }

    public String getMsgvalue(String key)
    {
        String msg = objToStr(language.get(key));

        return msg;
    }

    /**
     * 资源文件信息取得
     * 
     * @param key
     *            信息KEY
     * @param params
     *            埋字
     * 
     * @return 信息
     */
    public String getMsgvalue(String key, Object[] params)
    {
        String msg = objToStr(language.get(key));

        MessageFormat mf = new MessageFormat(msg);

        return mf.format(params);
    }

    /**
     * 
     * 
     * @Title: getMsgvalueAndPar
     * @Description: msgId+msg
     * @param @param key
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public String getMsgvalueAndPar(String key)
    {
        String msg = objToStr(language.get(key));

        msg = "[" + key + "]" + "[" + msg + "]";

        return msg;
    }

    /**
     * 
     * 
     * @Title: getMsgvalueAndPar
     * @Description: msgId+msg+parameter
     * @param @param key
     * @param @param Par
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public String getMsgvalueAndPar(String key, String Par)
    {
        String msg = objToStr(language.get(key));

        msg = "[" + key + "]" + "[" + msg + "]" + "[" + Par + "]";

        return msg;
    }

    /**
     * 
     * 
     * @Title: getConfigvalueAndPar
     * @Description: 取得config文件的�??
     * @param @param key
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public String getConfigvalueAndPar(String key)
    {
        String msg = objToStr(config.get(key));

        return msg;
    }

    /**
     * Object对象向文字列变更�?
     * 
     * @param obj
     *            Object对象
     * @return 向文�?
     */
    public static String objToStr(Object obj)
    {
        if (obj == null)
        {
            return "";
        }
        else
        {
            return String.valueOf(obj);
        }
    }
    
    public static synchronized GetConfigMsg getInstance()
    {

        if (getConfigMsg == null)
            getConfigMsg = new GetConfigMsg();
        return getConfigMsg;
    }
}
