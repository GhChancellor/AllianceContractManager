/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.gui.frame.ShowContractJFrame;
import alliancecontractmanager.gui.frame.ShowReportShipJFrame;
import alliancecontractmanager.logic.xml.ContractXml;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lele
 */
public class MasterManager {

    public MasterManager() {
    }
    
    public void init(){
        System.out.println("");
        List < UserApiEntity > userApiEntitys = 
         ManagerLoginSql.getInstance().getUserApiEntities();
        
        // create the fake users
        if (userApiEntitys.isEmpty()){
            initFakeUser();
            userApiEntitys = ManagerLoginSql.getInstance().getAllUserApiEntities();
        }
            

        // Init contract User
        ManagerContracts managerContractXmlMySql = 
         new ManagerContracts(userApiEntitys); 
        
        
//        List < ContractEntity > contractEntitys = 
//         ManagerSQLMicrimsDB.getInstance().getContractsTitleAvoidDuplicate();
//        
//        for (ContractEntity contractEntity : contractEntitys) {
//            System.out.println(""+contractEntity.getTitle());
//        }
        
        ShowContractJFrame showContractJFrame = new ShowContractJFrame();
        showContractJFrame.setVisible(true);
        showContractJFrame.setLocationRelativeTo(showContractJFrame);
        
//        ShowReportShipJFrame showReportShipJFrame = new ShowReportShipJFrame();
//        showReportShipJFrame.setVisible(true);
//        showReportShipJFrame.setLocationRelativeTo(showReportShipJFrame);
//        
//        ShowRemainShipsJFrame showRemainShipsJFrame = new ShowRemainShipsJFrame();
//        showRemainShipsJFrame.setVisible(true);
//        showRemainShipsJFrame.setLocationRelativeTo(showRemainShipsJFrame);     
    }
    
    private  List < UserApiEntity > addFakeUserDBG(){
        List < UserApiEntity > userApiEntitys = new ArrayList<>();
        UserApiEntity userApiEntity = new UserApiEntity("111", "222", "lele", true);
        userApiEntitys.add(userApiEntity);
        
        UserApiEntity userApiEntity02 = new UserApiEntity("333", "444", "luca", true);
        userApiEntitys.add(userApiEntity02);
        
        UserApiEntity userApiEntity03 = new UserApiEntity("555", "666", "lilo", true);
        userApiEntitys.add(userApiEntity03);
        
        return userApiEntitys;
    }    
    
    /** 
     * Create a fake users don't load XML FILE
     */
    private List < UserApiEntity > initFakeUser(){
        List < UserApiEntity > userApiEntitys = addFakeUserDBG();
        for (UserApiEntity userApiEntity : userApiEntitys) {
            ManagerLoginSql.getInstance().addUserApiIndex(userApiEntity);
        }
        return userApiEntitys;
    }
    
    /**
     * Write Xml Value In The Data base
     * @param contractXml 
     */
    private ContractEntity writeValueInTheDatabaseFake(ContractXml contractXml){
        try {
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setContractID(contractXml.getContractID());
            contractEntity.setPriceSell( contractXml.getPrice()) ;
            contractEntity.setPriceBuy( "0" );
            contractEntity.setStatusContract(contractXml.getStatus()); 
            contractEntity.setTitle( contractXml.getTitle() );
            contractEntity.setDateIssued(contractXml.getDateIssued());
            contractEntity.setDateExpired(contractXml.getDateExpired());
            if (!contractXml.getDateCompleted().equals("") )
                contractEntity.setDateCompleted(contractXml.getDateCompleted());                       
            return contractEntity;
        } catch (Exception e) {
            System.out.println("ManagerContractXmlMySql writeValueInTheDatabase");
            e.printStackTrace();
        }          
        return null;
    } 
    
}
