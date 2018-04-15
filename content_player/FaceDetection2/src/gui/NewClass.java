/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author tokio
 */
public class NewClass {

    public void capturar() throws InterruptedException {
        //System.loadLibrary("opencv_java244");
        //String url = NewClass.class.getResource("haarcascade_fullbody.xml").getPath().substring(1);
        //System.out.println(url);
        CascadeClassifier faceDetector = new CascadeClassifier("/home/tokio/Escritorio/FaceDetection2/haarcascade_upperbody.xml");

        VideoCapture webSource = null;
        Mat frame = new Mat();
        MatOfByte mem = new MatOfByte();
        MatOfRect faceDetections = new MatOfRect();


        if (webSource.grab()) {
            try {
                webSource.retrieve(frame);
                faceDetector.detectMultiScale(frame, faceDetections);

                /*Mat frame = new Mat();
                cap.retrieve(frame);
                Imgcodecs.imwrite("cek.jpg", frame);
                cap.release();
                Mat resim = Imgcodecs.imread("cek.jpg");
                MatOfRect rect = new MatOfRect();
                cascadeClassifier.detectMultiScale(resim, rect);
                Scalar renk = new Scalar(255, 0, 0);
                for (Rect dik : rect.toArray()) {
                    Imgproc.rectangle(resim, new Point(dik.x, dik.y), new Point(dik.x + dik.width, dik.y + dik.height), renk);
                }
                System.out.println(rect.height()); 
                Imgcodecs.imwrite("dene.jpg", resim);
            }*/
                for (Rect rect : faceDetections.toArray()) {
                    
                    Imgproc.circle(frame, new Point(rect.x + rect.width / 2, rect.y + rect.height / 2), 5 + rect.width / 2, new Scalar(0, 255, 0));
                   
                }
            } catch (Exception ex) {
                System.out.println("Error");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        NewClass clase = new NewClass();
        clase.capturar();
    }

}
