package Timy.GUI;

import Timy.DB.DataBaseManagement;
import Timy.Login.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Follow extends Timy{
    private JButton fdAdd = new JButton("수락");
    private JButton fdDel = new JButton("거절");
    private JPanel panelToRemove = new JPanel();


    String friendId;
    public Follow(String friendId) {

        super(new Login());
        System.out.println("들어옴 follow");
        this.friendId = friendId;
        System.out.println(friendId);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // 화면 크기 정보를 가져오기 위해 GraphicsEnvironment 사용
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // 기본 화면 장치의 크기 가져오기
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        JPanel chkPanel = new JPanel();
        JLabel fdName = new JLabel(friendId + "님이 친구추가");
        JButton fdAdd = new JButton("수락");
        JButton fdDel = new JButton("거절");
        super.add(chkPanel);
        chkPanel.setBounds(screenWidth * 1336/1536, screenHeight * 664/864, 100, 200);
        chkPanel.add(fdName);
        chkPanel.add(fdAdd);
        chkPanel.add(fdDel);

        fdAdd.addActionListener(new ActionListener() {
            DataBaseManagement DB = new DataBaseManagement();
            @Override
            public void actionPerformed(ActionEvent e) {
                DB.addFollow(DB.userIdCode, friendId);

                addedFriend.setBounds(screenWidth * 1330/1536, screenHeight * 110/864, 200, 360);
                addedFriend.setBackground(Color.CYAN);
                add(addedFriend);
                // friendId 값을 표시하는 레이블을 생성
                JButton friendLabel = new JButton(friendId);

                addedFriend.add(friendLabel); // Timy 프레임에 추가
                revalidate(); // 프레임을 다시 그리도록 갱신
                //super.dispose();
                // friendId 값을 표시한 후 필요한 추가 작업 수행
                // ...


            }

        });


        super.setVisible(true); // 프레임을 화면에 표시

    }

}
