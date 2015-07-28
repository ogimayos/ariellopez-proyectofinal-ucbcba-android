package com.example.ariel.ventas_moviles.BLL;

/**
 * Created by ariel on 08/04/2015.
 */
public class Cliente {
    private String Nombre;
    private String Telefono;
    private String Email;
    private String Nit;
    private int id;
    public Cliente(String nombre, String telefono, String email, String nit, int idd)
    {
        Nombre = nombre;
        Telefono = telefono;
        Email = email;
        Nit = nit;
        this.id = idd;
    }
    public String getNombre(){
        return Nombre;
    }
    public String getTelefono()
    {
        return Telefono;
    }
    public String getEmail(){
        return Email;
    }
    public String getNit(){
        return Nit;
    }
    public int getId(){
        return id;
    }

}
