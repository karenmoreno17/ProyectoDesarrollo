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

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Alvaro Portocarrero.
 */
public class ControladorOrdenTrabajo implements Initializable 
{

    @FXML
    private TextField tfIdCliente, tfIdEncargado;

    
    private void verificacionTeclasEspecialesAux(KeyEvent event, TextField tf) 
    {
        if(event.getCode().toString().equals("CONTROL"))
        {
            tf.setEditable(false);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        tfIdCliente.setContextMenu(new ContextMenu());
        tfIdEncargado.setContextMenu(new ContextMenu());

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

        } catch (NumberFormatException nfe)
        {
            event.consume();
        }

    }

    @FXML
    private void crearOrden(MouseEvent event) 
    {
        
    }

    @FXML
    private void LimpiarCampos(MouseEvent event) 
    {
        
    }

    @FXML
    private void verificacionTeclasEspeciales(KeyEvent event) 
    {
        TextField tf = (TextField) event.getSource();

        if(event.getCode().toString().equals("CONTROL"))
        {
            tf.setEditable(false);
        }

    }

}
