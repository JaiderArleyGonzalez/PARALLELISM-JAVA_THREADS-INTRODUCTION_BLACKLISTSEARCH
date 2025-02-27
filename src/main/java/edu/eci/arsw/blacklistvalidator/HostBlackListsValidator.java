/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private int ocurrencesCount = 0;
    private int checkedListsCount = 0;
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @param N cantidad de hilos.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int N){
        LinkedList<Integer> blackListOcurrences = new LinkedList<>();
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        List<MaliciousHostHunter> threads = new ArrayList<>();
        int chunkSize = skds.getRegisteredServersCount() / N; 
        for(int i = 0; i < N; i++){
            int startIndex = i * chunkSize;
            int endIndex = (i == N - 1) ? skds.getRegisteredServersCount() : (i + 1) * chunkSize;
            MaliciousHostHunter thread = new MaliciousHostHunter(skds, ipaddress, startIndex, endIndex);
            threads.add(thread);
        }
        for (MaliciousHostHunter thread : threads) {
            thread.start();
        }
        for (MaliciousHostHunter thread : threads) {
            try {
                thread.join();
                blackListOcurrences.addAll(thread.getBlackListOcurrences());
            
                for (Integer element : thread.getBlackListOcurrences()) {
                    if (!blackListOcurrences.contains(element)) {
                        blackListOcurrences.add(element);
                    }
                }
                checkedListsCount += thread.howManyCheckedListsCount();
                ocurrencesCount += thread.howManyOccurrences();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
