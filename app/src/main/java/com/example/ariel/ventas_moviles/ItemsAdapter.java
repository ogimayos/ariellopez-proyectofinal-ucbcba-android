package com.example.ariel.ventas_moviles;

/**
 * Created by ariel on 15/07/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ariel.ventas_moviles.BLL.Item;
import com.example.ariel.ventas_moviles.R;

import java.util.ArrayList;

/**
 * Created by ariel on 15/07/2015.
 */
class ItemsAdapter extends ArrayAdapter<Item> {
    ViewHolder holder;
    ArrayList<Item> datosa;
    View vv;
    public ItemsAdapter(Context context,ArrayList<Item> datos){
        super(context, R.layout.listado_items_table_view, datos);
        this.datosa = datos;
    }

    public void removeHolderPosition(int id){
        this.datosa.remove(id);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.vv = convertView;

        View item = vv;
        Item itemm = getItem(position);

        if(item == null){
            LayoutInflater inflater =   ((Activity)getContext()).getLayoutInflater();
            //item = inflater.inflate(R.layout.listado_clientes_view, null);
            item = inflater.inflate(R.layout.listado_items_table_view, null);

            holder = new ViewHolder();

            holder.IdCentralItem = (TextView)item.findViewById(R.id.LblIdCentralItem);
            holder.NombreItem = (TextView)item.findViewById(R.id.LblNombreItem);
            holder.CodigoItem = (TextView)item.findViewById(R.id.LblCodigoItem);
            holder.SaldoEstimadoItem = (TextView)item.findViewById(R.id.LblSaldoEstimadoItem);
            holder.SaldoRealItem = (TextView)item.findViewById(R.id.LblSaldoRealItem);
            holder.PrecioCostoItem = (TextView)item.findViewById(R.id.LblPrecioCostoItem);
            holder.PrecioVentaItem = (TextView)item.findViewById(R.id.LblPrecioVentaItem);


            item.setTag(holder);

        }
        else{
            holder = (ViewHolder)item.getTag();
        }

        holder.IdCentralItem.setText(itemm.getIdeitemcentral().toString());
        holder.NombreItem.setText(itemm.getNombre());
        holder.CodigoItem.setText(itemm.getCodigo());
        holder.SaldoRealItem.setText(itemm.getSaldoreal().toString());
        holder.SaldoEstimadoItem.setText(itemm.getSaldoestimado().toString());
        holder.PrecioCostoItem.setText(itemm.getPreciocosto().toString());
        holder.PrecioVentaItem.setText(itemm.getPrecioventa().toString());



        //////Tercera forma con optimizacion incluso de objetos del frame END

        return item;
    }
    //holder para optimizacion utilizado para guardar los objetos del layout
    static class ViewHolder {
        TextView IdCentralItem;
        TextView NombreItem;
        TextView CodigoItem;
        TextView SaldoRealItem;
        TextView SaldoEstimadoItem;
        TextView PrecioVentaItem;
        TextView PrecioCostoItem;

    }
}
