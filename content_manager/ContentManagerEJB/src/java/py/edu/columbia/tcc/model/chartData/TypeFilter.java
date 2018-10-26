/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

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
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "type_filter", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "TypeFilter.findAll", query = "SELECT t FROM TypeFilter t")})
public class TypeFilter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filter")
    private Integer idFilter;
    @Size(max = 40)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTypeFilter")
    private List<FilterSetting> filterSettingList;

    public TypeFilter() {
    }

    public TypeFilter(Integer idFilter) {
        this.idFilter = idFilter;
    }

    public Integer getIdFilter() {
        return idFilter;
    }

    public void setIdFilter(Integer idFilter) {
        this.idFilter = idFilter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FilterSetting> getFilterSettingList() {
        return filterSettingList;
    }

    public void setFilterSettingList(List<FilterSetting> filterSettingList) {
        this.filterSettingList = filterSettingList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilter != null ? idFilter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeFilter)) {
            return false;
        }
        TypeFilter other = (TypeFilter) object;
        if ((this.idFilter == null && other.idFilter != null) || (this.idFilter != null && !this.idFilter.equals(other.idFilter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.TypeFilter[ idFilter=" + idFilter + " ]";
    }
    
}
