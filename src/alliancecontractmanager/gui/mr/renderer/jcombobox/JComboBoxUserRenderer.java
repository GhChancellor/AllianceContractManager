/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.renderer.jcombobox;

import alliancecontractmanager.db.entities.UserApiEntity;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author lele
 */
public class JComboBoxUserRenderer extends BasicComboBoxRenderer{

    public JComboBoxUserRenderer() {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); //To change body of generated methods, choose Tools | Templates.
        
        if ( c != null && value instanceof UserApiEntity){
            ((JLabel)c).setText(((UserApiEntity)value).getNameCharacter() );
        }
        return this;
    }

    
    
    
}
