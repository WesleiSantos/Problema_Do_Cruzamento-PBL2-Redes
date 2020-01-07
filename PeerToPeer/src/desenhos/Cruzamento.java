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
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author root
 */
public class Cruzamento {

    Point posicao;
    
    public Cruzamento(int x, int y) {
        this.posicao = new Point(x,y);
    }
    
     /**
     * Aqui vão os desenhos das linhas
     * @param g 
     */
    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        
        Point2D.Double ponto1 = new Point2D.Double(posicao.x+100, posicao.y+200);
        Point2D.Double ponto2 = new Point2D.Double(posicao.x+400, posicao.y+200);
        Point2D.Double ponto3 = new Point2D.Double(posicao.x+100, posicao.y+300);
        Point2D.Double ponto4 = new Point2D.Double(posicao.x+400, posicao.y+300);
        
        Point2D.Double ponto5 = new Point2D.Double(posicao.x+500, posicao.y+200);
        Point2D.Double ponto6 = new Point2D.Double(posicao.x+800, posicao.y+200);
        Point2D.Double ponto7 = new Point2D.Double(posicao.x+500, posicao.y+300);
        Point2D.Double ponto8 = new Point2D.Double(posicao.x+800, posicao.y+300);
        
        Rectangle2D.Double areaCritica = new Rectangle2D.Double( posicao.x+400,posicao.y+200, 100, 100);
        
        Point2D.Double ponto9 = new  Point2D.Double(posicao.x+400, posicao.y+200);
        Point2D.Double ponto10 = new Point2D.Double(posicao.x+400, posicao.y-100);
        Point2D.Double ponto11 = new Point2D.Double(posicao.x+500, posicao.y+200);
        Point2D.Double ponto12 = new Point2D.Double(posicao.x+500, posicao.y-100);
        
        Point2D.Double ponto13 = new Point2D.Double(posicao.x+400, posicao.y+300);
        Point2D.Double ponto14 = new Point2D.Double(posicao.x+400, posicao.y+600);
        Point2D.Double ponto15 = new Point2D.Double(posicao.x+500, posicao.y+300);
        Point2D.Double ponto16 = new Point2D.Double(posicao.x+500, posicao.y+600);
        
        Line2D.Double horizontal1 = new Line2D.Double(ponto1, ponto2);
        Line2D.Double horizontal2 = new Line2D.Double(ponto3, ponto4);
        Line2D.Double horizontal3 = new Line2D.Double(ponto5, ponto6);
        Line2D.Double horizontal4 = new Line2D.Double(ponto7, ponto8);
        Line2D.Double vertical1 = new Line2D.Double(ponto9, ponto10);
        Line2D.Double vertical2 = new Line2D.Double(ponto11, ponto12);
        Line2D.Double vertical3 = new Line2D.Double(ponto13, ponto14);
        Line2D.Double vertical4 = new Line2D.Double(ponto15, ponto16);
        
        float dash1[] = {10.0f};
        BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        
        Point2D.Double ponto17 = new Point2D.Double(posicao.x+100, posicao.y+250);
        Point2D.Double ponto18 = new Point2D.Double(posicao.x+400, posicao.y+250);
        Point2D.Double ponto19 = new Point2D.Double(posicao.x+500, posicao.y+250);
        Point2D.Double ponto20 = new Point2D.Double(posicao.x+800, posicao.y+250);
        
        Point2D.Double ponto21 = new  Point2D.Double(posicao.x+450, posicao.y+200);
        Point2D.Double ponto22 = new Point2D.Double(posicao.x+450, posicao.y-100);
        Point2D.Double ponto23 = new Point2D.Double(posicao.x+450, posicao.y+300);
        Point2D.Double ponto24 = new Point2D.Double(posicao.x+450, posicao.y+600);
        
        Line2D.Double horizontalPontilhada1 = new Line2D.Double(ponto17, ponto18);
        Line2D.Double horizontalPontilhada2 = new Line2D.Double(ponto19, ponto20);
        Line2D.Double verticalPontilhada1 = new Line2D.Double(ponto21, ponto22);
        Line2D.Double verticalPontilhada2 = new Line2D.Double(ponto23, ponto24);
         
        g2.draw(horizontal1);
        g2.draw(horizontal2);
        g2.draw(horizontal3);
        g2.draw(horizontal4);
        g2.draw(vertical1);
        g2.draw(vertical2);
        g2.draw(vertical3);
        g2.draw(vertical4);
        g2.setColor(Color.red);
        g2.draw(areaCritica);
        g2.setStroke(dashed);
        g2.setColor(Color.black);
        g2.draw(horizontalPontilhada1);
        g2.draw(horizontalPontilhada2);
        g2.draw(verticalPontilhada1);
        g2.draw(verticalPontilhada2);
       
        
        
    }  
    
}
