/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Fachada;
import Modelo.Notificacion;
import Modelo.Vehiculo;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Alvaro Portocarrero.
 */
public class ControladorNotificaciones implements Initializable
{
    private int idUsuario;
    private String consulta;

    @FXML
    private AnchorPane panelNotificaciones;
    @FXML
    private TableView<Notificacion> tvPendientes;
    @FXML
    private TableView<Notificacion> tvAprobados;
    @FXML
    private TableView<Notificacion> tvRechazados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        tvPendientes. setPlaceholder(new Label("No hay solicitudes pendientes."));
        tvAprobados. setPlaceholder(new Label("No hay solicitudes aprobadas."));
        tvRechazados. setPlaceholder(new Label("No hay solicitudes rechazadas."));

        tvPendientes.getColumns().get(0).setCellValueFactory( new PropertyValueFactory("id_vehiculo"));
        tvPendientes.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("id_solicitante"));
        tvPendientes.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("sede_origen"));
        tvPendientes.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("sede_destino"));
        tvPendientes.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("estado"));

        tvAprobados.getColumns().get(0).setCellValueFactory( new PropertyValueFactory("id_vehiculo"));
        tvAprobados.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("id_solicitante"));
        tvAprobados.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("sede_origen"));
        tvAprobados.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("sede_destino"));
        tvAprobados.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("estado"));

        tvRechazados.getColumns().get(0).setCellValueFactory( new PropertyValueFactory("id_vehiculo"));
        tvRechazados.getColumns().get(1).setCellValueFactory( new PropertyValueFactory("id_solicitante"));
        tvRechazados.getColumns().get(2).setCellValueFactory( new PropertyValueFactory("sede_origen"));
        tvRechazados.getColumns().get(3).setCellValueFactory( new PropertyValueFactory("sede_destino"));
        tvRechazados.getColumns().get(4).setCellValueFactory( new PropertyValueFactory("estado"));

        idUsuario = 4;
        consulta = "id_solicitante";
        cargarSolicitudes("Pendientes", tvPendientes);
    }

    @FXML
    private void aprobarSolicitud()
    {
        
    }

    @FXML
    private void rechazarSolicitud()
    {
        
    }

    @FXML
    private void cancelar()
    {
        
    }

    private void cargarSolicitudes(String tipoSolicitud, TableView tvSolicitudes)
    {
        Fachada con = new Fachada();
        Connection conexion = con.getConnection();


        Timer temporizador = new Timer();

        TimerTask tarea = new TimerTask()
        {
            @Override
            public void run() 
            {
                try
                {
                    Statement st = conexion.createStatement();
                    String sql = "SELECT * FROM notificacion WHERE " + consulta + " = " + idUsuario;

                    tvSolicitudes.getItems().clear();

                    ResultSet rs = st.executeQuery(sql);

                    while(rs.next())
                    {
                        tvSolicitudes.getItems().add(new Notificacion(rs.getInt(1), rs.getInt(6), rs.getInt(2), rs.getInt(5), rs.getInt(4), rs.getInt(3)));
                    }

                    st.close();
                    conexion.close();
                }
                catch(SQLException ex)
                {
                    JOptionPane.showMessageDialog(null,"Error Mostrar: " + ex.getMessage());
                }
                cancel();
                temporizador.cancel();
            }
        };
        temporizador.schedule(tarea, 0); 
    }

    private void cargarAprobados()
    {
        
    }

    private void cargarRechazados()
    {
        
    }

}