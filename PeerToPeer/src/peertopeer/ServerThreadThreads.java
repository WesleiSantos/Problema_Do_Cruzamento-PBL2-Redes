/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author root
 */
public class ServerThreadThreads extends Thread {

    private ServerThread serverThread;
    private Socket socket;
    private PrintWriter printWriter;

    public ServerThreadThreads(Socket socket, ServerThread serverThread) {
        this.serverThread = serverThread; //thread de servidor
        this.socket = socket; //recebe instancia de socket
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream());
            while(true) serverThread.sendMessage(bufferedReader.readLine());

        } catch (Exception e) {
            serverThread.getThreadThreads().remove(this);
        }
    }
    public PrintWriter getPrintWriter(){return printWriter;}

}
