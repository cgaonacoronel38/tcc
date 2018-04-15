/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.edu.columbia.tcc.common;

import py.edu.columbia.tcc.exception.GDMException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.ejb.Stateless;

/**
 *
 * @author tid
 */
@Stateless
public class ZipFileServices {

    /**
     * Crea un archivo ZIP comprimiendo varios ficheros.
     *
     * Ejemplo: zipFileName =
     * '/home/adva/tmp/reportes-saldos-25062015131505.zip'
     *
     * entries[0] = '/home/adva/tmp/saldos-procard-25062015.csv' entries[1] =
     * '/home/adva/tmp/saldos-cuentas-gestion-25062015.csv' entries[1] =
     * '/home/adva/tmp/saldos-cuentas-activas-25062015.csv'
     *
     * @param zipFileName Nombre del archivo zip a crear [ruta + nombre].
     * @param entries Lista de ficheros a comprimir e incluir en el zip [ruta +
     * nombre].
     *
     * @throws GDMException
     */
    public void createZipFile(String zipFileName, String[] entries) throws GDMException {
        try {
            ZipFileUtil zip = new ZipFileUtil();
            zip.create(zipFileName);

            for (String e : entries) {
                zip.addEntryFile(e);
            }

            zip.close();
        } catch (Exception ex) {
            throw new GDMException("No se pudo crear el archivo zip.", ex);
        }
    }

    public void unZip(String routeZip, String destinationFolder) throws IOException, GDMException {

        try {
            ZipInputStream entryZip = new ZipInputStream(new FileInputStream(
                    routeZip));

            ZipEntry looseFile = null;

            while ((looseFile = entryZip.getNextEntry()) != null) {
                System.out.println("Archivo leido: " + looseFile.getName());
                File newFile = new File(destinationFolder + File.separator
                        + looseFile.getName());
                FileOutputStream out = new FileOutputStream(newFile);

                byte[] buffer = new byte[1024];
                int read;

                while ((read = entryZip.read(buffer, 0, buffer.length)) != -1) {
                    out.write(buffer, 0, read);
                }
                out.flush();
                out.close();
            }

            entryZip.close();

        } catch (FileNotFoundException ex) {
            throw new GDMException("No se pudo encontrar el archivo zip.", ex);
        } catch (IOException ex) {
            throw new GDMException("No se pudo descomprimir el archivo zip.", ex);
        }

    }
}
