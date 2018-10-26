package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.PieChart;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.primefaces.PrimeFaces;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import py.edu.columbia.tcc.controller.bean.ChartPanel;
import py.edu.columbia.tcc.controller.view.utils.Message;
import py.edu.columbia.tcc.ejb.jpa.bean.ChartDeviceBean;
import py.edu.columbia.tcc.ejb.jpa.content.AudienceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.ChartPanelFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilterSettingFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilteredContentFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.bean.AudienceBean;

@ManagedBean(name = "dashboardView")
@ViewScoped
public class DashboardView implements Serializable {

    @Inject
    private ChartPanelFacade chartPanelEJB;

    @Inject
    private AudienceFacade audienceEJB;

    @Inject
    private FilteredContentFacade filteredContentEJB;

    @Inject
    private FilterSettingFacade filterSettingEJB;

    private DashboardModel model;
    private List<ChartPanel> listChartPanels;

    @PostConstruct
    public void init() {

        loadChartPanels();

    }

    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());
        System.out.println("Reordenando paneles");
        int x = 0;
        for (DashboardColumn dbcolum : model.getColumns()) {
            x++;
            int y = 0;
            for (String wg : dbcolum.getWidgets()) {
                y++;
                System.out.println("Wg: " + wg + " Columna: " + x + " Linea: " + y);
            }
        }

        addMessage(message);
    }

    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
        System.out.println("Cerrando paneles");
        addMessage(message);
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
        System.out.println("Moviendo paneles");
        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        //FacesContext.getCurrentInstance().addMessage(null, message);
        Message.info("Summary", "detail");
    }

    public DashboardModel getModel() {
        return model;
    }

    public List<ChartPanel> getListChartPanels() {
        return listChartPanels;
    }

    public void setListChartPanels(List<ChartPanel> listChartPanels) {
        this.listChartPanels = listChartPanels;
    }

    public void viewModalCreateChartPanel() {
        System.out.println("habriendo modal...");
        Map<String, Object> options = new HashMap<>();
        options.put("width", 800);
        options.put("height", 500);
        options.put("modal", true);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("createChartPanel", options, null);
        System.out.println("Modal desplegado...");
    }

    public void onChartPanelClosed() {
        loadChartPanels();
        Message.info("Registro exitoso", "El panel ha sido agregado exitosamente!");
    }

    public void loadChartPanels() {
        model = new DefaultDashboardModel();
        listChartPanels = new ArrayList<>();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();

        List<py.edu.columbia.tcc.model.chartData.ChartPanel> listPanel = chartPanelEJB.findAll();
        for (py.edu.columbia.tcc.model.chartData.ChartPanel cpanel : listPanel) {
            ChartPanel panel = new ChartPanel();
            panel.setDashboardID("db" + cpanel.getIdChartPanel().toString());
            panel.setTitle(cpanel.getTitle());
            panel.setLineChartModel(getChartModel(cpanel.getIdTypeChart().getName(),cpanel.getIdFilterSetting().getIdTypeTime().getIdTypeTime(),cpanel.getIdFilterSetting().getIdFilterSetting()));
            panel.setTypeChart(cpanel.getIdTypeChart().getName());
            listChartPanels.add(panel);
        }

        int x = 2;
        for (ChartPanel chart : listChartPanels) {
            if (x % 2 == 0) {
                column1.addWidget(chart.getDashboardID());
            } else {
                column2.addWidget(chart.getDashboardID());
            }
            x++;
        }

        model.addColumn(column1);
        model.addColumn(column2);
    }

    public void deleteChart(String chartID) {
        try {
            Integer idChartPanel = Integer.valueOf(chartID.replace("db", ""));
            try {
                filterSettingEJB.deleteFilterSetting(idChartPanel);
            } catch (Exception e) {
                System.err.println("Error al eliminar filtersetting: " + e.getMessage());
                Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, e);
            }

            chartPanelEJB.deleteChartPanel(idChartPanel);

            loadChartPanels();
            Message.info("Operacion exitosa", "El panel ha sido removido exitosamente");
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DashboardView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getChartModel(String typeChart, int typeTime, Integer filterSetting) {
        System.out.println("Type Chart: "+typeChart);
         System.out.println("Type time: "+typeTime);
          System.out.println("Filter Setting: "+filterSetting);
          
        List<ChartDeviceBean> listChartDevices = null;
        List<AudienceBean> audienceList = null;
        switch (typeChart) {
            case "line":
                LineChartModel lineModel = new LineChartModel();
//                lineModel.setTitle("Linear Chart");
                lineModel.setLegendPosition("e");

                listChartDevices = audienceEJB.getChartDevices(filterSetting);
                for (ChartDeviceBean bean : listChartDevices) {
                    LineChartSeries series1 = new LineChartSeries();
                    series1.setLabel(bean.getDescription());

                    switch(typeTime){
                        case 1:
                            audienceList = audienceEJB.audienceQuantityDeviceToday(bean.getIdDevice());
                            break;
                        case 2:
                            audienceList = audienceEJB.audienceQuantityDeviceWeek(bean.getIdDevice());
                            break;
                    }
                    
                    if (audienceList != null) {
                        for (AudienceBean audience : audienceList) {
                            series1.set(audience.getReference(), audience.getQuantity());
                        }
                    }

                    lineModel.addSeries(series1);
                }

                lineModel.setTitle("Zoom para detalles");
                lineModel.setZoom(true);
                lineModel.setAnimate(true);
                lineModel.getAxis(AxisType.Y).setLabel("Audiencia");
                DateAxis axis = new DateAxis("Fecha");
                axis.setTickAngle(-50);

                //http://www.jqplot.com/docs/files/plugins/jqplot-dateAxisRenderer-js.html
                if(typeTime == 2){
                    axis.setTickFormat("%A %d");
                }

                lineModel.getAxes().put(AxisType.X, axis);
                return lineModel;
            case "pie":
                PieChartModel pieModel = new PieChartModel();
                
                listChartDevices = audienceEJB.getChartDevices(filterSetting);
                for (ChartDeviceBean bean : listChartDevices) {
                    ChartSeries series = new ChartSeries();
                    series.setLabel(bean.getDescription());
                    
                    switch(typeTime){
                        case 1:
                            audienceList = audienceEJB.audienceQuantityDeviceTodayGroup();
                            break;
                        case 2:
                            audienceList = audienceEJB.audienceQuantityDeviceWeekGroup();
                            break;
                    }
                    
                    if (audienceList != null) {
                        for (AudienceBean audience : audienceList) {
                            pieModel.set(audience.getReference(), audience.getQuantity());
                        }
                    }
                }

                pieModel.setTitle(typeTime == 1 ? "Ultimas 24 hs" : "Ultima semana");
                pieModel.setLegendPosition("e");
                pieModel.setFill(false);
                pieModel.setShowDataLabels(true);
                pieModel.setDiameter(150);
                pieModel.setShadow(false);
                pieModel.setMouseoverHighlight(true);

                return pieModel;
            case "bar":
                BarChartModel barModel = new BarChartModel();                
                listChartDevices = audienceEJB.getChartDevices(filterSetting);
                for (ChartDeviceBean bean : listChartDevices) {
                    ChartSeries series = new ChartSeries();
                    series.setLabel(bean.getDescription());
                    
                    switch(typeTime){
                        case 1:
                            audienceList = audienceEJB.audienceQuantityDeviceToday(bean.getIdDevice());
                            break;
                        case 2:
                            audienceList = audienceEJB.audienceQuantityDeviceWeek(bean.getIdDevice());
                            break;
                    }
                    
                    if (audienceList != null) {
                        for (AudienceBean audience : audienceList) {
                            series.set(audience.getReference(), audience.getQuantity());
                        }
                    }

                    barModel.addSeries(series);
                }
                
//                barModel.setTitle("Bar Chart");
                barModel.setLegendPosition("ne");
                barModel.setStacked(true);
                barModel.setAnimate(true);
                barModel.setZoom(true);
                barModel.setMouseoverHighlight(true);

                DateAxis axisss = new DateAxis("Fecha");
                axisss.setTickAngle(-50);
                barModel.getAxes().put(AxisType.X, axisss);
                
                if(typeTime == 2){
                    axisss.setTickFormat("%A %d");
                }

                Axis yAxisbm = barModel.getAxis(AxisType.Y);
                yAxisbm.setLabel("Audiencia");
                return barModel;
        }
        return null;
    }
}
