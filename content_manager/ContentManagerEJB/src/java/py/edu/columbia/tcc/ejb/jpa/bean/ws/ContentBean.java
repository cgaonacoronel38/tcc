/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.bean.ws;

/**
 *
 * @author tokio
 */
public class ContentBean {
    private DeviceContentBean deviceContentBean;
    private String directory;
    private boolean active;
    private boolean downloaded;

    public DeviceContentBean getDeviceContentBean() {
        return deviceContentBean;
    }

    public void setDeviceContentBean(DeviceContentBean deviceContentBean) {
        this.deviceContentBean = deviceContentBean;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDownloaded() {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}
