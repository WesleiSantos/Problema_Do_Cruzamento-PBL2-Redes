/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenhos;

/**
 *
 * @author root
 */
public class NewClass {
    if (posicaoInicio.equalsIgnoreCase("NORTE")) {
            if (currentSegundo > 14) {
                if (posicaoFinal.equalsIgnoreCase("SUL")) {

                    posicao.y = (posicao.y + ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("LESTE")) {
                    g2.rotate(Math.toRadians(angulo), posicao.x, posicao.y);
                    posicao.x = (posicao.x - ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("OESTE")) {

                    posicao.x = (posicao.x + ineX) % maxX;
                }
            }else{
                posicao.y = (posicao.y + ineX) % maxX;
            }
        } else if (posicaoInicio.equalsIgnoreCase("SUL")) {
            
            if (currentSegundo > 13) {
                if (posicaoFinal.equalsIgnoreCase("NORTE")) {

                    posicao.y = (posicao.y - ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("LESTE")) {

                    posicao.x = (posicao.x - ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("OESTE")) {

                    posicao.x = (posicao.x + ineX) % maxX;
                }
            }else{
                posicao.y = (posicao.y - ineX) % maxX;
            }
        } else if (posicaoInicio.equalsIgnoreCase("LESTE")) {
            
            if (currentSegundo > 13) {
                if (posicaoFinal.equalsIgnoreCase("NORTE")) {

                    posicao.y = (posicao.y - ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("SUL")) {

                    posicao.y = (posicao.y + ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("OESTE")) {

                    posicao.x = (posicao.x + ineX) % maxX;
                }
            }else{
                posicao.x = (posicao.x + ineX) % maxX;
            }
        } else if (posicaoInicio.equalsIgnoreCase("OESTE")) {
            
            if (currentSegundo > 13) {
                if (posicaoFinal.equalsIgnoreCase("NORTE")) {

                    posicao.y = (posicao.y - ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("SUL")) {

                    posicao.y = (posicao.y + ineX) % maxX;
                } else if (posicaoFinal.equalsIgnoreCase("LESTE")) {

                    posicao.x = (posicao.x - ineX) % maxX;
                }
            }else{
                posicao.x = (posicao.x - ineX) % maxX;
            }
        }
}
