/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String a[]){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        //Tantos hilos como el doble de núcleos de procesamiento.
        //int numCores = 2 * Runtime.getRuntime().availableProcessors();

        long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("200.24.34.55", 100);
        long endTime = System.currentTimeMillis();
        long elapsedTimeMillis = endTime - startTime;
        double elapsedTimeSeconds = (double) elapsedTimeMillis / 1000.0;
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        System.out.println("Con 100 hilos. Tiempo transcurrido: " + elapsedTimeSeconds + " segundos");
        
    }
    
}
