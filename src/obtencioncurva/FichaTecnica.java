/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package obtencioncurva;
import java.io.Serializable;

/**
 *
 * @author IsraelPC
 */
public class FichaTecnica implements Serializable {
    double grados;
    double voc;
    double icc;
    double vmax;
    double imax;
    double pmax;
    double irradiancia;
    double largo;
    double ancho;

    

    public FichaTecnica(double grados,double voc,double icc,double vmax,double imax,double pmax,double irradiancia,double largo, double ancho){
        this.grados = grados;
        this.voc = voc;
        this.icc = icc;
        this.vmax = vmax;
        this.imax = imax;
        this.pmax = pmax;
        this.irradiancia = irradiancia;
        this.largo = largo;
        this.ancho = ancho;
    }

    FichaTecnica() {
    }
    public double getGrados() {
        return grados;
    }

    public void setGrados(double grados) {
        this.grados = grados;
    }

    public double getVoc() {
        return voc;
    }

    public void setVoc(double voc) {
        this.voc = voc;
    }

    public double getIcc() {
        return icc;
    }

    public void setIcc(double icc) {
        this.icc = icc;
    }

    public double getVmax() {
        return vmax;
    }

    public void setVmax(double vmax) {
        this.vmax = vmax;
    }

    public double getImax() {
        return imax;
    }

    public void setImax(double imax) {
        this.imax = imax;
    }

    public double getPmax() {
        return pmax;
    }

    public void setPmax(double pmax) {
        this.pmax = pmax;
    }

    public double getIrradiancia() {
        return irradiancia;
    }

    public void setIrradiancia(double irradiancia) {
        this.irradiancia = irradiancia;
    }
    public double getLargo() {
        return largo;
    }

    public void setLargo(double largo) {
        this.largo = largo;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }
    
    
}
