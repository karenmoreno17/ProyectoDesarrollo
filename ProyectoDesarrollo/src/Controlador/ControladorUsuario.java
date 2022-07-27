/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Fachada;
import Modelo.usuario;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Karen Daniela
 */
public class ControladorUsuario implements Initializable {

    @FXML
    private TextField tTelefonoUsuario;
    @FXML
    private TextField tRolUsuario;
    @FXML
    private TextField tCedulaUsuario;
    @FXML
    private TextField tDireccionUsuario;
    @FXML
    private TextField tCorreoUsuario;
    @FXML
    private TextField tApellidoUsuario;
    @FXML
    private TextField tNombreUsuario;
    @FXML
    private Button bCrearUsuario;
    private TextField tCedulaM;
    @FXML
    private Button bBuscarUsuario;
    @FXML
    private Button bModificarUsuario;
    @FXML
    private TextField tCorreoUsuarioM;
    private TextField tApellidoUsuarioM;
    @FXML
    private TextField tRolUsuarioM;
    @FXML
    private TextField tNombreUsuarioM;
    @FXML
    private TextField tTelefonoUsuarioM;
    @FXML
    private TextField tDireccionUsuarioM;
    @FXML
    private Button bLimpiarCampos;
    @FXML
    private TextField tSedeUsuario;
    @FXML
    private TextField tContrasenaUsuario;
    @FXML
    private TextField tSedeUsuarioM;
    @FXML
    private TextField tContrasenaUsuarioM;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private ComboBox<Integer> cbCedula;
    @FXML
    private TableView<usuario> table_usuarios_registro;
    @FXML
    private Button button_aplicar;
    @FXML
    private ComboBox<String> cb_Rol_Registro;
    @FXML
    private ComboBox<String> cb_Estado_Registro;
    private TextField txt_sede_registro;
    @FXML
    private TableColumn<usuario, String> colum_Cedula;
    @FXML
    private TableColumn<usuario, String> colum_Nombre;
    @FXML
    private TableColumn<usuario, String> colum_Correo;
    @FXML
    private TableColumn<usuario, String> colum_Direccion;
    @FXML
    private TableColumn<usuario, String> colum_Telefono;
    @FXML
    private TableColumn<usuario, String> colum_Rol;
    @FXML
    private TableColumn<usuario, String> colum_Estado;
    @FXML
    private TableColumn<usuario, String> colum_Sede;
     ObservableList<usuario> obsUsuarios;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        tCedulaUsuario.setContextMenu(new ContextMenu());
        tTelefonoUsuario.setContextMenu(new ContextMenu());
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();
            
            try {
            String sql_buscar_cedula = "SELECT cedula_empleado FROM empleado;";                                 
            Statement sentenciaCedulasM = conexion.createStatement();
            ResultSet rst_buscar_cedula =sentenciaCedulasM.executeQuery(sql_buscar_cedula);
            
            while(rst_buscar_cedula.next()){
                
                cbCedula.getItems().add(Integer.parseInt(rst_buscar_cedula.getString(1)));
                
            }           
            
            } catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cb_Estado_Registro.getItems().add("Seleccione");
        cb_Estado_Registro.getItems().add("Activo");
        cb_Estado_Registro.getItems().add("Inactivo");
        cb_Estado_Registro.setValue("Seleccione");
        
        cb_Rol_Registro.getItems().add("Seleccione");
        cb_Rol_Registro.getItems().add("Jefe de taller");
        cb_Rol_Registro.getItems().add("vendedor");
        cb_Rol_Registro.getItems().add("Gerente");
        cb_Rol_Registro.setValue("Seleccione");
        
        
        
            
        this.colum_Cedula.setCellValueFactory(new PropertyValueFactory("Cedula"));
        this.colum_Nombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.colum_Correo.setCellValueFactory(new PropertyValueFactory("Correo"));
        this.colum_Direccion.setCellValueFactory(new PropertyValueFactory("Direccion"));
        this.colum_Telefono.setCellValueFactory(new PropertyValueFactory("Telefono"));
        this.colum_Rol.setCellValueFactory(new PropertyValueFactory("Rol"));
        this.colum_Estado.setCellValueFactory(new PropertyValueFactory("Estado"));
        this.colum_Sede.setCellValueFactory(new PropertyValueFactory("Sede")); 
        
