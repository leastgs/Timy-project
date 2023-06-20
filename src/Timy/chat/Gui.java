package Timy.chat;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Gui extends JFrame {
    private JPanel jPanel = new JPanel(null);
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage("src/image/message.png");


    private JTextArea chatArea = new JTextArea(); //채팅창 내용 보이는super 곳

    private JLabel chatInfo;
    private JTextArea chat11 = new JTextArea();
    private JScrollPane chatUser;

    private JTextField inputField = new JTextField();

    private JButton selectFileBtn = new JButton("");

    private JButton sendBtn = new JButton("전송");
    private JButton exitBtn = new JButton("나가기");
    private Client client;

    public Gui(Client client) {
        this.client = client;

        init();
        setVisible(true);

    }

    public void init() {
        setTitle("채팅창");
        setSize(700, 800);
        setIconImage(image);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(jPanel);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatArea.setEditable(false);
        chatArea.setFont(new Font("나눔고딕", Font.PLAIN, 18));
        scrollPane.setBounds(10, 10, 500, 500);
        jPanel.add(scrollPane);

        inputField.setFont(new Font("나눔고딕", Font.PLAIN, 18));
        inputField.setBounds(10, 550, 390, 60);
        inputField.addActionListener(e -> {
            String message = inputField.getText();
            client.sendMessage(message);
            inputField.setText("");
        });
        jPanel.add(inputField);

        sendBtn.setBounds(430, 550, 80, 45);
        sendBtn.setBackground(Color.LIGHT_GRAY);
        sendBtn.addActionListener(e -> {
            String message = inputField.getText();
            client.sendMessage(message);
            inputField.setText("");
        });
        jPanel.add(sendBtn);

        selectFileBtn.setBounds(10, 620, 30, 30);
        selectFileBtn.setBackground(Color.LIGHT_GRAY);
        selectFileBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(jPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                client.sendImage(selectedFile);
            }
        });
        jPanel.add(selectFileBtn);

        exitBtn.setBackground(Color.LIGHT_GRAY);
        exitBtn.setBounds(550, 10, 100, 50);
        exitBtn.addActionListener(e -> {
            client.sendMessage("나가기버튼누름");
            client.closeConnection();
            System.exit(0);
        });
        jPanel.add(exitBtn);

    }

    public void displayMessage(String message) {
        chatArea.append(message + "\n");
    }

}