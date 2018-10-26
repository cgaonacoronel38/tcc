/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.chartData;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import py.edu.columbia.tcc.model.contentHandler.Content;

/**
 *
 * @author tokio
 */
@Embeddable
public class FilteredContentPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_filter_setting")
    private FilterSetting idFilterSetting;
    
    @ManyToOne
    @JoinColumn(name = "id_content")
    private Content idContent;

    public FilteredContentPK() {
    }

    public FilteredContentPK(FilterSetting idFilterSetting, Content idContent) {
        this.idFilterSetting = idFilterSetting;
        this.idContent = idContent;
    }

    public FilterSetting getIdFilterSetting() {
        return idFilterSetting;
    }

    public void setIdFilterSetting(FilterSetting idFilterSetting) {
        this.idFilterSetting = idFilterSetting;
    }

    public Content getIdContent() {
        return idContent;
    }

    public void setIdContent(Content idContent) {
        this.idContent = idContent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFilterSetting.getIdFilterSetting();
        hash += (int) idContent.getIdContent();
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilteredContentPK)) {
            return false;
        }
        FilteredContentPK other = (FilteredContentPK) object;
        if (this.idFilterSetting != other.idFilterSetting) {
            return false;
        }
        if (this.idContent != other.idContent) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.FilteredContentPK[ idFilterSetting=" + idFilterSetting + ", idContent=" + idContent + " ]";
    }
    
}
