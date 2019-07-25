/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obtencioncurva;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author IsraelPC
 */
public class DatosCapturarController implements Initializable {

    @FXML
    private TextField txt_grados;
    @FXML
    private TextField txt_irradiancia;
    @FXML
    private Button btn_abrirArchivo;
    @FXML
    private Pane paneGrafica;
    
    File selectedFile = null;
    @FXML
    private Pane pane;
    @FXML
    private Button btn_graficar;
    @FXML
    private Button btn_guardarGrafica;
    @FXML
    private TextField txt_titulo;
    @FXML
    private Label label_factorF;
    @FXML
    private Label label_pmax;
    @FXML
    private Label lable_eficiencia;
    @FXML
    private Label label_pmaxF;
    FichaTecnica ficha = new FichaTecnica();
    @FXML
    private Button btm_graficar2;
    
    ArrayList<String[]> arrayDatos = new ArrayList<>();
    NumberAxis xAxis = new NumberAxis();
    NumberAxis yAxis = new NumberAxis();
    LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
    XYChart.Series series2 = new XYChart.Series ();
    XYChart.Series series = new XYChart.Series();
    XYChart.Series series3;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        try{
            FileInputStream fis = new FileInputStream("datosFicha.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ficha = (FichaTecnica) ois.readObject();//El método readObject() recupera el objeto
            ois.close(); fis.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        // TODO
    }    

    @FXML
    private void cargarArchivo(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File"); 
        selectedFile =  fileChooser.showOpenDialog(pane.getScene().getWindow());
         try {
            //String rutaArchivoExcel = selectedFile.toString();
            FileInputStream inputStream = new FileInputStream(new File(selectedFile.toString()));
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

     
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File (selectedFile.getAbsolutePath());
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);
         // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
            //System.out.println(linea);
                String []  bar =linea.split("  ");
                arrayDatos.add(bar);
            }      
            if( null != fr ){   
               fr.close();     
            }                        
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tamaño de datos: "+arrayDatos.size());
      
        
    }

    @FXML
    private void graficar(ActionEvent event) {
        if(series3!=null){
            lineChart.getData().removeAll(series,series2,series3);
            paneGrafica.getChildren().removeAll(lineChart);
        }
        String irradiancia = txt_irradiancia.getText();
        String temperatura = txt_grados.getText();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Voltaje");
        yAxis.setLabel("Corriente"); 
        //creating the chart
        lineChart = new LineChart<Number,Number>(xAxis,yAxis);       
        lineChart.setTitle(txt_titulo.getText());

        series = new XYChart.Series();
        series.setName("Curva carácteristica");
        //populating the series with data

        for(int i =0;i<arrayDatos.size();i++){
             String []  datos =arrayDatos.get(i);
             double voltaje = Double.parseDouble(datos[0]);
             double corriente= Double.parseDouble(datos[1]);
             series.getData().add(new XYChart.Data(voltaje, corriente));
             System.out.println(voltaje +" "+ corriente);     
        }
        int nodoMax = encontrarFF(arrayDatos);
        System.out.println("Nodo Max: "+nodoMax);
        
        System.out.println("Nodo: "+series.getData().get(nodoMax));
        System.out.println("xy: "+Arrays.toString(arrayDatos.get(nodoMax)));
        String [] xy = arrayDatos.get(nodoMax);
        double x = Double.parseDouble(xy[0]);
        double y = Double.parseDouble(xy[1]);
        series2 = new XYChart.Series ();
        series2.setName ("Factor de forma");
        for(float i = 0;i<=x;i+=0.1){
            series2.getData().add(new XYChart.Data (i,y));
        }
        series2.getData().add(new XYChart.Data<>(x,0));     
        lineChart.setCreateSymbols(false);
        lineChart.getData().addAll(series,series2);
        paneGrafica.getChildren().add(lineChart);
        
        
        String []  primerPunto =arrayDatos.get(0);
        double voc = Double.parseDouble(primerPunto[0]);
        System.out.println("voc: "+voc);
        String []  ultimoPunto =arrayDatos.get(arrayDatos.size()-1);
        double icc= Double.parseDouble(ultimoPunto[1]);
        System.out.println("ICC: "+icc);
        System.out.println("Valor de x: "+x);
        System.out.println("Valor de y: "+y);
        double factorForma = (x * y)/(voc*icc);
        DecimalFormat formato = new DecimalFormat("#.####");
        label_factorF.setText("Factor de forma: "+formato.format(factorForma));
        label_pmaxF.setText("Potencia máxima de ficha: "+ficha.getPmax());
        label_pmax.setText("Potencia máxima calculada: "+formato.format((x*y)));
        lable_eficiencia.setText("Eficiencia: ");
    
    }

    @FXML
    private void guardarGrafica(ActionEvent event) {
        try {
            Stage stage = (Stage)btn_guardarGrafica.getScene().getWindow();
            Scene actual = (Scene)btn_guardarGrafica.getScene();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Reporte");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);                
            File archivo = fileChooser.showSaveDialog(stage);
            WritableImage snapShot = actual.snapshot(null);        
            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png", archivo);     
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(DatosCapturarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public int encontrarFF(ArrayList<String[]> valores){
        int posMaximoPunto=0;
        double valorMaximo=0;
        for(int i=0;i<valores.size();i++){
            String []  datos =valores.get(i);
             double voltaje = Double.parseDouble(datos[0]);
             double corriente= Double.parseDouble(datos[1]);
             //System.out.println("Multiplicacion"+voltaje*corriente);
            if((voltaje*corriente)>valorMaximo){
                valorMaximo=voltaje*corriente;
                posMaximoPunto=i;
            }
        }
        return posMaximoPunto;
    }

    @FXML
    private void graficarCurva2(ActionEvent event) {
        lineChart.getData().removeAll(series,series2);
        paneGrafica.getChildren().removeAll(lineChart);
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Voltaje");
        yAxis.setLabel("Potencia");
        lineChart = new LineChart<>(xAxis,yAxis);
        series3 = new XYChart.Series();
        series3.setName("Curva carácteristica");
        ArrayList<double[]> valores = new ArrayList<>();
        for(int i =0;i<arrayDatos.size();i++){
            double [] corrientePotencia = new double[2];
             String []  datos =arrayDatos.get(i);
             double voltaje = Double.parseDouble(datos[0]);
             double corriente= Double.parseDouble(datos[1]);
             double potencia = voltaje*corriente;
             corrientePotencia[0] = voltaje;
             corrientePotencia[1] = potencia;
             valores.add(corrientePotencia);
        }
        
         for(int i =0;i<valores.size();i++){
             double []  datos =valores.get(i);
             double voltaje = datos[0];
             double potencia= datos[1];
             series3.getData().add(new XYChart.Data(voltaje, potencia));
             //System.out.println(voltaje +" "+ potencia);     
        }
        
        //System.out.println("valores: "+valores.size());
        lineChart.setCreateSymbols(false);
        lineChart.getData().addAll(series3);
        paneGrafica.getChildren().add(lineChart);      
    }
    
    private double calcularEficiencia(){
        double pmax = ficha.getPmax();
        double irradiancia = ficha.getIrradiancia();
        double largo = ficha.getLargo();
        double ancho = ficha.getAncho();
        
        double area = (largo*ancho);
        
        double eficiencia = pmax/(irradiancia*area);
        
        return eficiencia;
    }
    
    
     
}
