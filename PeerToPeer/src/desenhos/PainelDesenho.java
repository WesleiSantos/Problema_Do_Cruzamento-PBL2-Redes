/*
 * Esta classe fará o desenho e será inserida no JFrame da classe principal.
 */
package desenhos;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author root
 */
public class PainelDesenho extends JComponent implements ActionListener{
    private Carro2D carroSimples;
    private Cruzamento cruzamento;
    private int posicaoX = -50;
    private Timer t;
    private boolean timerRodando = false;

   

    public PainelDesenho() {
        this.carroSimples = new Carro2D(posicaoX, 360);
        this.cruzamento = new Cruzamento(posicaoX, 105);
        t = new Timer(60, this);
    }
    
    /**
     * Desenha o carro
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        int incremento =1;
        cruzamento.draw(g);
        if(timerRodando){
            rodarCarro(g,incremento);
        }else{
          t.stop();
          incremento=1;
          carroSimples = new Carro2D(posicaoX, 360);
        }
    }
    
    private void rodarCarro(Graphics g, int incremento){
        carroSimples.move(g,incremento, getWidth());
        t.start();
    }

     public boolean isTimerRodando() {
        return timerRodando;
    }

    public void setTimerRodando(boolean timerRodando) {
        this.timerRodando = timerRodando;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }
}
