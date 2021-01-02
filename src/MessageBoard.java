import java.io.IOException;
import java.util.ArrayList;

public class MessageBoard implements StringConsumer, StringProducer {
    private ArrayList<ConnectionProxy> proxys;

    public MessageBoard() {
        proxys = new ArrayList<>();
    }

    @Override
    public void consume(String str) {
        try {
            boolean flag = false;
            StringConsumer tmp = null;
            for (ConnectionProxy proxy : proxys) {
                if (proxy.getState() != Thread.State.TERMINATED)
                    proxy.consume(str);
                else  {
                    flag = true;                    tmp = proxy;
                }
            }
            if (flag)
                removeConsumer(tmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        if (sc != null)
            proxys.add((ConnectionProxy) sc);
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        if (sc != null)
            proxys.remove(sc);
    }
}
