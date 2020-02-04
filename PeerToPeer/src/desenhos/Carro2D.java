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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.Timer;
import org.json.simple.JSONObject;
import peertopeer.Chave;
import peertopeer.Peer;
import peertopeer.PeerThread;
import peertopeer.ServerThread;

/**
 *
 * @author root
 */
public class Carro2D extends JComponent implements ActionListener {

    private Point posicao;
    private ServerThread serverThread;
    private String username;
    private String porta;
    private Set<PeerThread> carrosRodando = new HashSet<PeerThread>();
    private int posicaoX = 0;
    private int posicaoY = 100;
    private Point posicaoNorte;
    private Point posicaoSul;
    private Point posicaoLeste;
    private Point posicaoOeste;
    private Timer t;
    private Timer timer;
    private boolean controleConometro = false;
    private boolean mudarPosicao = false;
    private boolean flag = true;
    private int currentSegundo = 0;
    private int velocidade = 1000;
    private boolean timerRodando = true;
    private String posicaoOrigem;
    private String posicaoFim;

    public Carro2D(int x, int y, String posicaoOrigem, String posicaoFim) {
        this.posicao = new Point(x, y);
        this.posicaoOrigem = posicaoOrigem;
        this.posicaoFim = posicaoFim;
        posicaoLeste = new Point(100, 360);
        posicaoOeste = new Point(735, 310);
        posicaoNorte = new Point(435, 0);
        posicaoSul = new Point(490, 625);
        t = new Timer(10, this);
    }

    public void instanciaServer(String porta, String nome) throws IOException {
        this.porta = porta;
        this.serverThread = new ServerThread(porta); //instancia um ServerThread passando porta
        serverThread.start();
        this.username = nome;
    }

