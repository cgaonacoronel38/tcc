/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.content;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "device_content", catalog = "content_manager", schema = "content_handler")
@NamedQueries({
    @NamedQuery(name = "DeviceContent.findAll", query = "SELECT d FROM DeviceContent d")})
public class DeviceContent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DeviceContentPK deviceContentPK;
    @Column(name = "uuid", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID uuid;
    @Column(name = "active", insertable = false)
    private boolean active;
    @Column(name = "downloaded", insertable = false)
    private boolean downloaded;
    @Column(name = "date_add", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;
    @JoinColumn(name = "id_content", referencedColumnName = "id_content", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Content content;
    @JoinColumn(name = "id_device", referencedColumnName = "id_device", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Device device;

    public DeviceContent() {
    }

    public DeviceContent(DeviceContentPK deviceContentPK) {
        this.deviceContentPK = deviceContentPK;
    }

    public DeviceContent(DeviceContentPK deviceContentPK, UUID uuid, boolean active, boolean downloaded) {
        this.deviceContentPK = deviceContentPK;
        this.uuid = uuid;
        this.active = active;
        this.downloaded = downloaded;
    }

    public DeviceContent(long idDevice, long idContent) {
        this.deviceContentPK = new DeviceContentPK(idDevice, idContent);
    }

    public DeviceContentPK getDeviceContentPK() {
        return deviceContentPK;
    }

    public void setDeviceContentPK(DeviceContentPK deviceContentPK) {
        this.deviceContentPK = deviceContentPK;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deviceContentPK != null ? deviceContentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceContent)) {
            return false;
        }
        DeviceContent other = (DeviceContent) object;
        if ((this.deviceContentPK == null && other.deviceContentPK != null) || (this.deviceContentPK != null && !this.deviceContentPK.equals(other.deviceContentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.content.DeviceContent[ deviceContentPK=" + deviceContentPK + " ]";
    }
    
}
