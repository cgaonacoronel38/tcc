package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import py.edu.columbia.tcc.ejb.jpa.bean.AudienceDataChartInfo;
import py.edu.columbia.tcc.ejb.jpa.bean.DeviceStatusBean;
import py.edu.columbia.tcc.ejb.jpa.content.ChartPanelFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;

@ManagedBean
@ViewScoped
public class ChartPanelInfo implements Serializable {

    private AudienceDataChartInfo audienceInfo;
    
    @Inject
    private ChartPanelFacade chartPanelEJB;

    @PostConstruct
    public void init() {
        try {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            String idChartPanel = (String) sessionMap.get("id_chart_panel");
            sessionMap.remove("id_chart_panel");
            audienceInfo = chartPanelEJB.getAudienceDataInfo(Integer.valueOf(idChartPanel));
        } catch (GDMEJBException ex) {
            Logger.getLogger(ChartPanelInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AudienceDataChartInfo getAudienceInfo() {
        return audienceInfo;
    }

    public void setAudienceInfo(AudienceDataChartInfo audienceInfo) {
        this.audienceInfo = audienceInfo;
    }
}
