package joyplus;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static String localNetworkIP;
    public static SocketServer ss;
    public static void main(String[] args) {
        try {
            localNetworkIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Joy Plus starts.");
        
        ss = SocketServer.getInstance();
        ss.start();
    }
}
