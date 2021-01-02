import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientDescriptor implements StringConsumer, StringProducer {
    private StringConsumer messageBoard;
    private String nickName;
    private String msgTime;
    private Date date;
    private SimpleDateFormat sdf;

    public ClientDescriptor() {
        messageBoard = null;
        nickName = null;
    }

    @Override
    public void consume(String str) throws IOException {

        date = new Date();
        sdf = new SimpleDateFormat("hh:mm:ss ");
        msgTime = sdf.format(date);
        if (nickName == null) {
            nickName = str;
            str += " joined the chat.";
        } else {
            str = nickName + ": " + str;
        }
        messageBoard.consume(msgTime + ':' + str + '\n');
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        if (sc != null)
            messageBoard = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        if (sc != null)
            messageBoard = null;
    }
}