package Timy.GUI.Calender;

import classes.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Calendar {
    private JLabel monthLabel;
    private JButton prevButton, nextButton, addButton;
    private JPanel calendarPanel;
    private int currentYear;
    private int currentMonth;

    public Calendar() {
        java.util.Calendar today = java.util.Calendar.getInstance(Locale.KOREA);
        this.currentYear = today.get(java.util.Calendar.YEAR);
        this.currentMonth = today.get(java.util.Calendar.MONTH);
    }

    public JPanel createControls() {
        monthLabel = new JLabel("", SwingConstants.CENTER);
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        addButton = new JButton("내용 추가");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(prevButton, BorderLayout.WEST);
        panel.add(monthLabel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.EAST) ;

        prevButton.setBackground(Color.gray);
        nextButton.setBackground(Color.gray);

        return panel;
    }

    public JPanel createCalendarPanel() {
        calendarPanel = new JPanel(new GridBagLayout());
        updateCalendarPanel();

        return calendarPanel;
    }

    public void updateCalendarPanel() {
        calendarPanel.removeAll();

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(currentYear, currentMonth, 1);

        int startDayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        int daysOfMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        monthLabel.setText(currentYear + "년 " + (currentMonth + 1) + "월");

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.weighty = 1;

        for (int i = 0; i < daysOfMonth + startDayOfWeek; i++) {
            gridBagConstraints.gridy = i / 7;
            gridBagConstraints.gridx = i % 7;

            if (i < startDayOfWeek) {
                calendarPanel.add(new JLabel(""), gridBagConstraints);
            } else {
                calendarPanel.add(createButton(currentYear, currentMonth, (i - startDayOfWeek) + 1), gridBagConstraints);
            }
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public JButton createButton(int year, int month, int day) {
        JButton button = new JButton(day + "일");
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame frame = new AppFrame();
            }
        });
        return button;
    }

    public void registerButtonHandlers() {
        prevButton.addActionListener(e -> {
            currentMonth--;
            if (currentMonth < 0) {
                currentMonth = 11;
                currentYear--;
            }
            updateCalendarPanel();
        });

        nextButton.addActionListener(e -> {
            currentMonth++;
            if (currentMonth > 11) {
                currentMonth = 0;
                currentYear++;
            }
            updateCalendarPanel();
        });
    }

    public JPanel getCalendarPanel() {
        return calendarPanel;
    }

    private void showMemoWindow(int year, int month, int day) {
        JFrame memoFrame = new JFrame(year + "년 " + (month + 1) + "월 " + day + "일 메모");

        memoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        memoFrame.setLocationRelativeTo(null);
        memoFrame.setSize(300, 200);

        JTextArea memoArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(memoArea);
        memoFrame.add(scrollPane);

        JButton saveButton = new JButton("저장");
        memoFrame.add(saveButton, BorderLayout.SOUTH);

        String memoContent = loadMemo(year, month, day);
        memoArea.setText(memoContent);
        // 저장 로직 구현
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 메모 내용을 저장하는 로직 구현 (예: 파일에 저장)
                // 원하는 저장 방식에 따라 구현을 추가해 주십시오.
                saveMemo(year, month, day, memoArea.getText());
                memoFrame.dispose();
            }
        });

        memoFrame.setVisible(true);
    }

    private void saveMemo(int year, int month, int day, String content) {
        String fileName = String.format("C:\\Users\\USER\\IdeaProjects\\Timy\\src\\memo\\memo_%d_%02d_%02d.txt", year, month + 1, day);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("메모 저장 중 오류가 발생했습니다. " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String loadMemo(int year, int month, int day) {
        String fileName = String.format("C:\\Users\\USER\\IdeaProjects\\Timy\\src\\memo\\memo_%d_%02d_%02d.txt", year, month + 1, day);
        File memoFile = new File(fileName);
        if (memoFile.exists()) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append("\n");
                }
            } catch (IOException e) {
                System.err.println("메모 불러오기 중 오류가 발생했습니다. " + e.getMessage());
                e.printStackTrace();
            }
            return content.toString();
        } else {
            return "";

        }
    }
}