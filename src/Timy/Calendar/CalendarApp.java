package Timy.Calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

class CalendarFrame extends JFrame {
    private CalendarApp calendarApp;

    public CalendarFrame() {
        setTitle("달력 애플리케이션");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        calendarApp = new CalendarApp();

        // 패널 생성 및 추가
        JPanel controlPanel = calendarApp.createControls();
        add(controlPanel, BorderLayout.NORTH);

        // 달력 생성 및 추가
        Calendar today = Calendar.getInstance(Locale.KOREA);
        calendarApp.createCalendarPanel(today.get(Calendar.YEAR), today.get(Calendar.MONTH));
        add(calendarApp.getCalendarPanel(), BorderLayout.CENTER);

        // 이벤트 핸들러 등록
        calendarApp.registerButtonHandlers();
    }
}

public class CalendarApp {
    private JLabel monthLabel;
    private JButton prevButton, nextButton, addButton;
    private JPanel calendarPanel;

    public JPanel createControls() {
        monthLabel = new JLabel("", SwingConstants.CENTER);
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        addButton = new JButton("내용 추가");

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(prevButton, BorderLayout.WEST);
        panel.add(monthLabel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.EAST);

        return panel;
    }

    public JPanel createCalendarPanel(int year, int month) {
        calendarPanel = new JPanel(new GridBagLayout());
        updateCalendarPanel(year, month);

        return calendarPanel;
    }

    public void updateCalendarPanel(int year, int month) {
        calendarPanel.removeAll();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);

        int startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday is index 1
        int daysOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        monthLabel.setText(year + "년 " + (month + 1) + "월");

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
                calendarPanel.add(createButton(year, month, (i - startDayOfWeek) + 1), gridBagConstraints);
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
                showMemoWindow(year, month, day);
            }
        });
        return button;
    }

    public void registerButtonHandlers() {
        prevButton.addActionListener(e -> {
            Calendar today = Calendar.getInstance(Locale.KOREA);
            today.add(Calendar.MONTH, -1);
            updateCalendarPanel(today.get(Calendar.YEAR), today.get(Calendar.MONTH));
        });

        nextButton.addActionListener(e -> {
            Calendar today = Calendar.getInstance(Locale.KOREA);
            today.add(Calendar.MONTH, 1);
            updateCalendarPanel(today.get(Calendar.YEAR), today.get(Calendar.MONTH));
        });

        addButton.addActionListener(e -> {
            // 메모 추가 로직을 따로 정의하지 않고
            // 사용자가 특정 날짜의 버튼을 누르면 showMemoWindow() 호출을 사용하게 됩니다.
        });
    }

    public JPanel getCalendarPanel() {
        return calendarPanel;
    }

    // 메모 창 메소드
    private void showMemoWindow(int year, int month, int day) {
        JFrame memoFrame = new JFrame(year + "년 " + (month + 1) + "월 " + day + "일 메모");

        memoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        String fileName = String.format("src/Timy/Calendar/Datafile/memo_%d_%02d_%02d.txt", year, month + 1, day);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("메모 저장 중 오류가 발생했습니다. " + e.getMessage());
            e.printStackTrace();
        }
    }
    private String loadMemo(int year, int month, int day) {
        String fileName = String.format("memo_%d_%02d_%02d.txt", year, month + 1, day);
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
