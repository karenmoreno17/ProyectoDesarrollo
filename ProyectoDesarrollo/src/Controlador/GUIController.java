/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Fachada;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Karen Daniela
 */
public class GUIController implements Initializable
{
    private ArrayList <AnchorPane> panelesSecundarios;

    @FXML
    private AnchorPane ventanaPrincipal, panelBotones, panelUsuario, panelInventario, panelSedes, panelReportes, panelInicio, panelOrdenes, panelVentas;
    @FXML
    private Pane ventanaMenu, panelPrincipal, panelBienvenida, panelOpciones, panelContenedor;
    @FXML
    private GridPane panelIngreso;
    @FXML
    private Button bCerrar, bContinuar, bIngresar, boton_inicio, boton_usuario, boton_inventario,boton_sede,boton_reporte, boton_orden, boton_venta, boton_cerrar;
    @FXML
    private TextField tCedula, tAuxContrasena;
    @FXML
    private PasswordField tContrasena;
    @FXML
    private CheckBox cbContrasena;


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
/*
        El nombre de cada archivo .fxml (de los paneles secundarios) se guarda en una posicion del array nombres.
        Con este array que el programa los añade después en su respectivo panel (en la función ingresar).

        Posiciones:
         - Panel de inicio:             0.
         - Panel de usuario:            1.
         - Panel de inventario:         2.
         - Panel de sedes:              3.
         - Panel de reportes            4.
         - Panel de órdenes de trabajo: 5.
         - Panel de ventas:             6.
*/

        String[] nombres = new String[7];
        nombres[0] = null;
        nombres[1] = "Usuario.fxml";
        nombres[2] = "Inventario.fxml";
        nombres[3] = "Sedes.fxml";
        nombres[4] = null;
        nombres[5] = "OrdenTrabajo.fxml";
        nombres[6] = null;

        panelesSecundarios = new ArrayList<>();

        for (int i = 0; i < nombres.length; i++)
        {
            panelesSecundarios.add(null);
        }

        cargarFXML(nombres);
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
    }

    @FXML
    private void activarContrasena(ActionEvent event) 
    {
        if(cbContrasena.isSelected())
        {
            tAuxContrasena.setText(tContrasena.getText());
            tAuxContrasena.setVisible(true);
            tContrasena.setVisible(false);
        }
        else
        {
            tContrasena.setText(tAuxContrasena.getText());
            tContrasena.setVisible(true);
            tAuxContrasena.setVisible(false);
        }
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
    private void verificarLogin ()
    {
        String contrasena = (cbContrasena.isSelected())? tAuxContrasena.getText(): tContrasena.getText();

        if (contrasena.equals("") || tCedula.getText().equals(""))
        {
            JOptionPane.showMessageDialog(null, "El usuario y/o la contraseña no pueden ser vacíos.");
            return;
        }

        Fachada con = new Fachada();
        Connection conexion = con.getConnection();

        try 
        {
            Statement st = conexion.createStatement();
            String sql = "SELECT contrasena, rol FROM empleado WHERE cedula_empleado = " + tCedula.getText() + ";";

            ResultSet rs = st.executeQuery(sql);

            rs.next();

            if (rs.getString(1).equals(contrasena))
            {
                ingresar(rs.getString(2));
            }
            else 
            {
                JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos.");
            }

            st.close();
            conexion.close();
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex.getMessage());
        }
    }

    private void setColor(Button boton )
    {
        boton.setStyle("-fx-background-color: #B5B2B2; ");
    }

    private void resetColor(Button boton )
    {
        boton.setStyle("-fx-background-color: #808080; ");
    }

    private void cargarFXML(String[] nombres)
    {
        for (int i = 0; i < 7; i++)
        {
            String nombre = nombres[i];
            int posicion = i;

            Timer temporizador = new Timer();
            TimerTask tarea = new TimerTask() 
            {
                @Override
                public void run() 
                {
                    if (nombre != null)
                    {
                        try 
                        {
                            panelesSecundarios.set(posicion, (AnchorPane) FXMLLoader.load(getClass().getResource("/Vista/" + nombre)));
                        } catch (IOException ex) {
                            Logger.getLogger(GUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    cancel();
                    temporizador.cancel();
                }
            };
            temporizador.schedule(tarea, 0, 10000);
        }
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

    private void ventanaDesarrollador()
    {
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

    private void ingresar(String rol) 
    {
        panelPrincipal.setVisible(false);
        ventanaMenu.setVisible(true);

        tContrasena.clear();
        tAuxContrasena.clear();
        tCedula.clear();

        for (int i = 0; i < 7; i++)
        {
            if (panelesSecundarios.get(i) != null)
            {
                AnchorPane hijo = (AnchorPane) panelContenedor.getChildren().get(i);
                hijo.getChildren().add(panelesSecundarios.get(i));
            }
        }

        switch(rol.toLowerCase())
        {
            case "gerente":
                ventanaGerente();
                break;
            case "vendedor":
                ventanaVendedor();
                break;
            case "jefe de taller":
                ventanaJefeTaller();
                break;
        }
    }

    private void ventanaGerente()
    {
        setColor(boton_inicio);
    }

    private void ventanaVendedor()
    {
        setColor(boton_inicio);

        boton_usuario.setVisible(false);
        boton_sede.setVisible(false);
        boton_orden.setVisible(false);

        boton_inventario.setLayoutY(121);
        boton_reporte.setLayoutY(166);
        boton_venta.setLayoutY(211);
        boton_cerrar.setLayoutY(256);
    }

    private void ventanaJefeTaller()
    {
        setColor(boton_inicio);

        boton_usuario.setVisible(false);
        boton_sede.setVisible(false);
        boton_venta.setVisible(false);

        boton_inventario.setLayoutY(121);
        boton_reporte.setLayoutY(166);
        boton_orden.setLayoutY(211);
        boton_cerrar.setLayoutY(256);
    }

}
