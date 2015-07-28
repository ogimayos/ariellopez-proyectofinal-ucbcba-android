package com.example.ariel.ventas_moviles;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ariel.ventas_moviles.BLL.Pedido;

import java.util.ArrayList;

/**
 * Created by ariel on 19/07/2015.
 */
public class PedidosAdapter extends ArrayAdapter<Pedido> {
    ViewHolder holder;
    ArrayList<Pedido> datosa;
    View vv;
    public PedidosAdapter(Context context,ArrayList<Pedido> datos){
        super(context, R.layout.listado_pedidos_table_view, datos);
        this.datosa = datos;
    }

    public void removeHolderPosition(int id){
        this.datosa.remove(id);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.vv = convertView;

        View pedido = vv;
        Pedido pedidom = getItem(position);

        if(pedido == null){
            LayoutInflater inflater =   ((Activity)getContext()).getLayoutInflater();
            //item = inflater.inflate(R.layout.listado_clientes_view, null);
            pedido = inflater.inflate(R.layout.listado_pedidos_table_view, null);

            holder = new ViewHolder();

            holder.hidCliente = (TextView)pedido.findViewById(R.id.LblPedidosidCliente);
            holder.hFecha = (TextView)pedido.findViewById(R.id.LblPedidosFecha);
            holder.hNombreCliente = (TextView)pedido.findViewById(R.id.LblPedidosNombreCliente);
            holder.hNitCliente = (TextView)pedido.findViewById(R.id.LblPedidosNitCliente);
            holder.hidPedido = (TextView)pedido.findViewById(R.id.LblPedidosidPedido);


            pedido.setTag(holder);

        }
        else{
            holder = (ViewHolder)pedido.getTag();
        }
        holder.hidCliente.setText(pedidom.getidCliente().toString());
        holder.hFecha.setText(pedidom.getFecha());
        holder.hNombreCliente.setText(pedidom.getNombreCliente());
        holder.hNitCliente.setText(pedidom.getNitCliente());
        holder.hidPedido.setText(pedidom.getId().toString());



        //////Tercera forma con optimizacion incluso de objetos del frame END

        return pedido;
    }
    //holder para optimizacion utilizado para guardar los objetos del layout
    static class ViewHolder {
        TextView hidCliente;
        TextView hFecha;
        TextView hNombreCliente;
        TextView hNitCliente;
        TextView hidPedido;

        TextView htotal;

    }
}
