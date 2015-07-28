package com.example.ariel.ventas_moviles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ariel.ventas_moviles.BLL.Pedido;
import com.example.ariel.ventas_moviles.BLL.PedidoDetail;
import com.example.ariel.ventas_moviles.data.PedidosDbHelper;
import com.example.ariel.ventas_moviles.data.PedidosProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListVentaPedidos extends ActionBarActivity {
    ListView listview2;
private PedidosAdapter pedidos;
    ArrayList<Pedido> arrayPedidos = new ArrayList<Pedido>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_venta_pedidos);

        //EditText inputSearch;
        listview2 = (ListView) findViewById(R.id.listVentaPedidos);

        //habilitar busqueda begin
        listview2.setTextFilterEnabled(true);
        //inputSearch = (EditText)findViewById(R.id.inputSearch);
        //habilitar busqueda end
        final  String[] values2;

        int i = 0;


        PedidosDbHelper usdbh2 = new PedidosDbHelper(this,"Pedidos",null,1);
        SQLiteDatabase db = usdbh2.getWritableDatabase();
        //String[] campos3 = new String[] {"_id","idCliente","Fecha","NombreCliente","idItem","codigoItem","nombreItem", "precioUnitario","cantidad","total"};
        String[] campos3 = new String[] {"_id","idCliente","Fecha","NombreCliente", "NitCliente"};
        Cursor cur = db.query("Pedidos", campos3, null, null, null, null, null);

        // Do something in response to button click
        int y = cur.getCount();
        values2 = new String[y];
        if (cur.moveToFirst()) {
            Integer idCliente;
            String Fecha;
            String NombreCliente;
            String NitCliente;

            int ID;
            int idd = cur.getColumnIndex(PedidosProvider.Pedidos._ID);
            int colidCliente = cur.getColumnIndex(PedidosProvider.Pedidos.COL_IDCLIENTE);
            int colFecha = cur.getColumnIndex(PedidosProvider.Pedidos.COL_FECHA);
            int colNombreCliente = cur.getColumnIndex(PedidosProvider.Pedidos.COL_NOMBRECLIENTE);
            int colNitCliente = cur.getColumnIndex(PedidosProvider.Pedidos.COL_NITCLIENTE);

            do {

                values2[i] = String.format("%d: %s - %s - %s - %d",
                        cur.getInt(colidCliente),
                        cur.getString(colFecha),
                        cur.getString(colNombreCliente),
                        cur.getString(colNitCliente),
                        cur.getInt(idd));

                idCliente = cur.getInt(colidCliente);
                Fecha = cur.getString(colFecha);
                NombreCliente = cur.getString(colNombreCliente);
                NitCliente = cur.getString(colNitCliente);

                ID = cur.getInt(idd);


                i++;

                Pedido c = new Pedido(idCliente,Fecha,NombreCliente,NitCliente,ID);

                arrayPedidos.add(c);

            } while (cur.moveToNext());


        }
        final ArrayList<String> list2 = new ArrayList<String>();
        for (int ii = 0; ii < values2.length; ++ii) {
            list2.add(values2[ii]);
        }
        final StableArrayAdapter adapter2 = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list2);
        //otro adaptador para prueba begin
        final ArrayAdapter<String> adaptador3 =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values2);
        //ResultAdapter resultAdapter4 = new ResultAdapter(getActivity(), null, 0);
        pedidos = new PedidosAdapter(this,arrayPedidos);

        //otro adaptador para prueba end

        //otro nuevo begin
        //ResultAdapter adaptador4 = new ResultAdapter(getActivity(),cur,0);
        //otro nuevo end
        //listview2.setAdapter(resultAdapter4);
        //listview2.setAdapter(adaptador3);

        listview2.setAdapter(pedidos);
        /** Registering context menu for the listview */
        registerForContextMenu(listview2);
        db.close();


    }

    //para context menu begin
    /** This will be invoked when an item in the listview is long pressed */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListVentaPedidos.this.getMenuInflater().inflate(R.menu.actions_list_venta_pedidos , menu);

    }

    /** This will be invoked when a menu item is selected */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        int iditem;

        switch(item.getItemId()){
            case R.id.cnt_mnulistventa_viewdetail:
                Intent VentaPedidoSelected = new Intent(ListVentaPedidos.this, ListVentaPedidosDetail.class);
                VentaPedidoSelected.putExtra("sendPedidoIdPedido", Integer.toString(arrayPedidos.get(position).getId()));
                VentaPedidoSelected.putExtra("sendPedidoFecha", arrayPedidos.get(position).getFecha());
                VentaPedidoSelected.putExtra("sendPedidoidCliente", Integer.toString(arrayPedidos.get(position).getidCliente()));
                VentaPedidoSelected.putExtra("sendPedidoNombreCliente", arrayPedidos.get(position).getNombreCliente());
                VentaPedidoSelected.putExtra("sendPedidoNitCliente", arrayPedidos.get(position).getNitCliente());
                startActivity(VentaPedidoSelected);
                //startActivityForResult(AddItemToSold,1);
//finish();



                //Toast.makeText(getActivity(), "Edit : "+ info.position+":" +info.id, Toast.LENGTH_SHORT).show();
                break;



        }
        return true;
    }
    //para context menu end
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_venta_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sincronizaventas) {


            AlertDialog.Builder builder = new AlertDialog.Builder(ListVentaPedidos.this);
            builder.setTitle("Luego de la sincronizacion se eliminaran todas las ventas");

// Add the buttons
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Utilities.sincronizaPedidos(ListVentaPedidos.this);

                    RefreshAfterSinc();
                    Toast.makeText(ListVentaPedidos.this, "Sincronizacion Exitosa ", Toast.LENGTH_SHORT).show();
                    // User clicked OK button
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    Toast.makeText(ListVentaPedidos.this, "Sincronizacion cancelada ", Toast.LENGTH_SHORT).show();
                }
            });
