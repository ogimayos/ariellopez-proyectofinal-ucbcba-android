package com.example.ariel.ventas_moviles.BLL;

/**
 * Created by ariel on 15/07/2015.
 */
public class Item {
    private Integer Iditemcentral;
    private String Nombre;
    private String Codigo;
    private Double Saldoreal;
    private Double Saldoestimado;
    private Double Precioventa;
    private Double Preciocosto;
    private int id;

    public Item(int iditemcentral, String nombre, String codigo, Double saldoreal, Double saldoestimado,Double precioventa, Double preciocosto, int idd)
    {
        Iditemcentral = iditemcentral;
        Nombre = nombre;
        Codigo = codigo;
        Saldoreal = saldoreal;
        Saldoestimado = saldoestimado;
        Preciocosto = preciocosto;
        Precioventa = precioventa;

        this.id = idd;

    }
//set
    public void setIdeitemcentral(Integer _Iditemcentral){
        this.Iditemcentral = _Iditemcentral;
    }
    public void setNombre(String _Nombre){
        this.Nombre = _Nombre;
    }
    public void setCodigo(String _Codigo)
    {
        this.Codigo=_Codigo;
    }
    public void setSaldoreal(Double _Saldoreal){
        this.Saldoreal=_Saldoreal;
    }

    public void setSaldoestimado(Double _Saldoestimado){
        this.Saldoestimado=_Saldoestimado;
    }
    public void setPrecioventa(Double _Precioventa){
        this.Precioventa=_Precioventa;
    }
    public void setPreciocosto(Double _Preciocosto){
        this.Preciocosto=_Preciocosto;
    }

//get
    public Integer getIdeitemcentral(){
        return Iditemcentral;
    }
    public String getNombre(){
        return Nombre;
    }
    public String getCodigo()
    {
        return Codigo;
    }
    public Double getSaldoreal(){
        return Saldoreal;
    }

    public Double getSaldoestimado(){
        return Saldoestimado;
    }
    public Double getPrecioventa(){
        return Precioventa;
    }
    public Double getPreciocosto(){
        return Preciocosto;
    }
    public int getId(){
        return id;
    }

}
