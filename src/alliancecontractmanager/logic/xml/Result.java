/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lele
 */
@XmlRootElement(name = "result")
public class Result {
    List < ContractXml > contractXmls = new ArrayList<>();

    @XmlElementWrapper(name = "rowset")
    @XmlElement(name = "row")
    public List<ContractXml> getContractXmls() {
        return contractXmls;
    }

    public void setContractXmls(List<ContractXml> contractXmls) {
        this.contractXmls = contractXmls;
    }
    
    
}
