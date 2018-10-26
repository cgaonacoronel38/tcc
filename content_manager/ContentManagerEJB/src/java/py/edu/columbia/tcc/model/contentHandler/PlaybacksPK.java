/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.contentHandler;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tokio
 */
@Embeddable
public class PlaybacksPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_content")
    private long idContent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_device")
    private long idDevice;

    public PlaybacksPK() {
    }

    public PlaybacksPK(long idContent, long idDevice) {
        this.idContent = idContent;
        this.idDevice = idDevice;
    }

    public long getIdContent() {
        return idContent;
    }

    public void setIdContent(long idContent) {
        this.idContent = idContent;
    }

    public long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(long idDevice) {
        this.idDevice = idDevice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idContent;
        hash += (int) idDevice;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlaybacksPK)) {
            return false;
        }
        PlaybacksPK other = (PlaybacksPK) object;
        if (this.idContent != other.idContent) {
            return false;
        }
        if (this.idDevice != other.idDevice) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.PlaybacksPK[ idContent=" + idContent + ", idDevice=" + idDevice + " ]";
    }
    
}
