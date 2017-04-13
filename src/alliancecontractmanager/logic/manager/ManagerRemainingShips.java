/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import alliancecontractmanager.logic.remainingships.RemainingShips;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author lele
 */
public class ManagerRemainingShips {
    private Map < String, RemainingShips > doctrineOutstandingMap = new HashMap<>();
    private Map < String, RemainingShips > doctrineCompletedMap = new HashMap<>();
    
    private static ManagerRemainingShips instance = null;
    
    public static ManagerRemainingShips getInstance(){
        if ( instance == null ){
            instance = new ManagerRemainingShips();
        }
        return instance;
    }

    public ManagerRemainingShips() {
        initDoctrine();
    }

    /**
     * Init Doctrine
     */
    public void initDoctrine(){
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setStatusContract(StatusEnum.OUTSTADING.getStatus());
                
        List < ContractEntity > contractEntitys = 
         ManagerSQLMicrimsDB.getInstance().getContractsByStatus(contractEntity);
        
        for (ContractEntity contractEntity1 : contractEntitys) {
            // Add Doctrine Outstanding Map
            addDoctrineOutstandingMap(contractEntity1);
        }
        
        contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
        contractEntitys = ManagerSQLMicrimsDB.getInstance().getContractsByStatus(contractEntity);
        
        for (ContractEntity contractEntity1 : contractEntitys) {
            // Add Doctrine Completed Map
            addDoctrineCompletedMap(contractEntity1);
        }
        
    }

    /**
     * Check Duplicate Doctrine Outstanding
     * @param doctrine
     * @return boolean checkDuplicateDoctrineOutstanding
     */
    private boolean checkDuplicateDoctrineOutstanding(ContractEntity contractEntity){
        return doctrineOutstandingMap.containsKey(contractEntity.getTitle());
    }
    
    /**
     * Check Duplicate Ships Completed Map
     * @param String doctrine
     * @return boolean
     */
    private boolean checkDuplicateDoctrineCompleted(ContractEntity contractEntity){
        return this.doctrineCompletedMap.containsKey(contractEntity.getTitle());
    }    
    
    /**
     * Check if there is a duplicate, if no add a new doctrine
     * @param String doctrine 
     */
    private void addDoctrineOutstandingMap(ContractEntity contractEntity){
          // se esiste la dottrina aggiungi una nave nuova
        if ( checkDuplicateDoctrineOutstanding(contractEntity) == true ){
           Integer value = doctrineOutstandingMap.get(contractEntity.getTitle()).getNumberShip() + 1;
           doctrineOutstandingMap.put(contractEntity.getTitle(), 
            new RemainingShips(contractEntity.getTitle(), value) );
       }else{
            // if doesn't exit then add new doctrine
           doctrineOutstandingMap.put(contractEntity.getTitle(), 
            new RemainingShips(contractEntity.getTitle(), 1));
       }
    }    
        
    /**
     * Add RemainingShips Complete dMap
     * @param String doctrine 
     */
    private void addDoctrineCompletedMap(ContractEntity contractEntity){
        if ( checkDuplicateDoctrineCompleted(contractEntity) == true ){
            Integer value = doctrineCompletedMap.get(contractEntity.getTitle()).getNumberShip() +1;
            doctrineCompletedMap.put(contractEntity.getTitle(), 
             new RemainingShips(contractEntity.getTitle(), value));                                               
        }else{
            doctrineCompletedMap.put(contractEntity.getTitle(), 
             new RemainingShips(contractEntity.getTitle(), 1));            
        }
    }    
    
    /**
     * unused
     * @param doctrineCompletedMap 
     */
    private void setDoctrineCompletedMap(Map<String, RemainingShips> doctrineCompletedMap) {
        this.doctrineCompletedMap = doctrineCompletedMap;
    }

    /**
     * remove doctrine from " outstanding " and put it in " completed "
     * @param doctrine 
     */
    private void removeDoctrineOutstandingMap(ContractEntity contractEntity){    
        if ( doctrineOutstandingMap.isEmpty() && doctrineOutstandingMap.size() > 0 ){
            Integer value = doctrineOutstandingMap.get(contractEntity.getTitle()).getNumberShip() - 1;
            doctrineOutstandingMap.put(contractEntity.getTitle(), 
             new RemainingShips(contractEntity.getTitle(), value));
        }  
    }
    
    /**
     * Get RemainingShips Completed Map
     * @return Map<String, RemainingShips>
     */
    public Map<String, RemainingShips> getDoctrineCompletedMap() {
        updateAllContractDBG();
        return doctrineCompletedMap;
    }
    
    /**
     * Get RemainingShips Map
     * @return Map<String, RemainingShips>
     */
    public Map<String, RemainingShips> getDoctrineOutstandingMap() {
        updateAllContractDBG();
        return doctrineOutstandingMap;
    }

    /**
     * Update All Contract
     */
    private void updateAllContractDBG(){
        doctrineCompletedMap.clear();
        doctrineOutstandingMap.clear();
        initDoctrine();
    }
    
    /**
     * unused
     * @param doctrineOutstandingMap 
     */
    private void setDoctrineOutstandingMap(Map<String, RemainingShips> doctrineOutstandingMap) {
        this.doctrineOutstandingMap = doctrineOutstandingMap;
    }
    
    public void tempDisplay(){
        for (Map.Entry <String, RemainingShips> entrySet : doctrineOutstandingMap.entrySet() ){
            System.out.println(" (tempDisplay) ManagerDoctrine Outstanding "+entrySet.getKey() + " " +entrySet.getValue().getNumberShip());
        }
        
        for (Map.Entry<String, RemainingShips> entrySet : doctrineCompletedMap.entrySet()){
            System.out.println(" (tempDisplay) ManagerDoctrine Completed "+entrySet.getKey() + " " +entrySet.getValue().getNumberShip());
        }
    }
    
}
