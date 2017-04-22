/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.db.entities;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 * @NamedQuery( name = "getDBContractsTitle", 
  query = "SELECT c FROM ContractEntity c ORDER BY C.title ASC"), 
  * // http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
 * @author lele
 */


/*
    https://www.tutorialspoint.com/javaexamples/jdbc_innerjoin.htm
    http://stackoverflow.com/questions/13334504/mysql-joins-over-3-tables
*/



@Entity
@NamedQueries({
     
// Get all contracts with this parameter User
@NamedQuery(name = "getUserContracts",
 query = "SELECT a FROM ContractEntity a WHERE a.userEntityId= :userEntityId ORDER BY a.title ASC"),
    
 // Get all contracts with this parameters User and Title
@NamedQuery(name = "getUserContractsByTitle",
 query = "SELECT d FROM ContractEntity d where d.title= :title and d.userEntityId=:userid"),

 // Get all contracts with this parameters Title, Status and User
@NamedQuery(name = "getUserContractsByTitleStatus",
 query = "SELECT a FROM ContractEntity a WHERE a.title= :title AND a.userEntityId= :userEntityId AND a.statusContract= :statusContract"),    

// Get all contracts with this parameters Status and User
@NamedQuery(name = "getUserContractsByStatus",
 query = "SELECT a FROM ContractEntity a WHERE a.userEntityId= :userEntityId AND a.statusContract= :statusContract ORDER BY a.title ASC"),

// Get all contracts with a specific status and date
@NamedQuery(name = "getUserContractsByStatusDate",
 query = "SELECT a FROM ContractEntity a WHERE a.userEntityId= :userEntityId AND a.statusContract= :statusContract AND a.dateIssued like :dateIssued  ORDER BY a.dateIssued ASC"),

 // Get specific Contract ID ( Eve Id ) from Eve Server
@NamedQuery(name = "getContractsId", 
 query = "SELECT c FROM ContractEntity c WHERE c.contractID = :id"),

// Get all contracts with this parameters Status "Expired" and DateExpired
@NamedQuery(name = "getContractDateExpired", 
 query = "SELECT c FROM ContractEntity c WHERE c.statusContract='expired' AND c.dateExpired = :dateExpired"),

// get all contracts ( to all users ) with parameter Status
@NamedQuery(name = "getContractsByStatus", 
 query = "SELECT c FROM ContractEntity c WHERE c.statusContract= :statusContract"),

// Get all contracts avoid contracts with the same name
@NamedQuery(name = "getContractsTitleAvoidDuplicate",
  query = "SELECT a.title FROM ContractEntity a GROUP BY a.title"),

// Da modificare tutti e convertire in selezione per utente e contratto
// Get all contract from DB
@NamedQuery(name = "getContracts", query = "SELECT c FROM ContractEntity c ORDER BY C.title ASC"),


//// Get all contract with a specific status and date
//@NamedQuery(name = "getUserContractsByStatusDate",
// query = "SELECT a FROM ContractEntity a WHERE a.userEntityId= :userEntityId AND a.statusContract= :statusContract AND a.dateIssued :dateIssued  ORDER BY a.dateIssued ASC"),

// // Get select all ship sold ( completed )contracts by title
//@NamedQuery( name = "getContractStatusByTitle",  
// query = "SELECT c FROM ContractEntity c WHERE c.statusContract= :status AND c.title = :title" ),

// // Get a title of all contracts
//@NamedQuery( name = "getContractsTitle", 
//  query = "SELECT c FROM ContractEntity c WHERE c.title =:title ORDER BY C.title ASC"),

// // Get Contracts Completed All Date
//@NamedQuery( name= "getContractsCompletedAllDate",
//    query = "SELECT c FROM ContractEntity c WHERE c.statusContract='completed' AND c.title= :title ORDER BY c.dateIssued DESC" ),

// // Get select all ship in outstading contracts by title DBG controllare se serve
//@NamedQuery(name = "getContractOutstandingByTitle", 
// query = "SELECT c FROM ContractEntity c WHERE c.statusContract= 'outstanding' AND c.title = :title"),

// // Get select all ship sold ( completed ) contracts by date
//@NamedQuery(name = "getContracstCompletedSelectDate",
// //query = "SELECT c FROM ContractEntity c WHERE c.statusContract= 'completed' AND c.title= :title AND c.dateCompleted= :dateCompleted" ),
// query = "SELECT c FROM ContractEntity c WHERE c.statusContract= 'completed' AND c.title= :title AND c.dateCompleted= :dateCompleted" ),

// // Get all status of all contracts
//@NamedQuery( name = "getContractsStatus", 
//  query = "SELECT c FROM ContractEntity c WHERE c.statusContract = :statusContract ORDER BY c.dateCompleted ASC"),
})
public class ContractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // DBG
    private transient int tempQualcosaDBG = 0;

    private String contractID = null; // < -- questo
    private String statusContract = null; // < -- questo
    private String title = null; // < -- questo
    private String priceSell = null; // < -- questo
    private String priceBuy = null;
    private Double profitPercent;
    private int profitPerShip;
    private int profitTot;
    private Long userEntityId;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateCompleted = null;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateIssued = null;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateExpired = null;

    public Long getUserEntityId() {
        return userEntityId;
    }

    public void setUserEntityId(Long userEntityId) {
        this.userEntityId = userEntityId;
    }
    
    
    
    public int getTempQualcosaDBG() {
        return tempQualcosaDBG;
    }

    public void setTempQualcosaDBG(int tempQualcosaDBG) {
        this.tempQualcosaDBG = tempQualcosaDBG;
    }


    
    /**
     * Get Date Expired with Formatted
     * @return Date
     */
    public Date getDateExpiredFormatted() {
        return dateExpired;
    }

    /**
     * Set Date Expired
     * @param String dateExpired 
     */
    public void setDateExpired(String dateExpired) {
        this.dateExpired = this.parseStringToDate(dateExpired) ;
    }
    
    /**
     * Get Date Expired Unformatted
     * @return Date
     */
    public Date getDateExpiredUnformatted() {
        return dateExpired;
    }     
    
    /**
     * Get Profit Percent
     * @return Double
     */
    public Double getProfitPercent() {
        return profitPercent;
    }

    /**
     * Set Profit Percent
     * @param Double profitPercent 
     */
    public void setProfitPercent(Double profitPercent) {
        this.profitPercent = profitPercent;
    }

    /**
     * Get Profit
     * @return Integer
     */
    public int getProfitPerShip() {
        return profitPerShip;
    }

    /**
     * Set Profit
     * @param Integer profitPerShip 
     */
    public void setProfitPerShip(int profitPerShip) {
        this.profitPerShip = profitPerShip;
    }

    /**
     * Get Profit per ship without formatting
     * see getProfitPerShip
     * @return Integer
     */
    public int getProfitPerShipUnformatted() {
        return profitPerShip;
    }    
    
    /**
     * Get ProfitTot
     * @return Integer
     */
    public int getProfitTot() {
        return profitTot;
    }

    /** 
     * Set ProfitTot
     * @param Integer profitTot 
     */
    public void setProfitTot(int profitTot) {
        this.profitTot = profitTot;
    }

    /**
     * Get Date Issued with formatted
     * @return String
     */
    public String getDateIssuedFormatted() {
        return parseDateToString(dateIssued);
    }

    /**
     * Set Date Issued
     * @param dateIssued 
     */
    public void setDateIssued( String dateIssuedStr) {
        if (!dateIssuedStr.equals(""))
            this.dateIssued = parseStringToDate(dateIssuedStr);
    }    

    /**
     * Get Date Issued without formatting
     * see getDateIssuedFormatted
     * @return Date
     */
    public Date getDateIssuedUnformatted() {
        return dateIssued;
    }
    
    /**
     * Set Date Completed
     * @param String dateCompleted 
     */
    public void setDateCompleted( String dateCompletedStr) {
        if (!dateCompletedStr.equals(""))
            this.dateCompleted = parseStringToDate(dateCompletedStr);
    }    

    /**
     * Get Date Completed with formatted
     * @return Date
     */
    public String getDateCompletedFormatted() {
        return parseDateToString(dateCompleted) ;
     }    

    /**
     * Get Date without formmatting
 see getDateCompletedFormatted
     * @return Date
     */
    public Date getDateCompletedUnformatted() {
        return dateCompleted;
    }
    
    /**
     * Get Price Sell
     * @return String
     */
    public String getPriceSell() {
        return priceSell;
    }

    /**
     * Set Price Sell
     * @param String priceSell 
     */
    public void setPriceSell(String priceSell) {
        // DBG
        this.priceSell = priceSell.substring(0, priceSell.length()-3);
    }

    /**
     * Get Price Buy
     * @return String String
     */
    public String getPriceBuy() {
        return decimalSeparatorUnit(priceBuy); 
    }

    /**
     * Set Price Buy
     * @param String priceBuy 
     */
    public void setPriceBuy(String priceBuy) {
        this.priceBuy = priceBuy;
    }
    
    /**
     * Get Price Buy Order without formatting
     * see getPriceBuy
     * @return String
     */
    public String getPriceBuyUnformatted() {
        return priceBuy;
    }      
    
    /**
     * Get Contract ID
     * @return String
     */
    public String getContractID() {
        return contractID;
    }

    /**
     * Set Contract ID
     * @param String contractID 
     */
    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    /**
     * Get Status
     * @return String
     */
    public String getStatusContract() {
        return statusContract;
    }

    /**
     * Set Status
     * @param String status 
     */
    public void setStatusContract(String status) {
        this.statusContract = status;
    }

    /**
     * Get Title
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set Title
     * @param String title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Convert string in Date
     * @param dateString
     * @return 
     */
    private Date parseStringToDate( String dateString){
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parserDate = formater.parse(dateString);
            return parserDate;           

        } catch (Exception e) {
            System.out.println("ContractEntity parseStringDate");
            e.printStackTrace();
            return null;
        }
    }       
    
    /**
     * Convert Date from type Date to String
     * @param Date date
     * @return String
     */
    private String parseDateToString(Date date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(date);
            return dateString;
        } catch (Exception e) {
            return null;
        }
    }    
    
    /**
     * trasform number from 200000000 to 200.000.000
     * @param value
     * @return String
     */
    private String decimalSeparatorUnit(String value){
        try {
            NumberFormat valueNF = NumberFormat.getNumberInstance(Locale.ITALY);
            valueNF.setGroupingUsed(true);
            String numberFormatted  = valueNF.format(Double.parseDouble(value));
            return numberFormatted;            
        } catch (Exception e) {
            return null;
        }
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContractEntity)) {
            return false;
        }
        ContractEntity other = (ContractEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alliancecontractmanager.db.entities.ContractEntity[ id=" + id + " ]";
    }
    
}
