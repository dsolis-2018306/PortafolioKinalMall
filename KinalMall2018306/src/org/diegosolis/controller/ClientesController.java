
package org.diegosolis.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import javax.print.CancelablePrintJob;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Administracion;
import org.diegosolis.bean.Clientes;
import org.diegosolis.bean.Locales;
import org.diegosolis.bean.TipoCliente;
import org.diegosolis.db.Conexion;
import org.diegosolis.report.GenerarReporte;
import org.diegosolis.system.Principal;


public class ClientesController implements Initializable {
        private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
        private operaciones tipoDeOperaciones = operaciones.NINGUNO;
        private Principal escenarioPrincipal;
        private ObservableList<Clientes>listaClientes;
        private ObservableList<Locales>listaLocal;
        private ObservableList<Administracion>listaAdministracion;
        private ObservableList<TipoCliente>listaTipoClientes;
        @FXML private Button btnNuevo;
        @FXML private Button btnEliminar;
        @FXML private Button btnEditar;
        @FXML private Button btnReporte;
        @FXML private TextField txtCodigoCliente;
        @FXML private TextField txtNombresCliente;
        @FXML private TextField txtApellidosCliente;
        @FXML private TextField txtTelCliente;
        @FXML private TextField txtDireccion;
        @FXML private TextField txtEmail;        
        @FXML private ComboBox  cmbCodigoLocal;
        @FXML private ComboBox  cmbCodigoAdmin;
        @FXML private ComboBox  cmbCodigoTipoCliente;
        @FXML private TableView tblClientes;
        @FXML private TableColumn colCodCliente;
        @FXML private TableColumn colNombresClientes;
        @FXML private TableColumn colApellidosCliente;
        @FXML private TableColumn colTelCliente;
        @FXML private TableColumn colDireccion;
        @FXML private TableColumn colEmail;
        @FXML private TableColumn colCodigoLocal;
        @FXML private TableColumn colCodigoAdmin;
        @FXML private TableColumn colCodigoTipoCliente;
        @FXML private ImageView imgNuevo;
        @FXML private ImageView imgEliminar;
        @FXML private ImageView imgEditar;
        @FXML private ImageView imgReporte;
                 
                
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        }
    public void cargarDatos(){
        tblClientes.setItems(getClientes());
        colCodCliente.setCellValueFactory(new PropertyValueFactory<Clientes,Integer>("codigoCliente"));
        colNombresClientes.setCellValueFactory(new PropertyValueFactory<Clientes,String>("nombresCliente"));
        colApellidosCliente.setCellValueFactory(new PropertyValueFactory<Clientes,String>("apellidosClientes"));
        colTelCliente.setCellValueFactory(new PropertyValueFactory<Clientes,String>("telefonoCliente"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Clientes,String>("direccionCliente"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Clientes,String>("emailCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocal"));
        colCodigoAdmin.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente,Integer>("codigoTipoCliente"));
        cmbCodigoLocal.setItems(getLocales());
        cmbCodigoAdmin.setItems(getAdministracion());
        cmbCodigoTipoCliente.setItems(getTipoCliente());
    }
    
    public void seleccionarElemento(){
        if(tblClientes.getSelectionModel().getSelectedItem() != null){
        txtCodigoCliente.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNombresCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidosCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidosClientes());
        txtTelCliente.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtDireccion.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtEmail.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getEmailCliente());
        cmbCodigoLocal.getSelectionModel().select(buscarLocal(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        cmbCodigoAdmin.getSelectionModel().select(buscarAdministracion(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoTipoCliente.getSelectionModel().select(buscarTipoCliente(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));

        }
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
                 tipoDeOperaciones=operaciones.GUARDAR;
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
                tipoDeOperaciones = ClientesController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    
            
    public void guardar(){
        Clientes registro = new Clientes();
        registro.setNombresCliente(txtNombresCliente.getText());
        registro.setApellidosClientes(txtApellidosCliente.getText());
        registro.setTelefonoCliente(txtTelCliente.getText());
        registro.setDireccionCliente(txtDireccion.getText());
        registro.setEmailCliente(txtEmail.getText());
        registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdmin.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoTipoCliente(((TipoCliente)cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCliente(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1,registro.getNombresCliente());
            procedimiento.setString(2,registro.getApellidosClientes());
            procedimiento.setString(3,registro.getTelefonoCliente());
            procedimiento.setString(4,registro.getDireccionCliente());
            procedimiento.setString(5,registro.getEmailCliente());
            procedimiento.setInt(6,registro.getCodigoLocal());
            procedimiento.setInt(7,registro.getCodigoAdministracion());
            procedimiento.setInt(8,registro.getCodigoTipoCliente());
            procedimiento.execute();
            listaClientes.add(registro);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
     public TipoCliente buscarTipoCliente(int codigoTipoCliente){
        TipoCliente resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoCliente(?)}"); 
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoCliente(registro.getInt("codigoTipoCliente"),
                                           registro.getString("descripcion"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
     }
    public ObservableList<TipoCliente>getTipoCliente(){
        ArrayList<TipoCliente>lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoCliente()}"); 
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"), resultado.getString("descripcion")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoClientes = FXCollections.observableArrayList(lista);
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
                 tipoDeOperaciones=ClientesController.operaciones.NINGUNO;
                 break;
            default:
                if (tblClientes.getSelectionModel().getSelectedItem()!=null){
                    int respuesta =JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar el registro?","Eliminar Admiministracion", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarCuentasPorCobrar(?)}");
                            procedimiento.setInt(1,((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedIndex());
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
                if (tblClientes.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    editarForanes();
                    tipoDeOperaciones = ClientesController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = ClientesController.operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
    public void actualizar(){
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?,?,?,?,?,?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombresCliente(txtNombresCliente.getText());
            registro.setApellidosClientes(txtApellidosCliente.getText());
            registro.setTelefonoCliente(txtTelCliente.getText());
            registro.setDireccionCliente(txtDireccion.getText());
            registro.setEmailCliente(txtEmail.getText());
            
            procedimiento.setInt(1,registro.getCodigoCliente());
            procedimiento.setString(2,registro.getNombresCliente());
            procedimiento.setString(3, registro.getApellidosClientes());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6,registro.getEmailCliente());
            
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
                tipoDeOperaciones = ClientesController.operaciones.NINGUNO;
              
                break;
                
                case NINGUNO:
                imprimirReporte();
                break;
        }
    }
    public void imprimirReporte(){
        if(tblClientes.getSelectionModel().getSelectedItem() != null){
        Map parametro = new HashMap();
        int client = ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();              
        parametro.put("client",client);
        GenerarReporte.mostrarReporte("ReporteCliente.jasper", "Reporte Clientes", parametro);
        }else{
            JOptionPane.showMessageDialog(null,"seleccione un registro");
        }
    } 
    public void desactivarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(false);
        txtApellidosCliente.setEditable(false);
        txtTelCliente.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdmin.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
    }
    public void activarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtTelCliente.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        cmbCodigoLocal.setDisable(false);
        cmbCodigoAdmin.setDisable(false);
        cmbCodigoTipoCliente.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoCliente.clear();
        txtNombresCliente.clear();
        txtApellidosCliente.clear();
        txtTelCliente.clear();
        txtDireccion.clear();
        txtEmail.clear();
        cmbCodigoLocal.getSelectionModel().clearSelection();
        cmbCodigoAdmin.getSelectionModel().clearSelection();
        cmbCodigoTipoCliente.getSelectionModel().clearSelection(); 
        
    }
    public void editarForanes(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtTelCliente.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdmin.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
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
    public void ventanaTipoCliente (){
        escenarioPrincipal.ventanaTipoClientes();
    }
}
