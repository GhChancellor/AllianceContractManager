/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLUser;
import java.util.List;


/**
 * DBG Forse da cancellare
 * @author lele
 */
public class ManagerLoginSql {
    // http://www.objectdb.com/java/jpa/persistence/update
    
    private static ManagerLoginSql instance = null;
    
    public static ManagerLoginSql getInstance (){
        if ( instance == null ) {
            instance = new ManagerLoginSql();
        }
        return instance;
    }
            
    public void initDBLogin(){
    }
    
    /**
     * Get User Api Index
     * @return List < UserApiIndexEntity >
     */
    public List < UserApiEntity > getUserApiEntities(){
        List < UserApiEntity > userApiEntities = 
         ManagerSQLUser.getInstance().getUserApiEntities();

        return userApiEntities;     
    }
    /**
     * Get User Api Entities without parameters userEnable
     * @return List < UserApiEntity > 
     */
    public List < UserApiEntity > getAllUserApiEntities(){
        List < UserApiEntity > userApiEntities = 
         ManagerSQLUser.getInstance().getUserApiEntities();

        return userApiEntities;     
    }
    
    
    
    /**
     * add write DB User Api from login
     * @param  
     */
    public void addUserApiIndex(UserApiEntity userApiEntity){
        ManagerSQLUser.getInstance().addUserApiEntity(userApiEntity);
    }
    
    /**
     * Delete from DB User Api
     * @param Integer id 
     */
    public void deleteUserApi(UserApiEntity userApiEntity){
        ManagerSQLUser.getInstance().deleteUserApiEntity(userApiEntity);
    }
    
    /**
     * Update value of User Api Entity
     * @param userApiEntity 
     */
    public void updateUserApiEntity(UserApiEntity userApiEntity){
        ManagerSQLUser.getInstance().updateUserApiEntity(userApiEntity);
    }
}
