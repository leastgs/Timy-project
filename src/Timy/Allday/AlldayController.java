package Timy.Allday;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AlldayController {
    // 클래스 변수
    private final String fileName;
    private static final String FILE_NAME = "Allday.txt"; // 파일 이름 상수화
    private final DefaultListModel<String> listModel;
    private final Map<Integer, Color> itemColors;

    public AlldayController(String fileName) {
        this.fileName = fileName;
        this.listModel = new DefaultListModel<>();
        this.itemColors = new HashMap<>();
        loadFile();
    }

    public void startApp() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("All-Day");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);
            frame.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.weightx = 1;
            constraints.fill = GridBagConstraints.HORIZONTAL;

            JLabel label = new JLabel("매일 할 일");
            label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
            label.setForeground(Color.BLUE);
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
            addButton.setForeground(Color.GREEN);
            addButton.setPreferredSize(new Dimension(30, 20));
            constraints.gridx = 4;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            panel.add(addButton, constraints);

            JButton deleteButton = new JButton("삭제"); // 삭제 버튼 생성
            deleteButton.setForeground(Color.RED); // 버튼 텍스트 색상 변경
            deleteButton.setPreferredSize(new Dimension(30, 20));
            constraints.gridx = 5; // 위치 조정
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            panel.add(deleteButton, constraints); // 패널에 삭제 버튼 추가

            JButton resetButton = new JButton("초기화"); // 삭제 버튼 생성
            resetButton.setForeground(Color.black); // 버튼 텍스트 색상 변경
            resetButton.setPreferredSize(new Dimension(30, 20));
            constraints.gridx = 6; // 위치 조정
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            panel.add(resetButton, constraints); // 패널에 삭제 버튼 추가

            JList<String> allDayList = new JList<>(listModel);
            allDayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


            JScrollPane scrollPane = new JScrollPane(allDayList);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            frame.add(scrollPane, BorderLayout.CENTER);

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String task = textField.getText().trim();
                    if (!task.isEmpty()) {
                        listModel.addElement(task);
                        writeFile(task);   //add 버튼을 누를 때마다 파일에 쓴다.
                    }
                    textField.setText("");
                }
            });

            // 삭제 버튼에 대한 이벤트 리스너 추가
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = allDayList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        listModel.remove(selectedIndex);
                    }
                }
            });
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = allDayList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        listModel.remove(selectedIndex);
                        deleteLineFromFile(FILE_NAME, selectedIndex); // 파일에서 해당 줄 삭제
                    }
                }
            });
            allDayList.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                    if (itemColors.containsKey(index)) {
                        c.setBackground(itemColors.get(index));

                        // 배경이 파란색일 때 배경색을 흰색으로 설정
                        c.setForeground(Color.WHITE);
                    } else {
                        c.setBackground(list.getBackground());
                        c.setForeground(list.getForeground());
                    }

                    return c;
                }
            });
            allDayList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int selectedIndex = allDayList.getSelectedIndex();
                        if (selectedIndex != -1) {
                            // 항목의 배경색을 파란색/기본값으로 전환
                            if (itemColors.remove(selectedIndex) == null) {
                                itemColors.put(selectedIndex, Color.BLUE);
                            }
                            // 목록을 다시 칠하고 업데이트된 배경색을 표시
                            allDayList.repaint();
                        }
                    }
                }
            });
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    itemColors.clear(); // 리스트 항목의 모든 배경색을 초기화
                    allDayList.repaint(); // 목록을 다시 그려서 변경된 배경색을 보이게 함
                }
            });


            frame.add(panel, BorderLayout.NORTH);

            frame.setVisible(true);
        });

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
    private void loadFile() {
        // 파일 불러오기
        String line;
        DefaultListModel<String> listModel = new DefaultListModel<>();
        Map<Integer, Color> itemColors = new HashMap<>();

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {  // try-with-resources 문 사용
            while ((line = in.readLine()) != null) { // 파일에서 한 줄씩 읽어서 리스트에 추가
                listModel.addElement(line);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("파일을 찾을 수 없습니다 : " + ex.getMessage());
        } catch (IOException ex) {
            System.err.println("파일을 읽는 중 오류가 발생하였습니다 : " + ex.getMessage());
        }
    }

    // ... 기존의 변수 및 메서드 설정 ...
}
