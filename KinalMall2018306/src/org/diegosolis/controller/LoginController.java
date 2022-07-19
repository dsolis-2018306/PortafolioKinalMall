
package org.diegosolis.controller;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Login;
import org.diegosolis.bean.Usuario;
import org.diegosolis.db.Conexion;
import org.diegosolis.system.Principal;

public class LoginController implements Initializable{
    private Principal escenaPrincipal;
    private ObservableList<Usuario>listaUsuario;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
   
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    
    public ObservableList<Usuario> getUsuarios(){
        
        ArrayList<Usuario> lista = new ArrayList<Usuario>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarUsuarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Usuario(resultado.getInt("codigoUsuario"),
                                      resultado.getString("nombreUsuario"),
                                      resultado.getString("apellidoUsuario"),
                                      resultado.getString("usuarioLogin"),
                                      resultado.getString("contrasena")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuario = FXCollections.observableArrayList(lista);
    }
    @FXML
    private void Login() throws NoSuchAlgorithmException{
        Login lg = new Login();
        int x = 0;
        boolean bandera = false;
        lg.setUsuarioMaster(txtUsuario.getText());
        lg.setPasswordLog(txtPassword.getText());   
        while(x< getUsuarios().size()){
            String user = getUsuarios().get(x).getUsuarioLogin();
            String pass = getUsuarios().get(x).getContrasena();
            if (user.equals(lg.getUsuarioMaster()) && pass.equals(lg.getPasswordLog())) {
                JOptionPane.showMessageDialog(null,"Sesión Iniciada\n"+ getUsuarios().get(x).getNombreUsuario()
                                    + " " + getUsuarios().get(x).getApellidoUsuario());
                 escenaPrincipal.menuPrincipal();
                 x = getUsuarios().size();
                 bandera = true;
            }
            x++;
       }
        if (bandera == false) {
            JOptionPane.showMessageDialog(null,"Usuario o Contraseña Incorrecto");
        }
    }
    
    public Principal getEscenaPrincipal() {
        return escenaPrincipal;
    }

    public void setEscenaPrincipal(Principal escenaPrincipal) {
        this.escenaPrincipal = escenaPrincipal;
    }
    
    public void ventanaUsuario(){
        escenaPrincipal.ventanaUsuario();
    }
    
}
