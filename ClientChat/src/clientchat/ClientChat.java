
package clientchat;

import java.io.IOException;
import javax.swing.JFrame;


public class ClientChat 
{

    
    public static void main(String[] args) throws IOException
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                Client client = new Client();
                client.setVisible(true);
                client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
            }
        });
    }
    
}
