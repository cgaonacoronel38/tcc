/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "type_time", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "TypeTime.findAll", query = "SELECT t FROM TypeTime t")})
public class TypeTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_time")
    private Integer idTypeTime;
    @Size(max = 40)
    @Column(name = "description")
    private String description;
    @Column(name = "date_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFrom;
    @Column(name = "date_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTo;
    @Column(name = "days")
    private Integer days;
    @Column(name = "active", insertable = false)
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTypeTime")
    private List<FilterSetting> filterSettingList;

    public TypeTime() {
    }

    public TypeTime(Integer idTypeTime) {
        this.idTypeTime = idTypeTime;
    }

    public TypeTime(Integer idTypeTime, boolean active) {
        this.idTypeTime = idTypeTime;
        this.active = active;
    }

    public Integer getIdTypeTime() {
        return idTypeTime;
    }

    public void setIdTypeTime(Integer idTypeTime) {
        this.idTypeTime = idTypeTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        hash += (idTypeTime != null ? idTypeTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeTime)) {
            return false;
        }
        TypeTime other = (TypeTime) object;
        if ((this.idTypeTime == null && other.idTypeTime != null) || (this.idTypeTime != null && !this.idTypeTime.equals(other.idTypeTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.TypeTime[ idTypeTime=" + idTypeTime + " ]";
    }
    
}
