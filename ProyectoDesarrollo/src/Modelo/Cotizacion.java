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
public class Cotizacion 
{
    private String nombreCliente;
    private String vehiculoCotizado;
    private String nombreVendedor;
    
    public Cotizacion(String nombreCliente, String vehiculoCotizado, String nombreVendedor)
    {
        this.nombreCliente = nombreCliente;
        this.vehiculoCotizado = vehiculoCotizado;
        this.nombreVendedor = nombreVendedor;
    }

    public String getNombreCliente() 
    {
        return nombreCliente;
    }

    public String getVehiculoCotizado() 
    {
        return vehiculoCotizado;
    }

    public String getNombreVendedor() 
    {
        return nombreVendedor;
    }
    
    
    
}
