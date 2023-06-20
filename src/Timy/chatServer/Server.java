package Timy.chatServer;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import Timy.*;
import Timy.chat.Client;

public class Server extends JFrame {

    private ServerSocket serverSocket = null;
    private Socket socket = null;

    private Client client;

    public Server() {}

    public void startServer() {
        try {
            System.out.println("[SERVER OPENED]");
            System.out.println("[클라이언트 연결대기중]");
            serverSocket = new ServerSocket(8080);

            while (true) {
                socket = serverSocket.accept();

                ReceiveThread receiveThread = new ReceiveThread(socket);
                receiveThread.start();
                System.out.println(receiveThread.getName());

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket!=null) {
                try {
                    serverSocket.close();
                    System.out.println("[서버종료]");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("[서버소켓통신에러]");
                }
            }
        }
    }

    public static void main(String[] args) {
        Server serverEx = new Server();
        serverEx.startServer();
    }
}
