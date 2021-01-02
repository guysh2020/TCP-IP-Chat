import java.io.*;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
    Socket socket = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    StringConsumer consumer = null;

    public ConnectionProxy(Socket s) {
        try {
            socket = s;
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message = "";
        try {
            while (socket.isConnected()) {
                message = dis.readUTF();
                if (message == "disconnect") {
                    consumer.consume(message);
                    break;
                }
                consumer.consume(message);
            }
            removeConsumer(consumer);
        } catch (IOException e) {
        }
    }

    @Override
    public void consume(String str) throws IOException {
        dos.writeUTF(str);
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        if (sc != null) {
            consumer = sc;
        }
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        try {
            consumer = null;
            if (dis != null) {
                dis.close();
            }

            if (dos != null) {
                dos.flush();
                dos.close();
            }

            if (socket != null)
                socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}