/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.remainingships;

/**
 *
 * @author lele
 */
public class RemainingShips {
    private String doctrineNameShip;
    private Integer numberShip;
    
    public RemainingShips(String doctrineNameShip, Integer numberShip) {
        this.doctrineNameShip = doctrineNameShip;
        this.numberShip = numberShip;
    }

    /**
     * Get RemainingShips Name Ship
     * @return String
     */
    public String getDoctrineNameShip() {
        return doctrineNameShip;
    }

    /**
     * Set RemainingShips Name Ship
     * @param String doctrineNameShip 
     */
    public void setDoctrineNameShip(String doctrineNameShip) {
        this.doctrineNameShip = doctrineNameShip;
    }

    /**
     * Get Number Ship
     * @return Integer
     */
    public Integer getNumberShip() {
        return numberShip;
    }

    /**
     * Set Number Ship
     * @param numberShip 
     */
    public void setNumberShip(Integer numberShip) {
        this.numberShip = numberShip;
    }
}
