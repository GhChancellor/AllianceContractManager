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
public enum StatusEnum {
    COMPLETED("Completed"),
    OUTSTADING("Outstanding"),
    EXPIRED("Expired"),
    DELETED("Deleted"),
    ALL("All");
    
    private String status;

    private StatusEnum(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
