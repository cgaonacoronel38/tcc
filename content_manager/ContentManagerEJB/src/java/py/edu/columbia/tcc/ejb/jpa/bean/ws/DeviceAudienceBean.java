/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.bean.ws;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author tokio
 */
public class DeviceAudienceBean {
    private UUID device;
    private Date deviceDate;
    private Date registrationTime;
    private Short stayTime;

    public DeviceAudienceBean() {
    }

    public DeviceAudienceBean(UUID device, Date deviceDate, Date registrationTime, Short stayTime) {
        this.device = device;
        this.deviceDate = deviceDate;
        this.registrationTime = registrationTime;
        this.stayTime = stayTime;
    }

    public UUID getDevice() {
        return device;
    }

    public void setDevice(UUID device) {
        this.device = device;
    }

    public Date getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(Date deviceDate) {
        this.deviceDate = deviceDate;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Short getStayTime() {
        return stayTime;
    }

    public void setStayTime(Short stayTime) {
        this.stayTime = stayTime;
    } 
}
