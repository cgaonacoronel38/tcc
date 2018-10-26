package py.edu.columbia.tcc.controller.view.content;

import py.edu.columbia.tcc.controller.session.GDMSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.controller.view.utils.Message;
import py.edu.columbia.tcc.ejb.jpa.bean.ContentDeviceBean;
import py.edu.columbia.tcc.ejb.jpa.content.ContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.contentHandler.DeviceContent;
import py.edu.columbia.tcc.model.contentHandler.DeviceContentPK;

@ManagedBean
@ViewScoped
public class ViewContentDevice implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(ViewContentDevice.class);

    @Inject
    private GDMSession session;

    @Inject
    private DeviceContentFacade contentDeviceEJB;

    @Inject
    private DeviceFacade deviceEJB;

    @Inject
    ContentFacade contentEJB;

    private Integer idContent;
    private DeviceContent selectedDeviceContent;
    private List<ContentDeviceBean> deviceContentList;

    @PostConstruct
    public void init() {
        try {
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            idContent = (Integer) sessionMap.get("id_content");
            sessionMap.remove("id_content");
            loadContentDevice(idContent);
        } catch (Exception ex) {
            log.error("Error al cargar datos create content device: "+ex.getMessage());
        }
    }

    public DeviceContent getSelectedDeviceContent() {
        return selectedDeviceContent;
    }

    public void setSelectedDeviceContent(DeviceContent selectedDeviceContent) {
        this.selectedDeviceContent = selectedDeviceContent;
    }

    public List<ContentDeviceBean> getDeviceContentList() {
        return deviceContentList;
    }

    public void setDeviceContentList(List<ContentDeviceBean> deviceContentList) {
        this.deviceContentList = deviceContentList;
    }

    private void loadContentDevice(Integer idContent) {
        try {
            deviceContentList = contentDeviceEJB.getContentDevice(idContent);
        } catch (Exception ex) {

        }
    }
    
    public void inactiveDeviceContent(Integer idDevice){
        try {
            contentDeviceEJB.inactiveContentDevices(idContent, idDevice);
            loadContentDevice(idContent);
            Message.info("Operación exitosa!", "La terminal ha sido desactivada exitosamente!");
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(ViewContentDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void viewModalCreateContent() {
        Map<String, Object> options = new HashMap<>();
        options.put("width", 800);
        options.put("height", 380);
        options.put("modal", true);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        PrimeFaces.current().dialog().openDynamic("createContent", options, null);
    }

    public void onModalClosed() {
        Message.info("Registro exitoso", "El contenido ha sido agregado exitosamente!, refrescando datos...");
    }
}
