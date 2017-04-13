/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.contract.formulas;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


/**
 *
 * @author lele
 */
public class ContractFormulas {
    private int priceBuy = 0;
    private int priceSell = 0;
    private int totalProfitPerShips = 0;
    private int profitSingleShip;
    private Double profitPercent =0.0;
    
    public ContractFormulas() {
        
    }
    
    public ContractFormulas(ContractEntity contractEntity) {
        calculateProfitSingleShip(contractEntity);
        calculateProfitPercent();        
    }    

    /**
     * Calculate Profit Single Ship
     * @return ContractFormulas
     */
    private void calculateProfitSingleShip(ContractEntity contractEntity){
        setPriceSell(Integer.parseInt(contractEntity.getPriceBuyUnformatted()));
        setPriceBuy(Integer.parseInt(contractEntity.getPriceSell()));
        setProfitSingleShip(getPriceBuy() - getPriceSell() );
    }
    
    /**
     * calculate Profit Percent
    */
    private void calculateProfitPercent(){
        Double profitPersent = (double) getProfitPerShip()/ getPriceSell() *100;
        setProfitPercent(profitPersent);
    }     

    /**
     * DGB
     * Get Total Protit All Ships
     * @param nameShip
     * @return String
     */
    public String calculateTotalProfitAllShips(ContractEntity contractEntity){
        System.out.println("calculateTotalProfitAllShips commentata");
//        contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
//        
//        List < ContractEntity > contractTitles = 
//         ManagerSQLMicrimsDB.getInstance().getContractStatusByTitle(contractEntity);
//        
//        for (ContractEntity contractTitle : contractTitles) {
//            setTotalProfitPerShips(Integer.parseInt(contractTitle.getPriceBuyUnformatted())  );
//        }
//        return formatterValue(getTotalProfitPerShips().toString());
        return null;
    }
  
    /**
     * Get price Buy
     * @return Integer
     */
    private int getPriceBuy() {
        return priceBuy;
    }

    /**
     * Set price Buy
     * @param priceBuy 
     */
    private void setPriceBuy(int priceBuy) {
        this.priceBuy = priceBuy;
    }    
            
    /**
     * Get SellOrder
     * @return Integer
     */
    private int getPriceSell() {
        return priceSell;
    }

    /**
     * Set Sell Order
     * @param priceSell 
     */
    private void setPriceSell(int priceSell) {
        this.priceSell = priceSell;
    }

    /**
     * Get Profit Percent
     * @return Integer
     */
    public Double getProfitPercent() {
        return profitPercent;
    }

    /**
     * Set Profit Percent
     * @param profitPercent 
     */
    private void setProfitPercent(Double profitPercent) {
        this.profitPercent = profitPercent;
    }

    /**
     * Get Total Profit
     * @return Integer
     */
    private Integer getTotalProfitPerShips() {
        return totalProfitPerShips;
    }

    /**
     * Set Total Profit
     * @param TotalprofitPerShips 
     */
    private void setTotalProfitPerShips(int totalProfitPerShips) {
        this.totalProfitPerShips += totalProfitPerShips;
    }
    
    /**
     * trasform number from 200000000 to 200.000.000
     * @param value
     * @return 
     */    
    private String formatterValue(String value){
        NumberFormat valueNF = NumberFormat.getNumberInstance(Locale.ITALY);
        valueNF.setGroupingUsed(true);
        String numberFormatted = valueNF.format(Double.parseDouble(value));
        return numberFormatted;
    }

    private void setProfitSingleShip(int profitSingleShip) {
        this.profitSingleShip = profitSingleShip;
    }

    /**
     * Get Profit Single Ship
     * @param contractEntity 
     */
    public Integer getProfitPerShip(){
        return profitSingleShip;
     }    
    
}
