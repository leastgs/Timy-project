package Timy.Login;

import Timy.SignUp.SignUp;
import Timy.GUI.Timy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
        loginButton.setBackground(Color.LIGHT_GRAY);

        add(signUpButton);
        signUpButton.setBounds(660,415,250,30);
        signUpButton.setBackground(Color.LIGHT_GRAY);

    }

    private void setEventListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logincheck();
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

    private void logincheck() {
        String id = idFielD.getText();
        String password = new String(passwordFielD.getPassword());
        String fileName = "src/Member.txt";

        try {
            File file = new File(fileName);
            BufferedReader in = new BufferedReader(new FileReader(file));

            String line;
            boolean isloginCheck = false;

            while((line = in.readLine()) != null) {
                String[] userInfo = line.split(" ");

                if (userInfo.length >= 2) {
                    String storedId = userInfo[1].trim();
                    String storedPassword = userInfo[2].trim();

                    if (id.equals(storedId) && password.equals(storedPassword)) {
                        isloginCheck = true;
                        break;
                    }
                }
            }
            in.close();

            if(isloginCheck) {
                JOptionPane.showMessageDialog(null,"로그인 성공", "login success",JOptionPane.INFORMATION_MESSAGE);
                new Timy();
                dispose();
            } else {
                JOptionPane.showMessageDialog(null,"아이디 비밀번호 오류", "login error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

}
