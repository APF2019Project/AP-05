package Main;

import Command.FirstCommandHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class Connection {

    private static HashMap<String, Connection> tokenToConnection = new HashMap<String, Connection>();
    private User user;
    private ArrayList<Menu> menus = new ArrayList<>();
    private DataOutputStream dataOutputStream;
    private Thread thread;
    private Socket socket;
    private String token;
    private int howManyTimeIgnoreEndTurn;

    // AI mode
    public Connection(User user) {
        this.user = user;
        user.setConnection(this);
    }

    public Connection(Socket socket, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataOutputStream = dataOutputStream;
        Server.getDataOutputStreams().add(dataOutputStream);
        howManyTimeIgnoreEndTurn = 2;
        thread = new Thread(() -> {
            DataInputStream dataInputStream = null;
            try {
                System.out.println("some Client accepted");

                dataInputStream = new DataInputStream(socket.getInputStream());

                DataInputStream finalDataInputStream = dataInputStream;
                new Thread(() -> {
                    try {
                        Thread.sleep(1500);
                        while (!socket.isClosed()) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("command", "end turn");
                            jsonObject.put("token", getToken());
                            receive(jsonObject.toString());
                            if (!getCurrentMenu().getCommandHandlerName().contains("Play"))
                                Thread.sleep(2000);
                            else
                                Thread.sleep(1000);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

                String line = "";
                while (!isExit(line)) {
                    line = finalDataInputStream.readUTF();
                    System.out.println(line);
                    JSONObject jsonObject = (JSONObject) new JSONParser().parse(line);
                    if (jsonObject.get("command").toString().equals("hand shake")) {
                        sendNewToken();
                        Thread.sleep(500);
                        new Menu(this, new FirstCommandHandler()).run();
                    }
                    else {
                        if (!isExit(line)) {
                            receive(line);
                        }
                    }
                }
                System.out.println("Closing connection");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    Server.getDataOutputStreams().remove(dataOutputStream);
                    if (dataInputStream != null) {
                        dataInputStream.close();
                    }
                    Server.getDataOutputStreams().remove(dataOutputStream);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    static public synchronized HashMap<String, Connection> getTokenToConnection() {
        return tokenToConnection;
    }

    static public synchronized Collection<Connection> getAllConnection() {
        return getTokenToConnection().values();
    }

    static private Connection findConnectionByToken(String token) {
        return tokenToConnection.get(token);
    }

    static boolean isExit(String message) throws ParseException {
        if (message.equals("")) return false;
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(message);
        return (jsonObject.get("command").toString().equals("exit"));
    }

    static void receive(String message) throws Exception {
        System.out.println("Client: " + message);
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(message);
        String token = jsonObject.get("token").toString();
        Connection connection = findConnectionByToken(token);
        connection.sendNewToken();
        connection.getCurrentMenu().accept(message);

    }

    public String getToken() {
        return token;
    }

    public int getHowManyTimeIgnoreEndTurn() {
        return howManyTimeIgnoreEndTurn;
    }

    public void setHowManyTimeIgnoreEndTurn(int howManyTimeIgnoreEndTurn) {
        this.howManyTimeIgnoreEndTurn = howManyTimeIgnoreEndTurn;
    }

    String tokenGenerator() {
        Random random = new Random();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            token.append(random.nextInt(26) + 'a');
        }
        return token.toString();
    }

    public void sendNewToken() {
        this.token = tokenGenerator();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            getTokenToConnection().remove(token);
            getTokenToConnection().put(token, this);
            String message = jsonObject.toJSONString();
            System.out.println("Server: " + message);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void popMenu() throws Exception {
        if ((!menus.isEmpty())) {
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for (Menu menu : menus)
                System.err.println(menu.getCommandHandlerName());
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            String name = menus.get(menus.size() - 1).getCommandHandlerName();
            while (menus.get(menus.size() - 1).getCommandHandlerName().equals(name))
                menus.remove(menus.size() - 1);
            if (menus.isEmpty()) {
                thread.wait();
            }
            //  send("popMenu", null);
            getCurrentMenu().run();
        }
    }

    public void popMenuWithoutRun() throws Exception {
        if ((!menus.isEmpty())) {
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for (Menu menu : menus)
                System.err.println(menu.getCommandHandlerName());
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            String name = menus.get(menus.size() - 1).getCommandHandlerName();
            while (menus.get(menus.size() - 1).getCommandHandlerName().equals(name))
                menus.remove(menus.size() - 1);
            if (menus.isEmpty()) {
                thread.wait();
            }
            //  send("popMenu", null);
        }
    }

    public void popDoubleMenuHandler() throws Exception {
        if (menus.size() >= 2) {
            send("popDoubleMenu", null);
            popMenuWithoutRun();
            popMenu();
        }
    }

    public void popDoubleMenu() throws Exception {
        if (GameMenuSwitcher.getGameStatus().equals(GameStatus.OnGame)) {
            getUser().getPlayer().getMap().getPlantPlayer().getConnection().popDoubleMenuHandler();
            getUser().getPlayer().getMap().getZombiePlayer().getConnection().popDoubleMenuHandler();
        }
        else
            popDoubleMenuHandler();
    }

    public void pushMenu(Menu menu) {
        if (menus.isEmpty() ||
                !menu.getCommandHandlerName().equals(menus.get(menus.size() - 1).getCommandHandlerName())) {
            menus.add(menu);
        }
    }

    public Menu getCurrentMenu() {
        return menus.get(menus.size() - 1);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        user.setConnection(this);
    }

    public synchronized void send(String command, Object data) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", command);
            jsonObject.put("data", data);
            String message = jsonObject.toJSONString();
            System.out.println("Server: " + message);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
