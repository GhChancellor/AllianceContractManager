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

    public void update(UserApiEntity user) {
        System.out.println("");
        List<ContractXml> loadContractFromXML = this.loadContractFromXML(); //2.0  OK

        this.checkContracts(loadContractFromXML); //2.1 OK

        Date nowMinus15 = new Date(new Date().getTime() - (1000l * 60l * 15l));

        List<ContractXml> allContractsAfter = this.getAllContractsIssued(loadContractFromXML, nowMinus15); //2.2)  OK

        for (ContractXml contractXml : allContractsAfter) {
            this.setContractAsNEW(contractXml, user);            // 2.2.1)  OK
        }

        List<ContractXml> allContractsCompleted = this.getAllContractsCompleted(loadContractFromXML, nowMinus15); //7 2.3)  OK

        for (ContractXml cCompleted : allContractsCompleted) {
            this.fixClosedContract(cCompleted);  // OK
        }

    }

    /**
     * 2.3.1) i contratti che soddisfano questa condizione sono CONCLUSI e va
     * aggiornato il DB locale
     *
     * @param xmlContract
     */
    public void fixClosedContract(ContractXml xmlContract) {
        
        ContractEntity contractEntity = ManagerSQLMicrimsDB.getInstance().getContractByEveId(xmlContract.getContractID());
        if(contractEntity!=null){
            contractEntity.setStatusContract(StatusEnum.COMPLETED.getStatus());
            ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);
            System.out.println("Contractto marcato come COMPLETED: "+xmlContract.getContractID());
        }
        //TODO
//            if(xmlContract.getStatus())
//            xmlContract.setStatus(StatusEnum.COMPLETED.getStatus());

    }

    /**
     * 2.3) cercare tra i contratti XML quelli con dateCompleted > NOW-15minuti
     *
     * @param date
     * @return
     */
    public List<ContractXml> getAllContractsCompleted(List<ContractXml> allContracts, Date date) {
        List<ContractXml> result = new ArrayList<>();
        for (ContractXml ccc : allContracts) {
            Date dateIssued = parseStringToDate(ccc.getDateCompleted());
            if (dateIssued.after(date)) {
                result.add(ccc);
            }
        }
        return result;
    }

    /**
     * 2.2) cercare tra i contratti XML quelli con dateIssued > NOW-15minuti
     *
     * @param date
     * @return
     */
    public List<ContractXml> getAllContractsIssued(List<ContractXml> allContracts, Date date) {

        List<ContractXml> result = new ArrayList<>();

        for (ContractXml ccc : allContracts) {

            Date dateIssued = parseStringToDate(ccc.getDateIssued());

            if (dateIssued.after(date)) {
                result.add(ccc);
            }
        }
        return result;
    }

    /**
     * 2.2.1) i contratti che soddisfano questa condizione sono NUOVI e vanno
     * inseriti nel DB locale
     *
     * @param contract
     */
    public void setContractAsNEW(ContractXml contractXml, UserApiEntity user) {
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
     * 2) caricare la NOSTRA lista completa dei contratti
     *
     * @return
     */
    public List<ContractXml> loadContractFromXML() {
        List<ContractXml> result = null;
        ManagerContractsXml.getInstance().loadXML();
        result = ManagerContractsXml.getInstance().getContractXmls();
        return result;
    }

    public void fixExpiredContract(ContractEntity contractEntity) {
        System.out.println("contratto scaduto");
        contractEntity.setContractID("");
        contractEntity.setStatusContract(StatusEnum.EXPIRED.getStatus());
        ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);
    }

    /**
     * 2.1) cercare tra i contratti xml tutti i nostri contratti NON SCADUTI O
     * CANCELLATI
     *
     * @param xmlContracts
     */
    public void checkContracts(List<ContractXml> xmlContracts) {
        List<ContractEntity> dbContracts = contractController.findContractEntityEntities();

        Date dateNow = new Date();
        List<ContractEntity> deletedContract = new ArrayList<>(); // qui ci salvo tutti i contratti scaduti che vado trovando

        for (ContractEntity dbContract : dbContracts) {
            if (dbContract.getDateExpiredUnformatted().after(dateNow)) {
                fixExpiredContract(dbContract); //sono i contratti sul DB che vengono trovati SCADUTI, perché hanno data di scadenza > oggi
                continue;
            }
            
            boolean trovato = false;

            // ??? su quale base decidi che è cancellato?           
            for (ContractXml xmlContract : xmlContracts) {
                if (dbContract.getContractID().equals(xmlContract.getContractID())) {
                    trovato = true;
                    break;
                }
            }
            
            if (!trovato) {
                deletedContract.add(dbContract);
            }
            
            this.setContractAsCanceled(dbContract, true);
        }

        //  2.1.1)se un NOSTRO contratto non viene trovato -> quel contratto è stato cancellato
        // 2.1.1.1) Segno il nostro contratto LOCALE come CANCELLATO
    }

    /**
     * 2.1.1.1) Segno il nostro contratto LOCALE come CANCELLATO
     *
     * @param contract
     * @param canceled
     */
    public void setContractAsCanceled(ContractEntity contract, boolean canceled) {
        if (canceled) {
            //contract.setContractID("");
            contract.setStatusContract(StatusEnum.DELETED.getStatus());
            ManagerSQLMicrimsDB.getInstance().updateContract(contract);
        }
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
