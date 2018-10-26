package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.controller.view.utils.Message;
import py.edu.columbia.tcc.ejb.jpa.content.ChartPanelFacade;
import py.edu.columbia.tcc.ejb.jpa.content.CityFacade;
import py.edu.columbia.tcc.ejb.jpa.content.ContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilterSettingFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilteredContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilteredPlaceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.LocationFacade;
import py.edu.columbia.tcc.ejb.jpa.content.TypeChartFacade;
import py.edu.columbia.tcc.ejb.jpa.content.TypeFilterFacade;
import py.edu.columbia.tcc.ejb.jpa.content.TypePlaceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.TypeTimeFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.chartData.ChartPanel;
import py.edu.columbia.tcc.model.chartData.FilterSetting;
import py.edu.columbia.tcc.model.chartData.FilteredContent;
import py.edu.columbia.tcc.model.chartData.FilteredContentPK;
import py.edu.columbia.tcc.model.chartData.FilteredPlace;
import py.edu.columbia.tcc.model.chartData.TypeChart;
import py.edu.columbia.tcc.model.chartData.TypeFilter;
import py.edu.columbia.tcc.model.chartData.TypePlace;
import py.edu.columbia.tcc.model.chartData.TypeTime;
import py.edu.columbia.tcc.model.contentHandler.City;
import py.edu.columbia.tcc.model.contentHandler.Content;
import py.edu.columbia.tcc.model.contentHandler.Location;

@ManagedBean
@ViewScoped
public class CreateChartPanel implements Serializable {

    @Inject
    private ContentFacade contentEJB;

    @Inject
    private CityFacade cityEJB;

    @Inject
    private LocationFacade locationEJB;

    @Inject
    private TypeChartFacade typeChartEJB;

    @Inject
    private TypePlaceFacade typePlaceEJB;

    @Inject
    private TypeTimeFacade typeTimeEJB;

    @Inject
    private TypeFilterFacade typeFilterEJB;

    @Inject
    private ChartPanelFacade chartPanelEJB;

    @Inject
    private FilterSettingFacade filterSettingEJB;

    @Inject
    private FilteredContentFacade filteredContentEJB;

    @Inject
    private FilteredPlaceFacade filteredPlaceEJB;

    @Inject
    private GDMSession gdmSession;

    private List<ChartPanel> listChartPanels;

    private DualListModel<Content> dualModelContents;
    private DualListModel<City> dualModelCities;
    private DualListModel<Location> dualModelLocations;
    private List<Content> selectedContents;
    private List<Content> listContents;
    private List<City> selectedCities;
    private List<City> listCities;
    private List<Location> selectedLocations;
    private List<Location> listLocations;
    private List<TypeChart> listTypeCharts;
    private List<TypePlace> listTypePlaces;
    private List<TypeTime> listTypeTimes;
    private List<TypeFilter> listTypeFilters;
    private Date fromDate;
    private Date toDate;

    private boolean filterContent;
    private boolean filterTipeZone;

    @Inject
    @New
    private TypeChart selectedTypeChart;
    @Inject
    @New
    private TypePlace selectedTypePlace;
    @Inject
    @New
    private TypeTime selectedTypeTime;
    @Inject
    @New
    private TypeFilter selectedTypeFilter;

    @Inject
    @New
    private ChartPanel newChartPanel;

    private String contentToDisplay = "1";

    private boolean skipWizard;

    @PostConstruct
    public void init() {
        /**
         * Inicializando valores PickList Content
         */
        initPLContentent();
        initPLLocation();
        initPLCities();

        loadTypecharts();
        loadTypePlaces();
        loadTypeTimes();
        loadTypeFilters();

        newChartPanel = new ChartPanel();
    }

    public List<ChartPanel> getListChartPanels() {
        return listChartPanels;
    }

    public void setListChartPanels(List<ChartPanel> listChartPanels) {
        this.listChartPanels = listChartPanels;
    }

