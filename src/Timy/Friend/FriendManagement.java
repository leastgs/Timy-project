package Timy.Friend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Timy.DB.DataBase;
public class FriendManagement {
    public void addFriend(String username1, String username2) {
        Connection connection = DataBase.getConnection();

        if (connection == null) {
            System.err.println("데이터베이스 연결에 실패했습니다.");
            return;
        }

        int userId1 = getUserId(connection, username1);
        int userId2 = getUserId(connection, username2);

        if (userId1 == -1 || userId2 == -1) {
            System.err.println("해당 사용자를 찾을 수 없습니다.");
            return;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO friends (user_id, friend_id) VALUES (?, ?)"
            );

            preparedStatement.setInt(1, userId1);
            preparedStatement.setInt(2, userId2);

            int rows = preparedStatement.executeUpdate();
            connection.close();

            if (rows > 0) {
                System.out.println("친구 추가에 성공했습니다!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getUserId(Connection connection, String username) {
        int userId = -1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id FROM users WHERE username = ?"
            );

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
}
