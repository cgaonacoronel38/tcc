/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.content;

import javax.ejb.Stateless;
import py.edu.columbia.tcc.model.chartData.TypeTime;

/**
 *
 * @author tid
 */
@Stateless
public class TypeTimeFacade extends AbstractFacade<TypeTime> {
    
    public TypeTimeFacade(){
        super(TypeTime.class);
    }
}