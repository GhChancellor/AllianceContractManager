/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.JComboBox;

import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.db.entities.UserApiIndexEntity;
import alliancecontractmanager.logic.manager.ManagerLoginSql;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author lele
 */
public class JComboBoxUserModel extends DefaultComboBoxModel<UserApiEntity>{

    public JComboBoxUserModel(){
        initJComboBoxUser();
    }
    
    
    /**
     * DBG Perchè per allacciare il model alla GUI devo commentare la parte sotto?
     */
    private void initJComboBoxUser(){

        if ( Beans.isDesignTime() ){
           List < UserApiIndexEntity >  userApiIndexEntitys = new ArrayList<>();
        }else{
            List < UserApiIndexEntity >  userApiIndexEntitys = 
             ManagerLoginSql.getInstance().getUserApiIndex();

            for (UserApiIndexEntity userApiIndexEntity : userApiIndexEntitys) {
                List < UserApiEntity > userApiEntitys = 
                 userApiIndexEntity.getUserApiIndexEntitys();

                for (UserApiEntity userApiEntity : userApiEntitys) {
                    this.addElement(userApiEntity);
                }
            }            
        }
        
    }
}
