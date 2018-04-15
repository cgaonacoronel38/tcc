/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.exception;

import java.util.List;

/**
 *
 * @author tid
 */
public class GDMEJBException extends Exception {
    
    private List<String> validatorList;    
    
    public GDMEJBException(String msg) {
        super(msg);
    }
    
    public GDMEJBException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public GDMEJBException(String msg, List list) {
        super(msg);
        
        this.validatorList = list;
    }    
}
