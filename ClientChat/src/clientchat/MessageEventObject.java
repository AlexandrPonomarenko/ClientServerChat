package clientchat;

import java.util.EventObject;

public class MessageEventObject extends EventObject {
    private String message;
    public MessageEventObject(Object source) {
        super(source);
    }
    
    public MessageEventObject(Object source, String message) {
        super(source);
        this.message = message;
    }
    
    
    
    public String getMessage()
    {
        return this.message;
    }
}




