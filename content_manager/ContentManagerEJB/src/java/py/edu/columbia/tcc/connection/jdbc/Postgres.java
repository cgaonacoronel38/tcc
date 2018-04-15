/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.connection.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tokio
 */
public class Postgres {
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/content_manager";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER_NAME);

            Connection conn = null;
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Estamos conectados al: "
                    + conn.getMetaData().getDatabaseProductName()
                    + " "
                    + conn.getMetaData().getDatabaseProductVersion());

            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Postgres.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

    }
}
