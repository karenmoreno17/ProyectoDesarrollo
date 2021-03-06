/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import Modelo.Fachada;
import Modelo.Repuesto;
import Modelo.Vehiculo;

/**
 * FXML Controller class
 * 
 * @author Alvaro Portocarrero.
 */

public class ControladorInventario implements Initializable 
{

    private Fachada conexion;
    private String privilegios;
    private int sede;
    
    @FXML
    private TableView<Vehiculo> tvVehiculos;
    @FXML
    private TableView<Repuesto> tvRepuestos;
    @FXML
    private TableColumn<TableView<Vehiculo>, String> tcVLinea;
    @FXML
    private TableColumn<TableView<Vehiculo>, String> tcVMarca;
    @FXML
    private TableColumn<TableView<Vehiculo>, String> tcVModelo;
    @FXML
    private TableColumn<TableView<Vehiculo>, Integer> tcVPrecio;
    @FXML
    private TableColumn<TableView<Vehiculo>, Integer> tcVCantidad;
    @FXML
    private TableColumn<TableView<Repuesto>, String> tcRLinea;
    @FXML
    private TableColumn<TableView<Repuesto>, String> tcRMarca;
    @FXML
    private TableColumn<TableView<Repuesto>, String> tcRModelo;
    @FXML
    private TableColumn<TableView<Repuesto>, Integer> tcRPrecio;
    @FXML
    private TableColumn<TableView<Repuesto>, Integer> tcRCantidad;
    @FXML
    private Button bActualizar;
    @FXML
    private TabPane tabPane;
    @FXML
    private ProgressBar pb;
    @FXML
    private TextField textfield_marca;
    @FXML
    private TextField textfield_linea;
    @FXML
    private ComboBox categoria;
    @FXML
    private TextField textfield_modelo;
    @FXML
    private TextField textfield_precio;
    @FXML
    private TextField textfield_cantidad;
    @FXML
    private Button boton_guardar_inventario;
    @FXML
    private TextField textfield_marca_modificacion;
    @FXML
    private TextField textfield_linea_modificacion;
    @FXML
    private ComboBox modificacion_categoria;
    @FXML
    private TextField textfield_modelo_modificacion;
    @FXML
    private TextField textfield_precio_modificacion;
    @FXML
    private TextField textfield_cantidad_modificacion;
    @FXML
    private Button boton_guardar_inventario_modificacion;
    @FXML
    private TextField textfield_buscar;
    @FXML
    private Button buscar_id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO 

        ObservableList<String> items = FXCollections.observableArrayList();
        ObservableList<String> itemsModificacion = FXCollections.observableArrayList();

        items.add("---");
        items.add("Veh??culo");
        items.add("Repuesto");
        itemsModificacion.add("---");
        itemsModificacion.add("Veh??culo");
        itemsModificacion.add("Repuesto");
        categoria.setItems(items);
        modificacion_categoria.setItems(itemsModificacion);
        
        conexion = new Fachada();

        tcVLinea.setCellValueFactory( new PropertyValueFactory("linea"));
        tcVMarca.setCellValueFactory( new PropertyValueFactory("marca"));
        tcVModelo.setCellValueFactory( new PropertyValueFactory("modelo"));
        tcVPrecio.setCellValueFactory( new PropertyValueFactory("precio"));
        tcVCantidad.setCellValueFactory( new PropertyValueFactory("cantidad"));

        tcRLinea.setCellValueFactory( new PropertyValueFactory("linea"));
        tcRMarca.setCellValueFactory( new PropertyValueFactory("marca"));
        tcRModelo.setCellValueFactory( new PropertyValueFactory("modelo"));
        tcRPrecio.setCellValueFactory( new PropertyValueFactory("precio"));
        tcRCantidad.setCellValueFactory( new PropertyValueFactory("cantidad"));

