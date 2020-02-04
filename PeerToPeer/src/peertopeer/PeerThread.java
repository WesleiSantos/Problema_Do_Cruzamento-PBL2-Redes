/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import desenhos.Carro2D;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComponent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author root
 */
public class PeerThread extends Thread {

    private BufferedReader bufferedReader;
    private Socket socket;
    private Carro2D carro;
    private JSONObject jsonObject = new JSONObject();
    private JSONParser parser = new JSONParser();

    /**
     * O construtor recebe um socket que será usado para obter o fluxo de
     * entrada
     *
     */
    public PeerThread(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
    }

    public void run() {
        boolean flag = true;
        while (flag) {
            try {
                if (bufferedReader.ready()) {
                    String str = bufferedReader.readLine();
                    
                    if (str.length() > 10) {
                        jsonObject = (JSONObject) parser.parse(bufferedReader.readLine());                        
                        System.out.println("[" + jsonObject.get("username") + "]" + " : " + "message=>" + jsonObject.get("message"));

                    }
                }
            } catch (Exception e) {
                flag = false;
                interrupt();
            }
        }
    }
     public JSONObject getJsonObject() {
        return jsonObject;
    }
}
