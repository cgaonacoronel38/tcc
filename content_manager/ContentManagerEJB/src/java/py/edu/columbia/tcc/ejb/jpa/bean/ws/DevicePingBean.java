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
public class DevicePingBean {
    private UUID device;
    private UUID currentContent;
    private int currentAudienceQuantity;
    private Date deviceDate;

    public DevicePingBean() {
    }

    public DevicePingBean(UUID device, UUID currentContent, int currentAudienceQuantity, Date deviceDate) {
        this.device = device;
        this.currentContent = currentContent;
        this.currentAudienceQuantity = currentAudienceQuantity;
        this.deviceDate = deviceDate;
    }

    public UUID getDevice() {
        return device;
    }

    public void setDevice(UUID device) {
        this.device = device;
    }

    public UUID getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(UUID currentContent) {
        this.currentContent = currentContent;
    }

    public int getCurrentAudienceQuantity() {
        return currentAudienceQuantity;
    }

    public void setCurrentAudienceQuantity(int currentAudienceQuantity) {
        this.currentAudienceQuantity = currentAudienceQuantity;
    }

    public Date getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(Date deviceDate) {
        this.deviceDate = deviceDate;
    }

}
