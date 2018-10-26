package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

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
import py.edu.columbia.tcc.ejb.jpa.bean.AudienceDataChart;
import py.edu.columbia.tcc.ejb.jpa.content.AudienceDeviceFacade;
import py.edu.columbia.tcc.ejb.jpa.content.ChartPanelFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilterSettingFacade;
import py.edu.columbia.tcc.ejb.jpa.content.FilteredContentFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;

@ManagedBean(name = "deviceDashboard")
@ViewScoped
public class DeviceDashboard implements Serializable {

    @Inject
    private ChartPanelFacade chartPanelEJB;

    @Inject
    private AudienceDeviceFacade audienceEJB;

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
        addMessage(message);
    }

    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
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
        Map<String, Object> options = new HashMap<>();
        options.put("width", 800);
        options.put("height", 500);
        options.put("modal", true);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("createChartPanel", options, null);
    }

    public void editDeviceChartPanel(String idChartPanel) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("id_chart_panel", idChartPanel.replace("db", ""));
        Map<String, Object> options = new HashMap<>();
        options.put("width", 600);
        options.put("height", 200);
        options.put("modal", true);
        options.put("title", "Editar panel de graficos");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("editDeviceChartPanel", options, null);
    }

    public void viewDeviceChartPanelInfo(String idChartPanel) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("id_chart_panel", idChartPanel.replace("db", ""));
        Map<String, Object> options = new HashMap<>();
        options.put("width", 450);
        options.put("height", 220);
        options.put("modal", true);
        options.put("title", "Información del panel de graficos");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("modalChartPanelInfo", options, null);
    }

    public void onChartPanelClosed() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Integer idChartPanel = (Integer) sessionMap.get("id_chart_panel");
        sessionMap.remove("id_chart_panel");

        reloadChartPanel(idChartPanel, true);

        RequestContext.getCurrentInstance().update("@([id$=pgDBView])");
        Message.info("Registro exitoso", "El panel ha sido agregado exitosamente!, refrescando datos...");
    }

    public void onChartPanelEdited() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Integer idChartPanel = (Integer) sessionMap.get("id_chart_panel");
        sessionMap.remove("id_chart_panel");

        reloadChartPanel(idChartPanel, false);

        RequestContext.getCurrentInstance().update("@([id$=pgDBView])");
        Message.info("Edicion exitosa", "El panel ha sido editado exitosamente!");
    }

    public void loadChartPanels() {
        List<ChartPanel> list = new ArrayList<>();
        List<py.edu.columbia.tcc.model.chartData.ChartPanel> listPanel = chartPanelEJB.findAll();
        for (py.edu.columbia.tcc.model.chartData.ChartPanel cpanel : listPanel) {
            ChartPanel panel = new ChartPanel();
            panel.setDashboardID("db" + cpanel.getIdChartPanel().toString());
            panel.setTitle(cpanel.getTitle());
            panel.setTypeChart(cpanel.getIdTypeChart().getName());
            panel.setIdChartPanel(cpanel.getIdChartPanel());
            panel.setTypeAudienceChart(cpanel.getIdFilterSetting().getIdTypeFilter().getDescription());
            panel.setLineChartModel(getChartModel(panel.getTypeChart(), panel.getIdChartPanel(), true));//, cpanel.getIdFilterSetting().getIdTypeTime().getIdTypeTime();

            list.add(panel);
        }

        listChartPanels = list;
        addChartPanelOnDashboard();
    }

    private void addChartPanelOnDashboard() {
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        int x = 2;
        for (ChartPanel chart : listChartPanels) {
            if (x % 2 == 0) {
                column1.addWidget(chart.getDashboardID());
            } else {
                column2.addWidget(chart.getDashboardID());
            }
            x++;
        }

        model = new DefaultDashboardModel();
        model.addColumn(column1);
        model.addColumn(column2);
    }

    public void reloadChartPanelData() {
        for (ChartPanel chart : listChartPanels) {
//            if (chart.getIdChartPanel() == idChartPanel) {
            chart.setLineChartModel(getChartModel(chart.getTypeChart(), chart.getIdChartPanel(), false));
//            }
        }
    }

    private void reloadChartPanel(Integer idChartPanel, boolean addNew) {
        py.edu.columbia.tcc.model.chartData.ChartPanel chartp = chartPanelEJB.find(idChartPanel);

        if (addNew) {
            ChartPanel panel = new ChartPanel();
            panel.setDashboardID("db" + chartp.getIdChartPanel().toString());
            panel.setTitle(chartp.getTitle());
            panel.setTypeChart(chartp.getIdTypeChart().getName());
            panel.setIdChartPanel(chartp.getIdChartPanel());
            panel.setTypeAudienceChart(chartp.getIdFilterSetting().getIdTypeFilter().getDescription());
            panel.setLineChartModel(getChartModel(panel.getTypeChart(), panel.getIdChartPanel(), true));
            listChartPanels.add(panel);
            addChartPanelOnDashboard();
        } else {
            for (ChartPanel chart : listChartPanels) {
                if (chart.getIdChartPanel() == idChartPanel) {
//                    chart.setDashboardID("db" + chartp.getIdChartPanel().toString());
//                    chart.setTitle(chartp.getTitle());
                    chart.setTypeChart(chartp.getIdTypeChart().getName());
                    chart.setIdChartPanel(chartp.getIdChartPanel());
                    chart.setTypeAudienceChart(chartp.getIdFilterSetting().getIdTypeFilter().getDescription());
                    chart.setLineChartModel(getChartModel(chart.getTypeChart(), chart.getIdChartPanel(), true));
                }
            }
        }
        
    }

    public void deleteChart(String chartID) {
        try {
            Integer idChartPanel = Integer.valueOf(chartID.replace("db", ""));
            try {
                filterSettingEJB.deleteFilterSetting(idChartPanel);
            } catch (Exception e) {
                System.err.println("Error al eliminar filtersetting: " + e.getMessage());
                Logger.getLogger(DeviceDashboard.class.getName()).log(Level.SEVERE, null, e);
            }

            chartPanelEJB.deleteChartPanel(idChartPanel);

            loadChartPanels();
            Message.info("Operacion exitosa", "El panel ha sido removido exitosamente");
        } catch (ConstraintViolationException ex) {
            Logger.getLogger(DeviceDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DeviceDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Object getChartModel(String typeChart, Integer idChartPanel, boolean setAnimate) {
        try {
            List<AudienceDataChart> audienceList = null;
            switch (typeChart) {
                case "line":
                    LineChartModel lineModel = new LineChartModel();
//                lineModel.setTitle("Linear Chart");
                    lineModel.setLegendPosition("e");

                    LineChartSeries series1 = null;

                    audienceList = chartPanelEJB.getAudienceDataChart(idChartPanel);
                    if (audienceList != null) {
                        int identifier = 0;
                        for (AudienceDataChart audience : audienceList) {
                            if (identifier != audience.getIdentifier()) {
                                if (series1 != null) {
                                    lineModel.addSeries(series1);
                                }

                                identifier = audience.getIdentifier();
                                series1 = new LineChartSeries();
                                series1.setLabel(audience.getDescription());
                            }
                            if (series1 != null) {
                                series1.set(audience.getReference(), audience.getQuantity());
                            }
                        }
                        if (series1 != null) {
                            lineModel.addSeries(series1);
                        }
                    }

                    lineModel.setTitle("Zoom para detalles");
                    lineModel.setZoom(true);
                    lineModel.setAnimate(setAnimate);
                    lineModel.getAxis(AxisType.Y).setLabel("Audiencia");
                    DateAxis axis = new DateAxis("Fecha");
                    axis.setTickAngle(-50);

                    //http://www.jqplot.com/docs/files/plugins/jqplot-dateAxisRenderer-js.html
//                if (typeTime == 2) {
//                    axis.setTickFormat("%A %d");
//                }
                    lineModel.getAxes().put(AxisType.X, axis);
                    return lineModel;
                case "pie":
                    PieChartModel pieModel = new PieChartModel();

                    audienceList = chartPanelEJB.getAudienceDataChart(idChartPanel);
                    if (audienceList != null) {
                        for (AudienceDataChart audience : audienceList) {
                            ChartSeries series = new ChartSeries();
                            series.setLabel(audience.getReference());
                            pieModel.set(audience.getReference(), audience.getQuantity());
                        }
                    }

                    pieModel.setTitle("Titulo");
                    pieModel.setLegendPosition("e");
                    pieModel.setFill(false);
                    pieModel.setShowDataLabels(true);
                    pieModel.setDiameter(150);
                    pieModel.setShadow(false);
                    pieModel.setMouseoverHighlight(true);
                    pieModel.setShowDatatip(true);

                    return pieModel;
                case "bar":
                    BarChartModel barModel = new BarChartModel();
                    ChartSeries series = null;

                    audienceList = chartPanelEJB.getAudienceDataChart(idChartPanel);
                    if (audienceList != null) {
                        int identifier = 0;
                        for (AudienceDataChart audience : audienceList) {
                            if (identifier != audience.getIdentifier()) {
                                if (series != null) {
                                    barModel.addSeries(series);
                                }

                                identifier = audience.getIdentifier();
                                series = new ChartSeries();
                                series.setLabel(audience.getDescription());
                            }
                            if (series != null) {
                                series.set(audience.getReference(), audience.getQuantity());
                            }
                        }
                        if (series != null) {
                            barModel.addSeries(series);
                        }
                    }

                    barModel.setTitle("Bar Chart");
                    barModel.setLegendPosition("ne");
                    barModel.setStacked(true);
                    barModel.setAnimate(setAnimate);
                    barModel.setZoom(true);
                    barModel.setMouseoverHighlight(true);

                    DateAxis axisss = new DateAxis("Fecha");
                    axisss.setTickAngle(-50);
                    barModel.getAxes().put(AxisType.X, axisss);

//                    if (typeTime == 2) {
                    axisss.setTickFormat("%A %d");
//                    }
                    Axis yAxisbm = barModel.getAxis(AxisType.Y);
                    yAxisbm.setLabel("Audiencia");
                    return barModel;
            }
            return null;
        } catch (GDMEJBException ex) {
            System.err.println("Type chart: " + typeChart);
            System.err.println("id chart panel: " + idChartPanel);
            System.err.println("Error el generar grafico: " + ex.getMessage());
            Logger.getLogger(DeviceDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
