/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.UpdateContractProva;

import alliancecontractmanager.db.controllers.ContractEntityJpaController;
import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.logic.enumname.StatusEnum;
import alliancecontractmanager.logic.manager.ManagerContractsXml;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import alliancecontractmanager.logic.xml.ContractXml;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Luca Coraci <luca.coraci@istc.cnr.it>
 */
public class PseudoAlgoritmo {

    private boolean firstUpdate = true;
    private ContractEntityJpaController contractController = new ContractEntityJpaController(Persistence.createEntityManagerFactory("AllianceContractManagerPU"));

    /*
      x  1) Leggiamo il file XML
      x  2) caricare la NOSTRA lista completa dei contratti 
      x      2.1) cercare tra i contratti xml tutti i nostri contratti NON SCADUTI O CANCELLATI
      x          2.1.1)se un NOSTRO contratto non viene trovato -> quel contratto è stato cancellato
      x              2.1.1.1) Segno il nostro contratto LOCALE come CANCELLATO
      x      2.2) cercare tra i contratti XML quelli con dateIssued > NOW-15minuti
      x          2.2.1) i contratti che soddisfano questa condizione sono NUOVI e vanno inseriti nel DB locale
      x      2.3) cercare tra i contratti XML quelli con dateCompleted > NOW-15minuti
      x          2.3.1) i contratti che soddisfano questa condizione sono CONCLUSI e va aggiornato il DB locale
                    
     */
    public static void main(String[] args) {

    }

    public void updateContract(UserApiEntity user) {
        System.out.println("");

        Date nowMinus15 = new Date(new Date().getTime() - (1000l * 60l * 15l));

        List<ContractXml> loadContractFromXML = this.loadContractFromXML(user); //2.0  OK

        List<ContractXml> allContractsCompleted = this.getAllContractsCompleted(loadContractFromXML, nowMinus15,firstUpdate); //7 2.3)  OK

        for (ContractXml contractCompleted : allContractsCompleted) {
            this.fixClosedContract(contractCompleted);  // OK
        }

        this.checkContracts(loadContractFromXML, user.getId()); //2.1 OK

        List<ContractXml> allNewContracts = this.getAllNewContracts(loadContractFromXML, nowMinus15); //2.2)  OK

        for (ContractXml contractXml : allNewContracts) {
            this.setContractAsNEW(user, contractXml);            // 2.2.1)  OK
        }

    }

    /**
     * Get All Contracts Completed
     * 2.3) cercare tra i contratti XML quelli con dateCompleted > NOW-15minuti
     * @param List<ContractXml> allContracts
     * @param Date date
     * @param boolean firstUpdate
     * @return List<ContractXml>
     */
    public List<ContractXml> getAllContractsCompleted(List<ContractXml> allContracts, Date date, boolean firstUpdate) {
        
        List<ContractXml> contractXmls = new ArrayList<>();
        
        for (ContractXml contractXml : allContracts) {
            if (contractXml.getStatus().equals(StatusEnum.COMPLETED.getStatus())) {
                System.out.println("sto valutando: " + contractXml.getTitle() + " ID -> " + contractXml.getContractID());
                
                if (!firstUpdate) {
                    Date dateIssued = parseStringToDate(contractXml.getDateCompleted());
                    if (dateIssued.after(date)) {
                        contractXmls.add(contractXml);
                    }
                }else{
                    contractXmls.add(contractXml);
                }
            }
        }
        return contractXmls;
    }

    /**
     * Get All New Contracts
     * 2.2) cercare tra i contratti XML quelli con dateIssued > NOW-15minuti
     * @param List<ContractXml> allContracts
     * @param Date date
     * @return List<ContractXml>
     */
    public List<ContractXml> getAllNewContracts(List<ContractXml> allContracts, Date date) {

        List<ContractXml> contractXmls = new ArrayList<>();

        for (ContractXml contractXml : allContracts) {

            Date dateIssued = parseStringToDate(contractXml.getDateIssued());

            if (dateIssued.after(date)) {
                contractXmls.add(contractXml);
            }
        }
        return contractXmls;
    }

    /**
     * Set Contract As NEW and put in DB
     * 2.2.1) i contratti che soddisfano questa condizione sono NUOVI e vanno
     * inseriti nel DB locale
     * @param ContractXml contractXml
     * @param UserApiEntity user 
     */
    public void setContractAsNEW(UserApiEntity user, ContractXml contractXml) {
        //li aggiorna nel db
        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setDateIssued(contractXml.getDateIssued());
        contractEntity.setContractID(contractXml.getContractID());
        contractEntity.setPriceSell(contractXml.getPrice());
        contractEntity.setPriceBuy("0");
        contractEntity.setStatusContract(contractXml.getStatus());
        contractEntity.setTitle(contractXml.getTitle());
        contractEntity.setDateIssued(contractXml.getDateIssued());
        contractEntity.setDateExpired(contractXml.getDateExpired());
        if (!contractXml.getDateCompleted().equals("")) {
            contractEntity.setDateCompleted(contractXml.getDateCompleted());
        }

        contractEntity.setUserEntityId(user.getId());

        ManagerSQLMicrimsDB.getInstance().createContract(contractEntity);

        user.addContractEntitys(contractEntity);
        ManagerSQLMicrimsDB.getInstance().updateUser(user);
    }

