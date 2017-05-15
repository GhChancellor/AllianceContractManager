/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic;

import alliancecontractmanager.db.controllers.ContractEntityJpaController;
import alliancecontractmanager.db.entities.ContractEntity;
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

    public void update() {
        List<ContractXml> loadContractFromXML = this.loadContractFromXML(); //2.0
        this.checkContracts(loadContractFromXML); //2.1
        Date nowMinus15 = new Date(new Date().getTime() - (1000l * 60l * 15l));
        List<ContractXml> allContractsAfter = this.getAllContractsIssued(loadContractFromXML, nowMinus15); //2.2) 
        for (ContractXml contractXml : allContractsAfter) { 
            this.setContractAsNEW(contractXml);            // 2.2.1) 
        }
        List<ContractXml> allContractsCompleted = this.getAllContractsCompleted(loadContractFromXML, nowMinus15); //7 2.3)
        for (ContractXml cCompleted : allContractsCompleted) {
            this.fixClosedContract(cCompleted);
        }

    }

    /**
     * 2.3.1) i contratti che soddisfano questa condizione sono CONCLUSI e va
     * aggiornato il DB locale
     *
     * @param xmlContracts
     */
    public void fixClosedContract(ContractXml xmlContracts) {
            //TODO
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
    public void setContractAsNEW(ContractXml contract) {
        //li aggiorna nel db
        //TODO
    }

    /**
     * 2) caricare la NOSTRA lista completa dei contratti
     *
     * @return
     */
    public List<ContractXml> loadContractFromXML() {
        List<ContractXml> result = null;
        //TODO
        return result;
    }

    public void fixExpiredContract(ContractEntity contractEntity) {
        System.out.println("contratto scaduto");
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
            if (dbContract.getDateExpiredFormatted().after(dateNow)) {
                fixExpiredContract(dbContract);
                continue;
            }
            boolean trovato = false;
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
        //TODO
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
