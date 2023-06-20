package Timy.chat;


import Timy.DB.DataBaseManagement;
import Timy.Login.Login;
import Timy.GUI.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class Client {
    private Socket socket;
    private InetAddress ip;
    private BufferedReader in;
    private PrintStream out;
    private String name;

    private Gui gui;
    private Login login;
    private Client client;
    private SendThread sendThread;

    public Client() {}

    public void startClient() {
        try {
            if(login == null) {
                System.out.println("Login 객체 X");
                return;
            }
            ip = InetAddress.getByName("172.20.10.5");
            socket = new Socket(ip, 8000);

            System.out.println("[서버와 연결되었습니다]");
            out = new PrintStream(socket.getOutputStream());
            DataBaseManagement DB = new DataBaseManagement();
            name = (DB.userIdCode);
            out.println(name);
            out.flush();

//            sendThread = new SendThread(socket,name);
//            sendThread.start();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread receiveTHREAD = new Thread(() -> {
                try {
                    String inputMsg;
                    while ((inputMsg = in.readLine()) != null) {
                        String finalInputMSG = inputMsg;
                        SwingUtilities.invokeLater(() -> gui.displayMessage(finalInputMSG));
//                        gui.displayMessage(inputMsg);
                        if (("[" + name + "]님이 나가셨습니다").equals(inputMsg)) {
                            System.out.println("[서버 접속종료]");
                            break;
                        }
                    }
                } catch (IOException e) {
                    if(!socket.isClosed()) {
                        e.printStackTrace();
                        SwingUtilities.invokeLater(() -> gui.displayMessage("[" + name + "님 서버 접속끊김]"));
                    }
                }
            });
            receiveTHREAD.start();
            System.out.println(receiveTHREAD.getName());
        } catch (IOException e) {
            e.printStackTrace();
//            gui.displayMessage("["+ name + "님 서버 접속끊김]");
        }
//        finally {
//            try {
//                if (socket != null) { socket.close(); }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("[서버 연결종료]");

    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
            out.flush();
        }
    }

    public void sendImage(File file) {
        try {
            BufferedImage image = ImageIO.read(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            byte[] imageData = baos.toByteArray();

            String base64ImageData = Base64.getEncoder().encodeToString(imageData);

            out.println("[IMAGE]");
            out.flush();

            // Send the size of the image data
            out.println(base64ImageData);
            out.flush();

            // Send the image data
            socket.getOutputStream().write(imageData);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setGui(Gui gui) {
        this.gui=gui;
    }

    public void setLogin(Login login) {
        this.login=login;
    }
}
