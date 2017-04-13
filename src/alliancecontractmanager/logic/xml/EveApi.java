/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.logic.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lele
 */
@XmlRootElement(name = "eveapi")
public class EveApi {
    private Result result;

    @XmlElement(name = "result")
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    
}
