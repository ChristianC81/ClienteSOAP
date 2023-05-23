/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Color;
import vista.vistaCentroUsuario;
import ws.ServiciosSOAP;
import ws.ServiciosSOAP_Service;

/**
 *
 * @author chris
 */
public class ControladorMenu {

    //Vista
    vistaCentroUsuario vista;

    //Modelo
    ServiciosSOAP_Service servicio;
    ServiciosSOAP cliente;

    String usuario;

    public ControladorMenu(ServiciosSOAP_Service servicio, String nombreUsu) {
        this.vista = new vistaCentroUsuario();
        this.servicio = servicio;
        this.cliente = servicio.getServiciosSOAPPort();
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
        this.usuario = nombreUsu;
        controlEventos();

        //Cargar datos del Usuario
        vista.getLbNombreU().setText(usuario);
        saldoUsuario();

    }

    public void controlEventos() {
        vista.getBtnMRegistrar().addActionListener(l -> registroOperación());
    }

    public void registroOperación() {
        //Asignación de valores a las variables
        float valor = Float.parseFloat(vista.getTxtValor().getText());

        if (valor <= cliente.saldo(usuario)) {
            //Seleccion de Operación
            if (vista.getRbtnDeposito().isSelected()) {
                cliente.depositar(valor, usuario);
                saldoUsuario();
                vista.getLbValidar().setForeground(new Color(49, 86, 153));
                vista.getLbValidar().setText("Deposito realizado con éxito");
            } else {
                if (vista.getRbtnRetirar().isSelected()) {
                    cliente.retirar(valor, usuario);
                    saldoUsuario();
                    vista.getLbValidar().setForeground(new Color(49, 86, 153));
                    vista.getLbValidar().setText("Retiro realizado con éxito");
                }
            }
        } else {
            vista.getLbValidar().setForeground(Color.red);
            vista.getLbValidar().setText("Saldo insuficiente");
        }
    }

    public void saldoUsuario() {
        String valorUsuario = String.valueOf(cliente.saldo(usuario));
        vista.getTxtMSaldo().setText(valorUsuario);
    }
}
