/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tokio
 */
@Embeddable
public class MenuPermissionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_role")
    private long idRole;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_menu")
    private long idMenu;

    public MenuPermissionPK() {
    }

    public MenuPermissionPK(long idRole, long idMenu) {
        this.idRole = idRole;
        this.idMenu = idMenu;
    }

    public long getIdRole() {
        return idRole;
    }

    public void setIdRole(long idRole) {
        this.idRole = idRole;
    }

    public long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(long idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idRole;
        hash += (int) idMenu;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPermissionPK)) {
            return false;
        }
        MenuPermissionPK other = (MenuPermissionPK) object;
        if (this.idRole != other.idRole) {
            return false;
        }
        if (this.idMenu != other.idMenu) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.login.model.MenuPermissionPK[ idRole=" + idRole + ", idMenu=" + idMenu + " ]";
    }
    
}
