package com.mtsscreen.webapi.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


import sun.misc.BASE64Encoder;
import sun.net.www.protocol.http.HttpURLConnection;

public class CommonUtil {
    public static Logger logger = Logger.getLogger(CommonUtil.class);

    public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();

    static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            return true;
        }
    };
    /**
     * 发�?�https请求
     * 
     * @param requestUrl
     *            请求地址
     * @param requestMethod
     *            请求方式（GET、POST�??
     * @param outputStr
     *            提交的数�??
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性�??)
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        String result = null;
        try {
            
            // 创建SSLContext对象，并使用我们指定的信任管理器初始�??
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn;
            HttpURLConnection connHttp;
            if (url.getProtocol().toLowerCase().equals("https")) {
                conn = (HttpsURLConnection) url.openConnection(); 
                HttpsURLConnection.setDefaultHostnameVerifier(hv);
                conn.setSSLSocketFactory(ssf);

                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                
                // 设置请求方式（GET/POST�??
                conn.setRequestMethod(requestMethod);
                String username = "admin";
                String password = "admin";
                String input = username + ":" + password;
                String encoding = new BASE64Encoder().encode(input.getBytes());
                conn.setRequestProperty ("Authorization", "Basic " + encoding);
                // 当outputStr不为null时向输出流写数据
                if (null != outputStr) {
                    OutputStream outputStream = conn.getOutputStream();
                    // 注意编码格式
                    outputStream.write(outputStr.getBytes("UTF-8"));
                    outputStream.close();
                }
                
                // 从输入流读取返回内容
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }

                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                conn.disconnect();
                result = buffer.toString();
            } else {

                connHttp = (HttpURLConnection) url.openConnection(); 
//                HttpURLConnection.setDefaultHostnameVerifier(hv);
//                connHttp.setSSLSocketFactory(ssf);

                connHttp.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                connHttp.setDoOutput(true);
                connHttp.setDoInput(true);
                connHttp.setUseCaches(false);

                connHttp.setConnectTimeout(20000);  
                connHttp.setReadTimeout(300000); 
                
                // 设置请求方式（GET/POST�??
                connHttp.setRequestMethod(requestMethod);
                String username = "admin";
                String password = "admin";
                String input = username + ":" + password;
                String encoding = new BASE64Encoder().encode(input.getBytes());
                connHttp.setRequestProperty("Authorization", "Basic " + encoding);
                connHttp.setRequestProperty("accept","*/*");
                connHttp.setRequestProperty("user","admin");
                connHttp.setRequestProperty("pass","admin");
                // 当outputStr不为null时向输出流写数据
                if (null != outputStr) {
                    OutputStream outputStream = connHttp.getOutputStream();
                    // 注意编码格式
                    outputStream.write(outputStr.getBytes("UTF-8"));
                    outputStream.close();
                }

                // 从输入流读取返回内容
                InputStream inputStream = connHttp.getInputStream();
//                OutputStream os = connHttp.getOutputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }

                // 释放资源
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                connHttp.disconnect();
                result = buffer.toString();
            }
        } catch (ConnectException e1) {
//            LoggerRun.FluentsId = LoggerRun.FluentsId.replace("_storm_info", "_storm_error");
            logger.error("Exception=" + e1);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0029"));
        } catch (IOException e1) {
//            LoggerRun.FluentsId = LoggerRun.FluentsId.replace("_storm_info", "_storm_error");
        	logger.error("Exception=" + e1);
        	logger.error(getConfigMsg.getMsgvalueAndPar("E0030"));
        } catch (KeyManagementException e1) {
            logger.error("Exception=" + e1);
            logger.error(getConfigMsg.getMsgvalueAndPar("E0031"));
        } catch (Exception e) {
        	logger.error("Exception=" + e);
        	logger.error(getConfigMsg.getMsgvalueAndPar("E0032"));
        }
        return result;
    }
    
    /**
     * @param url
     * @param jsonstr
     * @return
     */
    public static String executeWebAPI(String url, String jsonstr) {
        String t = null;
        try {
            // 发起POST请求获取凭证
            String result = httpsRequest(url, "POST", jsonstr);
            
            if (result != null) {
                t = result.toString();
            }
        } catch (Exception e) {
        	logger.error("Exception=" + e);
        }
        return t;
    }
    

    public static void logout(String key, String jsonstr) {

        FileWriter fw1 = null;
        try {
            // 如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f1 = new File("/opt/storm/logs/storm.log");
            fw1 = new FileWriter(f1, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(new Date());
        PrintWriter pw1 = new PrintWriter(fw1);
        pw1.println("["+ time +"]["+ key +"][" + jsonstr + "]");
        pw1.flush();
        try {
            fw1.flush();
            pw1.close();
            fw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getHostNameForLiunx() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "UnknownHost";
        }
    }
    
    /**
     * 使用FileReader类读文本文件
     */
    public static String readTxtFile(String filePath) {
        String lineTxt = null;
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                lineTxt = bufferedReader.readLine();
                read.close();
            } else {
                System.out.println("找不到指定的文件");
                logger.info("readTxtFile error," + "找不到指定的文件.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("readTxtFile error,读取文件内容出错." + e.toString());
        }
        return lineTxt;
    }
    /**
     * 写入TXT，覆盖原内容
     * @param content
     * @param fileName
     * @return
     * @throws Exception
     */
    public static boolean writeTxtFile(String content,String filePath){
        boolean flag=false;
        try {
            File file = new File(filePath);
            RandomAccessFile mm=null;
            FileOutputStream fileOutputStream=null;
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes("utf-8"));
            fileOutputStream.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
