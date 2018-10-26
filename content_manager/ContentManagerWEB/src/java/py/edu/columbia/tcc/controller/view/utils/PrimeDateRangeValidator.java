/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.controller.view.utils;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.component.calendar.Calendar;

/**
 *
 * @author tokio
 */
@FacesValidator("primeDateRangeValidator")
public class PrimeDateRangeValidator implements Validator {
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
         
        //Leave the null handling of startDate to required="true"
        Object startDateValue = component.getAttributes().get("startDate");
//        if (startDateValue==null) {
//            return;
//        }
         
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)value; 
        
        if(startDate != null && endDate != null){
            System.out.println("Valores de fechas no nulas");
            System.out.println("Start date: "+startDate);
            System.out.println("End date: "+endDate);
        } else {
            if(startDate == null){
                System.err.println("Star  date nulo");
            } else {
                System.err.println("end date nulo");
            }
        }
        
        if (endDate.before(startDate)) {
            throw new ValidatorException(
                    FacesMessageUtil.newBundledFacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha no valida", "verificar inicio fin", "Desde fecha", startDate));
        }
    }
    
}
