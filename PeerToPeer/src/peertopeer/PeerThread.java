/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author root
 */
public class PeerThread extends Thread{
    private BufferedReader bufferedReader;
    
    /**
     *O construtor recebe um socket que será usado para obter o fluxo de entrada 
     *
     */
    public PeerThread(Socket socket)throws IOException{
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public void run(){
        boolean flag = true;
        while(flag){
            try{
                JSONObject jsonObject = new JSONObject();
                JSONParser parser = new JSONParser();
                jsonObject = (JSONObject)parser.parse(bufferedReader.readLine());
                System.out.println("[" +jsonObject.get("username") +"]"+ " : " +"message=>"+jsonObject.get("message"));
            }catch(Exception e){
                flag = false;
                interrupt();
            }
        }
    }
}
