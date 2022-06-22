/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Fachada;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author JUAN DAVID
 */
public class VentasController implements Initializable
{

    @FXML
    private TextField buscar_cotizacion_venta;
    @FXML
    private Button boton_buscar_cotizacion_venta;
    @FXML
    private TextField cedula_cliente_venta;
    @FXML
    private TextField nombre_cliente_venta;
    @FXML
    private TextField telefono_cliente_venta;
    @FXML
    private TextField direccion_cliente_venta;
    @FXML
    private TextField correo_cliente_venta;
    @FXML
    private TextField genero_cliente_venta;
    @FXML
    private TextField marca_venta;
    @FXML
    private TextField linea_venta;
    @FXML
    private TextField modelo_venta;
    @FXML
    private TextField precio_venta;
    @FXML
    private TextField cantidad_venta;
    @FXML
    private Button boton_vender;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
    
    @FXML
    private void buscar_cotizacion_venta(ActionEvent event) 
    {
        String sql_buscar_cotizacion;
        String sql_buscar_cliente;
        String sql_buscar_articulo;
        if(!(buscar_cotizacion_venta.getText().isEmpty()))
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            try
            {
                sql_buscar_cotizacion = "SELECT * FROM cotizacion WHERE id_cotizacion = " + buscar_cotizacion_venta.getText();
                Statement sentencia = conexion.createStatement();
                Statement sentencia1 = conexion.createStatement();
                Statement sentencia2 = conexion.createStatement();
                ResultSet cotizacion = sentencia.executeQuery(sql_buscar_cotizacion);
                
                while(cotizacion.next())
                {   
                    sql_buscar_cliente = "SELECT * FROM cliente WHERE cedula_cliente = " + cotizacion.getString(3);
                    ResultSet cliente = sentencia1.executeQuery(sql_buscar_cliente);
                    sql_buscar_articulo = "SELECT * FROM vehiculo WHERE id_vehiculo = " + cotizacion.getString(2);
                    ResultSet articulo = sentencia2.executeQuery(sql_buscar_articulo);
                    cliente.next();
                    articulo.next();
                    cedula_cliente_venta.setText(cliente.getString(1));
                    nombre_cliente_venta.setText(cliente.getString(2));
                    telefono_cliente_venta.setText(cliente.getString(4));
                    telefono_cliente_venta.setEditable(true);
                    direccion_cliente_venta.setText(cliente.getString(3));
                    direccion_cliente_venta.setEditable(true);
                    correo_cliente_venta.setText(cliente.getString(6));
                    correo_cliente_venta.setEditable(true);
                    genero_cliente_venta.setText(cliente.getString(5));
                    marca_venta.setText(articulo.getString(3));
                    linea_venta.setText(articulo.getString(2));
                    modelo_venta.setText(articulo.getString(5));
                    precio_venta.setText(articulo.getString(4));
                    cantidad_venta.setEditable(true);
                    boton_vender.setDisable(false);
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
            JOptionPane.showMessageDialog(null,"Ingrese el codigo de la cotización. ");
        }
    }
    
    
    @FXML
    private void vender_articulo(ActionEvent event) 
    {
        String sql_buscar_cotizacion;
        String sql_buscar_cliente;
        String sql_buscar_articulo;
        String sql_actualizar_inventario;
        String sql_actualizar_cliente;
        String sql_actualizar_compra;
        
        if(!(nombre_cliente_venta.getText().isEmpty()) && !(telefono_cliente_venta.getText().isEmpty()) && !(direccion_cliente_venta.getText().isEmpty())
             && !(correo_cliente_venta.getText().isEmpty()) && !(cantidad_venta.getText().isEmpty()))
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();
            try
            {
                sql_buscar_cotizacion = "SELECT * FROM cotizacion WHERE id_cotizacion = " + buscar_cotizacion_venta.getText();
                Statement sentencia = conexion.createStatement();
                Statement sentencia1 = conexion.createStatement();
                Statement sentencia2 = conexion.createStatement();
                Statement sentencia3 = conexion.createStatement();
                Statement sentencia4 = conexion.createStatement();
                Statement sentencia5 = conexion.createStatement();
                
                ResultSet cotizacion = sentencia.executeQuery(sql_buscar_cotizacion);
                if(cotizacion == null)
                {
                    JOptionPane.showMessageDialog(null,"Digite el codigo de la cotización. ");
                }
                
                cotizacion.next();
                String id_cotizacion = cotizacion.getString(1);
                String id_vendedor = cotizacion.getString(4);
                sql_buscar_cliente = "SELECT * FROM cliente WHERE cedula_cliente = " + cotizacion.getString(3);
                ResultSet cliente = sentencia1.executeQuery(sql_buscar_cliente);
                sql_buscar_articulo = "SELECT * FROM vehiculo WHERE id_vehiculo = " + cotizacion.getString(2);
                ResultSet articulo = sentencia2.executeQuery(sql_buscar_articulo);
                articulo.next();
                cliente.next();
                if(Integer.parseInt(cantidad_venta.getText().trim()) <= Integer.parseInt(articulo.getString(6)))
                {
                    sql_actualizar_inventario = "UPDATE vehiculo SET cantidad_vehiculo = " + (Integer.parseInt(articulo.getString(6)) - Integer.parseInt(cantidad_venta.getText().trim()))
                                              + " WHERE id_vehiculo = " +  articulo.getString(1);
                    int actualizar_inventario = sentencia3.executeUpdate(sql_actualizar_inventario);
                    sql_actualizar_cliente = "UPDATE cliente SET direccion_cliente = " + "'" + direccion_cliente_venta.getText() + "'" + ", telefono_cliente = " + "'" + telefono_cliente_venta.getText() +"'"
                                              + ", correo_electronico = " + "'" + correo_cliente_venta.getText() +"'" +  "WHERE cedula_cliente = " +  cliente.getString(1);
                    int actualizar_cliente = sentencia4.executeUpdate(sql_actualizar_cliente);
                    sql_actualizar_compra = "INSERT INTO compra (id_cotizacion,id_vendedor) VALUES (" + id_cotizacion + " ," +  id_vendedor + ")";
                    int actualizar_compra = sentencia5.executeUpdate(sql_actualizar_compra);
                    JOptionPane.showMessageDialog(null,"Venta realizada con exito. ");
                    conexion.close();
                    buscar_cotizacion_venta.setText("");
                    cedula_cliente_venta.setText("");
                    nombre_cliente_venta.setText("");
                    telefono_cliente_venta.setText("");
                    telefono_cliente_venta.setEditable(false);
                    direccion_cliente_venta.setText("");
                    direccion_cliente_venta.setEditable(false);
                    correo_cliente_venta.setText("");
                    correo_cliente_venta.setEditable(false);
                    genero_cliente_venta.setText("");
                    marca_venta.setText("");
                    linea_venta.setText("");
                    modelo_venta.setText("");
                    precio_venta.setText("");
                    cantidad_venta.setText("");
                    cantidad_venta.setEditable(false);
                    boton_vender.setDisable(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Cantidad en inventario insuficiente.");
                }  
            }
            catch(SQLException sqle)
            {
                JOptionPane.showMessageDialog(null,"Error: " + 
                    sqle.getMessage());
            }
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Los valores introducidos no son validos. ");
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Por favor llene todos los campos. ");
        }
        
    }
    
}
