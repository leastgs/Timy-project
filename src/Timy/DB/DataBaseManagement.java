package Timy.DB;

import Timy.Login.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManagement {

    String dbUrl = "jdbc:mysql://127.0.0.1:3306/TimyDB";
    //String dbUrl = "jdbc:mysql://192.168.50.79:3306/TimyDB?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    String dbUser = "ldgsmacbook";
    String dbPwd = "timyDB1234!!";
    String dbDriver = "com.mysql.cj.jdbc.Driver";

    String idData;
    String nicknameData;
    String passwordData;
    String location,purpose;
    int time;

    private Login login;
    String userIdCode  = Login.getUserIdCode();
    Connection dbCon = null;
    public DataBaseManagement(){}
    public DataBaseManagement(String idData){
        this.idData = idData;
    }
    public DataBaseManagement(String idData, String passwordData){
        this.idData = idData;
        this.passwordData = passwordData;
    }
    public DataBaseManagement(String nicknameData, String idData, String passwordData){
        this.nicknameData = nicknameData;
        this.idData = idData;
        this.passwordData = passwordData;
    }
    public DataBaseManagement(String location, String purpose, int time) {
        this.location = location;
        this.purpose = purpose;
        this.time = time;
    }

    private void dBConnection(){ // DB 연결 메소드

        Connection connection = null;

        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
            dbCon = connection;
            System.out.println("DB Connection success");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.err.println("DB Connection fail");
            e.printStackTrace();
        }
    }
    public void DBInsertUserData() throws SQLException { // DB에 id,nickname,password 입력 메소드

        dBConnection(); // 서버연결

        String sql = "INSERT INTO DBusers (username,userid,password) VALUES (?,?,?)";
        PreparedStatement pstmt = dbCon.prepareStatement(sql);
        pstmt.setString(1, nicknameData);
        pstmt.setString(2, idData);
        pstmt.setString(3, passwordData);
        pstmt.executeUpdate();

    }
    public boolean dbDuplicateIdCheck() throws SQLException {

        dBConnection();
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
    public Boolean dbLoginCheck() throws SQLException {

        dBConnection();

        String sql = "SELECT * FROM DBusers WHERE userid = ? AND password = ?";
        PreparedStatement pstmt = dbCon.prepareStatement(sql);

        pstmt.setString(1, idData);
        pstmt.setString(2, passwordData);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            dbCon.close();
            return true; // id 비밀번호 일치
        }
        else{
            dbCon.close();
            return false;
        }
    }
    public void addFollow(String user_id1, String user_id2) {
        try {
            dBConnection();
            Statement stmt = dbCon.createStatement();

            String checkUsersQuery = String.format("SELECT * FROM DBusers WHERE userid IN ('%s', '%s');", user_id1, user_id2);
            ResultSet userResultSet = stmt.executeQuery(checkUsersQuery);

            int counter = 0;
            while (userResultSet.next()) {
                counter++;
            }

            if (counter == 2) {
                String checkFriendQuery = String.format("SELECT * FROM DBFollows WHERE (user_id = '%s' AND friend_id = '%s') OR (user_id = '%s' AND friend_id = '%s');", user_id1, user_id2, user_id2, user_id1);
                ResultSet friendResultSet = stmt.executeQuery(checkFriendQuery);

                if (friendResultSet.next()) {
                    System.out.println("이미 친구된 사용자 ID입니다.");
                } else {
                    String insertQuery = String.format("INSERT INTO DBFollows(user_id, friend_id) VALUES ('%s', '%s');", user_id1, user_id2);
                    stmt.executeUpdate(insertQuery);

                    System.out.println("친구목록에 추가되었습니다.");
                }
            } else {
                System.out.println("입력된 사용자 ID 중 존재하지 않는 ID가 있습니다.");
            }

            dbCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getUserNameByUserId(String userId) {
        String userName = null;

        try {
            dBConnection();
            Statement stmt = dbCon.createStatement();

            // 입력한 user_id 와 일치하는 데이터 검색
            String query = String.format("SELECT username FROM DBusers WHERE userid = '%s';", userId);
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next()) {
                // 일치하는 행이 있을 경우 username 반환
                userName = resultSet.getString("username");
            } else {
                System.out.println("해당되는 User ID가 존재하지 않습니다.");
            }

            dbCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userName;
    }
    public boolean togetherFollowCheck(String user_id2) {

        String user_id1 = userIdCode;

        try {
            dBConnection();
            Statement stmt = dbCon.createStatement();

            String checkUsersQuery = String.format("SELECT * FROM DBusers WHERE userid IN ('%s', '%s');", user_id1, user_id2);
            ResultSet userResultSet = stmt.executeQuery(checkUsersQuery);

            int counter = 0;
            while (userResultSet.next()) {
                counter++;
            }
            if (counter == 2) {
                String checkFriendQuery = String.format("SELECT * FROM DBFollows WHERE (user_id = '%s' AND friend_id = '%s') OR (user_id = '%s' AND friend_id = '%s');", user_id1, user_id2, user_id2, user_id1);
                ResultSet friendResultSet = stmt.executeQuery(checkFriendQuery);

                if (friendResultSet.next()) {
                    return true;
                    //팔로우확인완료
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean addFriendTempFollowCheck(String user_id2) {

        String user_id1 = userIdCode;
        try {
            dBConnection();
            Statement stmt = dbCon.createStatement();

            String checkUsersQuery = String.format("SELECT * FROM DBusers WHERE userid IN ('%s', '%s');", user_id1, user_id2); //dbuser확인
            ResultSet userResultSet = stmt.executeQuery(checkUsersQuery);

            int counter = 0;
            while (userResultSet.next()) {
                counter++;
            }

            if (counter == 2) {
                String checkFriendQuery = String.format("SELECT * FROM DBFollows WHERE (user_id = '%s' AND friend_id = '%s') OR (user_id = '%s' AND friend_id = '%s');", user_id1, user_id2, user_id2, user_id1);
                ResultSet friendResultSet = stmt.executeQuery(checkFriendQuery);

                if (friendResultSet.next()) {
                    System.out.println("이미 팔로우된 사용자 ID입니다.");
                    return false;
                } else {
                    String insertQuery = String.format("INSERT INTO DBtempFollows(user_id, friend_id) VALUES ('%s', '%s');", user_id1, user_id2);
                    stmt.executeUpdate(insertQuery);

                    System.out.println("팔로우 메시지를 보냈습니다..");
                    return true;
                }
            } else {
                System.out.println("입력된 사용자 ID 중 존재하지 않는 ID가 있습니다.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addFollowDBIdCheck(String user_id2) {
        String user_id1 = userIdCode;
        try {
            dBConnection();
            Statement stmt = dbCon.createStatement();

            String checkUsersQuery = String.format("SELECT * FROM DBusers WHERE userid IN ('%s', '%s');", user_id1, user_id2);
            ResultSet userResultSet = stmt.executeQuery(checkUsersQuery);

            int counter = 0;
            while (userResultSet.next()) {
                counter++;
            }
            if (counter == 2) {
                String checkFriendQuery = String.format("SELECT * FROM DBFollows WHERE (user_id = '%s' AND friend_id = '%s') OR (user_id = '%s' AND friend_id = '%s');", user_id1, user_id2, user_id2, user_id1);
                ResultSet friendResultSet = stmt.executeQuery(checkFriendQuery);

                if (friendResultSet.next()) {
                    System.out.println("이미 친구된 사용자 ID입니다.");
                }
                else{
                    return true;
                }
            }
            else{
                return false;
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean friendAcceptCheck() throws SQLException {
        dBConnection(); // 서버연결
        Statement stmt = dbCon.createStatement();

        String exportID = null;

        String userId1 = userIdCode;
        String query1 = String.format("SELECT friend_id FROM DBtempfollows WHERE user_id = '%s';", userId1);
        ResultSet resultSet1 = stmt.executeQuery(query1);
        while (resultSet1.next()) {
            // 일치하는 행이 있을 경우 friend_id 값 저장
            exportID = resultSet1.getString("friend_id");
            return true;
        }
        String query2 = String.format("SELECT user_id FROM DBtempfollows WHERE friend_id = '%s';", userId1);
        ResultSet resultSet2 = stmt.executeQuery(query2);
        while (resultSet2.next()) {
            // 일치하는 행이 있을 경우 friend_id 값 저장
            exportID = resultSet2.getString("user_id");
            return true;
        }
        dbCon.close();

        return false;
    }

    public void DBInsertFriendsWithWork() throws SQLException { //DB에 같이 할 일 입력 메소드
        dBConnection(); // 서버연결
        String sql = "INSERT INTO DBtogether (location,purpose,time) VALUES (?,?,?)";

        PreparedStatement pstmt = dbCon.prepareStatement(sql);
        pstmt.setString(1, location);
        pstmt.setString(2, purpose);
        pstmt.setString(3, String.valueOf(time));
        pstmt.executeUpdate();

    }




    public static void main(String[] args) throws SQLException {
        DataBaseManagement DB = new DataBaseManagement("wbwb","asdd","nickd");
        DB.dbDuplicateIdCheck();
    }



}
