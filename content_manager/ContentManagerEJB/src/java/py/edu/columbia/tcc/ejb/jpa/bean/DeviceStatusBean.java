/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jpa.bean;

import java.util.Date;

/**
 *
 * @author tokio
 */
public class DeviceStatusBean {
    private String device;
    private Integer idDevice;
    private String currentContent;
    private Integer currentAudienceQuantity;
    private Date deviceDate;
    private Date serverDate;
    private String locationName;
    private String locationDescription;
    private String country;
    private String city;
    private String statusClass;
    private String icon;
    private String title;
    private String dashboardID;
    private String deviceStatus;
    private String georeference;

    public DeviceStatusBean() {
    }

    public DeviceStatusBean(String device, String currentContent, Integer currentAudienceQuantity, Date deviceDate, Date serverDate, String locationName, String locationDescription, String country, String city) {
        this.device = device;
        this.currentContent = currentContent;
        this.currentAudienceQuantity = currentAudienceQuantity;
        this.deviceDate = deviceDate;
        this.serverDate = serverDate;
        this.locationName = locationName;
        this.locationDescription = locationDescription;
        this.country = country;
        this.city = city;
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }
    
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(String currentContent) {
        this.currentContent = currentContent;
    }

    public Integer getCurrentAudienceQuantity() {
        return currentAudienceQuantity;
    }

    public void setCurrentAudienceQuantity(Integer currentAudienceQuantity) {
        this.currentAudienceQuantity = currentAudienceQuantity;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getGeoreference() {
        return georeference;
    }

    public void setGeoreference(String georeference) {
        this.georeference = georeference;
    }
}
