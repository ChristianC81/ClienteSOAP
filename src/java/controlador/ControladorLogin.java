/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import vista.vistaLogin;
import ws.*;

/**
 *
 * @author chris
 */
public class ControladorLogin {

    //Vistas
    vistaLogin vista;

    //Modelo
    ServiciosSOAP_Service servicio;
    ServiciosSOAP cliente;

    //Controladores 
    ControladorRegistro cRegistro;
    ControladorMenu cMenu;

    public ControladorLogin(vistaLogin vlogin, ServiciosSOAP_Service servicio) {
        this.vista = vlogin;
        this.servicio = servicio;
        this.cliente = servicio.getServiciosSOAPPort();
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        vista.getLbValidar().setText("");
        controlEventos();
    }

    public void controlEventos() {
        vista.getBtnIngresar().addActionListener(l -> ingresar());
        vista.getBtnRegistrar().addActionListener(l -> registrar());
    }

    public void ingresar() {
        //Obtener datos para validar el ingreso
        String usu = vista.getTxtUsuario().getText();
        String clv = vista.getTxtClave().getText();
        if (validarCampos()) {
            if (cliente.login(usu, clv)) {
                vista.dispose();
                cMenu = new ControladorMenu(servicio, usu);
            } else {
                vista.getLbValidar().setText("Usuario no existe o las \n credenciales son incorrectas");
            }
        }
    }

    public void registrar() {
        vista.dispose();
        cRegistro = new ControladorRegistro(servicio);
    }

    public boolean validarCampos() {
        Validaciones validaciones = new Validaciones();
        boolean validar = false;

        vista.getLbValidar().setForeground(Color.red);
        if (!vista.getTxtUsuario().getText().isEmpty()) {
            if (validaciones.validarNombre(vista.getTxtUsuario().getText())) {
                if (!vista.getTxtClave().getText().isEmpty()) {
                    if (validaciones.validarContrasena(vista.getTxtClave().getText())) {
                        validar = true;
                    } else {
                        vista.getLbValidar().setText(" Contraseña con al menos una letra minúscula, una letra mayúscula, un número, un carácter especial y longitud entre 8 y 20 caracteres.");
                    }
                } else {
                    vista.getLbValidar().setText("Campo clave se encuetra vacio");
                }
            } else {
                vista.getLbValidar().setText("Ingrese un nombre de usuario correcto Ejm: carlosc123");
            }
        } else {
            vista.getLbValidar().setText("Campo Usuario se encuetra vacio");
        }
        return validar;
    }
}
