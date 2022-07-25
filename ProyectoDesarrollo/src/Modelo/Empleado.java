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
public class Empleado 
{
    private String nombre;
    private String cargo;
    private String sede;
    private String estado;
    
    public Empleado (String nombre, String cargo, String sede, String estado)
    {
        this.nombre = nombre;
        this.cargo = cargo;
        this.sede = sede;
        this.estado = estado;
    }
    
    
    public String getNombre()
    {
        return nombre;
    }
    
    public String getCargo()
    {
        return cargo;
    }
    
    public String getSede()
    {
        return sede;
    }
    
    public String getEstado()
    {
        return estado;
    }
}
