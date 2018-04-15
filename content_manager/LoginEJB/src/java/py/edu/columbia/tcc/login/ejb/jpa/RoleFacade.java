/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.jpa;

import javax.ejb.Stateless;
import py.edu.columbia.tcc.login.model.Role;

/**
 *
 * @author tid
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> {
    
    public RoleFacade() {
        super(Role.class);
    }
}
