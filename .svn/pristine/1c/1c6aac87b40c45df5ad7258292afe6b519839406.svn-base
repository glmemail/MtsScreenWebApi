package com.mtsscreen.webapi.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;


public class SshUtil {
    private static String DEFAULT_CHAR_SET = "UTF-8";
    private static String tipStr = "=======================%s=======================";
    private static String splitStr = "=====================================================";
 
    /**
     * 登录主机
     * @return
     *      登录成功返回true，否则返回false
     */
    public static Connection login(String ip, String userName, String password){
        boolean isAuthenticated = false;
        Connection conn = null;
        long startTime = Calendar.getInstance().getTimeInMillis();
        try {
            conn = new Connection(ip);
            conn.connect(); // 连接主机

            isAuthenticated = conn.authenticateWithPassword(userName, password); // 认证
            if(isAuthenticated){
                System.out.println(String.format(tipStr, "认证成功"));
            } else {
                System.out.println(String.format(tipStr, "认证失败"));
            }
        } catch (IOException e) {
            System.err.println(String.format(tipStr, "登录失败"));
            e.printStackTrace();
        }
        long endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("登录用时: " + (endTime - startTime)/1000.0 + "s" + System.getProperty("line.separator") + splitStr);
        return conn;
    }
 
    /**
     * 远程执行shell脚本或�?�命�?
     * @param cmd
     *      即将执行的命�?
     * @return
     *      命令执行完后返回的结果�??
     */
    public static String execute(Connection conn, String cmd){
        String result = "";
        Session session = null;
        try {
            if(conn != null){
                session = conn.openSession();  // 打开�?个会�?
                session.execCommand(cmd);      // 执行命令
                result = processStdout(session.getStdout(), DEFAULT_CHAR_SET);

                //如果为得到标准输出为空，说明脚本执行出错�?
                if(StringUtil.isEmpty(result)){
                    System.err.println("【得到标准输出为空�?�\" + System.getProperty(\"line.separator\") + \"执行的命令如下：" + System.getProperty("line.separator") + cmd);
                    result = processStdout(session.getStderr(), DEFAULT_CHAR_SET);
                }else{
                    System.out.println("【执行命令成功�??" + System.getProperty("line.separator") + "执行的命令如下：" + System.getProperty("line.separator") + cmd);
                }
            }
        } catch (IOException e) {
            System.err.println("【执行命令失败�??" + System.getProperty("line.separator") + "执行的命令如下：" + System.getProperty("line.separator")  + cmd + System.getProperty("line.separator") + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (session != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * 解析脚本执行返回的结果集
     * @param in 输入流对�?
     * @param charset 编码
     * @return
     *       以纯文本的格式返�?
     */
    private static String processStdout(InputStream in, String charset){
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while((line = br.readLine()) != null){
                buffer.append(line + System.getProperty("line.separator"));
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("解析脚本出错�?" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("解析脚本出错�?" + e.getMessage());
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String execute_shell(String cmd, String ip, String userName, String password){
        Connection conn =  SshUtil.login(ip, userName, password);

        String result = SshUtil.execute(conn, cmd);
        return result;
    }
}