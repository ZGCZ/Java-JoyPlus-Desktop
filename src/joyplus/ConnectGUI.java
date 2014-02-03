package joyplus;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConnectGUI extends JFrame {
    int gameId;
    
    public ConnectGUI(int gameId) {
        this.gameId = gameId;
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
        JLabel iplabel = new JLabel("IP: " + serverIP + ":31415 ID: " + gameId);
        iplabel.setBounds(10, 60, 300, 30);
        
        panel.add(iplabel);
    }
    
    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
