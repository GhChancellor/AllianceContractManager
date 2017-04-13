/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.JComboBox;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.db.entities.UserApiIndexEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import alliancecontractmanager.logic.manager.ManagerLoginSql;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author lele
 */
public class JComboBoxNameShip02Model extends JComboBoxGenericModel {

    public JComboBoxNameShip02Model() {
        super();
        if (Beans.isDesignTime()) {
            List< ContractEntity> contractEntitys = new ArrayList<>();
        }else{
            init();
        }
    }

    public void init() {

        UserApiIndexEntity userApiIndexEntity =
         ManagerLoginSql.getInstance().getUserApiIndex().get(0);

        UserApiEntity userApiEntity = userApiIndexEntity.getUserApiIndexEntitys().get(0);

        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
        
        List< ContractEntity> contractEntitys = 
         ManagerSQLMicrimsDB.getInstance().getUserContractsByStatus(userApiEntity, contractEntity);

         if ( contractEntitys != null )   {
            addElements(contractEntitys,1);
         }
        
    }

}
