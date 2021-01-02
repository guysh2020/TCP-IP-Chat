import java.io.IOException;

public interface StringProducer {
    public void addConsumer(StringConsumer sc);

    public void removeConsumer(StringConsumer sc) throws IOException;
}
