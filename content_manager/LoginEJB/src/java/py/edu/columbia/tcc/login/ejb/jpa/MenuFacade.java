/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.ejb.jpa;

import javax.ejb.Stateless;
import py.edu.columbia.tcc.login.model.Menu;

/**
 *
 * @author tid
 */
@Stateless
public class MenuFacade extends AbstractFacade<Menu> {
    
    public MenuFacade() {
        super(Menu.class);
    }
}
