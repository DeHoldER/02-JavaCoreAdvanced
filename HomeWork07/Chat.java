package HomeWork07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Chat {

    Chat() {
        try {
            Socket socket = new Socket("localhost", 8081);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String newMessage = dataInputStream.readUTF();
                        System.out.println(newMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            new Thread(() -> {
                try {
                    while (true) {
                        String newMessage = scanner.nextLine();
                        dataOutputStream.writeUTF(newMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Chat();
    }
}
