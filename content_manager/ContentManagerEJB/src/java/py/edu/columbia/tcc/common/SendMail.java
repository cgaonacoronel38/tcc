package py.edu.columbia.tcc.common;


import py.edu.columbia.tcc.exception.GDMEJBException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Clase que permite enviar correo electrónico con un adjunto, la funcionalidad
 * esta dado según el seteo en el archivo properties principal (dss.properties).
 *
 * OBS:: se deben crear parámetros de sistema.
 * 
 * @see MailUserConfirmSetup
 * @see Util
 */
public class SendMail {
    private static final Logger log = LoggerFactory.getLogger(SendMail.class);
    
    private static final String LOGS_PATH = "%s/%s";

    String hostAS400 = "LOCALHOST";
    //Util util;

    public SendMail() throws GDMEJBException {
        //util = new Util();
    }

    /**
     * Crea una sesión de correo electrónico y lo envía.
     *
     * @param msgBody Texto a incluir como parte del cuerpo del e-mail.
     * @param msgSubject Testo a incluir como asunto en el e-mail.
     * @param recipients Dirección de correo electrónico.
     * @param fileLog Indica si se incluira el archivo de log de errores, este parámetro debe modificarse para recibir un archivo.
     * @throws com.documenta.pmc.exception.PMCGeneralException
     */
    public void send(String msgBody, String msgSubject, String recipients, boolean fileLog) throws GDMEJBException {
        Properties props = new java.util.Properties();
        //String[] recipients = mailSetup.getTo().split(",");

        try {
            
            // Almacen de certificados
            //System.setProperty("javax.net.ssl.keyStore", "C:\\Alcides\\certificado\\cer-test-win.cer");
            //System.setProperty("javax.net.ssl.keyStore", "C:\\Alcides\\certificado\\documenta.com.py-cert.crt");
            //System.setProperty("javax.net.ssl.keyStore", "C:\\Alcides\\certificado\\cacert.crt");
            //System.setProperty("javax.net.ssl.keyStore", "C:\\Alcides\\certificado\\documenta.com.py.p12");
            System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");     
            
            //System.setProperty("javax.net.ssl.trustStore", "C:\\Alcides\\certificado\\cer-test-win.cer");
            //System.setProperty("javax.net.ssl.trustStore", "C:\\Alcides\\certificado\\documenta.com.py.p12");
            //System.setProperty("javax.net.ssl.trustStore", "C:\\Alcides\\certificado\\documenta.com.py-cert.crt");
            //System.setProperty("javax.net.ssl.trustStore", "C:\\Alcides\\certificado\\cacert.crt");
            //System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
            
            
            // Cabecera del mensaje
            log.info("Creando e-mail...");
            
            props.put("mail.debug", "false");
            props.put("mail.smtp.host", "192.168.30.5");
            props.put("mail.smtp.port", "25");
            
            // Si requiere autenticación
            props.setProperty("mail.smtp.auth", "true");

            // StartTLS
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.socketFactory.port", "25");    
            props.put("mail.smtp.socketFactory.fallback", "false");
            
            // Para conexiones SSL
            //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");            
            
            // Nombre del usuario
            props.setProperty("mail.smtp.user", "adva@documenta.com.py");
            
            
            Session session = Session.getDefaultInstance(props, null);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("adva@documenta.com.py"));
            msg.setRecipients(Message.RecipientType.TO, recipients);

            if(msgSubject != null && !msgSubject.trim().equals("")) {
                msg.setSubject(msgSubject);
            } else {
                msg.setSubject("INFO - PRD");
            }

            // Contenedor de partes del cuerpo del mensaje
            Multipart mp = new MimeMultipart();

            // Parte 1 - texto
            BodyPart msgBodyPart = new MimeBodyPart();
            msgBodyPart.setText(msgBody + getFooterMessage());
            mp.addBodyPart(msgBodyPart);
            log.info("Se agregó el texto al e-mail...");

            // Parte 2 - archivo
            //if(!mailSetup.getAttachFile().trim().toUpperCase().equals("NONE") && fileLog) {
                /*
                msgBodyPart = new MimeBodyPart();
                String fileName = getFileName();
                log.info("Archivo adjunto a agregar al e-mail, " + fileName);

                if(existsAttach(fileName)) {
                    DataSource source = new FileDataSource(fileName);
                    msgBodyPart.setDataHandler(new DataHandler(source));
                    msgBodyPart.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1).concat(".txt"));
                    mp.addBodyPart(msgBodyPart);
                    log.info("Se agregó el archivo adjunto " + fileName + " al e-mail estadístico...");
                } else {
                    log.info("El adjunto para el e-mail estadístico no existe...");
                }
                */
            //}

            // Agregando partes al mensaje
            msg.setContent(mp);

            // Enviando
            Transport t = session.getTransport("smtp");
            t.connect("adva@documenta.com.py", "documenta");
            t.sendMessage(msg, msg.getAllRecipients());
            
            //Transport.send(msg);
            log.info(String.format("Se envió correctamente el e-mail a: ", recipients));
        } catch (Exception ex) {
            throw new GDMEJBException("Error in sending e-mail for SAU.", ex);
        }
    }

    /*
    private InternetAddress[] getRecipients() throws AddressException {
        
        String[] recips = mailSetup.getTo().split(",");
        InternetAddress[] adds = new InternetAddress[recips.length];
        for(int i = 0; i < recips.length; i++) { 
            adds[i] = new InternetAddress(recips[i].trim());
        }
        
        return adds;
        
        return null;
    }
    */
    
    private String getFooterMessage() {
        StringBuilder msg = new StringBuilder("");
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        /*
        msg.append("\n\n----------------------------------------------------------------------------------------\n")
           .append("Correo creado por ").append(PMCVersion.productName).append(" ").append(PMCVersion.productVersion).append(" en fecha ").append(sdf.format(cal.getTime())).append(".\n")
           .append("Dpto. de T.I.D - Documenta S.A.\n")
           .append("Contiene información confidencial.");
        */
        
        return msg.toString();
    }

    /*
    private String getFileName() {
        SimpleDateFormat sdf = null;
        String strDate = "";

        if(!mailSetup.getAttachDatePattern().trim().toUpperCase().equals("NONE")) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            Date date = cal.getTime();

            sdf = new SimpleDateFormat(mailSetup.getAttachDatePattern());
            strDate = sdf.format(date);
        }

        return String.format(LOGS_PATH, util.getApplicationLogsPath(), mailSetup.getAttachFile().concat(strDate));
    }

    private boolean existsAttach(String fileName) {
        boolean existsAttach = false;

        try {
            if (util.isOS("AS400") || util.isOS("OS400")) {
                // Crea un objeto AS400 para el servidor que contiene los archivos.
                AS400 as400 = new AS400(hostAS400);

                IFSFile f = new IFSFile(as400, fileName);
                existsAttach = f.exists();
                f = null;
            } else {
                File f = new File(fileName);
                existsAttach = f.exists();
                f = null;
            }

            return existsAttach;
        } catch (IOException ex) {
            log.error("Error de I/O al chequear si existe el archivo a adjuntar en e-mail estadístico.", ex);

            return false;
        }
    }
    */
    
    public static void main(String[] args) throws GDMEJBException, Exception {
        SendMail sendMail = new SendMail();
        sendMail.send("hola", "hola que tal", "alcidesalarcon@gmail.com", true);
    }
}

