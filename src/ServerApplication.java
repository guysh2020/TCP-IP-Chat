import jdk.nashorn.internal.objects.NativeUint8Array;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {

    public static void main(String args[]) {

        ServerSocket server = null;
        MessageBoard mb = new MessageBoard();

        try {
            server = new ServerSocket(1300, 5);
        } catch (IOException e) {
            System.out.println(e);
        }

        Socket socket = null;
        ClientDescriptor client = null;
        ConnectionProxy connection = null;
        InputStream is = null;
        OutputStream os = null;
        while (true) {
            try {
                socket = server.accept();
                is = socket.getInputStream();
                os = socket.getOutputStream();
                connection = new ConnectionProxy(socket);
                client = new ClientDescriptor();
                connection.addConsumer(client);
                client.addConsumer(mb);
                mb.addConsumer(connection);
                connection.start();

            } catch (IOException e) {
            }
        }
    }
}