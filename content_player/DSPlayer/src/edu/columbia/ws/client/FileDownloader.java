/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.columbia.ws.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.columbia.tcc.ejb.bean.ContentAudienceBean;
import edu.columbia.tcc.ejb.bean.ContentBean;
import edu.columbia.tcc.ejb.bean.DeviceAudienceBean;
import edu.columbia.tcc.ejb.bean.DevicePingBean;
import edu.columbia.tcc.ejb.bean.ResponseStatusBean;
import edu.columbia.tcc.ejb.entity.Content;
import edu.columbia.tcc.ejb.facade.ContentFacade;
import edu.columbia.tcc.ejb.facade.SystemParamFacade;
import edu.columbia.tcc.utils.ZipFileServices;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileDownloader {
    private static final Logger log = LoggerFactory.getLogger(FileDownloader.class);
    private static final String RESOURSE_PATH = "/var/www/html/";

    private final SystemParamFacade sysParamEJB = new SystemParamFacade();
    private final ContentFacade contentEJB = new ContentFacade();
    
    private static Gson gson = new Gson();
    //private static Gson gson = new GsonBuilder().setDateFormat("U").create();

    public void downloadContent(String contentUUID) throws IOException {
        ClientConfig clientConfig = null;
        Client client = null;
        WebTarget webTarget = null;
        Invocation.Builder invocationBuilder = null;
        Response response = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int responseCode;
        String qualifiedDownloadFilePath = null;

        try {
            String baseURI = sysParamEJB.getParam("base_uri_content");
            
            clientConfig = new ClientConfig();
            clientConfig.register(MultiPartFeature.class);
            client = ClientBuilder.newClient(clientConfig);
            client.property("accept", MediaType.APPLICATION_OCTET_STREAM);
            webTarget = client.target(baseURI).path("download").path(contentUUID);

            invocationBuilder = webTarget.request();
            response = invocationBuilder.get();

            // get response code
            responseCode = response.getStatus();

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed with HTTP error code : " + responseCode);
            }

            // read response string
            inputStream = response.readEntity(InputStream.class);
            qualifiedDownloadFilePath = getResponseFileName(contentUUID);
            outputStream = new FileOutputStream(qualifiedDownloadFilePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // release resources, if any
            outputStream.close();
            response.close();
            client.close();
        }
        
        ZipFileServices.unZip(qualifiedDownloadFilePath);
    }
    
    public String verifiContent(){
        try {
            String deviceId = sysParamEJB.getParam("device_id");
            String baseURI = sysParamEJB.getParam("base_uri_content");
            
            ClientConfig config = new ClientConfig();
            config.register(JacksonJsonProvider.class);
            
            Client client = ClientBuilder.newClient(config);
            WebTarget webTarget = client.target(baseURI).path("get").path(deviceId);

            
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.get();
            
            if(response.getStatus() == 200){
                ContentBean contentBean = response.readEntity(ContentBean.class);

                if(contentBean != null){
                    Content content = new Content();
                    content.setIdCompany(BigInteger.valueOf(contentBean.getIdCompany()));
                    content.setIdRemoteContent(BigInteger.valueOf(contentBean.getIdContent()));
                    content.setName(contentBean.getName());
                    content.setDescription(contentBean.getDescription());
                    content.setUuid(contentBean.getUuid().toString());
                    
                    contentEJB.create(content);
                    System.out.println("Contenido descargado");
                    return content.getUuid();
                }
            }     
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void confirmDownload(String cotentId) {
        try {
            String baseURI = sysParamEJB.getParam("base_uri_content");
            
            ClientConfig config = new ClientConfig();
            config.register(JacksonJsonProvider.class);
            
            Client client = ClientBuilder.newClient(config);
            WebTarget webTarget = client.target(baseURI).path("comfirmDownload").path(cotentId);
            
            Invocation.Builder invocationBuilder = webTarget.request();
            Response response = invocationBuilder.get();
            
            System.out.println(response.getStatus());
            if(response.getStatus() == 200){
                System.out.println("Contenido actualizado");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getResponseFileName(String file) {
        return RESOURSE_PATH + file + ".zip";
    }
    
    public static void ping(UUID device, UUID currentContent, Integer audienceQuantity){
        DevicePingBean deviceBean = new DevicePingBean();
        deviceBean.setDevice(device);
        deviceBean.setCurrentContent(currentContent);
        deviceBean.setCurrentAudienceQuantity(audienceQuantity);
        deviceBean.setDeviceDate(new Date().getTime());
        String response = sendPost("http://localhost:9090/tcc/rest/deviceping/ping", gson.toJson(deviceBean));
        ResponseStatusBean responseBean = gson.fromJson(response, ResponseStatusBean.class);
        if(responseBean != null){
            System.err.println("Response status: "+responseBean.getResponseCode() + " | " + responseBean.getResponseDescription());
        } else {
            System.err.println("Response ping es nulo");
        }
    }
    
    public static void registerAudienceDevice(UUID device, Integer stayTime){
        DeviceAudienceBean deviceBean = new DeviceAudienceBean();
        deviceBean.setDevice(device);
        deviceBean.setDeviceDate(new Date().getTime());
        deviceBean.setRegistrationTime(new Date().getTime());
        deviceBean.setStayTime(stayTime);
        String response = sendPost("http://localhost:9090/tcc/rest/deviceaudience/register", gson.toJson(deviceBean));
        ResponseStatusBean responseBean = gson.fromJson(response, ResponseStatusBean.class);
        if(responseBean != null){
            System.err.println("Response status: "+responseBean.getResponseCode() + " | " + responseBean.getResponseDescription());
        } else {
            System.err.println("Response ping es nulo");
        }
    }
    
    public static void registerAudienceContent(UUID device, UUID content, Integer audienceQuantity){
        ContentAudienceBean contentBean = new ContentAudienceBean();
        contentBean.setContent(content);
        contentBean.setDevice(device);
        contentBean.setDeviceDate(new Date().getTime());
        contentBean.setRegistrationTime(new Date().getTime());
        contentBean.setFromTime(new Date().getTime());
        contentBean.setToTime(new Date().getTime());
        contentBean.setAudienceQuantity(audienceQuantity);
        String response = sendPost("http://localhost:9090/tcc/rest/contentaudience/register", gson.toJson(contentBean));
        ResponseStatusBean responseBean = gson.fromJson(response, ResponseStatusBean.class);
        if(responseBean != null){
            System.err.println("Response status: "+responseBean.getResponseCode() + " | " + responseBean.getResponseDescription());
        } else {
            System.err.println("Response ping es nulo");
        }
    }
    
    private static String sendPost(String _url, String _jsonData){
        String response = "";
        try {
            System.err.println("URL: "+_url);
            System.err.println("Data: "+_jsonData);
            
		URL url = new URL(_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		OutputStream os = conn.getOutputStream();
		os.write(_jsonData.getBytes());
		os.flush();

                System.err.println("HTTP code: "+conn.getResponseCode());
		if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output;
		while ((output = br.readLine()) != null) {
			response = response.concat(output);
		}
                System.err.println("Response http: "+response);

		conn.disconnect();

	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	 }
        return response;
    }
}
