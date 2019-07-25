/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obtencioncurva;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author IsraelPC
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txt_grados;
    @FXML
    private TextField txt_voc;
    @FXML
    private TextField txt_icc;
    @FXML
    private TextField txt_vmax;
    @FXML
    private TextField txt_imax;
    @FXML
    private TextField txt_pmax;
    @FXML
    private TextField txt_irradiancia;
    @FXML
    private Button btn_capturarDatos;
    @FXML
    private TextField txt_largo;
    @FXML
    private TextField txt_ancho;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void capturarDatos(ActionEvent event) throws IOException {
        double grados = Double.parseDouble(txt_grados.getText());
        double voc = Double.parseDouble(txt_voc.getText());
        double icc = Double.parseDouble(txt_icc.getText());
        double vmax = Double.parseDouble(txt_vmax.getText());
        double imax = Double.parseDouble(txt_imax.getText());
        double pmax = Double.parseDouble(txt_pmax.getText());
        double irradiancia = Double.parseDouble(txt_irradiancia.getText());
        double largo = Double.parseDouble(txt_largo.getText());
        double ancho = Double.parseDouble(txt_ancho.getText());
        
        FichaTecnica datosFicha = new FichaTecnica(grados,voc,icc,vmax,imax,pmax,irradiancia,largo,ancho);
       
        try{
              FileOutputStream fs = new FileOutputStream("datosFicha.bin");//Creamos el archivo
              ObjectOutputStream os = new ObjectOutputStream(fs);//Esta clase tiene el método writeObject() que necesitamos
              os.writeObject(datosFicha);//El método writeObject() serializa el objeto y lo escribe en el archivo
              os.close();//Hay que cerrar siempre el archivo
        } catch (IOException ex) {
              Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        Stage stage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("DatosCapturar.fxml"));
        Scene scene = new Scene(root);        
        stage.setScene(scene);
        stage.show();
        
     
        
        
        
        
    }
    
}
