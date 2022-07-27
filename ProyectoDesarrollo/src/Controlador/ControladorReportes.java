/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Cotizacion;
import Modelo.Empleado;
import Modelo.Fachada;
import Modelo.Repuesto;
import Modelo.Vehiculo;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 */
public class ControladorReportes implements Initializable 
{
    private ObservableList<PieChart.Data> datos_empleados_pieChart = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> datos_vehiculos_pieChart_auxiliar = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> datos_repuestos_pieChart_auxiliar = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> datosClientesComunaGrafica = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> datosClientesGeneroGrafica = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> datosCotizaciones = FXCollections.observableArrayList();
    private ObservableList<Empleado> datos_empleados_tabla;
    private ObservableList<Vehiculo> datos_vehiculos_tabla;
    private ObservableList<Repuesto> datos_repuestos_tabla;
    private ObservableList<Cliente> datosClientesTabla;
    private ObservableList<Cotizacion> datosCotizacionesTabla;
    
    
    @FXML
    private TableView<Empleado> tabla_empleados_reporte;
    @FXML
    private TableColumn<Empleado, String> nombre_empleado_reporte;
    @FXML
    private TableColumn<Empleado, String> cargo_empleado_reporte;
    @FXML
    private TableColumn<Empleado, String> sede_empleado_reporte;
    @FXML
    private TableColumn<Empleado, String> estado_empleado_reporte;
    @FXML
    private PieChart pieChart_empleado_reporte;
    @FXML
    private TableView<Vehiculo> tabla_vehiculos_reporte;
    @FXML
    private TableColumn<Vehiculo,String > marca_vehiculos_reporte;
    @FXML
    private TableColumn<Vehiculo,String> linea_vehiculos_reporte;
    @FXML
    private TableColumn<Vehiculo,String> modelo_vehiculos_reporte;
    @FXML
    private TableColumn<Vehiculo,Integer> cantidad_vehiculos_reporte;
    @FXML
    private TableView<Repuesto> tabla_repuestos_reporte;
    @FXML
    private TableColumn<Repuesto, String> marca_repuestos_reporte;
    @FXML
    private TableColumn<Repuesto, String> linea_repuestos_reporte;
    @FXML
    private TableColumn<Repuesto, String> modelo_repuestos_reporte;
    @FXML
    private TableColumn<Repuesto, Integer> cantidad_repuestos_reporte;
    @FXML
    private Button boton_grafica_vehiculo;
    @FXML
    private Pane panel_grafica_vehiculo;
    @FXML
    private PieChart grafica_vehiculos_reporte_auxiliar;
    @FXML
    private Button cerrar_panel_grafica;
    @FXML
    private Pane panel_grafica_repuesto;
    @FXML
    private PieChart grafica_repuesto_reporte_auxiliar1;
    @FXML
    private Button cerrar_panel_grafica1;
    @FXML
    private Button boton_grafica_repuesto;
    @FXML
    private PieChart graGeneroClientes;
    @FXML
    private TableView<Cliente> tablaClientes;
    @FXML
    private TableColumn<Cliente, String> columnaNombreCliente;
    @FXML
    private TableColumn<Cliente, String> columnaDireccionCliente;
    @FXML
    private TableColumn<Cliente, String> columnaGeneroCliente;
    @FXML
    private TableColumn<Cliente, Integer> columnaComunaCliente;
    
    @FXML
    private TableColumn<Cliente, String> columnaClienteNombre;
    @FXML
    private TableColumn<Vehiculo, String> columnaVehiculo;
    @FXML
    private TableColumn<Empleado, String> columnaVendedor;
    @FXML
    private TableView<Cotizacion> tablaCotizaciones;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        datos_empleados_tabla = FXCollections.observableArrayList();
        datos_vehiculos_tabla = FXCollections.observableArrayList();
        datos_repuestos_tabla = FXCollections.observableArrayList();
        datosClientesTabla = FXCollections.observableArrayList();
        datosCotizacionesTabla = FXCollections.observableArrayList();
        
        nombre_empleado_reporte.setCellValueFactory( new PropertyValueFactory("nombre"));
        cargo_empleado_reporte.setCellValueFactory( new PropertyValueFactory("cargo"));
        sede_empleado_reporte.setCellValueFactory( new PropertyValueFactory("sede"));
        estado_empleado_reporte.setCellValueFactory( new PropertyValueFactory("estado"));
        
        marca_vehiculos_reporte.setCellValueFactory( new PropertyValueFactory("marca"));
        linea_vehiculos_reporte.setCellValueFactory( new PropertyValueFactory("linea"));
        modelo_vehiculos_reporte.setCellValueFactory( new PropertyValueFactory("modelo"));
        cantidad_vehiculos_reporte.setCellValueFactory( new PropertyValueFactory("cantidad"));
        
