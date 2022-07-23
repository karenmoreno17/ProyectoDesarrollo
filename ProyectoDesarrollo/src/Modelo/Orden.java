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
public class Orden 
{
    private int id_orden;
    private String nombre_repuesto;
    private String nombre_jefe;
    private String proceso;
    
    public Orden(int id_orden, String nombre_repuesto, String nombre_jefe, String proceso)
    {
        this.id_orden = id_orden;
        this.nombre_repuesto = nombre_repuesto;
        this.nombre_jefe = nombre_jefe;
        this.proceso = proceso;
    }
    
    public int getId_orden()
    {
        return id_orden;
    }
    
    public String getNombre_repuesto()
    {
        return nombre_repuesto;
    }
    
    public String getNombre_jefe()
    {
        return nombre_jefe;
    }
    
    public String getProceso()
    {
        return proceso;
    }
}
