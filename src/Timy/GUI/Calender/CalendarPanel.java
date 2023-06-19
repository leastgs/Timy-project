package Timy.GUI.Calender;

import javax.swing.*;
import java.awt.*;

public class CalendarPanel extends JPanel {
    private Calendar calendar;

    public CalendarPanel() {
        setLayout(new BorderLayout());

        calendar = new Calendar();

        JPanel controlPanel = calendar.createControls();
        add(controlPanel, BorderLayout.NORTH);

        calendar.createCalendarPanel();
        JPanel calendarPanel = calendar.getCalendarPanel();
        calendarPanel.setPreferredSize(new Dimension(500, 500));
        add(calendar.getCalendarPanel(), BorderLayout.CENTER);

        calendar.registerButtonHandlers();
    }
}
