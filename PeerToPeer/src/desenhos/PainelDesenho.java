/*
 * Esta classe fará o desenho e será inserida no JFrame da classe principal.
 */
package desenhos;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author root
 */
public class PainelDesenho extends JComponent implements ActionListener {

    private Carro2D carroSimples;
    private Cruzamento cruzamento;
    private int posicaoX = 0;
    private int posicaoY = 100;
    private Point posicaoNorte;
    private Point posicaoSul;
    private Point posicaoLeste;
    private Point posicaoOeste;
    private Timer t;
    private boolean timerRodando = false;
    private String posicaoOrigem;
    private String posicaoFim;

    public PainelDesenho() {
        posicaoLeste = new Point(100, 360);
        posicaoOeste = new Point(735, 310);
        posicaoNorte = new Point(435, 0);
        posicaoSul = new Point(490, 625);
        
        this.cruzamento = new Cruzamento(posicaoX, posicaoY);
        t = new Timer(60, this);
    }

    /**
     * Desenha o carro
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        int incremento = 1;
        cruzamento.draw(g);
        if (timerRodando) {
            if (posicaoOrigem.equalsIgnoreCase("NORTE")) {  
                if(carroSimples==null) carroSimples = new Carro2D(posicaoNorte.x, posicaoNorte.y);
                rodarCarro(g, incremento, 90, posicaoOrigem, posicaoFim);
            } else if (posicaoOrigem.equalsIgnoreCase("SUL")) {
                if(carroSimples==null) carroSimples = new Carro2D(posicaoSul.x, posicaoSul.y);
                rodarCarro(g, incremento, 90, posicaoOrigem, posicaoFim);
            } else if (posicaoOrigem.equalsIgnoreCase("LESTE")) {
               if(carroSimples==null) carroSimples = new Carro2D(posicaoLeste.x, posicaoLeste.y);
                rodarCarro(g, incremento, 0, posicaoOrigem, posicaoFim);
            } else if (posicaoOrigem.equalsIgnoreCase("OESTE")) {
                if(carroSimples==null) carroSimples = new Carro2D(posicaoOeste.x, posicaoOeste.y);
                rodarCarro(g, incremento, 0, posicaoOrigem, posicaoFim);
            }
        } else {
            t.stop();
            carroSimples=null;
            incremento = 1;          
        }
    }

    private void rodarCarro(Graphics g, int incremento, int angulo, String posicaoInicio,String posicaoFinal) {
        carroSimples.move(g, incremento, getWidth(), angulo, posicaoInicio, posicaoFinal);
        t.start();
        System.out.println(t.getInitialDelay());
    }

    public boolean isTimerRodando() {
        return timerRodando;
    }

    public void setTimerRodando(boolean timerRodando) {
        this.timerRodando = timerRodando;
    }
    
    public String getPosicaoOrigem() {
        return posicaoOrigem;
    }

    public void setPosicaoOrigem(String posicaoOrigem) {
        this.posicaoOrigem = posicaoOrigem;
    }

    public String getPosicaoFim() {
        return posicaoFim;
    }

    public void setPosicaoFim(String posicaoFim) {
        this.posicaoFim = posicaoFim;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }
}
