
package server__chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;


public class ServerTreadLogic  implements Runnable
{
    BufferedReader reader;
    PrintWriter user;
    Socket socket;
    UserColAndClientCol usc;
    private ArrayList clientConnections;
    public ServerTreadLogic(Socket clienSocket, PrintWriter winter, ArrayList clientConnections)
    {
        usc = new UserColAndClientCol(clientConnections);
        user = winter;
        this.clientConnections = clientConnections;
        try
        {
            socket = clienSocket;
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isReader);
            
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() 
    {
        String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
        String[] data;
        
        
        try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    System.out.print("Received: " + message + "\n");
                    data = message.split(":");
                    
                    for (String token:data) 
                    {
                        System.out.print(token + "\n");
                    }

                    if (data[2].equals(connect)) 
                    {
                        usc.tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        usc.addUsers(data[0]);
                    } 
                    else if (data[2].equals(disconnect)) 
                    {
                        usc.tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        usc.userRemove(data[0]);
                    } 
                    else if (data[2].equals(chat)) 
                    {
                        usc.tellEveryone(message);
                    } 
                    else 
                    {
                        System.out.print("No Conditions were met. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                System.out.print("Lost a connection. \n");
                ex.printStackTrace();
                clientConnections.remove(user);
             }
    }
 
    
}
