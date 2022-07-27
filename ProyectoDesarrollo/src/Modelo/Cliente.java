/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Karen Daniela
 */
public class Cliente 
{
    private String nombre;
    private String direccion;
    private String genero;
    private Integer comuna;
    
    public Cliente(String nombre, String direccion, String genero, Integer comuna)
    {
        this.nombre = nombre;
        this.direccion = direccion;
        this.genero = genero;
        this.comuna = comuna;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public String getDireccion() 
    {
        return direccion;
    }

    public String getGenero() 
    {
        return genero;
    }

    public Integer getComuna() 
    {
        return comuna;
    }
    
    
    
}
