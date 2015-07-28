package com.example.ariel.ventas_moviles;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ariel.ventas_moviles.BLL.PedidoDetail;
import com.example.ariel.ventas_moviles.data.PedidosDetailDbHelper;
import com.example.ariel.ventas_moviles.data.PedidosDetailProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListVentaPedidosDetail extends ActionBarActivity {

    private static String SendPedidoFecha =null;
    private PedidosDetailAdapter pedidosdetail;
    ArrayList<PedidoDetail> arrayPedidosDetail = new ArrayList<PedidoDetail>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_venta_pedidos_detail);

        //EditText inputSearch;
        final ListView listview2 = (ListView) findViewById(R.id.listVentaPedidosDetail);
        final TextView LblPedidosdetailidPedido = (TextView) findViewById(R.id.LblPedidosdetailidPedido);
        final TextView LblPedidosDetailidCliente = (TextView) findViewById(R.id.LblPedidosDetailidCliente);
        final TextView LblPedidosDetailFecha = (TextView) findViewById(R.id.LblPedidosDetailFecha);
        final TextView LblPedidosDetailNombreCliente = (TextView) findViewById(R.id.LblPedidosDetailNombreCliente);

        //captura los datos que vienen de cabecera BEGIN
        //SendPedidoFecha = getIntent().getStringExtra("sendPedidoFecha");
        LblPedidosdetailidPedido.setText(getIntent().getStringExtra("sendPedidoIdPedido"));
        LblPedidosDetailidCliente.setText(getIntent().getStringExtra("sendPedidoidCliente"));
        LblPedidosDetailFecha.setText(getIntent().getStringExtra("sendPedidoFecha"));
        LblPedidosDetailNombreCliente.setText(getIntent().getStringExtra("sendPedidoNombreCliente"));
        //LblPedidosDetailFecha.setText(SendPedidoFecha);
        //captura datos de cabecera END


        //habilitar busqueda begin
        listview2.setTextFilterEnabled(true);
        //inputSearch = (EditText)findViewById(R.id.inputSearch);
        //habilitar busqueda end
        final  String[] values2;

        int i = 0;


        PedidosDetailDbHelper usdbh2 = new PedidosDetailDbHelper(this,"Pedidosdetail",null,1);
        SQLiteDatabase db = usdbh2.getWritableDatabase();
        //String[] campos3 = new String[] {"_id","idCliente","Fecha","NombreCliente","idItem","codigoItem","nombreItem", "precioUnitario","cantidad","total"};
        String[] campos3 = new String[] {"_id","idPedido","idItem","codigoItem","nombreItem", "precioUnitario","cantidad","total"};

        //Cursor cur = db.query("Pedidosdetail", campos3, null, null, null, null, null);
        Cursor cur = db.query("Pedidosdetail", campos3, " idPedido = " + getIntent().getStringExtra("sendPedidoIdPedido"), null, null, null, null);

        // Do something in response to button click
        int y = cur.getCount();
        values2 = new String[y];
        if (cur.moveToFirst()) {
            Integer idPedido;
            Integer idItem;
            String codigoItem;
            String nombreItem;
            Double precioUnitario;
            Double cantidad;
            Double total;
            int ID;
            int idd = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail._ID);
            int colidPedido = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_IDPEDIDO);
            int colidItem = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_IDITEM);
            int colcodigoItem = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_CODIGOITEM);
            int colnombreItem = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_NOMBREITEM);
            int colprecioUnitario = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_PRECIOUNITARIO);
            int colcantidad = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_CANTIDAD);
            int coltotal = cur.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_TOTAL);
            do {

                values2[i] = String.format("%d: %d - %d: %s - %s - %f - %f - %f",
                        cur.getInt(colidPedido),
                        cur.getInt(idd),
                        cur.getInt(colidItem),
                        cur.getString(colcodigoItem),
                        cur.getString(colnombreItem),
                        cur.getDouble(colprecioUnitario),
                        cur.getDouble(colcantidad),
                        cur.getDouble(coltotal));

                idPedido = cur.getInt(colidPedido);
                idItem = cur.getInt(colidItem);
                codigoItem = cur.getString(colcodigoItem);
                nombreItem = cur.getString(colnombreItem);
                precioUnitario = cur.getDouble(colprecioUnitario);
                cantidad = cur.getDouble(colcantidad);
                total = cur.getDouble(coltotal);

                ID = cur.getInt(idd);


                i++;

                PedidoDetail c = new PedidoDetail(idPedido,idItem,codigoItem,nombreItem,precioUnitario,
                        cantidad,total, ID);

                arrayPedidosDetail.add(c);

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
        pedidosdetail = new PedidosDetailAdapter(this,arrayPedidosDetail);

        //otro adaptador para prueba end

        //otro nuevo begin
        //ResultAdapter adaptador4 = new ResultAdapter(getActivity(),cur,0);
        //otro nuevo end
        //listview2.setAdapter(resultAdapter4);
        //listview2.setAdapter(adaptador3);

        listview2.setAdapter(pedidosdetail);
        /** Registering context menu for the listview */
        registerForContextMenu(listview2);
        db.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_venta_pedidos_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
}
