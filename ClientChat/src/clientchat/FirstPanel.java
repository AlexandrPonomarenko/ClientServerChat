
package clientchat;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;


public class FirstPanel extends JPanel
{
    private String address = "";
    private Pattern pattern;
    private final String regIP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                               + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                               + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                               + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    
    private EventListenerList listenerList;

    private GridBagLayout layout;
    private GridBagConstraints gridBag;

    private JLabel nameText;
    private JLabel ipAddress;
    
    private JTextField userName;
    private JTextField userIpAddress;
    
    private JButton enterToChatButton;
    
    public FirstPanel()
    {
        listenerList = new EventListenerList();
        layout = new GridBagLayout();
        gridBag = new GridBagConstraints();

        createNameLabel();
        createIpAddressLabel();

        createUserName();
        createUserIpAddress();

        createEnterToChatButton();

        this.setLayout(layout);
    }
    
    private void createNameLabel()
    {
        nameText = new JLabel("Your name is: ");
        gridBag.gridx = 0;
        gridBag.gridy = 0;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 0; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.SOUTHEAST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(20, 20, 5, 5); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(nameText, gridBag);
        this.add(nameText);
                
    }
    
    private void createIpAddressLabel()
    {
        ipAddress = new JLabel("Your ip address is: ");
        gridBag.gridx = 0;
        gridBag.gridy = 1;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 0; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.NORTHEAST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 20, 20, 5); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(ipAddress, gridBag);
        this.add(ipAddress);
    }
    
    private void createUserName()
    {
        userName = new JTextField(15);
        userName.setText("");
        gridBag.gridx = 1;
        gridBag.gridy = 0;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 0; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.SOUTHWEST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(20, 5, 5, 20); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(userName, gridBag);
        this.add(userName);
        addDocListener(userName);
    }
    
    private void createUserIpAddress()
    {
        userIpAddress = new JTextField(15);
        userIpAddress.setText("");
        gridBag.gridx = 1;
        gridBag.gridy = 1;
        gridBag.gridwidth = 1;
        gridBag.gridheight = 1;
        gridBag.weightx = 0; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.NORTHWEST;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(5, 5, 20, 20); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 0;
        layout.setConstraints(userIpAddress, gridBag);
        this.add(userIpAddress);
        addDocListener(userIpAddress);
    }
    
    private void createEnterToChatButton()
    {
        enterToChatButton = new JButton("Enter to the chat");
        enterToChatButton.setEnabled(false);
        
        enterToChatButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String _userName = userName.getText();
                String _userIpAddress = userIpAddress.getText();
                
                //if (isUserNameValid(_userName) && isUserIpAddressValid(_userIpAddress))
                //{
                    fireMyEvent(new MyEventObject(e, _userName, _userIpAddress));
                //}
            }
        });

        gridBag.gridx = 0;
        gridBag.gridy = 2;
        gridBag.gridwidth = 2;
        gridBag.gridheight = 1;
        gridBag.weightx = 0; // ?
        gridBag.weighty = 0; // ?
        gridBag.anchor = GridBagConstraints.CENTER;
        gridBag.fill = GridBagConstraints.HORIZONTAL;
        gridBag.insets = new Insets(10, 25, 20, 25); // top left bottom right
        gridBag.ipadx = 0;
        gridBag.ipady = 5;
        layout.setConstraints(enterToChatButton, gridBag);
        this.add(enterToChatButton);
    }
    
//    private boolean isUserNameValid(String value)
//    {
//        return "Rob".equals(value);
//    }
//    
//    private boolean isUserIpAddressValid(String value)
//    {
//        return "localhost".equals(value);
//    }
    
    public void addMyEventListener(MyEventListener listener)
    {
        listenerList.add(MyEventListener.class, listener);
    }
    
    public void removeMyEventListener(MyEventListener listener)
    {
        listenerList.remove(MyEventListener.class, listener);
    }

    private void fireMyEvent(MyEventObject evt)
    {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2)
        {
            if (listeners[i] == MyEventListener.class)
            {
                ((MyEventListener) listeners[i + 1]).enterButtonClicked(evt);
            }
        }
    }
    
    
    private void onButton()
    {
        IPAddressValidator();
        if(userName.getText().length() > 0 && (userIpAddress.getText().equals("localhost")|| validate(userIpAddress.getText())))
        {
            address = userIpAddress.getText();
            enterToChatButton.setEnabled(true);
        }else if (userName.getText().length() == 0 || ((!validate(userIpAddress.getText())) && userIpAddress.getText().length() > 15))
        {
            enterToChatButton.setEnabled(false);
        }
    }
    
    public String getAddress()
    {
        return address;
    }
    private void IPAddressValidator() {
        pattern = Pattern.compile(regIP);
    }
 
    private boolean validate(String ipAddress) {
        return pattern.matcher(ipAddress).matches();
    }
    
    private void addDocListener(JTextField tf)
    {
        tf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) 
            {
                onButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) 
            {
                onButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) 
            {
                
            }
        });
    }
}
