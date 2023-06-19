package Timy.GUI.Allday;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AlldayController {
    // 클래스 변수
    private final String fileName;
    private static final String FILE_NAME = "src//Allday.txt"; // 파일 이름 상수화
    private final DefaultListModel<String> listModel;
    private final Map<Integer, Color> itemColors;

    public AlldayController(String fileName) {
        this.fileName = fileName;
        this.listModel = new DefaultListModel<>();
        this.itemColors = new HashMap<>();
        loadFile();
    }

    public JPanel getPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("매일 할 일");
        label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        label.setForeground(Color.black);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(label, constraints);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        textField.setForeground(Color.black);
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        panel.add(textField, constraints);

        JButton addButton = new JButton("추가");
//        addButton.setForeground(Color.GREEN);
        addButton.setBackground(Color.gray);
        addButton.setPreferredSize(new Dimension(30, 20));
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(addButton, constraints);

        JButton deleteButton = new JButton("삭제");
//        deleteButton.setForeground(Color.RED);
        deleteButton.setBackground(Color.gray);
        deleteButton.setPreferredSize(new Dimension(30, 20));
        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(deleteButton, constraints);

        JButton resetButton = new JButton("초기화");
//        resetButton.setForeground(Color.black);
        resetButton.setBackground(Color.gray);
        resetButton.setPreferredSize(new Dimension(30, 20));
        constraints.gridx = 6;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        panel.add(resetButton, constraints);

        JList<String> allDayList = new JList<>(listModel);
        allDayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(allDayList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 7;
        panel.add(scrollPane, constraints);

        addButton.addActionListener(e -> {
            String task = textField.getText().trim();
            if (!task.isEmpty()) {
                listModel.addElement(task);
                itemColors.put(listModel.size() - 1, Color.WHITE); // 추가될 아이템의 색을 하얀색으로 설정
                writeFile(task);
            }
            textField.setText("");
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = allDayList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
                itemColors.remove(selectedIndex);
                deleteLineFromFile(FILE_NAME, selectedIndex);

                // 인덱스 조정
                Map<Integer, Color> newItemColors = new HashMap<>();
                for (Map.Entry<Integer, Color> entry : itemColors.entrySet()) {
                    if (entry.getKey() > selectedIndex) {
                        newItemColors.put(entry.getKey() - 1, entry.getValue());
                    } else {
                        newItemColors.put(entry.getKey(), entry.getValue());
                    }
                }
                itemColors.clear();
                itemColors.putAll(newItemColors);
            }
        });

        allDayList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // 배경색 설정
                Color backgroundColor = itemColors.getOrDefault(index, Color.WHITE);
                c.setBackground(backgroundColor);

                // 글자색 설정 (배경이 파란색이면 글자색을 하얀색으로 설정)
                if (Color.BLUE.equals(backgroundColor)) {
                    c.setForeground(Color.WHITE);
                } else {
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });

        allDayList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = allDayList.locationToIndex(e.getPoint());
                    Color newColor = itemColors.getOrDefault(index, Color.WHITE).equals(Color.WHITE) ? Color.BLUE : Color.WHITE;
                    itemColors.put(index, newColor);
                    allDayList.repaint();
                }
            }
        });

        resetButton.addActionListener(e -> {
            // 리스트의 항목들의 배경색을 초기 상태로 변경
            for (int i = 0; i < listModel.size(); i++) {
                itemColors.put(i, Color.WHITE);
            }
            allDayList.repaint(); // 리스트를 다시 그려 변경된 배경색을 반영
        });

        return panel;
    }

    private void loadFile() {
        // 파일 불러오기
        String line;

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            while ((line = in.readLine()) != null) {
                // 클래스 변수인 listModel을 직접 사용
                listModel.addElement(line);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("파일을 찾을 수 없습니다 : " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("파일을 읽는 중 오류가 발생하였습니다 : " + ex.getMessage());
        }
    }

    private static void writeFile(String content) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(FILE_NAME, true))) { // 텍스트를 파일에 쓸 때마다 덮어쓰는 게 아니라 파일 끝에 추가로 쓰기 위해 true 전달
            out.write(content);
            out.newLine();  // 줄바꿈 추가
        } catch (IOException e) {
            System.err.println("파일에 쓰는 중 오류가 발생하였습니다 : " + e.getMessage());
        }
    }

    public static void deleteLineFromFile(String fileName, int indexToDelete) {
        File inputFile = new File(fileName);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            int currentIndex = 0;

            while ((currentLine = reader.readLine()) != null) {
                if (currentIndex != indexToDelete) {
                    writer.write(currentLine);
                    writer.newLine();
                }
                currentIndex++;
            }

        } catch (IOException e) {
            System.err.println("파일을 삭제하는 중 오류가 발생하였습니다 : " + e.getMessage());
        }

// 원본 파일을 업데이트된 파일로 바꾸기
        if (!inputFile.delete()) {
            System.err.println("파일을 삭제하는 도중 오류 발생 : " + fileName);
            return;
        }

        if (!tempFile.renameTo(inputFile)) {
            System.err.println("파일 이름 변경 오류 : " + tempFile.getName());
        }
    }

    private void clearFile(String fileName) {
        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.print("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
