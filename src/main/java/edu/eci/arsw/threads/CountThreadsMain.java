/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    
    public static void main(String a[]){
        //primer punto
        //CountThread obj = new CountThread(1,3);
        //obj.start();

        //segundo punto
        CountThread hilo1 = new CountThread(0,99);
        CountThread hilo2 = new CountThread(99,199);
        CountThread hilo3 = new CountThread(200,299);
        //ii y iii)
        //hilo1.start();
        //hilo2.start();
        //hilo3.start();
        
        //iv)
        hilo1.run();
        hilo2.run();
        hilo3.run();
    }
    
}
