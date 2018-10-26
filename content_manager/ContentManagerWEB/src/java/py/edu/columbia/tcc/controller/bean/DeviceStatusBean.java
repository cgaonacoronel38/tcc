/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.controller.bean;

/**
 *
 * @author tokio
 */
public class DeviceStatusBean {
    String title;
    String dashboardID;
    String serverHour;
    String deviceHour;
    String audienceQuantity;
    String deviceStatus;
    String statusClass;
    String icon;
    String deviceDescription;
    String company;
    String location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDashboardID() {
        return dashboardID;
    }

    public void setDashboardID(String dashboardID) {
        this.dashboardID = dashboardID;
    }

    public String getServerHour() {
        return serverHour;
    }

    public void setServerHour(String serverHour) {
        this.serverHour = serverHour;
    }

    public String getDeviceHour() {
        return deviceHour;
    }

    public void setDeviceHour(String deviceHour) {
        this.deviceHour = deviceHour;
    }

    public String getAudienceQuantity() {
        return audienceQuantity;
    }

    public void setAudienceQuantity(String audienceQuantity) {
        this.audienceQuantity = audienceQuantity;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getStatusClass() {
        return statusClass;
    }

    public void setStatusClass(String statusClass) {
        this.statusClass = statusClass;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
