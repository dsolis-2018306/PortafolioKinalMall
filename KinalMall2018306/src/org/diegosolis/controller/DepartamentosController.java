
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.diegosolis.bean.Administracion;
import org.diegosolis.bean.Departamentos;
import org.diegosolis.bean.TipoCliente;
import org.diegosolis.db.Conexion;
import org.diegosolis.system.Principal;

public class DepartamentosController  implements Initializable{
     private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Departamentos>listaDepartamentos;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoDepartamento;
    @FXML private TextField txtNombreDepartamento;
    @FXML private TableView tblDepartamento;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colNombreDepartamento;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    
    public void cargarDatos(){
        tblDepartamento.setItems(getDepartamentos());
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<TipoCliente,Integer>("codigoDepartamento"));
        colNombreDepartamento.setCellValueFactory(new PropertyValueFactory<TipoCliente,String>("nombreDepartamento"));
    
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
    
    public void seleccionarElemento(){
        if(tblDepartamento.getSelectionModel().getSelectedItem() != null){
        txtCodigoDepartamento.setText(String.valueOf(((Departamentos)tblDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
        txtNombreDepartamento.setText(((Departamentos)tblDepartamento.getSelectionModel().getSelectedItem()).getNombreDepartamento());
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
                tipoDeOperaciones = DepartamentosController.operaciones.GUARDAR;
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
                tipoDeOperaciones = DepartamentosController.operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Departamentos registro = new Departamentos();
        registro.setNombreDepartamento(txtNombreDepartamento.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarDepartamento(?)}");
            procedimiento.setString(1, registro.getNombreDepartamento());
            procedimiento.execute();
            listaDepartamentos.add(registro);
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
                 tipoDeOperaciones=DepartamentosController.operaciones.NINGUNO;
                 break;
            default:
                 if(tblDepartamento.getSelectionModel().getSelectedItem()!=null){
                     int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Administracion",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                     if (respuesta == JOptionPane.YES_OPTION)
                         try{
                             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_borrarDepartamentos(?)}");
                             procedimiento.setInt(1,((Departamentos)tblDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
                             procedimiento.execute();
                             listaDepartamentos.remove(tblDepartamento.getSelectionModel().getSelectedIndex());
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
                if (tblDepartamento.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    imgEditar.setImage(new Image("/org/diegosolis/images/actualizar.png"));
                    imgReporte.setImage(new Image ("/org/diegosolis/images/cancelar.png"));
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperaciones = DepartamentosController.operaciones.ACTUALIZAR;
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
                tipoDeOperaciones = DepartamentosController.operaciones.NINGUNO;
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
                tipoDeOperaciones = DepartamentosController.operaciones.NINGUNO;
              
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarDepartemento(?,?)}");
            Departamentos registro = (Departamentos)tblDepartamento.getSelectionModel().getSelectedItem();
            registro.setNombreDepartamento(txtNombreDepartamento.getText());
            procedimiento.setInt(1,registro.getCodigoDepartamento());
            procedimiento.setString(2,registro.getNombreDepartamento());
            procedimiento.execute();
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(false);
        
    }
    public void activarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoDepartamento.clear();
        txtNombreDepartamento.clear();
        
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
    
   
}