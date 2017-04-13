/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.gui.logicgui.event.Listener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lele
 */
public class ManagerDispatcher {
    private List < Listener > listeners = new ArrayList<>();
    private static ManagerDispatcher instance = null;
    
    public static ManagerDispatcher getInstance(){
        if ( instance == null ){
            instance = new ManagerDispatcher();
        }
        return instance;
    }
    
    public ManagerDispatcher() {
        super();
    }
    
    public void addDispatcher(Listener listener){
        listeners.add(listener);
    }
    
    public void updateGui(UserApiEntity userApiEntity){
        for (Listener listener : listeners) {
            listener.updateGui(userApiEntity);
        }
    }
}
