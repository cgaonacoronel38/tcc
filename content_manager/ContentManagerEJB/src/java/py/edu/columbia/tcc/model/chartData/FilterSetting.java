/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "filter_setting", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "FilterSetting.findAll", query = "SELECT f FROM FilterSetting f")})
public class FilterSetting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filter_setting")
    private Integer idFilterSetting;
    @Column(name = "all_places")
    private boolean allPlaces;
    @Column(name = "all_contents")
    private boolean allContents;
    @Column(name = "all_devices")
    private boolean allDevices;
    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private Date fromDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private Date toDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFilterSetting")
    private List<ChartPanel> chartPanelList;
    @JoinColumn(name = "id_type_filter", referencedColumnName = "id_filter")
    @ManyToOne(optional = false)
    private TypeFilter idTypeFilter;
    @JoinColumn(name = "id_type_place", referencedColumnName = "id_type_place")
    @ManyToOne(optional = false)
    private TypePlace idTypePlace;
    @JoinColumn(name = "id_type_time", referencedColumnName = "id_type_time")
    @ManyToOne(optional = false)
    private TypeTime idTypeTime;

    public FilterSetting() {
    }

    public FilterSetting(Integer idFilterSetting) {
        this.idFilterSetting = idFilterSetting;
    }

    public FilterSetting(Integer idFilterSetting, boolean allPlaces, boolean allContents, boolean allDevices) {
        this.idFilterSetting = idFilterSetting;
        this.allPlaces = allPlaces;
        this.allContents = allContents;
        this.allDevices = allDevices;
    }

    public Integer getIdFilterSetting() {
        return idFilterSetting;
    }

    public void setIdFilterSetting(Integer idFilterSetting) {
        this.idFilterSetting = idFilterSetting;
    }

    public boolean getAllPlaces() {
        return allPlaces;
    }

    public void setAllPlaces(boolean allPlaces) {
        this.allPlaces = allPlaces;
    }

    public boolean getAllContents() {
        return allContents;
    }

    public void setAllContents(boolean allContents) {
        this.allContents = allContents;
    }

    public boolean getAllDevices() {
        return allDevices;
    }

    public void setAllDevices(boolean allDevices) {
        this.allDevices = allDevices;
    }

    public List<ChartPanel> getChartPanelList() {
        return chartPanelList;
    }

    public void setChartPanelList(List<ChartPanel> chartPanelList) {
        this.chartPanelList = chartPanelList;
    }

    public TypeFilter getIdTypeFilter() {
        return idTypeFilter;
    }

    public void setIdTypeFilter(TypeFilter idTypeFilter) {
        this.idTypeFilter = idTypeFilter;
    }

    public TypePlace getIdTypePlace() {
        return idTypePlace;
    }

    public void setIdTypePlace(TypePlace idTypePlace) {
        this.idTypePlace = idTypePlace;
    }

    public TypeTime getIdTypeTime() {
        return idTypeTime;
    }

    public void setIdTypeTime(TypeTime idTypeTime) {
        this.idTypeTime = idTypeTime;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFilterSetting != null ? idFilterSetting.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilterSetting)) {
            return false;
        }
        FilterSetting other = (FilterSetting) object;
        if ((this.idFilterSetting == null && other.idFilterSetting != null) || (this.idFilterSetting != null && !this.idFilterSetting.equals(other.idFilterSetting))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.FilterSetting[ idFilterSetting=" + idFilterSetting + " ]";
    }
    
}
