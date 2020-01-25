package Main;

import Command.LoginCommandHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private User user;
    private Menu menu;
    private DataOutputStream dataOutputStream;
    private Thread thread;
    private Socket socket;

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
        this.socket=socket;
        this.dataOutputStream=dataOutputStream;
        Server.getDataOutputStreams().add(dataOutputStream);
        thread=new Thread(() -> {
            DataInputStream dataInputStream = null;
            try {
                System.out.println("some Client accepted");
                menu=new Menu(this, new LoginCommandHandler());
                menu.run();
                dataInputStream = new DataInputStream(socket.getInputStream());

                String line = "";
                while (!line.equals("exit")) {
                    line = dataInputStream.readUTF();
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

    public Thread getThread() {
        return thread;
    }

    void receive(String message) throws Exception {
        System.out.println("Client: "+message);
        if(message.equals("exit")){
            thread.wait();
            dataOutputStream.close();
            socket.close();
        }
        menu.accept(message.toLowerCase());
    }

    public void send(String message) throws Exception {
        System.out.println("Server: "+message);
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }
}
