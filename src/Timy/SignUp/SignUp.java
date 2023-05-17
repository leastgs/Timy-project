package Timy.SignUp;

import Timy.Login.Login;

import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;

public class SignUp extends JFrame{

    public SignUp() {
        setTitle("회원가입 화면");
        setSize(500,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();

        JLabel infor = new JLabel("TIMY");
        add(infor);
        infor.setBounds(220,20,150,50);

        JLabel userName = new JLabel("*이름"); //글씨
        add(userName);
        userName.setBounds(160,50,150,100);

        JTextField userNameField = new JTextField("이름");
        add(userNameField);
        userNameField.setBounds(160,110,150,30);

        userNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { //포커스 얻음
                if (userNameField.getText().equals("이름")) {
                    userNameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) { //포커스 잃음
                if (userNameField.getText().isEmpty()) {
                    userNameField.setText("이름");
                }
            }
        });

        JLabel userId = new JLabel("*아이디"); //글씨
        add(userId);
        userId.setBounds(160,110,150,100);

        JTextField userIdField = new JTextField("아이디 입력");
        add(userIdField);
        userIdField.setBounds(160,170,150,30);

        userIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { //포커스 얻음
                if (userIdField.getText().equals("아이디 입력")) {
                    userIdField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) { //포커스 잃음
                if (userIdField.getText().isEmpty()) {
                    userIdField.setText("아이디 입력");
                }
            }
        });

        JLabel userPasssword = new JLabel("*비밀번호"); //글씨
        add(userPasssword);
        userPasssword.setBounds(160,170,150,100);
        JPasswordField userPasswordField = new JPasswordField(8);
        add(userPasswordField);
        userPasswordField.setBounds(160,230,150,30);

        JButton idCheckBtn = new JButton("중복 확인");
        add(idCheckBtn);
        idCheckBtn.setBounds(320,170,90,30);

        JButton signUpBtn = new JButton("가입하기");
        add(signUpBtn);
        signUpBtn.setBounds(160,700,150,50);
        JButton cancelBtn = new JButton("취소");
        add(cancelBtn);
        cancelBtn.setBounds(350,700,80,50);


        signUpBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String name = userNameField.getText().toString();
                String id = userIdField.getText().toString();
                //String password = new String(userPasswordField.getPassword());
                String password = userPasswordField.getText().toString();
                String fileName = "Member.txt";
                try {
                    File file = new File(fileName);
                    BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
                    BufferedReader in = new BufferedReader(new FileReader(file));
                    String check;
                    int standard = 0;
                    while((check = in.readLine()) != null) {
                        StringTokenizer tokenizer = new StringTokenizer(check, " ");
                        while(tokenizer.hasMoreTokens()) {
                            if(userIdField.equals(tokenizer.nextToken())) {
                                standard = 1;
                            }
                        }
                    }
                    if(standard == 1) {
                        JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.", "회원가입오류", JOptionPane.ERROR_MESSAGE);
                        userNameField.setText("");
                        userIdField.setText("");
                        userPasswordField.setText("");
                    } else if (standard == 0) {
                        out.write(name);
                        out.write(" ");
                        out.write(id);
                        out.write(" ");
                        out.write(password);
                        out.newLine();
                        dispose();
                    }
                    out.close();
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Login();
            }
        });


        add(jPanel);
        jPanel.setLayout(null);
        setVisible(true);
    }
}
