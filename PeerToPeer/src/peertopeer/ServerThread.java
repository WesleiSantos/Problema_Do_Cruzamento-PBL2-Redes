/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author root
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;

    
    private Set<ServerThreadThreads> serverThreadThreads = new HashSet<ServerThreadThreads>();
    private Set<String> messagens = new HashSet<String>();
    private boolean status_veiculo = false;

    private String port;

    public ServerThread(String portNum) throws IOException {
        serverSocket = new ServerSocket(Integer.valueOf(portNum)); //instancia serverSocket passando uma porta
        this.port = portNum;
    }

    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
               // System.out.println(socket.getLocalPort());
                ServerThreadThreads serverThreadThread = new ServerThreadThreads(socket , this); //thread de servidor para cada um dos pares
                serverThreadThreads.add(serverThreadThread); //adicione ao conjunto
                serverThreadThread.start(); //chame o start em cada um desses threads 
                                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Enviar mensagens
     *
     */
    public void sendMessage(String message) {
        try { //aqui usamos o printWriter para enviar mensagens
            boolean flag = true;
            for (String str : messagens) {
                if (str.equalsIgnoreCase(message)) {
                    flag = false;
                }
            }
            if (flag) {
                messagens.add(message);
                //System.out.println(message);

                for (ServerThreadThreads socket : serverThreadThreads) {
                   BufferedWriter buffer = socket.getBuffer();
                   buffer.write(message+"\r\n");
                   buffer.flush();
                }
                //serverThreadThreads.forEach(t -> t.getSocket()); //aqui escolhe-se o gravador de impressão em cada um dos threads do servidor 

                flag = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Set<ServerThreadThreads> getThreadThreads() { //retorna conjunto de serverThreads
        return serverThreadThreads;
    }

    public String getPort() {
        return this.port;
    }

    public boolean isStatus_veiculo() {
        return status_veiculo;
    }
    
    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
