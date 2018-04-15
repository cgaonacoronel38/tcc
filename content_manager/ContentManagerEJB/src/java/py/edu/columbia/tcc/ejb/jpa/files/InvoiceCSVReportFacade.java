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
public class InvoiceCSVReportFacade extends AbstractFacade<InvoiceCSVReportFacade> {
    public InvoiceCSVReportFacade() {
        super(InvoiceCSVReportFacade.class);
    }
    
    // lista facturas con saldos
    public List<String> invoiceWithBalanceH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT  coalesce(i.refcustomer1,'')||'|'||  ");  
            sql.append("	i.idinvoice ||'|'|| ");
            sql.append("	coalesce(i.invoicenumber,'') ||'|'|| ");
            sql.append("	coalesce(to_char(i.DATEEMI,'dd/mm/yyyy'),'') ||'|'|| ");
            sql.append("	(i.totinvoicegrav10 + i.totinvoicetax5 + i.totinvoiceexem) ||'|'||");  
            sql.append("	i.totexpense ||'|'||  ");
            sql.append("	(i.totpayinvoice + i.totpayexpense) ||'|'||");
            sql.append("	(i.totinvoicegrav10 + i.totinvoicetax5 + i.totinvoiceexem + i.totexpense) - (i.totpayinvoice + i.totpayexpense)");
            sql.append("	AS \"BILLTO|CODIGO|NRO_FACTURA|FECHA|IMPORTE|CARGOS|PAGADO|SALDO\"");  
            sql.append(" FROM 	BILLING.INVOICE i  ");
            sql.append(" WHERE 	i.ACTIVE IS TRUE ");
            sql.append(" AND ((i.totinvoicegrav10 + i.totinvoicetax5 + i.totinvoiceexem + i.totexpense) - (i.totpayinvoice + i.totpayexpense)) > 0 ");
            sql.append(" AND i.idcompany = ?1 ");
            sql.append(" AND i.dateemi BETWEEN ?2 AND ?3 ");
            sql.append(" AND (?4 is null or i.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or i.refdocnumber = ?7) ");
            sql.append(" order by i.refcustomer1 ");
            
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
            throw new GDMEJBException("Error al generar reporte - facturas con saldo; "+e.getLocalizedMessage());
        } 
    }
    
    
    public String invoiceWithBalanceF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
       try{
            StringBuilder sql = new StringBuilder();
            sql.append(" select 'TOTAL|||||||' || sum((totinvoicegrav10 + totinvoicetax5 + totinvoiceexem + totexpense) - (totpayinvoice + totpayexpense)) ");
            sql.append(" from BILLING.INVOICE ");
            sql.append(" where active is true");
            sql.append(" AND idcompany = ?1 ");
            sql.append(" AND dateemi BETWEEN ?2 AND ?3 ");
            sql.append(" AND (?4 is null or refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or refdocnumber = ?7) ");
            
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
            throw new GDMEJBException("Error al generar reporte - facturas con saldo; "+e.getLocalizedMessage());
        } 
    }
    
    // estado de cuentas (detallado por billto) - Cabecera, Registros
    public List<String> accountStateByBilltoH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT refcustomer1 ||'|'||  ");  
            sql.append("       coalesce(to_char(lastpayment,'DD/MM/YYYY'),'')  ||'|'|| ");
            sql.append("       coalesce(lastamount,0)  ||'|'||");
            sql.append("	(sum(totinvoicegrav10) + sum(totinvoicetax5) + sum(totinvoiceexem)) ||'|'|| ");  
             sql.append("       sum(totexpense)  ||'|'||");
            sql.append("	(sum(totpayinvoice) + sum(totpayexpense)) ||'|'||");  
            sql.append("	(sum(totinvoicegrav10) + sum(totinvoicetax5) + sum(totinvoiceexem) + sum(totexpense)) - (sum(totpayinvoice) + sum(totpayexpense))");
            sql.append("	as \"BILLTO|ULTIMA_FECHA|ULTIMO_PAGO|IMPORTE|CARGOS|PAGADO|SALDO\"");
            sql.append(" from billing.invoice ");  
            sql.append(" where idcompany = ?1 ");
            sql.append(" AND dateemi BETWEEN ?2 AND ?3 ");
            sql.append(" AND (?4 is null or refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or refdocnumber = ?7) ");
            sql.append(" AND active is true ");
            sql.append(" group by refcustomer1,lastpayment,lastamount ");
            sql.append(" ORDER BY refcustomer1 ");
            
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
            throw new GDMEJBException("Error al generar reporte - estado de cuentas detallado - billto; "+e.getLocalizedMessage());
        } 
    }
    
    // estado de cuentas (detallado por billto) - Totales
    public String accountStateByBilltoF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("select 'TOTALES' ||'|'||  ");  
            sql.append("       '' ||'|'|| ");
            sql.append("       SUM(lastamount) ||'|'|| ");
            sql.append("        (sum(totinvoicegrav10) + sum(totinvoicetax5) + sum(totinvoiceexem)) ||'|'|| ");
            sql.append("        sum(totexpense) ||'|'|| ");
            sql.append("	(sum(totpayinvoice) + sum(totpayexpense)) ||'|'|| ");  
            sql.append("	(sum(totinvoicegrav10) + sum(totinvoicetax5) + sum(totinvoiceexem) + sum(totexpense)) - (sum(totpayinvoice) + sum(totpayexpense)) ");
            sql.append("	as \"BILLTO|ULTIMA_FECHA|ULTIMO_PAGO|IMPORTE|CARGOS|PAGADO|SALDO\"");
            sql.append(" from billing.invoice ");  
            sql.append(" where idcompany = ?1 ");
            sql.append(" AND active is true ");
            sql.append(" AND dateemi BETWEEN ?2 AND ?3 ");
            sql.append(" AND (?4 is null or refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or refdocnumber = ?7) ");
            
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
            throw new GDMEJBException("Error al generar reporte - estado de cuentas detallado - billto; "+e.getLocalizedMessage());
        } 
    }
    
    // estado de cuentas por tipo de factura (registros)
    public List<String> accountStateByTypeInvoiceH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT (select t.description from billing.typeinvoicegroup t where t.idtypeinvoicegroup = i.idtypeinvoicegroup) ||'|'|| ");  
            sql.append("       count(i.idinvoice) ||'|'|| ");
            sql.append("       (sum(i.totinvoicegrav10) + sum(i.totinvoicetax5) + sum(i.totinvoiceexem)) ||'|'|| ");
            sql.append("       sum(i.totexpense) ||'|'||");
            sql.append("       (sum(i.totpayinvoice) + sum(i.totpayexpense)) ||'|'||");  
            sql.append("	(sum(i.totinvoicegrav10) + sum(i.totinvoicetax5) + sum(i.totinvoiceexem) + sum(i.totexpense)) - (sum(i.totpayinvoice) + sum(i.totpayexpense))");
            sql.append("	as \"BILLTO|CANTIDAD|IMPORTE|CARGOS|PAGADO|SALDO\"");
            sql.append(" from billing.invoice i ");  
            sql.append(" where i.idcompany = ?1 ");
            sql.append(" AND i.active is true ");
            sql.append(" AND i.dateemi BETWEEN ?2 AND ?3 ");
            sql.append(" AND (?4 is null or i.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or i.refdocnumber = ?7) ");
            sql.append(" group by i.idtypeinvoicegroup ");
            
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
            throw new GDMEJBException("Error al generar reporte - estado de cuentas detallado - billto; "+e.getLocalizedMessage());
        } 
    }
    
    // estado de cuentas por tipo de factura (totales)
    public String accountStateByTypeInvoiceF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT 'TOTALES' ||'|'|| ");  
            sql.append("       count(i.idinvoice) ||'|'|| ");
            sql.append("       (sum(i.totinvoicegrav10) + sum(i.totinvoicetax5) + sum(i.totinvoiceexem)) ||'|'|| ");
            sql.append("       sum(i.totexpense) ||'|'||");
            sql.append("       (sum(i.totpayinvoice) + sum(i.totpayexpense)) ||'|'||");  
            sql.append("	(sum(i.totinvoicegrav10) + sum(i.totinvoicetax5) + sum(i.totinvoiceexem) + sum(i.totexpense)) - (sum(i.totpayinvoice) + sum(i.totpayexpense))");
            sql.append("	as \"BILLTO|CANTIDAD|IMPORTE|CARGOS|PAGADO|SALDO\"");
            sql.append(" from billing.invoice i ");  
            sql.append(" where i.idcompany = ?1 ");
            sql.append(" AND i.dateemi BETWEEN ?2 AND ?3 ");
            sql.append(" AND (?4 is null or i.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or i.refdocnumber = ?7) ");
            sql.append(" AND i.active is true ");
            
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
            throw new GDMEJBException("Error al generar reporte - estado de cuentas detallado - billto; "+e.getLocalizedMessage());
        } 
    }
    
    // cargos por cuenta (registros)
    public List<String> expensePerAccountH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT i.refcustomer1 ||'|'|| ");  
            sql.append("       coalesce(i.invoicenumber,'')  ||'|'||");
            sql.append("       coalesce(to_char(i.dateemi,'DD/MM/YYYY'),'')  ||'|'|| ");
            sql.append("       i.totexpense ||'|'||");
            sql.append("       (select sum(totexpense) from billing.invoice where refcustomer1 like i.refcustomer1 and active is true and idcompany = i.idcompany and i.totexpense > 0 and dateemi BETWEEN ?1 AND ?2 )");
            sql.append("       as \"BILLTO|NRO_FACTURA|FECHA|CARGOS|CARGO_CUENTA\"");
            sql.append("       from billing.invoice i");
            sql.append(" where i.idcompany = ?3");
            sql.append(" AND i.dateemi BETWEEN ?4 AND ?5");
            sql.append(" AND (?6 is null or i.refcustomer1 = ?7) ");
            sql.append(" AND (?8 is null or i.refdocnumber = ?9) ");
            sql.append(" AND i.active is true");
            sql.append(" AND i.totexpense > 0");
            sql.append(" ORDER BY i.refcustomer1");
            
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
            throw new GDMEJBException("Error al generar reporte - cargos por cuenta; "+e.getLocalizedMessage());
        } 
    }
    
    // cargos por cuenta (totales)
    public String expensePerAccountF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT 'TOTAL' ||'|'|| ");  
            sql.append("       ''  ||'|'||");
            sql.append("       ''  ||'|'|| ");
            sql.append("       SUM(i.totexpense) ||'|'");
            sql.append("       as \"BILLTO|NRO_FACTURA|FECHA|CARGOS|CARGO_CUENTA\"");
            sql.append("       from billing.invoice i");
            sql.append(" where i.idcompany = ?1");
            sql.append(" AND i.dateemi BETWEEN ?2 AND ?3");
            sql.append(" AND (?4 is null or i.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or i.refdocnumber = ?7) ");
            sql.append(" AND i.active is true");
            sql.append(" AND i.totexpense > 0");
            
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
            throw new GDMEJBException("Error al generar reporte - cargos por cuenta; "+e.getLocalizedMessage());
        } 
    }
    
    // cargos por fecha (registros)
    public List<String> expensePerDateH(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT coalesce(to_char(i.dateemi,'DD/MM/YYYY'),'')  ||'|'|| ");  
            sql.append("       i.refcustomer1 ||'|'|| ");
            sql.append("       coalesce(i.invoicenumber,'')  ||'|'||");
            sql.append("       i.totexpense ||'|'||");
            sql.append("       (select sum(totexpense) from billing.invoice where  active is true and idcompany = i.idcompany and i.totexpense > 0 and to_char(dateemi,'DD/MM/YYYY') like to_char(i.dateemi,'DD/MM/YYYY') )");
            sql.append("       as \"FECHA|BILLTO|NRO_FACTURA|CARGOS|CARGO_CUENTA\"");
            sql.append("       from billing.invoice i");
            sql.append(" where i.idcompany = ?1");
            sql.append(" AND i.dateemi BETWEEN ?2 AND ?3");
            sql.append(" AND (?4 is null or i.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or i.refdocnumber = ?7) ");
            sql.append(" AND i.active is true");
            sql.append(" AND i.totexpense > 0");
            sql.append(" ORDER BY i.refcustomer1");
            
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
            throw new GDMEJBException("Error al generar reporte - cargos por fecha; "+e.getLocalizedMessage());
        } 
    }
    
    // cargos por fecha (totales)
    public String expensePerDateF(Integer idcompany, Date fromDate, Date toDate, String refcustomer1, String refdocnumber) throws GDMEJBException{
        
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT 'TOTAL' ||'|'|| ");  
            sql.append("       ''  ||'|'||");
            sql.append("       ''  ||'|'|| ");
            sql.append("       SUM(i.totexpense) ||'|'");
            sql.append("       as \"FECHA|BILLTO|NRO_FACTURA|CARGOS|CARGO_CUENTA\"");
            sql.append("       from billing.invoice i");
            sql.append(" where i.idcompany = ?1");
            sql.append(" AND i.dateemi BETWEEN ?2 AND ?3");
            sql.append(" AND (?4 is null or i.refcustomer1 = ?5) ");
            sql.append(" AND (?6 is null or i.refdocnumber = ?7) ");
            sql.append(" AND i.active is true");
            sql.append(" AND i.totexpense > 0");
            
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
            throw new GDMEJBException("Error al generar reporte - cargos por fecha; "+e.getLocalizedMessage());
        } 
    }
}
    