         obsUsuarios=getUsuarios();
        this.table_usuarios_registro.setItems(obsUsuarios);   
            
    }    

    @FXML
    private void crearUsuario(ActionEvent event)
    {
        boolean ordenInvalida = tTelefonoUsuario.getText().equals("") || tRolUsuario.getText().equals("") 
                             || tCedulaUsuario.getText().equals("") || tDireccionUsuario.getText().equals("")
                             || tCorreoUsuario.getText().equals("") || tApellidoUsuario.getText().equals("")
                             || tNombreUsuario.getText().equals("") || tSedeUsuario.getText().equals("")
                             || tContrasenaUsuario.getText().equals("");
        
        if (ordenInvalida)
        {
            JOptionPane.showConfirmDialog(null, "Por favor, llene todos los campos.");
        }
        else
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();

            
            try 
            {
                Statement st = conexion.createStatement();
                String sql = "INSERT INTO empleado (cedula_empleado, contrasena, rol, nombre_empleado, correo_empleado, direccion_empleado, telefono_empleado, id_sede, estado) VALUES ("
                        + tCedulaUsuario.getText() + ", '" + tContrasenaUsuario.getText() + "', '" + tRolUsuario.getText() + "', '"
                        + tNombreUsuario.getText() + " " + tApellidoUsuario.getText() + "', '" + tCorreoUsuario.getText() + "', '"
                        + tDireccionUsuario.getText() + "', " + tTelefonoUsuario.getText() + ", " + tSedeUsuario.getText()
                        + ", 'Activo');";

                int result = st.executeUpdate(sql);

                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "El empleado se ha creado exitosamente.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al crear el empleado.");
                }

                st.close();
                conexion.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex.getMessage());
            }
        }
            
    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
        
         Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            
            try {
            String sql_buscar_Usuario = "SELECT * FROM empleado WHERE cedula_empleado=" + cbCedula.getValue()+";";            
            Statement sentenciaUsuarioM = conexion.createStatement();
            ResultSet rst_buscar_usuario =sentenciaUsuarioM.executeQuery(sql_buscar_Usuario);
            rst_buscar_usuario.next();
            tContrasenaUsuarioM.setText(rst_buscar_usuario.getString(2));
            tRolUsuarioM.setText(rst_buscar_usuario.getString(3));
            tNombreUsuarioM.setText(rst_buscar_usuario.getString(4));
            tCorreoUsuarioM.setText(rst_buscar_usuario.getString(5));
            tDireccionUsuarioM.setText(rst_buscar_usuario.getString(6));
            tTelefonoUsuarioM.setText(rst_buscar_usuario.getString(7)); 
            tSedeUsuarioM.setText(rst_buscar_usuario.getString(8));            
            cbEstado.setValue(rst_buscar_usuario.getString(9));
            conexion.close();       
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void modificarUsuario(ActionEvent event) 
    {
        System.out.println("entro a modificar");
        String sql_guardar;
        
        
            try
            {
                sql_guardar = "UPDATE empleado SET estado = '" + cbEstado.getValue() + "',contrasena= '" +tContrasenaUsuarioM.getText()+ "', rol ='" +tRolUsuarioM.getText()+"', nombre_empleado='"+ tNombreUsuarioM.getText() +"',correo_empleado='" + tCorreoUsuarioM.getText()+"',direccion_empleado='"+ tDireccionUsuarioM.getText()+"',telefono_empleado="+Integer.parseInt(tTelefonoUsuarioM.getText())+",id_sede="+ Integer.parseInt(tSedeUsuarioM.getText())+"WHERE cedula_empleado = " + cbCedula.getValue();
                System.out.println(sql_guardar);
                Fachada con = new Fachada();
                Connection conexion = con.getConnection();
                Statement sentencia = conexion.createStatement();
                sentencia.executeUpdate(sql_guardar);
                conexion.close();
                JOptionPane.showMessageDialog(null,"Estado modificado exitosamente");
                limpiarCamposM();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + sqle.getMessage());
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Lo escrito en los campos no coincide con el tipo de dato");
            }
        
        
    }
    
    @FXML
    private void verificacionTeclasEspeciales(KeyEvent event) 
    {
        TextField tf = (TextField) event.getSource();

        if (event.getCode().toString().equals("CONTROL")) 
        {
            tf.setEditable(false);
        }
    }

    @FXML
    private void verificacionEntradaNumerica(KeyEvent event) 
    {
        TextField tf = (TextField) event.getSource();

        try 
        {
            Integer.parseInt(event.getCharacter());
            tf.setEditable(true);

            if (tf.getText().length() > 15) 
            {
                event.consume();
            }
        } 
        catch (NumberFormatException nfe) 
        {
            event.consume();
        }
    }

    @FXML
    private void limpiarCampos() 
    {
        tTelefonoUsuario.setText("");
        tRolUsuario.setText("");
        tCedulaUsuario.setText("");
        tDireccionUsuario.setText("");
        tCorreoUsuario.setText("");
        tApellidoUsuario.setText("");
        tNombreUsuario.setText("");
        tSedeUsuario.setText("");
        tContrasenaUsuario.setText("");
        
    }

    private void limpiarCamposM() {
        tCedulaM.setText("");
        tCorreoUsuarioM.setText("");
        tApellidoUsuarioM.setText("");
        tRolUsuarioM.setText("");
        tNombreUsuarioM.setText("");
        tTelefonoUsuarioM.setText("");
        tDireccionUsuarioM.setText("");
        tSedeUsuarioM.setText("");
        tContrasenaUsuarioM.setText("");
        cbEstado.setValue("Elija el estado de su empleado");
    }

    @FXML
    private void filtrar(ActionEvent event) {
        
        obsUsuarios=filtrarLista();
        
        System.out.println(obsUsuarios.get(0).getCedula());
        
        
        table_usuarios_registro.setItems(obsUsuarios);
    }
    
    public ObservableList<usuario> getUsuarios(){
        ObservableList<usuario> obsUsuarios = FXCollections.observableArrayList();
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();
        try{
            String sql_usuarios_registro = "SELECT * FROM empleado;";
            Statement sentenciaUsuariosR=conexion.createStatement();
            ResultSet rstU = sentenciaUsuariosR.executeQuery(sql_usuarios_registro);
            
            while(rstU.next()){
                usuario Usuario_ = new usuario(rstU.getInt(1), rstU.getString(4), rstU.getString(5), rstU.getString(6), 
                                                rstU.getInt(7), rstU.getString(3),rstU.getString(9),rstU.getInt(8));
                
                System.out.println(rstU.getInt(1));
                
                obsUsuarios.add(Usuario_);
                
                
            }
            
            conexion.close();
            
            
        }catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(obsUsuarios.get(0).getCedula());
        System.out.println(obsUsuarios.get(2).getCedula());
        System.out.println(obsUsuarios.get(3).getCedula());
        return obsUsuarios;
    }
    
    public ObservableList<usuario> filtrarLista(){
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();
        obsUsuarios.clear();
        
        if(cb_Rol_Registro.getValue()=="Seleccione" && cb_Estado_Registro.getValue()=="Seleccione"){
            
             try{
            String sql_usuarios_registro = "SELECT * FROM empleado;";
            Statement sentenciaUsuariosR=conexion.createStatement();
            ResultSet rstU = sentenciaUsuariosR.executeQuery(sql_usuarios_registro);
            
            while(rstU.next()){
                usuario Usuario_ = new usuario(rstU.getInt(1), rstU.getString(4), rstU.getString(5), rstU.getString(6), 
                                                rstU.getInt(7), rstU.getString(3),rstU.getString(9),rstU.getInt(8));               
               
                obsUsuarios.add(Usuario_);
                               
            }            
            conexion.close();            
            
        }catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }else if(cb_Rol_Registro.getValue()!="Seleccione" && cb_Estado_Registro.getValue()=="Seleccione" ){
            try{
            String sql_usuarios_registro = "SELECT * FROM empleado WHERE rol ='"+cb_Rol_Registro.getValue()+"';";
            Statement sentenciaUsuariosR=conexion.createStatement();
            ResultSet rstU = sentenciaUsuariosR.executeQuery(sql_usuarios_registro);
            
            while(rstU.next()){
                usuario Usuario_ = new usuario(rstU.getInt(1), rstU.getString(4), rstU.getString(5), rstU.getString(6), 
                                                rstU.getInt(7), rstU.getString(3),rstU.getString(9),rstU.getInt(8));               
               
                obsUsuarios.add(Usuario_);
                               
            }            
            conexion.close();            
            
        }catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else if(cb_Rol_Registro.getValue()!="Seleccione" && cb_Estado_Registro.getValue()!="Seleccione" ){
            try{
            String sql_usuarios_registro = "SELECT * FROM empleado WHERE rol ='"+cb_Rol_Registro.getValue()+"' and estado ='"+cb_Estado_Registro.getValue()+"';";
            Statement sentenciaUsuariosR=conexion.createStatement();
            ResultSet rstU = sentenciaUsuariosR.executeQuery(sql_usuarios_registro);
            
            while(rstU.next()){
                usuario Usuario_ = new usuario(rstU.getInt(1), rstU.getString(4), rstU.getString(5), rstU.getString(6), 
                                                rstU.getInt(7), rstU.getString(3),rstU.getString(9),rstU.getInt(8));               
               
                obsUsuarios.add(Usuario_);
                               
            }            
            conexion.close();            
            
        }catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        }else if(cb_Rol_Registro.getValue()=="Seleccione" && cb_Estado_Registro.getValue()!="Seleccione" ){
             try{
            String sql_usuarios_registro = "SELECT * FROM empleado WHERE estado ='"+cb_Estado_Registro.getValue()+"';";
            Statement sentenciaUsuariosR=conexion.createStatement();
            ResultSet rstU = sentenciaUsuariosR.executeQuery(sql_usuarios_registro);
            
            while(rstU.next()){
                usuario Usuario_ = new usuario(rstU.getInt(1), rstU.getString(4), rstU.getString(5), rstU.getString(6), 
                                                rstU.getInt(7), rstU.getString(3),rstU.getString(9),rstU.getInt(8));               
               
                obsUsuarios.add(Usuario_);
                               
            }            
            conexion.close();            
            
        }catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        
        return obsUsuarios;
    }
}
