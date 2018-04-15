/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.files;

import py.edu.columbia.tcc.ejb.jpa.content.AbstractFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author tokio
 */
@Stateless
public class VoucherFromFileFacade extends AbstractFacade<VoucherFromFileFacade> {
    public VoucherFromFileFacade() {
        super(VoucherFromFileFacade.class);
    }
    
    public String createVoucherFromFile(String filePath, String company, String typeinvoicegroupcode, String obs) throws GDMEJBException{
        try{
            StringBuilder sb = new StringBuilder();

            sb.append("SELECT billing.fn_bill_addvoucher_from_file('");
            sb.append(company);
            sb.append("','");
            sb.append(filePath);
            sb.append("','");
            sb.append(typeinvoicegroupcode);
            sb.append("','");
            sb.append(obs);
            sb.append("')");

            Query q = getEntityManager().createNativeQuery(sb.toString());

            return q.getSingleResult().toString();
        } catch(Exception e){
            throw new GDMEJBException("Error al crear pagos desde archivo."+e.getMessage());
        }
    }
}
    
