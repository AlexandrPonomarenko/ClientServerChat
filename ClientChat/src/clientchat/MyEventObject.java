
package clientchat;

import java.util.EventObject;


public class MyEventObject extends EventObject
{
    private String userName;
    private String userIpAddress;
    public MyEventObject(Object source, String userName, String userIpAddress) {
        super(source);
        this.userName = userName;
        this.userIpAddress = userIpAddress;
    }
    public String getUserName()
    {
        return this.userName;
    }
    
    public String getUserIpAddress()
    {
        return this.userIpAddress;
    }
}
