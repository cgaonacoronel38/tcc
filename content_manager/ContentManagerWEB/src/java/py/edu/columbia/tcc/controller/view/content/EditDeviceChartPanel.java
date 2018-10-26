package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.controller.view.utils.Message;
import py.edu.columbia.tcc.ejb.jpa.content.ChartPanelFacade;
import py.edu.columbia.tcc.ejb.jpa.content.TypeChartFacade;
import py.edu.columbia.tcc.ejb.jpa.content.TypeTimeFacade;
import py.edu.columbia.tcc.model.chartData.ChartPanel;
import py.edu.columbia.tcc.model.chartData.FilterSetting;
import py.edu.columbia.tcc.model.chartData.TypeChart;
import py.edu.columbia.tcc.model.chartData.TypeTime;

@ManagedBean
@ViewScoped
public class EditDeviceChartPanel implements Serializable {

    @Inject
    private TypeChartFacade typeChartEJB;

    @Inject
    private TypeTimeFacade typeTimeEJB;

    @Inject
    private ChartPanelFacade chartPanelEJB;

    @Inject
    private GDMSession gdmSession;

    private List<TypeChart> listTypeCharts;
    private List<TypeTime> listTypeTimes;

    @Inject
    @New
    private TypeChart selectedTypeChart;
    @Inject
    @New
    private TypeTime selectedTypeTime;

    @Inject
    @New
    private ChartPanel selectedChartPanel;

    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Integer idChartPanel = Integer.valueOf((String) sessionMap.get("id_chart_panel"));
        sessionMap.remove("id_chart_panel");
        System.out.println("Id chart panel = "+idChartPanel);
        
        selectedChartPanel = chartPanelEJB.find(idChartPanel);
        selectedTypeChart = selectedChartPanel.getIdTypeChart();
        selectedTypeTime = selectedChartPanel.getIdFilterSetting().getIdTypeTime();

        loadTypecharts();
        loadTypeTimes();
    }

    public ChartPanel getSelectedChartPanel() {
        return selectedChartPanel;
    }

    public void setSelectedChartPanel(ChartPanel selectedChartPanel) {
        this.selectedChartPanel = selectedChartPanel;
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

    private void loadTypecharts() {
        try {
            listTypeCharts = typeChartEJB.findAll();
        } catch (Exception ex) {
            Logger.getLogger(EditDeviceChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTypeTimes() {
        try {
            listTypeTimes = typeTimeEJB.findAll();
        } catch (Exception ex) {
            Logger.getLogger(EditDeviceChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editChartPanel() {
        try {
            selectedChartPanel.setIdTypeChart(selectedTypeChart);
            FilterSetting setting = selectedChartPanel.getIdFilterSetting();
            setting.setIdTypeTime(selectedTypeTime);
            selectedChartPanel.setIdFilterSetting(setting);
            chartPanelEJB.edit(selectedChartPanel);
            System.err.println("!!!!!!!!!!!!!!!Chart panel id: " + selectedChartPanel.getIdChartPanel());
            
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            sessionMap.put("id_chart_panel", selectedChartPanel.getIdChartPanel());
            
            PrimeFaces.current().dialog().closeDynamic("editDeviceChartPanel");
        } catch (Exception ex) {
            Message.error("Error de registro", "Error al registrar panel. " + ex.getMessage());
            Logger.getLogger(EditDeviceChartPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
