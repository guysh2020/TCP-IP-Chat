import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientGUI extends Thread implements StringConsumer, StringProducer {
    private StringConsumer consumer;
    @Override
    public void consume(String str) throws IOException {
        System.out.println(str);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String a = scanner.nextLine();
                consumer.consume(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {

    }
}
