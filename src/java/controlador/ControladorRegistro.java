/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import vista.vistaLogin;
import vista.vistaRegistro;
import ws.ServiciosSOAP;
import ws.ServiciosSOAP_Service;

/**
 *
 * @author chris
 */
public class ControladorRegistro {

    vistaRegistro vista;

    //Modelo
    ServiciosSOAP_Service servicio;
    ServiciosSOAP cliente;

    //Controlador
    ControladorLogin cLogin;

    public ControladorRegistro(ServiciosSOAP_Service servicio) {
        this.vista = new vistaRegistro();
        this.servicio = servicio;
        this.cliente = servicio.getServiciosSOAPPort();
        this.vista.setLocationRelativeTo(null);
        this.vista.setVisible(true);
        vista.getLbValidar().setText("");
        controlEventos();
    }

    public void controlEventos() {
        vista.getBtnRRegistrar().addActionListener(l -> registroUsuario());
    }

    public void registroUsuario() {

        //Variables que almacenaran los datos ingresados
        if (validarCampos()) {
            String nombre = vista.getTxtRUsuario().getText();
            String clave = vista.getTxtRClave().getText();
            String rclave = vista.getTxtRCClave().getText();
            float saldoinicial = Float.parseFloat(vista.getTxtSaldo().getText());

            //Creamos el registo de usuario usando el servicio 
            cliente.registro(nombre, clave, rclave, saldoinicial);
            vista.getLbValidar().setForeground(Color.BLUE);
            vista.getLbValidar().setText("Usuario registrado con éxito");
            try {
              
                // Retraso de 2 segundos (2000 milisegundos)
                Thread.sleep(2000);
                cLogin = new ControladorLogin(new vistaLogin(), servicio);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public boolean validarCampos() {
        Validaciones validaciones = new Validaciones();
        boolean validar = false;

        vista.getLbValidar().setForeground(Color.red);
        if (!vista.getTxtRUsuario().getText().isEmpty()) {
            if (validaciones.validarNombre(vista.getTxtRUsuario().getText())) {
                if (!vista.getTxtRClave().getText().isEmpty()) {
                    if (validaciones.validarContrasena(vista.getTxtRClave().getText())) {
                        if (!vista.getTxtRCClave().getText().isEmpty()) {
                            if (vista.getTxtRClave().getText().equals(vista.getTxtRCClave().getText())) {
                                if (!vista.getTxtSaldo().getText().isEmpty()) {
                                    if (validaciones.validarNumeroFloat(vista.getTxtSaldo().getText())) {
                                        validar = true;
                                    } else {
                                        vista.getLbValidar().setText("Ingrese un numero correcto");
                                    }
                                } else {
                                    vista.getLbValidar().setText("Campo Saldo se encuetra vacio");
                                }
                            } else {
                                vista.getLbValidar().setText("Las claves no coinciden");
                            }
                        } else {
                            vista.getLbValidar().setText("Campo Repetir clave se encuetra vacio");
                        }
                    } else {
                        vista.getLbValidar().setText(" Contraseña con al menos una letra minúscula, una letra mayúscula, un número, un carácter especial y longitud entre 8 y 20 caracteres.");
                    }
                } else {
                    vista.getLbValidar().setText("Campo clave se encuetra vacio");
                }
            } else {
                vista.getLbValidar().setText("Campo Usuario se encuetra vacio");
            }
        } else {
            vista.getLbValidar().setText("Campo Usuario se encuetra vacio");
        }
        return validar;
    }
}
