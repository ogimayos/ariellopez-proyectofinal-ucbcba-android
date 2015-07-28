package com.example.ariel.ventas_moviles.BLL;

/**
 * Created by ariel on 19/07/2015.
 */
public class Pedido {
    private Integer idCliente;
    private String Fecha;
    private String NombreCliente;
    private String NitCliente;


    private Integer id;

    public Pedido(int IdCliente, String Fecha, String NombreCliente, String NitCliente, int idd)
    {
        this.idCliente = IdCliente;
        this.Fecha = Fecha;
        this.NombreCliente = NombreCliente;
        this.NitCliente = NitCliente;
        this.id = idd;
    }
    //set
    public void setidCliente(Integer _idCliente){
        this.idCliente = _idCliente;
    }
    public void setFecha(String _Fecha){
        this.Fecha = _Fecha;
    }
    public void setNombreCliente(String _NombreCliente)
    {
        this.NombreCliente=_NombreCliente;
    }
    public void setNitCliente(String _NitCliente)
    {
        this.NitCliente=_NitCliente;
    }


    //get
    public Integer getidCliente(){
        return this.idCliente;
    }
    public String getFecha(){
        return this.Fecha;
    }
    public String getNombreCliente()
    {
        return this.NombreCliente;
    }
    public String getNitCliente()
    {
        return this.NitCliente;
    }

    public Integer getId(){
        return id;
    }

}

