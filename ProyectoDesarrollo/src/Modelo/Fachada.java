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
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dinos.
 */
public class Fachada {

    Connection con;

    public Fachada(){
        con = null;
    }

    public  Connection getConnection(){

        try
        {
            if(con == null){

                //Runtime.getRuntime().addShutdownHook(new MiShDwnHook());

                String usuario = "iyhodmleuapojt";
                String contraseña = "71ae51b3a9149e9cd5c264776a7d297733bfbe1033a8b4ec85d8ade486c8c72c";
                String url = "jdbc:postgresql://ec2-52-44-13-158.compute-1.amazonaws.com:5432/d3m3lc4m466r71";
                con = DriverManager.getConnection(url, usuario, contraseña);

            }

        }
        catch( SQLException ex){
            JOptionPane.showMessageDialog(null,"Error Fachada : " + ex.getMessage());
        }

        return con;
    }

}
