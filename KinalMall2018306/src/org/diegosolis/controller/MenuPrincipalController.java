
package org.diegosolis.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.diegosolis.system.Principal;

public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    public void ventanaAdministracion(){
        escenarioPrincipal.ventanaAdministracion();
    }
    public void ventanaTipoCliente (){
        escenarioPrincipal.ventanaTipoClientes();
    }
    public void ventanaLocales (){
        escenarioPrincipal.ventanaLocales();
    }
    
    public void ventanaDepartamentos (){
        escenarioPrincipal.ventanaDepartamentos();
    }
    public void ventanaClientes(){
        escenarioPrincipal.ventanaClientes();
    }
    public void ventanaProveedor(){
        escenarioPrincipal.ventanaProveedor();
    }
    public void ventanaCuentasPorPagar(){
        escenarioPrincipal.ventanaCuentasPorPagar();
    }
    public void ventanaHorarios(){
        escenarioPrincipal.ventanaHorarios();
    }
    public void ventanaLogin(){
        int dato = JOptionPane.showConfirmDialog(null, "¿Estas Seguro de querer cerrar sesión?","Cerrar Sesión",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if (dato == JOptionPane.YES_OPTION) {
                    escenarioPrincipal.ventanaLogin();

        }
    }
    public void ventanaCargos(){
        escenarioPrincipal.ventanaCargos();
    }
    public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
    public void ventanaCuentasPorCobrar(){
        escenarioPrincipal.ventanaCuentasPorCobrar();
    }
    
}



