/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.common;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tokio
 */
public class FTPTransferFile {

    String host = "192.168.30.224";
    int port = 22;
    String user = "root";
    String pass = "d4t4cor3";
    String filePath = "/procard/uploaded/";

    public void transfer(String fileName) {
        try {
            
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            //UserInfo ui = new UserInfo(pass, null);
            
            //session.setUserInfo(ui);
            session.setPassword(pass);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            
            ChannelSftp sftp = (ChannelSftp)session.openChannel("sftp");
            sftp.connect();
            
            sftp.cd(filePath);
            sftp.put(filePath+fileName,fileName);
            
            sftp.exit();
            sftp.disconnect();
            session.disconnect();
        } catch (JSchException ex) {
            Logger.getLogger(FTPTransferFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SftpException ex) {
            Logger.getLogger(FTPTransferFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    public void ftpTransfer(String fileName) {
        try {
            FTPClient ftpClient = new FTPClient();
            //ftpClient.connect(InetAddress.getByName("192.168.30.250"),22);
            ftpClient.connect(InetAddress.getByName("192.168.30.250"));
            ftpClient.login("datacore", "d4t4cor3");

            //Verificar conexión con el servidor.
            int reply = ftpClient.getReplyCode();

            System.out.println("Respuesta recibida de conexión FTP:" + reply);

            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conectado Satisfactoriamente");
                ftpClient.changeWorkingDirectory("/procard/uploaded/");//Cambiar directorio de trabajo
                System.out.println("Se cambió satisfactoriamente el directorio");

                //Activar que se envie cualquier tipo de archivo
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                BufferedInputStream buffIn = null;
                buffIn = new BufferedInputStream(new FileInputStream(filePath + fileName));//Ruta del archivo para enviar
                ftpClient.enterLocalPassiveMode();
                ftpClient.storeFile(filePath, buffIn);//Ruta completa de alojamiento en el FTP

                buffIn.close(); //Cerrar envio de arcivos al FTP
                ftpClient.logout(); //Cerrar sesión
                ftpClient.disconnect();//Desconectarse del servidor
            } else {
                System.out.println("Imposible conectarse al servidor");
            }

            //Verificar si se cambia de direcotirio de trabajo
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Algo malo sucedió");
        }
    }
*/
}