        marca_repuestos_reporte.setCellValueFactory( new PropertyValueFactory("marca"));
        linea_repuestos_reporte.setCellValueFactory( new PropertyValueFactory("linea"));
        modelo_repuestos_reporte.setCellValueFactory( new PropertyValueFactory("modelo"));
        cantidad_repuestos_reporte.setCellValueFactory( new PropertyValueFactory("cantidad"));
        
        columnaNombreCliente.setCellValueFactory( new PropertyValueFactory("nombre"));
        columnaDireccionCliente.setCellValueFactory( new PropertyValueFactory("direccion"));
        columnaGeneroCliente.setCellValueFactory( new PropertyValueFactory("genero"));
        columnaComunaCliente.setCellValueFactory( new PropertyValueFactory("comuna"));
        
        columnaClienteNombre.setCellValueFactory( new PropertyValueFactory("nombreCliente"));
        columnaVehiculo.setCellValueFactory( new PropertyValueFactory("vehiculoCotizado"));
        columnaVendedor.setCellValueFactory( new PropertyValueFactory("nombreVendedor"));
                
        reporte_empleados();
        reporte_inventario();
        reporteClientes();
        reporteCotizaciones();
    }    
    
    
    private void reporte_empleados()
    {
        datos_empleados_pieChart.clear();
        datos_empleados_tabla.clear();
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
                String sql_buscar_sede = "SELECT nombre_sede FROM sede WHERE id_Sede = " + rs.getString(8);
                Statement sentencia3 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs3 = sentencia3.executeQuery(sql_buscar_sede);
                rs3.next();
                vendedores += 1;
                datos_empleados_tabla.add(new Empleado(rs.getString(4), rs.getString(3),rs3.getString(1),rs.getString(9)));
            }
     
            while (rs2.next())
            {
                String sql_buscar_sede = "SELECT nombre_sede FROM sede WHERE id_Sede = " + rs2.getString(8);
                Statement sentencia4 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                ResultSet rs4 = sentencia4.executeQuery(sql_buscar_sede);
                rs4.next();
                datos_empleados_tabla.add(new Empleado(rs2.getString(4), rs2.getString(3),rs4.getString(1),rs2.getString(9)));
                jefes += 1;
            }
            
            rs.beforeFirst();
            rs2.beforeFirst();
            rs.next();
            rs2.next();
            datos_empleados_pieChart.add(new PieChart.Data(rs.getString("rol"),vendedores));
            datos_empleados_pieChart.add(new PieChart.Data(rs2.getString("rol"),jefes));
            pieChart_empleado_reporte.setData(datos_empleados_pieChart);
            tabla_empleados_reporte.setItems(datos_empleados_tabla);
            
            conexion.close();
        } catch (SQLException sqle) 
        {
            JOptionPane.showMessageDialog(null,"Error: " + sqle.getMessage());
        }
    }
    
    
    private void reporte_inventario()
    {
        datos_vehiculos_pieChart_auxiliar.clear();
        datos_vehiculos_tabla.clear();
        
        try 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            String sql = "SELECT * FROM vehiculo;";
            Statement sentencia = conexion.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            
            while(rs.next())
            {

                datos_vehiculos_tabla.add(new Vehiculo(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(5), 
                                                Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(6))));
                datos_vehiculos_pieChart_auxiliar.add(new PieChart.Data(rs.getString("linea"),Integer.parseInt(rs.getString(6))));
            }
            
            grafica_vehiculos_reporte_auxiliar.setData(datos_vehiculos_pieChart_auxiliar);
            tabla_vehiculos_reporte.setItems(datos_vehiculos_tabla);
            
            sql = "SELECT * FROM repuesto;";

            rs = sentencia.executeQuery(sql);

            while(rs.next())
            {
                datos_repuestos_tabla.add(new Repuesto(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(5), 
                                                Integer.parseInt(rs.getString(4)), Integer.parseInt(rs.getString(6))));
                datos_repuestos_pieChart_auxiliar.add(new PieChart.Data(rs.getString("linea"),Integer.parseInt(rs.getString(6))));
            }
            
            grafica_repuesto_reporte_auxiliar1.setData(datos_repuestos_pieChart_auxiliar);
            tabla_repuestos_reporte.setItems(datos_repuestos_tabla);
            
            conexion.close();
        } catch (SQLException sqle) 
        {
            JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
        }
        
    }
    
    private void reporteClientes()
    {
        datosClientesComunaGrafica.clear();
        datosClientesGeneroGrafica.clear();
        datosClientesTabla.clear();
        
        Timer temporizador = new Timer();
        TimerTask tarea; 
        tarea = new TimerTask() 
        {
            @Override
            public void run()
            {
                try 
                {
                    Fachada con = new Fachada(); 
                    Connection conexion = con.getConnection();
                    String sql = "SELECT * FROM cliente;";
                    Statement sentencia = conexion.createStatement();
                    ResultSet rs = sentencia.executeQuery(sql);
                    
                    while(rs.next())
                    {
                        datosClientesTabla.add(new Cliente(rs.getString(2), rs.getString(3), rs.getString(5), rs.getInt(7)));
                    }
                    tablaClientes.setItems(datosClientesTabla);
                    
                                    
                    sql = "SELECT count(genero_cliente) FROM cliente WHERE genero_cliente = 'Masculino';";
                    Statement sentencia1 = conexion.createStatement();
                    ResultSet rs1 = sentencia1.executeQuery(sql);
                    
                    sql = "SELECT count(genero_cliente) FROM cliente WHERE genero_cliente = 'Femenino';";
                    Statement sentencia2 = conexion.createStatement();
                    ResultSet rs2 = sentencia2.executeQuery(sql);
                    
                    rs1.next();
                    rs2.next();
                    
                    datosClientesGeneroGrafica.add(new PieChart.Data("Masculino", rs1.getInt(1)));
                    datosClientesGeneroGrafica.add(new PieChart.Data("Femenino", rs2.getInt(1)));
                    graGeneroClientes.setData(datosClientesGeneroGrafica);
                    
                    sentencia1.close();
                    sentencia2.close();
                    conexion.close();
                }
                catch (SQLException sqle)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + sqle.getMessage());
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
    
    private void reporteCotizaciones()
    {
        
        datosCotizacionesTabla.clear();
        
        Timer temporizador = new Timer();
        TimerTask tarea; 
        tarea = new TimerTask() 
        {
            @Override
            public void run()
            {
                try 
                {
                    Fachada con = new Fachada();
                    Connection conexion = con.getConnection();
                    String sqlAuxCotizacion = "SELECT * FROM cotizacion";
                    Statement sentencia = conexion.createStatement();
                    ResultSet rs = sentencia.executeQuery(sqlAuxCotizacion);
            
                    while (rs.next())
                    {

                        String sqlNombreCliente = "SELECT * FROM cliente WHERE cedula_cliente = " + rs.getInt(3);
                        Statement sentencia1 = conexion.createStatement();
                        ResultSet rs1 = sentencia1.executeQuery(sqlNombreCliente); 

                        String sqlVehiculoCotizado = "SELECT * FROM vehiculo WHERE id_vehiculo = " + rs.getInt(2);
                        Statement sentencia2 = conexion.createStatement();
                        ResultSet rs2 = sentencia2.executeQuery(sqlVehiculoCotizado);

                        String sqlVendedorEncargado = "SELECT * FROM empleado WHERE cedula_empleado = " + rs.getInt(4);
                        Statement sentencia3 = conexion.createStatement();
                        ResultSet rs3 = sentencia3.executeQuery(sqlVendedorEncargado);

                        if(rs1.next() && rs2.next() && rs3.next())
                        {
                            datosCotizacionesTabla.add(new Cotizacion(rs1.getString(2), rs2.getString(3), rs3.getString(4)));
                        }
                        sentencia1.close();
                        sentencia2.close();
                        sentencia3.close();
                    }
                    tablaCotizaciones.setItems(datosCotizacionesTabla);

                    sentencia.close();
                   
                    
                    conexion.close();
                    } catch (SQLException sqle) 
                    {
                        JOptionPane.showMessageDialog(null,"Error: " + 
                                sqle.getMessage());
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

    
    
    @FXML
    private void mostrar_grafico_vehiculo(ActionEvent event) 
    {
        grafica_vehiculos_reporte_auxiliar.setData(datos_vehiculos_pieChart_auxiliar);
        panel_grafica_vehiculo.setVisible(true);
        //System.out.println("VEHICULO: "+ pieChart_vehiculos_reporte.isVisible());
    }

    @FXML
    private void mostrar_grafico_repuesto(ActionEvent event) 
    {
        grafica_repuesto_reporte_auxiliar1.setData(datos_repuestos_pieChart_auxiliar);
        panel_grafica_repuesto.setVisible(true);
        //System.out.println("REPUESTO: "+ pieChart_repuestos_reporte.isVisible());
    }

    @FXML
    private void cerrar_grafico(ActionEvent event) 
    {
        panel_grafica_vehiculo.setVisible(false);
        panel_grafica_repuesto.setVisible(false);
    }
    
}
