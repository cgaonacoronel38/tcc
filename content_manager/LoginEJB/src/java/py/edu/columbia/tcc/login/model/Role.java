/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "role", catalog = "content_manager", schema = "system_handler")
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_role")
    private Integer idRole;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "realmgroupname")
    private String realmgroupname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sys_role")
    private boolean sysRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<MenuPermission> menuPermissionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRole")
    private List<User> userList;

    public Role() {
    }

    public Role(Integer idRole) {
        this.idRole = idRole;
    }

    public Role(Integer idRole, String description, String realmgroupname, boolean sysRole) {
        this.idRole = idRole;
        this.description = description;
        this.realmgroupname = realmgroupname;
        this.sysRole = sysRole;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealmgroupname() {
        return realmgroupname;
    }

    public void setRealmgroupname(String realmgroupname) {
        this.realmgroupname = realmgroupname;
    }

    public boolean getSysRole() {
        return sysRole;
    }

    public void setSysRole(boolean sysRole) {
        this.sysRole = sysRole;
    }

    public List<MenuPermission> getMenuPermissionList() {
        return menuPermissionList;
    }

    public void setMenuPermissionList(List<MenuPermission> menuPermissionList) {
        this.menuPermissionList = menuPermissionList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRole != null ? idRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.idRole == null && other.idRole != null) || (this.idRole != null && !this.idRole.equals(other.idRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.login.model.Role[ idRole=" + idRole + " ]";
    }
    
}
