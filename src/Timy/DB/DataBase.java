package Timy.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataBase {

    // DB Driver
    String dbDriver = "com.mysql.cj.jdbc.Driver";

    // DB URL
    // IP:PORT/스키마 
    private final static String dbUrl = "jdbc:mysql://127.0.0.1:3306/TimyDB";
    // DB ID/PW
    private final static String dbUser = "ldgsmacbook";
    private final static String dbPassword = "timyDB1234!!";

    Connection dbconn = null;
    public void dbConnection()
    {
        Connection connection = null;

        try
        {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            dbconn = connection;

            System.out.println("DB Connection 성공. ");

        }
        catch (SQLException e)
        {
            System.out.println("DB Connection 실패 (SQLException)");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("DB Connection 실패 (ClassNotFoundException)");
            e.printStackTrace();
        }
        if(dbconn!= null)
            try {
                dbconn.close();
                System.out.println("DB Close 성공.");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public void dbClose()
    {
        try
        {
            if(dbconn != null)
            {
                dbconn.close();
                dbconn = null;
                System.out.println("DB Close 성공.");
            }
        }
        catch (SQLException e)
        {
            System.out.println("DB Close 실패.");
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


}