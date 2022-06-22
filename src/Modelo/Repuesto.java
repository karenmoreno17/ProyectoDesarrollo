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

/**
 *
 * @author Alvaro Portocarrero.
 */
public class Repuesto {
    
    private int id_repuesto;
    private String linea;
    private String marca;
    private String modelo;
    private int precio;
    private int cantidad;
    
    public Repuesto(int id, String linea, String marca, String modelo, int precio, int cantidad){
        id_repuesto = id;
        this.linea = linea;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.cantidad = cantidad;

    }
    
    public int getId(){
        return id_repuesto;
    }
    
    public void setId(int id){
        id_repuesto = id;
    }
    
    public String getLinea(){
        return linea;
    }
    
    public void setLinea(String linea){
        this.linea = linea;
    }
    
    public String getMarca(){
        return marca;
    }
    
    public void setMarca(String marca){
        this.marca = marca;
    }
    
    public String getModelo(){
        return modelo;
    }
    
    public void setModelo(String modelo){
        this.modelo = modelo;
    }
    
    public int getPrecio(){
        return precio;
    }
    
    public void setPrecio(int precio){
        this.precio = precio;
    }
    
    public int getCantidad(){
        return cantidad;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

}
