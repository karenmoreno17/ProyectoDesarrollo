/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Compra;
import Modelo.Fachada;
import Modelo.Orden;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author JUAN DAVID
 */
public class ControladorInicio implements Initializable 
{
    ObservableList<PieChart.Data> datos_empleados = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> datos_vehiculos = FXCollections.observableArrayList();
    ObservableList<Compra> datos_compras; 
    ObservableList<Orden> datos_ordenes;
    
    @FXML
    private Button empleados_inicio;
    @FXML
    private Button ventas_inicio;
    @FXML
    private Button vehiculo_inicio;
    @FXML
    private Button orden_inicio;
    @FXML
    private Pane panel_bienvenida;
    @FXML
    private Pane panel_vehiculos_inicio;
    @FXML
    private Pane panel_empleado_inicio;
    @FXML
    private Pane panel_ventas_inicio;
    @FXML
    private Pane panel_ordenes_inicio;
    @FXML
    private PieChart pieChart_empleados;
    @FXML
    private PieChart pieChart_vehiculos;
    @FXML
    private Label nombre_usuario_inicio;
    @FXML
    private TableView<Compra> tabla_ventas;
    @FXML
    private TableColumn<Compra, Integer> id_venta_inicio;
    @FXML
    private TableColumn<Compra, Integer> precio_inicio;
    @FXML
    private TableColumn<Compra, String> vendedor_inicio;
    @FXML
    private TableColumn<Compra, String> vehiculo_inicio_tabla;
    @FXML
    private TableView<Orden> tabla_ordenes;
    @FXML
    private TableColumn<Orden,Integer > id_orden_inicio;
    @FXML
    private TableColumn<Orden, String> jefe_inicio;
    @FXML
    private TableColumn<Orden, String> repuesto_inicio;
    @FXML
    private TableColumn<Orden, String> proceso_inicio;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        datos_compras = FXCollections.observableArrayList();
        datos_ordenes = FXCollections.observableArrayList();
        id_venta_inicio.setCellValueFactory( new PropertyValueFactory("id_compra"));
        precio_inicio.setCellValueFactory( new PropertyValueFactory("precio_vehiculo"));
        vendedor_inicio.setCellValueFactory( new PropertyValueFactory("nombre_vendedor"));
        vehiculo_inicio_tabla.setCellValueFactory( new PropertyValueFactory("nombre_vehiculo"));
        