    private void iniciarContagem() {
        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controleConometro) {
                    currentSegundo++;
                    String seg = currentSegundo <= 9 ? "0" + currentSegundo : currentSegundo + "";
                    int x = posicao.x;
                    int y = posicao.y;
                    String str = Integer.toString(x)+" "+Integer.toString(y)+" "+posicaoOrigem+" "+posicaoFim+" "+currentSegundo;     
                    communicate(str);
                    carrosRodando.spliterator().forEachRemaining(t-> System.out.println(t.getJsonObject().get("username")));
                }
            }
        };
        if (!controleConometro) {
            controleConometro = true;
            this.timer = new Timer(velocidade, action);
            this.timer.start();
            
        }

    }

    private void stopTime() {
        if (controleConometro) {
            controleConometro = false;
            timer.stop();
            currentSegundo = 0;
            System.out.println("00:00:00");
        }

    }

    
    
    
    /**
     * Atualiza os peers que esse parceiro está captando menssagens
     *
     * @param dados
     * @param p
     * @throws java.lang.Exception
     */
    public void updateListenToPeers(String dados) throws Exception { //Atualiza a lista de peers        
        String input = dados; //pega entrada do usuário
        String[] inputValues = input.split(" "); //separa por espaços
        for (int i = 0; i < inputValues.length; i++) {

            String[] address = inputValues[i].split(":");  //separa ip de porta
            //Carro2D carro = new Carro2D(0,0, address[1],address[0]);
            //carrosRodando.add(carro);
            Socket socket = null;
            try {
                socket = new Socket(address[0], Integer.valueOf(address[1])); //é instanciado um socket
                PeerThread peer = new PeerThread(socket);
                peer.start();//tem-se uma instancia de peeThread para cada peer e é passado o socket e imediatamente é chamadaa execução da thread
                carrosRodando.add(peer);               
            } catch (Exception e) {
                if (socket != null) {
                    socket.close();
                } else {
                    System.out.println("Entrada inválida.");
                }
            }

        }

        //communicate(dados, username); //envia menssagem
    }

    public void communicate(String buffer) {
        try {
            boolean flag = true;
            String message = buffer; //pega mensagem
            String[] dados = buffer.split(" ");
            if (message.equals("e")) { //mensagem foi 'e' (sair)
                flag = false;
            } else if (message.equals("c")) { //mensagem foi 'c'(atualiza lista de peers)
                //updateListenToPeers(message);
            } else { //caso tenha sido apenas uma mensagem
                //converte para JSON nome de usuário e mensagem
                JSONObject jsonObject = new JSONObject();
                Chave chave = new Chave(this.serverThread.getPort());
                jsonObject.put("username", username);
                jsonObject.put("posicaoX", dados[0]);
                jsonObject.put("posicaoY", dados[1]);
                jsonObject.put("posicaoInicial", dados[2]);
                jsonObject.put("posicaoFinal", dados[3]);
                jsonObject.put("tempo", dados[4]);
                jsonObject.put("message", message);
                jsonObject.put("porta", porta);
                serverThread.sendMessage(jsonObject.toJSONString());

            }

        } catch (Exception e) {
            System.exit(0);
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
        g2.dispose();
        repaint();
    }

    /**
     * Desenha o carro
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        if (timerRodando) {
            t.start();
            iniciarContagem();
            int incremento = 1;
            super.paintComponent(g);
            Graphics g2d = g.create(); //Faz a cópia
            this.desenhaCarro(g2d, posicaoOrigem, posicaoFim, 1000, 1000, incremento);
            g2d.dispose();
        } else {
            t.stop();
            flag = true;
            if (flag) {
                stopTime();
            }
        }
    }

    /**
     *
     * @param g Graphics
     * @param ineX incrementa em x para mover horizontalmente
     * @param maxX mantém dentro da tela
     * @param angulo
     * @param direcao
     * @param currentSegundo
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

    public void desenhaCarro(Graphics g, String posicaoOrigem, String posicaoFim, int getWidth, int getHeight, int incremento) {
        if (posicaoOrigem.equalsIgnoreCase("NORTE")) {
            if (flag || this.excedeuAreaDaTela(new Point(getWidth, getHeight))) {
                this.setPosicao(this.posicaoNorte.getLocation());
                mudarPosicao = false;
                flag = false;
            }
            if (!mudarPosicao) {
                rodarCarro(g, incremento, 90, "SUL", currentSegundo);
            }
            if (posicaoFim.equalsIgnoreCase("LESTE") && this.getPosicao().y > 310) {
                rodarCarro(g, incremento, 0, "LESTE", currentSegundo);
                mudarPosicao = true;
            } else if (posicaoFim.equalsIgnoreCase("OESTE") && this.getPosicao().y > 355) {
                rodarCarro(g, incremento, 0, "OESTE", currentSegundo);
                mudarPosicao = true;
            }
        } else if (posicaoOrigem.equalsIgnoreCase("SUL")) {
            if (flag || this.excedeuAreaDaTela(new Point(getWidth, getHeight + 50))) {
                this.setPosicao(this.posicaoSul.getLocation());
                mudarPosicao = false;
                flag = false;
            }
            if (!mudarPosicao) {
                rodarCarro(g, incremento, 90, "NORTE", currentSegundo);
            }
            if (posicaoFim.equalsIgnoreCase("LESTE") && this.getPosicao().y < 310) {
                rodarCarro(g, incremento, 0, "LESTE", currentSegundo);
                mudarPosicao = true;
            } else if (posicaoFim.equalsIgnoreCase("OESTE") && this.getPosicao().y < 355) {
                rodarCarro(g, incremento, 0, "OESTE", currentSegundo);
                mudarPosicao = true;
            }
        } else if (posicaoOrigem.equalsIgnoreCase("LESTE")) {
            if (flag || this.excedeuAreaDaTela(new Point(getWidth, getHeight + 50))) {
                this.setPosicao(this.posicaoLeste.getLocation());
                mudarPosicao = false;
                flag = false;
            }
            if (!mudarPosicao) {
                rodarCarro(g, incremento, 0, "OESTE", currentSegundo);
            }
            if (posicaoFim.equalsIgnoreCase("NORTE") && this.getPosicao().x > 480) {
                rodarCarro(g, incremento, 90, "NORTE", currentSegundo);
                mudarPosicao = true;
            } else if (posicaoFim.equalsIgnoreCase("SUL") && this.getPosicao().x > 430) {
                rodarCarro(g, incremento, 90, "SUL", currentSegundo);
                mudarPosicao = true;
            }
        } else if (posicaoOrigem.equalsIgnoreCase("OESTE")) {
            if (flag || this.excedeuAreaDaTela(new Point(getWidth + 70, getHeight))) {
                this.setPosicao(this.posicaoOeste.getLocation());
                mudarPosicao = false;
                flag = false;
            }
            if (!mudarPosicao) {
                rodarCarro(g, incremento, 0, "LESTE", currentSegundo);
            }

            if (posicaoFim.equalsIgnoreCase("NORTE") && this.getPosicao().x < 490) {
                rodarCarro(g, incremento, 90, "NORTE", currentSegundo);
                mudarPosicao = true;
            } else if (posicaoFim.equalsIgnoreCase("SUL") && this.getPosicao().x < 435) {
                rodarCarro(g, incremento, 90, "SUL", currentSegundo);
                mudarPosicao = true;
            }
        }
    }

    private void rodarCarro(Graphics g, int incremento, int angulo, String direcao, int currentSegundo) {
        this.move(g, incremento, 1000, angulo, direcao, currentSegundo);

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

    public Set<PeerThread> getCarrosRodando() {
        return carrosRodando;
    }

    public void setPosicaoOrigem(String posicaoOrigem) {
        this.posicaoOrigem = posicaoOrigem;
    }

    public void setPosicaoFim(String posicaoFim) {
        this.posicaoFim = posicaoFim;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

}
