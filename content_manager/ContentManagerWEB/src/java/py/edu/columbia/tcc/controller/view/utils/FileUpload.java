package py.edu.columbia.tcc.controller.view.utils;

import py.edu.columbia.tcc.common.MsgUtil;
import py.edu.columbia.tcc.common.SystemPaths;
import py.edu.columbia.tcc.controller.session.GDMSession;
import py.edu.columbia.tcc.ejb.jpa.content.ContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceContentFacade;
import py.edu.columbia.tcc.ejb.jpa.content.DeviceFacade;
import py.edu.columbia.tcc.exception.GDMEJBException;
import py.edu.columbia.tcc.model.content.Content;
import py.edu.columbia.tcc.model.content.Device;
import py.edu.columbia.tcc.model.content.DeviceContent;
import py.edu.columbia.tcc.model.content.DeviceContentPK;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.New;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import py.edu.columbia.tcc.model.content.City;

@ManagedBean
@ViewScoped
public class FileUpload implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(FileUpload.class);

    private StringBuilder sb = new StringBuilder();
    private String fileName = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    @Inject
    private GDMSession session;

    @Inject
    private ContentFacade contentEJB;

    @Inject
    private DeviceContentFacade deviceContentEJB;

    @Inject
    private DeviceFacade deviceEJB;

    @Inject
    @New
    private Content newContent;

    private Integer selectedContent;
    private Integer dcIdDevice;

    private List<Content> listContent;
    private List<Device> listDevice;
    private List<DeviceContent> listDeviceContent;

    @PostConstruct
    public void init() {
    }

    public List<Content> getListContent() {
        return listContent;
    }

    public void setListContent(List<Content> listContent) {
        this.listContent = listContent;
    }

    public Content getNewContent() {
        return newContent;
    }

    public void setNewContent(Content newContent) {
        this.newContent = newContent;
    }

    public Integer getDcIdDevice() {
        return dcIdDevice;
    }

    public void setDcIdDevice(Integer dcIdDevice) {
        this.dcIdDevice = dcIdDevice;
    }

    public Integer getSelectedContent() {
        return selectedContent;
    }

    public void setSelectedContent(Integer selectedContent) {
        this.selectedContent = selectedContent;
    }

    public List<Device> getListDevice() {
        return listDevice;
    }

    public void setListDevice(List<Device> listDevice) {
        this.listDevice = listDevice;
    }

    public List<DeviceContent> getListDeviceContent() {
        return listDeviceContent;
    }

    public void setListDeviceContent(List<DeviceContent> listDeviceContent) {
        this.listDeviceContent = listDeviceContent;
    }

    public void upload(FileUploadEvent event) {
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());

            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO,
                    "Archivo " + event.getFile().getFileName() + " subido exitosamente!",
                    "");
        } catch (IOException e) {
            log.error("Error al subir archivos en servicios de pagos " + e.getLocalizedMessage());
        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            File filepath = new File(getFilePath());
            
            if (existPath(filepath)) {
                String directory = filepath.getAbsolutePath() + "/" + fileName;
                OutputStream out = new FileOutputStream(new File(directory));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }

                in.close();
                out.flush();
                out.close();

                newContent.setName(fileName);
                newContent.setDirectory(directory);
                newContent.setIdCompany(session.getDefaultCompany().getIdCompany().longValue());
                contentEJB.create(newContent);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean existPath(File filePath) {
        if (!filePath.exists()) {
            return filePath.mkdirs();
        }
        return true;
    }

    public String getFilePath() {
        try {
            sb.append(SystemPaths.ROOT_COMPANY_DIRECTORY);
            sb.append(session.getDefaultCompany().getIdCompany());
            sb.append(SystemPaths.CONTENT_DIRECTORY);
            sb.append(sdf.format(Calendar.getInstance().getTime()));
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    public void updateContentList() {
        try {
            listContent = contentEJB.listContent();
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDeviceContentList() {
        try {
            log.info("listando contenidos de dispositivos");
            log.info("content " + selectedContent);
            listDeviceContent = deviceContentEJB.listDeviceContent(selectedContent);
            if (listDeviceContent == null) {
                log.info("Lista de contenidos par dispositivo nulo");
            } else {
                log.info("Elementos lsitados " + listDeviceContent.size());
            }
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void contentSelected(Integer content) {
        try {
            log.info("Contenido: " + content);
            selectedContent = content;
            listDevice = deviceEJB.listDevice(session.getDefaultCompany().getIdCompany());
        } catch (GDMEJBException ex) {
            java.util.logging.Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDeviceContent() {
        try {
            DeviceContent deviceContent = new DeviceContent();
            deviceContent.setDeviceContentPK(new DeviceContentPK(dcIdDevice, selectedContent));
            deviceContent.setActive(true);
            deviceContentEJB.create(deviceContent);
            MsgUtil.addMessageWithoutKey(FacesMessage.SEVERITY_INFO, "Inserción exitosa", "Dispositivo agregado correctamente!");
        } catch (ConstraintViolationException | GDMEJBException ex) {
            java.util.logging.Logger.getLogger(FileUpload.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
