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
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Alvaro Portocarrero.
 */
public class ControladorSedes implements Initializable 
{

    @FXML
    private ComboBox<String> cbId;
    @FXML
    private TextField tfNombre, tfUbicacion, tfTelefono;
    @FXML
    private Button bEliminar, bModificar;
    @FXML
    private TextField tNombreSede;
    @FXML
    private TextField tTelefonoSede;
    @FXML
    private TextField tIdentificadorSede;
    @FXML
    private TextField tDireccionSede;
    @FXML
    private Button bCrearSede;
    @FXML
    private Button bLimpiarCamposSede;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        tfTelefono.setContextMenu(new ContextMenu());

        bEliminar.setOnAction((event) -> {});
        bModificar.setOnAction((event) -> {});
        
        cargarIds();
    }

    @FXML
    private void limpiarCampos() 
    {
        cbId.setValue(cbId.getPromptText());
        tfNombre.clear();
        tfTelefono.clear();
        tfUbicacion.clear();
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
    private void habilitarCampos() 
    {
        if (!cbId.getValue().equals("Elige una sede")) 
        {
            bEliminar.setOnAction((event) -> {eliminarSede();});
            bModificar.setOnAction((event) -> {modificarSede();});

            tfNombre.setEditable(true);
            tfTelefono.setEditable(true);
            tfUbicacion.setEditable(true);

            cargarDatos();
        } 
        else if (tfNombre.isEditable()) 
        {
            tfNombre.setEditable(false);
            tfTelefono.setEditable(false);
            tfUbicacion.setEditable(false);

            tfNombre.clear();
            tfTelefono.clear();
            tfUbicacion.clear();

            bEliminar.setOnAction((event) -> {});
            bModificar.setOnAction((event) -> {});
        }
    }

    private void cargarIds() 
    {
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();

        Timer temporizadorV = new Timer();
        TimerTask tareaV = new TimerTask() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    Statement st = conexion.createStatement();
                    String sql = "SELECT * FROM sede;";

                    cbId.getItems().clear();
                    cbId.getItems().add("Elige una sede");

                    ResultSet rs = st.executeQuery(sql);

                    while (rs.next()) 
                    {
                        cbId.getItems().add(rs.getString(1));
                    }
                    st.close();
                    conexion.close();
                } 
                catch (SQLException ex) 
                {
                    JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex.getMessage());
                } 
                finally 
                {
                    cancel();
                    temporizadorV.cancel();
                }
            }
        };
        temporizadorV.schedule(tareaV, 0, 10000);
    }

    private void modificarSede() 
    {
        boolean datosInvalidos = tfNombre.equals("") || tfTelefono.equals("") || tfUbicacion.equals("");

        if (datosInvalidos)
        {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos.");
        }
        else 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();

            String id = cbId.getValue();
            String nombre = tfNombre.getText();
            String ubicacion = tfUbicacion.getText();
            String telefono = tfTelefono.getText();

            limpiarCampos();

            Timer temporizadorV = new Timer();
            TimerTask tareaV = new TimerTask() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        Statement st = conexion.createStatement();
                        String sql = "UPDATE sede "
                                   + "SET nombre_sede = '" + nombre + "', ubicacion_sede = '" + ubicacion + "', telefono_sede = " + telefono 
                                   + " WHERE id_sede = " + id + ";";
                        System.out.println(sql);
                        int result = st.executeUpdate(sql);

                        if (result == 1) 
                        {
                            JOptionPane.showMessageDialog(null, "Los datos de la sede fueron modificados con éxito.");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Ocurrió un error al modificar los datos de la sede.");
                        }

                        st.close();
                        conexion.close();
                    } 
                    catch (SQLException ex) 
                    {
                        JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex.getMessage());
                    } 
                    finally 
                    {
                        cancel();
                        temporizadorV.cancel();
                    }
                }
            };
            temporizadorV.schedule(tareaV, 0, 10000);
        }
    }

    private void eliminarSede() 
    {
        int confirmacion = JOptionPane.showConfirmDialog(null, "Esta acción no se puede deshacer. ¿Confirma que desea eliminar la sede?", "Eliminar sede", 0, 2);
        System.out.println(confirmacion);
        if (confirmacion == 0) 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();

            String idSede = cbId.getValue();

            limpiarCampos();

            Timer temporizadorV = new Timer();
            TimerTask tareaV = new TimerTask() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        Statement st = conexion.createStatement();
                        String sql1 = "DELETE FROM sede WHERE id_sede = " + idSede + ";";
                        String sql2 = "UPDATE empleado SET id_sede = -1 WHERE id_sede = " + idSede + ";";

                        int eliminar = st.executeUpdate(sql1);
                        st.executeUpdate(sql2);

                        if (eliminar == 1) 
                        {
                            JOptionPane.showMessageDialog(null, "La sede fue eliminada con éxito.");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Ocurrió un error al eliminar la sede.");
                        }

                        st.close();
                        conexion.close();
                    } 
                    catch (SQLException ex) 
                    {
                        JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex.getMessage());
                    } 
                    finally 
                    {
                        cancel();
                        temporizadorV.cancel();
                    }
                }
            };
            temporizadorV.schedule(tareaV, 0, 10000);
        }
    }
    
    private void cargarDatos()
    {
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();

        Timer temporizadorV = new Timer();
        TimerTask tareaV = new TimerTask() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    Statement st = conexion.createStatement();
                    String sql = "SELECT * FROM sede WHERE id_sede = " + cbId.getValue() + ";";

                    ResultSet rs = st.executeQuery(sql);

                    rs.next();

                    tfNombre.setText(rs.getString(2));
                    tfUbicacion.setText(rs.getString(3));
                    tfTelefono.setText(rs.getString(4));

                    st.close();
                    conexion.close();
                } 
                catch (SQLException ex) 
                {
                    JOptionPane.showMessageDialog(null, "Error Mostrar: " + ex.getMessage());
                } 
                finally 
                {
                    cancel();
                    temporizadorV.cancel();
                }
            }
        };
        temporizadorV.schedule(tareaV, 0, 10000);
    }

    @FXML
    private void crearSede(ActionEvent event) 
    {
        boolean sedeInvalida = tIdentificadorSede.getText().equals("") && tNombreSede.getText().equals("") 
                             && tDireccionSede.getText().equals("") && tTelefonoSede.getText().equals("");
        
        if (sedeInvalida)
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
                String sql = "INSERT INTO sede (id_sede, nombre_sede, ubicacion_sede, telefono_sede) VALUES ("
                        + tIdentificadorSede.getText() + ", '" + tNombreSede.getText() + "', '" + tDireccionSede.getText() + "', "
                        + tTelefonoSede.getText() + " );";

                int result = st.executeUpdate(sql);

                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "La sede se ha creado exitosamente.");
                    limpiarCamposSede();
                } else {
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al crear la sede.");
                }

                st.close();
                conexion.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error : " + ex.getMessage());
            }
        }
            
    }

    @FXML
    private void limpiarCamposSede() 
    {
        tIdentificadorSede.setText("");
        tNombreSede.setText("");
        tDireccionSede.setText("");
        tTelefonoSede.setText("");
    }
}
