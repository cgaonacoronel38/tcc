/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import py.edu.columbia.tcc.model.contentHandler.Device;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "audience_device", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "AudienceDevice.findAll", query = "SELECT a FROM AudienceDevice a")})
public class AudienceDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_audience_device")
    private Long idAudienceDevice;
    @Basic(optional = false)
    @JoinColumn(name = "id_device", referencedColumnName = "id_device")
    @ManyToOne
    private Device idDevice;
    @Basic(optional = false)
    @Column(name = "server_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverDate;
    @Basic(optional = false)
    @Column(name = "device_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deviceDate;
    @Column(name = "stay_time")
    private Short stayTime;
    @Basic(optional = false)
    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime;

    public AudienceDevice() {
    }

    public AudienceDevice(Long idAudienceDevice) {
        this.idAudienceDevice = idAudienceDevice;
    }

    public AudienceDevice(Long idAudienceDevice, Device idDevice, Date serverDate, Date deviceDate, Date registrationTime) {
        this.idAudienceDevice = idAudienceDevice;
        this.idDevice = idDevice;
        this.serverDate = serverDate;
        this.deviceDate = deviceDate;
        this.registrationTime = registrationTime;
    }

    public Long getIdAudienceDevice() {
        return idAudienceDevice;
    }

    public void setIdAudienceDevice(Long idAudienceDevice) {
        this.idAudienceDevice = idAudienceDevice;
    }

    public Device getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Device idDevice) {
        this.idDevice = idDevice;
    }

    public Date getServerDate() {
        return serverDate;
    }

    public void setServerDate(Date serverDate) {
        this.serverDate = serverDate;
    }

    public Date getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(Date deviceDate) {
        this.deviceDate = deviceDate;
    }

    public Short getStayTime() {
        return stayTime;
    }

    public void setStayTime(Short stayTime) {
        this.stayTime = stayTime;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAudienceDevice != null ? idAudienceDevice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudienceDevice)) {
            return false;
        }
        AudienceDevice other = (AudienceDevice) object;
        if ((this.idAudienceDevice == null && other.idAudienceDevice != null) || (this.idAudienceDevice != null && !this.idAudienceDevice.equals(other.idAudienceDevice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.ejb.jpa.content.AudienceDevice[ idAudienceDevice=" + idAudienceDevice + " ]";
    }
    
}
