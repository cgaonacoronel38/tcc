/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.bean;

import py.edu.columbia.tcc.ejb.jpa.content.AbstractFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
//import edu.columbia.tcc.model.bean.GeneralBalanceBean;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * @author tokio
 */
@Stateless
public class GeneralBalanceFacade extends AbstractFacade<GeneralBalanceFacade> {
//    List<GeneralBalanceBean> listGeneralBalance =  null;
    
    public GeneralBalanceFacade() {
        super(GeneralBalanceFacade.class);
    }
    /*
    // lista general de saldos
    public List<GeneralBalanceBean> getBalance(String billto, Date toDate) throws GDMEJBException{
       try{
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT cc.billto AS bill_to,  ");  //0
            sql.append(" q.last_payment, "); //1
            sql.append(" COALESCE(q.day_arrears, 0) AS day_arrears, "); //2
            
            sql.append(" CASE WHEN q.balance_arrears IS NOT NULL AND q.balance_arrears > 0 AND q.balance_arrears >= q.pay_advance ");
            sql.append(" THEN q.balance_arrears - q.pay_advance ELSE 0 ");
            sql.append(" END AS balance_arrears, ");//3
            
            sql.append(" 0 AS remaindersfinan, ");//4
            sql.append(" (COALESCE(q.balance_invoiced_equi, 0) + COALESCE(q.balance_invoiced_service, 0)) - COALESCE(q.pay_advance, 0) AS remaindersnoexp, ");//5
            
            sql.append(" COALESCE(q.balance_expire_service, 0) AS remainderstoinvoice, ");//6
            sql.append(" COALESCE(q.balance_expire_equi, 0) AS pendigamor, ");//7
            sql.append(" 0 AS pendigdebt ");//8
            sql.append(" FROM public.cc_customer cc ");
            sql.append(" LEFT JOIN billing.fn_bill_diary_balance(?1,?2) q ");
            sql.append(" ON cc.billto = q.bill_to ");
            sql.append(" WHERE ?3 is null OR cc.billto like ?4 ");
            sql.append(" AND cc.status = 'A' ");
            sql.append(" AND cc.cardactive = '3' ");
            sql.append(" ORDER BY cc.billto ");
            
            Query q = getEntityManager().createNativeQuery(sql.toString());
            q.setParameter(1, billto == null || billto.isEmpty() ? null : billto.trim());
            q.setParameter(2, new java.sql.Date(toDate.getTime()));
            q.setParameter(3, billto == null || billto.isEmpty() ? null : billto.trim());
            q.setParameter(4, billto == null || billto.isEmpty() ? null : billto.trim());
            
            List<Object[]> data = q.getResultList();
            listGeneralBalance = new ArrayList<>();
            
            for(Object[] row : data){
                GeneralBalanceBean bean = new GeneralBalanceBean();
                if(row[0] != null){
                    bean.setBillto((String)row[0]);
                }
                
                if(row[1] != null){
                    bean.setLastPayment((Date)row[1]);
                }
                
                if(row[2] != null){
                    bean.setDayArrears((Integer)row[2]);
                }
                
                if(row[3] != null){
                    bean.setBalanceArrears( new BigDecimal(row[3].toString()));
                }
                
                if(row[4] != null){
                    bean.setRemaindersFinan(new BigDecimal(row[4].toString()));
                }
                
                if(row[5] != null){
                    bean.setRemaindersNoExp(new BigDecimal(row[5].toString()));
                }
                
                if(row[6] != null){
                    bean.setRemaindersToInvoice(new BigDecimal(row[6].toString()));
                }
                
                if(row[7] != null){
                    bean.setPendigAmor(new BigDecimal(row[7].toString()));
                }
                
                if(row[8] != null){
                    bean.setPendigDebt(new BigDecimal(row[8].toString()));
                }
                
                listGeneralBalance.add(bean);
            }
            
            return listGeneralBalance;
        } catch(Exception e){
            throw new GDMEJBException("Error al generar listado balance general procard; "+e.getLocalizedMessage());
        } 
    }*/
}
    
