
package Timy.Login;

import Timy.DB.DataBaseManagement;
import Timy.SignUp.SignUp;
import Timy.GUI.Timy;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Login extends JFrame {
    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    private JPanel jPanel = new JPanel();
    private JTextField textFieldID= new JTextField();
    private JPasswordField passwordField = new JPasswordField(8);

    public static String userIdCode;

    //

    //
    public String storeID;
    public String clientID;

    public Login() {
        setTitle("로그인 화면");
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel menu = new JLabel("로그인");
        add(menu);
        menu.setBounds(760,195,350,50);

        JLabel id = new JLabel("아이디"); //글씨
        add(id);
        id.setBounds(600,265,80,40);

        JLabel password = new JLabel("비밀번호"); //글씨
        add(password);
        password.setBounds(600,315,80,40);

//        JTextField textFieldID= new JTextField();
        add(textFieldID);
        textFieldID.setBounds(660,265,250,40);

//        JPasswordField passwordField = new JPasswordField(8);
        add(passwordField);
        passwordField.setBounds(660,315,250,40);



        JButton loginButton = new JButton("로그인");
        add(loginButton);
        loginButton.setBounds(660,375,250,30);
        loginButton.setBackground(Color.gray);

        loginButton.addActionListener(e -> {
            try {
                if(loginCheck()) { //로그인 성공시
                    new Timy(this);
                    dispose();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });



        JButton signUpButton = new JButton("회원가입");
        add(signUpButton);
        signUpButton.setBounds(660,415,250,30);
        signUpButton.setBackground(Color.gray);

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignUp();
                dispose();
            }

        });


        add(jPanel);
        jPanel.setLayout(null);
        setVisible(true);
//        serverEx.start();
    }


    private boolean loginCheck() throws SQLException {
        String id = textFieldID.getText();
        String password = new String(passwordField.getPassword());

        boolean isLoginCheck = false;
        DataBaseManagement DB = new DataBaseManagement(id,password);
        isLoginCheck = DB.dbLoginCheck();

        if (isLoginCheck) {
            userIdCode = id;
            JOptionPane.showMessageDialog(null, "로그인 성공", "message", JOptionPane.INFORMATION_MESSAGE);
            return true;

        } else {
            JOptionPane.showMessageDialog(null, "아이디 비밀번호 오류", "login error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public String getClientID() {
        return clientID;
    }

    public static String getUserIdCode() {return userIdCode;}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
