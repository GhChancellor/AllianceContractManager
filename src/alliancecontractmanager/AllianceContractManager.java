/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager;

import alliancecontractmanager.logic.manager.MasterManager;

/**
 * BUG NOTI
 * con contract2.XML, in Show report Contract, selezionando MOros, Revelation penso che sia un errore di formatazzione proveniente da eve
 * @author lele
 */
public class AllianceContractManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MasterManager masterManager = new MasterManager();
        masterManager.init();

    }
    
}
