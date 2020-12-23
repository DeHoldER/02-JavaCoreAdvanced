package HomeWork07;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    AuthService authService;
    ServerApp server;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    Client client;

    ClientHandler(AuthService authService, ServerApp server, Socket socket) {
        this.authService = authService;
        this.server = server;
        this.socket = socket;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            if (!authorize(dataInputStream, dataOutputStream)) {
                // Удаляемся из сервера
                server.onClientDisconnected(this, socket, dataInputStream, dataOutputStream);
                return;
            }
            server.onNewClient(this);
            messageListener(dataInputStream);

        } catch (IOException e) {
            server.onClientDisconnected(this, socket, dataInputStream, dataOutputStream);
            e.printStackTrace();
        }
    }

    void sendMessage(Client client, String text) {
        try {
            dataOutputStream.writeUTF(client.name + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String privateMessage, Client client, String text) {
        try {
            dataOutputStream.writeUTF(privateMessage + client.name + ": " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean authorize(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF("Пожалуйста, ведите логин и пароль через пробел!");
        // Цикл ожидания авторизации клиентов
        int tryCount = 0;
        int maxTryCount = 5;
        while (true) {
            // Читаем команду от клиента
            String newMessage = dataInputStream.readUTF();
            // Разбиваем сообщение на составляющие
            String[] messageData = newMessage.split("\\s");
            // Проверяем, команда ли это авторизации
            if (newMessage.startsWith("/auth")) {
                if (messageData.length == 3 && messageData[0].equals("/auth")) {
                    tryCount++;
                    String login = messageData[1];
                    String password = messageData[2];
                    client = authService.authorize(login, password);
                    // Проверяем, зарегистрирован ли клиент
                    if (client != null) {
                        // Если получилось авторизоваться, то выходим из цикла
                        dataOutputStream.writeUTF("Вы вошли в чат под ником " + client.name);
                        break;
                    } else {
                        dataOutputStream.writeUTF("Неверные логин или пароль!");
                    }
                }
            } else {
                dataOutputStream.writeUTF("Ошибка авторизации!");
            }
            if (tryCount == maxTryCount) {
                dataOutputStream.writeUTF("Первышен лимит попыток авторизции!");
                server.onClientDisconnected(this, socket, dataInputStream, dataOutputStream);
                return false;
            }
        }
        return true;
    }

    private void messageListener(DataInputStream dataInputStream) throws IOException {
        while (true) {
            String newMessage = dataInputStream.readUTF();
            if (newMessage.equals("/exit")) {
                server.onClientDisconnected(this, socket, dataInputStream, dataOutputStream);
            } else if (newMessage.startsWith("/w")) {
                String[] privateMessageData = newMessage.split("\\s", 3);
                if (privateMessageData.length == 3) {
                    String recipient = privateMessageData[1];
                    server.onNewPrivateMessage(client, recipient, privateMessageData[2], dataOutputStream);
                }
            } else {
                server.onNewMessage(client, newMessage);
            }

        }
    }
}
