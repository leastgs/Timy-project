package classes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Footer extends JPanel {

    JButton addTask;
    JButton clear;

    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 60));

        setLayout(new FlowLayout(FlowLayout.RIGHT));

        addTask = new JButton("+");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Sans-serif", Font.PLAIN, 20));
        this.add(addTask);

        /*this.add(Box.createHorizontalStrut(20));//Space between buttons
        clear = new JButton("끝낸일 지우기");
        clear.setFont(new Font("Sans-serif",Font.PLAIN, 20));
        clear.setBorder(emptyBorder);
        //clear.setBackground();
        this.add(clear);*/
    }

    public JButton getNewTask() {
        return addTask;
    }
}
