package server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

public class MyServer {

    /**
     * We want to keep track of all players that are in the game.
     * We use a hashmap (python dictionary) that has their IP as key and player.Player object as value
     * Each player object has coordinates (x and y)
     */
    private final HashMap<java.net.InetSocketAddress, Location> players = new HashMap<>();
    private final Server server;

    public MyServer() throws IOException {

        server = new Server();  // initialize server
        Network.register(server);  // register all the classes that are sent over the network


        // Add listener to tell the server, what to do after something is sent over the network
        server.addListener(new Listener() {

            /**
             * New player connected.
             * We want to make them a new player object to keep track of their coordinates
             */
            public void connected(Connection c) {
                players.put(c.getRemoteAddressUDP(), new Location(0, 0));
                // System.out.println(c.getRemoteAddressUDP().toString() + " connected");

                sendState();  // send info about all players to all players
            }

            /**
             * We received some data from one of the players.
             */
            public void received(Connection c, Object object) {


                // We check if we recieved a character (they want to tell the server, where they want to go).
                if (object instanceof Location) {
                    // Hetkel vaatab kas ta saab info katte
                    System.out.println(c.getRemoteAddressUDP());
                    System.out.println(((Location) object).x + " " + ((Location) object).y);

                    players.put(c.getRemoteAddressUDP(), (Location) object);
                    sendState();
                }
            }

            /**
             * Someone disconnected from the game.
             * Removes that player from the game.
             */
            public void disconnected(Connection c) {
                players.remove(c.getRemoteAddressUDP());
                // System.out.println(c.getRemoteAddressUDP().toString()  + " disconnected");

                sendState();  // send info about all players to all players
            }
        });

        server.bind(3000, 3001);  // set ports for TCP, UDP. They must be equal with clients.
        server.start();  // start the server
    }

    /**
     * Sends all player objects to all players.
     * So everyone can see, where all the players are.
     */
    private void sendState() {
        // Create a player array from the hashmap values
        Location[] playersList = players.values().toArray(new Location[0]);

        // send this array to all of the connected clients
        server.sendToAllUDP(playersList);
    }
}

