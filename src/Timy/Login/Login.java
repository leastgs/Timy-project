package Timy.Login;

import Timy.SignUp.SignUp;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame {
    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    public Login() {
        setTitle("로그인 화면");
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();

        JLabel menu = new JLabel("로그인");
        add(menu);
        menu.setBounds(760,195,350,50);

        JLabel id = new JLabel("아이디"); //글씨
        add(id);
        id.setBounds(600,265,80,40);

        JLabel passsword = new JLabel("비밀번호"); //글씨
        add(passsword);
        passsword.setBounds(600,315,80,40);

        JTextField textFieldID= new JTextField();
        add(textFieldID);
        textFieldID.setBounds(660,265,250,40);

        JPasswordField passwordField = new JPasswordField(8);
        add(passwordField);
        passwordField.setBounds(660,315,250,40);



        JButton loginButton = new JButton("로그인");
        add(loginButton);
        loginButton.setBounds(660,375,250,30);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //로그인 화면
            }
        });

        JButton signUpButton = new JButton("회원가입");
        add(signUpButton);
        signUpButton.setBounds(660,415,250,30);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //회원가입 화면 구성
                new SignUp();
                dispose();
            }
        });

        add(jPanel);
        setVisible(true);
        jPanel.setLayout(null);
    }

}

