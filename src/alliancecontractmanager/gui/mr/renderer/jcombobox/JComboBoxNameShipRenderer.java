/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.renderer.jcombobox;

import alliancecontractmanager.db.entities.ContractEntity;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author lele
 */
public class JComboBoxNameShipRenderer extends BasicComboBoxRenderer{

    public JComboBoxNameShipRenderer() {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if ( c != null && value instanceof ContractEntity){
            ((JLabel)c).setText(((ContractEntity)value).getTitle() );
        }
        return this;
    }
    
    
}