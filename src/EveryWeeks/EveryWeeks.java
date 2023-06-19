package EveryWeeks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EveryWeeks extends JPanel{

    private JTable scheduleTable;
    private JTextField timeField;
    private JTextField taskField;
    private JComboBox<String> dayComboBox;

    public EveryWeeks() {
        this.setSize( 500, 500);
        this.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        scheduleTable = createScheduleTable();
        panel.add(scheduleTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(scheduleTable, BorderLayout.CENTER);

        JPanel inputPanel = createInputPanel();
        panel.add(inputPanel, BorderLayout.SOUTH);

        this.add(panel);
    }

    private JTable createScheduleTable() {
        String[] columnNames = {"", "일", "월", "화", "수", "목", "금"};
        Object[][] data = new Object[25][7];

        for (int i = 0; i < 25; i++) {
            data[i][0] = i;
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        return table;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel timeLabel = new JLabel("시간: ");
        timeField = new JTextField(5);
        panel.add(timeLabel);
        panel.add(timeField);

        JLabel taskLabel = new JLabel("일정: ");
        taskField = new JTextField(20);
        panel.add(taskLabel);
        panel.add(taskField);

        JLabel dayLabel = new JLabel("요일: ");
        String[] days = {"일", "월", "화", "수", "목", "금"};
        dayComboBox = new JComboBox<>(days);
        panel.add(dayLabel);
        panel.add(dayComboBox);

        JButton addButton = new JButton("추가");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String time = timeField.getText();
                String task = taskField.getText();
                String day = (String) dayComboBox.getSelectedItem();

                int row = Integer.parseInt(time);
                int column = getColumnIndex(day);

                scheduleTable.setValueAt(task, row, column);

                timeField.setText("");
                taskField.setText("");
            }
        });
        panel.add(addButton);

        return panel;
    }

    private int getColumnIndex(String day) {
        switch (day) {
            case "일":
                return 1;
            case "월":
                return 2;
            case "화":
                return 3;
            case "수":
                return 4;
            case "목":
                return 5;
            case "금":
                return 6;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EveryWeeks();
            }
        });
    }
}
