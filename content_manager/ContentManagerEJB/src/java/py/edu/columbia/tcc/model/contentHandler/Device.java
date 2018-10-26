/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.contentHandler;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "device", catalog = "content_manager", schema = "content_handler")
@NamedQueries({
    @NamedQuery(name = "Device.findAll", query = "SELECT d FROM Device d")})
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_device")
    private Integer idDevice;
    @Column(name = "id_company")
    private long idCompany;
    @Column(name = "uuid", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID uuid;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "active", insertable = false)
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    private List<Playbacks> playbacksList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    private List<Audiences> audiencesList;
    @JoinColumn(name = "id_location", referencedColumnName = "id_location")
    @ManyToOne(optional = false)
    private Location idLocation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    private List<DeviceContent> deviceContentList;

    public Device() {
    }

    public Device(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public Device(Integer idDevice, long idCompany, UUID uuid, boolean active) {
        this.idDevice = idDevice;
        this.idCompany = idCompany;
        this.uuid = uuid;
        this.active = active;
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<DeviceContent> getDeviceContentList() {
        return deviceContentList;
    }

    public void setDeviceContentList(List<DeviceContent> deviceContentList) {
        this.deviceContentList = deviceContentList;
    }

    public List<Playbacks> getPlaybacksList() {
        return playbacksList;
    }

    public void setPlaybacksList(List<Playbacks> playbacksList) {
        this.playbacksList = playbacksList;
    }

    public List<Audiences> getAudiencesList() {
        return audiencesList;
    }

    public void setAudiencesList(List<Audiences> audiencesList) {
        this.audiencesList = audiencesList;
    }

    public Location getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Location idLocation) {
        this.idLocation = idLocation;
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
        if (!(object instanceof Device)) {
            return false;
        }
        Device other = (Device) object;
        if ((this.idDevice == null && other.idDevice != null) || (this.idDevice != null && !this.idDevice.equals(other.idDevice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.contentHandler.Device[ idDevice=" + idDevice + " ]";
    }
    
}
