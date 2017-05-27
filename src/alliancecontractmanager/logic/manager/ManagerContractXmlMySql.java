/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLUser;
import alliancecontractmanager.logic.xml.ContractXml;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author lele
 */
public class ManagerContractXmlMySql {

    /**
     * Init contract User
     * @param List < UserApiIndexEntity > userApiIndexEntity 
     */
    public ManagerContractXmlMySql(List < UserApiEntity > userApiEntitys) {
        initDBContract(userApiEntitys);
    }
    
    public ManagerContractXmlMySql(){
        
    }
    
    public void initDBContract(List < UserApiEntity > userApiEntitys){
        boolean boolean001 = false;

        if (userApiEntitys.isEmpty())
            return;
        
        for (UserApiEntity userApiEntity : userApiEntitys) {

            // check that DB is not empty
            if ( ManagerSQLMicrimsDB.getInstance().getContracts().isEmpty() ){
                boolean001 = true;
            }
            // Load XML contract by User
            ManagerContractXml.getInstance().loadXMLDBG(userApiEntity);

            // Get contract XML
            List < ContractXml > contractXmls =
             ManagerContractXml.getInstance().getContractXmls(); 

            // check that are there contracts
            if ( !contractXmls.isEmpty() ){
                if ( boolean001 ){
                    checkAndWrite(userApiEntity, contractXmls);                    
                }else{
                    contractExpired(userApiEntity);
                    updateContract(userApiEntity);                    
                }                        
            }      
        }       
    }    

    /**
     * If contract is null don't update.
     * Check if status of the contract is completed.
     * Check if status of the contract is outstanding.
     * Check if status of the contract is cancellated.
     * Check if status of the contract is expired.
     */
    public void updateContract(UserApiEntity userApiEntity){
        // Load XML contract by User
        ManagerContractXml.getInstance().loadXMLDBG(userApiEntity);
        
        // Get Contract List Xmls 
        List < ContractXml > contractXmls = 
         ManagerContractXml.getInstance().getContractXmls();
        
        for (ContractXml contractXml : contractXmls) {
           
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setContractID(contractXml.getContractID());
            
            contractEntity = ManagerSQLMicrimsDB.getInstance().getContractEveId(contractEntity);
            
            if ( isNew(userApiEntity, contractXml, contractEntity))
                return;

            if ( isExpired(contractEntity))
                return;            
            
            if ( isStatusCompleted(contractEntity, contractXml))
                return;

            if ( isStatusDelete(contractEntity))
                return;


//            // if contract IS:
//            if ( isExpired(contractEntity))
//                return;
//            
//            if ( isNew(userApiEntity, contractXml, contractEntity )) // controllare
//                return;
//
//            if ( isStatusCompleted(contractEntity, contractXml))
//                return;
//
//            if ( isStatusDelete(contractEntity))
//                return;
        }
    }
    
    /**
     * DBG si può migliorare
     * Create a new contract
     * @param contractEntity
     * @param contractXml
     * @return 
     */
    private Boolean isNew(UserApiEntity userApiEntity, ContractXml contractXml, 
     ContractEntity contractEntity){
        
        // the contract IS NEW !!!
        if (contractEntity == null ){
            contractEntity = new ContractEntity();
            contractEntity.setDateIssued(contractXml.getDateIssued());
            
            // avoid insert if already exists the same dateIssued
            System.out.println(""+ ManagerSQLMicrimsDB.getInstance().getContractDateIssued(contractEntity));
            if ( ManagerSQLMicrimsDB.getInstance().getContractDateIssued(contractEntity) == null) {
                List < ContractXml > contractXmls = new ArrayList<>();
                 contractXmls.add(contractXml);
                
                checkAndWrite(userApiEntity, contractXmls);
                return true;
            }
         
        }
        return false;   
        
//        List < ContractXml > contractXmls = new ArrayList<>();
//        contractXmls.add(contractXml);
//        
//        if (contractEntity == null ){
//            checkAndWrite(userApiEntity, contractXmls);
//            return true;
//        }
//        return false;
    }
    
    /**
     * If is expired don't do nothing
     * @param contractEntity
     * @return 
     */
    private Boolean isExpired(ContractEntity contractEntity){
        if ( contractEntity == null ||contractEntity.getContractID().isEmpty() )
            return true;
        return false;
    }
    
    /**
     * Check if date is expired
     * @param contractEntity
     * @return boolean
     */
    private Boolean isDateExpired(ContractEntity contractEntity){       
        
        
        Long dateCurrent = new Date().getTime();
        Long dateContract = contractEntity.getDateExpiredUnformatted().getTime();
        
        if ( dateCurrent > dateContract){
            return true;
        }
        return false;
    }     
        
