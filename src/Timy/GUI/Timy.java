package Timy.GUI;

import EveryWeeks.EveryWeeks;
import Timy.DB.DataBaseManagement;
import Timy.Login.Login;
import Timy.Together.Together;
import Timy.GUI.Calender.CalendarPanel;
import Timy.GUI.Allday.AlldayController;
import Timy.chat.Client;
import Timy.chat.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Timy extends JFrame {

    // private final int SCREEN_WIDTH = 1920;
    // private final int SCREEN_HEIGHT = 1080;

    private JLabel myINFO = new JLabel("Inpo");
    private JTextField IdFind = new JTextField();
    private JButton FindFriends = new JButton("검색");

    private JButton inRoom = new JButton("입장");
    private String friendId = IdFind.getText();
    JPanel addedFriend = new JPanel();
    private Login loginRF;

    public Timy(Login login) {
        this.loginRF = login;
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // 화면 크기 정보를 가져오기 위해 GraphicsEnvironment 사용
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // 기본 화면 장치의 크기 가져오기
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        setTitle("Timy");
        setSize(screenWidth, screenHeight);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(null);

       /* CalendarPanel calendarPanel = new CalendarPanel();
        calendarPanel.setBounds(20, 70, 500, 500); // CalendarPanel의 위치와 크기를 설정
        add(calendarPanel);  */ // ->기존 캘린더 코드

        EveryWeeks everyweeks = new EveryWeeks();
        everyweeks.setBounds(screenWidth * 20/1536, screenHeight * 70/864, 550, 500);
        add(everyweeks);



        AlldayController alldayController = new AlldayController("Allday.txt");
        JPanel alldayPanel = alldayController.getPanel();
        alldayPanel.setBounds(screenWidth * 570/1536, screenHeight * -100/864, 420, 500);
        add(alldayPanel);

        Together togetherPanel = new Together();
        togetherPanel.setBounds(screenWidth * 540/1536, screenHeight * 360/864, 500, 700);  // 위치와 크기를 설정
        add(togetherPanel);

        add(myINFO);
        myINFO.setBounds(screenWidth * 1360/1536,screenHeight * 10/864,150,30);
        myINFO.setBackground(Color.white);
        // myINFO.addActionListener(e -> new MyINFO());

        add(IdFind);
        IdFind.setBounds(screenWidth * 1335/1536,screenHeight * 80/864,130,30);
        IdFind.setBackground(Color.white);

        add(FindFriends);
        FindFriends.setBounds(screenWidth * 1470/1536,screenHeight * 80/864,60,30);
        FindFriends.setBackground(Color.white);

        JPanel separatorLine = new JPanel();
        separatorLine.setBackground(Color.lightGray); // 선 색상 설정
        separatorLine.setBounds(screenWidth * 1330/1536, 0, 1, screenHeight - 20); // 선 위치와 크기 설정
        add(separatorLine);

        JPanel horizontalLine = new JPanel();
        horizontalLine.setBackground(Color.lightGray); // 선 색상 설정
        horizontalLine.setBounds(screenWidth * 1330/1536, screenHeight * 75/864, 200, 2); // 선 위치와 크기 설정
        add(horizontalLine);

        addedFriend.setBounds(screenWidth * 1330/1536, screenHeight * 110/864, 200, 360);
        addedFriend.setBackground(Color.lightGray);
        add(addedFriend);

        inRoom.setBounds(screenWidth * 1130/1536, 30, 40, 40);
        inRoom.setBackground(Color.lightGray);
        add(inRoom);

        //추가
        /* JPanel FriendList = new JPanel();
        FriendList.setBounds(1505, 90, 195, 300);
        FriendList.setBackground(Color.white);
        FriendList.setLayout(null); // Layout manager를 비활성화합니다.

        JTextField friendField = new JTextField(8);
        friendField.setBounds(5, 5, 90, 30); // FriendList 패널 내부에서의 상대적인 위치와 크기를 설정합니다.
        FriendList.add(friendField);

        JTextField ShowId = new JTextField(8);
        ShowId.setBounds(5, 40, 185, 30); //
        FriendList.add(ShowId);

        JTextField ShowNick = new JTextField(8);
        ShowNick.setBounds(5, 70, 185, 30); //
        FriendList.add(ShowNick);

        add(FriendList);

        //선
        JPanel BorderLine = new JPanel();
        BorderLine.setBackground(Color.lightGray);
        BorderLine.setBounds(1500,85,200,2);
        add(BorderLine);

        JPanel SecondHorLine = new JPanel();
        SecondHorLine.setBackground(Color.lightGray);
        SecondHorLine.setBounds(5, 35, 185, 2); // FriendList 패널 내부에서의 상대적인 위치와 크기를 설정합니다.
        FriendList.add(SecondHorLine);

        add(FriendList); */


        setVisible(true);
        addListeners();
    }
    public void addListeners() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // 화면 크기 정보를 가져오기 위해 GraphicsEnvironment 사용
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        // 기본 화면 장치의 크기 가져오기
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        FindFriends.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(500, 500);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JButton closeButton = new JButton("닫기");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();  // JFrame 닫기
                    }
                });
                JButton addButton = new JButton("Add");

                // friendId 값을 가져옴
                String friendId = IdFind.getText();

                // friendId 값을 표시하는 레이블 생성
                JLabel friendLabel = new JLabel("Friend ID: " + friendId);

                JPanel panel = new JPanel();
                panel.add(friendLabel); // friendId 값을 표시하는 레이블을 패널에 추가
                panel.add(closeButton);
                panel.add(addButton);

                frame.add(panel);
                frame.setVisible(true);

                /*JPanel realPanel = new JPanel();
                realPanel.setBounds(screenWidth * 1330/1536, screenHeight * 80/864, 200, 360);
                JLabel addedfriend = new JLabel(friendId);
                addedfriend.setBounds(screenWidth * 1330/1536, screenHeight * 80/864, 200, 30);
                realPanel.add(addedfriend);*/

                addButton.addActionListener(new ActionListener() {
                    DataBaseManagement DB = new DataBaseManagement();
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    DB.addFollow(DB.userIdCode,friendId);
                    JButton friendLabel = new JButton(friendId);
                    addedFriend.add(friendLabel); // Timy 프레임에 추가
                    revalidate(); // 프레임을 다시 그리도록 갱신
                    frame.dispose();
                    // friendId 값을 표시한 후 필요한 추가 작업 수행
                    // ...

                    }
                });
            }
        });

        inRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();
                client.setLogin(loginRF);

                Gui gui = new Gui(client);
                client.setGui(gui);
                client.startClient();
                gui.setVisible(true);
            }
        });


    }



    public static void main(String[] args) {
        new Login();
    }
}