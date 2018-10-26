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
public class ContentAudienceBean {
    private UUID content;
    private UUID device;
    private Date deviceDate;
    private Date registrationTime;
    private Date fromTime;
    private Date toTime;
    private Integer stayTime;
    private Integer audienceQuantity;

    public ContentAudienceBean() {
    }

    public ContentAudienceBean(UUID device, Date deviceDate, Date registrationTime, Integer stayTime) {
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

    public Integer getStayTime() {
        return stayTime;
    }

    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    } 

    public UUID getContent() {
        return content;
    }

    public void setContent(UUID content) {
        this.content = content;
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

    public Integer getAudienceQuantity() {
        return audienceQuantity;
    }

    public void setAudienceQuantity(Integer audienceQuantity) {
        this.audienceQuantity = audienceQuantity;
    }
}
