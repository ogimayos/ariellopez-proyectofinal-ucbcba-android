package com.example.ariel.ventas_moviles;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.ventas_moviles.BLL.Cliente;
import com.example.ariel.ventas_moviles.data.ClientesDbHelper;
import com.example.ariel.ventas_moviles.data.ClientesProvider;
import com.example.ariel.ventas_moviles.data.PedidosDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.transform.Result;


public class ListClientes extends ActionBarActivity {
PlaceholderFragment p = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_clientes);
        if (savedInstanceState == null) {

            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();*/
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, p)
                    .commit();
        }







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_clientes, menu);

        //para incorporar el search
        // Associate searchable configuration with the SearchView
       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();// .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
*/


        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sincroniza) {


            AlertDialog.Builder builder = new AlertDialog.Builder(ListClientes.this);
            builder.setTitle("La sincronizacion actualizara su lista de CLIENTES");

// Add the buttons
            builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Utilities.sincronizaClientes(ListClientes.this);

                    p.RefreshAfterSinc();

                    Toast.makeText(ListClientes.this, "Sincronizacion Exitosa ", Toast.LENGTH_SHORT).show();
                    // User clicked OK button
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    Toast.makeText(ListClientes.this, "Sincronizacion cancelada ", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();




            /*android.app.Fragment frg = null;
            frg = getFragmentManager().findFragmentById(R.id.container);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();*/




            return true;
        }
        if(id == R.id.mnuRegClientes){
            Intent regClientes = new Intent(this, RegClientes.class);

            startActivity(regClientes);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        private ResultAdapter resultAdapter;
        private ClientesAdapter clientes;
        ArrayList<Cliente> arrayClientes = new ArrayList<Cliente>();
        ListView listview2;
        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list_clientes, container, false);

            EditText inputSearch;




            //PRIMERA OPCION BEGIN
            //resultAdapter = new ResultAdapter(getActivity(), null, 0);

            //ListView listView = (ListView)rootView.findViewById(R.id.result_list_view);

            //listView.setAdapter( resultAdapter);
//PRIMERA OPCION END

            //SEGUNDA OPCION BEGIN
/*
            final ListView listview = (ListView) rootView.findViewById(R.id.result_list_view);
            String[] values = new String[] { "ariel1", "Android", "iPhone", "WindowsMobile",
                    "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                    "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                    "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                    "Android", "iPhone", "WindowsMobile", "ariel2" };

            final ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < values.length; ++i) {
                list.add(values[i]);
            }


            final StableArrayAdapter adapter = new StableArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, list);

            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);


                                    list.remove(item);
                                    adapter.notifyDataSetChanged();

                }

            });
*/
//SEGUNDA OPCION END

//OTRA OPCION BEGIN

            //final ListView listview2 = (ListView) rootView.findViewById(R.id.result_list_view);
            listview2 = (ListView) rootView.findViewById(R.id.result_list_view);
            //habilitar busqueda begin
            listview2.setTextFilterEnabled(true);
            inputSearch = (EditText)rootView.findViewById(R.id.inputSearch);
            //habilitar busqueda end
          final  String[] values2;

            int i = 0;

            //Columnas de la tabla a recuperar
            String[] projection = new String[]{
                    ClientesProvider.Clientes._ID,
                    ClientesProvider.Clientes.COL_NOMBRE,
                    ClientesProvider.Clientes.COL_TELEFONO,
                    ClientesProvider.Clientes.COL_EMAIL,
                    ClientesProvider.Clientes.COL_NIT};

            Uri clientesUri = ClientesProvider.CONTENT_URI;

            ContentResolver cr = getActivity().getContentResolver();


            //cr.delete(ClientesProvider.CONTENT_URI, ClientesProvider.Clientes.COL_NOMBRE + " = 'Cliente'", null);

//Hacemos la consulta

            Cursor cur2 = cr.query(clientesUri,
                    projection, //Columnas a devolver
                    null,       //CondiciÃ³n de la query
                    null,       //Argumentos variables de la query
                    null);      //Orden de los resultados

            ClientesDbHelper usdbh2 = new ClientesDbHelper(getActivity(),"Clientes",null,1);
            SQLiteDatabase db = usdbh2.getWritableDatabase();
            String[] campos3 = new String[] {"_id","nombre", "telefono","email","nit"};
            Cursor cur = db.query("Clientes", campos3, null, null, null, null, null);

            //final ArrayList<Cliente> arrayClientes = new ArrayList<Cliente>();

            // Do something in response to button click
            int y = cur.getCount();
            values2 = new String[y];
            if (cur.moveToFirst()) {
                String nombre;
                String telefono;
                String email;
                String nit;
                int ID;
                int idd = cur.getColumnIndex(ClientesProvider.Clientes._ID);
                int colNombre = cur.getColumnIndex(ClientesProvider.Clientes.COL_NOMBRE);
                int colTelefono = cur.getColumnIndex(ClientesProvider.Clientes.COL_TELEFONO);
                int colEmail = cur.getColumnIndex(ClientesProvider.Clientes.COL_EMAIL);
                int colNit = cur.getColumnIndex(ClientesProvider.Clientes.COL_NIT);

                //TextView txtResultados = (TextView) getView().findViewById(R.id.details_text);
                //txtResultados.setText("");

                do {

values2[i] = String.format("%s: %d - %s - %s %d",
        cur.getString(colNombre),
        cur.getInt(idd),
        cur.getString(colEmail),
        cur.getString(colNit),
        cur.getInt(colTelefono));
//values2[i] =  cur.getString(colNombre);
                    nombre = cur.getString(colNombre);
                    telefono = cur.getString(colTelefono);
                    email = cur.getString(colEmail);
                    nit = cur.getString(colNit);
                    ID = cur.getInt(idd);

                    //txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");
i++;
Cliente c = new Cliente(nombre,telefono,email,nit, ID);
                    arrayClientes.add(c);
                    //para la nueva forma begin

                    //para la nueva forma end
                } while (cur.moveToNext());


            }
            final ArrayList<String> list2 = new ArrayList<String>();
            for (int ii = 0; ii < values2.length; ++ii) {
                list2.add(values2[ii]);
            }
            final StableArrayAdapter adapter2 = new StableArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, list2);
            //otro adaptador para prueba begin
            final ArrayAdapter<String> adaptador3 =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values2);
            //ResultAdapter resultAdapter4 = new ResultAdapter(getActivity(), null, 0);
 clientes = new ClientesAdapter(getActivity(),arrayClientes);

            //otro adaptador para prueba end

            //otro nuevo begin
            //ResultAdapter adaptador4 = new ResultAdapter(getActivity(),cur,0);
            //otro nuevo end
            //listview2.setAdapter(resultAdapter4);
            //listview2.setAdapter(adaptador3);

            listview2.setAdapter(clientes);
