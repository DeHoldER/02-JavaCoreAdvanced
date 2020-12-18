package HomeWork06;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private boolean isConnected;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client() {
        try {
            openConnection();
            System.out.println("Вы подключились к серверу...");
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        Scanner scanner = new Scanner(System.in);

        Thread incoming = new Thread(() -> {
            try {
                while (true) {
                    String strFromServer = in.readUTF();
                    if (strFromServer.equalsIgnoreCase("/end")) {
                        closeConnection();
                        System.out.println("Сервер завершил соединение");
                        isConnected = false;
                        break;
                    }
                    System.out.println("Сервер: " + strFromServer);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread upcoming = new Thread(() -> {
            while (true) {
                String readConsole = scanner.nextLine();
                try {
                    if (isConnected) {
                        out.writeUTF(readConsole);
                    } else System.out.println("Нет подключения");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        incoming.start();
        upcoming.start();
    }

    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}