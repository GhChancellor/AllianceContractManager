/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.jtable;

import alliancecontractmanager.logic.remainingships.RemainingShips;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lele
 */
public class JTableShowRemainOutstadingModel extends DefaultTableModel{
    private Map < String, RemainingShips > doctrineMap = new HashMap<>();
    
    public JTableShowRemainOutstadingModel() {
        super(new String[]{"Ship outstanding","Number ship oustanding"},0);
//        super(new String[]{"Ship outstanding","Number ship oustanding"});
    }

    public Map < String, RemainingShips> getContract( int index ){
        return (Map < String, RemainingShips>) dataVector.get(index);
    }    
    
    @Override
    public Object getValueAt(int row, int column) {
        
        Object[] entries=doctrineMap.entrySet().toArray();
        Map.Entry entry=(Map.Entry)entries[row];
        
        switch ( column ){
            case 0:
                return ((RemainingShips) entry.getValue()).getDoctrineNameShip();
            case 1:
                return ((RemainingShips) entry.getValue()).getNumberShip();
            default:
                return null;
        }        
    }
        
    /**
     * Add Contract
     * @param Map < String, RemainingShips > doctrineMap
     */
    public void addContract( RemainingShips remainingShips) {
        this.dataVector.add(remainingShips);
        this.doctrineMap.put(remainingShips.getDoctrineNameShip(), remainingShips);
    }       

    /**
     * Clear table
     */
    public void clear(){
        this.dataVector.clear();
        fireTableDataChanged();
    }
    
    /**
     * Refresh table
     */
    public void refesh(){
        Integer column = this.getColumnCount();
        Integer row = this.getRowCount();
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                fireTableCellUpdated(row, column);
            }
        }
    }   
    
}
