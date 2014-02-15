package joyplus;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
        games.remove(findGame(conn));
        sockets.remove(conn);
    }

    @Override
    public synchronized void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        
        if (message.equals("GAMEINIT")) {
            games.add(new Game(conn));
            sockets.put(conn, 0);
            System.out.println("Create new game.");
            return;
        }
        
        if (sockets.containsKey(conn)) {
            if (sockets.get(conn) == 0) {
                Game game = findGame(conn);
                game.fromGame(message);
            } else {
                Game game = findGame(sockets.get(conn));
                game.fromDevice(message, conn.getRemoteSocketAddress().getPort());
            }
        } else {
            // new device
            System.out.println("new device");
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
            int gameId = jsonObject.get("gameId").getAsInt();
            sockets.put(conn, gameId);
            Game game = findGame(gameId);
            game.devices.add(conn);
            game.connectGUI.close();
            // sned layout to device
            String layout = "{\"event\":\"layout\",\"layout\":\""+game.layout+"\"}";
            conn.send(layout);
            game.fromDevice("{\"event\":\"connect\"}", conn.getRemoteSocketAddress().getPort());
            System.out.println("new device added");
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
    
    private Game findGame(int gameId) {
        for (Game game : games) {
            if (game.id == gameId) {
                return game;
            }
        }
        return null;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
    	System.err.println(ex.toString());
    }
    
}
