package server__chat;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import static server__chat.ServerStart.users;


public class UserColAndClientCol 
{   
    private ArrayList clientConnections;
    public UserColAndClientCol(ArrayList clientConnections)
    {
        this.clientConnections = clientConnections;
    }
 
    
    public void addUsers(String date)
    {
        String message, add = ": :Connect", done = "Server: :Done", name = date;
        System.out.println("Before " + name + " added.");
        users.add(name);
        System.out.println("After " + name + " added.");
        String[] tempList = new String[users.size()];
        users.toArray(tempList);
        
        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void userRemove (String data) 
    {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
    public void tellEveryone(String message) 
    {
	Iterator it = clientConnections.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
		writer.println(message);
                writer.flush();

            } 
            catch (Exception ex) 
            {
                System.out.println("Error telling everyone.");
            }
        } 
    }
}
