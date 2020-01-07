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
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author root
 */
public class Carro2D {

    Point posicao;

    public Carro2D(int x, int y) {
        this.posicao = new Point(x, y);
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
    public void move(Graphics g, int ineX, int maxX, int angulo, String posicaoInicio, String posicaoFinal) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke());
        g2.rotate(Math.toRadians(angulo), posicao.x, posicao.y);
        draw(g2);
        if (posicaoInicio.equalsIgnoreCase("NORTE")) {
             posicao.y = (posicao.y + ineX) % maxX;
        } else if (posicaoInicio.equalsIgnoreCase("SUL")) {
            posicao.y = (posicao.y - ineX) % maxX;
        } else if (posicaoInicio.equalsIgnoreCase("LESTE")) {
            posicao.x = (posicao.x + ineX) % maxX;
        } else if (posicaoInicio.equalsIgnoreCase("OESTE")) {
            posicao.x = (posicao.x - ineX) % maxX;
        } else if (posicaoFinal.equalsIgnoreCase("NORTE")) {
            
            posicao.y = (posicao.y - ineX) % maxX;
        } else if (posicaoFinal.equalsIgnoreCase("SUL")) {
            
            posicao.y = (posicao.y + ineX) % maxX;
        } else if (posicaoFinal.equalsIgnoreCase("LESTE")) {
            
             posicao.x = (posicao.x - ineX) % maxX;
        } else if (posicaoFinal.equalsIgnoreCase("OESTE")) {
            
            posicao.x = (posicao.x + ineX) % maxX;
        }

       
        g.setColor(Color.black);
    }

    /*private void rotaciona(Graphics g, int angulo, int posicaoX, int posicaoY){
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(Math.toRadians(90), posicao.x,posicao.y );
    }*/
    public Point getPosicao() {
        return posicao;
    }

}
