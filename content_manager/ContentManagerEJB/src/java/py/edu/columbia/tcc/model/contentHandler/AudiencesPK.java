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
public class AudiencesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_audience")
    private long idAudience;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_device")
    private long idDevice;

    public AudiencesPK() {
    }

    public AudiencesPK(long idAudience, long idDevice) {
        this.idAudience = idAudience;
        this.idDevice = idDevice;
    }

    public long getIdAudience() {
        return idAudience;
    }

    public void setIdAudience(long idAudience) {
        this.idAudience = idAudience;
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
        hash += (int) idAudience;
        hash += (int) idDevice;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AudiencesPK)) {
            return false;
        }
        AudiencesPK other = (AudiencesPK) object;
        if (this.idAudience != other.idAudience) {
            return false;
        }
        if (this.idDevice != other.idDevice) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.AudiencesPK[ idAudience=" + idAudience + ", idDevice=" + idDevice + " ]";
    }
    
}
