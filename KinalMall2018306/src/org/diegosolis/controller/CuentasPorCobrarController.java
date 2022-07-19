
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
import org.diegosolis.bean.Clientes;
import org.diegosolis.bean.Locales;
import org.diegosolis.db.Conexion;
import org.diegosolis.system.Principal;
import org.diegosolis.bean.CuentasPorCobrar;

public class CuentasPorCobrarController implements Initializable{

    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentasPorCobrar>listaCuentasPorCobrar;
    private ObservableList<Administracion>listaAdministracion;
    private ObservableList<Locales>listaLocal;
    private ObservableList<Clientes>listaClientes;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentasPorCobrar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtAnos;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoDePago;
    @FXML private TableView tblCuentasPorCobrar;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private ComboBox cmbCodigoLocal;
    @FXML private TableColumn colCodigoCuentasPorCobrar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colAnos;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoDePago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoClientes;
    @FXML private TableColumn colCodigoLocal;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
      public void cargarDatos(){
        tblCuentasPorCobrar.setItems(getCuentasPorCobrar());
        colCodigoCuentasPorCobrar.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("codigoCuentasPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("numeroFactura"));
        colAnos.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Double>("valorNetoPago"));
        colEstadoDePago.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("estadoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,String>("codigoAdministracion"));
        colCodigoClientes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("codigoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("codigoLocal"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoCliente.setItems(getClientes());
        cmbCodigoLocal.setItems(getLocales());  
    }
    public void seleccionarElemento(){
        if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
            txtNumeroFactura.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura()));
            txtAnos.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio()));
            txtMes.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes()));            
            txtValorNetoPago.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            txtEstadoDePago.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));           
            cmbCodigoCliente.getSelectionModel().select(buscarClientes(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            cmbCodigoLocal.getSelectionModel().select(buscarLocal(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        }
    }
    public ObservableList<CuentasPorCobrar>getCuentasPorCobrar(){
        ArrayList<CuentasPorCobrar> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentasPorCobrar(resultado.getInt("codigoCuentasPorCobrar"),
                                           resultado.getString("numeroFactura"),
                                           resultado.getInt("anio"),
                                           resultado.getInt("mes"),
                                           resultado.getDouble("valorNetoPago"),
                                           resultado.getString("estadoPago"),
                                           resultado.getInt("codigoAdministracion"),
                                           resultado.getInt("codigoCliente"),
                                           resultado.getInt("codigolocal")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCuentasPorCobrar=FXCollections.observableArrayList(lista);
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
    public ObservableList<Clientes>getClientes(){
        ArrayList<Clientes>lista = new ArrayList<Clientes>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                                        resultado.getString("nombresClientes"),
                                        resultado.getString("apellidosClientes"),
                                        resultado.getString("telefonoCliente"),
                                        resultado.getString("direccionCliente"),
                                        resultado.getString("emailCliente"),
                                        resultado.getInt("codigoLocal"),
                                        resultado.getInt("codigoAdministracion"),
                                        resultado.getInt("codigoTipoCliente")));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaClientes = FXCollections.observableArrayList(lista);
    }
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales>lista = new ArrayList<Locales>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
        while(resultado.next()){
            lista.add(new Locales(resultado.getInt("codigoLocal"),
                                    resultado.getDouble("saldoFavor"),
                                    resultado.getDouble("saldoContra"),
                                    resultado.getInt("mesesPendientes"),
                                    resultado.getBoolean("disponibilidad"),
                                    resultado.getDouble("valorLocal"),
                                    resultado.getDouble("valorAdministracion")));
        }
         
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaLocal= FXCollections.observableArrayList(lista);
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
    public Clientes buscarClientes(int codigoClientes){
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarClientes(?)}");
            procedimiento.setInt(1, codigoClientes);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Clientes(registro.getInt("codigoCliente"),
                                        registro.getString("nombresClientes"),
                                        registro.getString("apellidosClientes"),
                                        registro.getString("telefonoCliente"),
                                        registro.getString("direccionCliente"),
                                        registro.getString("emailCliente"),
                                        registro.getInt("codigoLocal"),
                                        registro.getInt("codigoAdministracion"),
                                        registro.getInt("codigoTipoCliente"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultado;
    }
    public Locales buscarLocal (int codigoLocal){
        Locales resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocales(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Locales ( registro.getInt("codigoLocal"),
                                          registro.getDouble("saldoFavor"),
                                          registro.getDouble("saldoContra"),
                                          registro.getInt("mesesPendientes"),
                                          registro.getBoolean("disponibilidad"),
                                          registro.getDouble("valorLocal"),
                                          registro.getDouble("valorAdministracion"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
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
                 tipoDeOperaciones=CuentasPorCobrarController.operaciones.GUARDAR;
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
                tipoDeOperaciones = CuentasPorCobrarController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    public void guardar(){
        CuentasPorCobrar registro = new CuentasPorCobrar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setAnio(Integer.parseInt(txtAnos.getText()));
        registro.setMes(Integer.parseInt(txtMes.getText()));
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoDePago.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Clientes)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
         try {
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentasPorCobrar(?,?,?,?,?,?,?,?)}");
             procedimiento.setString(1,registro.getNumeroFactura());
             procedimiento.setInt(2, registro.getAnio());
             procedimiento.setInt(3, registro.getMes());
             procedimiento.setDouble(4, registro.getValorNetoPago());
             procedimiento.setString(5,registro.getEstadoPago());
             procedimiento.setInt(6,registro.getCodigoAdministracion());
             procedimiento.setInt(7,registro.getCodigoCliente());
             procedimiento.setInt(8,registro.getCodigoLocal());
             procedimiento.execute();
             listaCuentasPorCobrar.add(registro);
            
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
                 tipoDeOperaciones=CuentasPorCobrarController.operaciones.NINGUNO;
                 break;
            default:
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!=null){
                    int respuesta =JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar el registro?","Eliminar Admiministracion", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarCuentasPorCobrar(?)}");
                            procedimiento.setInt(1,((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorCobrar());
                            procedimiento.execute();
                            listaCuentasPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
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
                if (tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    editarForanes();
                   
                    tipoDeOperaciones = CuentasPorCobrarController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = CuentasPorCobrarController.operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
    public void actualizar(){
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentasPorCobrar(?,?,?,?,?,?)}");
            CuentasPorCobrar registro = (CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(Integer.parseInt(txtAnos.getText()));
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoDePago.getText());
            
            procedimiento.setInt(1,registro.getCodigoCuentasPorCobrar());
            procedimiento.setString(2,registro.getNumeroFactura());
            procedimiento.setInt(3, registro.getAnio());
            procedimiento.setInt(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6,registro.getEstadoPago());
            
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
                tipoDeOperaciones = CuentasPorCobrarController.operaciones.NINGUNO;
              
                break;
        }
    }
    public void desactivarControles(){
        txtCodigoCuentasPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnos.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoDePago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);
    }
    public void activarControles(){
        txtCodigoCuentasPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnos.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoDePago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoLocal.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoCuentasPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnos.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoDePago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoCliente.setValue(null);
        cmbCodigoLocal.setValue(null);
       
    }
    public void editarForanes(){
        txtCodigoCuentasPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnos.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoDePago.setEditable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);
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
    public void ventanaLocales (){
        escenarioPrincipal.ventanaLocales();
    }
}
