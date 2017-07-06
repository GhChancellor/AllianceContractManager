/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.jtable;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lele
 */
public class JTableShowGenericModel extends DefaultTableModel {

    public JTableShowGenericModel(Object[] objects) {
        super(objects, 0);
    }

    /**
     * Add Contract
     *
     * @param ContractEntity contractEntity
     */
    public void addContract(ContractEntity contractEntity) {
        this.dataVector.add(contractEntity);
    }

    /**
     * Get Contract
     *
     * @param index
     * @return ContractEntity
     */
    public ContractEntity getContract(int index) {
        return (ContractEntity) dataVector.get(index);
    }

    /**
     * Clear table
     */
    public void clear() {
        this.dataVector.clear();
        fireTableDataChanged();
    }

    // DBG
    public void reload() {
        List<Long> ids = new ArrayList<>();
        for (Object object : dataVector) {
            ids.add(((ContractEntity) object).getId());
        }
        List<ContractEntity> tmp = new ArrayList<>();
        for (Long id : ids) {
            ContractEntity cc = ManagerSQLMicrimsDB.getInstance().getContractEntityById(id);
            if (cc != null) {
                tmp.add(cc);

            }
        }
        this.dataVector.clear();
        for (ContractEntity contractEntity : tmp) {
            System.out.println("CONTRACT STATUS: "+contractEntity.getStatusContract());
            this.dataVector.add(contractEntity);
        }
        refesh();
    }

    /**
     * Refresh table
     */
    public void refesh() {
        int column = this.getColumnCount();
        int row = this.getRowCount();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fireTableCellUpdated(row, column);
            }
        }
        fireTableDataChanged();
    }

    public void removeElement(int index) {
        this.dataVector.remove(index);
        fireTableRowsDeleted(index, index);
    }

    public ContractEntity getObjectValue(Object aValue, int row, int column) {

        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setContractID(getContract(row).getContractID());

        // get Contract Eve Id ( from Eve Server )      
        contractEntity = ManagerSQLMicrimsDB.getInstance().getContractEveId(contractEntity);

        contractEntity.setPriceBuy(aValue.toString());

        return contractEntity;
    }
}
