/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.model.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RemoteContentBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer idContent;
    private long idRemoteContent;
    private Date dateAdd;

    public RemoteContentBean() {
    }

    public RemoteContentBean(Integer idContent) {
        this.idContent = idContent;
    }

    public RemoteContentBean(Integer idContent, long idRemoteContent) {
        this.idContent = idContent;
        this.idRemoteContent = idRemoteContent;
    }

    public Integer getIdContent() {
        return idContent;
    }

    public void setIdContent(Integer idContent) {
        this.idContent = idContent;
    }

    public long getIdRemoteContent() {
        return idRemoteContent;
    }

    public void setIdRemoteContent(long idRemoteContent) {
        this.idRemoteContent = idRemoteContent;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    @Override
    public String toString() {
        return "edu.columbia.tcc.ejb.entity.Content[ idContent=" + idContent + " ]";
    }
    
}
