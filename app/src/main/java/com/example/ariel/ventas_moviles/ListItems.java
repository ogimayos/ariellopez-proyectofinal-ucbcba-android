package com.example.ariel.ventas_moviles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.ventas_moviles.BLL.Item;
import com.example.ariel.ventas_moviles.data.ItemsDbHelper;
import com.example.ariel.ventas_moviles.data.ItemsProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ListItems extends ActionBarActivity {
    ListView listview2;
    private ResultAdapter resultAdapter;
    private ItemsAdapter items;
    ArrayList<Item> arrayItems = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);




        final TextView TxtListNombreCliente = (TextView)findViewById(R.id.txtListItemsNombreCliente);
        final TextView TxtListidCliente = (TextView)findViewById(R.id.txtListItemsidCliente);
        final TextView TxtListidPedido = (TextView)findViewById(R.id.txtListItemsidPedido);
        final TextView TxtListItemsNitCliente = (TextView)findViewById(R.id.txtListItemsNitCliente);


//capturamos datos que vienen desde el liestado de clientes
        TxtListNombreCliente.setText(getIntent().getStringExtra("ClienteVentaNombre"));
        TxtListidCliente.setText(getIntent().getStringExtra("ClienteVentaId"));
        TxtListItemsNitCliente.setText(getIntent().getStringExtra("ClienteVentaNit"));
        try {
            TxtListidPedido.setText(getIntent().getStringExtra("ClienteVentaIdPedido").toString());
        } catch(Exception e) {
            TxtListidPedido.setText("0");
            //Log.d("", "Failed to do something: " + e.getMessage());
        }
        /*if( getIntent().getStringExtra("ClienteVentaId") == null){
            TxtListidPedido.setText(getIntent().getStringExtra("ClienteVentaId"));
        }*/




        //EditText inputSearch;
        listview2 = (ListView) findViewById(R.id.listItems);

        //habilitar busqueda begin
        listview2.setTextFilterEnabled(true);
        //inputSearch = (EditText)findViewById(R.id.inputSearch);
        //habilitar busqueda end
        final  String[] values2;

        int i = 0;


        ItemsDbHelper usdbh2 = new ItemsDbHelper(this,"Items",null,1);
        SQLiteDatabase db = usdbh2.getWritableDatabase();
        String[] campos3 = new String[] {"_id","iditemcentral","nombre","codigo","saldoreal","saldoestimado","precioventa", "preciocosto"};
        Cursor cur = db.query("Items", campos3, null, null, null, null, null);

        // Do something in response to button click
        int y = cur.getCount();
        values2 = new String[y];
        if (cur.moveToFirst()) {
            Integer idItemCentral;
            String nombreItem;
            String codigoItem;
            Double saldoEstimadoItem;
            Double saldoRealItem;
            Double precioVentaItem;
            Double precioCostoItem;
            int ID;
            int idd = cur.getColumnIndex(ItemsProvider.Items._ID);
            int colidItemCentral = cur.getColumnIndex(ItemsProvider.Items.COL_IDITEMCENTRAL);
            int colNombreItem = cur.getColumnIndex(ItemsProvider.Items.COL_NOMBRE);
            int colcodigoItem = cur.getColumnIndex(ItemsProvider.Items.COL_CODIGO);
            int colsaldoEstimadoItem = cur.getColumnIndex(ItemsProvider.Items.COL_SALDOESTIMADO);
            int colsaldoRealItem = cur.getColumnIndex(ItemsProvider.Items.COL_SALDOREAL);
            int colprecioVentaItem = cur.getColumnIndex(ItemsProvider.Items.COL_PRECIOVENTA);
            int colprecioCostoItem = cur.getColumnIndex(ItemsProvider.Items.COL_PRECIOCOSTO);


            do {

                values2[i] = String.format("%d: %s - %s: %d - %f - %f - %f - %f",
                        cur.getInt(colidItemCentral),
                        cur.getString(colNombreItem),
                        cur.getString(colcodigoItem),
                        cur.getInt(idd),
                        cur.getDouble(colsaldoEstimadoItem),
                        cur.getDouble(colsaldoRealItem),
                        cur.getDouble(colprecioVentaItem),
                        cur.getDouble(colprecioCostoItem));

                idItemCentral = cur.getInt(colidItemCentral);
                nombreItem = cur.getString(colNombreItem);
                codigoItem = cur.getString(colcodigoItem);
                saldoEstimadoItem = cur.getDouble(colsaldoEstimadoItem);
                saldoRealItem = cur.getDouble(colsaldoRealItem);
                precioCostoItem = cur.getDouble(colprecioCostoItem);
                precioVentaItem = cur.getDouble(colprecioVentaItem);
                ID = cur.getInt(idd);


                i++;

                Item c = new Item(idItemCentral,nombreItem,codigoItem,saldoRealItem,saldoEstimadoItem,precioVentaItem,precioCostoItem, ID);
                arrayItems.add(c);

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
        items = new ItemsAdapter(this,arrayItems);

        //otro adaptador para prueba end

        //otro nuevo begin
        //ResultAdapter adaptador4 = new ResultAdapter(getActivity(),cur,0);
        //otro nuevo end
        //listview2.setAdapter(resultAdapter4);
        //listview2.setAdapter(adaptador3);

        listview2.setAdapter(items);
        /** Registering context menu for the listview */
        registerForContextMenu(listview2);
        db.close();

    }


    //para context menu begin
    /** This will be invoked when an item in the listview is long pressed */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

       ListItems.this.getMenuInflater().inflate(R.menu.actions_additems , menu);

    }

    /** This will be invoked when a menu item is selected */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        int iditem;

        switch(item.getItemId()){
            case R.id.cnt_mnuadditem_add:
                Intent AddItemToSold = new Intent(ListItems.this, PedidoItem.class);
                AddItemToSold.putExtra("sendIdCentralItem", Integer.toString(arrayItems.get(position).getIdeitemcentral()));
                AddItemToSold.putExtra("sendNombreItem", arrayItems.get(position).getNombre());
                AddItemToSold.putExtra("sendCodigoItem", arrayItems.get(position).getCodigo());
                AddItemToSold.putExtra("sendSaldoRealItem", Double.toString(arrayItems.get(position).getSaldoreal()));
                AddItemToSold.putExtra("sendSaldoEstimadoItem", Double.toString(arrayItems.get(position).getSaldoestimado()));
                AddItemToSold.putExtra("sendPrecioVentaItem",Double.toString(arrayItems.get(position).getPrecioventa()));
                AddItemToSold.putExtra("sendPrecioCostoItem", Double.toString(arrayItems.get(position).getPreciocosto()));

                AddItemToSold.putExtra("sendNombreCliente", getIntent().getStringExtra("ClienteVentaNombre"));
                AddItemToSold.putExtra("sendNitCliente", getIntent().getStringExtra("ClienteVentaNit"));
                AddItemToSold.putExtra("sendIdCliente", getIntent().getStringExtra("ClienteVentaId"));
                AddItemToSold.putExtra("sendIdPedido", getIntent().getStringExtra("ClienteVentaIdPedido"));
                startActivity(AddItemToSold);
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
        getMenuInflater().inflate(R.menu.menu_list_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sincronizaitems) {

            AlertDialog.Builder builder = new AlertDialog.Builder(ListItems.this);
            builder.setTitle("La sincronizacion actualizara su lista de ITEMS");

// Add the buttons
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Utilities.sincronizaItems(ListItems.this);

                    RefreshAfterSinc();

                    Toast.makeText(ListItems.this, "Sincronizacion Exitosa ", Toast.LENGTH_SHORT).show();
                    // User clicked OK button
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    Toast.makeText(ListItems.this, "Sincronizacion cancelada ", Toast.LENGTH_SHORT).show();
                }
            });

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
        //ListView listview22 = (ListView) getActivity().findViewById(R.id.result_list_view);
        listview2.setAdapter(null);
        arrayItems.clear();
        items.notifyDataSetChanged();
        items.notifyDataSetInvalidated();
        // final ListView listview22 = (ListView) rootView.findViewById(R.id.result_list_view);
        ItemsDbHelper usdbh22 = new ItemsDbHelper(ListItems.this,"Items",null,1);
        SQLiteDatabase db = usdbh22.getWritableDatabase();
        String[] campos3 = new String[] {"_id","iditemcentral","nombre","codigo","saldoreal","saldoestimado","precioventa", "preciocosto"};
        Cursor cur = db.query("Items", campos3, null, null, null, null, null);

        // Do something in response to button click
        int y = cur.getCount();

        if (cur.moveToFirst()) {
            Integer idItemCentral;
            String nombreItem;
            String codigoItem;
            Double saldoEstimadoItem;
            Double saldoRealItem;
            Double precioVentaItem;
            Double precioCostoItem;
            int ID;
            int idd = cur.getColumnIndex(ItemsProvider.Items._ID);
            int colidItemCentral = cur.getColumnIndex(ItemsProvider.Items.COL_IDITEMCENTRAL);
            int colNombreItem = cur.getColumnIndex(ItemsProvider.Items.COL_NOMBRE);
            int colcodigoItem = cur.getColumnIndex(ItemsProvider.Items.COL_CODIGO);
            int colsaldoEstimadoItem = cur.getColumnIndex(ItemsProvider.Items.COL_SALDOESTIMADO);
            int colsaldoRealItem = cur.getColumnIndex(ItemsProvider.Items.COL_SALDOREAL);
            int colprecioVentaItem = cur.getColumnIndex(ItemsProvider.Items.COL_PRECIOVENTA);
            int colprecioCostoItem = cur.getColumnIndex(ItemsProvider.Items.COL_PRECIOCOSTO);


            do {

                idItemCentral = cur.getInt(colidItemCentral);
                nombreItem = cur.getString(colNombreItem);
                codigoItem = cur.getString(colcodigoItem);
                saldoEstimadoItem = cur.getDouble(colsaldoEstimadoItem);
                saldoRealItem = cur.getDouble(colsaldoRealItem);
                precioCostoItem = cur.getDouble(colprecioCostoItem);
                precioVentaItem = cur.getDouble(colprecioVentaItem);
                ID = cur.getInt(idd);

                Item c = new Item(idItemCentral,nombreItem,codigoItem,saldoRealItem,saldoEstimadoItem,precioVentaItem,precioCostoItem, ID);
                arrayItems.add(c);

            } while (cur.moveToNext());


        }

        items = new ItemsAdapter(this,arrayItems);
        listview2.setAdapter(items);
        /** Registering context menu for the listview */
        registerForContextMenu(listview2);
        db.close();
        items.notifyDataSetChanged();
        items.notifyDataSetInvalidated();
    }

}
