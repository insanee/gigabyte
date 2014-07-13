/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package by.insane.gigabyte;

import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author insane
 */
public class DataBaseConnection implements Serializable{
    private static String url = "jdbc:mysql://localhost:3306/GigaByte";
    private static String user = "root";
    private static String password = "111111";
    private Connection connection;
    private Statement statement;
    private static DataBaseConnection instance = null;
    

    private DataBaseConnection(String url, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private DataBaseConnection(){
    }
    public static synchronized DataBaseConnection getInstance(){
        if(instance == null){
            instance = new DataBaseConnection(url, user, password);
        }
        return instance;
    }
    
    @Override
    protected void  finalize() throws Throwable{
        super.finalize();
        statement.close();
        connection.close();
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
    
}
