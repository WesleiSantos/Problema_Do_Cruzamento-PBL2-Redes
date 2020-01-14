/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenhos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.net.Socket;
import org.json.simple.JSONObject;
import peertopeer.Chave;
import peertopeer.Peer;
import peertopeer.PeerThread;
import peertopeer.ServerThread;

/**
 *
 * @author root
 */
public class Carro2D {

    Point posicao;
    Peer peer;
    ServerThread serverThread;
    StringBuffer buffer;
    String username;

    public Carro2D(int x, int y, String porta, String nome) throws IOException {
        this.buffer = new StringBuffer("");
        this.posicao = new Point(x, y);
        this.serverThread = new ServerThread(porta); //instancia um ServerThread passando porta
        serverThread.start();
        this.username = nome;

    }

    /**
     * Atualiza os peers que esse parceiro está captando menssagens
     *
     * @param dados
     * @param username
     * @throws java.lang.Exception
     */
    public void updateListenToPeers(String ip, String porta) throws Exception { //Atualiza a lista de peers
        //if (!input.equals("s")) { //caso escolhar pular etapa, caso não é passado nome do host e porta
        Socket socket = null;
        try {
            socket = new Socket(ip, Integer.valueOf(porta)); //é instanciado um socket
            new PeerThread(socket).start(); //tem-se uma instancia de peeThread para cada peer e é passado o socket e imediatamente é chamadaa execução da thread
        } catch (Exception e) {
            if (socket != null) {
                socket.close();
            } else {
                System.out.println("Entrada inválida.");
            }
        }
        // }

        //communicate(dados, username); //envia menssagem
    }

    public void communicate(StringBuffer buffer) {
        try {
            boolean flag = true;
            String message = buffer.toString(); //pega mensagem
            if (message.equals("e")) { //mensagem foi 'e' (sair)
                flag = false;
            } else if (message.equals("c")) { //mensagem foi 'c'(atualiza lista de peers)
                //updateListenToPeers(message);
            } else { //caso tenha sido apenas uma mensagem
                //converte para JSON nome de usuário e mensagem
                JSONObject jsonObject = new JSONObject();
                Chave chave = new Chave(this.serverThread.getPort());
                jsonObject.put("username", username);
                jsonObject.put("message", message);
                jsonObject.put("chave", chave.getChave());
                serverThread.sendMessage(jsonObject.toJSONString());
            }
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * Aqui vão os desenhos das linhas
     *
     * @param g
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D.Double corpo = new Rectangle2D.Double(posicao.x + 5, posicao.y + 10, 60, 12);
        Ellipse2D.Double rodaFrente = new Ellipse2D.Double(posicao.x + 15, posicao.y + 20, 10, 10);
        Ellipse2D.Double rodaTras = new Ellipse2D.Double(posicao.x + 45, posicao.y + 20, 10, 10);
        Rectangle2D.Double parachoqueFrente = new Rectangle2D.Double(posicao.x, posicao.y + 17, 5, 5);
        Rectangle2D.Double parachoqueTras = new Rectangle2D.Double(posicao.x + 65, posicao.y + 17, 5, 5);

        Point2D.Double capo1 = new Point2D.Double(posicao.x + 15, posicao.y + 10);
        Point2D.Double capo2 = new Point2D.Double(posicao.x + 25, posicao.y);
        Point2D.Double capo3 = new Point2D.Double(posicao.x + 45, posicao.y);
        Point2D.Double capo4 = new Point2D.Double(posicao.x + 55, posicao.y + 10);

        Line2D.Double c1 = new Line2D.Double(capo1, capo2);
        Line2D.Double c2 = new Line2D.Double(capo2, capo3);
        Line2D.Double c3 = new Line2D.Double(capo3, capo4);

        g2.draw(corpo);
        g2.draw(rodaFrente);
        g2.draw(rodaTras);
        g2.draw(parachoqueFrente);
        g2.draw(parachoqueTras);

        g2.draw(c1);
        g2.draw(c2);
        g2.draw(c3);
    }

    /**
     *
     * @param g Graphics
     * @param ineX incrementa em x para mover horizontalmente
     * @param maxX mantém dentro da tela
     * @param angulo
     */
    public void move(Graphics g, int ineX, int maxX, int angulo, String direcao, int currentSegundo) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke());
        g2.rotate(Math.toRadians(angulo), posicao.x, posicao.y);
        draw(g2);
        if (direcao.equalsIgnoreCase("Norte")) {
            moveNorte(ineX, maxX);
        }
        if (direcao.equalsIgnoreCase("Sul")) {
            moveSul(ineX, maxX);
        }
        if (direcao.equalsIgnoreCase("Leste")) {
            moveLeste(ineX, maxX);
        }
        if (direcao.equalsIgnoreCase("Oeste")) {
            moveOeste(ineX, maxX);
        }
        g.setColor(Color.black);

    }

    public void moveNorte(int ineX, int maxX) {
        posicao.y = (posicao.y - ineX) % maxX;
    }

    public void moveLeste(int ineX, int maxX) {
        posicao.x = (posicao.x - ineX) % maxX;
    }

    public void moveOeste(int ineX, int maxX) {
        posicao.x = (posicao.x + ineX) % maxX;
    }

    public void moveSul(int ineX, int maxX) {
        posicao.y = (posicao.y + ineX) % maxX;
    }

    public boolean excedeuAreaDaTela(Point tamanhoTela) {
        return posicao.x < 0 || posicao.x + 180 > tamanhoTela.x || posicao.y < 0 || posicao.y + 90 > tamanhoTela.y;
    }

    public Point getPosicao() {
        return posicao;
    }

    public void setPosicao(Point posicao) {
        this.posicao = posicao;
    }

}
