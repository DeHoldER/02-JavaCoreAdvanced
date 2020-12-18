package HomeWork06;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            Thread incoming = new Thread(() -> {
                while (true) {
                    String str = null;
                    try {
                        str = in.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (str.equals("/end")) {
                        break;
                    }
                    System.out.println("Клиент: " + str);
                }
            });

            Thread upcoming = new Thread(() -> {
                while (true) {
                    String readConsole = scanner.nextLine();
                    try {
                        out.writeUTF(readConsole);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            incoming.start();
            upcoming.start();

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }
}

