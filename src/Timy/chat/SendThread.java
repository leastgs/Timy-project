package Timy.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class SendThread extends Thread {
    private Socket socket = null;
    private String name;


    SendThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.println(name);
            out.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String outputMsg = reader.readLine();
                out.println(outputMsg);
                out.flush();
                if("나가기버튼누름".equals(outputMsg)) {
                    System.out.println("[서버 연결종료]");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