        privilegios = "Gerente";
    }

    @FXML
    public void cargarInventario()
    {
        switch (privilegios)
        {
            case "gerente":
                cargarVehiculos();
                cargarRepuestos();
                break;
            case "vendedor":
                cargarVehiculos();
                break;
            case "jefe de taller":
                cargarRepuestos();
                break;
        }
    }

    public void cargarVehiculos()
    {
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();


        Timer temporizador = new Timer();

        TimerTask tarea = new TimerTask()
        {
            @Override
            public void run() 
            {
                try
                {
                    Statement st = conexion.createStatement();
                    String sql = "SELECT * FROM vehiculo WHERE id_sede = " + sede + " ORDER BY id_vehiculo;";

                    tvVehiculos.getItems().clear();

                    ResultSet rs = st.executeQuery(sql);

                    while(rs.next())
                    {
                        tvVehiculos.getItems().add(new Vehiculo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), 
                                                rs.getInt(4), rs.getInt(6)));
                    }

                    st.close();
                    conexion.close();
                }
                catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error Mostrar: " + ex.getMessage());
                } 
                finally 
                {
                    pb.setVisible(false);
                }
                cancel();
                temporizador.cancel();
            }
        };
        temporizador.schedule(tarea, 0); 

        pb.setVisible(true);
    }
    
    public void cargarRepuestos()
    {
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();


        Timer temporizador = new Timer();

        TimerTask tarea = new TimerTask() 
        {

            @Override
            public void run() 
            {
                try
                {
                    Statement st = conexion.createStatement();
                    String sql = "SELECT * FROM repuesto ORDER BY id_repuesto;";
            
                    tvRepuestos.getItems().clear();

                    ResultSet rs = st.executeQuery(sql);

                    while(rs.next())
                    {
                        tvRepuestos.getItems().add(new Repuesto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(5), 
                                                rs.getInt(4), rs.getInt(6)));
                    }

                    st.close();
                    conexion.close();
                }
                catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error Mostrar: " + ex.getMessage());
                } 
                finally 
                {
                    pb.setVisible(false);
                }
                cancel();
                temporizador.cancel();
            }
        };
        temporizador.schedule(tarea, 0); 

        pb.setVisible(true);
    }

    @FXML
    private void guardarInventario(ActionEvent event) 
    {
        boton_guardar_inventario.setStyle("-fx-background-color: #c7eb7a; ");
        
        String sql_guardar;
        
        if(categoria.getValue() == "Veh??culo" && !(textfield_marca.getText().isEmpty()) && !(textfield_linea.getText().isEmpty()) &&
           !(textfield_modelo.getText().isEmpty()) && !(textfield_precio.getText().isEmpty()) && !(textfield_cantidad.getText().isEmpty()))
        {
            try
            {
                sql_guardar = "INSERT INTO vehiculo (marca, linea, modelo, precio, cantidad_vehiculo, id_Sede) VALUES ('"+
                textfield_marca.getText()+ "', '"+ textfield_linea.getText()+"', '" + textfield_modelo.getText() 
                +"', '" + Integer.parseInt(textfield_precio.getText().trim()) + "', "+ Integer.parseInt(textfield_cantidad.getText().trim())+ ", " + sede +")";
                
                Fachada con = new Fachada();
                Connection conexion = con.getConnection();
                Statement sentencia = conexion.createStatement();
                int num = sentencia.executeUpdate(sql_guardar);
                conexion.close();
                JOptionPane.showMessageDialog(null,"Veh??culo registrado con exito. ");
                boton_guardar_inventario.setStyle("-fx-background-color: #66c00b; ");
                categoria.getSelectionModel().clearSelection();
                textfield_marca.clear();
                textfield_linea.clear();
                textfield_modelo.clear();
                textfield_precio.clear();
                textfield_cantidad.clear();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Los valores introducidos no son validos. ");
                boton_guardar_inventario.setStyle("-fx-background-color: #66c00b; ");
            }
        }
        else if(categoria.getValue() == "Repuesto" && !(textfield_marca.getText().isEmpty()) && !(textfield_linea.getText().isEmpty()) &&
           !(textfield_modelo.getText().isEmpty()) && !(textfield_precio.getText().isEmpty()) && !(textfield_cantidad.getText().isEmpty()))
        {     
            try
            {
                sql_guardar = "INSERT INTO repuesto (marca, linea, modelo, precio, cantidad_repuesto) VALUES ('"+
                textfield_marca.getText()+ "', '"+ textfield_linea.getText()+"', '" + textfield_modelo.getText() 
                +"', '" + Integer.parseInt(textfield_precio.getText().trim()) + "', "+ Integer.parseInt(textfield_cantidad.getText().trim())+ ", " + sede +")";
                
                Fachada con = new Fachada();
                Connection conexion = con.getConnection();
                Statement sentencia = conexion.createStatement();
                int num = sentencia.executeUpdate(sql_guardar);
                conexion.close();
                JOptionPane.showMessageDialog(null,"Repuesto registrado con exito. ");
                boton_guardar_inventario.setStyle("-fx-background-color: #66c00b; ");
                categoria.getSelectionModel().clearSelection();
                textfield_marca.clear();
                textfield_linea.clear();
                textfield_modelo.clear();
                textfield_precio.clear();
                textfield_cantidad.clear();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Los valores introducidos no son validos. ");
                boton_guardar_inventario.setStyle("-fx-background-color: #66c00b; ");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Por favor complete todos los campos. ");
            boton_guardar_inventario.setStyle("-fx-background-color: #66c00b; ");
        }
    }

    @FXML
    private void guardar_Inventario_Modificado (ActionEvent event) 
    {
        boton_guardar_inventario_modificacion.setStyle("-fx-background-color: #c7eb7a; ");
        
        String sql_guardar;
        
        
        if(modificacion_categoria.getValue().equals("Veh??culo") && !(textfield_marca_modificacion.getText().isEmpty()) && 
           !(textfield_linea_modificacion.getText().isEmpty()) && !(textfield_modelo_modificacion.getText().isEmpty()) &&
           !(textfield_precio_modificacion.getText().isEmpty()) && !(textfield_cantidad_modificacion.getText().isEmpty()))
        {
            try
            {
                sql_guardar = "UPDATE vehiculo SET marca = '" + textfield_marca_modificacion.getText() +"', linea = '" + textfield_linea_modificacion.getText()+ "'"
                           + ", modelo = '"+ textfield_modelo_modificacion.getText() + "', precio = " + Integer.parseInt(textfield_precio_modificacion.getText().trim())
                    + ", cantidad_vehiculo = " + Integer.parseInt(textfield_cantidad_modificacion.getText().trim()) + "WHERE id_vehiculo = " + Integer.parseInt(textfield_buscar.getText().trim());
                
                Fachada con = new Fachada();
                Connection conexion = con.getConnection();
                Statement sentencia = conexion.createStatement();
                int num = sentencia.executeUpdate(sql_guardar);
                conexion.close();
                JOptionPane.showMessageDialog(null,"Veh??culo modificado con exito. ");
                boton_guardar_inventario_modificacion.setStyle("-fx-background-color: #66c00b; ");
                modificacion_categoria.getSelectionModel().clearSelection();
                textfield_marca_modificacion.clear();
                textfield_linea_modificacion.clear();
                textfield_modelo_modificacion.clear();
                textfield_precio_modificacion.clear();
                textfield_cantidad_modificacion.clear();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
                return;
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Los valores introducidos no son validos. ");
                boton_guardar_inventario_modificacion.setStyle("-fx-background-color: #66c00b; ");
                return;
            }
        }
        else if(modificacion_categoria.getValue() == "Repuesto" && !(textfield_marca_modificacion.getText().isEmpty()) && !(textfield_linea_modificacion.getText().isEmpty()) &&
                !(textfield_modelo_modificacion.getText().isEmpty()) && !(textfield_precio_modificacion.getText().isEmpty()) && !(textfield_cantidad_modificacion.getText().isEmpty()))
        { 
            try
            {
                sql_guardar = "UPDATE repuesto SET marca = '" + textfield_marca_modificacion.getText() + "', linea = '" + textfield_linea_modificacion.getText() + "'"
                           + ", modelo = '" + textfield_modelo_modificacion.getText() +"', precio = " + Integer.parseInt(textfield_precio_modificacion.getText().trim())
                    + ", cantidad_repuesto = " + Integer.parseInt(textfield_cantidad_modificacion.getText().trim()) + "WHERE id_repuesto = " + Integer.parseInt(textfield_buscar.getText().trim());
                
                Fachada con = new Fachada();
                Connection conexion = con.getConnection();
                Statement sentencia = conexion.createStatement();
                int num = sentencia.executeUpdate(sql_guardar);
                conexion.close();
                JOptionPane.showMessageDialog(null,"Repuesto modificado con exito. ");
                boton_guardar_inventario_modificacion.setStyle("-fx-background-color: #66c00b; ");
                modificacion_categoria.getSelectionModel().clearSelection();
                textfield_marca_modificacion.clear();
                textfield_linea_modificacion.clear();
                textfield_modelo_modificacion.clear();
                textfield_precio_modificacion.clear();
                textfield_cantidad_modificacion.clear();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
                return;
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Los valores introducidos no son validos. ");
                boton_guardar_inventario_modificacion.setStyle("-fx-background-color: #66c00b; ");
                return;
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Por favor complete todos los campos. ");
            boton_guardar_inventario.setStyle("-fx-background-color: #66c00b; ");
            return;
        }

        textfield_marca_modificacion.setDisable(true);
        textfield_linea_modificacion.setDisable(true);
        textfield_modelo_modificacion.setDisable(true);
        textfield_precio_modificacion.setDisable(true);
        textfield_cantidad_modificacion.setDisable(true);
    }

    @FXML
    private void buscarArticulo (ActionEvent event) 
    {
        String sql_buscar;

        if (textfield_buscar.getText().trim().equals(""))
        {
            return;
        }

        if(modificacion_categoria.getValue() == "Veh??culo")
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            try
            {
                sql_buscar = "SELECT * FROM vehiculo WHERE id_vehiculo = " + textfield_buscar.getText();
                Statement sentencia = conexion.createStatement();
                ResultSet rs = sentencia.executeQuery(sql_buscar);
                if(rs == null)
                {
                    JOptionPane.showMessageDialog(null,"Vehiculo inexistente ");
                    textfield_marca_modificacion.clear();
                    textfield_marca_modificacion.setDisable(true);
                    textfield_linea_modificacion.clear();
                    textfield_linea_modificacion.setDisable(true);
                    textfield_modelo_modificacion.clear();
                    textfield_modelo_modificacion.setDisable(true);
                    textfield_precio_modificacion.clear();
                    textfield_precio_modificacion.setDisable(true);
                    textfield_cantidad_modificacion.clear();
                    textfield_cantidad_modificacion.setDisable(true);
                }
                while(rs.next())
                {
                    textfield_marca_modificacion.setText(rs.getString(3));
                    textfield_marca_modificacion.setDisable(false);
                    textfield_linea_modificacion.setText(rs.getString(2));
                    textfield_linea_modificacion.setDisable(false);
                    textfield_modelo_modificacion.setText(rs.getString(5));
                    textfield_modelo_modificacion.setDisable(false);
                    textfield_precio_modificacion.setText(rs.getString(4));
                    textfield_precio_modificacion.setDisable(false);
                    textfield_cantidad_modificacion.setText(rs.getString(6));
                    textfield_cantidad_modificacion.setDisable(false);
                    boton_guardar_inventario_modificacion.setDisable(false);
                }
                conexion.close();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
            }
        }
        else if(modificacion_categoria.getValue() == "Repuesto")
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            try
            {
                sql_buscar = "SELECT * FROM repuesto WHERE id_repuesto = " + textfield_buscar.getText();
                Statement sentencia = conexion.createStatement();
                ResultSet rs = sentencia.executeQuery(sql_buscar);
                if(rs == null)
                {
                    JOptionPane.showMessageDialog(null,"Repuesto inexistente ");
                    textfield_marca_modificacion.clear();
                    textfield_marca_modificacion.setDisable(true);
                    textfield_linea_modificacion.clear();
                    textfield_linea_modificacion.setDisable(true);
                    textfield_modelo_modificacion.clear();
                    textfield_modelo_modificacion.setDisable(true);
                    textfield_precio_modificacion.clear();
                    textfield_precio_modificacion.setDisable(true);
                    textfield_cantidad_modificacion.clear();
                    textfield_cantidad_modificacion.setDisable(true);
                }
                while(rs.next())
                {
                    textfield_marca_modificacion.setText(rs.getString(3));
                    textfield_marca_modificacion.setDisable(false);
                    textfield_linea_modificacion.setText(rs.getString(2));
                    textfield_linea_modificacion.setDisable(false);
                    textfield_modelo_modificacion.setText(rs.getString(5));
                    textfield_modelo_modificacion.setDisable(false);
                    textfield_precio_modificacion.setText(rs.getString(4));
                    textfield_precio_modificacion.setDisable(false);
                    textfield_cantidad_modificacion.setText(rs.getString(6));
                    textfield_cantidad_modificacion.setDisable(false);
                    boton_guardar_inventario_modificacion.setDisable(false);
                }
                conexion.close();
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Seleccione primero una categoria. ");
        } 
    }

    public void login(String privilegios, int sede)
    {
        this.privilegios = privilegios;
        this.sede = sede;

        cargarInventario();

        switch (privilegios)
        {
            case "gerente":
                break;
            case "vendedor":
                tabPane.getTabs().remove(1, 4);
                break;
            case "jefe de taller":
                tabPane.getTabs().remove(0);
                tabPane.getTabs().remove(1, 3);
                break;
        }
    }
}
