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
public class DeviceContentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_device")
    private long idDevice;
    @Basic(optional = false)
    @Column(name = "id_content")
    private long idContent;

    public DeviceContentPK() {
    }

    public DeviceContentPK(long idDevice, long idContent) {
        this.idDevice = idDevice;
        this.idContent = idContent;
    }

    public long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(long idDevice) {
        this.idDevice = idDevice;
    }

    public long getIdContent() {
        return idContent;
    }

    public void setIdContent(long idContent) {
        this.idContent = idContent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDevice;
        hash += (int) idContent;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeviceContentPK)) {
            return false;
        }
        DeviceContentPK other = (DeviceContentPK) object;
        if (this.idDevice != other.idDevice) {
            return false;
        }
        if (this.idContent != other.idContent) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.contentHandler.DeviceContentPK[ idDevice=" + idDevice + ", idContent=" + idContent + " ]";
    }
    
}