        id_orden_inicio.setCellValueFactory( new PropertyValueFactory("id_orden"));
        jefe_inicio.setCellValueFactory( new PropertyValueFactory("nombre_jefe"));
        repuesto_inicio.setCellValueFactory( new PropertyValueFactory("nombre_repuesto"));
        proceso_inicio.setCellValueFactory( new PropertyValueFactory("proceso"));
        
    }
    
    @FXML
    private void mostrar_empleados(ActionEvent event)
    {
        panel_bienvenida.setVisible(false);
        panel_vehiculos_inicio.setVisible(false);
        panel_empleado_inicio.setVisible(true);
        panel_ventas_inicio.setVisible(false);
        panel_ordenes_inicio.setVisible(false);
        datos_empleados.clear();
        String sql_buscar_vendedor = "SELECT * FROM empleado WHERE rol = 'vendedor'";
        String sql_buscar_jefe = "SELECT * FROM empleado WHERE rol = 'Jefe de taller'";
        double vendedores = 0.0;
        double jefes = 0.0;
        try 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = sentencia.executeQuery(sql_buscar_vendedor);
            Statement sentencia2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2 = sentencia2.executeQuery(sql_buscar_jefe);
            
            while (rs.next())
            {
                vendedores += 1;
            }
     
            while (rs2.next())
            {
                jefes += 1;
            }
            
            rs.beforeFirst();
            rs2.beforeFirst();
            rs.next();
            rs2.next();
            datos_empleados.add(new PieChart.Data(rs.getString("rol"),vendedores));
            datos_empleados.add(new PieChart.Data(rs2.getString("rol"),jefes));
            pieChart_empleados.setData(datos_empleados);
            
            conexion.close();
        } catch (SQLException sqle) 
        {
            JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
        }
    }
    
    
    @FXML
    private void mostrar_vehiculos_repuestos(ActionEvent event)
    {
        panel_bienvenida.setVisible(false);
        panel_vehiculos_inicio.setVisible(true);
        panel_empleado_inicio.setVisible(false);
        panel_ventas_inicio.setVisible(false);
        panel_ordenes_inicio.setVisible(false);
        datos_vehiculos.clear();
        
        String sql_buscar_vehiculo = "SELECT * FROM vehiculo";
        String sql_buscar_repuesto = "SELECT * FROM repuesto";
        double vehiculos = 0.0;
        double repuestos = 0.0;
        try 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = sentencia.executeQuery(sql_buscar_vehiculo);
            Statement sentencia2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2 = sentencia2.executeQuery(sql_buscar_repuesto);
            
            while (rs.next())
            {
                vehiculos += 1;
            }
     
            while (rs2.next())
            {
                repuestos += 1;
            }
            
            rs.beforeFirst();
            rs2.beforeFirst();
            rs.next();
            rs2.next();
            datos_vehiculos.add(new PieChart.Data("Vehículos",vehiculos));
            datos_vehiculos.add(new PieChart.Data("Repuestos",repuestos));
            pieChart_vehiculos.setData(datos_vehiculos);
            
            conexion.close();
        } catch (SQLException sqle) 
        {
            JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
        }
    }
    
    
    @FXML
    private void mostrar_ventas()
    {
        panel_bienvenida.setVisible(false);
        panel_vehiculos_inicio.setVisible(false);
        panel_empleado_inicio.setVisible(false);
        panel_ventas_inicio.setVisible(true);
        panel_ordenes_inicio.setVisible(false);
        
        datos_compras.clear();
        String sql_buscar_compra = "SELECT * FROM compra";
        
        try 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql_buscar_compra);
            
            while (rs.next())
            {
                String sql_buscar_cotizacion = "SELECT * FROM cotizacion WHERE id_cotizacion = " + rs.getString(2);
                Statement sentencia2 = conexion.createStatement();
                ResultSet rs2 = sentencia2.executeQuery(sql_buscar_cotizacion);
                rs2.next();
                String sql_buscar_vehiculo = "SELECT linea,precio FROM vehiculo WHERE id_vehiculo = " + rs2.getString(2);
                Statement sentencia3 = conexion.createStatement();
                ResultSet rs3 = sentencia3.executeQuery(sql_buscar_vehiculo);
                rs3.next();
                String sql_buscar_vendedor = "SELECT nombre_empleado FROM empleado WHERE cedula_empleado = " + rs.getString(3);
                Statement sentencia4 = conexion.createStatement();
                ResultSet rs4 = sentencia4.executeQuery(sql_buscar_vendedor);
                rs4.next();
                datos_compras.add(new Compra(Integer.parseInt(rs.getString(1)),rs3.getString(1), rs4.getString(1),Integer.parseInt(rs3.getString(2))));
                sentencia2.clearBatch();
                sentencia3.clearBatch();
                sentencia4.clearBatch();
            }
     
            conexion.close();
            tabla_ventas.setItems(datos_compras);
            
        } catch (SQLException sqle) 
        {
            JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
        }
    }

    
    @FXML
    private void mostrar_ordenes(ActionEvent event) 
    {
        panel_bienvenida.setVisible(false);
        panel_vehiculos_inicio.setVisible(false);
        panel_empleado_inicio.setVisible(false);
        panel_ventas_inicio.setVisible(false);
        panel_ordenes_inicio.setVisible(true);
        
        datos_ordenes.clear();
        String sql_buscar_orden = "SELECT * FROM orden_trabajo";
        
        try 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql_buscar_orden);
            
            while (rs.next())
            {
                String sql_buscar_repuesto = "SELECT linea FROM repuesto WHERE id_repuesto = " + rs.getString(3);
                Statement sentencia3 = conexion.createStatement();
                ResultSet rs3 = sentencia3.executeQuery(sql_buscar_repuesto);
                rs3.next();
                String sql_buscar_jefe = "SELECT nombre_empleado FROM empleado WHERE cedula_empleado = " + rs.getString(2);
                Statement sentencia4 = conexion.createStatement();
                ResultSet rs4 = sentencia4.executeQuery(sql_buscar_jefe);
                rs4.next();
                System.out.println(rs.getString(5));
                datos_ordenes.add(new Orden(Integer.parseInt(rs.getString(1)),rs3.getString(1), rs4.getString(1),rs.getString(5)));
                sentencia3.clearBatch();
                sentencia4.clearBatch();
            }
     
            conexion.close();
            tabla_ordenes.setItems(datos_ordenes);
            
        } catch (SQLException sqle) 
        {
            JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
        }
    }

    @FXML
    private void resetearColorEmpleado(MouseEvent event) 
    {
        empleados_inicio.setStyle("-fx-background-color:#ff3f3f;");
    }

    @FXML
    private void cambiarColorEmpleado(MouseEvent event) 
    {
        empleados_inicio.setStyle("-fx-background-color: #f54021;");
    }

    @FXML
    private void resetearColorVenta(MouseEvent event)
    {
        ventas_inicio.setStyle("-fx-background-color: #77DD77;");
    }

    @FXML
    private void cambiarColorVenta(MouseEvent event) 
    {
        ventas_inicio.setStyle("-fx-background-color: #98FF98;");
    }

    @FXML
    private void resetearColorVR(MouseEvent event) 
    {
        vehiculo_inicio.setStyle("-fx-background-color: #31AAC1;");
    }

    @FXML
    private void cambiarColorVR(MouseEvent event)
    {
        vehiculo_inicio.setStyle("-fx-background-color: #00AAE4;");
    }

    @FXML
    private void resetearColorOrden(MouseEvent event) 
    {
        orden_inicio.setStyle("-fx-background-color: #CCA9DD;");
    }

    @FXML
    private void cambiarColorOrden(MouseEvent event) 
    {
        orden_inicio.setStyle("-fx-background-color: #E1CBEE;");
    }
    
    
}
