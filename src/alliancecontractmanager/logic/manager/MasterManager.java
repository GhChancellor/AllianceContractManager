/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.db.entities.UserApiIndexEntity;
import alliancecontractmanager.gui.frame.ShowContractJFrame;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLUser;
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
        // Get User Api Index
        List < UserApiIndexEntity > userApiIndexEntitys =
         ManagerLoginSql.getInstance().getUserApiIndex();
        
        // create a Fake users
        if ( userApiIndexEntitys.isEmpty() )
            initFakeUser02();
        
        // Get User Api Index ( all list of users )
        List < UserApiIndexEntity > userApiIndexEntity = 
         ManagerSQLUser.getInstance().getUserApiIndexEntity();

        // Init contract User
        ManagerContractXmlMySql managerContractXmlMySql = 
         new ManagerContractXmlMySql(userApiIndexEntity); 
        
        
//        List < ContractEntity > contractEntitys = 
//         ManagerSQLMicrimsDB.getInstance().getContractsTitleAvoidDuplicate();
//        
//        for (ContractEntity contractEntity : contractEntitys) {
//            System.out.println(""+contractEntity.getTitle());
//        }
        
//        ShowContractJFrame showContractJFrame = new ShowContractJFrame();
//        showContractJFrame.setVisible(true);
//        showContractJFrame.setLocationRelativeTo(showContractJFrame);
//        
//        ShowReportShipJFrame showReportShipJFrame = new ShowReportShipJFrame();
//        showReportShipJFrame.setVisible(true);
//        showReportShipJFrame.setLocationRelativeTo(showReportShipJFrame);
        
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
        
        UserApiEntity userApiEntity03 = new UserApiEntity("555", "666", "lilo", false);
        userApiEntitys.add(userApiEntity03);
        
        return userApiEntitys;
    }    
    
    /** 
     * Create a fake users don't load XML FILE
     */
    private void initFakeUser02(){
        if ( ManagerSQLUser.getInstance().getUserApiIndexEntity().isEmpty() ){
 
            List < UserApiEntity > userApiEntitys = addFakeUserDBG();
  
            for (UserApiEntity userApiEntity : userApiEntitys) {
                
                UserApiIndexEntity userApiIndexEntity = new UserApiIndexEntity();                  
                userApiIndexEntity.addUserApiIndexEntitys(userApiEntity);
                ManagerSQLUser.getInstance().addUserApiIndexEntity( userApiIndexEntity );     
            }
        }
        return; 
    }
    
    /**
     * CON caricamento dati XML
     * @return 
     */
    private List < UserApiIndexEntity > initFakeUser(){
        if ( ManagerSQLUser.getInstance().getUserApiIndexEntity().isEmpty() ){
 
            List < UserApiEntity > userApiEntitys = addFakeUserDBG();
  
            for (UserApiEntity userApiEntity : userApiEntitys) {
                
                UserApiIndexEntity userApiIndexEntity = new UserApiIndexEntity();                  
                
//                ManagerContractXml.getInstance().loadXMLDBG(userApiEntity);
//                
//                List < ContractXml > contractXmls =
//                 ManagerContractXml.getInstance().getContractXmls();
//                
//                for (ContractXml contractXml : contractXmls) {
//                    ContractEntity contractEntity = writeValueInTheDatabaseFake(contractXml);
//                    userApiEntity.addContractEntitys( contractEntity );
//                }

                userApiIndexEntity.addUserApiIndexEntitys(userApiEntity);
                ManagerSQLUser.getInstance().addUserApiIndexEntity( userApiIndexEntity );     
            }
        }
        
        List < UserApiIndexEntity > userApiIndexEntity = ManagerSQLUser.getInstance().getUserApiIndexEntity();      
        return userApiIndexEntity;
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

    /**
     * mostra gli utenti e i contratti personali
     */
    private void displayDBG(){
        List < UserApiIndexEntity > userApiIndexEntity  = ManagerSQLUser.getInstance().getUserApiIndexEntity();
        
        for (UserApiIndexEntity userApiIndexEntity1 : userApiIndexEntity) {
            List < UserApiEntity > apiEntitys = userApiIndexEntity1.getUserApiIndexEntitys();
            
            for (UserApiEntity apiEntity : apiEntitys) {
                System.out.println(""+ apiEntity.getNameCharacter());
                
                List < ContractEntity > contractEntitys = apiEntity.getAllContractEntitys();
                for (ContractEntity contractEntity : contractEntitys) {
                    System.out.println(""+ contractEntity.getTitle());
                }
                System.out.println("");
            }
        }
 
    }    
    
}