/* Activando el filtro de busqueda */
            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    final ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values2);
                    adaptador5.getFilter().filter(arg0);
                    listview2.setAdapter(adaptador5);






                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub

                }



            });



            //OTRA OPCION END
//agregamos un context menu begin
            /** Registering context menu for the listview */
            registerForContextMenu(listview2);

            ////Una forma de hacer una acciond e borrado sosteniendo un item
            ///----------------------------------------------------------------
//            listview2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                @Override
//                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                    arrayClientes.remove(position);
//                    clientes.notifyDataSetChanged();
//                    clientes.notifyDataSetInvalidated();
//                    return false;
//                }
//            });

            //agregamos un context menu end
db.close();

            return rootView;

        }





//para context menu begin
        /** This will be invoked when an item in the listview is long pressed */
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);

         getActivity().getMenuInflater().inflate(R.menu.actions , menu);

        }

        /*@Override
        public void onResume() {

            getFragmentManager().beginTransaction().detach(getParentFragment());
            getFragmentManager().beginTransaction().attach(getParentFragment());
            getFragmentManager().beginTransaction().commit();

            clientes.notifyDataSetChanged();
            clientes.notifyDataSetInvalidated();
            Toast.makeText(getActivity(), "refrescado : ", Toast.LENGTH_SHORT).show();
            super.onResume();
        }*/
        public void RefreshAfterSinc() {
           //ListView listview22 = (ListView) getActivity().findViewById(R.id.result_list_view);
            listview2.setAdapter(null);
            arrayClientes.clear();
            clientes.notifyDataSetChanged();
            clientes.notifyDataSetInvalidated();
           // final ListView listview22 = (ListView) rootView.findViewById(R.id.result_list_view);
            ClientesDbHelper usdbh22 = new ClientesDbHelper(getActivity(),"Clientes",null,1);
            SQLiteDatabase db = usdbh22.getWritableDatabase();
            String[] campos33 = new String[] {"_id","nombre", "telefono","email","nit"};
            Cursor cur = db.query("Clientes", campos33, null, null, null, null, null);
            int y = cur.getCount();

            if (cur.moveToFirst()) {
                String nombre;
                String telefono;
                String email;
                String nit;
                int ID;
                int idd = cur.getColumnIndex(ClientesProvider.Clientes._ID);
                int colNombre = cur.getColumnIndex(ClientesProvider.Clientes.COL_NOMBRE);
                int colTelefono = cur.getColumnIndex(ClientesProvider.Clientes.COL_TELEFONO);
                int colEmail = cur.getColumnIndex(ClientesProvider.Clientes.COL_EMAIL);
                int colNit = cur.getColumnIndex(ClientesProvider.Clientes.COL_NIT);



                do {

                    nombre = cur.getString(colNombre);
                    telefono = cur.getString(colTelefono);
                    email = cur.getString(colEmail);
                    nit = cur.getString(colNit);
                    ID = cur.getInt(idd);

                    //txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");

                    Cliente c = new Cliente(nombre,telefono,email,nit, ID);
                    arrayClientes.add(c);
                    //para la nueva forma begin

                    //para la nueva forma end
                } while (cur.moveToNext());


            }
            clientes = new ClientesAdapter(getActivity(),arrayClientes);

            //otro adaptador para prueba end

            //otro nuevo begin
            //ResultAdapter adaptador4 = new ResultAdapter(getActivity(),cur,0);
            //otro nuevo end
            //listview2.setAdapter(resultAdapter4);
            //listview2.setAdapter(adaptador3);

            listview2.setAdapter(clientes);
            clientes.notifyDataSetChanged();
            clientes.notifyDataSetInvalidated();
        }

        /** This will be invoked when a menu item is selected */
        @Override
        public boolean onContextItemSelected(MenuItem item) {

           AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int position = info.position;
            final int iditem;

            switch(item.getItemId()){
                case R.id.cnt_mnu_edit:
                    Intent EditaCliente = new Intent(getActivity(), RegClientes.class);
                    EditaCliente.putExtra("editEmail", arrayClientes.get(position).getEmail());
                    EditaCliente.putExtra("editNit", arrayClientes.get(position).getNit());
                    EditaCliente.putExtra("editNombre", arrayClientes.get(position).getNombre());
                    EditaCliente.putExtra("editTelefono", arrayClientes.get(position).getTelefono());
                    EditaCliente.putExtra("editId", Integer.toString(arrayClientes.get(position).getId()));
                    //EditaCliente.putExtra("id4","4");
                    startActivity(EditaCliente);

                    //Toast.makeText(getActivity(), "Edit : "+ info.position+":" +info.id, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cnt_mnu_delete:
                    iditem = arrayClientes.get(position).getId();



                    //Toast.makeText(getActivity(), "Delete : " + info.position+":" +info.id  , Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Esta Seguro de Eliminar este registro?");

// Add the buttons
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(BorrarRegistro(iditem)) {
                                //BorrarRegistro(position);
                                arrayClientes.remove(position);
                                clientes.notifyDataSetChanged();
                                clientes.notifyDataSetInvalidated();
                            }
                            // User clicked OK button
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Toast.makeText(getActivity(), "Eliminacion cancelada ", Toast.LENGTH_SHORT).show();
                        }
                    });
