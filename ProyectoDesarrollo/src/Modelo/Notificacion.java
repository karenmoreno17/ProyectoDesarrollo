/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Alvaro Portocarrero.
 */
public class Notificacion
{
    int id_notificacion;
    int id_vehiculo;
    int sede_origen;
    int sede_destino;
    int id_solicitante;
    int estado;
    
    public Notificacion(int id_notificacion, int id_vehiculo, int sede_origen, int sede_destino, int id_solicitante, int estado)
    {
        this.id_notificacion = id_notificacion;
        this.id_vehiculo = id_vehiculo;
        this.id_solicitante = id_solicitante;
        this.sede_origen = sede_origen;
        this.sede_destino = sede_destino;
        this.estado = estado;
    }

    public int getVehiculo()
    {
        return id_vehiculo;
    }

    public int getSolicitante()
    {
        return id_solicitante;
    }

    public int getSedeOrigen()
    {
        return sede_origen;
    }

    public int getSedeDestino()
    {
        return sede_destino;
    }

    public int getEstado()
    {
        return estado;
    }

}
