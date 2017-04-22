/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.panel.mainpanel;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.exception.NoVesselSoldException;
import alliancecontractmanager.logic.contract.formulas.ContractFormulas;
import alliancecontractmanager.logic.enumname.StatusEnum;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author lele
 */
public class ShowReportShipJPanel extends javax.swing.JPanel{

    /**
     * Creates new form ShowNameShipRenderer
     */
    public ShowReportShipJPanel() {
        initComponents();
        
        if ( Beans.isDesignTime()){
            List < ContractEntity > contractEntitys = new ArrayList<>();            
        }else{
//           initShowNameShipJPanel();    
            initGui();
        }
        
    }

    /**
     * init jComboBoxNameShip02 get name from DB
     */
    public void initShowNameShipJPanel(){
//        ManagerDispatcher.getInstance().addListeners(this);               
    }

    public void initGui(){
        if (jComboBoxNameShip02.getSelectedIndex() != -1 ){
            jComboBoxContractDateIssuedModel1.init( (UserApiEntity) jComboBoxUser.getSelectedItem(), 
              (ContractEntity) jComboBoxNameShip02.getSelectedItem()  );                
        }else{
            try {
                throw new NoVesselSoldException();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxNameShip02Model1 = new alliancecontractmanager.gui.mr.model.JComboBox.JComboBoxNameShip02Model();
        jComboBoxNameShipRenderer1 = new alliancecontractmanager.gui.mr.renderer.jcombobox.JComboBoxNameShipRenderer();
        jTableShowReportShipModel1 = new alliancecontractmanager.gui.mr.model.jtable.JTableShowReportShipModel();
        jComboBoxUserModel1 = new alliancecontractmanager.gui.mr.model.JComboBox.JComboBoxUserModel();
        jComboBoxUserRenderer1 = new alliancecontractmanager.gui.mr.renderer.jcombobox.JComboBoxUserRenderer();
        jComboBoxContractDateIssuedModel1 = new alliancecontractmanager.gui.mr.model.JComboBox.jComboBoxContractDateIssuedModel();
        jComboBoxContractDateIssuedRenderer1 = new alliancecontractmanager.gui.mr.renderer.jcombobox.jComboBoxContractDateIssuedRenderer();
        ShowNameShipJPanel_Continer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableShowReportShip = new javax.swing.JTable();
        jComboBoxNameShip02 = new javax.swing.JComboBox<>();
        jComboBoxContractDateIssued = new javax.swing.JComboBox<>();
        jComboBoxUser = new javax.swing.JComboBox<>();

        jComboBoxNameShipRenderer1.setText("jComboBoxNameShipRenderer1");

        jComboBoxUserRenderer1.setText("jComboBoxUserRenderer1");

        jComboBoxContractDateIssuedRenderer1.setText("jComboBoxContractDateIssuedRenderer1");

        setBackground(new java.awt.Color(255, 102, 102));

        ShowNameShipJPanel_Continer.setBackground(new java.awt.Color(51, 255, 51));

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseReleased(evt);
            }
        });

        JTableShowReportShip.setModel(jTableShowReportShipModel1);
        jScrollPane1.setViewportView(JTableShowReportShip);
        if (JTableShowReportShip.getColumnModel().getColumnCount() > 0) {
            JTableShowReportShip.getColumnModel().getColumn(0).setHeaderValue("Date Issued");
            JTableShowReportShip.getColumnModel().getColumn(1).setHeaderValue("Sell Price");
            JTableShowReportShip.getColumnModel().getColumn(2).setHeaderValue("Buy price");
            JTableShowReportShip.getColumnModel().getColumn(3).setHeaderValue("% profit");
            JTableShowReportShip.getColumnModel().getColumn(4).setHeaderValue("Profit");
        }

