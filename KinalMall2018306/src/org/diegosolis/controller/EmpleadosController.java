
package org.diegosolis.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Administracion;
import org.diegosolis.bean.Cargos;
import org.diegosolis.bean.Departamentos;
import org.diegosolis.bean.Empleados;
import org.diegosolis.bean.Horarios;
import org.diegosolis.db.Conexion;
import org.diegosolis.report.GenerarReporte;
import org.diegosolis.system.Principal;


public class EmpleadosController implements Initializable{
    
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empleados>listaEmpleados;
    private ObservableList<Administracion>listaAdministracion;
    private ObservableList<Cargos>listaCargos;
    private ObservableList<Horarios>listaHorarios;
    private ObservableList<Departamentos>listaDepartamentos;
    private DatePicker fechaContratacion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private GridPane grpEmpleados;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombreEmpleado;
    @FXML private TextField txtApellidoEmpleado;
    @FXML private TextField txtCorreoElectronico;
    @FXML private TextField txtTelefonoEmpleado;
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbCodigoDepartamento;
    @FXML private ComboBox cmbCodigoCargo;
    @FXML private ComboBox cmbCodigoHorario;
    @FXML private ComboBox cmbCodigoAdministracion;      
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodEmpleado;
    @FXML private TableColumn colNombreEmpleado;       
    @FXML private TableColumn colApellidoEmpleado;       
    @FXML private TableColumn  colCorreoElectronico; 
    @FXML private TableColumn colTelefonoEmpleado;
    @FXML private TableColumn colFechaDeContratacion;       
    @FXML private TableColumn colSueldo;       
    @FXML private TableColumn colCodigoDepartamento;   
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colCodHorario;       
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
      
                            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaContratacion = new DatePicker(Locale.ENGLISH);
        fechaContratacion.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().setValue("Today");
        grpEmpleados.add(fechaContratacion,5, 1);
        fechaContratacion.getStylesheets().add("/org/diegosolis/resource/DatePicker.css");
        cargarDatos();
    }
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoEmpleado"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombreEmpleado"));
        colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidoEmpleado"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correoElectronico"));
        colTelefonoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefonoEmpleado"));
        colFechaDeContratacion.setCellValueFactory(new PropertyValueFactory<Empleados,DatePicker>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados,Double>("sueldo"));
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamentos,Integer>("codigoDepartamento"));
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargos,Integer>("codigoCargo"));
        colCodHorario.setCellValueFactory(new PropertyValueFactory<Horarios,Integer>("codigoHorario"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        cmbCodigoDepartamento.setItems(getDepartamentos());
        cmbCodigoCargo.setItems(getCargos());
        cmbCodigoHorario.setItems(getHorarios());
        cmbCodigoAdministracion.setItems(getAdministracion());
    }
    public void seleccionarElemento(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
            txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNombreEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
            txtApellidoEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
            txtCorreoElectronico.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico());
            txtTelefonoEmpleado.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
            fechaContratacion.selectedDateProperty().set(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
            txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));  
            cmbCodigoDepartamento.getSelectionModel().select(buscarDepartamentos(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            cmbCodigoCargo.getSelectionModel().select(buscarDepartamentos(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargo()));
            cmbCodigoHorario.getSelectionModel().select(buscarHorarios(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));

        }
    }
    public ObservableList<Empleados>getEmpleados(){
        ArrayList<Empleados>lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                              resultado.getString("nombreEmpleado"),
                                              resultado.getString("apellidoEmpleado"),
                                              resultado.getString("correoElectronico"),
                                              resultado.getString("telefonoEmpleado"),
                                              resultado.getDate("fechaContratacion"),
                                              resultado.getDouble("sueldo"),
                                              resultado.getInt("codigoDepartamento"),
                                              resultado.getInt("codigoCargo"),
                                              resultado.getInt("codigoHorario"),
                                              resultado.getInt("codigoAdministracion")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaEmpleados=FXCollections.observableArrayList(lista);
    }
    public Empleados buscarEmpleados(int codigoEmpleado){
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpleado(?)}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                                              registro.getString("nombreEmpleado"),
                                              registro.getString("apellidoEmpleado"),
                                              registro.getString("correoElectronico"),
                                              registro.getString("telefonoEmpleado"),
                                              registro.getDate("fechaContratacion"),
                                              registro.getDouble("sueldo"),
                                              registro.getInt("codigoDepartamento"),
                                              registro.getInt("codigoCargo"),
                                              registro.getInt("codigoHorario"),
                                              registro.getInt("codigoAdministracion"));
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
    public ObservableList<Departamentos>getDepartamentos(){
        ArrayList<Departamentos>lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamento()}"); 
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamentos(resultado.getInt("codigoDepartamento"), resultado.getString("nombreDepartamento")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }
    public Departamentos buscarDepartamentos(int codigoDepartamento){
            Departamentos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarDepartamento(?)}"); 
            procedimiento.setInt(1, codigoDepartamento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Departamentos(registro.getInt("codigoDepartamento"), 
                                                registro.getString("nombreDepartamento"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    public ObservableList<Cargos>getCargos(){
        ArrayList<Cargos>lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargo()}"); 
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargo"), resultado.getString("nombreCargo")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargos = FXCollections.observableArrayList(lista);
    }
    public Cargos buscarCargos(int codigoCargo){
         Cargos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCargo(?)}"); 
            procedimiento.setInt(1, codigoCargo);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
               resultado = new Cargos(registro.getInt("codigoCargo"), registro.getString("nombreCargo"));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
     public ObservableList<Horarios>getHorarios(){
        ArrayList <Horarios> lista = new ArrayList<Horarios>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorario()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()){
                lista.add(new Horarios(resultado.getInt("codigoHorario"),
                                       resultado.getString("horarioEntrada"),
                                       resultado.getString("horarioSalida"),
                                       resultado.getBoolean("lunes"),
                                       resultado.getBoolean("martes"),
                                       resultado.getBoolean("miercoles"),
                                       resultado.getBoolean("jueves"),
                                       resultado.getBoolean("viernes")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaHorarios = FXCollections.observableArrayList(lista);
    }
      public Horarios buscarHorarios(int codigoHorario){
          Horarios resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarHorario(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()){
                resultado = new Horarios(registro.getInt("codigoHorario"),
                                       registro.getString("horarioEntrada"),
                                       registro.getString("horarioSalida"),
                                       registro.getBoolean("lunes"),
                                       registro.getBoolean("martes"),
                                       registro.getBoolean("miercoles"),
                                       registro.getBoolean("jueves"),
                                       registro.getBoolean("viernes"));
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
                 tipoDeOperaciones=EmpleadosController.operaciones.GUARDAR;
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
                tipoDeOperaciones = EmpleadosController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    } 
    
    public void guardar(){
        Empleados registro = new Empleados();
        registro.setNombreEmpleado(txtNombreEmpleado.getText());
        registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
        registro.setCorreoElectronico(txtCorreoElectronico.getText());
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setFechaContratacion(fechaContratacion.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));        
        registro.setCodigoDepartamento(((Departamentos)cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
        registro.setCodigoCargo(((Cargos)cmbCodigoCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
        registro.setCodigoHorario(((Horarios)cmbCodigoHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());

        
        try {
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
             procedimiento.setString(1,registro.getNombreEmpleado());
             procedimiento.setString(2,registro.getApellidoEmpleado());
             procedimiento.setString(3,registro.getCorreoElectronico());
             procedimiento.setString(4, registro.getTelefonoEmpleado());
             procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
             procedimiento.setDouble(6, registro.getSueldo());
             procedimiento.setInt(7,registro.getCodigoDepartamento());
             procedimiento.setInt(8,registro.getCodigoCargo());
             procedimiento.setInt(9,registro.getCodigoHorario());
             procedimiento.setInt(10,registro.getCodigoAdministracion());
             procedimiento.execute();
             listaEmpleados.add(registro);
            
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
                 tipoDeOperaciones=EmpleadosController.operaciones.NINGUNO;
                 break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem()!=null){
                    int respuesta =JOptionPane.showConfirmDialog(null,"¿Está seguro de eliminar el registro?","Eliminar Admiministracion", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarEmpleado(?)}");
                            procedimiento.setInt(1,((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    editarForanes();
                    tipoDeOperaciones = EmpleadosController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = EmpleadosController.operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    }
        public void actualizar(){
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombreEmpleado.getText());
        registro.setApellidoEmpleado(txtApellidoEmpleado.getText());
        registro.setCorreoElectronico(txtCorreoElectronico.getText());
        registro.setTelefonoEmpleado(txtTelefonoEmpleado.getText());
        registro.setFechaContratacion(fechaContratacion.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));        
        
             procedimiento.setInt(1,registro.getCodigoEmpleado());
             procedimiento.setString(2,registro.getNombreEmpleado());
             procedimiento.setString(3,registro.getApellidoEmpleado());
             procedimiento.setString(4,registro.getCorreoElectronico());
             procedimiento.setString(5, registro.getTelefonoEmpleado());
             procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
             procedimiento.setDouble(7, registro.getSueldo());
             
             procedimiento.execute();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   public void reporte(){
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
                tipoDeOperaciones = EmpleadosController.operaciones.NINGUNO;
              
                break;
                case NINGUNO:
                imprimirReporte();
                break;
        }
    }
    public void imprimirReporte(){
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
        Map parametro = new HashMap();
        int empleados = ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado();              
        parametro.put("empleados",empleados);
        GenerarReporte.mostrarReporte("reporteEmpleados.jasper", "Reporte Empleados", parametro);
        }else{
            JOptionPane.showMessageDialog(null,"seleccione un registro");
        }
    }
        public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(false);
        txtApellidoEmpleado.setEditable(false);
        txtCorreoElectronico.setEditable(false);
        txtTelefonoEmpleado.setEditable(false);
        txtSueldo.setEditable(false);
        cmbCodigoDepartamento.setDisable(false);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        fechaContratacion.setDisable(false);
    }
        public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        cmbCodigoDepartamento.setDisable(false);
        cmbCodigoCargo.setDisable(false);
        cmbCodigoHorario.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        fechaContratacion.setDisable(false);
    }
        public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNombreEmpleado.clear();
        txtApellidoEmpleado.clear();
        txtCorreoElectronico.clear();
        txtTelefonoEmpleado.clear();
        txtSueldo.clear();
        cmbCodigoDepartamento.setValue(null);
        cmbCodigoCargo.setValue(null);
        cmbCodigoHorario.setValue(null);
        cmbCodigoAdministracion.setValue(null);
        fechaContratacion.setPromptText("");
    }
        public void editarForanes(){
        txtCodigoEmpleado.setEditable(false);
        txtNombreEmpleado.setEditable(true);
        txtApellidoEmpleado.setEditable(true);
        txtCorreoElectronico.setEditable(true);
        txtTelefonoEmpleado.setEditable(true);
        txtSueldo.setEditable(true);
        cmbCodigoDepartamento.setDisable(true);
        cmbCodigoCargo.setDisable(true);
        cmbCodigoHorario.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        fechaContratacion.setDisable(false);
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
    public void ventanaCargos(){
        escenarioPrincipal.ventanaCargos();
    }
}

