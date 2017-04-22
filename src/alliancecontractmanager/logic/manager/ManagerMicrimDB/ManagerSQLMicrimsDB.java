/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager.ManagerMicrimDB;

import alliancecontractmanager.db.controllers.ContractEntityJpaController;
import alliancecontractmanager.db.controllers.UserApiEntityJpaController;
import alliancecontractmanager.db.controllers.UserApiIndexEntityJpaController;
import alliancecontractmanager.db.controllers.exceptions.NonexistentEntityException;
import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.db.entities.UserApiIndexEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * DBG NON MI PIACE
     * Lo voglio riportare direttamente in oggetto tralasciando doppioni
     * Get all contracts avoid these with the same name
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getContractsTitleAvoidDuplicate(){
        try {
            
            Query query =  entityManagerEM.createNamedQuery("getContractsTitleAvoidDuplicate");
            List < String > titles = query.getResultList();

            List < ContractEntity > contractEntitys = new ArrayList<>();
            
            for (String title : titles) {
                ContractEntity contractEntity = new ContractEntity();
                contractEntity.setTitle(title);
                contractEntitys.add(contractEntity);
            }

            return contractEntitys;
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getContractsTitleAvoidDuplicate");
            e.printStackTrace();
            return null ;
        }
    }
    
    /**
     * Get all contract ( to all users ) with parameter Status
     * @return List < ContractEntity > 
     */
    public List < ContractEntity > getContractsByStatus(ContractEntity contractEntity) {
        try {
            TypedQuery < ContractEntity > getContractsByStatusTQ =
             entityManagerEM.createNamedQuery("getContractsByStatus", ContractEntity.class);

            getContractsByStatusTQ.setParameter("statusContract", contractEntity.getStatusContract());
            return getContractsByStatusTQ.getResultList();            
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getContractsByStatus");
            e.printStackTrace();
            return null ;
        }
    }
    
    /**
     * Get all contract with this parameter User
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getUserContracts(UserApiEntity userApiEntity){
        
        try {
            TypedQuery < ContractEntity > getUserContractsTQ =
             entityManagerEM.createNamedQuery("getUserContracts", ContractEntity.class);
            
            getUserContractsTQ.setParameter("userEntityId", userApiEntity.getId());
            
            return getUserContractsTQ.getResultList();
        } catch (Exception e) {
            System.out.println("ManagerMicrimDB getUserContracts");
            e.printStackTrace();
            return null ;
        }
    }    
    
    /**
     * Get all contract with this parameters Status and User
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getUserContractsByStatus(UserApiEntity userApiEntity, ContractEntity contractEntity){
        
        try {
            TypedQuery < ContractEntity > getUserContractsTQ =
             entityManagerEM.createNamedQuery("getUserContractsByStatus", ContractEntity.class);
            
            getUserContractsTQ.setParameter("userEntityId", userApiEntity.getId());
            getUserContractsTQ.setParameter("statusContract", contractEntity.getStatusContract());
            
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
    public List < ContractEntity > getUserContractsByStatusDate(UserApiEntity userApiEntity, ContractEntity contractEntity){
        
        try {
            TypedQuery < ContractEntity > getUserContractsTQ =
             entityManagerEM.createNamedQuery("getUserContractsByStatusDate", ContractEntity.class);
            
            getUserContractsTQ.setParameter("userEntityId", userApiEntity.getId());
            getUserContractsTQ.setParameter("statusContract", contractEntity.getStatusContract());
            getUserContractsTQ.setParameter("dateIssued", contractEntity.getDateIssuedUnformatted() );
            
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
    public List < ContractEntity > getUserContractsByTitleStatus(UserApiEntity userApiEntity, ContractEntity contractEntity){
        try {
            TypedQuery < ContractEntity > getContractStatusByTitleTQ = 
             entityManagerEM.createNamedQuery("getUserContractsByTitleStatus", ContractEntity.class);

            getContractStatusByTitleTQ.setParameter("title", contractEntity.getTitle());
            getContractStatusByTitleTQ.setParameter("statusContract", contractEntity.getStatusContract());
            getContractStatusByTitleTQ.setParameter("userEntityId", userApiEntity.getId());

            return getContractStatusByTitleTQ.getResultList();            
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB1 getUserContractsByTitleStatus");
            e.printStackTrace();
            return null;
        }
    }     
      
    /**
     * Delete a contract
     * @param value
     * @param id 
     */
    public void deleteContract(ContractEntity contractEntity){       
        try {
            Long value = contractEntity.getId();
            contractEntityJpaController.destroy(value);
        } catch (NonexistentEntityException e) {
            e.printStackTrace();
        }
    }

    /**
     * DBG
     * get Contract By ID
     * @param id
     * @return 
     */
    public ContractEntity getContractByID(Long id) {
        return contractEntityJpaController.findContractEntity(id);
    }
       
    /**
     * Update contract
     * Chance status from outstanding to completed
     * Update field sell price
     * @param value
     * @param id 
     */
    public void updateContract(ContractEntity contractEntity){
//        init();
        
        try {
            EntityManager updateContractEM = 
             entityManagerFactoryEMF.createEntityManager();
                     
            if ( updateContractEM != null ){
                EntityTransaction updateTransactionET =
                 updateContractEM.getTransaction();

                updateTransactionET.begin();

                Query query = getQueryDB(contractEntity, updateContractEM);

                query.setParameter("id", contractEntity.getId() );

                query.executeUpdate();
                updateTransactionET.commit();
             }            
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB updateContract");
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * Get Query DB
     * @param value
     * @param contractEntity
     * @param entityManagerEM
     * @return Query
     */
    private Query getQueryDB(ContractEntity contractEntity, EntityManager entityManagerEM){

        // check contract.getPriceSell and contractEntity.getPriceSell are differnt       
        // DBG fa cagare
        if ( contractEntity.getTempQualcosaDBG() == 1 ){
            Query query = updatePriceSell(contractEntity, entityManagerEM);
            contractEntity.setTempQualcosaDBG(0);
            return query;
        }
        
       // update status to completed
        if ( contractEntity.getStatusContract().equals(StatusEnum.OUTSTADING.getStatus() )){
            // Change value from outstanding to completed
            Query query = updateStatusContract(contractEntity, entityManagerEM);
            return query;
        }
        
        // update status to expired
        if ( contractEntity.getStatusContract().equals(StatusEnum.EXPIRED.getStatus())){
            Query query = updateStatusContractRemoveID(contractEntity, entityManagerEM);
            return query;             
        }
        
        return null;
    }

        /**
     * Change value sell price
     * @param value
     * @param id
     * @param entityManagerEM
     * @return Query
     */
    private Query updatePriceSell(ContractEntity contractEntity, EntityManager entityManagerEM){
        Long id = contractEntity.getId();
        String value = contractEntity.getPriceBuyUnformatted();
        
        Query query = entityManagerEM.createQuery
        ("UPDATE ContractEntity contractEntity set CONTRACTENTITY.priceBuy = '"
        + value + "' WHERE CONTRACTENTITY.id = :id" );
        return query;        
    }
    
    /**
     * Change value from outstanding to completed
     * @param value
     * @param id
     * @param entityManagerEM
     * @return Query
     */
    private Query updateStatusContract(ContractEntity contractEntity, EntityManager entityManagerEM){
        Long id = contractEntity.getId();
        String value = contractEntity.getStatusContract();
        // :id
        
        Query query = entityManagerEM.createQuery
        ("UPDATE ContractEntity contractEntity set CONTRACTENTITY.statusContract = '"
        + value  + "' WHERE CONTRACTENTITY.id = :id"  );
        
        return query;
    }

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
    private Query updateStatusContractRemoveID(ContractEntity contractEntity, 
     EntityManager entityManagerEM){
        Long id = contractEntity.getId();
        String statusContract = contractEntity.getStatusContract();
        
        Query query = entityManagerEM.createQuery(
         "UPDATE ContractEntity contractEntity SET CONTRACTENTITY.statusContract ='" + statusContract 
         + "', CONTRACTENTITY.contractID='' WHERE CONTRACTENTITY.id = :id");
        return query;
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
     * @param contractEntity
     * @return 
     */
    public ContractEntity getContractEveId(ContractEntity contractEntity){
        try {
            TypedQuery < ContractEntity > getDBContractIdTQ =
             entityManagerEM.createNamedQuery("getContractsId", ContractEntity.class);

            getDBContractIdTQ.setParameter("id", contractEntity.getContractID());
            return getDBContractIdTQ.getSingleResult();                       
        } catch (Exception e) {
            System.out.println("ManagerMicrimsDB getDBContractId");
            e.printStackTrace();
            return null;
        }
    }        
    
    /**
     * Get all contract with this parameters User and Title
     * @param UserApiEntity userApiEntity
     * @param ContractEntity contractEntity
     * @return List < ContractEntity >
     */
    public List < ContractEntity > getUserContractsByTitle(UserApiEntity userApiEntity, ContractEntity contractEntity){
        try {
            TypedQuery < ContractEntity > getUserContractsByTitleTQ = 
             entityManagerEM.createNamedQuery("getUserContractsByTitle", ContractEntity.class);

            getUserContractsByTitleTQ.setParameter("title", contractEntity.getTitle());
            getUserContractsByTitleTQ.setParameter("userid", userApiEntity.getId());

            return getUserContractsByTitleTQ.getResultList();            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
    
}
