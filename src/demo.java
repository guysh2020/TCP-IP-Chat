import java.io.IOException;
import java.net.Socket;

public class demo {
    public static void main(String args[]) throws IOException {
        try{
            Socket socket = new Socket("127.0.0.1",1300);
            ConnectionProxy proxy = new ConnectionProxy(socket);
            ClientGUI client = new ClientGUI();
            proxy.addConsumer(client);
            client.addConsumer(proxy);
            proxy.start();
            client.start();

            String name = "ido";
            proxy.consume(name);



        }catch (IOException e) {

        }

    }
}
