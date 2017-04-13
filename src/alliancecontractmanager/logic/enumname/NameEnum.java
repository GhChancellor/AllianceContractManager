/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.enumname;

/**
 *
 * @author lele
 */
public enum NameEnum {
    CONTRACTID("Contract ID"),
    TITLE("Title"),
    DATEISSUED("Date Issued"),
    DATECOMPLETED("Date Completed"),
    STATUSCONTRACT("Status Contract"),
    BUYORICE("Buy price"),
    SELLPRICE("Sell Price"),
    PERCENTPROFIT("% profit"),
    PROFIT("Profit"),
    SOLD("Sold"),
    TOTALPROFIT("Total profit");
        
    private String name;

    private NameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
