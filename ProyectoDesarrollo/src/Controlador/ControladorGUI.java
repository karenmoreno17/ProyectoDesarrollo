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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.postgresql.util.PSQLException;

/**
 * FXML Controller class
 *
 * @author Karen Daniela
 */
public class ControladorGUI implements Initializable
{
    private ArrayList <AnchorPane> panelesSecundarios;
    private ArrayList controladores;
    private boolean estado = false;

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
    @FXML
    private Label nombre_usuario_inicio;
    @FXML
    private Label rol_usuario_inicio;
    @FXML
    private Button boton_inicio_lateral;
    @FXML
    private Button boton_usuario_lateral;
    @FXML
    private Button boton_inventario_lateral;
    @FXML
    private Button boton_sede_lateral;
    @FXML
    private Button boton_reporte_lateral;
    @FXML
    private Button boton_orden_lateral;
    @FXML
    private Button boton_venta_lateral;
    @FXML
    private Button boton_menu_lateral;
    @FXML
    private AnchorPane panel_lateral;
    @FXML
    private Button boton_cotizacion;
    @FXML
    private Button boton_cotizacion_lateral;
    @FXML
    private AnchorPane panelCotizaciones;
    @FXML
    private AnchorPane panelNotificaciones;
    @FXML
    private Button boton_notificacion;
    

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
         - Panel de cotizaciones:       6.
         - Panel de ventas:             7.
        */

        String[] nombres = new String[8];
        nombres[0] = "Inicio.fxml";
        nombres[1] = "Usuario.fxml";
        nombres[2] = "Inventario.fxml";
        nombres[3] = "Sedes.fxml";
        nombres[4] = "Reportes.fxml";
        nombres[5] = "OrdenTrabajo.fxml";
        nombres[6] = "Cotizaciones.fxml";
        nombres[7] = "Venta.fxml";

        tAuxContrasena.setContextMenu(new ContextMenu());
        tCedula.setContextMenu(new ContextMenu());
        tContrasena.setContextMenu(new ContextMenu());

        panelesSecundarios = new ArrayList<>();
        controladores = new ArrayList<>();

        for (int i = 0; i < nombres.length; i++)
        {
            panelesSecundarios.add(null);
            controladores.add(null);
        }

