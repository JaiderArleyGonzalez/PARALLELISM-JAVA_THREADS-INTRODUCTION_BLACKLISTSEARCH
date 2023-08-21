package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/*
 * Clase de tipo Thread que representa el ciclo de vida de un hilo.
 * Realiza la búsqueda de un segmento del conjunto de servidores disponibles.
 */
public class MaliciousHostHunter extends Thread{
    private String ipAddress;
    private int startIndex, endIndex, BLACK_LIST_ALARM_COUNT, ocurrencesCount, checkedListsCount;
    private HostBlacklistsDataSourceFacade skds;
    private LinkedList<Integer> blackListOcurrences;
    /*
     * Constructor de la clase.
     * @param ipAddress El segmento del conjunto de servidores disponibles.
     * @param N Cantidad de hilos
     */
    public MaliciousHostHunter(HostBlacklistsDataSourceFacade skds, String ipAddress, int startIndex, int endIndex, int BLACK_LIST_ALARM_COUNT){
        this.skds = skds;
        this.ipAddress=ipAddress;
        this.startIndex=startIndex;
        this.endIndex=endIndex;
        this.BLACK_LIST_ALARM_COUNT=BLACK_LIST_ALARM_COUNT;
        
        
        ocurrencesCount = 0;
        checkedListsCount = 0;
        blackListOcurrences = new LinkedList<>();
    }

    public void run(){
        int localOccurrencesCount = 0;
        int localCheckedListsCount = 0;
        
        for (int i=startIndex;i < endIndex && localOccurrencesCount <= BLACK_LIST_ALARM_COUNT;i++){
            localCheckedListsCount++;
            
            if (skds.isInBlackListServer(i, ipAddress)){
                
                blackListOcurrences.add(i);
                
                localOccurrencesCount++;
                System.out.println(blackListOcurrences);
            }
            if(blackListOcurrences.size()>=BLACK_LIST_ALARM_COUNT){
                
                break;
            }
            
            
        }

        checkedListsCount = localCheckedListsCount;
        ocurrencesCount = localOccurrencesCount;
        
        
    }
    
    /*
     *Método que permite preguntar a las instancias del mismo (los hilos) cuántas ocurrencias 
     *de servidores maliciosos ha encontrado o encontró.
     *@return cantidad de ocurrencias encontradas
     */
    public int howManyOccurrences(){
        return ocurrencesCount;
    }
    public int howManyCheckedListsCount(){
        return checkedListsCount;
    }
    public LinkedList<Integer> getBlackListOcurrences(){
        return blackListOcurrences;
    }

}
