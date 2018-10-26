/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jdbc.content;

import py.edu.columbia.tcc.connection.jdbc.Postgres;
import py.edu.columbia.tcc.model.bean.AudienceBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tokio
 */
public class DeviceContentJDBCFacade {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DeviceContentJDBCFacade.class);

    private Connection conn = null;

    public String getContentForDevice(String idDevice) {
        String file = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select c.directory ");
            sb.append("from content_handler.device_content dc join content_handler.content c on dc.id_content = c.id_content ");
            sb.append("where dc.uuid = CAST(? AS uuid) ");
            sb.append("and dc.active is true ");
            sb.append("and dc.downloaded is false ");
            sb.append("limit 1 ");

            conn = Postgres.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
                ps.setString(1, idDevice);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    file = rs.getString(1);
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DeviceContentJDBCFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return file;
    }
    
    public void setProcessed(int idDevice, String file) {
        try {
            int idContent = getContentId(file);
            
            StringBuilder sb = new StringBuilder();
            sb.append("update content_handler.device_content  set active = false, processed = true where id_content = ? and id_device = ?");

            conn = Postgres.getConnection();
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            ps.setLong(1, idContent);
            ps.setLong(2, idDevice);
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DeviceContentJDBCFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void notifyDownload(Integer idContent, Long remoteIdContent, Date addDate) {
        try {
            
            StringBuilder sb = new StringBuilder();
            sb.append("update content_handler.device_content set id_remote_content= ?, remote_content_add_date = ? where id_content = ? and id_device = 1");

            conn = Postgres.getConnection();
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            ps.setLong(1, remoteIdContent);
            ps.setDate(2, new java.sql.Date(addDate.getTime()));
            ps.setInt(3, idContent);
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DeviceContentJDBCFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public int getContentId(String file) {
        int idContent = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select id_content ");
            sb.append("from content_handler.content ");
            sb.append("where path like ? ");

            conn = Postgres.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
                ps.setString(1, file);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    idContent = rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DeviceContentJDBCFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return idContent;
    }
}
