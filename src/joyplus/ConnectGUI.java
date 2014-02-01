package joyplus;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectGUI extends JFrame {
    public ConnectGUI() {
        setTitle("Connect");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        createUI();
    }
    
    private void createUI() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(null);

        String serverIP = JoyPlus.getLocalNetworkIP();
        JLabel iplabel = new JLabel("IP: " + serverIP + ":31415");
        iplabel.setBounds(50, 60, 200, 30);
        
        panel.add(iplabel);
    }
}
