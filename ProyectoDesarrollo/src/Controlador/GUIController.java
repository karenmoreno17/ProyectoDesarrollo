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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Fachada;
import modelo.Repuesto;
import modelo.Vehiculo;

/**
 * FXML Controller class
 *
 * @author Karen Daniela
 */
public class GUIController implements Initializable
{

    private ObservableList<Vehiculo> datosV;
    private ObservableList<Repuesto> datosR;
    private Fachada conexion;
    
    @FXML
    private Pane ventanaPrincipal, ventanaMenu;
    @FXML
    private Pane panelPrincipal;
    @FXML
    private Pane panelBienvenida;
    @FXML
    private Button bCerrar, bContinuar, bIngresar, boton_inicio, boton_usuario, boton_inventario,boton_sede,boton_reporte;
    @FXML
    private TextField tCedula, tAuxContrasena;
    @FXML
    private PasswordField tContrasena;
    @FXML
    private GridPane panelIngreso;
    @FXML
    private CheckBox cbContrasena;
    @FXML
    private Pane panelOpciones;
    @FXML
    private AnchorPane panelBotones;
    @FXML
    private Pane panelContenedor;
    @FXML
    private Pane panelUsuario;
    @FXML
    private Pane panelInventario;
    @FXML
    private Pane panelSedes;
    @FXML
    private Pane panelReportes;
    @FXML
    private Pane panelInicio;
    
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
      
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        conexion = new Fachada();

        datosV = FXCollections.observableArrayList();

        tcVLinea.setCellValueFactory( new PropertyValueFactory("linea"));
        tcVMarca.setCellValueFactory( new PropertyValueFactory("marca"));
        tcVModelo.setCellValueFactory( new PropertyValueFactory("modelo"));
        tcVPrecio.setCellValueFactory( new PropertyValueFactory("precio"));
        tcVCantidad.setCellValueFactory( new PropertyValueFactory("cantidad"));

        datosR = FXCollections.observableArrayList();

        tcRLinea.setCellValueFactory( new PropertyValueFactory("linea"));
        tcRMarca.setCellValueFactory( new PropertyValueFactory("marca"));
        tcRModelo.setCellValueFactory( new PropertyValueFactory("modelo"));
        tcRPrecio.setCellValueFactory( new PropertyValueFactory("precio"));
        tcRCantidad.setCellValueFactory( new PropertyValueFactory("cantidad"));

        cargarInventario();
    }    

    @FXML
    private void cerrar(ActionEvent event) 
    {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void continuar(ActionEvent event) 
    {
        panelBienvenida.setVisible(false);
        panelIngreso.setVisible(true);
        tContrasena.setVisible(true);
        tAuxContrasena.setVisible(false); 
    }

    @FXML
    private void ingresar(ActionEvent event) 
    {
        panelPrincipal.setVisible(false);
        ventanaMenu.setVisible(true);
    }

    @FXML
    private void activarContrasena(ActionEvent event) 
    {
        if(cbContrasena.isSelected())
        {
            tAuxContrasena.setText(tContrasena.getText());
            tAuxContrasena.setVisible(true);
            tContrasena.setVisible(false);
            return;
        }
        tContrasena.setVisible(true);
        tAuxContrasena.setVisible(false); 
        tAuxContrasena.setText("");
    }
    
    void setColor(Button boton )
    {
        boton.setStyle("-fx-background-color: #B5B2B2; ");
    }
    
    void resetColor(Button boton )
    {
        boton.setStyle("-fx-background-color: #808080; ");
    }

    @FXML
    private void botonInicio(ActionEvent event) 
    {
        setColor(boton_inicio);
        resetColor(boton_usuario);
        resetColor(boton_inventario);
        resetColor(boton_sede);
        resetColor(boton_reporte);
        panelInicio.setVisible(true);
        panelUsuario.setVisible(false);
        panelInventario.setVisible(false);
        panelSedes.setVisible(false);
        panelReportes.setVisible(false);
    }

    @FXML
    private void botonUsuario(ActionEvent event) 
    {
        setColor(boton_usuario);
        resetColor(boton_inicio);
        resetColor(boton_inventario);
        resetColor(boton_sede);
        resetColor(boton_reporte);
        panelUsuario.setVisible(true);
        panelInicio.setVisible(false);
        panelInventario.setVisible(false);
        panelSedes.setVisible(false);
        panelReportes.setVisible(false);
    }

    @FXML
    private void botonInventario(ActionEvent event) 
    {
        setColor(boton_inventario);
        resetColor(boton_usuario);
        resetColor(boton_inicio);
        resetColor(boton_sede);
        resetColor(boton_reporte);
        panelInventario.setVisible(true);
        panelUsuario.setVisible(false);
        panelInicio.setVisible(false);
        panelSedes.setVisible(false);
        panelReportes.setVisible(false);
    }

    @FXML
    private void botonSedes(ActionEvent event) 
    {
        setColor(boton_sede);
        resetColor(boton_usuario);
        resetColor(boton_inventario);
        resetColor(boton_inicio);
        resetColor(boton_reporte);
        panelSedes.setVisible(true);
        panelUsuario.setVisible(false);
        panelInventario.setVisible(false);
        panelInicio.setVisible(false);
        panelReportes.setVisible(false);
    }

    @FXML
    private void botonReportes(ActionEvent event) 
    {
        setColor(boton_reporte);
        resetColor(boton_usuario);
        resetColor(boton_inventario);
        resetColor(boton_sede);
        resetColor(boton_inicio);
        panelReportes.setVisible(true);
        panelUsuario.setVisible(false);
        panelInventario.setVisible(false);
        panelSedes.setVisible(false);
        panelInicio.setVisible(false);
    }

    private void botonRegistroInventario(ActionEvent event) 
    {
        panelInventario.setVisible(false);
    }

    @FXML
    public void cargarInventario()
    {
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();
        try{
            datosV.clear();
            datosR.clear();
            Statement st = conexion.createStatement();
            String sql = "select * from vehiculo;";

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                datosV.add(new Vehiculo(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), 
                                        Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6))));
            }

            sql = "select * from repuesto;";

            rs = st.executeQuery(sql);

            while(rs.next()){
                datosR.add(new Repuesto(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), 
                                        Integer.parseInt(rs.getString(5)), Integer.parseInt(rs.getString(6))));
            }

            st.close();
            conexion.close();

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error Mostrar: " + 
                    ex.getMessage());
        } finally {

            tvVehiculos.setItems(datosV);
            tvRepuestos.setItems(datosR);
            
        }
    }
}
