/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import py.edu.columbia.tcc.model.contentHandler.Device;

/**
 *
 * @author tokio
 */
@Embeddable
public class FilteredDevicePK implements Serializable {

    @Column(name = "id_filter_setting")
    private FilterSetting idFilterSetting;
    @Column(name = "id_device")
    private Device idDevice;

    public FilteredDevicePK() {
    }

    public FilteredDevicePK(FilterSetting idFilterSetting, Device idDevice) {
        this.idFilterSetting = idFilterSetting;
        this.idDevice = idDevice;
    }

    public FilterSetting getIdFilterSetting() {
        return idFilterSetting;
    }

    public void setIdFilterSetting(FilterSetting idFilterSetting) {
        this.idFilterSetting = idFilterSetting;
    }

    public Device getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Device idDevice) {
        this.idDevice = idDevice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFilterSetting.getIdFilterSetting();
        hash += (int) idDevice.getIdDevice();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilteredDevicePK)) {
            return false;
        }
        FilteredDevicePK other = (FilteredDevicePK) object;
        if (this.idFilterSetting != other.idFilterSetting) {
            return false;
        }
        if (this.idDevice != other.idDevice) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.FilteredDevicePK[ idFilterSetting=" + idFilterSetting + ", idDevice=" + idDevice + " ]";
    }
    
}
