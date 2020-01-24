import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Connected");
            startListening();
        }catch (Exception e){
           // MessageBox.showErrorAndExit(e.getMessage());
        }
    }

    public void close() {
        try {
            send("exit");
            dataOutputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(DataOutputStream dataInputStream) {
        try {
            send("exit");
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
                String line = "";
                while (!line.equals("exit")) {
                    line = dataInputStream.readUTF();

                }
            } catch (Exception e) {
                MessageBox.showErrorAndExit(e.getMessage());
            }
            close(dataOutputStream);
            System.exit(0);
        });
        thread.start();
    }

    public synchronized void send(String input) throws IOException {
        dataOutputStream.writeUTF(input);
        dataOutputStream.flush();
    }
}