
package clientchat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.EventListenerList;


public class SecondPanel extends JPanel
{
    private GridBagLayout layout;
    private GridBagConstraints gridBag;
    private EventListenerList listenerList;

    private JTextArea textArea;
    private JScrollPane scroll;
    
    private TextField userMessage;
    private JButton sendMessageButton;
    private JButton disconnectButton;
    
    private String userName;
    
    public SecondPanel()
    {
        listenerList = new EventListenerList();
        layout = new GridBagLayout();
        gridBag = new GridBagConstraints();
        
        createDisplayTextArea();
        createUserMessage();
        createSendButton();
        createDisconnectButton();
        
        this.setLayout(layout);
    }
    
    private void createDisplayTextArea()
    {
        textArea = new JTextArea("");
//        textArea.setPreferredSize(new Dimension(100, 100));
        textArea.setLineWrap( true );
        textArea.setWrapStyleWord( true );
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.gridwidth = 3;
        gridBag.gridheight = 1;
        gridBag.weightx = 1.0; // ?
        gridBag.weighty = 1.0; // ?
        gridBag.anchor = GridBagConstraints.EAST;
        gridBag.fill = GridBagConstraints.BOTH;
        gridBag.insets = new Insets(0, 0, 0, 0); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(scroll, gridBag);
        this.add(scroll);
    }
    
    private void createUserMessage()
    {
        userMessage = new TextField();
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 4.0; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.WEST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 0, 0, 0); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(userMessage, gridBag);
        this.add(userMessage);
    }
    
    private void createSendButton()
    {
        sendMessageButton = new JButton("Send");
        
        sendMessageButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String message = userMessage.getText();
                if(isMessageValide(message))
                {
                    sendMessage(new MessageEventObject(e, message));
                }
                userMessage.setText("");
            }
        });
        
        gridBag.gridx = 1;
        gridBag.gridy = 1;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 0.5; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.EAST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 0, 0, 0); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(sendMessageButton, gridBag);
        this.add(sendMessageButton);
    }
    
    private void createDisconnectButton()
    {
        disconnectButton = new JButton("Disconnect");
        gridBag.gridx = 2;
        gridBag.gridy = 1;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 0.5; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.EAST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(0, 0, 0, 0); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(disconnectButton, gridBag);
        this.add(disconnectButton);
        
        disconnectButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                disconnect(new MessageEventObject(e));
            }
        });
    }
    
    public void addMessage(String message)
    {
        String allMessages = textArea.getText();
        allMessages += (this.userName + ": ");
        allMessages += message;
        allMessages += "\n";
        textArea.setText(allMessages);
    }
    public void addMessage(String date, String name, String message)
    {
        String allMessages = textArea.getText();
        
        allMessages += (date + " ");
        allMessages += (name + ": ");
        allMessages += message;
        allMessages += "\n";

        textArea.setText(allMessages);
    }
    
    private boolean isMessageValide(String message)
    {
        return !"".equals(message.trim());
    }
    
    public void setUserName(String name)
    {
        this.userName = name;
    }
    
    public void addMyEventListener(MessageListener listener)
    {
        listenerList.add(MessageListener.class, listener);
    }
    
    public void removeMyEventListener(MessageListener listener)
    {
        listenerList.remove(MessageListener.class, listener);
    }

    private void sendMessage(MessageEventObject evt)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2)
        {
            if (listeners[i] == MessageListener.class)
            {
                ((MessageListener) listeners[i + 1]).getMessage(evt);
            }
        }
    }
    
    private void disconnect(MessageEventObject evt)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2)
        {
            if (listeners[i] == MessageListener.class)
            {
                ((MessageListener) listeners[i + 1]).disconnect(evt);
            }
        }
    }
}
