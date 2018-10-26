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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tokio
 */
public class AudienceJDBCFacade {

    private Connection conn = null;

    public void insertar(AudienceBean bean) {
//        try {
//            StringBuilder sb = new StringBuilder();
//            sb.append("insert into content_handler.audience(id_device, quantity, device_date, server_date) ");
//            sb.append("values(?,?,?,now()) ");
//
//            conn = Postgres.getConnection();
//            PreparedStatement ps = conn.prepareStatement(sb.toString());
//            ps.setLong(1, bean.getIdDevice());
//            ps.setLong(2, bean.getQuantity());
//            ps.setDate(3, new java.sql.Date(bean.getDeviceDate().getTime()));
//            ps.executeUpdate();
//            ps.close();
//            
//        } catch (SQLException ex) {
//            System.err.println(ex.getLocalizedMessage());
//        } finally {
//            if(conn != null){
//                try {
//                    conn.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(AudienceJDBCFacade.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
    }
}
