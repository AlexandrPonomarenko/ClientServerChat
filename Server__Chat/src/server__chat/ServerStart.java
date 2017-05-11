package server__chat;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//import static server__chat.UserColAndClientCol.clientConnections;


public class ServerStart  implements Runnable
{
    public ArrayList clientConnections;
    public static ArrayList<String> users;
    public ServerStart() 
    {
        run();
    }

    
    @Override
    public void run() 
    {
        clientConnections = new ArrayList();
        users = new ArrayList<>();
        try 
        {
            ServerSocket serverSocket = new ServerSocket(9000);
            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                Thread listener = new Thread(new ServerTreadLogic(clientSocket, writer, clientConnections));
                clientConnections.add(writer);
                
                listener.start();
                System.out.println("Con a connectoion \n");
            }
        } catch (Exception e) 
        {
            System.out.println("Error making a connection \n");
        } 
    }
}
