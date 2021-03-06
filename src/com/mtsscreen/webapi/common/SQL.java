package com.mtsscreen.webapi.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SQL {


    public static Logger logger = Logger.getLogger(SQL.class);

    public static GetConfigMsg getConfigMsg = GetConfigMsg.getInstance();

    

    public JSONObject select_screen_user(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String id = (String)parm.get("id");
        	String secret = (String)parm.get("secret");
        	String token = (String)parm.get("token");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, ");
            sql.append(" secret, ");
            sql.append(" token, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM screen_user ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(id)) {
            	sql.append(" and id = '").append(id).append("'");
            }
            if (!StringUtil.isEmpty(secret)) {
                sql.append(" and secret = '").append(secret).append("'");
            }
            if (!StringUtil.isEmpty(token)) {
                sql.append(" and token = '").append(token).append("'");
            }
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("id", rs.getString("id"));
                tt.put("secret", rs.getString("secret"));
                tt.put("token", rs.getString("token"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }
    
    

    public JSONObject select_link(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String key = (String)parm.get("key");
        	String state = (String)parm.get("state");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" key, ");
            sql.append(" state, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM link ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(key)) {
            	sql.append(" and key = '").append(key).append("'");
            }
            if (!StringUtil.isEmpty(state)) {
            	sql.append(" and state = '").append(state).append("'");
            }
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("key", rs.getString("key"));
                tt.put("state", rs.getString("state"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }
    
    

    public JSONObject select_InOut(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String key = (String)parm.get("key");
        	String name = (String)parm.get("name");
        	String state = (String)parm.get("state");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" key, ");
            sql.append(" name, ");
            sql.append(" state, ");
            sql.append(" value, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM in_out ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(key)) {
            	sql.append(" and key = '").append(key).append("'");
            }
            if (!StringUtil.isEmpty(name)) {
            	sql.append(" and name = '").append(name).append("'");
            }
            if (!StringUtil.isEmpty(state)) {
            	sql.append(" and state = '").append(state).append("'");
            }
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("key", rs.getString("key"));
                tt.put("name", rs.getString("name"));
                tt.put("state", rs.getString("state"));
                tt.put("value", rs.getString("value"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }

    public JSONObject select_Fluentd(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String fluentdid = (String)parm.get("fluentdid");
        	String fluentdkey = (String)parm.get("fluentdkey");
        	String name = (String)parm.get("name");
        	String type = (String)parm.get("type");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT fluentdid, fluentdkey, \"name\", \"in\", \"out\", \"type\", created_at, updated_at");
            sql.append(" FROM fluentd ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(fluentdid)) {
            	sql.append(" and fluentdid = '").append(fluentdid).append("'");
            }
            if (!StringUtil.isEmpty(fluentdkey)) {
            	sql.append(" and fluentdkey = '").append(fluentdkey).append("'");
            }
            if (!StringUtil.isEmpty(name)) {
            	sql.append(" and name = '").append(name).append("'");
            }
            if (!StringUtil.isEmpty(type)) {
            	sql.append(" and type = '").append(type).append("'");
            }
            sql.append(" ORDER BY cast(\"in\" as int)  DESC");
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("fluentdid", rs.getString("fluentdid"));
                tt.put("fluentdkey", rs.getString("fluentdkey"));
                tt.put("name", rs.getString("name"));
                tt.put("in", rs.getString("in"));
                tt.put("out", rs.getString("out"));
                tt.put("type", rs.getString("type"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }

    public JSONObject select_Notice(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String key = (String)parm.get("key");
        	String period = (String)parm.get("period");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" key, ");
            sql.append(" period, ");
            sql.append(" count, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM notice ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(key)) {
            	sql.append(" and key = '").append(key).append("'");
            }
            if (!StringUtil.isEmpty(period)) {
            	sql.append(" and period = '").append(period).append("'");
            }
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("key", rs.getString("key"));
                tt.put("period", rs.getString("period"));
                tt.put("count", rs.getString("count"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }

    public JSONObject select_performance(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String instanceid = (String)parm.get("instanceId");
        	String type = (String)parm.get("type");
        	String state = (String)parm.get("state");
        	String value = (String)parm.get("value");
        	String time = (String)parm.get("time");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, ");
            sql.append(" instanceid, ");
            sql.append(" type, ");
            sql.append(" state, ");
            sql.append(" value, ");
            sql.append(" time, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM performance ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(instanceid)) {
            	sql.append(" and instanceid = '").append(instanceid).append("'");
            }
            if (!StringUtil.isEmpty(type)) {
            	sql.append(" and type = '").append(type).append("'");
            }
            if (!StringUtil.isEmpty(state)) {
            	sql.append(" and state = '").append(state).append("'");
            }
            if (!StringUtil.isEmpty(value)) {
            	sql.append(" and value = '").append(value).append("'");
            }
            if (!StringUtil.isEmpty(time)) {
            	sql.append(" and time = '").append(time).append("'");
            }
            sql.append(" and created_at between (now() - interval '60 Mins') and now() ");
            sql.append("ORDER BY type,id asc");
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("id", rs.getInt("id"));
                tt.put("instanceid", rs.getString("instanceid"));
                tt.put("type", rs.getString("type"));
                tt.put("state", rs.getString("state"));
                tt.put("value", rs.getString("value"));
                tt.put("time", rs.getString("time"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }
    
    public JSONObject select_ecs(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String instanceid = (String)parm.get("instanceid");
        	String public_ip = (String)parm.get("public_ip");
        	String lan_ip = (String)parm.get("lan_ip");
        	String accesskeyid = (String)parm.get("accesskeyid");
        	String accesskeysecret = (String)parm.get("accesskeysecret");
        	String type = (String)parm.get("type");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" instanceid, ");
            sql.append(" public_ip, ");
            sql.append(" lan_ip, ");
            sql.append(" accesskeyid, ");
            sql.append(" accesskeysecret, ");
            sql.append(" type, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM ecs ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(instanceid)) {
            	sql.append(" and instanceid = '").append(instanceid).append("'");
            }
            if (!StringUtil.isEmpty(public_ip)) {
            	sql.append(" and public_ip = '").append(public_ip).append("'");
            }
            if (!StringUtil.isEmpty(lan_ip)) {
            	sql.append(" and lan_ip = '").append(lan_ip).append("'");
            }
            if (!StringUtil.isEmpty(accesskeyid)) {
            	sql.append(" and accesskeyid = '").append(accesskeyid).append("'");
            }
            if (!StringUtil.isEmpty(accesskeysecret)) {
            	sql.append(" and accesskeysecret = '").append(accesskeysecret).append("'");
            }
            if (!StringUtil.isEmpty(type)) {
            	sql.append(" and type = '").append(type).append("'");
            }
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("instanceid", rs.getString("instanceid"));
                tt.put("public_ip", rs.getString("public_ip"));
                tt.put("lan_ip", rs.getString("lan_ip"));
                tt.put("accesskeyid", rs.getString("accesskeyid"));
                tt.put("accesskeysecret", rs.getString("accesskeysecret"));
                tt.put("type", rs.getString("type"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }

    public JSONObject select_shell(Map parm) {
        Connection c = null;
        Statement stmt = null;
        JSONObject r = null;
        JSONArray t = new JSONArray();
        try {
        	String cmd = (String)parm.get("cmd");
        	String ip = (String)parm.get("ip");
        	String username = (String)parm.get("username");
        	String password = (String)parm.get("password");
        	String type = (String)parm.get("type");
        	String keyname = (String)parm.get("keyname");
            c = ConnectPostgres();
            stmt = c.createStatement();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" id, ");
            sql.append(" cmd, ");
            sql.append(" ip, ");
            sql.append(" username, ");
            sql.append(" password, ");
            sql.append(" type, ");
            sql.append(" keyname, ");
            sql.append(" splitstr, ");
            sql.append(" created_at, ");
            sql.append(" updated_at ");
            sql.append(" FROM shell ");
            sql.append(" where 1 = 1 ");
            if (!StringUtil.isEmpty(cmd)) {
            	sql.append(" and cmd = '").append(cmd).append("'");
            }
            if (!StringUtil.isEmpty(ip)) {
            	sql.append(" and ip = '").append(ip).append("'");
            }
            if (!StringUtil.isEmpty(username)) {
            	sql.append(" and username = '").append(username).append("'");
            }
            if (!StringUtil.isEmpty(password)) {
            	sql.append(" and password = '").append(password).append("'");
            }
            if (!StringUtil.isEmpty(type)) {
            	sql.append(" and type = '").append(type).append("'");
            }
            if (!StringUtil.isEmpty(keyname)) {
            	sql.append(" and keyname = '").append(keyname).append("'");
            }
            ResultSet rs = stmt.executeQuery(sql.toString());
            int rowCount = 0;
            while (rs.next()) {
                JSONObject tt = new JSONObject();
                tt.put("id", rs.getInt("id"));
                tt.put("cmd", rs.getString("cmd"));
                tt.put("ip", rs.getString("ip"));
                tt.put("username", rs.getString("username"));
                tt.put("password", rs.getString("password"));
                tt.put("type", rs.getString("type"));
                tt.put("keyname", rs.getString("keyname"));
                tt.put("created_at", rs.getString("created_at"));
                tt.put("updated_at", rs.getString("updated_at"));
                t.add(tt);
                rowCount++;
            }
            if (rowCount > 0) {
                r = new JSONObject();
                r.put("data", t);
            }
            rs.close();
            stmt.close();
            c.close();
//            logger.info(getConfigMsg.getMsgvalueAndPar("I0029", "Operation done successfully"));
        } catch (Exception e) {
//            logger.error(getConfigMsg.getMsgvalueAndPar("E0055", "contactway"));
//            logger.error(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
        } finally {
            try {
                if (stmt != null) {
                	stmt.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
                logger.error(getConfigMsg.getMsgvalueAndPar("E0056", "deleteMessageDaily"));
                logger.error(e.getClass().getName() + ": " + e.toString());
            }
            
        }
        
        return r;
    }
    
    public static Connection ConnectPostgres() throws ClassNotFoundException, SQLException {
        Connection c;
        String datasource_url = getConfigMsg.getConfigvalueAndPar("datasource_url");
        String datasource_username = getConfigMsg.getConfigvalueAndPar("datasource_username");
        String datasource_password = getConfigMsg.getConfigvalueAndPar("datasource_password");
        String datasource_driver_class_name = getConfigMsg.getConfigvalueAndPar("datasource_driver_class_name");
        Class.forName(datasource_driver_class_name);
//        logger.info(datasource_url + " " + datasource_username + " " + datasource_password + " " + datasource_driver_class_name);
        c = DriverManager.getConnection(datasource_url, datasource_username, datasource_password);
        c.setAutoCommit(false);
//        logger.info("Opened database successfully");
        return c;
    }

}
