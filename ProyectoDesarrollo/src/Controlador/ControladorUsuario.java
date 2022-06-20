/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Fachada;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Karen Daniela
 */
public class ControladorUsuario implements Initializable {

    @FXML
    private TextField tTelefonoUsuario;
    @FXML
    private TextField tRolUsuario;
    @FXML
    private TextField tCedulaUsuario;
    @FXML
    private TextField tDireccionUsuario;
    @FXML
    private TextField tCorreoUsuario;
    @FXML
    private TextField tApellidoUsuario;
    @FXML
    private TextField tNombreUsuario;
    @FXML
    private Button bCrearUsuario;
    @FXML
    private TextField tCedulaM;
    @FXML
    private Button bBuscarUsuario;
    @FXML
    private Button bModificarUsuario;
    @FXML
    private TextField tCorreoUsuarioM;
    @FXML
    private TextField tApellidoUsuarioM;
    @FXML
    private TextField tRolUsuarioM;
    @FXML
    private TextField tNombreUsuarioM;
    @FXML
    private TextField tTelefonoUsuarioM;
    @FXML
    private TextField tDireccionUsuarioM;
    @FXML
    private Button bLimpiarCampos;
    @FXML
    private TextField tSedeUsuario;
    @FXML
    private TextField tContrasenaUsuario;
    @FXML
    private TextField tSedeUsuarioM;
    @FXML
    private TextField tContrasenaUsuarioM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void crearUsuario(ActionEvent event)
    {
        boolean ordenInvalida = tTelefonoUsuario.getText().equals("") && tRolUsuario.getText().equals("") 
                             && tCedulaUsuario.getText().equals("") && tDireccionUsuario.getText().equals("")
                             && tCorreoUsuario.getText().equals("") && tApellidoUsuario.getText().equals("")
                             && tNombreUsuario.getText().equals("") && tSedeUsuario.getText().equals("")
                             && tContrasenaUsuario.getText().equals("");
        
        if (ordenInvalida)
        {
            JOptionPane.showConfirmDialog(null, "Por favor, llene todos los campos.");
        }
        else
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
                        String sql = "INSERT INTO empleado (cedula_empleado, contrasena, rol, nombre_empleado, correo_empleado, direccion_empleado, telefono_empleado, id_sede) VALUES ("
                                   + tCedulaUsuario.getText() + ", '" + tContrasenaUsuario.getText() + "', '" + tRolUsuario.getText() + "', '" 
                                   + tNombreUsuario.getText() + tApellidoUsuario.getText() + "', '" + tCorreoUsuario.getText() +  "', '"
                                   + tDireccionUsuario.getText() + "', "  + tTelefonoUsuario.getText() + ", " + tSedeUsuario.getText() + ");";

                        int result = st.executeUpdate(sql);

                        if (result == 1)
                        {
                            JOptionPane.showMessageDialog(null, "El empleado se ha creado exitosamente.");
                            limpiarCampos();
                        } 
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al crear el empleado.");
                        }

                        st.close();
                        conexion.close();
                    } catch(SQLException ex)
                    {
                        JOptionPane.showMessageDialog(null,"Error Mostrar: " + ex.getMessage());
                    } finally
                    {
                        cancel();
                        temporizadorV.cancel();
                    }
                }
            };
            temporizadorV.schedule(tareaV, 0, 10000);
        }
    }

    @FXML
    private void buscarUsuario(ActionEvent event) {
    }

    @FXML
    private void modificarUsuario(ActionEvent event) {
    }

    @FXML
    private void limpiarCampos() 
    {
        tTelefonoUsuario.setText("");
        tRolUsuario.setText("");
        tCedulaUsuario.setText("");
        tDireccionUsuario.setText("");
        tCorreoUsuario.setText("");
        tApellidoUsuario.setText("");
        tNombreUsuario.setText("");
        tSedeUsuario.setText("");
        tContrasenaUsuario.setText("");
    }
    
}
