
package org.diegosolis.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Administracion;
import org.diegosolis.bean.Proveedores;
import org.diegosolis.db.Conexion;
import org.diegosolis.system.Principal;


public class ProveedoresController implements Initializable{
    
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Proveedores>listaProveedores;
    private ObservableList<Administracion>listaAdministracion;
    @FXML private Button btnNuevo;
        @FXML private Button btnEliminar;
        @FXML private Button btnEditar;
        @FXML private Button btnReporte;
        @FXML private TextField txtCodProveedor;
        @FXML private TextField txtNitProveedor;
        @FXML private TextField txtServicioPrestado;
        @FXML private TextField txtTelefonoProveedor;
        @FXML private TextField txtDireccionProveedor;
        @FXML private TextField txtSaldoFavor;
        @FXML private TextField txtSaldoContra;
        @FXML private ComboBox  cmbCodAdmin;
        @FXML private TableView tblProveedores;
        @FXML private TableColumn colCodProveedor;
        @FXML private TableColumn colNitProveedor;
        @FXML private TableColumn colServicioPrestado;
        @FXML private TableColumn colTelefonoProveedor;
        @FXML private TableColumn colDireccionProveedor;
        @FXML private TableColumn colSaldoFavor;
        @FXML private TableColumn colSaldoContra;
        @FXML private TableColumn colCodAdmin;
        @FXML private ImageView imgNuevo;
        @FXML private ImageView imgEliminar;
        @FXML private ImageView imgEditar;
        @FXML private ImageView imgReporte;
    
        
        
        @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        }
    public void cargarDatos(){
        tblProveedores.setItems(getProveedores());
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,Integer>("codigoProveedor"));
        colNitProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedores,String>("servicioPrestado"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,String>("telefonoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,String>("direccionProveedor"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedores,Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Proveedores,Double>("saldoContra"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        cmbCodAdmin.setItems(getAdministracion());
    }
    
     public void selececcionarElemento(){
         txtCodProveedor.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
         txtNitProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
         txtServicioPrestado.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
         txtTelefonoProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
         txtDireccionProveedor.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
         txtSaldoFavor.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoFavor()));
         txtSaldoContra.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getSaldoContra()));
         cmbCodAdmin.getSelectionModel().select(buscarAdministracion(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
     
     }
     
     public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                 activarControles();
                 limpiarControles();
                 btnNuevo.setText("Guardar");
                 btnEliminar.setText("Cancelar");
                 btnEditar.setDisable(true);
                 btnReporte.setDisable(true);
                 imgNuevo.setImage(new Image("/org/diegosolis/images/guardar.png"));
                 imgEliminar.setImage(new Image("/org/diegosolis/images/cancelar.png"));
                 tipoDeOperaciones=ProveedoresController.operaciones.GUARDAR;
                 break;
        
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/diegosolis/images/Agregar.png"));
                imgEliminar.setImage(new Image ("/org/diegosolis/images/basureropng.png"));
                tipoDeOperaciones = ProveedoresController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    } 
    public void guardar(){
        Proveedores registro = new Proveedores();
        registro.setNITProveedor(txtNitProveedor.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
        registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNITProveedor());
            procedimiento.setString(2,registro.getServicioPrestado());
            procedimiento.setString(3,registro.getTelefonoProveedor());
            procedimiento.setString(4,registro.getDireccionProveedor());
            procedimiento.setDouble(5,registro.getSaldoFavor());
            procedimiento.setDouble(6,registro.getSaldoContra());
            procedimiento.setInt(7,registro.getCodigoAdministracion());
            procedimiento.execute();
            listaProveedores.add(registro);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void eliminar(){
        switch(tipoDeOperaciones){
            case GUARDAR:
                 desactivarControles();
                 limpiarControles();
                 btnNuevo.setText("Nuevo");
                 btnEliminar.setText("Eliminar");
                 imgNuevo.setImage(new Image("/org/diegosolis/images/Agregar.png"));
                 imgEliminar.setImage(new Image("/org/diegosolis/images/basureropng.png"));
                 btnEditar.setDisable(false);
                 btnReporte.setDisable(false);
                 tipoDeOperaciones=ProveedoresController.operaciones.NINGUNO;
                 break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem()!=null){
                    int respuesta =JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar el registro?","Eliminar Admiministracion", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarProveedor(?)}");
                            procedimiento.setInt(1,((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(null,"Debe Seleccionar un Elemento para Realizar esta Acción");
                    }
                }
        }  
    }
      public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    editarForanes();
                    tipoDeOperaciones = ProveedoresController.operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null,"Debe Seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/diegosolis/images/scissors-png.png"));
                imgReporte.setImage(new Image("/org/diegosolis/images/docspng.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = ProveedoresController.operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
      public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/diegosolis/images/scissors-png.png"));
                imgReporte.setImage(new Image("/org/diegosolis/images/docspng.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = ProveedoresController.operaciones.NINGUNO;
              
                break;
        }
    }
      public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?,?,?,?,?,?,?)}");
            Proveedores registro = (Proveedores)tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITProveedor(txtNitProveedor.getText());
            registro.setServicioPrestado(txtServicioPrestado.getText());
            registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
            procedimiento.setInt(1,registro.getCodigoProveedor());
            procedimiento.setString(2,registro.getNITProveedor());
            procedimiento.setString(3,registro.getServicioPrestado());
            procedimiento.setString(4,registro.getTelefonoProveedor());
            procedimiento.setString(5,registro.getDireccionProveedor());
            procedimiento.setDouble(6,registro.getSaldoFavor());
            procedimiento.setDouble(7,registro.getSaldoContra());
                                       
            procedimiento.execute();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     public ObservableList<Proveedores> getProveedores(){
         ArrayList<Proveedores>lista = new ArrayList<Proveedores>();
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedor()}");
                ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                                          resultado.getString("NITProveedor"),
                                          resultado.getString("servicioPrestado"),
                                          resultado.getString("telefonoProveedor"),
                                          resultado.getString("direccionProveedor"),
                                          resultado.getDouble("saldoFavor"),
                                          resultado.getDouble("saldoContra"),
                                          resultado.getInt("codigoAdministracion")));
                
            }
         } catch (Exception e) {
             e.printStackTrace();
         }
            return listaProveedores = FXCollections.observableArrayList(lista);
     }

     public ObservableList<Administracion>getAdministracion(){
         ArrayList <Administracion> lista = new ArrayList<Administracion>();
         try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
     
    public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
            try {
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdministracion(?)}");
                procedimiento.setInt(1, codigoAdministracion );
                ResultSet registro = procedimiento.executeQuery();
                while (registro.next()){
                    resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                                    registro.getString("direccion"),
                                                    registro.getString("telefono"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return resultado;
    }
     
    public void activarControles(){
        txtCodProveedor.setEditable(false);
        txtNitProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodAdmin.setDisable(false);
    }

    public void desactivarControles(){
        txtCodProveedor.setEditable(false);
        txtNitProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        cmbCodAdmin.setDisable(true);
    }
     
    public void limpiarControles(){
        txtCodProveedor.clear();
        txtNitProveedor.clear();
        txtServicioPrestado.clear();
        txtTelefonoProveedor.clear();
        txtDireccionProveedor.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        cmbCodAdmin.getSelectionModel().clearSelection();
    }
    public void editarForanes(){
        txtCodProveedor.setEditable(false);
        txtNitProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        cmbCodAdmin.setDisable(true);
    }
     public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    public void ventanaCuentasPorPagar(){
        escenarioPrincipal.ventanaCuentasPorPagar();
    }
} 



