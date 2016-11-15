package pkg;


import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.data.category.DefaultCategoryDataset;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;


public class TAsk1 {
    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    private static JFrame frame;
    private static JLabel imageLabel;
    Socket sock = null;
    static Socket skt = null;
    DataOutputStream out = null;
    DataInputStream in = null;
     
    ServerSocket myServerSocket;
    static OutputStream os;
    static double[] rgb; 
   DefaultCategoryDataset dataset;
    static int[] array = new int[640];
    public static void main(String[] args) {
        TAsk1 app = new TAsk1();
        app.initGUI();
        app.SoketConnection(args);
          
    }
    
    public void SoketConnection(String[] args){
        try {
            System.out.println("Server listening on port : 3265");
            myServerSocket = new ServerSocket(3265);
            skt = myServerSocket.accept();   
            System.out.println("Connected");
            
            TAsk1.runMainLoop(args);

        } catch (IOException ex) {
            System.out.println("Error");
        }
       
    }
    
    private void initGUI(){
    
        frame = new JFrame("Camera");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        imageLabel = new JLabel();
        frame.add(imageLabel);
        frame.setVisible(true);   
    }
    
    private static void runMainLoop(String[] args){
        ImageProcessor imageProcessor = new ImageProcessor();
        Mat webcamMatImage = new Mat();
        Image tempImage;
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        
        if(capture.isOpened()){
            while(true){
                capture.read(webcamMatImage);
                if(!webcamMatImage.empty()){
                   
    for(int j=0;j<640;j++)
        webcamMatImage.put(240, j, new double[]{0, 0, 255});
    for(int j=0;j<640;j++)
        webcamMatImage.put(245, j, new double[]{0, 255, 0});
    for(int j=0;j<640;j++){
         rgb = webcamMatImage.get(243, j);
         
         System.out.println(rgb[1]);
         array[j] =(int) rgb[1];
         
    }
//   ArrayList<Double> l = new ArrayList<Double>(); 
//    for(int i=0;i<640;i++){
//        l.add(array[i]);
////    }
  
     
                    try {
                        os = skt.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(os);  
                        oos.writeObject(array);
                    } catch (IOException ex) {
                        
                    }
      
    
    tempImage = imageProcessor.toBufferedImage(webcamMatImage);
    ImageIcon imageIcon = new ImageIcon(tempImage,"Capture Video");
    imageLabel.setIcon(imageIcon);
    frame.pack();
    }
        else{
        System.out.println("--- Frame Not Captured ---");
        break;
    }       
        }
      }
        else{
        
            System.out.println("Could Not open Capture");
        }  
    }
    
    
       
      
}