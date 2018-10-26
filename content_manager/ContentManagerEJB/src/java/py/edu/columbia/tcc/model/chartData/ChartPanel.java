/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "chart_panel", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "ChartPanel.findAll", query = "SELECT c FROM ChartPanel c")})
public class ChartPanel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chart_panel")
    private Integer idChartPanel;
    @Column(name = "uuid", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID uuid;
    @Column(name = "id_user")
    private long idUser;
    @Size(max = 60)
    @Column(name = "title")
    private String title;
    @Column(name = "row_order")
    private int rowOrder;
    @Column(name = "column_order")
    private int columnOrder;
    @JoinColumn(name = "id_filter_setting", referencedColumnName = "id_filter_setting")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private FilterSetting idFilterSetting;
    @JoinColumn(name = "id_type_chart", referencedColumnName = "id_type_chart")
    @ManyToOne(optional = false)
    private TypeChart idTypeChart;

    public ChartPanel() {
    }

    public ChartPanel(Integer idChartPanel) {
        this.idChartPanel = idChartPanel;
    }

    public ChartPanel(Integer idChartPanel, UUID uuid, long idUser, int rowOrder, int columnOrder) {
        this.idChartPanel = idChartPanel;
        this.uuid = uuid;
        this.idUser = idUser;
        this.rowOrder = rowOrder;
        this.columnOrder = columnOrder;
    }

    public Integer getIdChartPanel() {
        return idChartPanel;
    }

    public void setIdChartPanel(Integer idChartPanel) {
        this.idChartPanel = idChartPanel;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRowOrder() {
        return rowOrder;
    }

    public void setRowOrder(int rowOrder) {
        this.rowOrder = rowOrder;
    }

    public int getColumnOrder() {
        return columnOrder;
    }

    public void setColumnOrder(int columnOrder) {
        this.columnOrder = columnOrder;
    }

    public FilterSetting getIdFilterSetting() {
        return idFilterSetting;
    }

    public void setIdFilterSetting(FilterSetting idFilterSetting) {
        this.idFilterSetting = idFilterSetting;
    }

    public TypeChart getIdTypeChart() {
        return idTypeChart;
    }

    public void setIdTypeChart(TypeChart idTypeChart) {
        this.idTypeChart = idTypeChart;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChartPanel != null ? idChartPanel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChartPanel)) {
            return false;
        }
        ChartPanel other = (ChartPanel) object;
        if ((this.idChartPanel == null && other.idChartPanel != null) || (this.idChartPanel != null && !this.idChartPanel.equals(other.idChartPanel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.ChartPanel[ idChartPanel=" + idChartPanel + " ]";
    }
    
}
