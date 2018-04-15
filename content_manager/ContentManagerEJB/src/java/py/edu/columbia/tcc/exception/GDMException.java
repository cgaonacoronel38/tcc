/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.exception;

/**
 *
 * @author tid
 */
public class GDMException extends Exception {
    
    public GDMException(String msg) {
        super(msg);
    }
    
    public GDMException(String msg, Throwable t) {
        super(msg, t);
    }
}
