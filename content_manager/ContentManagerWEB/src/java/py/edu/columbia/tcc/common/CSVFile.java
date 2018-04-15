/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.common;

import java.io.FileWriter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tokio
 */
public class CSVFile {
    private static final Logger log = LoggerFactory.getLogger(CSVFile.class);
    
    public static boolean create(String filePath, String fileName, String header, List<String> data, String companyName, String fromDate, String todate){
        try{
            FileWriter fwriter = new FileWriter(filePath+fileName);
            fwriter.write('\ufeff'); //si no se escribe esto, excel no muestra bien el texto con tildes
            if(companyName != null && !companyName.isEmpty()){
                fwriter.write(companyName+"\n");
            }
            if(fromDate != null && todate != null){
                fwriter.write(fromDate+"\n");
                fwriter.write(todate+"\n\n");
            }
            fwriter.write(header);
            for(String row : data){
                fwriter.write("\n"+row);
            }
            fwriter.flush();
            fwriter.close();
            return true;
        }catch (Exception ex){
            log.error("Error al generar reporte "+fileName+"; "+ex.getLocalizedMessage());
            return false;
        }
    }
}
