/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager.ManagerMicrimDB;

import alliancecontractmanager.db.controllers.ContractEntityJpaController;
import alliancecontractmanager.db.controllers.UserApiEntityJpaController;
import alliancecontractmanager.db.controllers.exceptions.NonexistentEntityException;
import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author lele
 */
public class ManagerSQLMicrimsDB {
    private static ManagerSQLMicrimsDB instance = null;
    
    private ContractEntityJpaController 
     contractEntityJpaController = new ContractEntityJpaController
     (Persistence.createEntityManagerFactory("AllianceContractManagerPU"));
    
    private EntityManager entityManagerEM = 
     Persistence.createEntityManagerFactory("AllianceContractManagerPU").createEntityManager();

    // Si può migliorare
    private EntityManagerFactory entityManagerFactoryEMF =
     Persistence.createEntityManagerFactory("AllianceContractManagerPU");
    
    private UserApiEntityJpaController 
     userKeyIDJpaController = new UserApiEntityJpaController
     (Persistence.createEntityManagerFactory("AllianceContractManagerPU"));
    
    public static ManagerSQLMicrimsDB getInstance(){
        if ( instance == null ){
            instance = new ManagerSQLMicrimsDB();
        }
        return instance;
    }

    
    /**
     * Update contract ( change status, price.... )
     * @param ContractEntity contractEntity 
     */
    public void updateContract(ContractEntity contractEntity){
        try {
            contractEntityJpaController.edit(contractEntity);
        } catch (Exception ex) {
            Logger.getLogger(ManagerSQLMicrimsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Create Contract
     * @param ContractEntity contractEntity 
     */
    public void createContract(ContractEntity contractEntity){
        try {
            contractEntityJpaController.create(contractEntity);
        } catch (Exception ex) {
            Logger.getLogger(ManagerSQLMicrimsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
    //DBG LUCA
    public boolean isContractExpired(ContractEntity contract){
        if(contract.getStatusContract().equals(StatusEnum.EXPIRED.getStatus())){
            return true;
        }
        if(new Date().after(contract.getDateExpiredUnformatted())){
            return true;
        }
        return false;
    }
    
    //DBG LUCA
    public boolean isThisIdAssignable(String contractID){
        
        ContractEntity contract = getContractByEveId(contractID);
        if(contract == null){
            return true;
        }else{
            if(isContractExpired(contract)){
                return true;
            }else{
                return false;
            }
        }
    }
    /**
     * Write Contract In DB
     * @param contractEntity 
     */    
    public void addContract(UserApiEntity userApiEntity){
        List < ContractEntity > contractEntitys = userApiEntity.getAllContractEntitys();
        
        try {
            for (ContractEntity contractEntity : contractEntitys) {
                contractEntity.setUserEntityId(userApiEntity.getId());
                contractEntityJpaController.create(contractEntity);
                userKeyIDJpaController.edit(userApiEntity);
            }            
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB addContractDBGLUCA");
            e.printStackTrace();
        }        
    }    
    
    /**
     * DBG NON va bene, i contratti sono collegati a un utente
     * Delete Contract
     * @param ContractEntity contractEntity 
     */
    public void deleteContract(ContractEntity contractEntity){
        try {
            contractEntityJpaController.destroy(contractEntity.getId());
        } catch (Exception ex) {
            Logger.getLogger(ManagerSQLMicrimsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Delete a contract
     * @param value
     * @param id 
     */
    public void deleteContract(UserApiEntity apiEntity, ContractEntity contractEntity){  
        unlinkContract(apiEntity, contractEntity);
        
        try {
            Long value = contractEntity.getId();
            contractEntityJpaController.destroy(value);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * Unlink contract from user
     * @param apiEntity
     * @param contractEntity 
     */
    private void unlinkContract( UserApiEntity apiEntity, ContractEntity contractEntity ){
        boolean removed = apiEntity.getAllContractEntitys().remove(contractEntity);
        if ( removed ){
            try {
                userKeyIDJpaController.edit(apiEntity);
            } catch (Exception ex) {
                Logger.getLogger(ManagerSQLMicrimsDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    

    /**
     * Update User
     * @param UserApiEntity user 
     */
    public void updateUser(UserApiEntity user){
        try {
            userKeyIDJpaController.edit(user);
        } catch (Exception ex) {
            Logger.getLogger(ManagerSQLMicrimsDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Get all contract ( to all users ) with parameter Status
     * @return List < ContractEntity > 
     */
    public List < ContractEntity > getContractsByStatus(String status) {
        try {
            TypedQuery < ContractEntity > getContractsByStatusTQ =
             entityManagerEM.createNamedQuery("getContractsByStatus", ContractEntity.class);

            getContractsByStatusTQ.setParameter("statusContract", status);
            return getContractsByStatusTQ.getResultList();            
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getContractsByStatus");
            e.printStackTrace();
            return null ;
        }
    }    
    
    
    // ------------------------------------------------------------------------------------------------
    
    /**
     * Get all contract with this parameters Status and User
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getUserContractsByStatus(long userId, String contractStauts){
        
        try {
            TypedQuery < ContractEntity > getUserContractsTQ =
             entityManagerEM.createNamedQuery("getUserContractsByStatus", ContractEntity.class);
            
            getUserContractsTQ.setParameter("userEntityId", userId);
            getUserContractsTQ.setParameter("statusContract", contractStauts);
            
            return getUserContractsTQ.getResultList();
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getUserContractsByStatus");
            e.printStackTrace();
            return null ;
        }
    }    
    
    /**
     * Get User all Contracts by status and Date
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getUserContractsByStatusDate(Long userId, String statusContract, Date dateIssuedUnformatted ){
        
        try {
            TypedQuery < ContractEntity > getUserContractsTQ =
             entityManagerEM.createNamedQuery("getUserContractsByStatusDate", ContractEntity.class);
            
            getUserContractsTQ.setParameter("userEntityId", userId );
            getUserContractsTQ.setParameter("statusContract", statusContract);
            getUserContractsTQ.setParameter("dateIssued", dateIssuedUnformatted);
            
            return getUserContractsTQ.getResultList();
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getUserContractsByStatusDate");
            e.printStackTrace();
            return null ;
        }
    }    

    /**
     * Get all contract with this parameter Title, Status and User
     * @param String title
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getUserContractsByTitleStatus(Long userApiEntityId, String status, String titleContract){
        
        if(status.equals("All")){
            return getUserContractsByTitle(userApiEntityId, titleContract);
        }
        try {
            TypedQuery < ContractEntity > getContractStatusByTitleTQ = 
             entityManagerEM.createNamedQuery("getUserContractsByTitleStatus", ContractEntity.class);

            getContractStatusByTitleTQ.setParameter("title", titleContract);
            getContractStatusByTitleTQ.setParameter("statusContract", status);
            getContractStatusByTitleTQ.setParameter("userEntityId", userApiEntityId);

            List < ContractEntity > contractEntitys = getContractStatusByTitleTQ.getResultList();
            
            // DBG
            System.out.println("DBG almeno che non lo riavvi la gui NON è consapevole del passaggio di status da outstanding a completed");
            for (ContractEntity contractEntity1 : contractEntitys) {
                System.out.println("ManagerSQLMicrimsDB > getUserContractsByTitleStatus "+contractEntity1.getStatusContract());
            }
            
            return getContractStatusByTitleTQ.getResultList();            
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB1 getUserContractsByTitleStatus");
            e.printStackTrace();
            return null;
        }
    }     
      
    /**
     * DBG unused
     * get Contract By ID
     * @param id
     * @return 
     */
    public ContractEntity getContractByID( ContractEntity contractEntity) {
        return contractEntityJpaController.findContractEntity(contractEntity.getId());
    }
    
    /**
     * DBG1 - Conrtollare
     * Get all contract with this parameters Status "Expired" and DateExpired
     * @param dateExpired
     * @return 
     */
    public ContractEntity getContractDateExpired(Date dateExpired){
        try {
            TypedQuery < ContractEntity > getContractDateExpiredQT = 
             entityManagerEM.createNamedQuery("getContractDateExpired", ContractEntity.class);

            getContractDateExpiredQT.setParameter("dateExpired", dateExpired);
            List < ContractEntity > contractEntitys = getContractDateExpiredQT.getResultList();
            
            if ( contractEntitys.isEmpty()){
                return null;
            }else{
                return contractEntitys.get(0);
            }
                  
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB getContractDateExpired");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DBG usato solo una volta.
     * Get All contracts from Db
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getContracts(){
        
        try {
            TypedQuery < ContractEntity > getDBContractsTQ =
             entityManagerEM.createNamedQuery("getContracts", ContractEntity.class);
            
            return getDBContractsTQ.getResultList();
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getContracts");
            e.printStackTrace();
            return null ;
        }
    }


    /**
     * Get specific Contract ID ( Eve Id ) from Eve Server
     * @param String contractId
     * @return ContractEntity
     */
    public ContractEntity getContractByEveId(String contractId){
        try {
            TypedQuery < ContractEntity > getDBContractIdTQ =
             entityManagerEM.createNamedQuery("getContractsId", ContractEntity.class);

            getDBContractIdTQ.setParameter("id", contractId);

            List < ContractEntity > contractEntitys = getDBContractIdTQ.getResultList();
            
            if ( contractEntitys.isEmpty()){
                return null;
            }else{
                return contractEntitys.get(0);
            }                                
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB getDBContractId");
            e.printStackTrace();
            return null;
        }
    }   
    
    /**
     * Get specific Contract ID ( Eve Id ) from Eve Server
     * @param contractEntity
     * @return 
     */
    public ContractEntity getContractEveId(ContractEntity contractEntity){
        try {
            TypedQuery < ContractEntity > getDBContractIdTQ =
             entityManagerEM.createNamedQuery("getContractsId", ContractEntity.class);

            getDBContractIdTQ.setParameter("id", contractEntity.getContractID());

            List < ContractEntity > contractEntitys = getDBContractIdTQ.getResultList();
            
            if ( contractEntitys.isEmpty()){
                return null;
            }else{
                return contractEntitys.get(0);
            }                                
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB getDBContractId");
            e.printStackTrace();
            return null;
        }
    }        
    
    /**
     * Get get All contract with same dateIssued
     * @param contractEntity
     * @return 
     */
    public ContractEntity getContractDateIssued(ContractEntity contractEntity){
        try {
            TypedQuery < ContractEntity > getDBContractIdTQ =
             entityManagerEM.createNamedQuery("getContractDateIssued", ContractEntity.class);

            getDBContractIdTQ.setParameter("dateIssued", contractEntity.getDateIssuedUnformatted() );

            List < ContractEntity > contractEntitys = getDBContractIdTQ.getResultList();
            
            if ( contractEntitys.isEmpty()){
                return null;
            }else{
                return contractEntitys.get(0);
            }                                
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB getContractDateIssued");
            e.printStackTrace();
            return null;
        }
    }        
    

    /**
     * 
     * @param userApiEntityID
     * @param title
     * @return 
     */
    public List < ContractEntity > getUserContractsByTitle(Long userApiEntityID, String title){
        try {
            TypedQuery < ContractEntity > getUserContractsByTitleTQ = 
             entityManagerEM.createNamedQuery("getUserContractsByTitle", ContractEntity.class);

            getUserContractsByTitleTQ.setParameter("title", title);
            getUserContractsByTitleTQ.setParameter("userid", userApiEntityID);

            return getUserContractsByTitleTQ.getResultList();            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }        
    }        
    
        /**
     * Get all contract with this parameter User
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getContractsByUser(Long userId){
        
//        userApiEntity.get
        try {
            TypedQuery < ContractEntity > getUserContractsTQ =
             entityManagerEM.createNamedQuery("getUserContracts", ContractEntity.class);
            
            getUserContractsTQ.setParameter("userEntityId", userId);
            
            return getUserContractsTQ.getResultList();
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getUserContracts");
            e.printStackTrace();
            return null ;
        }
    }   
    
    
    
    // da sostituire con la versione con user
    
//    /**
//     * Get all ship from DB that correspond to this parameters 
//     * 'completed', 'outstanding', expired
//     * @param String title
//     * @return List < ContractEntity >
//     */
//    private List < ContractEntity > getContractStatusByTitle(ContractEntity contractEntity){
//        try {
//            TypedQuery < ContractEntity > getDBContractShipsSoldQT = 
//             entityManagerEM.createNamedQuery("getContractStatusByTitle", ContractEntity.class);
//
//            getDBContractShipsSoldQT.setParameter("title", contractEntity.getTitle());
//            getDBContractShipsSoldQT.setParameter("status", contractEntity.getStatusContract());
//
//            return getDBContractShipsSoldQT.getResultList();            
//        } catch (Exception e) {
//            System.out.println("ManagerMicrimsDB1 getContractStatusByTitle");
//            e.printStackTrace();
//            return null;
//        }
//    }     

    /**
     * DBG NON MI PIACE
     * Lo voglio riportare direttamente in oggetto tralasciando doppioni
     * Get all contracts avoid these with the same name
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getContractsTitleAvoidDuplicate(){
        
        List<ContractEntity> contracts = contractEntityJpaController.findContractEntityEntities();
        return contracts;
//        try {
//            
//            Query query =  entityManagerEM.createNamedQuery("getContractsTitleAvoidDuplicate");
//            List < String > titles = query.getResultList();
//
//            List < ContractEntity > contractEntitys = new ArrayList<>();
//            
//            for (String title : titles) {
//                ContractEntity contractEntity = new ContractEntity();
//                contractEntity.setTitle(title);
//                contractEntitys.add(contractEntity);
//            }
//
//            return contractEntitys;
//        } catch (Exception e) {
//            System.out.println("ManagerMicrimDB getContractsTitleAvoidDuplicate");
//            e.printStackTrace();
//            return null ;
//        }
    }   
    

//    /**
//     * Get Query DB
//     * @param value
//     * @param contractEntity
//     * @param entityManagerEM
//     * @return Query
//     */
//    private Query getQueryDB(ContractEntity contractEntity, EntityManager entityManagerEM){
//
//        // check contract.getPriceSell and contractEntity.getPriceSell are differnt       
//        // DBG fa cagare
//        if ( contractEntity.getTempQualcosaDBG() == 1 ){
//            Query query = updatePriceSell(contractEntity, entityManagerEM);
//            contractEntity.setTempQualcosaDBG(0);
//            return query;
//        }
//        
//       // check and update status from outstanding to completed
//        if ( contractEntity.getStatusContract().equals(StatusEnum.COMPLETED.getStatus() )){
//            // Change value from outstanding to completed
//            Query query = updateStatusContract(contractEntity, entityManagerEM);
//            return query;
//        }
//        
//        // update status to expired
//        if ( contractEntity.getStatusContract().equals(StatusEnum.EXPIRED.getStatus())){
//            Query query = updateStatusContractRemoveID(contractEntity, entityManagerEM);
//            return query;             
//        }
//        
//        return null;
//    }

        /**
//     * Change value sell price
//     * @param value
//     * @param id
//     * @param entityManagerEM
//     * @return Query
//     */
//    private Query updatePriceSell(ContractEntity contractEntity, EntityManager entityManagerEM){
//        Long id = contractEntity.getId();
//        String value = contractEntity.getPriceBuyUnformatted();
//        
//        Query query = entityManagerEM.createQuery
//        ("UPDATE ContractEntity contractEntity set CONTRACTENTITY.priceBuy = '"
//        + value + "' WHERE CONTRACTENTITY.id = :id" );
//        return query;        
//    }
//    
    /**
     * DGB DateCompleted non viene inserito nel campo
     * Change value from outstanding to completed
     * @param value
     * @param id
     * @param entityManagerEM
     * @return Query
     */
//    private Query updateStatusContract(ContractEntity contractEntity, EntityManager entityManagerEM){
//        Long id = contractEntity.getId();
//        String statusContract = contractEntity.getStatusContract();
//        Date dateCompleted = contractEntity.getDateCompletedUnformatted();
//        // :id
//        
//        Query query = entityManagerEM.createQuery
//        ("UPDATE ContractEntity contractEntity set CONTRACTENTITY.dateCompleted= :" + dateCompleted + " , "
//       + "CONTRACTENTITY.statusContract = '" +  statusContract + "' WHERE CONTRACTENTITY.id = :id  ");
//        
//        
////        Query query = entityManagerEM.createQuery
////        ("UPDATE ContractEntity contractEntity set CONTRACTENTITY.dateCompleted= '" + dateCompleted + "' , "
////       + "CONTRACTENTITY.statusContract = '" +  statusContract + "' WHERE CONTRACTENTITY.id = :id  ");
//        
////        Query query = entityManagerEM.createQuery
////        ("UPDATE ContractEntity contractEntity set CONTRACTENTITY.statusContract = '"
////        + statusContract  + "'  WHERE CONTRACTENTITY.id = :id  "  );
//        
//        return query;
//    }

    /**
     * THIS IS CRIME AGAINST HUMANITY!!!!!!!
     * 
     * Quando un contrattato in outstading scade il contractID viene recilato per
     * uno nuovo contratto, mentre quello vecchio viene perso.
     * Cancello contractID proveniente da Eve ma l'oggetto non viene perso grazie
     * l'ID di mysql che resta in archivio. Il suo status passa da "Oustanding" a "Expired"
     * @param contractEntity
     * @param entityManagerEM
     * @return Query
     */
//    private Query updateStatusContractRemoveID(ContractEntity contractEntity, 
//     EntityManager entityManagerEM){
//        Long id = contractEntity.getId();
//        String statusContract = contractEntity.getStatusContract();
//        
//        Query query = entityManagerEM.createQuery(
//         "UPDATE ContractEntity contractEntity SET CONTRACTENTITY.statusContract ='" + statusContract 
//         + "', CONTRACTENTITY.contractID='' WHERE CONTRACTENTITY.id = :id");
//        return query;
//    }    
    
    
}
