/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 *
 * @author root
 */
public class ServerThreadThreads extends Thread {

    private ServerThread serverThread;

    private Socket socket;
    private OutputStream output;
    private Writer w;
    private BufferedWriter buffer;

    

    public ServerThreadThreads(Socket socket, ServerThread serverThread) {
        this.serverThread = serverThread; //thread de servidor
        this.socket = socket; //recebe instancia de socket
    }

    public void conectar() throws IOException {
        output = socket.getOutputStream();
        w = new OutputStreamWriter(output);
        buffer = new BufferedWriter(w);
        buffer.write("Cliente: "+ socket.getInetAddress().getHostName() + "\r\n");
        buffer.flush();
    }

    public void run() {
        try {
            conectar();
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            InputStream input = socket.getInputStream();
            BufferedReader bufferIn = new BufferedReader(new InputStreamReader(input));
             while(true){
                 String str = bufferIn.readLine();
                 serverThread.sendMessage(str);
             }
        } catch (Exception e) {
            serverThread.getThreadThreads().remove(this);
        }
    }

    public BufferedWriter getBuffer() {
        return buffer;
    }
    
    public Socket getSocket() {
        return socket;
    }
    
    public ServerThread getServerThread() {
        return serverThread;
    }

}
