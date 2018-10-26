//package py.edu.columbia.tcc.controller.view.content;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.logging.Level;
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//import javax.inject.Inject;
//import org.primefaces.model.chart.Axis;
//import org.primefaces.model.chart.AxisType;
//import org.primefaces.model.chart.DateAxis;
//import org.primefaces.model.chart.LineChartModel;
//import org.primefaces.model.chart.LineChartSeries;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import py.edu.columbia.tcc.controller.session.GDMSession;
//import py.edu.columbia.tcc.ejb.jdbc.content.AudienceJDBCFacade;
//import py.edu.columbia.tcc.model.bean.LineCharMonthBean;
//
//@ManagedBean
//@ViewScoped
//public class AudienceReport implements Serializable {
//
//    private static final Logger log = LoggerFactory.getLogger(AudienceReport.class);
//
//    @Inject
//    private GDMSession session;
//
//    @Inject
//    private AudienceJDBCFacade audienceEJB;
//
//    private LineChartModel deviceMonthAnimateModel;
//    private LineChartModel deviceHourAnimateModel;
//    private LineChartModel cityMonthAnimateModel;
//    private LineChartModel cityHourAnimateModel;
//    private List<Long> listDevice;
//    private List<Long> listCity;
//    private List<LineCharMonthBean> listLineCharMonthDevice;
//    private List<LineCharMonthBean> listLineCharHourDevice;
//    private List<LineCharMonthBean> listLineCharMonthCity;
//    private List<LineCharMonthBean> listLineCharHourCity;
//
//    public AudienceReport() {
//    }
//
//    @PostConstruct
//    public void init() {
//        try {
//            listDevice = null;//audienceEJB.getDevices();
//            listCity = null;//audienceEJB.getCities();
//            createDeviceMonthAnimatedModels();
//            createDeviceHourAnimatedModels();
//            createCityMonthAnimatedModels();
//            createCityHourAnimatedModels();
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(AudienceReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public LineChartModel getAnimatedModel1() {
//        return deviceMonthAnimateModel;
//    }
//
//    public void setAnimatedModel1(LineChartModel deviceMonthAnimateModel) {
//        this.deviceMonthAnimateModel = deviceMonthAnimateModel;
//    }
//
//    public List<Long> getListDevice() {
//        return listDevice;
//    }
//
//    public void setListDevice(List<Long> listDevice) {
//        this.listDevice = listDevice;
//    }
//
//    public List<Long> getListCity() {
//        return listCity;
//    }
//
//    public void setListCity(List<Long> listCity) {
//        this.listCity = listCity;
//    }
//
//    public List<LineCharMonthBean> getListLineCharMonthDevice() {
//        return listLineCharMonthDevice;
//    }
//
//    public void setListLineCharMonthDevice(List<LineCharMonthBean> listLineCharMonthDevice) {
//        this.listLineCharMonthDevice = listLineCharMonthDevice;
//    }
//
//    public List<LineCharMonthBean> getListLineCharMonthCity() {
//        return listLineCharMonthCity;
//    }
//
//    public void setListLineCharMonthCity(List<LineCharMonthBean> listLineCharMonthCity) {
//        this.listLineCharMonthCity = listLineCharMonthCity;
//    }
//
//    public LineChartModel getDeviceAnimateModel() {
//        return deviceMonthAnimateModel;
//    }
//
//    public void setDeviceAnimateModel(LineChartModel deviceMonthAnimateModel) {
//        this.deviceMonthAnimateModel = deviceMonthAnimateModel;
//    }
//
//    public LineChartModel getCityAnimateModel() {
//        return cityMonthAnimateModel;
//    }
//
//    public void setCityAnimateModel(LineChartModel cityMonthAnimateModel) {
//        this.cityMonthAnimateModel = cityMonthAnimateModel;
//    }
//
//    public LineChartModel getDeviceMonthAnimateModel() {
//        return deviceMonthAnimateModel;
//    }
//
//    public void setDeviceMonthAnimateModel(LineChartModel deviceMonthAnimateModel) {
//        this.deviceMonthAnimateModel = deviceMonthAnimateModel;
//    }
//
//    public LineChartModel getDeviceHourAnimateModel() {
//        return deviceHourAnimateModel;
//    }
//
//    public void setDeviceHourAnimateModel(LineChartModel deviceHourAnimateModel) {
//        this.deviceHourAnimateModel = deviceHourAnimateModel;
//    }
//
//    public LineChartModel getCityMonthAnimateModel() {
//        return cityMonthAnimateModel;
//    }
//
//    public void setCityMonthAnimateModel(LineChartModel cityMonthAnimateModel) {
//        this.cityMonthAnimateModel = cityMonthAnimateModel;
//    }
//
//    public LineChartModel getCityHourAnimateModel() {
//        return cityHourAnimateModel;
//    }
//
//    public void setCityHourAnimateModel(LineChartModel cityHourAnimateModel) {
//        this.cityHourAnimateModel = cityHourAnimateModel;
//    }
//
//    public List<LineCharMonthBean> getListLineCharHourDevice() {
//        return listLineCharHourDevice;
//    }
//
//    public void setListLineCharHourDevice(List<LineCharMonthBean> listLineCharHourDevice) {
//        this.listLineCharHourDevice = listLineCharHourDevice;
//    }
//
//    public List<LineCharMonthBean> getListLineCharHourCity() {
//        return listLineCharHourCity;
//    }
//
//    public void setListLineCharHourCity(List<LineCharMonthBean> listLineCharHourCity) {
//        this.listLineCharHourCity = listLineCharHourCity;
//    }
//
//    private void createDeviceMonthAnimatedModels() {
//        deviceMonthAnimateModel = initDeviceMonthLinearModel();
//        deviceMonthAnimateModel.setTitle("Audiencia por dispositivos - Meses");
//        deviceMonthAnimateModel.setAnimate(true);
//        deviceMonthAnimateModel.setLegendPosition("ne");
//        deviceMonthAnimateModel.getAxis(AxisType.Y).setLabel("Audiencia");
//        DateAxis axis = new DateAxis("Meses");
//        axis.setTickAngle(-50);
//        axis.setMax("2016-11-01");
//        axis.setTickFormat("%b %y");
//        deviceMonthAnimateModel.getAxes().put(AxisType.X, axis);
//    }
//    
//    private void createDeviceHourAnimatedModels() {
//        deviceHourAnimateModel = initDeviceHourLinearModel();
//        deviceHourAnimateModel.setTitle("Audiencia por dispositivos");
//        deviceHourAnimateModel.setAnimate(true);
//        deviceHourAnimateModel.setLegendPosition("ne");
//        deviceHourAnimateModel.getAxis(AxisType.Y).setLabel("Audiencia");
//        DateAxis axis = new DateAxis("Horas");
//        axis.setMin("2016-11-15 00:00:00");
//        axis.setMax("2016-11-15 23:00:00");
//        axis.setTickFormat("%H");
//        deviceHourAnimateModel.getAxes().put(AxisType.X, axis);
//    }
//    
//    private void createCityMonthAnimatedModels() {
//        cityMonthAnimateModel = initCityMonthLinearModel();
//        cityMonthAnimateModel.setTitle("Audiencia por ciudades - Meses");
//        cityMonthAnimateModel.setAnimate(true);
//        cityMonthAnimateModel.setLegendPosition("ne");
//        cityMonthAnimateModel.getAxis(AxisType.Y).setLabel("Audiencia");
//        DateAxis axis = new DateAxis("Meses");
//        axis.setTickAngle(-50);
//        axis.setMax("2016-11-01");
//        axis.setTickFormat("%b %y");
//        cityMonthAnimateModel.getAxes().put(AxisType.X, axis);
//    }
//    
//    private void createCityHourAnimatedModels() {
//        cityHourAnimateModel = initCityHourLinearModel();
//        cityHourAnimateModel.setTitle("Audiencia por ciudad - Hoy");
//        cityHourAnimateModel.setAnimate(true);
//        cityHourAnimateModel.setLegendPosition("ne");
//        cityHourAnimateModel.getAxis(AxisType.Y).setLabel("Audiencia");
//        DateAxis axis = new DateAxis("Horas");
//        axis.setMin("2016-11-15 00:00:00");
//        axis.setMax("2016-11-15 23:00:00");
//        axis.setTickFormat("%H");
//        cityHourAnimateModel.getAxes().put(AxisType.X, axis);
//    }
//
//    private LineChartModel initDeviceMonthLinearModel() {
//        LineChartModel model = new LineChartModel();
//        
//        try {
//            for (Long device : listDevice) {
//                LineChartSeries series1 = new LineChartSeries();
//
//                listLineCharMonthDevice = null;//audienceEJB.getAudienceMothDevice(device);
//
//                series1.setLabel(listLineCharMonthDevice.get(0).getDevice());
//                
//                for(LineCharMonthBean bean : listLineCharMonthDevice){
//                    series1.set(bean.getMonth(), bean.getQuantity());
//                }
//                model.addSeries(series1);
//            }
//
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(AudienceReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return model;
//    }
//    
//    private LineChartModel initDeviceHourLinearModel() {
//        LineChartModel model = new LineChartModel();
//        
//        try {
//            for (Long device : listDevice) {
//                LineChartSeries series1 = new LineChartSeries();
//
//                listLineCharHourDevice = null;// audienceEJB.getAudienceHourDevice(device);
//                
//                if(listLineCharHourDevice != null){
//                    series1.setLabel(listLineCharHourDevice.get(0).getDevice());
//
//                    for(LineCharMonthBean bean : listLineCharHourDevice){
//                        series1.set(bean.getMonth(), bean.getQuantity());
//                    }
//                    model.addSeries(series1);
//                } 
//            }
//
//        } catch (Exception ex) {
//            log.info("Error listLineCharHourDevice: "+ex.getLocalizedMessage());
//            java.util.logging.Logger.getLogger(AudienceReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return model;
//    }
//    
//    private LineChartModel initCityMonthLinearModel() {
//        LineChartModel model = new LineChartModel();
//        
//        try {
//            for (Long city : listCity) {
//                LineChartSeries series1 = new LineChartSeries();
//
//                listLineCharMonthCity = null;//udienceEJB.getAudienceMothCity(city);
//
//                series1.setLabel(listLineCharMonthCity.get(0).getDevice());
//                
//                for(LineCharMonthBean bean : listLineCharMonthCity){
//                    series1.set(bean.getMonth(), bean.getQuantity());
//                }
//                model.addSeries(series1);
//            }
//
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(AudienceReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return model;
//    }
//    
//    private LineChartModel initCityHourLinearModel() {
//        LineChartModel model = new LineChartModel();
//        
//        try {
//            for (Long city : listCity) {
//                LineChartSeries series1 = new LineChartSeries();
//
//                listLineCharHourCity = null;//audienceEJB.getAudienceHourCity(city);
//                
//                if(listLineCharHourCity != null){
//                    series1.setLabel(listLineCharHourCity.get(0).getDevice());
//
//                    for(LineCharMonthBean bean : listLineCharHourCity){
//                        series1.set(bean.getMonth(), bean.getQuantity());
//                    }
//                    model.addSeries(series1);
//                }
//            }
//
//        } catch (Exception ex) {
//            log.info("Error listLineCharHourCity: "+ex.getLocalizedMessage());
//            java.util.logging.Logger.getLogger(AudienceReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return model;
//    }
//}
