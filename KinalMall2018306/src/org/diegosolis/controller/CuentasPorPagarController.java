
package org.diegosolis.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Administracion;
import org.diegosolis.bean.CuentasPorPagar;
import org.diegosolis.bean.Proveedores;
import org.diegosolis.db.Conexion;
import org.diegosolis.system.Principal;


public class CuentasPorPagarController implements Initializable{
    
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentasPorPagar>listaCuentasPorPagar;
    private ObservableList<Administracion>listaAdministracion;
    private ObservableList<Proveedores>listaProveedores;

    private DatePicker fechaLimite;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCuentasPorPagar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtEstadoPago;
    @FXML private TextField txtValorNetoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private GridPane grpFechaLimite;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodCPP;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodAdmin;
    @FXML private TableColumn colCodProveedor;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().setValue("Today");
        grpFechaLimite.add(fechaLimite,5, 0);
        fechaLimite.getStylesheets().add("/org/diegosolis/resource/DatePicker.css");
        cargarDatos();
    }
    public void cargarDatos(){
        tblCuentasPorPagar.setItems(getCuentasPorPagar());
        colCodCPP.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,Integer>("codigoCuentasPorPagar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,String>("numeroFactura"));
        colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,Date>("fechaLimitePago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,String>("estadoPago"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorPagar,Double>("valorNetoPago"));
        colCodAdmin.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colCodProveedor.setCellValueFactory(new PropertyValueFactory<Proveedores,Integer>("codigoProveedor"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoProveedor.setItems(getProveedores());     
    }
    
    public void seleccionarElemento(){
        if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
            txtCuentasPorPagar.setText(String.valueOf(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar()));
            txtNumeroFactura.setText(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            fechaLimite.selectedDateProperty().set(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
            txtEstadoPago.setText(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
            txtValorNetoPago.setText(String.valueOf(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbCodigoProveedor.getSelectionModel().select(buscarProveedores(((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        }
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
    public Proveedores buscarProveedores(int codigoProveedores){
        Proveedores resultado = null;
            try {
                PreparedStatement procedimineto = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedores(?)}");
                procedimineto.setInt(1, codigoProveedores);
                ResultSet registro = procedimineto.executeQuery();
                while (registro.next()){
                    resultado = new Proveedores(registro.getInt("codigoProveedor"),
                                                registro.getString("NITProveedor"),
                                                registro.getString("servicioPrestado"),
                                                registro.getString("telefonoProveedor"),
                                                registro.getString("direccionProveedor"),
                                                registro.getDouble("saldoFavor"),
                                                registro.getDouble("saldoContra"),
                                                registro.getInt("codigoAdministracion"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return resultado;
    }
    
    public ObservableList<CuentasPorPagar>getCuentasPorPagar(){
        ArrayList<CuentasPorPagar>lista = new ArrayList<CuentasPorPagar>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentasPorPagar(resultado.getInt("codigoCuentasPorPagar"),
                                              resultado.getString("numeroFactura"),
                                              resultado.getDate("fechaLimitePago"),
                                              resultado.getString("estadoPago"),
                                              resultado.getDouble("valorNetoPago"),
                                              resultado.getInt("codigoAdministracion"),
                                              resultado.getInt("codigoProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCuentasPorPagar=FXCollections.observableArrayList(lista);
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
                 tipoDeOperaciones=CuentasPorPagarController.operaciones.GUARDAR;
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
                tipoDeOperaciones = CuentasPorPagarController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    } 
    
    public void guardar(){
        CuentasPorPagar registro = new CuentasPorPagar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoProveedor(((Proveedores)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
         try {
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorPagar(?,?,?,?,?,?)}");
             procedimiento.setString(1,registro.getNumeroFactura());
             procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
             procedimiento.setString(3, registro.getEstadoPago());
             procedimiento.setDouble(4, registro.getValorNetoPago());
             procedimiento.setInt(5,registro.getCodigoAdministracion());
             procedimiento.setInt(6,registro.getCodigoProveedor());
             procedimiento.execute();
             listaCuentasPorPagar.add(registro);
            
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
                 tipoDeOperaciones=CuentasPorPagarController.operaciones.NINGUNO;
                 break;
            default:
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
                    int respuesta =JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar el registro?","Eliminar Admiministracion", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarCuentasPorPagar(?)}");
                            procedimiento.setInt(1,((CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar());
                            procedimiento.execute();
                            listaCuentasPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
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
                if (tblCuentasPorPagar.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    editarForanes();
                    tipoDeOperaciones = CuentasPorPagarController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = CuentasPorPagarController.operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
    public void actualizar(){
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorPagar(?,?,?,?,?)}");
            CuentasPorPagar registro = (CuentasPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoPago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            procedimiento.setInt(1,registro.getCodigoCuentasPorPagar());
            procedimiento.setString(2,registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            
            procedimiento.execute();
            
        } catch (Exception e) {
            e.printStackTrace();
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
                tipoDeOperaciones = CuentasPorPagarController.operaciones.NINGUNO;
              
                break;
        }
    }
    
    
    public void desactivarControles(){
        txtCuentasPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtEstadoPago.setEditable(false);
        txtValorNetoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
        fechaLimite.setDisable(true);
    }
    public void activarControles(){
        txtCuentasPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtEstadoPago.setEditable(true);
        txtValorNetoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
        fechaLimite.setDisable(false);
    }
    public void limpiarControles(){
        txtCuentasPorPagar.clear();
        txtNumeroFactura.clear();
        txtEstadoPago.clear();
        txtValorNetoPago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoProveedor.setValue(null);
        fechaLimite.setPromptText("");
    }
    public void editarForanes(){
        txtCuentasPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtEstadoPago.setEditable(true);
        txtValorNetoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
        fechaLimite.setDisable(false);
    }
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    public void ventanaProveedor(){
        escenarioPrincipal.ventanaProveedor();
    }
}
