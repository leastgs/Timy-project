package Timy.SignUp;

import Timy.DB.DataBaseManagement;
import Timy.Login.Login;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;

public class SignUp extends JFrame {
    private JPanel jPanel = new JPanel();
    private JLabel infor = new JLabel("TIMY");
    private JLabel userName = new JLabel("*이름"); //글씨
    private JTextField userNameField = new JTextField("이름");
    private JLabel userId = new JLabel("*아이디"); //글씨
    private JTextField userIdField = new JTextField("아이디 입력");
    private JLabel userPasssword = new JLabel("*비밀번호"); //글씨
    private JPasswordField userPasswordField = new JPasswordField(10);
    private JLabel retryPW = new JLabel("*비밀번호 확인");
    private JPasswordField retryPWField= new JPasswordField(10);
    private JButton iD_DuplicateCheckBtn = new JButton("중복 확인");
    private JButton signUpBtn = new JButton("가입하기");
    private JButton cancelBtn = new JButton("취소");

    public SignUp() {
        setTitle("회원가입 화면");
        setSize(500, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        setFocusListener();
        setEventListeners();

        add(jPanel);
        setVisible(true);
    }

    private void initComponents() {
        jPanel.setLayout(null);
        jPanel.add(infor);
        infor.setBounds(220, 20, 150, 50);
        jPanel.add(userName);
        userName.setBounds(160, 50, 150, 100);
        jPanel.add(userNameField);
        userNameField.setBounds(160, 110, 150, 30);
        jPanel.add(userId);
        userId.setBounds(160, 110, 150, 100);
        jPanel.add(userIdField);
        userIdField.setBounds(160, 170, 150, 30);

        jPanel.add(userPasssword);
        userPasssword.setBounds(160, 170, 150, 100);
        jPanel.add(userPasswordField);
        userPasswordField.setBounds(160, 230, 150, 30);
        jPanel.add(retryPW);
        retryPW.setBounds(160, 230, 150, 100);
        jPanel.add(retryPWField);
        retryPWField.setBounds(160,290,150,30);

        jPanel.add(iD_DuplicateCheckBtn);
        iD_DuplicateCheckBtn.setBounds(320, 170, 90, 30);
        jPanel.add(signUpBtn);
        signUpBtn.setBounds(160, 700, 150, 50);
        jPanel.add(cancelBtn);
        cancelBtn.setBounds(350, 700, 80, 50);

    }

    private void setEventListeners() {
        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinMember();
            }
        });

        iD_DuplicateCheckBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    idDuplicate_Check();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });
    }

    private void setFocusListener() {
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
    }

    private void joinMember() {
        String name = userNameField.getText();
        String id = userIdField.getText().toString();
        String password = new String(userPasswordField.getPassword());
        try {
            DataBaseManagement DB = new DataBaseManagement(name,id,password);
            DB.DBInsertUserData();
            JOptionPane.showMessageDialog(null, "가입이 되었습니다","가입 완료",JOptionPane.INFORMATION_MESSAGE);
            new Login();
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean idDuplicate_Check() throws SQLException {
        String id = userIdField.getText();
        boolean idCheck = false;
        DataBaseManagement DB = new DataBaseManagement(id);
        idCheck = DB.dbDuplicateIdCheck();

        if(idCheck) {
            JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.", "회원가입오류", JOptionPane.ERROR_MESSAGE);
            return false;
        } else
        {
            JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

    }

    private boolean isFieldCheck() {
        if (userNameField.getText().trim().isEmpty() || userNameField.getText().equals("이름")) {
            JOptionPane.showMessageDialog(null, "이름을 입력해주세요", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if(userIdField.getText().trim().isEmpty() || userIdField.getText().equals("아이디 입력")) {
            JOptionPane.showMessageDialog(null,"아이디를 입력해주세요","입력 오류",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if(userPasswordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(null,"비밀번호를 입력해주세요","입력 오류",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else if(!Arrays.equals(userPasswordField.getPassword(), retryPWField.getPassword())) {
            JOptionPane.showMessageDialog(null,"비밀번호가 일치하지 않습니다.","메시지",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {return true;}
    }


    public static void main(String[] args) {
        new SignUp();
    }
}