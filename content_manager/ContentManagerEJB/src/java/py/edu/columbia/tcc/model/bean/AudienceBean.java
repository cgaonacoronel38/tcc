/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.bean;

import java.util.Date;

/**
 *
 * @author tokio
 */
public class AudienceBean {
    private Long idAudience;
    private Long idDevice;
    private Integer quantity;
    private Date deviceDate;
    private Date serverDate;

    public AudienceBean() {
    }

    public Long getIdAudience() {
        return idAudience;
    }

    public void setIdAudience(Long idAudience) {
        this.idAudience = idAudience;
    }

    public Long getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Long idDevice) {
        this.idDevice = idDevice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
}
