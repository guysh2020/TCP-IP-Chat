import java.io.IOException;

public class ClientDescriptor implements StringConsumer, StringProducer {
    private StringConsumer messageBoard;
    private String nickName ;

    public ClientDescriptor() {
        messageBoard = null;
        nickName = "";
    }

    @Override
    public void consume(String str) throws IOException {
        if(nickName == ""){
            nickName =str;
            str += " joined the chat";
        }
        else{
            str = nickName + ": " + str ;
        }
        messageBoard.consume(str);
    }

    @Override
    public void addConsumer(StringConsumer sc) {
        if(sc != null)
            messageBoard = sc;
    }

    @Override
    public void removeConsumer(StringConsumer sc) {
        if(sc != null)
            messageBoard = null;
    }
}

