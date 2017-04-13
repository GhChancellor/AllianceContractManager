/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.JComboBox;

import alliancecontractmanager.db.entities.ContractEntity;
import java.beans.Beans;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author lele
 */
public class JComboBoxGenericModel extends DefaultComboBoxModel<ContractEntity>{
    private Map < String, ContractEntity > contractShipsNameMap = new TreeMap<>(); 
    
    public JComboBoxGenericModel() {
        if ( Beans.isDesignTime() ){
            Map < String, ContractEntity > contractShipsNameMap = new TreeMap<>();            
        }else{
//            initShipsName();
        }
    }
    
    /**
     * Get Contract title
     * @return Map < String, ContractEntity > contractNameShipMap
     */
    public Map < String, ContractEntity > getNameShip(){
        return contractShipsNameMap;
    }

    /**
     * Put contract into jComboBox and avoid contracts with the same name
     * @param List < ContractEntity > contractEntitys 
     */
    private void addElementsAvoidDuplicate(List < ContractEntity > contractEntitys, int titleOrDate){
        if ( !contractShipsNameMap.isEmpty() ){
            contractShipsNameMap.clear();
        }        
                
        for (ContractEntity contractEntity : contractEntitys) {
            if (!contractShipsNameMap.containsKey( getTitleOrDate(contractEntity, titleOrDate))){
                contractShipsNameMap.put(getTitleOrDate(contractEntity, titleOrDate), contractEntity);                     
             }
        }

    }    

    /**
     * Add all Elements to JcomboBoxNameShip avoid contracts with the same name
     * @param List < ContractEntity > contractEntitys 
     */
    public void addElements(List < ContractEntity > contractEntitys, int titleOrDate){
        addElementsAvoidDuplicate(contractEntitys, titleOrDate);
        Map < String, ContractEntity > nameShips = getNameShip();
        
        for (Map.Entry<String, ContractEntity> entry : nameShips.entrySet()) {
            this.addElement(entry.getValue());   
        }
    }      
    
    /**
     * Get Date or title
     * @param contractEntity
     * @return String
     */
    private String getTitleOrDate(ContractEntity contractEntity, int titleOrDate){
        
        switch ( titleOrDate ){
            case 1:
                return contractEntity.getTitle();
            case 2:
                return contractEntity.getDateIssuedFormatted();
        }
        return null;
    }
}
