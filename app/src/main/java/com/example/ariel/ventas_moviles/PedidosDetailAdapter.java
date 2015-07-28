package com.example.ariel.ventas_moviles;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ariel.ventas_moviles.BLL.PedidoDetail;

import java.util.ArrayList;

/**
 * Created by ariel on 20/07/2015.
 */
public class PedidosDetailAdapter extends ArrayAdapter<PedidoDetail> {
    ViewHolder holder;
    ArrayList<PedidoDetail> datosa;
    View vv;
    public PedidosDetailAdapter(Context context,ArrayList<PedidoDetail> datos){
        super(context, R.layout.listado_pedidosdetail_table_view, datos);
        this.datosa = datos;
    }

    public void removeHolderPosition(int id){
        this.datosa.remove(id);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.vv = convertView;

        View pedido = vv;
        PedidoDetail pedidom = getItem(position);

        if(pedido == null){
            LayoutInflater inflater =   ((Activity)getContext()).getLayoutInflater();
            //item = inflater.inflate(R.layout.listado_clientes_view, null);
            pedido = inflater.inflate(R.layout.listado_pedidosdetail_table_view, null);

            holder = new ViewHolder();

            holder.hidPedido = (TextView)pedido.findViewById(R.id.LblPedidosdetailidPedido);
            holder.hidItem = (TextView)pedido.findViewById(R.id.LblPedidosdetailidItem);
            holder.hcodigoItem = (TextView)pedido.findViewById(R.id.LblPedidosdetailcodigoItem);
            holder.hnombreItem = (TextView)pedido.findViewById(R.id.LblPedidosdetailnombreItem);
            holder.hprecioUnitario = (TextView)pedido.findViewById(R.id.LblPedidosdetailprecioUnitario);
            holder.hcantidad = (TextView)pedido.findViewById(R.id.LblPedidosdetailcantidad);
            holder.htotal = (TextView)pedido.findViewById(R.id.LblPedidosdetailtotal);
            pedido.setTag(holder);

        }
        else{
            holder = (ViewHolder)pedido.getTag();
        }

        holder.hidItem.setText(pedidom.getidItem().toString());
        holder.hcodigoItem.setText(pedidom.getcodigoItem());
        holder.hnombreItem.setText(pedidom.getnombreItem());
        holder.hprecioUnitario.setText(pedidom.getprecioUnitario().toString());
        holder.hcantidad.setText(pedidom.getcantidad().toString());
        holder.htotal.setText(pedidom.gettotal().toString());

        //////Tercera forma con optimizacion incluso de objetos del frame END

        return pedido;
    }
    //holder para optimizacion utilizado para guardar los objetos del layout
    static class ViewHolder {
        TextView hidPedido;
        TextView hidItem;
        TextView hcodigoItem;
        TextView hnombreItem;
        TextView hprecioUnitario;
        TextView hcantidad;
        TextView htotal;

    }
}
