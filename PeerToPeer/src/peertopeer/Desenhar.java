/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author root
 */
public class Desenhar extends JFrame{
    private JFrame frame = new JFrame("programa");
    private Container painel = new JPanel(new BorderLayout());
    private Container painelNorth = new JPanel(new BorderLayout());
    private JButton ok;
    private JButton limpar;
    private Container tela;

    public Desenhar(){
        tela = new JPanel();
        ok = new JButton("OK");
        limpar= new JButton("Limpar");       
        frame.add(BorderLayout.NORTH, painelNorth);
        frame.add(BorderLayout.CENTER, painel);
        frame.setSize(500, 200);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new ProgramaView();
    }
    
    @Override
    public void paint(Graphics g){
        int x1 =100, y1=300, x2=400, y2=300;
        g.drawLine(x1, y1, x2, y2);
        frame.update(g);
    }
    public static void main(String[] args) {
        new Desenhar();
        
    }


private class ProgramaView extends JFrame{

        JButton cancelar, gerar;
        JLabel mousePosition;
        Container cont1 = new JPanel(new GridLayout(1, 2, 10, 10));
        Container panelSec = new JPanel();
        Container panelOpicoes;
        JProgressBar p = new JProgressBar(0, 3);
        JTextArea textArea = new JTextArea();

        private ProgramaView() {
            this.panelOpicoes = new JPanel();
            mousePosition = new JLabel(" ");
            ConfiguraFrame();
            criaComponentes();
        }

        private void ConfiguraFrame() {
            setTitle("Programa.exe");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        private void criaComponentes() {

            p.setValue(0);
            p.setStringPainted(true);
            //temporario só para testes
            cont1.add(mousePosition);
            cont1.setBackground(java.awt.Color.white);

            gerar = new JButton("Gerar menor Rota");
            gerar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }

            });

            cancelar = new JButton("Cancelar");
            cancelar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() instanceof JButton) {
                        panelSec.repaint();

                    }
                }

            });

            //configura layout
            BoxLayout box = new BoxLayout(panelOpicoes, BoxLayout.LINE_AXIS);
            panelOpicoes.setLayout(box);
            panelSec.setLayout(new BorderLayout());

            panelOpicoes.setBackground(java.awt.Color.white);
            panelOpicoes.add(cancelar);
            panelOpicoes.add(gerar);

            //adiciona componentes a painel secundario
            setContentPane(panelSec);
            panelSec.setBackground(java.awt.Color.white);
            panelSec.add(BorderLayout.CENTER, tela);
            panelSec.add(BorderLayout.SOUTH, cont1);
            panelSec.add(BorderLayout.EAST, panelOpicoes);
            tela.setSize(200, 200);
        }

        private void limparDados() {
            p.setValue(0);
        }
        
    }
}