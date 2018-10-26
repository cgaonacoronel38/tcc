///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package py.edu.columbia.tcc.controller.view.utils;
//
//import java.util.List;
//import javax.inject.Inject;
//import org.primefaces.model.chart.Axis;
//import org.primefaces.model.chart.AxisType;
//import org.primefaces.model.chart.BarChartModel;
//import org.primefaces.model.chart.ChartSeries;
//import org.primefaces.model.chart.DateAxis;
//import org.primefaces.model.chart.LineChartModel;
//import org.primefaces.model.chart.LineChartSeries;
//import org.primefaces.model.chart.PieChartModel;
//import py.edu.columbia.tcc.ejb.jpa.content.AudienceFacade;
//import py.edu.columbia.tcc.model.bean.AudienceBean;
//
///**
// *
// * @author tokio
// */
//public class ChartModel {
//    @Inject
//    private static AudienceFacade audienceEJB;
//    
//    public static Object getChartModel(String typeChart, int typeTime) {
//        switch (typeChart) {
//            case "line":
//                LineChartModel lineModel = new LineChartModel();
//                for (long x = 1; x <= 3; x++) {
//                    LineChartSeries series1 = new LineChartSeries();
//                    series1.setLabel("Terminal " + x);
//
////            List<AudienceBean> audienceList = audienceEJB.audienceQuantityDeviceToday(x);
//                    List<AudienceBean> audienceList = audienceEJB.audienceQuantityDeviceWeek(x);
//                    if (audienceList != null) {
//                        for (AudienceBean audience : audienceList) {
//                            series1.set(audience.getReference(), audience.getQuantity());
//                        }
//                    }
//
//                    lineModel.addSeries(series1);
//                }
//
//                lineModel.setTitle("Linear Chart");
//                lineModel.setLegendPosition("e");
//
//                lineModel.setTitle("Zoom para detalles");
//                lineModel.setZoom(true);
//                lineModel.getAxis(AxisType.Y).setLabel("Audiencia");
//                DateAxis axis = new DateAxis("Fecha");
//                axis.setTickAngle(-50);
//
//                //http://www.jqplot.com/docs/files/plugins/jqplot-dateAxisRenderer-js.html
//                axis.setTickFormat("%A %d");
//
//                lineModel.getAxes().put(AxisType.X, axis);
//                return lineModel;
//            case "pie":
//                PieChartModel pieModel = new PieChartModel();
//                pieModel.setTitle("Pie Chart");
//                pieModel.setLegendPosition("e");
//
//                pieModel = new PieChartModel();
//
//                List<AudienceBean> audienceList = audienceEJB.audienceQuantityDeviceTodayGroup();
//                if (audienceList != null) {
//                    for (AudienceBean audience : audienceList) {
//                        pieModel.set(audience.getReference(), audience.getQuantity());
//                    }
//                }
//
////                pieModel.setTitle("Custom Pie");
//                pieModel.setLegendPosition("e");
//                pieModel.setFill(false);
//                pieModel.setShowDataLabels(true);
//                pieModel.setDiameter(150);
//                pieModel.setShadow(false);
//
//                return pieModel;
//            case "bar":
//                BarChartModel barModel = new BarChartModel();
//                for (long x = 1; x <= 3; x++) {
//                    List<AudienceBean> audienceList2 = audienceEJB.audienceQuantityDeviceToday(x);
//                    if (audienceList2 != null) {
//                        ChartSeries series = new ChartSeries();
//                        series.setLabel("Terminal " + x);
//                        for (AudienceBean audience : audienceList2) {
//                            series.set(audience.getReference(), audience.getQuantity());
//                        }
//                        barModel.addSeries(series);
//                    }
//                }
////                barModel.setTitle("Bar Chart");
//                barModel.setLegendPosition("ne");
//                barModel.setStacked(true);
//
//                DateAxis axisss = new DateAxis("Fecha");
//                axisss.setTickAngle(-50);
//                barModel.getAxes().put(AxisType.X, axisss);
//
//                Axis yAxisbm = barModel.getAxis(AxisType.Y);
//                yAxisbm.setLabel("Audience");
//                return barModel;
//        }
//        return null;
//    }
//
//    private LineChartModel initLinearModel() {
//        LineChartModel model = new LineChartModel();
//
//        for (long x = 1; x <= 3; x++) {
//            LineChartSeries series1 = new LineChartSeries();
//            series1.setLabel("Terminal " + x);
//
////            List<AudienceBean> audienceList = audienceEJB.audienceQuantityDeviceToday(x);
//            List<AudienceBean> audienceList = audienceEJB.audienceQuantityDeviceWeek(x);
//            if (audienceList != null) {
//                for (AudienceBean audience : audienceList) {
//                    series1.set(audience.getReference(), audience.getQuantity());
//                }
//            }
//
//            model.addSeries(series1);
//        }
//
//        return model;
//    }
//
//    private BarChartModel initBarModel() {
//        BarChartModel model = new BarChartModel();
//
//        ChartSeries boys = new ChartSeries();
//        boys.setLabel("Boys");
//        boys.set("2004", 120);
//        boys.set("2005", 100);
//        boys.set("2006", 44);
//        boys.set("2007", 150);
//        boys.set("2008", 25);
//
//        ChartSeries girls = new ChartSeries();
//        girls.setLabel("Girls");
//        girls.set("2004", 52);
//        girls.set("2005", 60);
//        girls.set("2006", 110);
//        girls.set("2007", 135);
//        girls.set("2008", 120);
//
//        model.addSeries(boys);
//        model.addSeries(girls);
//
//        return model;
//    }
//}
