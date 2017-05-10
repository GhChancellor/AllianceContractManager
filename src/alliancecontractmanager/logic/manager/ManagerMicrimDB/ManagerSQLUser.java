/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager.ManagerMicrimDB;

import alliancecontractmanager.db.controllers.UserApiEntityJpaController;
import alliancecontractmanager.db.controllers.exceptions.NonexistentEntityException;
import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author lele
 */
public class ManagerSQLUser {
    private static ManagerSQLUser instance = null;
    
    private EntityManager entityManagerEM = 
     Persistence.createEntityManagerFactory("AllianceContractManagerPU").createEntityManager();

    // Si può migliorare
    private EntityManagerFactory entityManagerFactoryEMF =
     Persistence.createEntityManagerFactory("AllianceContractManagerPU");    
    
    private UserApiEntityJpaController 
     userKeyIDJpaController = new UserApiEntityJpaController
     (Persistence.createEntityManagerFactory("AllianceContractManagerPU"));
    
    public static ManagerSQLUser getInstance(){
        if ( instance == null ){
            instance = new ManagerSQLUser();
        }
        return instance;
    }

    /**
     * get User Api
     * @return List < UserApiEntity > UserApiEntity
     */
    public List < UserApiEntity > getUserApiEntities(){
        try {
            EntityManager getUserApiEntityEM = entityManagerEM;

            TypedQuery < UserApiEntity > getUserApiEntityTQ =
             getUserApiEntityEM.createNamedQuery("getUserApiEntity", UserApiEntity.class);
            
            getUserApiEntityTQ.setParameter("userEnable", true);

            return getUserApiEntityTQ.getResultList();            
        } catch (Exception e) {
            e.printStackTrace();
            return null;            
        }
    }
    
    /**
     * Add User Api Inde xEntity
     * @param userApiEntity 
     */
    public void addUserApiEntity(UserApiEntity userApiEntity ){
        try {
            UserApiEntityJpaController userApiEntityJpaController = new UserApiEntityJpaController(entityManagerFactoryEMF);            
            userApiEntityJpaController.create(userApiEntity);        
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }    
    
    /**
     * Update User Api Index 
     * @param UserApiIndexEntity userApiIndexEntity 
     */
    public void updateUserApiEntity(UserApiEntity userApiEntity){
        try {
            UserApiEntityJpaController userApiEntityJpaController = 
             new UserApiEntityJpaController(entityManagerFactoryEMF);

            userApiEntityJpaController.edit(userApiEntity);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete User Api Entity
     * @param UserApiEntity userApiEntity 
     */
    public void deleteUserApiEntity(UserApiEntity userApiEntity){
        UserApiEntityJpaController userApiEntityJpaController = 
         new UserApiEntityJpaController(entityManagerFactoryEMF);
        
        unlinkContract(userApiEntity);

        try {
            userApiEntityJpaController.destroy(userApiEntity.getId());
        } catch (NonexistentEntityException ex) {
            ex.printStackTrace();
            Logger.getLogger(ManagerSQLUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    /**
     * Unlink contract from user
     * @param userApiEntity 
     */
    private void unlinkContract(UserApiEntity userApiEntity){
       if ( !userApiEntity.getAllContractEntitys().isEmpty() ){
           try {
               userApiEntity.getAllContractEntitys().clear();
               
               userKeyIDJpaController.edit(userApiEntity);
           } catch (Exception ex) {
               Logger.getLogger(ManagerSQLUser.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
}
