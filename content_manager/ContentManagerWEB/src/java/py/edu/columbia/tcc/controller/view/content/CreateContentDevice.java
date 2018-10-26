package py.edu.columbia.tcc.controller.view.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;
import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.ejb.jpa.bean.ContentDeviceBean;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.DeviceContent;
import py.edu.columbia.tcc.model.contentHandler.DeviceContentPK;

@ManagedBean
@ViewScoped
public class CreateContentDevice implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(CreateContentDevice.class);

    @Inject
    private DeviceContentFacade deviceContentEJB;

    @Inject
    private DeviceFacade deviceEJB;

    private Integer idContent;
    private DualListModel<ContentDeviceBean> dualModelContentDevices;
    private List<ContentDeviceBean> targetContentDeviceList;
    private List<ContentDeviceBean> sourceContentDeviceList;

    private boolean skipWizard;

    @PostConstruct
    public void init() {
        try {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            idContent = (Integer) sessionMap.get("id_content");
            sessionMap.remove("id_content");
            initPLContentent(idContent);
        } catch (Exception ex) {
            log.error("Error al cargar datos create content device: " + ex.getMessage());
        }
    }

    public DualListModel<ContentDeviceBean> getDualModelContentDevices() {
        return dualModelContentDevices;
    }

    public void setDualModelContentDevices(DualListModel<ContentDeviceBean> dualModelContentDevices) {
        this.dualModelContentDevices = dualModelContentDevices;
    }

    public List<ContentDeviceBean> getTargetContentDeviceList() {
        return targetContentDeviceList;
    }

    public void setTargetContentDeviceList(List<ContentDeviceBean> targetContentDeviceList) {
        this.targetContentDeviceList = targetContentDeviceList;
    }

    public List<ContentDeviceBean> getSourceContentDeviceList() {
        return sourceContentDeviceList;
    }

    public void setSourceContentDeviceList(List<ContentDeviceBean> sourceContentDeviceList) {
        this.sourceContentDeviceList = sourceContentDeviceList;
    }

    private void loadContentDevice(Integer idContent) {
        try {
            sourceContentDeviceList = deviceContentEJB.getContentDevicefiltered(idContent);
        } catch (Exception ex) {
            log.error("Error al inicializar lista de dispositivos; " + ex.getMessage());
        }
    }

    //  Initializes the values for the content PickLists
    private void initPLContentent(Integer idContent) {
        loadContentDevice(idContent);
        targetContentDeviceList = new ArrayList<>();
        dualModelContentDevices = new DualListModel<>(sourceContentDeviceList, targetContentDeviceList);
    }

    public void createDeviceContent() {
        targetContentDeviceList = dualModelContentDevices.getTarget();

        try {
            for (ContentDeviceBean bean : targetContentDeviceList) {
                log.info("Id content: "+idContent+"; idDEvice: "+bean.getIdDevice());
                deviceContentEJB.createContentDevice(idContent, bean.getIdDevice(), new Date());
            }

            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO,
                    "Terminal añadida exitosamente",
                    "");
            PrimeFaces.current().dialog().closeDynamic("createContent");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(CreateContentDevice.class.getName()).log(Level.SEVERE, null, ex);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_WARN,
                    "La terminal no ha podido ser añadida",
                    "");
        }

    }
}
