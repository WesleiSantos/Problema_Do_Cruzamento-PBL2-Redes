/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import org.json.simple.JSONObject;
/**
 *
 * @author root
 */
public class Peer {

    public static void main(String[] args) throws IOException, Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in)); //Solicita entrada do usuário
        System.out.println("> entre com nome de usuário e porta do peer:");
        String[] setupValues = bufferedReader.readLine().split(" "); //dividir a entrada para pegar os campos nome do usuário e senha
        ServerThread serverThread = new ServerThread(setupValues[1]); //instancia um ServerThread passando porta
        serverThread.start(); //método start() chama método run() quando o sistema operacional decedir fazer
        new Peer().updateListenToPeers(bufferedReader, setupValues[0], serverThread);
    }

    /**
     * Atualiza os peers que esse parceiro está captando menssagens
     *
     * @param bufferedReader
     * @param username
     * @param serverThread
     * @throws java.lang.Exception
     */
    public void updateListenToPeers(BufferedReader bufferedReader, String username, ServerThread serverThread) throws Exception { //Atualiza a lista de peers
        System.out.println("> Entre com (separados por espaços) hostname:port#");
        System.out.println(" Insira os peers que receberam mensagens (selecione 's' para pular etapa):");
        String input = bufferedReader.readLine(); //pega entrada do usuário
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
        communicate(bufferedReader, username, serverThread); //envia menssagem
    }

    public void communicate(BufferedReader bufferedReader, String username, ServerThread serverThread) {
        try {
            System.out.println("> Agora voce pode se comunicar (presse 'e' para sair, presse 'c' para adicionar peers)"); //digitar 'e' para sair e 'c' para conectar a peers e receber mensagens
            boolean flag = true;
            while (flag) {
                String message = bufferedReader.readLine(); //pega mensagem do buffer
                if (message.equals("e")) { //mensagem foi 'e' (sair)
                    flag = false;
                    break;
                } else if (message.equals("c")) { //mensagem foi 'c'(atualiza lista de peers)
                    updateListenToPeers(bufferedReader, username, serverThread);
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
