package com.example.ariel.ventas_moviles;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ariel.ventas_moviles.data.ClientesDbHelper;


public class RegClientes extends ActionBarActivity {


    //verificamos si algun dato esta viniendo de un anterior INTENT
    private static String editEmail =null;
    private static String editNit =null;
    private static String editNombre =null;
    private static String editTelefono =null;
    private static String editId =null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_clientes);

//recupero los datos que vienen del anterior INTENT BEGIN
        editEmail = getIntent().getStringExtra("editEmail");
        editNit = getIntent().getStringExtra("editNit");
        editNombre = getIntent().getStringExtra("editNombre");
        editTelefono = getIntent().getStringExtra("editTelefono");
        editId = getIntent().getStringExtra("editId");

        //recupero los datos que vienen del anterior INTENT END


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg_clientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnAddCliente) {

            Button btnRegClientes = (Button) findViewById(R.id.btnRegClientes);
            btnRegClientes.callOnClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


public int editMode = 0;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_reg_clientes, container, false);

//Log.w("",saveExtra);


            final EditText txtNombres = (EditText)rootView.findViewById(R.id.txtNombres);
            final EditText txtTelefono = (EditText)rootView.findViewById(R.id.txtTelefono);
            final EditText txtemail = (EditText)rootView.findViewById(R.id.txtEmail);
            final EditText txtNit = (EditText)rootView.findViewById(R.id.txtNit);

            final EditText txtidcliente = (EditText)rootView.findViewById(R.id.txtidcliente);

            if(editEmail!=null)
            {

                txtNombres.setText(editNombre);
                txtemail.setText(editEmail);
                txtNit.setText(editNit);
                txtTelefono.setText(editTelefono);
                txtidcliente.setText(editId);
                editMode = 1;

            }

            Button btnRegClientes = (Button) rootView.findViewById(R.id.btnRegClientes);
            btnRegClientes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ClientesDbHelper usdbh = new ClientesDbHelper(getActivity(),"Clientes",null,1);
                    SQLiteDatabase db = usdbh.getWritableDatabase();

                    if(db != null) {
                        if (txtNombres.getText().toString().isEmpty() || txtTelefono.getText().toString().isEmpty() || txtNit.getText().toString().isEmpty() || txtemail.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Debe ingresar todos los campos solicitados ", Toast.LENGTH_SHORT).show();
                        return;
                        } else {

                            //Generamos los datos

                            if (editMode == 1) {
                                //editamos el registro actual
                                db.execSQL("UPDATE Clientes set nombre = '" + txtNombres.getText() + "' , telefono = '" + txtTelefono.getText() + "' , nit = '" + txtNit.getText() + "' ,email = '" + txtemail.getText() + "' WHERE _id =" + Integer.parseInt(editId));

                            } else {


                                //Insertamos los datos en la tabla Usuarios
                                db.execSQL("INSERT INTO Clientes (nombre,nit,telefono,email) " +
                                        "VALUES ('" + txtNombres.getText() + "', '" + txtNit.getText() + "', '" + txtTelefono.getText() + "', '" + txtemail.getText() + "')");
                            }
    //para ver datos en logs begin
                            String[] campos = new String[]{"nombre", "telefono", "_id"};
                            Cursor c1 = db.query("Clientes", campos, null, null, null, null, null);
    //Nos aseguramos de que existe al menos un registro
                            if (c1.moveToFirst()) {
                                //Recorremos el cursor hasta que no haya más registros
                                do {
                                    String codigo = c1.getString(0);
                                    String nombre = c1.getString(1);
                                    String idd = c1.getString(2);
                                    Log.d("resultado", codigo + ":" + nombre + ":" + idd);

                                } while (c1.moveToNext());
                            }
                            // para ver datos en logs end
                            //Cerramos la base de datos
                            db.close();
                    }
                    }
                    Intent intenClientes5 = new Intent(getActivity(), ListClientes.class);


                    startActivity(intenClientes5);
                }
            });

            return rootView;
        }
    }
}
