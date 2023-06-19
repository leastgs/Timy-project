package Timy.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManagementServer {

    String dbUrl = "jdbc:mysql://127.0.0.1:3306/TimyDB";
//  String dbUrl = "jdbc:mysql://172.16.27.99:3306/TimyDB?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    String dbUser = "ldgsmacbook";
    String dbPwd = "timyDB1234!!";
    String dbDriver = "com.mysql.cj.jdbc.Driver";

    String idData;
    String nicknameData;
    String passwordData;

    Connection dbCon = null;

    public DataBaseManagementServer(String idData){
        this.idData = idData;
    }
    public DataBaseManagementServer(String idData, String passwordData){
        this.idData = idData;
        this.passwordData = passwordData;
    }
    public DataBaseManagementServer(String nicknameData, String idData, String passwordData){
        this.nicknameData = nicknameData;
        this.idData = idData;
        this.passwordData = passwordData;
    }

    private void DBConnection(){ // DB 연결 메소드

        Connection connection = null;

        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            dbCon = connection;
            System.out.println("DB Connection success");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println("DB Connection fail");
            e.printStackTrace();
        }
    }
    public void DBInsertUserData() throws SQLException { // DB에 id,nickname,password 입력 메소드

        DBConnection(); // 서버연결

        String sql = "INSERT INTO DBusers (username,userid,password) VALUES (?,?,?)";
        PreparedStatement pstmt = dbCon.prepareStatement(sql);
        pstmt.setString(1, nicknameData);
        pstmt.setString(2, idData);
        pstmt.setString(3, passwordData);
        pstmt.executeUpdate();

    }
    public boolean DBDuplicateIdCheck() throws SQLException {

        DBConnection();
        List<String> userIdCheckList = new ArrayList<>();
        boolean idCheck = false;

        String sql = "SELECT userid FROM DBusers";
        PreparedStatement pstmt = dbCon.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String userid = rs.getString("userid");
            userIdCheckList.add(userid);
        }

        if (userIdCheckList.contains(idData)) {
            idCheck = true;
            System.out.printf("true");
        }
        else{
            System.out.printf("false");
        }

        return idCheck;
    }
    public Boolean DBloginCheck() throws SQLException {
        DBConnection();

        String sql = "SELECT * FROM DBusers WHERE userid = ? AND password = ?";
        PreparedStatement pstmt = dbCon.prepareStatement(sql);

        pstmt.setString(1, idData);
        pstmt.setString(2, passwordData);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return true; // id 비밀번호 일치
        }
        else{
            return false;
        }
    }
    public Boolean DBCheckData(){

        Boolean idCheck = false;
        DBConnection(); // 서버연결


        return idCheck;
    }

    public static void main(String[] args) throws SQLException {
        DataBaseManagementServer DB = new DataBaseManagementServer("wbwb","asdd","nickd");
        DB.DBDuplicateIdCheck();
    }



}
