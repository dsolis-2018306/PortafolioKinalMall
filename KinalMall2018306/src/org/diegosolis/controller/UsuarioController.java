
package org.diegosolis.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.diegosolis.bean.Usuario;
import org.diegosolis.db.Conexion;
import org.diegosolis.system.Principal;


public class UsuarioController implements Initializable{
    private Principal escenarioPrincipal; 
    private enum operaciones {NUEVO,GUARDAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Usuario> listaUsuario;
    
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtPassword;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) { 
    }
    
    
    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/diegosolis/images/guardar.png"));
                tipoDeOperaciones= operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/diegosolis/images/guardar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    public void guardar(){
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombre.getText());
        registro.setApellidoUsuario(txtApellido.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setContrasena(txtPassword.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarUsuario(?,?,?,?)}");
            procedimiento.setString(1,registro.getNombreUsuario());
            procedimiento.setString(2,registro.getApellidoUsuario());
            procedimiento.setString(3,registro.getUsuarioLogin());
            procedimiento.setString(4,registro.getContrasena());
            procedimiento.execute();
            ventanaUsuario();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    public void desactivarControles(){
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
    }
    
    public void activarControles(){
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNombre.clear();
        txtApellido.clear();
        txtUsuario.clear();
        txtPassword.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void ventanaUsuario(){
        escenarioPrincipal.ventanaLogin();
    }
}
