package com.example.ariel.ventas_moviles;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.ventas_moviles.data.PedidosDbHelper;
import com.example.ariel.ventas_moviles.data.PedidosDetailDbHelper;


public class PedidoItem extends ActionBarActivity {

    //verificamos si algun dato esta viniendo de un anterior INTENT
    private static String sendIdCentralItem =null;
    private static String sendNombreItem =null;
    private static String sendCodigoItem =null;
    private static String sendSaldoRealItem =null;
    private static String sendSaldoEstimadoItem =null;
    private static String sendPrecioVentaItem =null;
    private static String sendPrecioCostoItem =null;
    private static String sendNombreCliente =null;
    private static String sendNitCliente =null;
    private static String sendIdCliente =null;
    private static String IdPedido =null;
    public int editMode = 0;
    private EditText cantidad;
private TextView Total;

    TextView txtCodigoItem;
    TextView txtNombreItem;
    TextView txtIdPedido;
    TextView txtIdCentralItem;
    TextView txtPrecioVentaItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_item);

        cantidad = (EditText) findViewById(R.id.txtAddItemCantidad);
        Total = (TextView)findViewById(R.id.LblAddItemTotal);

//verificamos si ya existe creado el ipPEDIDO


        TextView existIdPedido = (TextView)findViewById(R.id.LblAddIdPedido);


        //recupero los datos que vienen del anterior INTENT BEGIN
        sendIdCentralItem = getIntent().getStringExtra("sendIdCentralItem");
        sendNombreItem = getIntent().getStringExtra("sendNombreItem");
        sendCodigoItem = getIntent().getStringExtra("sendCodigoItem");
        sendSaldoRealItem = getIntent().getStringExtra("sendSaldoRealItem");
        sendSaldoEstimadoItem = getIntent().getStringExtra("sendSaldoEstimadoItem");
        sendPrecioVentaItem = getIntent().getStringExtra("sendPrecioVentaItem");
        sendPrecioCostoItem = getIntent().getStringExtra("sendPrecioCostoItem");
        //estos valores solo se obtienen con el afan de almacenarlos en la base de datos
        sendNombreCliente = getIntent().getStringExtra("sendNombreCliente");
        sendNitCliente = getIntent().getStringExtra("sendNitCliente");
        sendIdCliente = getIntent().getStringExtra("sendIdCliente");
        Toast.makeText(PedidoItem.this, "nombre cliente: "+sendNombreCliente, Toast.LENGTH_LONG).show();
        Toast.makeText(PedidoItem.this, "id cliente: "+sendIdCliente, Toast.LENGTH_LONG).show();
        //recupero los datos que vienen del anterior INTENT END

        txtIdCentralItem = (TextView)findViewById(R.id.LblAddIdCentralItem);
        txtNombreItem = (TextView)findViewById(R.id.LblAddNombreItem);
        TextView txtemail = (TextView)findViewById(R.id.LblAddCodigoItem);
        txtCodigoItem = (TextView)findViewById(R.id.LblAddSaldoEstimadoItem);
        TextView txtSaldoRealItem = (TextView)findViewById(R.id.LblAddSaldoRealItem);
        txtPrecioVentaItem = (TextView)findViewById(R.id.LblAddPrecioVentaItem);
        TextView txtPrecioCostoItem = (TextView)findViewById(R.id.LblAddPrecioCostoItem);

        txtIdPedido = (TextView)findViewById(R.id.LblAddIdPedido);
        try {
            txtIdPedido.setText(getIntent().getStringExtra("sendIdPedido"));
        } catch(Exception e) {
            txtIdPedido.setText("0");
            //Log.d("", "Failed to do something: " + e.getMessage());
        }
        /*if( !getIntent().getStringExtra("sendIdCliente").isEmpty()){
            txtIdPedido.setText(getIntent().getStringExtra("sendIdCliente"));
        }*/



        txtIdCentralItem.setText(sendIdCentralItem);
        txtNombreItem.setText(sendNombreItem);
        txtemail.setText(sendCodigoItem);
        txtCodigoItem.setText(sendSaldoRealItem);
        txtSaldoRealItem.setText(sendSaldoEstimadoItem);
        txtPrecioVentaItem.setText(sendPrecioVentaItem);
        txtPrecioCostoItem.setText(sendPrecioCostoItem);
        editMode = 1;



        cantidad.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here

                //Total.setText("after text change");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Total.setText("before change");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if(cantidad.getText().toString() == "" || cantidad.getText().toString().equals("") || cantidad.getText().toString() ==" ")
                {
                    Total.setText("0.00");
                }
                else {
                    Double c = Double.parseDouble(cantidad.getText().toString());
                    Double p = Double.parseDouble(txtPrecioVentaItem.getText().toString());
                    Double t = c*p;
                    Total.setText(t.toString());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pedido_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_additem) {
//creamos la cabecera
            PedidosDbHelper pedidosdbh = new PedidosDbHelper(PedidoItem.this,"Pedidos",null,1);
            SQLiteDatabase db = pedidosdbh.getWritableDatabase();
            if(db != null)
            {

                //Generamos los datos

                    //Insertamos los datos en la tabla Usuarios
                if(txtIdPedido.getText().equals(0) || txtIdPedido.getText().equals("0")) {
                    db.execSQL("INSERT INTO Pedidos (idCliente, Fecha, NombreCliente, NitCliente) " +
                            "VALUES (" + sendIdCliente + ", '2015-07-21', '"+sendNombreCliente+"', '"  + sendNitCliente + "')");

                    //db.execSQL("SELECT seq FROM SQLITE_SEQUENCE WHERE name='Pedidos'");
                    Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
                    c.moveToFirst();
                    int idNewPedido = c.getInt(0);

                    txtIdPedido.setText(Integer.toString(idNewPedido));
                }


                // para ver datos en logs end
                //Cerramos la base de datos
                db.close();
            }
//creamos el detalle
            PedidosDetailDbHelper pedidosdetaildbh = new PedidosDetailDbHelper(PedidoItem.this,"Pedidosdetail",null,1);
            SQLiteDatabase dbdetail = pedidosdetaildbh.getWritableDatabase();
            if(dbdetail != null)
            {

                //Generamos los datos

                //Insertamos los datos en la tabla Usuarios

                dbdetail.execSQL("INSERT INTO Pedidosdetail (idPedido, idItem, codigoItem,nombreItem,precioUnitario,cantidad,total) " +
                        "VALUES (" + Integer.parseInt(txtIdPedido.getText().toString()) + ", " + Integer.parseInt(txtIdCentralItem.getText().toString()) +", '"+txtCodigoItem.getText().toString()+"','"+txtNombreItem.getText().toString()+"',"+txtPrecioVentaItem.getText().toString()+","+Double.parseDouble(cantidad.getText().toString())+","+Double.parseDouble(Total.getText().toString())+")");


                // para ver datos en logs end
                //Cerramos la base de datos
                dbdetail.close();
            }
            Intent AddItem = new Intent(PedidoItem.this, ventaPedidos.class);

            AddItem.putExtra("addNombreItem", txtNombreItem.getText().toString());
            AddItem.putExtra("addCodigoItem", txtCodigoItem.getText().toString());
            AddItem.putExtra("addIdPedido", txtIdPedido.getText().toString());
            startActivity(AddItem);
            //finish();


            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
