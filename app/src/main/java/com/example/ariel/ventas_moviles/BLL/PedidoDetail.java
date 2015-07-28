package com.example.ariel.ventas_moviles.BLL;

/**
 * Created by ariel on 20/07/2015.
 */
public class PedidoDetail {
    private Integer idPedido;
    private Integer idItem;
    private String codigoItem;
    private String nombreItem;
    private Double precioUnitario;
    private Double cantidad;
    private Double total;

    private int id;

    public PedidoDetail(int IdPedido, int IdItem, String CodigoItem,
                  String NombreItem, Double PrecioUnitario, Double Cantidad, Double Total, int idd)
    {
        this.idPedido = IdPedido;

        this.idItem = IdItem;
        this.codigoItem = CodigoItem;
        this.nombreItem = NombreItem;
        this.precioUnitario = PrecioUnitario;
        this.cantidad = Cantidad;
        this.total = Total;
        this.id = idd;
    }
    //set
    public void setidPedido(Integer _idPedido){
        this.idPedido = _idPedido;
    }

    public void setidItem(Integer _idItem){
        this.idItem=_idItem;
    }
    public void setcodigoItem(String _codigoItem){
        this.codigoItem=_codigoItem;
    }
    public void setnombreItem(String _nombreItem){
        this.nombreItem=_nombreItem;
    }
    public void setprecioUnitario(Double _precioUnitario){
        this.precioUnitario=_precioUnitario;
    }
    public void setcantidad(Double _cantidad){
        this.cantidad=_cantidad;
    }
    public void settotal(Double _total){
        this.total=_total;
    }

    //get
    public Integer getidPedido(){
        return this.idPedido;
    }
    public Integer getidItem(){
        return this.idItem;
    }
    public String getcodigoItem(){
        return this.codigoItem;
    }
    public String getnombreItem(){
        return this.nombreItem;
    }
    public Double getprecioUnitario(){
        return this.precioUnitario;
    }
    public Double getcantidad(){
        return this.cantidad;
    }
    public Double gettotal(){
        return this.total;
    }
    public int getId(){
        return id;
    }

}

