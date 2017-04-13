/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.exception;

/**
 *
 * @author lele
 */
public class VerificationCodeWrongException extends Exception{

    public VerificationCodeWrongException() {
        super("Verification Code is wrong");
    }
    
}
