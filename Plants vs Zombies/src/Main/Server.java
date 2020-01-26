package Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<DataOutputStream> dataOutputStreams = new ArrayList<>();

    private static ArrayList<Connection> connections = new ArrayList<>();

    public static ArrayList<DataOutputStream> getDataOutputStreams() {
        return dataOutputStreams;
    }

    public static ArrayList<Connection> getConnections() {
        return connections;
    }

    public static Connection getConnectionByUsername(String username) throws Exception {
        for (Connection connection : connections) {
            if(connection.getUser()!=null && connection.getUser().getUsername().equals(username)){
                return connection;
            }
        }
        throw new Exception("This user is not online");
    }

    public static void run(int port) throws Exception {
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(5000);
                    User.saveAllUsers();
                    System.out.println("All users saved");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            while (true) {
                Socket socket = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                connections.add(new Connection(socket, dataOutputStream));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        User.saveAllUsers();
    }
}