        javax.swing.GroupLayout ShowNameShipJPanel_ContinerLayout = new javax.swing.GroupLayout(ShowNameShipJPanel_Continer);
        ShowNameShipJPanel_Continer.setLayout(ShowNameShipJPanel_ContinerLayout);
        ShowNameShipJPanel_ContinerLayout.setHorizontalGroup(
            ShowNameShipJPanel_ContinerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE)
        );
        ShowNameShipJPanel_ContinerLayout.setVerticalGroup(
            ShowNameShipJPanel_ContinerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
        );

        jComboBoxNameShip02.setModel(jComboBoxNameShip02Model1);
        jComboBoxNameShip02.setRenderer(jComboBoxNameShipRenderer1);
        jComboBoxNameShip02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNameShip02ActionPerformed(evt);
            }
        });

        jComboBoxContractDateIssued.setModel(jComboBoxContractDateIssuedModel1);
        jComboBoxContractDateIssued.setRenderer(jComboBoxContractDateIssuedRenderer1);
        jComboBoxContractDateIssued.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxContractDateIssuedActionPerformed(evt);
            }
        });

        jComboBoxUser.setModel(jComboBoxUserModel1);
        jComboBoxUser.setRenderer(jComboBoxUserRenderer1);
        jComboBoxUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ShowNameShipJPanel_Continer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(2, 2, 2))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jComboBoxUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxNameShip02, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxContractDateIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(265, 265, 265))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxNameShip02, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxContractDateIssued, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(ShowNameShipJPanel_Continer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxNameShip02ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNameShip02ActionPerformed
        jComboBoxNameShip02ActionPerformedDBG(evt);
    }//GEN-LAST:event_jComboBoxNameShip02ActionPerformed

    private void jComboBoxContractDateIssuedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxContractDateIssuedActionPerformed
        // http://stackoverflow.com/questions/5937017/how-to-convert-a-date-in-this-format-tue-jul-13-000000-cest-2010-to-a-java-d
        // http://stackoverflow.com/questions/1341104/parameter-in-like-clause-jpql
        
        if ( jComboBoxContractDateIssued.getItemCount() == 0){
            return;
        }
        
        UserApiEntity userApiEntity = (UserApiEntity) jComboBoxUser.getSelectedItem();
        ContractEntity contractEntity = (ContractEntity) jComboBoxContractDateIssued.getSelectedItem();
                
        List < ContractEntity > contractEntitys = 
         ManagerSQLMicrimsDB.getInstance().getUserContractsByStatusDate(userApiEntity, contractEntity);
        
        for (ContractEntity contractEntity1 : contractEntitys) {
            System.out.println("XXX "+ contractEntity1.getTitle() + " " + 
             contractEntity1.getDateIssuedFormatted());
        }
        
        writeValueToTable(contractEntitys);  
    }//GEN-LAST:event_jComboBoxContractDateIssuedActionPerformed
    
    private void jComboBoxUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxUserActionPerformed
        UserApiEntity userApiEntity = (UserApiEntity) jComboBoxUser.getSelectedItem();
       
        jComboBoxNameShip02Model1.removeAllElements();
        jComboBoxContractDateIssuedModel1.removeAllElements();
        
        
        int selectedUserIndex = jComboBoxUser.getSelectedIndex();
       
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
        
        if ( jComboBoxNameShip02.getSelectedIndex() == -1 ) {
            String titleContract = userApiEntity.getAllContractEntitys().get(0).getTitle();
            contractEntity.setTitle(titleContract);
            
        }else{
            contractEntity.setTitle(jComboBoxNameShip02.getSelectedItem().toString() );
        }
        
        List < ContractEntity > contractEntitys = 
         ManagerSQLMicrimsDB.getInstance().getUserContractsByStatus(userApiEntity, contractEntity);

        if ( ! contractEntitys.isEmpty() ){
            
            // DBG Perchè " saltella " a jComboBoxNameShip02ActionPerformed ???            
            jComboBoxNameShip02Model1.addElements(contractEntitys,1);

            

//            contractEntitys = ManagerSQLMicrimsDB.getInstance().getUserContractsByTitleStatus(userApiEntity, contractEntity);
//            // DBG RIPETIZIONE
//            jComboBoxContractDateIssuedModel1.setDate(userApiEntity, contractEntity);


            writeValueToTable(contractEntitys);             
            
        }        
    }//GEN-LAST:event_jComboBoxUserActionPerformed

    private void jScrollPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseReleased

    }//GEN-LAST:event_jScrollPane1MouseReleased

    /**
     * Write Value To jTable in ShowReportShipJPanel
     * @param contractEntity 
     */
    private void writeValueToTable( List < ContractEntity > contractEntitys ){
        jTableShowReportShipModel1.clear();
        
        for (ContractEntity contractEntity : contractEntitys) {
            if ( contractEntity.getStatusContract().equals(StatusEnum.COMPLETED.getStatus())){
                if (!contractEntity.getPriceBuy().isEmpty() &&
                    !contractEntity.getPriceSell().isEmpty()){

                    ContractFormulas contractFormulas = new ContractFormulas(contractEntity);
                    contractEntity.setProfitPercent(contractFormulas.getProfitPercent());
                    contractEntity.setProfitPerShip(contractFormulas.getProfitPerShip() );
                }
            }
            jTableShowReportShipModel1.addContract(contractEntity);              
        }
    }    
    
    private void jComboBoxNameShip02ActionPerformedDBG(java.awt.event.ActionEvent evt) { 
        System.out.println("jComboBoxNameShip02ActionPerformed");
        
        if ( jComboBoxNameShip02.getItemCount() == 0 ){
            return;
        }
        
        if ( jComboBoxNameShip02.getSelectedIndex() == -1 ){
            try {
                throw new NoVesselSoldException();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
            return;
        }
        
        jComboBoxContractDateIssuedModel1.removeAllElements();
        
        ContractEntity contractEntity = new ContractEntity();      
        contractEntity.setTitle( ((ContractEntity) jComboBoxNameShip02.getSelectedItem()).getTitle());
        contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());    
       
        UserApiEntity userApiEntity = ( UserApiEntity ) jComboBoxUser.getSelectedItem();
        
        List < ContractEntity > contractEntitys = 
         ManagerSQLMicrimsDB.getInstance().getUserContractsByTitleStatus(userApiEntity, contractEntity);     
        
        // DBG RIPETIZIONE
        jComboBoxContractDateIssuedModel1.setDate(userApiEntity, contractEntity);

        writeValueToTable(contractEntitys);          
    }          

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableShowReportShip;
    private javax.swing.JPanel ShowNameShipJPanel_Continer;
    private javax.swing.JComboBox<ContractEntity> jComboBoxContractDateIssued;
    private alliancecontractmanager.gui.mr.model.JComboBox.jComboBoxContractDateIssuedModel jComboBoxContractDateIssuedModel1;
    private alliancecontractmanager.gui.mr.renderer.jcombobox.jComboBoxContractDateIssuedRenderer jComboBoxContractDateIssuedRenderer1;
    private javax.swing.JComboBox<ContractEntity> jComboBoxNameShip02;
    private alliancecontractmanager.gui.mr.model.JComboBox.JComboBoxNameShip02Model jComboBoxNameShip02Model1;
    private alliancecontractmanager.gui.mr.renderer.jcombobox.JComboBoxNameShipRenderer jComboBoxNameShipRenderer1;
    private javax.swing.JComboBox<UserApiEntity> jComboBoxUser;
    private alliancecontractmanager.gui.mr.model.JComboBox.JComboBoxUserModel jComboBoxUserModel1;
    private alliancecontractmanager.gui.mr.renderer.jcombobox.JComboBoxUserRenderer jComboBoxUserRenderer1;
    private javax.swing.JScrollPane jScrollPane1;
    private alliancecontractmanager.gui.mr.model.jtable.JTableShowReportShipModel jTableShowReportShipModel1;
    // End of variables declaration//GEN-END:variables

//    @Override
//    public void showReportShipJPanelUpdate(ContractEntity contractEntity) {
        
//        this.jComboBoxContractDateIssuedModel1.removeAllElements();
////        this.jComboBoxContractDateIssuedRenderer.addItem("All");
//        
//        this.jComboBoxContractDateIssuedModel1.initContractDateIssued(nameShip);
//        if ( jComboBoxContractDateIssuedRenderer.getItemCount() == -1 ){
//            return;
//        }
//
//        this.jComboBoxContractDateIssuedRenderer.setSelectedIndex(0);
//    }
    

}