// Set other dialog properties


// Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    break;
                case R.id.cnt_mnu_pedido:
                    Toast.makeText(getActivity(), "Share : " + info.position+":" +info.id   , Toast.LENGTH_SHORT).show();
                    Intent ventaPedido = new Intent(getActivity(), ventaPedidos.class);
                    ventaPedido.putExtra("ClienteNombre", arrayClientes.get(position).getNombre());
                    ventaPedido.putExtra("ClienteId", Integer.toString(arrayClientes.get(position).getId()));
                    ventaPedido.putExtra("ClienteNit", arrayClientes.get(position).getNit());
                    startActivity(ventaPedido);
                    break;

            }
            return true;
        }
        //para context menu end

private Boolean BorrarRegistro(int id){
    Boolean eliminadook = false;
    ClientesDbHelper usdbh = new ClientesDbHelper(getActivity(),"Clientes",null,1);
    SQLiteDatabase db = usdbh.getWritableDatabase();

    PedidosDbHelper usdbhpedido = new PedidosDbHelper(getActivity(),"Pedidos",null,1);
    SQLiteDatabase dbpedido = usdbhpedido.getWritableDatabase();
    if(db != null) {

        Cursor cursorpedido = dbpedido.rawQuery("select * from Pedidos where idCliente =" + id, null);
        if (cursorpedido.getCount() > 0) {
            Toast.makeText(getActivity(), "No puede Eliminar el Cliente, Ya se relaciono a una venta ", Toast.LENGTH_SHORT).show();
            cursorpedido.close();
            eliminadook = false;

        } else {


            String s = "DELETE FROM Clientes WHERE _id =" + id;

            //Log.w("Consulta", s.toString());
            db.execSQL(s);
            eliminadook = true;
        }
        cursorpedido.close();
        dbpedido.close();
    }
   db.close();
    return eliminadook;
}

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