    /**
     * Set status contract like completed
     * @param contractEntity
     * @param contractXml 
     */
    private Boolean isStatusCompleted(ContractEntity contractEntity, ContractXml contractXml){
        if ( contractEntity.getStatusContract().equals(StatusEnum.OUTSTADING.getStatus()) && 
         contractXml.getStatus().equals(StatusEnum.COMPLETED.getStatus())){
            
            contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
            contractEntity.setDateCompleted(contractXml.getDateCompleted());
            ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);      
            return true;
        }
        return false;
    }
    
    /**
     * delete a contract
     * @param contractEntity 
     */
    private Boolean isStatusDelete(ContractEntity contractEntity){
        if ( contractEntity.getStatusContract().equals(StatusEnum.DELETED.getStatus())){
//           ManagerSQLMicrimsDB.getInstance().deleteContract(contractEntity);
           return true;
        }
        return false;
    }    
    
    
    /**
     * checkup many rules are true:
     * If doesn't have title of the contract, 
     * If price is 0.00, 
     * If title is "deleted",
     * Don't write in db
     */
    public void checkAndWrite(UserApiEntity userApiEntity, List <ContractXml> contractXmls ){

        for (ContractXml contractXml : contractXmls) {
            // if don't have title of the contract and price is 0.00 Don't write in db
            if (!contractXml.getTitle().equals("")){
                if (!contractXml.getPrice().equals("0.00")){
                    if (!contractXml.getTitle().equals("Deleted")){
                        ContractEntity contractEntity = new ContractEntity();
                        contractEntity.setDateIssued(contractXml.getDateIssued());
                        
                        // NON FUNZIONA!!!! il Controllo
                        // avoid insert if already exists the same dateIssued
                        if (ManagerSQLMicrimsDB.getInstance().getContractDateIssued(contractEntity) == null){
                            userApiEntity = buildContract(userApiEntity, contractXml);
                        }
                    }  
                }
            }             
        }
        
        if ( !contractXmls.isEmpty() )
            writeValueInTheDatabase(userApiEntity);
        
//        for (ContractXml contractXml : contractXmls) {
//            // if don't have title of the contract and price is 0.00 Don't write in db
//            if (!contractXml.getTitle().equals("")){
//                if (!contractXml.getPrice().equals("0.00")){
//                    if (!contractXml.getTitle().equals("Deleted")){
//                       userApiEntity = buildContract(userApiEntity, contractXml);
//                    }  
//                }
//            }             
//        }
//        
//        if ( !contractXmls.isEmpty() )
//            writeValueInTheDatabase(userApiEntity);
    }     

    /**
     * Build a contract and insert value
     * @param contractXml
     * @param userApiEntity
     * @return 
     */
    private UserApiEntity buildContract(UserApiEntity userApiEntity, ContractXml contractXml){
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setContractID(contractXml.getContractID());
        contractEntity.setPriceSell( contractXml.getPrice()) ;
        contractEntity.setPriceBuy( "0" );
        contractEntity.setStatusContract(contractXml.getStatus()); 
        contractEntity.setTitle( contractXml.getTitle() );
        contractEntity.setDateIssued(contractXml.getDateIssued());
        contractEntity.setDateExpired(contractXml.getDateExpired());
        if (!contractXml.getDateCompleted().equals("") )
            contractEntity.setDateCompleted(contractXml.getDateCompleted());                       

//        userApiEntity.setContractEntity(contractEntity);

        // DBGLUCA
        /**
         * Voglio che il database venga scritto solo dal metodo writeValueInTheDatabase
         */

        contractEntity.setUserEntityId(userApiEntity.getId());
        userApiEntity.addContractEntitys(contractEntity);
        return userApiEntity;
    }
        
    /**
     * Write contract into DB
     * @param UserApiIndexEntity userApiIndexEntity 
     */
    private void writeValueInTheDatabase(UserApiEntity userApiEntity){
        if ( ManagerSQLUser.getInstance().getUserApiEntities().isEmpty() ){
            ManagerSQLMicrimsDB.getInstance().addContract(userApiEntity);
            ManagerSQLUser.getInstance().addUserApiEntity(userApiEntity);
        }else{
            ManagerSQLUser.getInstance().updateUserApiEntity(userApiEntity);
        }

    }     
    
    /**
     * Check all contract expired
     */
    private void contractExpired(UserApiEntity userApiEntity){
        // Get all contract Entitys
        List < ContractEntity > contractEntitys = userApiEntity.getAllContractEntitys();
        
        for (ContractEntity contractEntity : contractEntitys) {
            // check if contractEntity is OUTSTADING
            if ( contractEntity.getStatusContract().equals(StatusEnum.OUTSTADING.getStatus()) ){
                
                // avoid insert if already exists
                if ( ManagerSQLMicrimsDB.getInstance().getContractDateExpired(contractEntity.getDateExpiredUnformatted()) == null ){
                    if ( isDateExpired(contractEntity)){

                        contractEntity.setContractID("");
                        contractEntity.setStatusContract(StatusEnum.EXPIRED.getStatus());
                        ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);
                    }                     
                }                
            }                     
        }        
    }    
    
}
