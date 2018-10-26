package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@ViewScoped
public class CreateDevice implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(CreateDevice.class);
    private MapModel simpleModel;
    private String geolocation;
    private String location;

    public CreateDevice() {
    }

    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        geolocation = (String) sessionMap.get("geolocation");
        location = (String) sessionMap.get("location");
        sessionMap.remove("geolocation");
        sessionMap.remove("location");
        showMap();
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public void setSimpleModel(MapModel simpleModel) {
        this.simpleModel = simpleModel;
    }
    
    public void showMap() {
        try {
            System.err.println("Georeferencia: " + geolocation);
            System.err.println("Localidad: " + location);

            if (geolocation != null && !geolocation.isEmpty()) {
                simpleModel = new DefaultMapModel();
                String[] georeference = geolocation.split(",");
                double latitude = Double.valueOf(georeference[0]);
                double longitude = Double.valueOf(georeference[1]);
                System.out.println("Latitude: " + latitude);
                System.out.println("Longitude: " + longitude);
                LatLng reference = new LatLng(latitude, longitude);

                simpleModel.addOverlay(new Marker(reference, location));
                System.err.println("Map model inicializado");
            } else {
                System.err.println("Georeferencia nula");
            }
        } catch (Exception ex) {
            log.info("Error al inicalizar georeferencia: " + ex.getMessage());
        }

    }
}
