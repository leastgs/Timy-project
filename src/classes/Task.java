package classes;

import java.awt.*;
import java.awt.event.MouseAdapter;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;




public class Task extends JPanel{

    JLabel index;
    JTextField taskName;
    //JButton done;  기존 Done 코드
    JCheckBox done;
    private boolean checked;

    JButton clear;
    Border emptyBorder = BorderFactory.createEmptyBorder();

    Task()
    {
        this.setPreferredSize(new Dimension(360,20));
        this.setBackground(Color.red);

        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20,20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index,BorderLayout.EAST);

        taskName = new JTextField("할 일 입력!");
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(Color.red);

        this.add(taskName,BorderLayout.CENTER);

        clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(40, 20));
        clear.setBorder(emptyBorder);
        clear.setFocusPainted(false);
        this.add(clear, BorderLayout.LINE_END);

        JCheckBox done = new JCheckBox();
        done.setPreferredSize(new Dimension(20,20));


        this.add(done,BorderLayout.WEST);
        addListeners();
/*
        done = new JButton("Done");
        done.setPreferredSize(new Dimension(40,20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setFocusPainted(false);

        this.add(done,BorderLayout.EAST);  //Done 버튼 위치
        */   //기존 Done 코드

    }

    public void changeIndex(int num)
    {
        this.index.setText(num+"");
        this.revalidate();
    }


    public JCheckBox getDone()
    {

        return done;
    }

    public boolean getState()
    {
        return checked;
    }

    public void changeState()
    {
        this.setBackground(Color.green);
        taskName.setBackground(Color.green);
        checked = true;
        revalidate();
    }
    public void addListeners(){
        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);


            }
        });

    }
}