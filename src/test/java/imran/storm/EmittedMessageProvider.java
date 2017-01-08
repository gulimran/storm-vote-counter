package imran.storm;

import java.util.ArrayList;
import java.util.List;

public class EmittedMessageProvider<T> {

    private final  List<T> emittedMessages = new ArrayList<>();

    public void reset() {
        emittedMessages.clear();
    }

    public void addMessage(T message) {
        emittedMessages.add(message);
    }

    public List<T> getMessages() {
        return emittedMessages;
    }
}
