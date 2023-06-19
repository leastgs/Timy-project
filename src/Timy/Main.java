package Timy;

import Timy.GUI.Timy;
import Timy.Login.Login;
import Timy.SignUp.SignUp;

import javax.swing.*;
import java.awt.*;

class Main extends JFrame {
    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;

    private Image background;

    public Main() {
        setTitle("Ex");
        setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        try {
//            background = new ImageIcon(Objects.requireNonNull(Main.class.getResource("이미지 경로"))).getImage();
//        } catch (NullPointerException e) {
//            JOptionPane.showMessageDialog(null,"이미지 로드 x");
//            System.exit(1);
//        }

        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(background != null) {
            g.drawImage(background,0,0,null);
        }
    }

    private void style() {

    }

    public static void main(String[] args) {
        new Login();
    }
}