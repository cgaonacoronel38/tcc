/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.ejb.jdbc.content;

import py.edu.columbia.tcc.connection.jdbc.Postgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.edu.columbia.tcc.model.chartData.DeviceStatus;

/**
 *
 * @author tokio
 */
public class DevicePingJDBCFacade {

    private Connection conn = null;

    public void registerPing(DeviceStatus deviceStatus) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO chart_data.device_status (id_device, server_hour, devicent_hour, audience_quantity) ");
            sb.append(" values(?,?,?,?) ");

            conn = Postgres.getConnection();
            PreparedStatement ps = conn.prepareStatement(sb.toString());
            ps.setLong(1, deviceStatus.getIdDevice().getIdDevice());
            ps.setDate(2, new java.sql.Date(deviceStatus.getServerHour().getTime()));
            ps.setDate(3, new java.sql.Date(deviceStatus.getDevicentHour().getTime()));
            ps.setShort(4, deviceStatus.getAudienceQuantity());
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException ex) {
            System.err.println(ex.getLocalizedMessage());
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AudienceJDBCFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
