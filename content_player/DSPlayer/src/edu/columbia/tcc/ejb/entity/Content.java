/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.columbia.tcc.ejb.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author tokio
 */
@Entity
@Table(name = "content", catalog = "dbplayer", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Content.findAll", query = "SELECT c FROM Content c")})
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_content")
    private Integer idContent;
    @Column(name = "id_remote_content")
    private BigInteger idRemoteContent;
    @Column(name = "id_company")
    private BigInteger idCompany;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "uuid", updatable = false)
    private String uuid;
    @Column(name = "active", insertable = false)
    private Boolean active;
    @Column(name = "downloaded", insertable = false)
    private Boolean downloaded;
    @Column(name = "confirmed", insertable = false)
    private Boolean confirmed;
    @Column(name = "date_add", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;

    public Content() {
    }

    public Content(Integer idContent) {
        this.idContent = idContent;
    }

    public Integer getIdContent() {
        return idContent;
    }

    public void setIdContent(Integer idContent) {
        this.idContent = idContent;
    }

    public BigInteger getIdRemoteContent() {
        return idRemoteContent;
    }

    public void setIdRemoteContent(BigInteger idRemoteContent) {
        this.idRemoteContent = idRemoteContent;
    }

    public BigInteger getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(BigInteger idCompany) {
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Boolean downloaded) {
        this.downloaded = downloaded;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
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
        return "edu.columbia.tcc.ejb.entity.Content[ idContent=" + idContent + " ]";
    }
    
}
