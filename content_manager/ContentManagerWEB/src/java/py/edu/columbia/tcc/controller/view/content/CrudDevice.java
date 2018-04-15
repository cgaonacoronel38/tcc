package py.edu.columbia.tcc.controller.view.content;

import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.ejb.jpa.content.CityFacade;
import py.edu.columbia.tcc.ejb.jpa.content.CountryFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.LocationFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.content.City;
import py.edu.columbia.tcc.model.content.Country;
import py.edu.columbia.tcc.model.content.Device;
import py.edu.columbia.tcc.model.content.Location;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    @Inject @New
    private Device newDevice;
    
    @Inject @New
    private Country newCountry;
    
    @Inject @New
    private City newCity;
    
    @Inject @New
    private Location newLocation;
    
    @Inject @New
    private Location selectedLocation;
    
    @Inject @New
    private Country selectedCountry;
    
    @Inject @New
    private Country selectedCountryl;
    
    @Inject @New
    private City selectedCity;
    
    private List<Device> listDevice;
    private List<Location> listLocation;
    private List<Country> listCountry;
    private List<City> listCity;

    public CrudDevice() {
    }

    @PostConstruct
    public void init() {
        listDevice = new ArrayList<>();
        listLocation = new ArrayList<>();
        listCountry = new ArrayList<>();
        listCity = new ArrayList<>();
        updDeviceList();
        updLocationList();
        updCountryList();
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

    public List<Device> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<Device> listDevice) {
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
    
    // metodos
    
    public void updDeviceList(){
        try {
            listDevice = deviceEJB.listDevice(session.getDefaultCompany().getIdCompany());
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updLocationList(){
        try {
            listLocation = locationEJB.listLocation();
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updCountryList(){
        try {
            listCountry = countryEJB.listCountry();
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
     
    public void updCityList(){
        try {
            listCity = cityEJB.listCity(selectedCountryl.getIdCountry());
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createDevice() {
        try {
            log.info("dispositivo "+newDevice.getName());
            newDevice.setIdLocation(selectedLocation);
            newDevice.setIdCompany(session.getDefaultCompany().getIdCompany());
            deviceEJB.create(newDevice);
            newDevice = new Device();
            selectedLocation = new Location();
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "Dispositivo agregado correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo crear dispositivo, intente nuevamente");
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
   
    public void createCountry(){
        try {
            countryEJB.create(selectedCountry);
            RequestContext.getCurrentInstance().execute("PF('wvCountry').hide()");
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "País agregado correctamente!");
        } catch (Exception ex) {
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN, "Atención!", "No se pudo agregar País, intente nuevamente");
            java.util.logging.Logger.getLogger(CrudDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createCity(){
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

}
