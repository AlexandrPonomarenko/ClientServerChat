
package clientchat;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;


public class Client  extends JFrame
{
    private String username = "", address = "";
    private ArrayList<String> users = new ArrayList();
    private int port = 9000;
    private Boolean isConnected = false;
    private Pattern pattern;
    private final String regIP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                               + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                               + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                               + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    private Socket sock;
    private BufferedReader reader;
    private PrintWriter writer;
    
    private JPanel mainPanel;
    private FirstPanel panel1;
    private SecondPanel panel2;
    private Dimension displaySize;
    private Date date;
    private SimpleDateFormat sdf;
    
    public Client() 
    {   
        super("Welcome to the chat");
        displaySize = Toolkit.getDefaultToolkit().getScreenSize();
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(1, 1));
        panel1 = new FirstPanel();
        panel2 = new SecondPanel();
        
        panel1.addMyEventListener(new MyEventListener() {
            public void enterButtonClicked(MyEventObject evt) {
                setSecondView(evt.getUserName());
                username = evt.getUserName();
                address = evt.getUserIpAddress();
                connectServer();
            }
        });
        
        panel2.addMyEventListener(new MessageListener() {
            public void getMessage(MessageEventObject evt) 
            {
                sendMassage(evt.getMessage());
            }
            
            public void disconnect(MessageEventObject evt) 
            {
                sendDisconnect();
                dissconnect();
                
            }
        });


        
        mainPanel.add(panel1, BorderLayout.CENTER);
        this.setContentPane(mainPanel);

        this.pack();
        this.setMinimumSize( this.getSize() );
        setJFrameInCenter();
        date = new Date();
        sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    }

    private void setSecondView(String value)
    {
        this.setTitle("Welcome " + value);
        panel2.setUserName(value);
        mainPanel.remove(panel1);
        mainPanel.add(panel2, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
        Dimension size = this.getSize();
        this.setMinimumSize(new Dimension(400, 300));
        correctPositioin(size);
    }
    
    private void setJFrameInCenter()
    {
        Dimension size = this.getSize();
        this.setLocation(displaySize.width / 2 - size.width / 2, displaySize.height / 2 - size.height / 2);
    }
    
    private void correctPositioin(Dimension oldSize)
    {
        Dimension newSize = this.getSize();
        Point p = this.getLocation();
        this.setLocation(p.x + (oldSize.width - newSize.width) / 2, p.y + (oldSize.height - newSize.height) / 2);
    }
    
    private void StartThread()
    {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }
    
    private void userAdd(String name)
    {
        users.add(name);
    }
    
    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    private void sendMassage(String message)
    {
      
        try 
        {
           writer.println(username + ":" + message + ":" + "Chat");
           writer.flush();
        }
        catch (Exception ex)
        {
            panel2.addMessage("Message was not sent. \n");
        }

    }
    
    
    
    public void userRemove(String data) 
    {
         panel2.addMessage(data + " is now offline.\n");
    }
    
     public void sendDisconnect() 
    {
        String bye = (username + ": :Disconnect");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            panel2.addMessage("Could not send Disconnect message.\n");
        }
    }
    
    public void dissconnect() 
    {
        try 
        {
            panel2.addMessage("Соединение отключено.\n");
            sock.close();
        } catch(Exception ex) {
            panel2.addMessage("Не удалось отклчиться. \n");
        }
        isConnected = false;

    }
    
    private void connectServer()
    {
        if (isConnected == false) 
        {             
            try 
            {
                sock = new Socket(panel1.getAddress(), port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                panel2.addMessage("Cannot Connect! Try Again. \n");
            }
            
            StartThread();
            
        } 
    }
    private class IncomingReader implements Runnable
    {

        @Override
        public void run() 
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");
                     
                     if (data[2].equals(chat)) 
                     {
                         panel2.addMessage("" + sdf.format(date), data[0], data[1]);
                     } 
                     else if (data[2].equals(connect))
                     {
                        userAdd(data[0]);
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                         userRemove(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                        writeUsers();
                        users.clear();
                     }
                }
           }catch(Exception ex) { } 
        }
    }
    
}
