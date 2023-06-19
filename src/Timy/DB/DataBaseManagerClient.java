package Timy.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataBaseManagerClient {
    String dbDriver = "com.mysql.cj.jdbc.Driver";
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://192.168.50.79:3306/TimyDB?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String dbUser = "myuser";
        String dbPwd = "timyDB1234!!";
        String dbDriver = "com.mysql.cj.jdbc.Driver";

        Connection dbCon = null;
        Connection connection = null;

        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            dbCon = connection;
            System.out.println("DB Connection success");

            String sql = "INSERT INTO DBusers (username,userid,password) VALUES (?,?,?)";
            PreparedStatement pstmt = dbCon.prepareStatement(sql);
            pstmt.setString(1, "ddd");
            pstmt.setString(2, "wow");
            pstmt.setString(3, "wowowowow");
            pstmt.executeUpdate();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("DB Connection fail");
            e.printStackTrace();
        }

    }


}
