/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Fachada;
import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author dinos
 */
public class ControladorCotizaciones implements Initializable {

    
    int cantidad_unidades;
    int precio_unidad;
    ObservableList<String> unidadeOp = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> combo_unidades_cotizacion;
    @FXML
    private TextField txt_IDautomovil_cotizacion;
    @FXML
    private TextField txt_cedula_cotizacion;
    @FXML
    private TextField txt_nombre_cotizacion;
    @FXML
    private TextField txt_direccion_cotizacion;
    @FXML
    private TextField txt_telefono_cotizacion;
    @FXML
    private TextField txt_correo_cotizacion;
    @FXML
    private TextField txt_genero_cotizacion;
    @FXML
    private Button button_buscar_cotizacion;
    @FXML
    private TextField txt_IDvendedor_cotizacion;
    @FXML
    private TextField txt_marca_cotizacion;
    @FXML
    private TextField txt_linea_cotizacion;
    @FXML
    private TableView<?> table_resumen_cotizacion;
    @FXML
    private Button button_resumen_cotizacion;
    @FXML
    private Button button_confirmar_cotizacion;
    @FXML
    private TextField txt_modelo_cotizacion;
    @FXML
    private TableColumn<?, ?> concepto;
    @FXML
    private TableColumn<?, ?> valor;
    @FXML
    private Button bBuscarCliente;
    private ComboBox<String> cbCedula;
    @FXML
    private TextField tfCorreoCliente;
    @FXML
    private TextField tfDireccionCliente;
    @FXML
    private TextField tfTelefonoCliente;
    @FXML
    private TextField tfNombreCliente;
    @FXML
    private Button bLimpiarCampos;
    @FXML
    private Button bModificarCliente;
    @FXML
    private TextField tfCedula;
    @FXML
    private TextField tfGeneroCliente;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscar_automovil(ActionEvent event) {
        
        if(!(txt_IDautomovil_cotizacion.getText().isEmpty())){
            
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            
            try {
            String sql_buscar_automovil = "SELECT * FROM vehiculo WHERE id_vehiculo=" + txt_IDautomovil_cotizacion.getText()+";";                                 
            Statement sentenciaVehiculo = conexion.createStatement();
            ResultSet rstVehiculo =sentenciaVehiculo.executeQuery(sql_buscar_automovil);
            rstVehiculo.next();
            txt_linea_cotizacion.setText(rstVehiculo.getString(2));
            txt_marca_cotizacion.setText(rstVehiculo.getString(3));
            txt_modelo_cotizacion.setText(rstVehiculo.getString(5));
            
            cantidad_unidades=Integer.parseInt(rstVehiculo.getString(6));
            precio_unidad = Integer.parseInt(rstVehiculo.getString(4));
            for(int i=0;i<cantidad_unidades;i++){
                combo_unidades_cotizacion.getItems().add((i+1)+"");
            }
            
             conexion.close();       
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }else{
            
            
            
        }
        
        
    }

    @FXML
    private void generar_resumen(ActionEvent event) {
    }

    @FXML
    private void confirmar_cotizacion(ActionEvent event) {
        
        
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();
        
        try {
            
            String sql_cotizacion="INSERT INTO cotizacion (id_vehiculo, id_cliente,id_vendedor) VALUES (" 
                    + Integer.parseInt(txt_IDautomovil_cotizacion.getText())+","+Integer.parseInt(txt_cedula_cotizacion.getText())+","+Integer.parseInt(txt_IDvendedor_cotizacion.getText())+");";
            Statement stCotizacion= conexion.createStatement();
            ResultSet rstCotizacion = stCotizacion.executeQuery(sql_cotizacion);
            String sql_cliente="INSERT INTO cliente (cedula_cliente, nombre_cliente,direccion_cliente,telefono_cliente,genero_cliente,correo_electronico) VALUES ("
                    + Integer.parseInt(txt_cedula_cotizacion.getText())+",'"+txt_nombre_cotizacion.getText()+"','"+txt_direccion_cotizacion.getText()+"',"+Integer.parseInt(txt_telefono_cotizacion.getText())+",'"+txt_genero_cotizacion.getText()+"','"+txt_correo_cotizacion.getText()+"');";
            System.out.println(sql_cliente);
            
            Statement stCliente= conexion.createStatement();
            ResultSet rstCliente = stCliente.executeQuery(sql_cliente);
            conexion.close();  
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @FXML
    private void limpiarCampos() 
    {
        tfCedula.setText("");
        tfCorreoCliente.setText("");
        tfDireccionCliente.setText("");
        tfTelefonoCliente.setText("");
        tfNombreCliente.setText("");
        tfGeneroCliente.setText("");
    }

    @FXML
    private void buscarCliente(ActionEvent event) 
    {
        String sqlBuscarCliente;
        if(!(tfCedula.getText().isEmpty()))
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            try
            {
                sqlBuscarCliente = "SELECT * FROM cliente WHERE cedula_cliente = " + tfCedula.getText();
                Statement st = conexion.createStatement();
                ResultSet cliente = st.executeQuery(sqlBuscarCliente);
                
                if(cliente.next() == false)
                {
                    cliente.beforeFirst();
                    conexion.close();
                    return;
                }
                
                        
                tfNombreCliente.setText(cliente.getString(2));
                tfDireccionCliente.setText(cliente.getString(3));
                tfTelefonoCliente.setText(cliente.getString(4));
                tfGeneroCliente.setText(cliente.getString(5));
                tfCorreoCliente.setText(cliente.getString(6));
                
                tfCorreoCliente.setDisable(false);
                tfDireccionCliente.setDisable(false);
                tfTelefonoCliente.setDisable(false);
                tfNombreCliente.setDisable(false);
                tfGeneroCliente.setDisable(false);
                
                tfCorreoCliente.setEditable(true);
                tfDireccionCliente.setEditable(true);
                tfTelefonoCliente.setEditable(true);
                tfNombreCliente.setEditable(true);
                tfGeneroCliente.setEditable(true);
                
                conexion.close();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null, "El cliente no existe, no pudo conectarse");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Por favor ingrese la cédula del cliente");
        }
    
    }
    
    @FXML
    private void modificarCliente(ActionEvent event) 
    {
        boolean ordenInvalida = tfCorreoCliente.getText().equals("") || tfDireccionCliente.getText().equals("") 
                             || tfTelefonoCliente.getText().equals("") || tfNombreCliente.getText().equals("");
        
        if (ordenInvalida)
        {
            JOptionPane.showMessageDialog(null, "Por favor rellene todos los campos.");
        }
        else 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            String correo = tfCorreoCliente.getText();
            String direccion = tfDireccionCliente.getText();
            String telefono = tfTelefonoCliente.getText();
            String nombre = tfNombreCliente.getText();
            String genero = tfGeneroCliente.getText();
            String cedula = tfCedula.getText();

            limpiarCampos();

            Timer temporizador = new Timer();
            TimerTask tarea = new TimerTask() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        
                        Statement st = conexion.createStatement();
                        String sql = "UPDATE cliente "
                                   + "SET nombre_cliente = '" + nombre + "', direccion_cliente = '" + direccion + "', telefono_cliente = " + telefono
                                   + ", correo_electronico = '" + correo
                                   + "', genero_cliente = '" + genero
                                   + "' WHERE cedula_cliente = " + cedula + ";";
                        System.out.println(sql);
                        int result = st.executeUpdate(sql);

                        if (result == 1) 
                        {
                            JOptionPane.showMessageDialog(null, "Los datos del cliente fueron modificados con éxito.");
                            
                            tfCorreoCliente.setDisable(true);
                            tfDireccionCliente.setDisable(true);
                            tfTelefonoCliente.setDisable(true);
                            tfNombreCliente.setDisable(true);
                            tfGeneroCliente.setDisable(true);
                
                            tfCorreoCliente.setEditable(false);
                            tfDireccionCliente.setEditable(false);
                            tfTelefonoCliente.setEditable(false);
                            tfNombreCliente.setEditable(false);
                            tfGeneroCliente.setEditable(false);
                            limpiarCampos();
                            
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Ocurrió un error al modificar los datos del cliente.");
                        }

                        st.close();
                        conexion.close();
                    } 
                    catch (SQLException ex) 
                    {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    } 
                    finally 
                    {
                        cancel();
                        temporizador.cancel();
                    }
                }
            };
            temporizador.schedule(tarea, 0);
        }
    }




}
    

