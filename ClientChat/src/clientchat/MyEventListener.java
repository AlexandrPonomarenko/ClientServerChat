package clientchat;

import java.util.EventListener;


public interface MyEventListener extends EventListener
{
    public void enterButtonClicked(MyEventObject evt);
}