    public DualListModel<Content> getDualModelContents() {
        return dualModelContents;
    }

    public void setDualModelContents(DualListModel<Content> dualModelContents) {
        this.dualModelContents = dualModelContents;
    }

    public DualListModel<City> getDualModelCities() {
        return dualModelCities;
    }

    public void setDualModelCities(DualListModel<City> dualModelCities) {
        this.dualModelCities = dualModelCities;
    }

    public DualListModel<Location> getDualModelLocations() {
        return dualModelLocations;
    }

    public void setDualModelLocations(DualListModel<Location> dualModelLocations) {
        this.dualModelLocations = dualModelLocations;
    }

    public boolean isSkipWizard() {
        return skipWizard;
    }

    public void setSkipWizard(boolean skipWizard) {
        this.skipWizard = skipWizard;
    }

    public ChartPanel getNewChartPanel() {
        return newChartPanel;
    }

    public void setNewChartPanel(ChartPanel newChartPanel) {
        this.newChartPanel = newChartPanel;
    }

    public TypeChart getSelectedTypeChart() {
        return selectedTypeChart;
    }

    public void setSelectedTypeChart(TypeChart selectedTypeChart) {
        this.selectedTypeChart = selectedTypeChart;
    }

    public List<TypeChart> getListTypeCharts() {
        return listTypeCharts;
    }

    public void setListTypeCharts(List<TypeChart> listTypeCharts) {
        this.listTypeCharts = listTypeCharts;
    }

    public List<TypePlace> getListTypePlaces() {
        return listTypePlaces;
    }

    public void setListTypePlaces(List<TypePlace> listTypePlaces) {
        this.listTypePlaces = listTypePlaces;
    }

    public TypePlace getSelectedTypePlace() {
        return selectedTypePlace;
    }

    public void setSelectedTypePlace(TypePlace selectedTypePlace) {
        this.selectedTypePlace = selectedTypePlace;
    }

    public List<TypeTime> getListTypeTimes() {
        return listTypeTimes;
    }

    public void setListTypeTimes(List<TypeTime> listTypeTimes) {
        this.listTypeTimes = listTypeTimes;
    }

    public TypeTime getSelectedTypeTime() {
        return selectedTypeTime;
    }

    public void setSelectedTypeTime(TypeTime selectedTypeTime) {
        this.selectedTypeTime = selectedTypeTime;
    }

    public String getContentToDisplay() {
        return contentToDisplay;
    }

    public void setContentToDisplay(String contentToDisplay) {
        this.contentToDisplay = contentToDisplay;
    }

    public boolean isFilterContent() {
        return filterContent;
    }

    public void setFilterContent(boolean filterContent) {
        this.filterContent = filterContent;
    }

    public boolean isFilterTipeZone() {
        return filterTipeZone;
    }

    public void setFilterTipeZone(boolean filterTipeZone) {
        this.filterTipeZone = filterTipeZone;
    }

    public TypeFilter getSelectedTypeFilter() {
        return selectedTypeFilter;
    }

    public void setSelectedTypeFilter(TypeFilter selectedTypeFilter) {
        this.selectedTypeFilter = selectedTypeFilter;
    }

    public List<TypeFilter> getListTypeFilters() {
        return listTypeFilters;
    }

