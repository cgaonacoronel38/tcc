/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import py.edu.columbia.tcc.model.contentHandler.Device;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "filtered_device", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "FilteredDevice.findAll", query = "SELECT f FROM FilteredDevice f")})
public class FilteredDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FilteredDevicePK filteredDevicePK;

    public FilteredDevice() {
    }

    public FilteredDevice(FilteredDevicePK filteredDevicePK) {
        this.filteredDevicePK = filteredDevicePK;
    }

    public FilteredDevice(FilterSetting idFilterSetting, Device idDevice) {
        this.filteredDevicePK = new FilteredDevicePK(idFilterSetting, idDevice);
    }

    public FilteredDevicePK getFilteredDevicePK() {
        return filteredDevicePK;
    }

    public void setFilteredDevicePK(FilteredDevicePK filteredDevicePK) {
        this.filteredDevicePK = filteredDevicePK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filteredDevicePK != null ? filteredDevicePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilteredDevice)) {
            return false;
        }
        FilteredDevice other = (FilteredDevice) object;
        if ((this.filteredDevicePK == null && other.filteredDevicePK != null) || (this.filteredDevicePK != null && !this.filteredDevicePK.equals(other.filteredDevicePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.FilteredDevice[ filteredDevicePK=" + filteredDevicePK + " ]";
    }
    
}
