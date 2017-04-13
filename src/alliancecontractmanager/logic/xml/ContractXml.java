/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.xml;

import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lele
 */
@XmlRootElement(name = "row")
public class ContractXml {
    private String contractID = null; // < -- questo
    private String status = null; // < -- questo
    private String title = null; // < -- questo    
    private String price = null; // < -- questo    
    private String dateCompleted = null;
    private String dateIssued = null;
    private String dateExpired = null;

    /**
     * Get Date Expired
     * @return String
     */
    @XmlAttribute
    public String getDateExpired() {
        return dateExpired;
    }

    /**
     * Set Date Expired
     * @param String dateExpired 
     */
    public void setDateExpired(String dateExpired) {
        this.dateExpired = dateExpired;
    }
    
    /**
     * Get Date Issued
     * @return String
     */
    @XmlAttribute
    public String getDateIssued() {
        return dateIssued;
    } 
    
    /**
     * XML get ContractID
     * @return String
     */
    @XmlAttribute
    public String getContractID() {
        return contractID;
    }

    /**
     * XML Get Status Contract
     * @return String
     */
    @XmlAttribute
    public String getStatus() {
        return status;
    }

    /**
     * XML Get Title
     * @return String
     */
    @XmlAttribute
    public String getTitle() {
        return title;
    }

    /**
     * XML Get Price
     * @return String
     */
    @XmlAttribute
    public String getPrice() {
        return price;
    }

    /**
     * XML Get Date
     * @return String
     */
    @XmlAttribute
    public String getDateCompleted() {
        return dateCompleted;
    }

    /**
     * Set ContractID
     * @param String contractID 
     */
    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    /**
     * Set Status
     * @param String status 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Set Title
     * @param String title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set Price
     * @param String price 
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Set Date Completed
     * @param String dateCompleted 
     */
    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    /**
     * Set Date Issued
     * @param String dateIssued 
     */
    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }      
    
}
