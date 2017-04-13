/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.JComboBox;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import java.beans.Beans;
import java.util.List;

/**
 *
 * @author lele
 */
public class jComboBoxContractDateIssuedModel extends JComboBoxGenericModel {

    public jComboBoxContractDateIssuedModel() {
        if (!Beans.isDesignTime()) {
//            init();
        }
    }

    public void init(UserApiEntity userApiEntity, ContractEntity contractEntity ){
        setDate(userApiEntity, contractEntity);
    }
    
    public void setDate(UserApiEntity userApiEntity, ContractEntity contractEntity){
        List< ContractEntity> contractEntitys = 
         ManagerSQLMicrimsDB.getInstance().getUserContractsByTitleStatus(userApiEntity, contractEntity);

        // 2 is mean "add date"
        addElements(contractEntitys,2);        
    }
}