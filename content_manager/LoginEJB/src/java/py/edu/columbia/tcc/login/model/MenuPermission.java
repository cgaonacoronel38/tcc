/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "menu_permission", catalog = "content_manager", schema = "system_handler")
@NamedQueries({
    @NamedQuery(name = "MenuPermission.findAll", query = "SELECT m FROM MenuPermission m")})
public class MenuPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MenuPermissionPK menuPermissionPK;
    @Column(name = "access")
    private boolean access;
    @Column(name = "insert")
    private boolean insert;
    @Column(name = "update")
    private boolean update;
    @Column(name = "report")
    private boolean report;
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Menu menu;
    @JoinColumn(name = "id_role", referencedColumnName = "id_role", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Role role;

    public MenuPermission() {
    }

    public MenuPermission(MenuPermissionPK menuPermissionPK) {
        this.menuPermissionPK = menuPermissionPK;
    }

    public MenuPermission(MenuPermissionPK menuPermissionPK, boolean access, boolean insert, boolean update, boolean report) {
        this.menuPermissionPK = menuPermissionPK;
        this.access = access;
        this.insert = insert;
        this.update = update;
        this.report = report;
    }

    public MenuPermission(long idRole, long idMenu) {
        this.menuPermissionPK = new MenuPermissionPK(idRole, idMenu);
    }

    public MenuPermissionPK getMenuPermissionPK() {
        return menuPermissionPK;
    }

    public void setMenuPermissionPK(MenuPermissionPK menuPermissionPK) {
        this.menuPermissionPK = menuPermissionPK;
    }

    public boolean getAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

    public boolean getInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean getUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean getReport() {
        return report;
    }

    public void setReport(boolean report) {
        this.report = report;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (menuPermissionPK != null ? menuPermissionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPermission)) {
            return false;
        }
        MenuPermission other = (MenuPermission) object;
        if ((this.menuPermissionPK == null && other.menuPermissionPK != null) || (this.menuPermissionPK != null && !this.menuPermissionPK.equals(other.menuPermissionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.login.model.MenuPermission[ menuPermissionPK=" + menuPermissionPK + " ]";
    }
    
}