// Set other dialog properties


// Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }
    }

    public void RefreshAfterSinc() {
        listview2.setAdapter(null);
        arrayPedidos.clear();
        pedidos.notifyDataSetChanged();
        pedidos.notifyDataSetInvalidated();

        PedidosDbHelper usdbh2 = new PedidosDbHelper(this,"Pedidos",null,1);
        SQLiteDatabase db2 = usdbh2.getWritableDatabase();
        //String[] campos3 = new String[] {"_id","idCliente","Fecha","NombreCliente","idItem","codigoItem","nombreItem", "precioUnitario","cantidad","total"};
        String[] campos3 = new String[] {"_id","idCliente","Fecha","NombreCliente", "NitCliente"};
        Cursor cur = db2.query("Pedidos", campos3, null, null, null, null, null);

        // Do something in response to button click
        int y = cur.getCount();

        if (cur.moveToFirst()) {
            Integer idCliente;
            String Fecha;
            String NombreCliente;
            String NitCliente;

            int ID;
            int idd = cur.getColumnIndex(PedidosProvider.Pedidos._ID);
            int colidCliente = cur.getColumnIndex(PedidosProvider.Pedidos.COL_IDCLIENTE);
            int colFecha = cur.getColumnIndex(PedidosProvider.Pedidos.COL_FECHA);
            int colNombreCliente = cur.getColumnIndex(PedidosProvider.Pedidos.COL_NOMBRECLIENTE);
            int colNitCliente = cur.getColumnIndex(PedidosProvider.Pedidos.COL_NITCLIENTE);

            do {



                idCliente = cur.getInt(colidCliente);
                Fecha = cur.getString(colFecha);
                NombreCliente = cur.getString(colNombreCliente);
                NitCliente = cur.getString(colNitCliente);

                ID = cur.getInt(idd);




                Pedido c = new Pedido(idCliente,Fecha,NombreCliente,NitCliente,ID);

                arrayPedidos.add(c);

            } while (cur.moveToNext());


        }


        //ResultAdapter resultAdapter4 = new ResultAdapter(getActivity(), null, 0);
        pedidos = new PedidosAdapter(this,arrayPedidos);
        listview2.setAdapter(pedidos);

        db2.close();
        pedidos.notifyDataSetChanged();
        pedidos.notifyDataSetInvalidated();


    }
}
