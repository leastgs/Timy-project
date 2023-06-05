package Timy.Together;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Together extends JFrame{
    public Together(){
        setTitle("Together");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("맑은 고딕", Font.BOLD, 18);  // 폰트 생성

        GridLayout grid = new GridLayout(5,2);
        grid.setVgap(5);

        Container c = getContentPane();
        c.setLayout(grid);

        JLabel titleLabel = new JLabel("같이 할 일!");
        titleLabel.setFont(font);  // 폰트 적용
        c.add(titleLabel);
        c.add(new JLabel(""));

        JLabel placeLabel = new JLabel("장소");
        placeLabel.setFont(font);
        c.add(placeLabel);

        JPanel placePanel = new JPanel(new CardLayout());
        JTextField placeTextField = new JTextField("");
        placeTextField.setFont(font);  // 폰트 적용
        placeTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JLabel placeLabelResult = new JLabel(placeTextField.getText());
                placeLabelResult.setFont(font);  // 폰트 적용
                placePanel.add(placeLabelResult, "Label");
                CardLayout cl = (CardLayout)(placePanel.getLayout());
                cl.show(placePanel, "Label");
            }
        });
        placePanel.add(placeTextField, "TextField");
        c.add(placePanel);

        JLabel purposeLabel = new JLabel("목적");
        purposeLabel.setFont(font);
        c.add(purposeLabel);

        JPanel purposePanel = new JPanel(new CardLayout());
        JTextField purposeTextField = new JTextField("");
        purposeTextField.setFont(font);  // 폰트 적용
        purposeTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JLabel purposeLabelResult = new JLabel(purposeTextField.getText());
                purposeLabelResult.setFont(font);  // 폰트 적용
                purposePanel.add(purposeLabelResult, "Label");
                CardLayout cl = (CardLayout)(purposePanel.getLayout());
                cl.show(purposePanel, "Label");
            }
        });
        purposePanel.add(purposeTextField, "TextField");
        c.add(purposePanel);

        JLabel timeLabel = new JLabel("시간");
        timeLabel.setFont(font);
        c.add(timeLabel);

        JPanel timePanel = new JPanel(new CardLayout());
        JTextField timeTextField = new JTextField("");
        timeTextField.setFont(font);  // 폰트 적용
        timeTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JLabel timeLabelResult = new JLabel(timeTextField.getText());
                timeLabelResult.setFont(font);  // 폰트 적용
                timePanel.add(timeLabelResult, "Label");
                CardLayout cl = (CardLayout)(timePanel.getLayout());
                cl.show(timePanel, "Label");
            }
        });
        timePanel.add(timeTextField, "TextField");
        c.add(timePanel);

        JLabel friendLabel = new JLabel("같이 할 친구");
        friendLabel.setFont(font);
        c.add(friendLabel);
        c.add(new JComboBox<>());

        setSize(500,500);
        setVisible(true);
    }

    public static void start() {
        new Together();
    }
}
