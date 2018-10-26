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
import py.edu.columbia.tcc.model.contentHandler.Content;
import py.edu.columbia.tcc.model.contentHandler.Device;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "audience_content", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "AudienceContent.findAll", query = "SELECT a FROM AudienceContent a")})
public class AudienceContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_audience_content")
    private Integer idAudienceContent;
    @Basic(optional = false)
    @JoinColumn(name = "id_content", referencedColumnName = "id_content")
    @ManyToOne
    private Content idContent;
    @Basic(optional = false)
    @JoinColumn(name = "id_device", referencedColumnName = "id_device")
    @ManyToOne
    private Device idDevice;
    @Basic(optional = false)
    @Column(name = "server_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverDate;
    @Column(name = "device_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deviceDate;
    @Column(name = "from_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromTime;
    @Column(name = "to_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toTime;
    @Column(name = "stay_time")
    private Integer stayTime;
    @Column(name = "audience_quantity")
    private Integer audienceQuantity;
    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationTime;

    public AudienceContent() {
    }

    public AudienceContent(Integer idAudienceContent) {
        this.idAudienceContent = idAudienceContent;
    }

    public AudienceContent(Integer idAudienceContent, Content idContent, Device idDevice, Date serverDate) {
        this.idAudienceContent = idAudienceContent;
        this.idContent = idContent;
        this.idDevice = idDevice;
        this.serverDate = serverDate;
    }

    public Integer getIdAudienceContent() {
        return idAudienceContent;
    }

    public void setIdAudienceContent(Integer idAudienceContent) {
        this.idAudienceContent = idAudienceContent;
    }

    public Content getIdContent() {
        return idContent;
    }

    public void setIdContent(Content idContent) {
        this.idContent = idContent;
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

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    }

    public Integer getAudienceQuantity() {
        return audienceQuantity;
    }

    public void setAudienceQuantity(Integer audienceQuantity) {
        this.audienceQuantity = audienceQuantity;
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
        hash += (idAudienceContent != null ? idAudienceContent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudienceContent)) {
            return false;
        }
        AudienceContent other = (AudienceContent) object;
        if ((this.idAudienceContent == null && other.idAudienceContent != null) || (this.idAudienceContent != null && !this.idAudienceContent.equals(other.idAudienceContent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.chartData.AudienceContent[ idAudienceContent=" + idAudienceContent + " ]";
    }
    
}
