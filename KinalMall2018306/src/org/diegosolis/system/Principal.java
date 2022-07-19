
package org.diegosolis.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import org.diegosolis.controller.AdministracionController;
import org.diegosolis.controller.CargosController;
import org.diegosolis.controller.ClientesController;
import org.diegosolis.controller.CuentasPorCobrarController;
import org.diegosolis.controller.CuentasPorPagarController;
import org.diegosolis.controller.DepartamentosController;
import org.diegosolis.controller.EmpleadosController;
import org.diegosolis.controller.HorariosController;
import org.diegosolis.controller.LocalesController;
import org.diegosolis.controller.LoginController;
import org.diegosolis.controller.MenuPrincipalController;
import org.diegosolis.controller.ProgramadorController;
import org.diegosolis.controller.ProveedoresController;
import org.diegosolis.controller.TipoClientesController;
import org.diegosolis.controller.UsuarioController;


/**
 *
 * @author diego solis
 */
public class Principal extends Application {
    
    private final String Paquete_Vista="/org/diegosolis/view/";//Ruta Vistas
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("KinalMall 2021");
        //Parent root = FXMLLoader.load(getClass().getResource("/org/diegosolis/view/ProgramadorView.fxml"));
////        Scene escena = new Scene(root);
//        escenarioPrincipal.setScene(escena);
    ventanaLogin(); 
    escenarioPrincipal.show();
                
        
    }


    public void menuPrincipal(){
        try {
            MenuPrincipalController menu=(MenuPrincipalController)cambiarEscena("MenuPrincipalview.fxml",541,400);
            menu.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void ventanaProgramador(){
            try {
                ProgramadorController programador =(ProgramadorController) cambiarEscena("ProgramadorView.fxml",600,400);
                programador.setEscenarioPrincipal(this);
            } catch (Exception e) {
                   e.printStackTrace();
            }
        }
        public void ventanaAdministracion(){
           try {
            AdministracionController admin =(AdministracionController)cambiarEscena("AdministracionView.fxml",800,447);
            admin.setEscenarioPrincipal(this);
           } catch (Exception e) {
                   e.printStackTrace();
            }
        }
        public void ventanaTipoClientes(){
            try{
                TipoClientesController tpCliente = (TipoClientesController)cambiarEscena("TipoClienteView.fxml",800,447);
                    tpCliente.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void ventanaLocales(){
            try{
                LocalesController local = (LocalesController)cambiarEscena("LocalesView.fxml", 910, 473);
                local.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
                }
            }
            
        
        public void ventanaDepartamentos(){
            try{
                DepartamentosController dept = (DepartamentosController)cambiarEscena("DepartamentosView.fxml",800,447);
                    dept.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
                
            }
        }
        
        public void ventanaClientes(){
            try{
                ClientesController cli =(ClientesController)cambiarEscena("ClientesView.fxml", 1375,499);
                    cli.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        public void ventanaProveedor(){
            try {
                ProveedoresController prov =(ProveedoresController)cambiarEscena("ProveedoresView.fxml",1055,460);
                    prov.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaCuentasPorPagar(){
            try {
                CuentasPorPagarController pagar =(CuentasPorPagarController)cambiarEscena("CuentasPorPagarView.fxml",1038,424);
                    pagar.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaHorarios(){
            try {
                HorariosController hora = (HorariosController)cambiarEscena("HorariosView.fxml",1156,463);
                    hora.setEscenarioPrincipal(this);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaUsuario(){
            try {
                UsuarioController UC =(UsuarioController)cambiarEscena("UsuarioView.fxml", 567,370);
                    UC.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaLogin (){
            try {
                LoginController LC = (LoginController)cambiarEscena("LoginView.fxml", 600, 400);
                LC.setEscenaPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaCargos(){
            try {
                CargosController CC = (CargosController)cambiarEscena("CargosView.fxml", 800, 447);
                CC.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaEmpleados(){
            try {
                EmpleadosController EC = (EmpleadosController)cambiarEscena("EmpleadosView.fxml", 1207,472);
                EC.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void ventanaCuentasPorCobrar(){
            try {
                CuentasPorCobrarController CCC = (CuentasPorCobrarController)cambiarEscena("CuentasPorCobrarView.fxml",1102, 409);
                CCC.setEscenarioPrincipal(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
    public static void main(String[] args) {
        launch(args);
    }
    public Initializable cambiarEscena(String fxml,int ancho,int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML= new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(Paquete_Vista+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(Paquete_Vista+fxml));
        escena= new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    
    
}
