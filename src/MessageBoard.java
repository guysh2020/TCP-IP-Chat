import java.io.IOException;
import java.util.ArrayList;

public class MessageBoard implements StringConsumer, StringProducer {
    private ArrayList<StringConsumer> proxys;

    public MessageBoard() {
        proxys = new ArrayList<>();
    }


    @Override
    public void consume(String str) throws IOException {
        for( StringConsumer proxy : proxys) {
            proxy.consume(str);
        }
    }


    @Override
    public void addConsumer(StringConsumer sc) {
        if(sc != null)
            proxys.add(sc);
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        if(sc != null)
            proxys.remove(sc);
    }
}
