/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.jtable;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.logic.enumname.NameEnum;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;

/**
 *
 * @author lele
 */
public class JTableShowContractModel extends JTableShowGenericModel{

    public JTableShowContractModel() {
        super(new String[]{ NameEnum.CONTRACTID.getName(), 
         NameEnum.STATUSCONTRACT.getName(), 
         NameEnum.TITLE.getName(), NameEnum.SELLPRICE.getName(), 
         NameEnum.BUYORICE.getName(), NameEnum.DATEISSUED.getName(),
         NameEnum.DATECOMPLETED.getName()});
    }

    /**
     * the 4° column is editable
     * @param row
     * @param column
     * @return boolean
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        if (column != 4){
            return false;
        }
        return true;
    }
        
    @Override
    public Object getValueAt(int row, int column) {
        switch ( column ){
            case 0:
                return getContract(row).getContractID();
            case 1:
                return getContract(row).getStatusContract();
            case 2:
                return getContract(row).getTitle();
            case 3:
                return getContract(row).getPriceSell();
            case 4:
                return getContract(row).getPriceBuy();
            case 5:
                return getContract(row).getDateIssuedFormatted();
            case 6: 
                return getContract(row).getDateCompletedFormatted();
            default:
                return null;
        }
    }
    
    /**
     * Get a single cell
     * @param aValue
     * @param row
     * @param column 
     */
    @Override
    public void setValueAt(Object aValue, int row, int column){
        // NO lui non sa l'id contract!!!!! Glielo dici tu ShowContractJPanel nel metodo writeValueToTable

        ContractEntity contractEntity = getObjectValue(aValue, row, column);
        contractEntity.setTempQualcosaDBG(1);
        
        // update sell price
        ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);        
    }    
}
