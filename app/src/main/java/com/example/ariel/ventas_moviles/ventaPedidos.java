package com.example.ariel.ventas_moviles;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ariel.ventas_moviles.BLL.Item;


import com.example.ariel.ventas_moviles.BLL.PedidoDetail;
import com.example.ariel.ventas_moviles.data.ItemsDbHelper;
import com.example.ariel.ventas_moviles.data.ItemsProvider;
import com.example.ariel.ventas_moviles.data.PedidosDbHelper;
import com.example.ariel.ventas_moviles.data.PedidosDetailDbHelper;
import com.example.ariel.ventas_moviles.data.PedidosDetailProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ventaPedidos extends ActionBarActivity {
    private PedidosDetailAdapter pedidosdetail;
    ArrayList<PedidoDetail> arrayPedidosDetail = new ArrayList<PedidoDetail>();
    private ResultAdapter resultAdapter;
    private ItemsAdapter adapter;
    private List<Item> listItems;
    private ListView list;

    private static String addNombreItem =null;
    private static String addCodigoItem =null;
    private static String addIdPedido =null;

    int request_code = 1;

    ArrayList<Item> arrayItems = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_pedidos);


        final TextView TxtVentaNombreCliente = (TextView)findViewById(R.id.txtVentaNombreCliente);
        final TextView TxtVentaidCliente = (TextView)findViewById(R.id.txtVentaidCliente);
        final TextView TxtVentaidPedido = (TextView)findViewById(R.id.txtVentaidPedido);
        final TextView TxtVentaFecha = (TextView)findViewById(R.id.txtVentaFecha);
        final TextView TxtVentaNitCliente = (TextView)findViewById(R.id.txtVentaNitCliente);


