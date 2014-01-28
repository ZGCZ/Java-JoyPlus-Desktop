package joyplus;

import org.java_websocket.WebSocket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Game {
    public WebSocket ws;
    public Game(WebSocket ws) {
        this.ws = ws;
    }
    public void process(String message) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(message, JsonObject.class);
        String action = jsonObject.get("action").getAsString();
        System.out.println(action);
        if (action.equals("connect")) {
            System.out.println("Trying to connect");
        }
    }
}
