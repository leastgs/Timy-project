package Timy.chatServer;



import Timy.chat.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.*;

class ReceiveThread extends Thread {

    private static List<PrintWriter> list =
            Collections.synchronizedList(new ArrayList<>());


    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private Client client;

    ReceiveThread (Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            list.add(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String name = "";
        try {
            name = in.readLine();
            System.out.println("[" + name + "님 연결성공!]");
            System.out.println("[연결된 사람: "+name+"]");

            sendAll("[" + name + "]님 접속!");

            while (in != null) {
                String inputMsg = in.readLine();
                if("나가기버튼누름".equals(inputMsg)) { break; }

                if ("[IMAGE]".equals(inputMsg)) {
                    try {
                        String base64ImageData = in.readLine();
                        byte[] imageData = Base64.getDecoder().decode(base64ImageData);

                        // Process the received image data
                        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                        BufferedImage image = ImageIO.read(bais);
                        // Do something with the image, such as displaying it in the GUI

                        continue;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                sendAll(name + "님이 보낸 메시지 >> " + inputMsg);
            }

        } catch (IOException e) {
            System.out.println("[" + name + " 접속끊김]");
        } finally {
            sendAll("[" + name + "]님이 나가셨습니다");
            list.remove(out);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[" + name + " 연결종료]");
    }

    private synchronized void sendAll (String s) {
        for(PrintWriter out: list) {
            out.println(s);
            out.flush();
        }
    }


}