//capturamos datos que vienen desde el liestado de clientes
        TxtVentaNombreCliente.setText(getIntent().getStringExtra("ClienteNombre"));
        TxtVentaidCliente.setText(getIntent().getStringExtra("ClienteId"));
        TxtVentaNitCliente.setText(getIntent().getStringExtra("ClienteNit"));



        Button btnadditems = (Button) findViewById(R.id.btnVentaAdd);
        btnadditems.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent addItem = new Intent(ventaPedidos.this, ListItems.class);
                addItem.putExtra("ClienteVentaNombre", TxtVentaNombreCliente.getText());
                addItem.putExtra("ClienteVentaId", TxtVentaidCliente.getText());
                addItem.putExtra("ClienteVentaNit", TxtVentaNitCliente.getText());
                addItem.putExtra("ClienteVentaIdPedido", TxtVentaidPedido.getText());

                startActivity(addItem);
                //startActivityForResult(addItem, request_code);


            }
        });

        //captura los datos que vienen desde el Pedido
        addNombreItem = getIntent().getStringExtra("addNombreItem");
        addCodigoItem = getIntent().getStringExtra("addCodigoItem");
        addIdPedido = getIntent().getStringExtra("addIdPedido");
        try {
            TxtVentaidPedido.setText(getIntent().getStringExtra("addIdPedido").toString());
            //suponemos que ya se creo la cabecera en la base de datos y por ende podemos capturar los datos por consulta
            PedidosDbHelper pedidosdbh = new PedidosDbHelper(ventaPedidos.this,"Pedidos",null,1);
            SQLiteDatabase db = pedidosdbh.getWritableDatabase();
            //Cursor c = db.rawQuery("SELECT idCliente,Fecha,NombreCliente from Pedidos where _id ="+addIdPedido, null);
            //Cursor c = db.Query("SELECT Fecha from Pedidos where _id ="+addIdPedido, null);

            String[] campos3 = new String[] {"idCliente","Fecha","NombreCliente","NitCliente"};

            //Cursor cur = db.query("Pedidosdetail", campos3, null, null, null, null, null);
            Cursor c = db.query("Pedidos", campos3, " _id = " + addIdPedido, null, null, null, null);

            c.moveToFirst();
            int vidcliente = c.getInt(0);
            String vfecha = String.valueOf(c.getString(1));
            String vnombrecliente = String.valueOf(c.getString(2));
            String vnitcliente = String.valueOf(c.getString(3));
            TxtVentaidCliente.setText(Integer.toString(vidcliente));
            TxtVentaFecha.setText(vfecha.toString());
            TxtVentaNombreCliente.setText(vnombrecliente.toString());
            TxtVentaNitCliente.setText(vnitcliente.toString());
            db.close();

        } catch(Exception e) {
            TxtVentaidPedido.setText("0");
            //Log.d("", "Failed to do something: " + e.getMessage());
        }

        /*if(!addIdPedido.isEmpty()) {
            TxtVentaidPedido.setText(addIdPedido.toString());
        }*/

        //---------------------------

        list = (ListView) findViewById(R.id.listViewVentaItems);
        listItems = new ArrayList<Item>();
        Item item;
        int i = 0;
        PedidosDetailDbHelper usdbhdetail = new PedidosDetailDbHelper(this,"Pedidosdetail",null,1);
        SQLiteDatabase dbdetail = usdbhdetail.getWritableDatabase();
        //String[] campos3 = new String[] {"_id","idCliente","Fecha","NombreCliente","idItem","codigoItem","nombreItem", "precioUnitario","cantidad","total"};
        String[] campos3 = new String[] {"_id","idPedido","idItem","codigoItem","nombreItem", "precioUnitario","cantidad","total"};

        //Cursor cur = db.query("Pedidosdetail", campos3, null, null, null, null, null);
        Cursor curdetail = dbdetail.query("Pedidosdetail", campos3, " idPedido = " + getIntent().getStringExtra("addIdPedido"), null, null, null, null);

        int y = curdetail.getCount();

        if (curdetail.moveToFirst()) {
            Integer idPedido;
            Integer idItem;
            String codigoItem;
            String nombreItem;
            Double precioUnitario;
            Double cantidad;
            Double total;
            int ID;
            int idd = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail._ID);
            int colidPedido = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_IDPEDIDO);
            int colidItem = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_IDITEM);
            int colcodigoItem = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_CODIGOITEM);
            int colnombreItem = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_NOMBREITEM);
            int colprecioUnitario = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_PRECIOUNITARIO);
            int colcantidad = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_CANTIDAD);
            int coltotal = curdetail.getColumnIndex(PedidosDetailProvider.PedidosDetail.COL_TOTAL);
            do {

                idPedido = curdetail.getInt(colidPedido);
                idItem = curdetail.getInt(colidItem);
                codigoItem = curdetail.getString(colcodigoItem);
                nombreItem = curdetail.getString(colnombreItem);
                precioUnitario = curdetail.getDouble(colprecioUnitario);
                cantidad = curdetail.getDouble(colcantidad);
                total = curdetail.getDouble(coltotal);

                ID = curdetail.getInt(idd);


                i++;

                PedidoDetail c = new PedidoDetail(idPedido,idItem,codigoItem,nombreItem,precioUnitario,
                        cantidad,total, ID);

                arrayPedidosDetail.add(c);

            } while (curdetail.moveToNext());


        }
        pedidosdetail = new PedidosDetailAdapter(this,arrayPedidosDetail);




        //Aqui rellenamos solo con un libro, pero se puede descargar una lista de internet o leyendo de base de datos por ejemplo.
        /*item = new Item(0,"","",0.00,0.00,0.00,0.00,0);
        item.setCodigo("codigo1111");
        item.setNombre("nombre111");
        listItems.add(item);
        arrayItems.add(item);*/


//Ahora rellenamos el ListView

        /*adapter = new ItemsAdapter(this, arrayItems);
        list.setAdapter(adapter);*/
//Posteriormente podemos a�adir m�s items a nuestro listview de la siguiente forma

//primero a�adimos m�s objetos a nuestra lista
        /*item = new Item(0,"","",0.00,0.00,0.00,0.00,0);
        item.setCodigo("codigo2222");
        item.setNombre("nombre222");
        listItems.add(item);
        arrayItems.add(item);



        item = new Item(0,"","",0.00,0.00,0.00,0.00,0);
        item.setCodigo(addCodigoItem);
        item.setNombre(addNombreItem);
        listItems.add(item);
        arrayItems.add(item);*/
        //--------------------

//y luego le decimos a nuestro adapter que notifique los cambios correspondientes y que actualice los items del ListView


        list.setAdapter(pedidosdetail);
        //pedidosdetail.notifyDataSetChanged();
        dbdetail.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_venta_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_listo_ventapedidos) {
            Intent intenClientes = new Intent(ventaPedidos.this, ListClientes.class);

            startActivity(intenClientes);
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
