package network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class ClientConnection {
    //    Siin toimub kliendi registreerimine ja sisendiga tegelemine
    public Client client;
    public Location[] locations;

    public ClientConnection() throws IOException {
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener() {

                               @Override
                               public void received(Connection connection, Object object) {

                                   if (object instanceof Location[]) {

                                       // get the list of locations
                                       locations = (Location[]) object;
                                   }
                               }
                           }
        );

        client.connect(5000, "localhost", 3000, 3001);

    }
}
