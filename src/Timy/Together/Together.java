package Timy.Together;

import Timy.DB.DataBaseManagement;
import Timy.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Together extends JPanel {

    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 700;

    private JLabel Text = new JLabel("같이 할 일");
    private JLabel place = new JLabel("장소");
    private JLabel purpose = new JLabel("목적");
    private JLabel time = new JLabel("시간");
    private JLabel friends = new JLabel("같이 할 친구");
    private JTextField placeAnswer = new JTextField();
    private JTextField purposeAnswer = new JTextField();
    private JTextField timeAnswer = new JTextField();
    private JTextField friendsAnswer = new JTextField();
    private JButton selectButton = new JButton("저장");
    private JButton cancelButton = new JButton("취소");

    private String savedPlace = "";
    private String savedPurpose = "";
    private String savedTime = "";
    private String savedFriends = "";
    private Login login;
    String userIdCode  = Login.getUserIdCode();
    public Together() {
        setLayout(null);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        initComponents();
        setEventListeners();
    }

    private void initComponents() {
        setLayout(null);

        FontMetrics metrics = getFontMetrics(Text.getFont());
        int textWidth = metrics.stringWidth(Text.getText());

        int x = (PANEL_WIDTH - textWidth) / 2 ;

        add(Text);
        Text.setBounds(x, 25, textWidth, 50);

        add(place);
        place.setBounds(50, 100, 80, 40);

        add(purpose);
        purpose.setBounds(50, 150, 80, 40);

        add(time);
        time.setBounds(50, 200, 80, 40);

        add(friends);
        friends.setBounds(50, 250, 80, 40);

        add(placeAnswer);
        placeAnswer.setBounds(150, 100, 250, 40);
        placeAnswer.setFont(new Font("",Font.PLAIN,16));

        add(purposeAnswer);
        purposeAnswer.setBounds(150, 150, 250, 40);
        purposeAnswer.setFont(new Font("",Font.PLAIN,16));

        add(timeAnswer);
        timeAnswer.setBounds(150, 200, 250, 40);
        timeAnswer.setFont(new Font("",Font.PLAIN,16));

        add(friendsAnswer);
        friendsAnswer.setBounds(150, 250, 250, 40);
        friendsAnswer.setFont(new Font("",Font.PLAIN,16));

        add(selectButton);
        selectButton.setBackground(Color.gray);
        selectButton.setBounds(200, 400, 100, 30);

//        add(cancelButton);
//        cancelButton.setBackground(Color.gray);
//        cancelButton.setBounds(300, 600, 100, 30);
    }

    private void setEventListeners() {
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savedPlace = placeAnswer.getText();
                savedPurpose = purposeAnswer.getText();
                savedTime = timeAnswer.getText();
                savedFriends = friendsAnswer.getText();

                DataBaseManagement DB = new DataBaseManagement();
                String user_id1 = userIdCode;

                if(DB.addFollowDBIdCheck(savedFriends)){

                };


                // 필요한 액션을 추가하실 수 있습니다. (예: DB에 데이터 저장 등)

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // 패널을 숨깁니다.
            }
        });
    }

}
