/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;
import org.json.simple.JSONObject;
/**
 *
 * @author root
 */
public class Peer {

    public Peer(){
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); //Solicita entrada do usuário
        //String[] setupValues = bufferedReader.readLine().split(" "); //dividir a entrada para pegar os campos nome do usuário e senha       
    }

    public ServerThread instanciaServerThread(String porta) throws IOException{
        ServerThread serverThread = new ServerThread(porta); //instancia um ServerThread passando porta
        serverThread.start(); //método start() chama método run() quando o sistema operacional decedir fazer
        return serverThread;
    }
    
    
    /**
     * Atualiza os peers que esse parceiro está captando menssagens
     *
     * @param dados
     * @param username
     * @param serverThread
     * @throws java.lang.Exception
     */
    public void updateListenToPeers(String dados, String username, ServerThread serverThread) throws Exception { //Atualiza a lista de peers
        String input = dados; //pega entrada do usuário
        String[] inputValues = input.split(" "); //separa por espaços
        if (!input.equals("s")) { //caso escolhar pular etapa, caso não é passado nome do host e porta
            for(int i = 0; i < inputValues.length; i++) {
                String[] address = inputValues[i].split(":");  //separa ip de porta
                Socket socket = null;
                try {
                    socket = new Socket(address[0], Integer.valueOf(address[1])); //é instanciado um socket
                    new PeerThread(socket).start(); //tem-se uma instancia de peeThread para cada peer e é passado o socket e imediatamente é chamadaa execução da thread
                } catch (Exception e) {
                    if (socket != null) {
                        socket.close();
                    } else {
                        System.out.println("Entrada inválida. pular para o proximo passo.");
                    }
                }
            }
        }
        //communicate(dados, username, serverThread); //envia menssagem
    }

    public void communicate(String msg, String username, ServerThread serverThread) {
        try {
            boolean flag = true;
            while (flag) {
                String message = msg; //pega mensagem
                if (message.equals("e")) { //mensagem foi 'e' (sair)
                    flag = false;
                    break;
                } else if (message.equals("c")) { //mensagem foi 'c'(atualiza lista de peers)
                    updateListenToPeers(msg, username, serverThread);
                } else { //caso tenha sido apenas uma mensagem
                    //converte para JSON nome de usuário e mensagem
                    JSONObject jsonObject = new JSONObject();
                    Chave chave = new Chave(serverThread.getPort());
                    
                    jsonObject.put("username", username);
                    jsonObject.put("message", message);
                    jsonObject.put("chave", chave.getChave());
                    serverThread.sendMessage(jsonObject.toJSONString());
                }
            }
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
