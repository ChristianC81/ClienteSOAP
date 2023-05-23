/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.vistaLogin;
import ws.ServiciosSOAP_Service;

/**
 *
 * @author chris
 */
public class TestWS {
    public static void main(String[] args) {
        
        vistaLogin vista = new vistaLogin();
        ServiciosSOAP_Service modelo = new ServiciosSOAP_Service();
        ControladorLogin c= new ControladorLogin(vista, modelo);
        
    }
}
