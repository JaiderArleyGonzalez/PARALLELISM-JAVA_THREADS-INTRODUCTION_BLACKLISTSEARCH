package edu.eci.arsw.blacklistvalidator;
import java.util.LinkedList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

/*
 * Clase de tipo Thread que representa el ciclo de vida de un hilo.
 * Realiza la búsqueda de un segmento del conjunto de servidores disponibles.
 */
public class MaliciousHostHunter extends Thread{
    private String ipAddress;
    private int startIndex, endIndex, ocurrencesCount, checkedListsCount;
    private HostBlacklistsDataSourceFacade skds;
    private LinkedList<Integer> blackListOcurrences;
    /*
     * Constructor de la clase.
     * @param ipAddress El segmento del conjunto de servidores disponibles.
     * @param N Cantidad de hilos
     */
    public MaliciousHostHunter(HostBlacklistsDataSourceFacade skds, String ipAddress, int startIndex, int endIndex){
        this.skds = skds;
        this.ipAddress=ipAddress;
        this.startIndex=startIndex;
        this.endIndex=endIndex;   
        ocurrencesCount = 0;
        checkedListsCount = 0;
        blackListOcurrences = new LinkedList<>();
    }
    /*
     * Ejecución del Thread
     */
    public void run(){
        int localOccurrencesCount = 0;
        int localCheckedListsCount = 0;
        
        for (int i=startIndex;i < endIndex;i++){
            localCheckedListsCount++;
            
            if (skds.isInBlackListServer(i, ipAddress)){
                
                blackListOcurrences.add(i);
                
                localOccurrencesCount++;
                
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
    /*
     *Método que permite preguntar a las instancias del mismo (los hilos) cuántas listas 
     *de servidores maliciosos ha revisado.
     *@return cantidad de listas revisadas
     */
    public int howManyCheckedListsCount(){
        return checkedListsCount;
    }
    /*
     *Método que permite preguntar a las instancias del mismo (los hilos) las posiciones dentro de la lista 
     *donde encontró la dirección la ip
     *@return posiciones dentro de una lista (int)
     */
    public LinkedList<Integer> getBlackListOcurrences(){
        return blackListOcurrences;
    }

}
