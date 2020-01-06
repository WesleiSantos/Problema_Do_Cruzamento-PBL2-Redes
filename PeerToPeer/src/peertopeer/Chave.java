/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peertopeer;

import java.sql.Timestamp;
import java.util.Random;
import java.util.Date;

/**
 *
 * @author root
 */
public class Chave {
    private final Timestamp time;
    private final String port;
    private final int numRandom;

    public Chave(String port){
        this.time = new Timestamp(System.currentTimeMillis());
        this.port=  port;
        this.numRandom = gerarNumRandom();
    }
    
    private int gerarNumRandom(){
        Random gerador = new Random();
        return gerador.nextInt(2000);
    }
    
     public Timestamp getTime() {
        return time;
    }

    public String getPort() {
        return port;
    }
    
     public int getNumRandom() {
        return numRandom;
    }
    
    public String getChave() {
        return Long.toString(time.getTime())+port+Integer.toString(numRandom);
    }
}