    public void setListTypeFilters(List<TypeFilter> listTypeFilters) {
        this.listTypeFilters = listTypeFilters;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private void loadContents() {
        try {
            listContents = contentEJB.listContent();
        } catch (GDMEJBException ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCities() {
        try {
            listCities = cityEJB.listCity(4);
        } catch (GDMEJBException ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadLocations() {
        try {
            listLocations = locationEJB.listLocation();
        } catch (GDMEJBException ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTypePlaces() {
        try {
            listTypePlaces = typePlaceEJB.findAll();
        } catch (Exception ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTypecharts() {
        try {
            listTypeCharts = typeChartEJB.findAll();
        } catch (Exception ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTypeTimes() {
        try {
            listTypeTimes = typeTimeEJB.findAll();
        } catch (Exception ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTypeFilters() {
        try {
            listTypeFilters = typeFilterEJB.findAll();
        } catch (Exception ex) {
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //  Initializes the values for the content PickLists
    public void initPLContentent() {
        loadContents();
        selectedContents = new ArrayList<>();
        dualModelContents = new DualListModel<>(listContents, selectedContents);
    }

    //  Initializes the values for the content PickLists
    public void initPLCities() {
        loadCities();
        selectedCities = new ArrayList<>();
        dualModelCities = new DualListModel<>(listCities, selectedCities);
    }

    //  Initializes the values for the content PickLists
    public void initPLLocation() {
        loadLocations();
        selectedLocations = new ArrayList<>();
        dualModelLocations = new DualListModel<>(listLocations, selectedLocations);
    }

    public String onFlowProcess(FlowEvent event) {
        if (skipWizard) {
            skipWizard = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void createChartPanel() {
        try {
            FilterSetting filterSetting = new FilterSetting();
            filterSetting.setIdTypeTime(selectedTypeTime);
            filterSetting.setIdTypePlace(selectedTypePlace);
            filterSetting.setIdTypeFilter(selectedTypeFilter);
            filterSetting.setAllContents(!filterContent);
            filterSetting.setAllDevices(true);
            filterSetting.setAllPlaces(!filterTipeZone);

            newChartPanel.setIdFilterSetting(filterSetting);
            newChartPanel.setColumnOrder(1);
            newChartPanel.setRowOrder(1);
            newChartPanel.setIdTypeChart(selectedTypeChart);
            newChartPanel.setIdUser(gdmSession.getUser().getIdUser());

            chartPanelEJB.create(newChartPanel);

            if (selectedTypeTime.getIdTypeTime() == 3) {
                filterSetting.setFromDate(fromDate);
                filterSetting.setToDate(toDate);
            }

            if (filterContent) {
                List<Content> contents = dualModelContents.getTarget();

                if (contents != null && !contents.isEmpty()) {
                    for (Content content : contents) {
                        FilteredContent filteredContent = new FilteredContent(new FilteredContentPK(newChartPanel.getIdFilterSetting(), content));
                        filteredContentEJB.create(filteredContent);
                    }
                    System.out.println("Filtered content saved");
                } else {
                    System.err.println("contenido nulo o vacio");
                }

            }

            if (filterTipeZone) {
                if (contentToDisplay.equals("1")) {
                    List<Location> locations = dualModelLocations.getTarget();

                    if (locations != null && !locations.isEmpty()) {
                        for (Location location : locations) {
                            filteredPlaceEJB.insertFilteredPlace(filterSetting.getIdFilterSetting(), location.getIdLocation(), null);
                        }
                        System.out.println("Filtered place location saved");
                    } else {
                        System.err.println("filtered place nulo o vacio");
                    }
                } else {
                    List<City> cities = dualModelCities.getTarget();

                    if (cities != null && !cities.isEmpty()) {
                        for (City city : cities) {
                            filteredPlaceEJB.insertFilteredPlace(filterSetting.getIdFilterSetting(), null, city.getIdCity());
                        }
                        System.out.println("Filtered place city saved");
                    } else {
                        System.err.println("filtered city nulo o vacio");
                    }
                }
            }

            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("id_chart_panel", newChartPanel.getIdChartPanel());
            System.err.println("!!!!!!!!!!!!!!!Chart panel id: " + newChartPanel.getIdChartPanel());
            PrimeFaces.current().dialog().closeDynamic("createChartPanel");
        } catch (Exception ex) {
            Message.error("Error de registro", "Error al registrar panel. " + ex.getMessage());
            Logger.getLogger(CreateChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
