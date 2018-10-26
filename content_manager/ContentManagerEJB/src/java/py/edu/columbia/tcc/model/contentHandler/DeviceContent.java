/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.contentHandler;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    @Column(name = "uuid_content", columnDefinition = "uuid", updatable = false)
    private UUID uuidContent;
    @Column(name = "uuid_device", columnDefinition = "uuid", updatable = false)
    private UUID uuidDevice;
    @Column(name = "active")
    private boolean active;
    @Column(name = "downloaded")
    private boolean downloaded;
    @Column(name = "date_add")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;
    @Column(name = "date_download")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDownload;
    @Column(name = "date_inactive")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInactive;
    @Column(name = "date_due")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDue;
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

    public DeviceContent(DeviceContentPK deviceContentPK, UUID uuidContent, UUID uuidDevice, boolean active, boolean downloaded) {
        this.deviceContentPK = deviceContentPK;
        this.uuidContent = uuidContent;
        this.uuidDevice = uuidDevice;
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

    public UUID getUuidContent() {
        return uuidContent;
    }

    public void setUuidContent(UUID uuidContent) {
        this.uuidContent = uuidContent;
    }

    public UUID getUuidDevice() {
        return uuidDevice;
    }

    public void setUuidDevice(UUID uuidDevice) {
        this.uuidDevice = uuidDevice;
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

    public Date getDateDownload() {
        return dateDownload;
    }

    public void setDateDownload(Date dateDownload) {
        this.dateDownload = dateDownload;
    }

    public Date getDateInactive() {
        return dateInactive;
    }

    public void setDateInactive(Date dateInactive) {
        this.dateInactive = dateInactive;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
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
        return "py.edu.columbia.tcc.model.contentHandler.DeviceContent[ deviceContentPK=" + deviceContentPK + " ]";
    }
    
}
