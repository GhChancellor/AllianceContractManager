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
public class KeyIdEmptyException extends Exception{

    public KeyIdEmptyException() {
        super("KeyId is invalid");
    }
    
}
