/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.jpa;

import javax.ejb.Stateless;
import py.edu.columbia.tcc.login.model.Company;

/**
 *
 * @author tid
 */
@Stateless
public class CompanyFacade extends AbstractFacade<Company> {
    
    public CompanyFacade() {
        super(Company.class);
    }
}
