package Timy.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManagement {

    String dbUrl = "jdbc:mysql://127.0.0.1:3306/TimyDB";
    String dbUser = "ldgsmacbook";
    String dbPwd = "timyDB1234!!";
    String dbDriver = "com.mysql.cj.jdbc.Driver";

    String idData;
    String nicknameData;
    String passwordData;

    Connection dbCon = null;

    public DataBaseManagement(String idData){
        this.idData = idData;
    }
    public DataBaseManagement(String idData, String passwordData){
        this.idData = idData;
        this.passwordData = passwordData;
    }
    public DataBaseManagement(String nicknameData,String idData, String passwordData){
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

        String sql = "INSERT INTO users (username,userid,password) VALUES (?,?,?)";
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

        String sql = "SELECT userid FROM users";
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

        String sql = "SELECT * FROM users WHERE userid = ? AND password = ?";
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
        DataBaseManagement DB = new DataBaseManagement("wbwb","asdd","nickd");
        DB.DBDuplicateIdCheck();
    }



}
