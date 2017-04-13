/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.logicgui.event;

import alliancecontractmanager.db.entities.UserApiEntity;

/**
 *
 * @author lele
 */
public interface Listener {
    // public void showReportShipJPanelUpdate(UserApiEntity userApiEntity);
    public void updateGui(UserApiEntity userApiEntity);
}
