/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.manager;

import alliancecontractmanager.db.entities.UserApiEntity;
import alliancecontractmanager.logic.xml.ContractXml;
import alliancecontractmanager.logic.xml.EveApi;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXB;

/**
 *
 * @author lele
 */
public class ManagerContractXml {
    private static ManagerContractXml instance = null;
    private String urlStr = null;
    private URL url = null;
    private EveApi eveApi = null;
    private List < ContractXml > contractXmls = new ArrayList<>();
    
    /**
     * Singleton
     * @return ManagerContractXml
     */
    public static ManagerContractXml getInstance(){
        if ( instance == null ){
            instance = new ManagerContractXml();
        }
        return instance;
    }

    public void loadXMLDBG(UserApiEntity userApiEntity){
        try {
   
            setUrlStr ("./" + userApiEntity.getNameCharacter() +".xml");   
            File fileUrl = new File(urlStr);
            eveApi = JAXB.unmarshal(fileUrl, EveApi.class);
            
//            setEveApi(JAXB.unmarshal(fileUrl, EveApi.class));
            setContractXmls(eveApi.getResult().getContractXmls());
        } catch (Exception e) {
            System.out.println("ManagerContractXml ManagerContractXml");
            e.printStackTrace();
        }            
    }
    
    public void loadXML(){
        try {

//            Lele
//            setUrlStr("");
//            url = new URL(urlStr);
//            eveApi = JAXB.unmarshal(url, EveApi.class);
            
//       accesso via file     
            setUrlStr ("./contract.xml");            
            File fileUrl = new File(urlStr);
            eveApi = JAXB.unmarshal(fileUrl, EveApi.class);
            
//            setEveApi(JAXB.unmarshal(fileUrl, EveApi.class));
            setContractXmls(eveApi.getResult().getContractXmls());
        } catch (Exception e) {
            System.out.println("ManagerContractXml ManagerContractXml");
            e.printStackTrace();
        }        
    }

    public ManagerContractXml() {
//        try {
//
////            Lele
////            setUrlStr("");
////            url = new URL(urlStr);
////            eveApi = JAXB.unmarshal(url, EveApi.class);
//            
////       accesso via file     
//            setUrlStr ("./contract.xml");            
//            File fileUrl = new File(urlStr);
//            eveApi = JAXB.unmarshal(fileUrl, EveApi.class);
//            
////            setEveApi(JAXB.unmarshal(fileUrl, EveApi.class));
//            setContractXmls(eveApi.getResult().getContractXmls());
//        } catch (Exception e) {
//            System.out.println("ManagerContractXml ManagerContractXml");
//            e.printStackTrace();
//        }

    }

    /**
     * Get Contract List Xmls 
     * @return List<ContractXml>
     */
    public List<ContractXml> getContractXmls() {
        return this.contractXmls;
    }

    /**
     * Set Contact List Xmls
     * @param contractXmls 
     */
    public void setContractXmls(List<ContractXml> contractXmls) {
        this.contractXmls = contractXmls;
    }

//    public void addContractXmls( ContractXml contractXmls) {
//        this.contractXmls.add(contractXmls);
//    }
    
    
    /**
     * Get Url String
     * @return String
     */
    public String getUrlStr() {
        return urlStr;
    }

    /**
     * Set Url String
     * @param urlStr 
     */
    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    /**
     * Get Url
     * @return URL
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Set Url
     * @param url 
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Get Eve Api
     * @return EveApi
     */
    public EveApi getEveApi() {
        return eveApi;
    }

    /**
     * Set Eve Api
     * @param eveApi 
     */
    public void setEveApi(EveApi eveApi) {
        this.eveApi = eveApi;
    }
            
}


//    public static void main(String[] args) {
//        try {
//            String urlStr = "";
//            URL url = new URL(urlStr);
//            
//            EveApi eveApi = JAXB.unmarshal(url, EveApi.class);
//            List < ContractXml > contractXmls = eveApi.getResult().getContractXmls();
//            
//            for (ContractXml contractXml : contractXmls) {
//                System.out.println("ContractID "+ contractXml.getContractID() );
//                System.out.println("Title "+ contractXml.getTitle() );
//                System.out.println("DateCompleted "+ contractXml.getDateCompleted() );
//                System.out.println("Price "+ contractXml.getPrice() );
//                System.out.println("Status "+ contractXml.getStatus());
//                System.out.println("");
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }