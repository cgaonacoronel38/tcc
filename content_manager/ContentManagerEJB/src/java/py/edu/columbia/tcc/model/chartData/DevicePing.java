/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "device_ping", catalog = "content_manager", schema = "chart_data")
public class DevicePing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "device", columnDefinition = "uuid", insertable = true, updatable = false)
    private UUID device;
    @Column(name = "current_content", columnDefinition = "uuid", insertable = true, updatable = false)
    private UUID currentContent;
    @Column(name = "current_audience_quantity")
    private int currentAudienceQuantity;
    @Column(name = "device_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deviceDate;
    @Column(name = "server_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverDate;

    public DevicePing() {
    }

    public UUID getDevice() {
        return device;
    }

    public void setDevice(UUID device) {
        this.device = device;
    }

    public UUID getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(UUID currentContent) {
        this.currentContent = currentContent;
    }

    public int getCurrentAudienceQuantity() {
        return currentAudienceQuantity;
    }

    public void setCurrentAudienceQuantity(int currentAudienceQuantity) {
        this.currentAudienceQuantity = currentAudienceQuantity;
    }

    public Date getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(Date deviceDate) {
        this.deviceDate = deviceDate;
    }

    public Date getServerDate() {
        return serverDate;
    }

    public void setServerDate(Date serverDate) {
        this.serverDate = serverDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (device != null ? device.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DevicePing)) {
            return false;
        }
        DevicePing other = (DevicePing) object;
        if ((this.device == null && other.device != null) || (this.device != null && !this.device.equals(other.device))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.chartData.DevicePing[ device=" + device + " ]";
    }
    
}
