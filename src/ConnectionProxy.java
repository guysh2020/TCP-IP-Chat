import java.io.*;
import java.net.Socket;


public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
    Socket socket = null;
    InputStream is = null;
    OutputStream os = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    StringConsumer consumer = null;

    public ConnectionProxy(Socket s) {
        try {

            socket = s;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        String message = "";
        while (true) {
            try {
                message = dis.readUTF();
                consumer.consume(message);
                message = "";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void consume(String str) throws IOException {
        dos.writeUTF(str);
    }

    
    @Override
    public void addConsumer(StringConsumer sc) {
        if (sc != null){
            consumer = sc;
        }
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        consumer = null;

        try{
            if(dis != null)
                dis.close();

            if(dos != null)
                dos.close();

            if(socket != null)
                socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
