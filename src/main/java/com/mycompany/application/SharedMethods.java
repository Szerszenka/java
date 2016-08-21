
package com.mycompany.application;

import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SharedMethods {
    
    public String checkUndefinded(String param) { 
    String result = param;
    if(param == null || "null".equals(param) || "".equals(param) || " ".equals(param)
            || "0".equals(param)) {
        result = "(undefined)";
    }
    return result;
   } 
    
    public String checkUndefinded(Date param) { 
        String result = null;
        if(param != null) { 
            result = new SimpleDateFormat("yyyy-MM-dd").format(param).toString();
        } else {
            if(param == null || "null".equals(param) || "".equals(param) || " ".equals(param)) {
                result = "(undefined)";
            }
        }
        return result;
   } 
    
    /**
    * Putting alert to display and reloading parent window
    *
    * @param out PrintWrite where the alert should be displayed
    * @param alert body of alert, text to display
    */
    public void putAlert(PrintWriter out, String alert) {
        out.println("<script type=\"text/javascript\">");
        out.println("alert('" + alert + "');");
        out.println("window.opener.location.reload()");
        out.println("window.close();");
        out.println("</script>");
    }
    /**
    * Every text have to be converted before adding to DB.
    * If text is in some form of null 
    *
    * @param check the string to check
    * @return neither string "null" or converted version of string
    */
    public String isNull(String check) {
        String full;
        if (check == null || "".equals(check) || "null".equals(check) 
                || "(undefined)".equals(check) || "0".equals(check))  {
            full = "null";
        } else {
            full = "'" + check + "'";
        }
        return full;
    }
    
    /**
     * @param connectionURL
     * @param login
     * @param password
     * @return Connection with database
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public Connection databaseConnection(String connectionURL, String login, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver"); 
        Connection conn = DriverManager.getConnection(connectionURL, login, password);
        return conn;
    }
    
}
