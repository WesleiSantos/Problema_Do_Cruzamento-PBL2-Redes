/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.IOException;
import java.net.ServerSocket;
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
    private boolean status_veiculo=false;

   
    private String port;

    public ServerThread(String portNum) throws IOException {
        serverSocket = new ServerSocket(Integer.valueOf(portNum)); //instancia serverSocket passando uma porta
        this.port = portNum;
    }

    public void run() {
        try {
            while (true) {
                ServerThreadThreads serverThreadThread = new ServerThreadThreads(serverSocket.accept(), this); //thread de servidor para cada um dos pares
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
            if (messagens.contains(message)) {
                messagens.add(message);
            }
            serverThreadThreads.forEach(t -> t.getPrintWriter().println(message)); //aqui escolhe-se o gravador de impressão em cada um dos threads do servidor 

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
}
