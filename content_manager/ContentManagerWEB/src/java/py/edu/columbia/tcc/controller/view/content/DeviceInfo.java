package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import py.edu.columbia.tcc.ejb.jpa.bean.DeviceStatusBean;

@ManagedBean
@ViewScoped
public class DeviceInfo implements Serializable {

    private DeviceStatusBean deviceStatus;
    private MapModel simpleModel;

    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        deviceStatus = (DeviceStatusBean) sessionMap.get("device_status");
        sessionMap.remove("device_status");

        if (deviceStatus.getGeoreference() != null) {
            simpleModel = new DefaultMapModel();
            String[] georeference = deviceStatus.getGeoreference().split(",");
            double latitude = Double.valueOf(georeference[0]);
            double longitude = Double.valueOf(georeference[1]);
            System.out.println("Latitude: "+latitude);
            System.out.println("Longitude: "+longitude);
            LatLng reference = new LatLng(latitude, longitude);

            simpleModel.addOverlay(new Marker(reference, deviceStatus.getLocationName()));
        }
    }

    public DeviceStatusBean getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(DeviceStatusBean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
    
    public MapModel getSimpleModel() {
        return simpleModel;
    }
}
