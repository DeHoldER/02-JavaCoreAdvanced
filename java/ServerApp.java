import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerApp {

    List<Message> messages = new ArrayList<>();
    List<ClientHandler> clients = new ArrayList<>();

    ServerApp() {
        try {
            ServerSocket serverSocket = new ServerSocket(8082);
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

    synchronized void onNewMessage(Client sender, String text) {
        Message message = new Message(getDateAndTime(), sender, text);
        messages.add(message);
        System.out.println("log: " + sender.name + ": " + text);
        // Рассылаем сообщения всем
        for (int i = 0; i < clients.size(); i++) {
            ClientHandler recipient = clients.get(i);
            if (!recipient.client.login.equals(sender.login)) {
                recipient.sendMessage(message.time, sender, text);
            }
        }
    }

    synchronized void sendPrivateMessage(Client sender, String toClient, String message, DataOutputStream dataOutputStream) {
        boolean isRecipientExist = false;
        for (int i = 0; i < clients.size(); i++) {
            ClientHandler recipient = clients.get(i);
            if (recipient.client.name.equalsIgnoreCase(toClient)) {
                isRecipientExist = true;
                recipient.sendMessage("Личное сообщение от ", sender, message);
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
            clientHandler.sendMessage(message.time, message.client, message.text);
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
        System.out.println("Сервер запущен. Ожидает подключений...");
        new ServerApp();
    }

    public String getDateAndTime() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy '@' hh:mm:ss");
        return formatForDateNow.format(dateNow);
    }
}
