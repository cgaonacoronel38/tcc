package py.edu.columbia.tcc.controller.view.content;

import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.ejb.jpa.content.CityFacade;
import py.edu.columbia.tcc.ejb.jpa.content.CountryFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.LocationFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.City;
import py.edu.columbia.tcc.model.contentHandler.Country;
import py.edu.columbia.tcc.model.contentHandler.Device;
import py.edu.columbia.tcc.model.contentHandler.Location;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.ejb.jpa.bean.ContentDeviceBean;

@ManagedBean
@ViewScoped
public class CrudDevice implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(CrudDevice.class);

    // managed beans
    @Inject
    private GDMSession session;

    @Inject
    private DeviceFacade deviceEJB;

    @Inject
    private LocationFacade locationEJB;

    @Inject
    private CountryFacade countryEJB;

    @Inject
    private CityFacade cityEJB;

    //entities
    @Inject
    @New
    private Device newDevice;

    @Inject
    @New
    private Country newCountry;

    @Inject
    @New
    private City newCity;

    @Inject
    @New
    private Location newLocation;

    @Inject
    @New
    private Location selectedLocation;

    @Inject
    @New
    private Country selectedCountry;

    @Inject
    @New
    private Country selectedCountryl;

    @Inject
    @New
    private City selectedCity;
    
    @Inject
    @New
    private Device selectedDevice;

    private Integer idDevice;
    private MapModel simpleModel;
    private String geolocation;
    private String location;
    private List<ContentDeviceBean> listDevice;
    private List<Location> listLocation;
    private List<Country> listCountry;
    private List<City> listCity;

    public CrudDevice() {
    }

    @PostConstruct
    public void init() {
        updDeviceList();
        geolocation = "-25.286156,-57.611497";
        location = "prueba";
        showMap(geolocation, location);
    }

    // getters y setters
    public Device getNewDevice() {
        return newDevice;
    }

    public void setNewDevice(Device newDevice) {
        this.newDevice = newDevice;
    }

    public Country getNewCountry() {
        return newCountry;
    }

    public void setNewCountry(Country newCountry) {
        this.newCountry = newCountry;
    }

    public City getNewCity() {
        return newCity;
    }

    public void setNewCity(City newCity) {
        this.newCity = newCity;
    }

    public Location getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(Location newLocation) {
        this.newLocation = newLocation;
    }

    public Location getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public Country getSelectedCountryl() {
        return selectedCountryl;
    }

    public void setSelectedCountryl(Country selectedCountryl) {
        this.selectedCountryl = selectedCountryl;
    }

    public Country getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(Country selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public City getSelectedCity() {
        return selectedCity;
    }

    public void setSelectedCity(City selectedCity) {
        this.selectedCity = selectedCity;
    }

    public List<ContentDeviceBean> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<ContentDeviceBean> listDevice) {
        this.listDevice = listDevice;
    }

    public List<Location> getListLocation() {
        return listLocation;
    }

    public void setListLocation(List<Location> listLocation) {
        this.listLocation = listLocation;
    }

    public List<Country> getListCountry() {
        return listCountry;
    }

    public void setListCountry(List<Country> listCountry) {
        this.listCountry = listCountry;
    }

    public List<City> getListCity() {
        return listCity;
    }

    public void setListCity(List<City> listCity) {
        this.listCity = listCity;
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

    public Device getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    public Integer getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(Integer idDevice) {
        this.idDevice = idDevice;
    }

    // metodos
    public void showMap(String _geolocation, String _location) {
        try {
            geolocation = _geolocation;
            location = _location;
            System.err.println("Georeferencia: " + _geolocation);
            System.err.println("Localidad: " + _location);
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

    public void updDeviceList() {
        try {
            listDevice = deviceEJB.listDevices();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updLocationList() {
        try {
            listLocation = locationEJB.listLocation();
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updCountryList() {
        try {
            listCountry = countryEJB.listCountry();
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updCityList() {
        try {
            listCity = cityEJB.listCity(selectedCountryl.getIdCountry());
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDevice() {
        try {
            if (selectedLocation == null || selectedLocation.getIdLocation() == null || selectedLocation.getIdLocation() == 0) {
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "Debe seleccionar la localidad");
                return;
            }
            newDevice.setIdLocation(selectedLocation);
            newDevice.setIdCompany(session.getDefaultCompany().getIdCompany());
            deviceEJB.create(newDevice);
            updDeviceList();
            RequestContext.getCurrentInstance().execute("PF('wvCreateDevice').hide()");
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "Dispositivo agregado correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo crear dispositivo, intente nuevamente");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initCreateDevice(){
        newDevice = new Device();
        selectedLocation = new Location();
        updLocationList();
    }
    
    public void initEditDevice(Integer idDevice){
        System.err.println("Id device retornado: "+idDevice);
        selectedDevice = deviceEJB.find(idDevice);
        selectedLocation = selectedDevice.getIdLocation();
        updLocationList();
    }
    
    public void editDevice() {
        try {
            selectedDevice.setIdLocation(selectedLocation);
            selectedDevice.setIdCompany(session.getDefaultCompany().getIdCompany());
            deviceEJB.edit(selectedDevice);
            updDeviceList();
            RequestContext.getCurrentInstance().execute("PF('wvEditDevice').hide()");
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Edición exitosa", "Terminal editada correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo crear terminal, intente nuevamente");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createLocation() {
        try {
            newLocation.setIdCountry(selectedCountryl);
            newLocation.setIdCity(selectedCity);
            locationEJB.create(newLocation);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "Localidad agregada correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo crear localidad, intente nuevamente");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeStatus(Integer idDevice, boolean status) {
        try {
            Integer updates = deviceEJB.updateStatus(idDevice, status);
            if(updates > 0){
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Operacion exitosa! ", status == true ? "Terminal activada exitosamente!" : "Terminal desactivada exitosamente!");
            } else {
                MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Error en operacion", "No se ha podido actualizar la terminal");
            }
        } catch (GDMEJBException ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Error en la operacion", "No se ha podido actualizar la terminal");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createCountry() {
        try {
            countryEJB.create(newCountry);
            RequestContext.getCurrentInstance().execute("PF('wvCountry').hide()");
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "País agregado correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo agregar País, intente nuevamente");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createCity() {
        try {
            newCity.setIdCountry(selectedCountry);
            cityEJB.create(newCity);
            RequestContext.getCurrentInstance().execute("PF('wvCity').hide()");
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "Ciudad agregada correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo agregar Ciudad, intente nuevamente");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try {
            Location locationEdited = ((Location) event.getObject());
            locationEJB.edit(locationEdited);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Edicion exitosa", "Localidad editada exitosamente!");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "Error al editar localidad");
        }
        
    }
     
    public void onRowCancel(RowEditEvent event) {
        MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "Edicion cancelada");
    }
    
    public void viewMap(String _geolocation, String _location) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("geolocation", _geolocation);
        sessionMap.put("location", _location);
        Map<String, Object> options = new HashMap<>();
        options.put("width", 600);
        options.put("height", 400);
        options.put("modal", true);
        options.put("title", "Información del panel de graficos");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("createDevice", options, null);
    }
}
