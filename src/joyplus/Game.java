package joyplus;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.java_websocket.WebSocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Game {
    public int id;
    public WebSocket ws;
    ConnectGUI connectGUI;
    public String layout = "";
    public List<WebSocket> devices = new ArrayList<WebSocket>();
    public Game(WebSocket ws) {
        id = ws.getRemoteSocketAddress().getPort();
        this.ws = ws;
    }
    public void fromGame(String message) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        System.out.println("Game " + id + " process: " + jsonObject.toString());
        
        String event = jsonObject.get("event").getAsString();
        if (event.equals("connect")) {
            layout = jsonObject.get("layout").getAsString();
            // System.out.println("Trying to connect");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    connectGUI = new ConnectGUI(id);
                    connectGUI.setVisible(true);
                }
            });
        }
    }
    public void fromDevice(String message, int device) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        jsonObject.add("device", gson.toJsonTree(device));
        System.out.println("Game " + id + " process: " + jsonObject.toString());
        ws.send(jsonObject.toString());
    }
}
