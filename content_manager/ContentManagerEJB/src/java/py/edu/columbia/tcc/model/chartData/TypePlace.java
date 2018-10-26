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
@Table(name = "type_place", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "TypePlace.findAll", query = "SELECT t FROM TypePlace t")})
public class TypePlace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_type_place")
    private Integer idTypePlace;
    @Size(max = 40)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTypePlace")
    private List<FilterSetting> filterSettingList;

    public TypePlace() {
    }

    public TypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public Integer getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(Integer idTypePlace) {
        this.idTypePlace = idTypePlace;
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
        hash += (idTypePlace != null ? idTypePlace.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypePlace)) {
            return false;
        }
        TypePlace other = (TypePlace) object;
        if ((this.idTypePlace == null && other.idTypePlace != null) || (this.idTypePlace != null && !this.idTypePlace.equals(other.idTypePlace))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.TypePlace[ idTypePlace=" + idTypePlace + " ]";
    }
    
}
