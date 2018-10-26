/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "type_chart", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "TypeChart.findAll", query = "SELECT t FROM TypeChart t")})
public class TypeChart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_type_chart")
    private Integer idTypeChart;
    @Size(max = 15)
    @Column(name = "name")
    private String name;
    @Size(max = 40)
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTypeChart")
    private List<ChartPanel> chartPanelList;

    public TypeChart() {
    }

    public TypeChart(Integer idTypeChart) {
        this.idTypeChart = idTypeChart;
    }

    public Integer getIdTypeChart() {
        return idTypeChart;
    }

    public void setIdTypeChart(Integer idTypeChart) {
        this.idTypeChart = idTypeChart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChartPanel> getChartPanelList() {
        return chartPanelList;
    }

    public void setChartPanelList(List<ChartPanel> chartPanelList) {
        this.chartPanelList = chartPanelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeChart != null ? idTypeChart.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeChart)) {
            return false;
        }
        TypeChart other = (TypeChart) object;
        if ((this.idTypeChart == null && other.idTypeChart != null) || (this.idTypeChart != null && !this.idTypeChart.equals(other.idTypeChart))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.TypeChart[ idTypeChart=" + idTypeChart + " ]";
    }
    
}
