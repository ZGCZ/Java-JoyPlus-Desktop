package joyplus;

import java.net.InetSocketAddress;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SocketServer extends WebSocketServer {
    private SocketServer(InetSocketAddress address) {
        super(address);
		System.out.println("Start server on " + address.getHostString() + " port " + address.getPort());
    }
    
    private static SocketServer ss;
    public static SocketServer getInstance() {
        if (ss == null) {
            String localHost = "0.0.0.0";
            int port = 31415;
            InetSocketAddress address = new InetSocketAddress(localHost, port);
            ss = new SocketServer(address);
        }
        return ss;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        this.sendToAll(message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
    	System.err.println(ex.toString());
    }
    
    public void sendToAll( String text ) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( text );
            }
        }
    }
}
