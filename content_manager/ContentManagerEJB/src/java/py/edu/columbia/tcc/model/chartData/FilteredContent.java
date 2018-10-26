/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import py.edu.columbia.tcc.model.contentHandler.Content;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "filtered_content", catalog = "content_manager", schema = "chart_data")
@NamedQueries({
    @NamedQuery(name = "FilteredContent.findAll", query = "SELECT f FROM FilteredContent f")})
public class FilteredContent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FilteredContentPK filteredContentPK;
//    @JoinColumn(name = "id_filter_setting", referencedColumnName = "id_filter_setting", insertable = false, updatable = false)
//    @ManyToOne(optional = false, cascade = CascadeType.ALL)
//    private FilterSetting filterSetting;

    public FilteredContent() {
    }

    public FilteredContent(FilteredContentPK filteredContentPK) {
        this.filteredContentPK = filteredContentPK;
    }

    public FilteredContent(FilterSetting idFilterSetting, Content idContent) {
        this.filteredContentPK = new FilteredContentPK(idFilterSetting, idContent);
    }

    public FilteredContentPK getFilteredContentPK() {
        return filteredContentPK;
    }

    public void setFilteredContentPK(FilteredContentPK filteredContentPK) {
        this.filteredContentPK = filteredContentPK;
    }

//    public FilterSetting getFilterSetting() {
//        return filterSetting;
//    }
//
//    public void setFilterSetting(FilterSetting filterSetting) {
//        this.filterSetting = filterSetting;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filteredContentPK != null ? filteredContentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilteredContent)) {
            return false;
        }
        FilteredContent other = (FilteredContent) object;
        if ((this.filteredContentPK == null && other.filteredContentPK != null) || (this.filteredContentPK != null && !this.filteredContentPK.equals(other.filteredContentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.FilteredContent[ filteredContentPK=" + filteredContentPK + " ]";
    }
    
}
