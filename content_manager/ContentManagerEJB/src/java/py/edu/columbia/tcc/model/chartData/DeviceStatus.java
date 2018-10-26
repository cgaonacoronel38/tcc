/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import py.edu.columbia.tcc.model.contentHandler.Device;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "device_status", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "DeviceStatus.findAll", query = "SELECT d FROM DeviceStatus d")})
public class DeviceStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @OneToOne
    @JoinColumn(name = "id_device")
    private Device idDevice;
    @Column(name = "server_hour")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverHour;
    @Column(name = "devicent_hour")
    @Temporal(TemporalType.TIMESTAMP)
    private Date devicentHour;
    @Column(name = "audience_quantity")
    private Short audienceQuantity;

    public DeviceStatus() {
    }

    public DeviceStatus(Device idDevice) {
        this.idDevice = idDevice;
    }

    public Device getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Device idDevice) {
        this.idDevice = idDevice;
    }

    public Date getServerHour() {
        return serverHour;
    }

    public void setServerHour(Date serverHour) {
        this.serverHour = serverHour;
    }

    public Date getDevicentHour() {
        return devicentHour;
    }

    public void setDevicentHour(Date devicentHour) {
        this.devicentHour = devicentHour;
    }

    public Short getAudienceQuantity() {
        return audienceQuantity;
    }

    public void setAudienceQuantity(Short audienceQuantity) {
        this.audienceQuantity = audienceQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDevice != null ? idDevice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceStatus)) {
            return false;
        }
        DeviceStatus other = (DeviceStatus) object;
        if ((this.idDevice == null && other.idDevice != null) || (this.idDevice != null && !this.idDevice.equals(other.idDevice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.chartData.DeviceStatus[ idDevice=" + idDevice + " ]";
    }
    
}
