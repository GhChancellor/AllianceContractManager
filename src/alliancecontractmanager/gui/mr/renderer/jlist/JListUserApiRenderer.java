/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.renderer.jlist;

import alliancecontractmanager.db.entities.UserApiEntity;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author lele
 */
public class JListUserApiRenderer extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        if ( c != null && value instanceof UserApiEntity){
            ((JLabel)c).setText( ((UserApiEntity)value).getShortReport() );
            if ( ((UserApiEntity)value).isUserEnable() == false ){
                ((JLabel)c).setForeground(Color.GRAY);
            }else{
                ((JLabel)c).setForeground(Color.BLACK);
            }
        }
        
        
        return c;
    }
    
}
