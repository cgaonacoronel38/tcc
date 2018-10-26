/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.contentHandler;

import py.edu.columbia.tcc.model.contentHandler.Device;
import py.edu.columbia.tcc.model.contentHandler.Content;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "playbacks", catalog = "content_manager", schema = "content_handler")
@NamedQueries({
    @NamedQuery(name = "Playbacks.findAll", query = "SELECT p FROM Playbacks p")})
public class Playbacks implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlaybacksPK playbacksPK;
    @Column(name = "hour_from")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hourFrom;
    @Column(name = "hour_to")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hourTo;
    @Column(name = "stay_time")
    private Integer stayTime;
    @Column(name = "device_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deviceDate;
    @Column(name = "server_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serverDate;
    @JoinColumn(name = "id_content", referencedColumnName = "id_content", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Content content;
    @JoinColumn(name = "id_device", referencedColumnName = "id_device", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Device device;

    public Playbacks() {
    }

    public Playbacks(PlaybacksPK playbacksPK) {
        this.playbacksPK = playbacksPK;
    }

    public Playbacks(long idContent, long idDevice) {
        this.playbacksPK = new PlaybacksPK(idContent, idDevice);
    }

    public PlaybacksPK getPlaybacksPK() {
        return playbacksPK;
    }

    public void setPlaybacksPK(PlaybacksPK playbacksPK) {
        this.playbacksPK = playbacksPK;
    }

    public Date getHourFrom() {
        return hourFrom;
    }

    public void setHourFrom(Date hourFrom) {
        this.hourFrom = hourFrom;
    }

    public Date getHourTo() {
        return hourTo;
    }

    public void setHourTo(Date hourTo) {
        this.hourTo = hourTo;
    }

    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
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
        hash += (playbacksPK != null ? playbacksPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Playbacks)) {
            return false;
        }
        Playbacks other = (Playbacks) object;
        if ((this.playbacksPK == null && other.playbacksPK != null) || (this.playbacksPK != null && !this.playbacksPK.equals(other.playbacksPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.Playbacks[ playbacksPK=" + playbacksPK + " ]";
    }
    
}
