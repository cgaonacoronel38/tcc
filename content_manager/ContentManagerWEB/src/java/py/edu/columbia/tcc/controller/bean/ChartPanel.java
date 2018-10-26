/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.controller.bean;

/**
 *
 * @author tokio
 */
public class ChartPanel {
    Integer idChartPanel;
    private String title;
    private String typeChart;
    private String typeAudienceChart;
    private int row;
    private int column;
    private int pediod;
    private int device;
    private int content;
    private int dashbordType;
    private String dashboardID;
    private Object lineChartModel;

    public ChartPanel() {
    }

    public Integer getIdChartPanel() {
        return idChartPanel;
    }

    public void setIdChartPanel(Integer idChartPanel) {
        this.idChartPanel = idChartPanel;
    }

    public String getTypeAudienceChart() {
        return typeAudienceChart;
    }

    public void setTypeAudienceChart(String typeAudienceChart) {
        this.typeAudienceChart = typeAudienceChart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeChart() {
        return typeChart;
    }

    public void setTypeChart(String typeChart) {
        this.typeChart = typeChart;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPediod() {
        return pediod;
    }

    public void setPediod(int pediod) {
        this.pediod = pediod;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getDashbordType() {
        return dashbordType;
    }

    public void setDashbordType(int dashbordType) {
        this.dashbordType = dashbordType;
    }

    public String getDashboardID() {
        return dashboardID;
    }

    public void setDashboardID(String dashboardID) {
        this.dashboardID = dashboardID;
    }

    public void setLineChartModel(Object lineChartModel) {
        this.lineChartModel = lineChartModel;
    }

    public Object getLineChartModel() {
        return lineChartModel;
    }
}
