/*
 * Copyright (C) 2022 Alvaro Portocarrero.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Alvaro Portocarrero.
 */
public class ControladorOrdenTrabajo implements Initializable 
{

    @FXML
    private TextField tfIdEncargado, tfIdCliente;
    @FXML
    private TextArea taProceso;
    @FXML
    private ComboBox<String> cbVehiculo, cbRepuesto;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        tfIdCliente.setContextMenu(new ContextMenu());
        tfIdEncargado.setContextMenu(new ContextMenu());

        cargarDeInventario(cbVehiculo, "vehiculo");
        cargarDeInventario(cbRepuesto, "repuesto");
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
    private void crearOrden() 
    {
        boolean ordenInvalida = tfIdCliente.getText().equals("") || tfIdEncargado.getText().equals("")
                || cbVehiculo.getValue().equals("Elige un vehiculo") || cbRepuesto.getValue().equals("Elige un repuesto")
                || taProceso.getText().equals("");

        if (ordenInvalida)
        {
            JOptionPane.showMessageDialog(null, "Por favor llene todos los campos.");
        } 
        else 
        {
            Fachada con = new Fachada();
            Connection conexion = con.getConnection();

            String cedulaCliente = tfIdCliente.getText();
            String cedulaEncargado = tfIdEncargado.getText();
            String idVehiculo = cbVehiculo.getValue().split("-")[0];
            String idRepuesto = cbRepuesto.getValue().split("-")[0];
            String proceso = taProceso.getText();

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
                        String sql = "INSERT INTO orden_trabajo (cedula_cliente, id_encargado, id_vehiculo, id_repuesto, proceso) VALUES ("
                                + cedulaCliente + ", " + cedulaEncargado + ", " + idVehiculo + ", "
                                + idRepuesto + ", '" + proceso + "');";

                        int result = st.executeUpdate(sql);

                        if (result == 1) 
                        {
                            JOptionPane.showMessageDialog(null, "La orden de trabajo fue creada con éxito.");
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null, "Ocurrió un error al crear la orden de trabajo.");
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
            temporizadorV.schedule(tareaV, 0);
        }
    }

    @FXML
    private void limpiarCampos() 
    {
        tfIdCliente.setText("");
        tfIdEncargado.setText("");
        taProceso.setText("");
        cbVehiculo.setValue("Elige un vehiculo");
        cbRepuesto.setValue("Elige un repuesto");
    }

    private void cargarDeInventario(ComboBox cb, String tipo) 
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
                    String sql = "SELECT * FROM " + tipo + ";";

                    cb.getItems().clear();
                    cb.setValue("Elige un " + tipo);
                    cb.getItems().add("Elige un " + tipo);

                    ResultSet rs = st.executeQuery(sql);

                    while (rs.next()) 
                    {
                        cb.getItems().add(rs.getString(1) + "- " + rs.getString(2) + ", " + rs.getString(5) + ", " + rs.getString(3));
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
        temporizadorV.schedule(tareaV, 0);
    }

}
