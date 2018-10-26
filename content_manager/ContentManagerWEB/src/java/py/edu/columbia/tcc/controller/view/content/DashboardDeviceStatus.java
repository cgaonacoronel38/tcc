package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import py.edu.columbia.tcc.ejb.jpa.bean.DeviceStatusBean;
import py.edu.columbia.tcc.ejb.jpa.content.DevicePingFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;

@ManagedBean(name = "deviceStatus")
@ViewScoped
public class DashboardDeviceStatus implements Serializable {

    @Inject
    private DevicePingFacade devicePingEJB;

    private DashboardModel model;
    private List<DeviceStatusBean> listChartPanels;
    private DeviceStatusBean selectedDeviceStatusBean;

    @PostConstruct
    public void init() {
        initDBPanel();
    }

    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
    //////////

    public List<DeviceStatusBean> getListChartPanels() {
        return listChartPanels;
    }

    public void setListChartPanels(List<DeviceStatusBean> listChartPanels) {
        this.listChartPanels = listChartPanels;
    }

    public DeviceStatusBean getSelectedDeviceStatusBean() {
        return selectedDeviceStatusBean;
    }

    public void setSelectedDeviceStatusBean(DeviceStatusBean selectedDeviceStatusBean) {
        this.selectedDeviceStatusBean = selectedDeviceStatusBean;
    }

    public void reloadDBPanel(String db) {
        for (DeviceStatusBean chart : listChartPanels) {
            if (chart.getDashboardID().equals(db)) {
                try {
                    DeviceStatusBean device = devicePingEJB.listDeviceStatusByIdDevice(Integer.valueOf(db.replace("DB", "")));
                    chart.setServerDate(device.getServerDate());
                    chart.setDeviceDate(device.getDeviceDate());
                    chart.setCurrentAudienceQuantity(device.getCurrentAudienceQuantity());

                    int diferencia = (int) ((new Date().getTime() - device.getServerDate().getTime()) / 1000);
//                    System.out.println("Diferencia segndos: " + diferencia);
                    if (diferencia < 10) {
                        chart.setDeviceStatus("OK");
                        chart.setStatusClass("green");
                        chart.setIcon("fa fa-check-square-o");
                    } else if (diferencia < 20) {
                        chart.setDeviceStatus("Verificar");
                        chart.setStatusClass("yellow");
                        chart.setIcon("fa fa-info-circle");
                    } else {
                        chart.setDeviceStatus("Warning");
                        chart.setStatusClass("red");
                        chart.setIcon("fa fa-warning");
                    }
                    return;
                } catch (GDMEJBException ex) {
                    Logger.getLogger(DashboardDeviceStatus.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void initDBPanel() {
        try {
            List<DeviceStatusBean> listDeviceStatus = devicePingEJB.listDeviceStatus();
            if (listDeviceStatus == null || listDeviceStatus.isEmpty()) {
                System.err.println("Lista de dipositivos vacia");
            }

            model = new DefaultDashboardModel();
            listChartPanels = new ArrayList<>();
            DashboardColumn column1 = new DefaultDashboardColumn();
            DashboardColumn column2 = new DefaultDashboardColumn();
            DashboardColumn column3 = new DefaultDashboardColumn();
            DashboardColumn column4 = new DefaultDashboardColumn();

            for (DeviceStatusBean device : listDeviceStatus) {
                device.setTitle(device.getDevice());
                device.setDashboardID("DB" + device.getIdDevice());
                
//                
//                System.out.println("Device: "+device.getDevice());
//                System.out.println("Server date: "+device.getServerDate());

                int diferencia = (int) ((new Date().getTime() - device.getServerDate().getTime()) / 1000);
//                System.out.println("Diferencia segndos: " + diferencia);
                if (diferencia < 10) {
                    device.setDeviceStatus("OK");
                    device.setStatusClass("green");
                    device.setIcon("fa fa-check-square-o");
                } else if (diferencia < 20) {
                    device.setDeviceStatus("Verificar");
                    device.setStatusClass("yellow");
                    device.setIcon("fa fa-info-circle");
                } else {
                    device.setDeviceStatus("Warning");
                    device.setStatusClass("red");
                    device.setIcon("fa fa-warning");
                }

                listChartPanels.add(device);
            }

            int x = 1;
            for (DeviceStatusBean chart : listChartPanels) {
                switch (x) {
                    case 1:
                        column1.addWidget(chart.getDashboardID());
                        break;
                    case 2:
                        column2.addWidget(chart.getDashboardID());
                        break;
                    case 3:
                        column3.addWidget(chart.getDashboardID());
                        break;
                    case 4:
                        column4.addWidget(chart.getDashboardID());
                        x = 0;
                        break;
                }
                x++;
            }

            model.addColumn(column1);
            model.addColumn(column2);
            model.addColumn(column3);
            model.addColumn(column4);
        } catch (GDMEJBException ex) {
            Logger.getLogger(DashboardDeviceStatus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void viewModalDeviceInfo(DeviceStatusBean status) {
        System.out.println("\n\n\n-------------------\n");
        System.err.println("dispositivo: "+status.getDevice());
        System.err.println("georeferencia: "+status.getGeoreference());
        System.out.println("\n-------------------\n\n\n");
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("device_status", status);
        Map<String, Object> options = new HashMap<>();
        options.put("width", 400);
        options.put("height", 500);
        options.put("modal", true);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("modalDeviceInfo", options, null);
    }
}