        cargarFXML(nombres);
    }

    @FXML
    private void cerrar(ActionEvent event) 
    {
    //    int confirmacion = JOptionPane.showConfirmDialog(null, "Esta acción no se puede deshacer. ¿Confirma que desea cerrar la ventana?", "Cerrar ventana", 0, 2);
    //    if (confirmacion == 0) 
    //    {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
    //    }
    }

    @FXML
    private void continuar() 
    {
        panelBienvenida.setVisible(false);
        panelIngreso.setVisible(true);
    }

    @FXML
    private void activarContrasena() 
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
    private void botonInicio() 
    {
        quitarVisibilidad();
        setColor(boton_inicio);
        panelInicio.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonUsuario() 
    {
        quitarVisibilidad();
        setColor(boton_usuario);
        panelUsuario.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonInventario() 
    {
        quitarVisibilidad();
        setColor(boton_inventario);
        panelInventario.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonSedes() 
    {
        quitarVisibilidad();
        setColor(boton_sede);
        panelSedes.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonReportes() 
    {
        quitarVisibilidad();
        setColor(boton_reporte);
        panelReportes.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonOrdenes() 
    {
        quitarVisibilidad();
        setColor(boton_orden);
        panelOrdenes.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonVentas() 
    {
        quitarVisibilidad();
        setColor(boton_venta);
        panelVentas.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void botonCotizaciones()
    {
        quitarVisibilidad();
        setColor(boton_cotizacion);
        panelCotizaciones.setVisible(true);
        esconderMenu();
    }

    @FXML
    private void verificacionTeclasEspeciales(KeyEvent event) 
    {
        TextField tf = (TextField) event.getSource();

        if (event.getCode().toString().equals("CONTROL"))
        {
            tf.setEditable(false);
        }
        else if (event.getCode().toString().equals("ENTER")) 
        {
            verificarLogin();
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
            String sql = "SELECT contrasena, rol, nombre_empleado, id_sede FROM empleado WHERE cedula_empleado = " + tCedula.getText() + " AND estado = 'Activo';";

            ResultSet rs = st.executeQuery(sql);

            rs.next();

            if (rs.getString(1).equals(contrasena))
            {
                nombre_usuario_inicio.setText(rs.getString(3));
                rol_usuario_inicio.setText(rs.getString(2));
                ingresar(rs.getString(2), rs.getInt(4));
            }
            else 
            {
                JOptionPane.showMessageDialog(null, "Contraseña incorrecta.");
            }

            st.close();
            conexion.close();
        }
        catch (PSQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Usuario incorrecto.");
        }
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex);
        }
    }

    private void setColor(Button boton)
    {
        boton.setStyle("-fx-background-color: #B5B2B2; ");
    }

    private void resetColor(Button boton)
    {
        boton.setStyle("-fx-background-color: #808080; ");
    }

    private void cargarFXML(String[] nombres)
    {
        for (int i = 0; i < nombres.length; i++)
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
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/" + nombre));

                            panelesSecundarios.set(posicion, (AnchorPane) loader.load());

                            controladores.set(posicion, loader.getController());
                        } catch (IOException ex) {
                            Logger.getLogger(ControladorGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        resetColor(boton_cotizacion);
        resetColor(boton_venta);

        panelInicio.setVisible(false);
        panelUsuario.setVisible(false);
        panelInventario.setVisible(false);
        panelSedes.setVisible(false);
        panelReportes.setVisible(false);
        panelOrdenes.setVisible(false);
        panelCotizaciones.setVisible(false);
        panelVentas.setVisible(false);
    }

    private void ingresar(String rol, int sede) 
    {
        ControladorInventario controladorInventario = (ControladorInventario) controladores.get(2);

        panelPrincipal.setVisible(false);
        ventanaMenu.setVisible(true);

        tContrasena.clear();
        tAuxContrasena.clear();
        tCedula.clear();

        for (int i = 0; i < panelesSecundarios.size(); i++)
        {
            if (panelesSecundarios.get(i) != null)
            {
                AnchorPane hijo = (AnchorPane) panelContenedor.getChildren().get(i);
                hijo.getChildren().add(panelesSecundarios.get(i));
            }
        }

        controladorInventario.login(rol.toLowerCase(), sede);

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

        boton_venta.setVisible(true);
        boton_orden.setVisible(false);

        boton_cerrar.setLayoutY(boton_venta.getLayoutY());
        boton_reporte.setLayoutY(boton_sede.getLayoutY());
        boton_venta.setLayoutY(boton_inventario.getLayoutY());
        boton_inventario.setLayoutY(boton_usuario.getLayoutY());

        boton_reporte_lateral.setLayoutY(boton_sede_lateral.getLayoutY());
        boton_venta_lateral.setLayoutY(boton_inventario_lateral.getLayoutY());
        boton_inventario_lateral.setLayoutY(boton_usuario_lateral.getLayoutY());

        panelBotones.getChildren().remove(5);
        panelBotones.getChildren().remove(3);
        panelBotones.getChildren().remove(1);

        panel_lateral.getChildren().remove(6);
        panel_lateral.getChildren().remove(4);
        panel_lateral.getChildren().remove(2);
    }

    private void ventanaJefeTaller()
    {
        setColor(boton_inicio);

        boton_cerrar.setLayoutY(boton_venta.getLayoutY());
        boton_reporte.setLayoutY(boton_sede.getLayoutY());
        boton_orden.setLayoutY(boton_inventario.getLayoutY());
        boton_inventario.setLayoutY(boton_usuario.getLayoutY());

        boton_reporte_lateral.setLayoutY(boton_sede_lateral.getLayoutY());
        boton_orden_lateral.setLayoutY(boton_inventario_lateral.getLayoutY());
        boton_inventario_lateral.setLayoutY(boton_usuario_lateral.getLayoutY());

        panelBotones.getChildren().remove(4);
        panelBotones.getChildren().remove(3);
        panelBotones.getChildren().remove(1);

        panel_lateral.getChildren().remove(5);
        panel_lateral.getChildren().remove(4);
        panel_lateral.getChildren().remove(2);
    }

    @FXML
    private void desplazar_menu()
    {
        if(estado)
        {
            esconderMenu();
        }
        else
        {
            mostrarMenu();
        }
    }

     private void mostrarMenu()
    {
        if(estado)
        {
            return;
        }
        for (Node elemento: panelBotones.getChildren())
        { 
            elemento.setVisible(true);
        }
        panelBotones.setStyle("-fx-background-color: #808080;");
        panelContenedor.toBack();
        panelBotones.setVisible(true);
        /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), panelBotones);
        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event2 -> {
            panelBotones.setVisible(true);
        });
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), panelBotones);
        translateTransition.setByX(25);
        translateTransition.play();*/
        estado = true;
    }

    private void esconderMenu()
    {
        if(!estado)
        {
            return;
        }
        panelContenedor.toFront();
        panelBotones.setVisible(false);
        /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),panelBotones);
        fadeTransition.setFromValue(0.2);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        fadeTransition.setOnFinished(event1 -> {panelBotones.setVisible(false);});
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),panelBotones);
        translateTransition.setByX(-25);
        translateTransition.play();*/
        estado = false;
    }


    @FXML
    private void cambiarColor(MouseEvent event)
    {
        Button boton = (Button) event.getSource();

        setColor(boton);
    }


    @FXML
    private void resetearColor(MouseEvent event)
    {
        Button boton = (Button) event.getSource();

        resetColor(boton);   
    }
    

    
    @FXML
    private void desplegarBoton(MouseEvent event)
    {
        mostrarMenu();
        panelBotones.setStyle("");
        Button botonDesplegado = (Button) event.getSource();

        int i = 0;

        for (; i < panelBotones.getChildren().size() - 1; i++)
        {
            if(panel_lateral.getChildren().get(i).equals(botonDesplegado))
            {
                panelBotones.getChildren().get(i-1).setVisible(true);
                panelBotones.getChildren().get(i).setVisible(false);
            }
            else
            {
                panelBotones.getChildren().get(i).setVisible(false);
            }
        }

        panelBotones.getChildren().get(i).setVisible(false);
   }

    @FXML
    private void esconderBoton() 
    {
        esconderMenu();
    }

}
