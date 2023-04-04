package com.gerenciadordepvendas.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

public class DB {

    private static Connection connection = null;
     public static  Connection getConnection(){
         try {
             if (connection == null) {
                 Properties props = loadProperties();
                 String url = props.getProperty("dburl");
                 connection = DriverManager.getConnection(url, props);
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
         return connection;
     }
     public static  void closeConection(){
         if(connection != null){
             try{
                 connection.close();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         }
     }

    private static Properties loadProperties(){
        try (FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new DBException(e.toString());
        }

    }
}
