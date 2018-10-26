/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.login.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "sys_param", catalog = "content_manager", schema = "system_handler")
@NamedQueries({
    @NamedQuery(name = "SysParam.findAll", query = "SELECT s FROM SysParam s")})
public class SysParam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_param")
    private Integer idParam;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "key")
    private String key;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "value")
    private String value;
    @Size(max = 200)
    @Column(name = "description")
    private String description;

    public SysParam() {
    }

    public SysParam(Integer idParam) {
        this.idParam = idParam;
    }

    public SysParam(Integer idParam, String key, String value) {
        this.idParam = idParam;
        this.key = key;
        this.value = value;
    }

    public Integer getIdParam() {
        return idParam;
    }

    public void setIdParam(Integer idParam) {
        this.idParam = idParam;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParam != null ? idParam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysParam)) {
            return false;
        }
        SysParam other = (SysParam) object;
        if ((this.idParam == null && other.idParam != null) || (this.idParam != null && !this.idParam.equals(other.idParam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.login.model.SysParam[ idParam=" + idParam + " ]";
    }
    
}