    /**
     * UNUSED
     * Load Contract From XML
     * @return List<ContractXml>
     */
    public List<ContractXml> loadContractFromXML() {
        List<ContractXml> contractXmls = null;
        ManagerContractsXml.getInstance().loadXML();
        contractXmls = ManagerContractsXml.getInstance().getContractXmls();
        return contractXmls;
    }

    /**
     * Load all Contractd From XML by user
     * 2) caricare la NOSTRA lista completa dei contratti
     * @param UserApiEntity user
     * @return List<ContractXml>
     */
    public List<ContractXml> loadContractFromXML(UserApiEntity user) {
        List<ContractXml> contractXmls = new ArrayList<>();
    
        ManagerContractsXml.getInstance().loadXML(user);
        contractXmls = ManagerContractsXml.getInstance().getContractXmls();
        return contractXmls;
    }

    /**
     * Fix Expired Contract
     * @param ContractEntity contractEntity 
     */
    public void fixExpiredContract(ContractEntity contractEntity) {
        System.out.println("contratto scaduto");
        
        contractEntity.setContractID("");
        contractEntity.setStatusContract(StatusEnum.EXPIRED.getStatus());
        ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);
    }

    /**
     * Fix Closed Contract
     * 2.3.1) i contratti che soddisfano questa condizione sono CONCLUSI e va
     * aggiornato il DB locale
     * @param ContractXml xmlContract
     */
    public void fixClosedContract(ContractXml contractXml) {

        ContractEntity contractEntity = ManagerSQLMicrimsDB.getInstance().getContractByEveId(contractXml.getContractID());
        if (contractEntity != null) {
            contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
            ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);
            System.out.println("Contractto marcato come COMPLETED: " + contractXml.getContractID());
        }
        //TODO
//            if(xmlContract.getStatus())
//            xmlContract.setStatus(StatusEnum.COMPLETED.getStatus());

    }    

    /**
     * Fix Deleted Contract
     * 2.1.1.1) Segno il nostro contratto LOCALE come CANCELLATO
     * @param ContractEntity contract 
     */
    public void fixDeletedContract(ContractEntity contractEntity) {
//        if (canceled) {
        //contract.setContractID("");
        contractEntity.setStatusContract(StatusEnum.DELETED.getStatus());
        ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);
//        }
    }
    
    /**
     * 2.1) cercare tra i contratti xml tutti i nostri contratti NON SCADUTI O
     * CANCELLATI
     * 
     * @param List<ContractXml> xmlContracts xmlContracts
     * @param Long userId 
     */
    public void checkContracts(List<ContractXml> contractXlms, Long userId) {

        //List<ContractEntity> dbContracts = contractController.findContractEntityEntities();
        List<ContractEntity> contractEntities = ManagerSQLMicrimsDB.getInstance().getContractsByUser(userId);
        
        Date dateNow = new Date();
        List<ContractEntity> deletedContracts = new ArrayList<>(); // qui ci salvo tutti i contratti scaduti che vado trovando

        for (ContractEntity contractEntity : contractEntities) {
            // Contract is completed?
            if (contractEntity.getStatusContract().equals(StatusEnum.COMPLETED.getStatus())) {
                continue;
            }
            
            // Contract is expired?
            if (contractEntity.getDateExpiredUnformatted().before(dateNow)) {
                fixExpiredContract(contractEntity); //sono i contratti sul DB che vengono trovati SCADUTI, perché hanno data di scadenza > oggi
                continue;
            }

            boolean foundIt = false;

            // non sono sicuro delle meccaniche sulla gestione dei contratti deleted
            for (ContractXml contractXml : contractXlms) {
                if (contractEntity.getContractID().equals(contractXml.getContractID())) {
                    foundIt = true;
                    break;
                }
            }

            if (!foundIt) {
                deletedContracts.add(contractEntity);
            }
        }

        for (ContractEntity contractEntity : deletedContracts) {
            this.fixDeletedContract(contractEntity);
        }

        //  2.1.1)se un NOSTRO contratto non viene trovato -> quel contratto è stato cancellato
        // 2.1.1.1) Segno il nostro contratto LOCALE come CANCELLATO
    }

    private Date parseStringToDate(String dateString) {
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

}
