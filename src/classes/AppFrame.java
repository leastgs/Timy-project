package classes;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class AppFrame extends JFrame {

    private TitleBar title;
    private Footer footer;
    private List list;

    private JButton newTask;

    public AppFrame() {
        this.setSize(400, 700);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        // JScrollPane 생성 및 설정
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(this.getSize()); // 현재 프레임의 크기와 동일하게 설정

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(scrollPane, BorderLayout.CENTER); // 스크롤 패널을 추가

        newTask = footer.getNewTask();

        addListeners();
    }

    public void addListeners() {
        newTask.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JFrame frame = new JFrame();
                frame.setSize(200, 200);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);

                JPanel panel = new JPanel();
                frame.add(panel);

                JTextField textField = new JTextField(10);
                panel.add(textField);

                JButton addButton = new JButton("Add");
                panel.add(addButton);

                JButton deleteButton = new JButton("Delete");
                panel.add(deleteButton);

                /*task.getDone().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        task.changeState();
                        list.updateNumbers();
                        revalidate();
                    }
                });*/
            }
        });
    }
}
