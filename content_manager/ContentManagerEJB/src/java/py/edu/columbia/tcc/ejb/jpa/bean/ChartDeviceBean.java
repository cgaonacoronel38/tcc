/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.bean;

/**
 *
 * @author tokio
 */
public class ChartDeviceBean {
    private Integer idDevice;
    private String description;

    public ChartDeviceBean(Integer idDevice, String description) {
        this.idDevice = idDevice;
        this.description = description;
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
