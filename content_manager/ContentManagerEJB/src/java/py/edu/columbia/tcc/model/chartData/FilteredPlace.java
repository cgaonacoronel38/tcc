/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import py.edu.columbia.tcc.model.contentHandler.City;
import py.edu.columbia.tcc.model.contentHandler.Location;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "filtered_place", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "FilteredPlace.findAll", query = "SELECT f FROM FilteredPlace f")})
public class FilteredPlace implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filtered_place")
    private Integer idFilteredPlace; 
    @JoinColumn(name = "id_location", referencedColumnName = "id_location")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Location idLocation;
    @JoinColumn(name = "id_city", referencedColumnName = "id_city")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private City idCity;
    @JoinColumn(name = "id_filter_setting")
    @ManyToOne
    private FilterSetting idFilterSetting;

    public FilteredPlace() {
    }

    public FilteredPlace(Integer idFilteredPlace) {
        this.idFilteredPlace = idFilteredPlace;
    }

    public Integer getIdFilteredPlace() {
        return idFilteredPlace;
    }

    public void setIdFilteredPlace(Integer idFilteredPlace) {
        this.idFilteredPlace = idFilteredPlace;
    }

    public Location getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Location idLocation) {
        this.idLocation = idLocation;
    }

    public City getIdCity() {
        return idCity;
    }

    public void setIdCity(City idCity) {
        this.idCity = idCity;
    }

    public FilterSetting getIdFilterSetting() {
        return idFilterSetting;
    }

    public void setIdFilterSetting(FilterSetting idFilterSetting) {
        this.idFilterSetting = idFilterSetting;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilteredPlace != null ? idFilteredPlace.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilteredPlace)) {
            return false;
        }
        FilteredPlace other = (FilteredPlace) object;
        if ((this.idFilteredPlace == null && other.idFilteredPlace != null) || (this.idFilteredPlace != null && !this.idFilteredPlace.equals(other.idFilteredPlace))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.FilteredPlace[ idFilteredPlace=" + idFilteredPlace + " ]";
    }
    
}
