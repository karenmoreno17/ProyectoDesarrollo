/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author JUAN DAVID
 */
public class Compra 
{
    private int id_compra;
    private String nombre_vehiculo;
    private String nombre_vendedor;
    private int precio_vehiculo;
    
    public Compra(int id_compra, String nombre_vehiculo, String nombre_vendedor, int precio_vehiculo)
    {
        this.id_compra = id_compra;
        this.nombre_vehiculo = nombre_vehiculo;
        this.nombre_vendedor = nombre_vendedor;
        this.precio_vehiculo = precio_vehiculo;
    }
    
    public int getId_compra()
    {
        return id_compra;
    }
    
    public void setId(int id)
    {
        id_compra = id;
    }
    
    public String getNombre_vehiculo()
    {
        return nombre_vehiculo;
    }
    
    public void setVehiculo(String linea)
    {
        this.nombre_vehiculo = linea;
    }
    
    public String getNombre_vendedor()
    {
        return nombre_vendedor;
    }
    
    public int getPrecio_vehiculo()
    {
        return precio_vehiculo;
    }
    
}
