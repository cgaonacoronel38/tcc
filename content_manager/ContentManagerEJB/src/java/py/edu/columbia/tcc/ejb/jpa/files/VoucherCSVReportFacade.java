/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.files;

import py.edu.columbia.tcc.ejb.jpa.content.AbstractFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author tokio
 */
@Stateless
public class VoucherCSVReportFacade extends AbstractFacade<VoucherCSVReportFacade> {
    public VoucherCSVReportFacade() {
        super(VoucherCSVReportFacade.class);
    }
    
    // recibos por cuenta, con datos de red de pagos (registros)
    public List<String> receiptsPerAccountH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  coalesce(v.refcustomer1,'')||'|'||   ");  
            sql.append("    v.idvoucher ||'|'||");
            sql.append("    coalesce(v.refinvoicenumber,'') ||'|'||");  
            sql.append("    coalesce(to_char(v.datemov,'dd/mm/yyyy'),'') ||'|'||");
            sql.append("    coalesce((select description from billing.typevoucher where idtypevoucher = v.idtypevoucher and idcompany = v.idcompany),'') ||'|'||");  
            sql.append("    coalesce(v.totalvoucher,0) ||'|'||");
            sql.append("    (select sum(totalvoucher) from billing.voucher where refcustomer1 = v.refcustomer1 and idcompany = v.idcompany AND active is true AND datemov between ?1 and ?2) ||'|'||");  
            sql.append("    coalesce((select idnet ||'|'|| coalesce(descbox,'') ||'|'||");
            sql.append("    coalesce(ticketnumber,'') ||'|'||");  
            sql.append("    coalesce(obs,'') from billing.vouchernet where idvoucher = v.idvoucher and idcompany = v.idcompany),'|||')");
            sql.append("    AS \"BILLTO|NRO_RECIBO|NRO_FACTURA|FECHA|MOVIMIENTO|IMPORTE|TOTAL_CUENTA|RED|CAJA|TICKET|OBS\"");  
            sql.append(" FROM 	billing.voucher v");
            sql.append(" WHERE 	v.ACTIVE IS TRUE ");  
            sql.append(" and 	v.idcompany = ?3 ");
            sql.append(" and 	v.datemov between ?4 and ?5 ");
            sql.append(" AND (?6 is null or v.refcustomer1 = ?7) ");
            sql.append(" AND (?8 is null or v.refdocnumber = ?9) ");
            sql.append(" order by v.refcustomer1");
            
            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, fromDate);
            q.setParameter(2, toDate);
            q.setParameter(3, idcompany);
            q.setParameter(4, fromDate);
            q.setParameter(5, toDate);
            q.setParameter(6, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(7, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(8, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            q.setParameter(9, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            
            return q.getResultList();
        } catch(Exception e){
            throw new GDMEJBException("Error al generar recibos por cuenta, con datos de red de pagos (registros); "+e.getLocalizedMessage());
        } 
    }
    
    // recibos por cuenta, con datos de red de pagos (totales)
    public String receiptsPerAccountF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  'TOTALES' ||'|'||   ");  
            sql.append("    '' ||'|'||");
            sql.append("    '' ||'|'||");  
            sql.append("    '' ||'|'||");
            sql.append("    '' ||'|'||");  
            sql.append("    '' ||'|'||");
            sql.append("    sum(v.totalvoucher) ||'|'||");  
            sql.append("    '' ||'|'||");
            sql.append("    '' ||'|'||");  
            sql.append("    ''");  
            sql.append(" FROM 	billing.voucher v");
            sql.append(" WHERE 	v.ACTIVE IS TRUE ");
            sql.append(" and 	v.idcompany = ?1 ");
            sql.append(" and 	v.datemov between ?2 and ?3 ");
            sql.append(" AND (?4 is null or v.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or v.refdocnumber = ?7) ");
            
            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, idcompany);
            q.setParameter(2, fromDate);
            q.setParameter(3, toDate);
            q.setParameter(4, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(5, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(6, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            q.setParameter(7, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            
            return (String)q.getSingleResult();
        } catch(Exception e){
            throw new GDMEJBException("Error al generar reporte recibos por cuenta, con datos de red de pagos (totales); "+e.getLocalizedMessage());
        } 
    }
    
    // recibos por fecha, con datos de red de pagos (registros)
    public List<String> receiptsPerDateH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT to_char(v.datemov,'dd/mm/yyyy') ||'|'||");  
            sql.append("    coalesce(v.refcustomer1,'')||'|'||");
            sql.append("    v.idvoucher ||'|'||");
            sql.append("    coalesce(v.refinvoicenumber,'') ||'|'||");  
            sql.append("    coalesce((select description from billing.typevoucher where idtypevoucher = v.idtypevoucher and idcompany = v.idcompany),'') ||'|'||");  
            sql.append("    coalesce(v.totalvoucher,0) ||'|'||");
            sql.append("    coalesce((select idnet ||'|'|| coalesce(descbox,'') ||'|'||");
            sql.append("    coalesce(ticketnumber,'') ||'|'||");  
            sql.append("    coalesce(obs,'') from billing.vouchernet where idvoucher = v.idvoucher and idcompany = v.idcompany),'|||')");
            sql.append("    AS \"FECHA|BILLTO|NRO_RECIBO|NRO_FACTURA|MOVIMIENTO|IMPORTE|RED|CAJA|TICKET|OBS\"");  
            sql.append(" FROM 	billing.voucher v");
            sql.append(" WHERE 	v.ACTIVE IS TRUE ");  
            sql.append(" and 	v.idcompany = ?1 ");
            sql.append(" and 	v.datemov between ?2 and ?3 ");
            sql.append(" AND (?4 is null or v.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or v.refdocnumber = ?7) ");
            sql.append(" order by 1");
            
            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, idcompany);
            q.setParameter(2, fromDate);
            q.setParameter(3, toDate);
            q.setParameter(4, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(5, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(6, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            q.setParameter(7, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            
            return q.getResultList();
        } catch(Exception e){
            throw new GDMEJBException("Error al generar recibos por fecha, con datos de red de pagos (registros); "+e.getLocalizedMessage());
        } 
    }
    
    // recibos por fecha, con datos de red de pagos (totales)
    public String receiptsPerDateF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT  'TOTALES' ||'|'||   ");  
            sql.append("    '' ||'|'||");
            sql.append("    '' ||'|'||");  
            sql.append("    '' ||'|'||");
            sql.append("    '' ||'|'||");  
            sql.append("    sum(v.totalvoucher) ||'|'||");  
            sql.append("    '' ||'|'||");
            sql.append("    '' ||'|'||");  
            sql.append("    ''");  
            sql.append(" FROM 	billing.voucher v");
            sql.append(" WHERE 	v.ACTIVE IS TRUE ");
            sql.append(" and 	v.idcompany = ?1 ");
            sql.append(" and 	v.datemov between ?2 and ?3 ");
            sql.append(" AND (?4 is null or v.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or v.refdocnumber = ?7) ");
            
            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, idcompany);
            q.setParameter(2, fromDate);
            q.setParameter(3, toDate);
            q.setParameter(4, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(5, refcustomer1 == null || refcustomer1.trim().isEmpty() ? null : refcustomer1.trim());
            q.setParameter(6, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            q.setParameter(7, refdocnumber == null || refdocnumber.trim().isEmpty() ? null : refdocnumber.trim());
            
            return (String)q.getSingleResult();
        } catch(Exception e){
            throw new GDMEJBException("Error al generar reporte recibos por fecha, con datos de red de pagos (totales); "+e.getLocalizedMessage());
        } 
    }
}
    
