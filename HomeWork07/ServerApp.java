package HomeWork07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerApp {

    List<Message> messages = new ArrayList<>();
    List<ClientHandler> clients = new ArrayList<>();

    ServerApp() {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            AuthService authService = new AuthService();
            // Обработчик клиентов
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    new ClientHandler(authService, this, socket);
                }).start();
            }
        } catch (IOException e) {
            System.out.println("Сервер завершил работу с ошибкой");
            e.printStackTrace();
        }
    }

    synchronized void onNewMessage(Client client, String message) {
        messages.add(new Message(client, message));
        // Рассылаем сообщения всем
        for (int i = 0; i < clients.size(); i++) {
            ClientHandler recipient = clients.get(i);
            if (!recipient.client.login.equals(client.login)) {
                recipient.sendMessage(client, message);
            }
        }
    }

    synchronized void onNewPrivateMessage(Client fromClient, String toClient, String message, DataOutputStream dataOutputStream) {
        boolean isRecipientExist = false;
        for (int i = 0; i < clients.size(); i++) {
            ClientHandler recipient = clients.get(i);
            if (recipient.client.name.equalsIgnoreCase(toClient)) {
                isRecipientExist = true;
                recipient.sendMessage("Личное сообщение от ", fromClient, message);
                break;
            }
        }
        if (!isRecipientExist) {
            try {
                dataOutputStream.writeUTF("Данный пользователь не в сети или не зарегистрирован");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized void onNewClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            clientHandler.sendMessage(message.client, message.text);
        }
        onNewMessage(clientHandler.client, "вошёл в чат");
    }

    synchronized void onClientDisconnected(ClientHandler clientHandler, Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        clients.remove(clientHandler);
        try {
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
            onNewMessage(clientHandler.client, "покиул чат");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Сервер работает...");
        new ServerApp();
    }
}
