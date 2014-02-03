package joyplus;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

public class JoyPlus {
    public static SocketServer ss;
    public static MainGUI mainGUI;
    public static void main(String[] args) {
        startGUI();
        
        System.out.println("Joy Plus starts.");
        
        ss = SocketServer.getInstance();
        ss.start();
    }
    
    private static void startGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainGUI = new MainGUI();
                mainGUI.setVisible(true);
            }
        });
    }
    
    public static String getLocalNetworkIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
