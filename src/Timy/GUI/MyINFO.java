package Timy.GUI;

import Timy.DB.DataBaseManagement;
import Timy.Login.Login;

import javax.swing.*;
import java.awt.*;

public class MyINFO extends JFrame {
    private Login login;
    DataBaseManagement DB = new DataBaseManagement();
    String userIdCode  = Login.getUserIdCode();
    String userName = DB.getUserNameByUserId(userIdCode);;

    public MyINFO() {
        setTitle("내 정보");
        setSize(300,400);
        JPanel panel = new JPanel();
        JLabel idDataLabel = new JLabel("My id: " + userIdCode);
        panel.add(idDataLabel, BorderLayout.NORTH);
        idDataLabel.setHorizontalAlignment(JLabel.CENTER);
        idDataLabel.setFont(new Font("Sans-serif",Font.BOLD, 20));


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);

    }

}
