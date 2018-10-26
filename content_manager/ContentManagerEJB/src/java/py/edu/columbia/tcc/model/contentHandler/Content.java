/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.contentHandler;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "content", catalog = "content_manager", schema = "content_handler")
@NamedQueries({
    @NamedQuery(name = "Content.findAll", query = "SELECT c FROM Content c")})
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_content")
    private Integer idContent;
    @Column(name = "id_company")
    private long idCompany;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "active")
    private boolean active;
    @Column(name = "directory")
    private String directory;
    @Column(name = "uuid", columnDefinition = "uuid", insertable = false, updatable = false)
    private UUID uuid;
    @Column(name = "duration")
    private int duration;
    @Column(name = "due_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<Playbacks> playbacksList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "content")
    private List<DeviceContent> deviceContentList;

    public Content() {
    }

    public Content(Integer idContent) {
        this.idContent = idContent;
    }

    public Content(Integer idContent, long idCompany, String name, boolean active, String directory, UUID uuid, int duration) {
        this.idContent = idContent;
        this.idCompany = idCompany;
        this.name = name;
        this.active = active;
        this.directory = directory;
        this.uuid = uuid;
        this.duration = duration;
    }

    public Integer getIdContent() {
        return idContent;
    }

    public void setIdContent(Integer idContent) {
        this.idContent = idContent;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<Playbacks> getPlaybacksList() {
        return playbacksList;
    }

    public void setPlaybacksList(List<Playbacks> playbacksList) {
        this.playbacksList = playbacksList;
    }

    public List<DeviceContent> getDeviceContentList() {
        return deviceContentList;
    }

    public void setDeviceContentList(List<DeviceContent> deviceContentList) {
        this.deviceContentList = deviceContentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContent != null ? idContent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Content)) {
            return false;
        }
        Content other = (Content) object;
        if ((this.idContent == null && other.idContent != null) || (this.idContent != null && !this.idContent.equals(other.idContent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "py.edu.columbia.tcc.model.contentHandler.Content[ idContent=" + idContent + " ]";
    }
    
}
