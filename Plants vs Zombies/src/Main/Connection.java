package Main;

import Command.FirstCommandHandler;
import org.json.simple.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Connection {
    private User user;
    private ArrayList<Menu> menus = new ArrayList<>();
    private DataOutputStream dataOutputStream;
    private Thread thread;
    private Socket socket;

    public void popMenu() throws Exception {
        if((!menus.isEmpty()) ) {
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for(Menu menu : menus)
                System.err.println(menu.getCommandHandlerName());
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            String name = menus.get(menus.size() - 1).getCommandHandlerName();
            while(menus.get(menus.size() - 1).getCommandHandlerName().equals(name))
                menus.remove(menus.size() - 1);
            if (menus.isEmpty()) {
                thread.wait();
            }
          //  send("popMenu", null);
            getCurrentMenu().run();
        }
    }

    public void popMenuWithoutRun() throws Exception {
        if((!menus.isEmpty()) ) {
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for(Menu menu : menus)
                System.err.println(menu.getCommandHandlerName());
            System.err.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            String name = menus.get(menus.size() - 1).getCommandHandlerName();
            while(menus.get(menus.size() - 1).getCommandHandlerName().equals(name))
                menus.remove(menus.size() - 1);
            if (menus.isEmpty()) {
                thread.wait();
            }
            //  send("popMenu", null);
        }
    }

    public void popDoubleMenu() throws Exception {
        if(menus.size() >= 2) {
            popMenuWithoutRun();
            popMenu();
           // send("popDoubleMenu", null);
        }
    }

    public void pushMenu(Menu menu) {
        if(menus.isEmpty() || !menu.equals(menus.get(menus.size()-1))) {
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
    }

    // AI mode
    public Connection(User user) {
        this.user = user;
    }

    public Connection(Socket socket, DataOutputStream dataOutputStream) {
        this.socket = socket;
        this.dataOutputStream = dataOutputStream;
        Server.getDataOutputStreams().add(dataOutputStream);
        thread = new Thread(() -> {
            DataInputStream dataInputStream = null;
            try {
                System.out.println("some Client accepted");
                dataInputStream = new DataInputStream(socket.getInputStream());
                new Menu(this, new FirstCommandHandler()).run();
                DataInputStream finalDataInputStream = dataInputStream;
                String line = "";
                new Thread(()->{
                    while(!socket.isClosed()){
                        try {
                            //if(!getCurrentMenu().getCommandHandlerName().contains("Zombie"))
                            receive("{\"command\":\"end turn\"}");
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                while (!line.equals("exit")) {
                    line = finalDataInputStream.readUTF();
                    System.out.println(line);
                    if (!line.equals("exit")) {
                        receive(line);
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

    void receive(String message) throws Exception {
        System.out.println("Client: " + message);
        if (message.equals("exit")) {
            thread.wait();
            dataOutputStream.close();
            socket.close();
        }
        getCurrentMenu().accept(message);
    }

    public void send(String command, Object data) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("command", command);
            jsonObject.put("data", data);
            String message = jsonObject.toJSONString();
            System.out.println("Server: " + message);
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
