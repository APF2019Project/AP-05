package Main;

import Chat.Message;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.json.simple.JSONObject;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

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
            if (connection.getUser() != null && connection.getUser().getUsername().equals(username)) {
                return connection;
            }
        }
        throw new Exception("This user is not online");
    }

    public static void run(int port) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    User.saveAllUsers();
                    Message.saveAllMessages();
                    System.out.println("All users and messages saved");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10000);
                    HashMap<String, Boolean> allUserState= new HashMap<String,Boolean>();
                    for(User user:User.getAllUsers()){
                        allUserState.put(user.getUsername(),false);
                    }
                    for(Connection connection:Connection.getAllConnection()){
                        User user=connection.getUser();
                        if(user!=null){
                            allUserState.put(user.getUsername(),true);
                        }
                    }
                    for(String username:allUserState.keySet()){
                        if(allUserState.get(username)){
                            System.out.println(username+": Online");
                        }else{
                            System.out.println(username+": OffLine");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    for(Connection connection:Connection.getAllConnection()){
                        if(connection.getHowManyTimeIgnoreEndTurn()==0){
                            try {
                                JSONObject jsonObject=new JSONObject();
                                jsonObject.put("command","end turn");
                                jsonObject.put("token",connection.getToken());
                                connection.receive(jsonObject.toString());
                                if(!connection.getCurrentMenu().getCommandHandlerName().contains("Play"))
                                    connection.setHowManyTimeIgnoreEndTurn(2);
                                else
                                    connection.setHowManyTimeIgnoreEndTurn(1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        connection.setHowManyTimeIgnoreEndTurn(connection.getHowManyTimeIgnoreEndTurn()-1);
                        User user=connection.getUser();
                    }
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
        Message.saveAllMessages();
    }
}