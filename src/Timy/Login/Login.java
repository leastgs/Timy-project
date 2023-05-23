package Timy.Login;

import Timy.SignUp.SignUp;

import javax.swing.*;
import java.awt.event.*;

public class Login extends JFrame {
    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    private JPanel jPanel = new JPanel();
    private JLabel loginText = new JLabel("로그인");
    private JLabel id = new JLabel("아이디"); //글씨
    private JLabel passsword = new JLabel("비밀번호"); //글씨
    private JTextField idFielD= new JTextField();
    private JPasswordField passwordFielD = new JPasswordField(8);
    private JButton loginButton = new JButton("로그인");
    private JButton signUpButton = new JButton("회원가입");


    public Login() {
        setTitle("로그인 화면");
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        setEventListeners();

        add(jPanel);
        setVisible(true);
    }

    private void initComponents() {
        jPanel.setLayout(null);

        add(loginText);
        loginText.setBounds(760,195,350,50);

        add(id);
        id.setBounds(600,265,80,40);

        add(passsword);
        passsword.setBounds(600,315,80,40);

        add(idFielD);
        idFielD.setBounds(660,265,250,40);

        add(passwordFielD);
        passwordFielD.setBounds(660,315,250,40);

        add(loginButton);
        loginButton.setBounds(660,375,250,30);

        add(signUpButton);
        signUpButton.setBounds(660,415,250,30);

    }

    private void setEventListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //로그인 화면
                //new Timy();
                dispose();
            }
        });

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SignUp();
                dispose();
            }

        });
    }

}
