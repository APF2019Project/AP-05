package Helper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String token="";

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Connected");
            startListening();
        } catch (Exception e) {
            MessageBox.showErrorAndExit(e.getMessage());
        }
    }

    public void close() {
        try {
            sendExit();
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(DataOutputStream dataInputStream) {
        try {
            sendExit();
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        Thread thread = new Thread(() -> {
            try {
                send("hand shake",new JSONObject());
                String line="";
                while (!line.equals("exit")) {
                    line = dataInputStream.readUTF();
                    System.out.println("server send:"+line);
                    JSONObject messageJsonObject = (JSONObject) new JSONParser().parse(line);
                    if(messageJsonObject.containsKey("token")){
                        token=messageJsonObject.get("token").toString();
                    }else {
                        MenuHandler.receive(line);
                    }
                }
            } catch (Exception e) {
                MessageBox.showErrorAndExit(e.getMessage());
            }
            close(dataOutputStream);
            System.exit(0);
        });
        thread.start();
    }

    public synchronized void sendExit() throws IOException {
        send("exit",new JSONObject());
    }

    public synchronized void send(String command, Object data) throws IOException {
        System.out.println("Sending");
        System.out.println(command);
        System.out.println(data);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        jsonObject.put("command", command);
        jsonObject.put("data", data);
        String message = jsonObject.toJSONString();
        System.out.println("we send:"+message);
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }
}