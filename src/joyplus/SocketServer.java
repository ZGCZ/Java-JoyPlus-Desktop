package joyplus;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SocketServer extends WebSocketServer {
    Map<WebSocket, Integer> sockets = new HashMap<WebSocket, Integer>();
    // Value type 0 for stimilate, 1 for game, 2 for device.
    Collection<Game> games = new ArrayList<Game>();
    
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
    public synchronized void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        // this.sendToAll(message);
        if (!sockets.containsKey(conn)) {
            if (message.equals("\"GAMEINIT\"")) {
                sockets.put(conn, 1);
                games.add(new Game(conn));
                System.out.println("Create new game.");
            } else {
                System.err.println("ERROR: unknown connection");
            }
            return;
        }
        switch (sockets.get(conn)) {
        case 1:
            findGame(conn).process(message);
        }
    }
    
    private Game findGame(WebSocket ws) {
        for (Game game : games) {
            if (game.ws.equals(ws)) {
                return game;
            }
        }
        return null;
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
