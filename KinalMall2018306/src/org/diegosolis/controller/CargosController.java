
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Cargos;
import org.diegosolis.db.Conexion;
import org.diegosolis.report.GenerarReporte;
import org.diegosolis.system.Principal;

public class CargosController implements Initializable{

    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Cargos>listaCargos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCargo;
    @FXML private TextField txtCargo;
    @FXML private TableView tblCargos;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colNombreCargo;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
     cargarDatos();
    }
    public void cargarDatos(){
        tblCargos.setItems(getCargos());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargos,Integer>("codigoCargo"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos,String>("nombreCargo"));
    }

    public void seleccionarElemento(){
        if(tblCargos.getSelectionModel().getSelectedItem() != null){
        txtCodigoCargo.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo()));
        txtCargo.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
        
    }
    }    
    
    public ObservableList<Cargos>getCargos(){
        ArrayList<Cargos>lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargo()}"); 
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                lista.add(new Cargos(registro.getInt("codigoCargo"), registro.getString("nombreCargo")));
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargos = FXCollections.observableArrayList(lista);
    }
//        public void seleccionarElemento(){
//        if(tblCargos.getSelectionModel().getSelectedItem() != null){
//        txtCodigoCargo.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo()));
//        txtCargo.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo()));
//        }
//    }
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
                tipoDeOperaciones = operaciones.GUARDAR;
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Cargos registro = new Cargos();
        registro.setNombreCargo(txtCargo.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargo (?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.execute();
            listaCargos.add(registro);
        }catch(Exception e){
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
                 tipoDeOperaciones=operaciones.NINGUNO;
                 break;
            default:
                 if(tblCargos.getSelectionModel().getSelectedItem()!=null){
                     int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Administracion",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                     if (respuesta == JOptionPane.YES_OPTION)
                         try{
                             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BorrarCargo(?)}");
                             procedimiento.setInt(1,((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargo());
                             procedimiento.execute();
                             listaCargos.remove(tblCargos.getSelectionModel().getSelectedIndex());
                             limpiarControles();
                         }catch(Exception e){
                             e.printStackTrace();
                         }
                 }else {
                     JOptionPane.showMessageDialog(null, "Debe Seleccionar un Elemento. ");
                 }
        }
    }
    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if (tblCargos.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
                
        }
    
    }
     public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCargo(?,?)}");
            Cargos registro = (Cargos)tblCargos.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtCargo.getText());
            procedimiento.setInt(1,registro.getCodigoCargo());
            procedimiento.setString(2,registro.getNombreCargo());
            procedimiento.execute();
        
        }catch(Exception e){
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
                tipoDeOperaciones = operaciones.NINGUNO;
              
                
        }
    }
    
    public void desactivarControles(){
        txtCodigoCargo.setEditable(false);
        txtCargo.setEditable(false);
        
    }
    public void activarControles(){
        txtCodigoCargo.setEditable(false);
        txtCargo.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCargo.clear();
        txtCargo.clear();
        
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
    public void ventanaEmpleados(){
        escenarioPrincipal.ventanaEmpleados();
    }
}
