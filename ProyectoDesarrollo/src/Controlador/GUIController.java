/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Karen Daniela
 */
public class GUIController implements Initializable
{

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
    private AnchorPane panelUsuario;
    @FXML
    private AnchorPane panelInventario;
    @FXML
    private AnchorPane panelSedes;
    @FXML
    private AnchorPane panelReportes;
    @FXML
    private AnchorPane panelInicio;
    @FXML
    private AnchorPane panelOrdenes;
    @FXML
    private Button boton_orden;
    @FXML
    private Button boton_venta;
    @FXML
    private Button boton_cerrar;
    @FXML
    private AnchorPane panelVentas;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
/*
        Timer temporizador = new Timer();
        TimerTask tarea = new TimerTask() 
        {
            @Override
            public void run() 
            {
*/
                try {
                    AnchorPane root1 = FXMLLoader.load(getClass().getResource("/Vista/Inventario.fxml"));

                    panelInventario.getChildren().add(root1);
/*
                    AnchorPane root2 = FXMLLoader.load(getClass().getResource("/Vista/*NOMBRE_ARCHIVO*.fxml"));

                    *NOMBRE_PANEL*.getChildren().add(0, root2);
*/

                } catch (IOException ex) {
                    Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
/*
                finally
                {
                    cancel();
                    temporizador.cancel();

                }
            }
        };
        temporizador.schedule(tarea, 0, 10000); 
*/

        boton_cerrar.setLayoutY(boton_inventario.getLayoutY());
        boton_orden.setLayoutY(boton_usuario.getLayoutY());
        boton_inventario.setLayoutY(boton_inicio.getLayoutY());
        
        setColor(boton_inventario);

        panelInventario.setVisible(true);
        panelInicio.setVisible(false);

        boton_inicio.setVisible(false);
        boton_usuario.setVisible(false);
        boton_sede.setVisible(false);
        boton_reporte.setVisible(false);
        boton_venta.setVisible(false);

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
        quitarVisibilidad();
        setColor(boton_inicio);
        panelInicio.setVisible(true);
    }

    @FXML
    private void botonUsuario(ActionEvent event) 
    {
        quitarVisibilidad();
        setColor(boton_usuario);
        panelUsuario.setVisible(true);
    }

    @FXML
    private void botonInventario(ActionEvent event) 
    {
        quitarVisibilidad();
        setColor(boton_inventario);
        panelInventario.setVisible(true);
    }

    @FXML
    private void botonSedes(ActionEvent event) 
    {
        quitarVisibilidad();
        setColor(boton_sede);
        panelSedes.setVisible(true);
    }

    @FXML
    private void botonReportes(ActionEvent event) 
    {
        quitarVisibilidad();
        setColor(boton_reporte);
        panelReportes.setVisible(true);
    }

    @FXML
    private void botonOrdenes(ActionEvent event) 
    {
        quitarVisibilidad();
        setColor(boton_orden);
        panelOrdenes.setVisible(true);
    }

    @FXML
    private void botonVentas(ActionEvent event) 
    {
        quitarVisibilidad();
        setColor(boton_venta);
        panelVentas.setVisible(true);
    }
    
    private void quitarVisibilidad()
    {
        resetColor(boton_inicio);
        resetColor(boton_usuario);
        resetColor(boton_inventario);
        resetColor(boton_sede);
        resetColor(boton_reporte);
        resetColor(boton_orden);
        resetColor(boton_venta);

        panelInicio.setVisible(false);
        panelUsuario.setVisible(false);
        panelInventario.setVisible(false);
        panelSedes.setVisible(false);
        panelReportes.setVisible(false);
        panelOrdenes.setVisible(false);
        panelVentas.setVisible(false);
    }

}
