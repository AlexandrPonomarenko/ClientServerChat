package clientchat;

import java.util.EventListener;

public interface MessageListener extends EventListener {
    public void getMessage(MessageEventObject evt);
    public void disconnect(MessageEventObject evt);
}
