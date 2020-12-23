package HomeWork07;

import java.util.ArrayList;
import java.util.List;

public class AuthService {
    List<Client> clients = new ArrayList();

    AuthService() {
        clients.add(new Client("Pavel", "pavel1", "1234"));
        clients.add(new Client("Oleg", "oleg1", "1234"));
        clients.add(new Client("Nick", "nick1", "4321"));
    }

    synchronized Client authorize(String login, String password) {
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if (client.login.equals(login) && client.password.equals(password)) {
                return client;
            }
        }
        return null;
    }

}
