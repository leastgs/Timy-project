package Timy;


import javax.swing.*;
import java.awt.*;

public class Timy extends JFrame {

    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    public Timy() {
        setTitle("Timy");
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




        setVisible(true);
    }


}
