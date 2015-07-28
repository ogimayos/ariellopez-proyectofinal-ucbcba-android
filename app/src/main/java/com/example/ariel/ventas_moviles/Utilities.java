package com.example.ariel.ventas_moviles;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.ariel.ventas_moviles.BLL.Cliente;
import com.example.ariel.ventas_moviles.BLL.Item;
import com.example.ariel.ventas_moviles.BLL.Pedido;
import com.example.ariel.ventas_moviles.data.ClientesDbHelper;
import com.example.ariel.ventas_moviles.data.ClientesProvider;
import com.example.ariel.ventas_moviles.data.ItemsDbHelper;
import com.example.ariel.ventas_moviles.data.ItemsProvider;
import com.example.ariel.ventas_moviles.data.PedidosDbHelper;
import com.example.ariel.ventas_moviles.data.PedidosDetailDbHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ariel on 27/03/2015.
 */
public class Utilities {
    private static final String LOG_TAG = Utilities.class.getSimpleName();

    public static boolean sincronizaClientes(Activity activity){
        final  String[] values2;
        ClientesAdapter clientes;
        ArrayList<Cliente> arrayClientes = new ArrayList<Cliente>();
        HashMap objMap = new HashMap();
        int i = 0;
        final JSONArray jsonclientes = new JSONArray();

        ClientesDbHelper usdbh2 = new ClientesDbHelper(activity,"Clientes",null,1);
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

                JSONObject jsoncliente = new JSONObject();
                try {
                    jsoncliente.put("nombre", nombre);
                    jsoncliente.put("telefono", telefono);
                    jsoncliente.put("email", email);
                    jsoncliente.put("nit", nit);
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                //txtResultados.append(nombre + " - " + telefono + " - " + email + "\n");
                i++;
                Cliente c = new Cliente(nombre,telefono,email,nit, ID);



                arrayClientes.add(c);
jsonclientes.put(jsoncliente);

                //para la nueva forma begin

                //para la nueva forma end
            } while (cur.moveToNext());


        }
        final ArrayList<String> list2 = new ArrayList<String>();
        for (int ii = 0; ii < values2.length; ++ii) {
            list2.add(values2[ii]);
        }


       /* Comprobamos que no venga alguno en blanco. */
       // if (!arrayClientes.isEmpty()){
            /* Creamos el objeto cliente que realiza la petición al servidor */
            HttpClient cliente = new DefaultHttpClient();
            /* Definimos la ruta al servidor. En mi caso, es un servlet. */

            HttpPost post = new HttpPost("http://www.saeta-erp.com/webservice/sincronizaclientes.php");

            try{
                /* Defino los parámetros que enviaré. Primero el nombre del parámetro, seguido por el valor. Es lo mismo que hacer un
                 http://192.168.0.142:8080/marcoWeb/Login?username=mario&pass=maritoPass&convertir=no */
                List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);

                /* Encripto la contraseña en MD5. Definición más abajo */
                //nvp.add(new BasicNameValuePair("password", toMd5(pass)));
                nvp.add(new BasicNameValuePair("clientes", "arui"));
                nvp.add(new BasicNameValuePair("enviado", "si"));
                /* Agrego los parámetros a la petición */
                //post.setEntity(new UrlEncodedFormEntity(nvp));


                //post.setEntity(new ByteArrayEntity(jsonclientes.toString().getBytes("UTF8")));
                post.setEntity(new ByteArrayEntity(jsonclientes.toString().getBytes()));
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Accept", "JSON");

                /* Ejecuto la petición, y guardo la respuesta */
                HttpResponse respuesta = cliente.execute(post);


Log.w("a",respuesta.toString());



                ClientesDbHelper usdbh25 = new ClientesDbHelper(activity,"Clientes",null,1);
                SQLiteDatabase db5 = usdbh25.getWritableDatabase();

                try{
                    String result ="";
                    HttpEntity entity = respuesta.getEntity();
                    if (entity != null) {
                        InputStream instream = entity.getContent();
                        result = convertStreamToString(instream);


                        db5.execSQL("delete from clientes");



                    }

                    //borramos los datos de clientes


                    //esto funciona cuando solo viene un array en el json de respuesta del servidor
                    /*JSONObject job = new JSONObject(result.toString());
                    String re = job.getString("status");*/
                    //esta parte fuiciona cuando vienen varios arrays en el json
                    JSONArray jsonarray = new JSONArray(result);
                    String re = "";
                    for(int ii=0; ii<jsonarray.length(); ii++){
                        JSONObject obj = jsonarray.getJSONObject(ii);
                        try {
                            String fsnombre = obj.getString("nombre");
                            String fstelefono = obj.getString("telefono");
                            String fsemail = obj.getString("email");
                            String fsnit = obj.getString("nit");
                            re = "success";

                            //sincronizamos hacia el movil

                                //Insertamos los datos en la tabla Usuarios
                                db5.execSQL("INSERT INTO Clientes (nombre,nit,telefono,email) " +
                                        "VALUES ('" + fsnombre + "', '" + fsnit + "', '" + fstelefono + "', '" + fsemail + "')");







                        }catch (JSONException e2) {
                            re = "error";
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }


                    }
                    db5.close();
                    if(!re.isEmpty()) {
                        //la sincronizacion hacia el server ha sido exitosa, ahora sincronizamos hacia el movil




                        Toast.makeText(activity, "Sincronizacion Exitosa", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else{
                        Toast.makeText(activity, "Hubo un problema, intente de nuevo mas Tarde", Toast.LENGTH_SHORT).show();
                        return false;

                    }

                }catch(JSONException ex){
                    Log.w("Aviso", ex.toString());
                    return false;
                }

            }catch(ClientProtocolException ex){
                Log.w("ClientProtocolException", ex.toString());
                return false;
            }catch(UnsupportedEncodingException ex){
                Log.w("UnsuportEncodinExceptio", ex.toString());
                return false;
            }catch(IOException ex){
                Log.w("IOException", ex.toString());
                return false;
            }
            catch(IllegalStateException ex){
                Log.w("IOException", ex.toString());
                return false;
            }
        /*}else{
            //Toast.makeText(getActivity(), "Campo vac'io !",Toast.LENGTH_LONG).show();
            return false;
        }*/
}



    public static boolean sincronizaItems(Activity activity){
        final JSONArray jsonitems = new JSONArray();

            /* Creamos el objeto cliente que realiza la petición al servidor */
            HttpClient cliente = new DefaultHttpClient();
            /* Definimos la ruta al servidor. En mi caso, es un servlet. */

            HttpPost post = new HttpPost("http://www.saeta-erp.com/webservice/sincronyzeitemtoserver.php");

            try{
                /* Defino los parámetros que enviaré. Primero el nombre del parámetro, seguido por el valor. Es lo mismo que hacer un
                 http://192.168.0.142:8080/marcoWeb/Login?username=mario&pass=maritoPass&convertir=no */
                List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
                nvp.add(new BasicNameValuePair("email", "ariel"));
                /* Encripto la contraseña en MD5. Definición más abajo */
                //nvp.add(new BasicNameValuePair("password", toMd5(pass)));
                nvp.add(new BasicNameValuePair("password", "pass"));
                nvp.add(new BasicNameValuePair("convertir", "no"));
                /* Agrego los parámetros a la petición */
                post.setEntity(new UrlEncodedFormEntity(nvp));

                //post.setHeader("Content-Type", "application/json");
                //post.setHeader("Accept", "JSON");

                //post.setEntity(new ByteArrayEntity(jsonitems.toString().getBytes("UTF8")));
                /* Ejecuto la petición, y guardo la respuesta */
                HttpResponse respuesta = cliente.execute(post);

                ClientesDbHelper usdbh25 = new ClientesDbHelper(activity,"Items",null,1);
                SQLiteDatabase db5 = usdbh25.getWritableDatabase();

                try{
                    String result ="";
                    HttpEntity entity = respuesta.getEntity();
                    if (entity != null) {
                        InputStream instream = entity.getContent();
                        result = convertStreamToString(instream);


                        db5.execSQL("delete from Items");



                    }

                    //borramos los datos de clientes


                    //esto funciona cuando solo viene un array en el json de respuesta del servidor
                    /*JSONObject job = new JSONObject(result.toString());
                    String re = job.getString("status");*/
                    //esta parte fuiciona cuando vienen varios arrays en el json
                    JSONArray jsonarray = new JSONArray(result);
                    String re = "";
                    for(int ii=0; ii<jsonarray.length(); ii++){
                        JSONObject obj = jsonarray.getJSONObject(ii);
                        try {
                            Integer fsiditemcentral = Integer.parseInt(obj.getString("iditemcentral"));
                            String fsnombre = obj.getString("item");
                            String fscodigo = obj.getString("codigo");
                            Double fssaldoreal = Double.parseDouble(obj.getString("saldoreal"));
                            Double fssaldoestimado = Double.parseDouble(obj.getString("saldoestimado"));
                            Double fsprecioventa = Double.parseDouble(obj.getString("precioventa"));
                            Double fspreciocosto = Double.parseDouble(obj.getString("preciocosto"));
                            re = "success";

                            //sincronizamos hacia el movil

                            //Insertamos los datos en la tabla Usuarios

                            db5.execSQL("INSERT INTO Items (iditemcentral, nombre, codigo,saldoreal,saldoestimado,precioventa,preciocosto) " +
                                    "VALUES (" + fsiditemcentral + ", '" + fsnombre +"', '" + fscodigo + "',"+fssaldoreal+","+fssaldoestimado+","+fsprecioventa+","+fspreciocosto+")");






                        }catch (JSONException e2) {
                            re = "error";
                            // TODO Auto-generated catch block
                            e2.printStackTrace();
                        }


                    }
                    db5.close();
                    if(!re.isEmpty()) {
                        //la sincronizacion hacia el server ha sido exitosa, ahora sincronizamos hacia el movil




                        Toast.makeText(activity, "Sincronizacion Exitosa", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else{
                        Toast.makeText(activity, "Hubo un problema, intente de nuevo mas Tarde", Toast.LENGTH_SHORT).show();
                        return false;

                    }

                }catch(JSONException ex){
                    Log.w("Aviso", ex.toString());
                    return false;
                }

            }catch(ClientProtocolException ex){
                Log.w("ClientProtocolException", ex.toString());
                return false;
            }catch(UnsupportedEncodingException ex){
                Log.w("UnsuportEncodinExceptio", ex.toString());
                return false;
            }catch(IOException ex){
                Log.w("IOException", ex.toString());
                return false;
            }
            catch(IllegalStateException ex){
                Log.w("IOException", ex.toString());
                return false;
            }
       }


//sincroniza ventas begin
public static boolean sincronizaPedidos(Activity activity){
    final  String[] values2;
    PedidosAdapter pedidos;
    ArrayList<Pedido> arrayPedidos = new ArrayList<Pedido>();
    HashMap objMap = new HashMap();
    int i = 0;
    final JSONArray jsonpedidos = new JSONArray();

    PedidosDbHelper usdbhpedido = new PedidosDbHelper(activity,"Pedidos",null,1);
    SQLiteDatabase dbpedido = usdbhpedido.getWritableDatabase();
    PedidosDetailDbHelper usdbhpedidodetail = new PedidosDetailDbHelper(activity,"Pedidosdetail",null,1);
    SQLiteDatabase dbpedidodetail = usdbhpedidodetail.getWritableDatabase();

    Cursor cursorpedido = dbpedido.rawQuery("select _id, NombreCliente, NitCliente, idCliente, Fecha from Pedidos", null);
    while (cursorpedido.moveToNext()) {


        JSONObject jsonpedido = new JSONObject();
        try {
            jsonpedido.put("idpedido", cursorpedido.getInt(0));
            jsonpedido.put("nombrecliente", cursorpedido.getString(1));
            jsonpedido.put("nitcliente", cursorpedido.getString(2));
            jsonpedido.put("idcliente", cursorpedido.getInt(3));
            jsonpedido.put("fecha", cursorpedido.getString(4));
            //detalle begin
            Cursor cursorpedidodetail = dbpedidodetail.rawQuery("select idItem, codigoItem, nombreItem, precioUnitario, cantidad, total from Pedidosdetail where idPedido ="+cursorpedido.getInt(0), null);
            while (cursorpedidodetail.moveToNext()) {
                JSONObject jsonpedidofinal = new JSONObject();
                try {
                    jsonpedidofinal.put("idpedido", cursorpedido.getInt(0));
                    jsonpedidofinal.put("nombrecliente", cursorpedido.getString(1));
                    jsonpedidofinal.put("nitcliente", cursorpedido.getString(2));
                    jsonpedidofinal.put("idcliente", cursorpedido.getInt(3));
                    jsonpedidofinal.put("fecha", cursorpedido.getString(4));
                    jsonpedidofinal.put("idItem", cursorpedidodetail.getInt(0));
                    jsonpedidofinal.put("codigoItem", cursorpedidodetail.getString(1));
                    jsonpedidofinal.put("nombreItem", cursorpedidodetail.getString(2));
                    jsonpedidofinal.put("precioUnitario", cursorpedidodetail.getDouble(3));
                    jsonpedidofinal.put("cantidad", cursorpedidodetail.getDouble(4));
                    jsonpedidofinal.put("total", cursorpedidodetail.getDouble(5));
                    jsonpedidos.put(jsonpedidofinal);
                }catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
                //detalle end
            //jsoncliente.put("email", email);
            //jsoncliente.put("nit", nit);
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        //jsonpedidos.put(jsonpedido);
    }

dbpedido.close();
    dbpedidodetail.close();

    //final ArrayList<Cliente> arrayClientes = new ArrayList<Cliente>();

    // Do something in response to button click

       /* Comprobamos que no venga alguno en blanco. */
    // if (!arrayClientes.isEmpty()){
            /* Creamos el objeto cliente que realiza la petición al servidor */
    HttpClient cliente = new DefaultHttpClient();
            /* Definimos la ruta al servidor. En mi caso, es un servlet. */

    HttpPost post = new HttpPost("http://www.saeta-erp.com/webservice/sincronyzepedidotoserver.php");

    try{
                /* Defino los parámetros que enviaré. Primero el nombre del parámetro, seguido por el valor. Es lo mismo que hacer un
                 http://192.168.0.142:8080/marcoWeb/Login?username=mario&pass=maritoPass&convertir=no */
        List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);

                /* Encripto la contraseña en MD5. Definición más abajo */
        //nvp.add(new BasicNameValuePair("password", toMd5(pass)));
        nvp.add(new BasicNameValuePair("clientes", "arui"));
        nvp.add(new BasicNameValuePair("enviado", "si"));
                /* Agrego los parámetros a la petición */
        //post.setEntity(new UrlEncodedFormEntity(nvp));


        //post.setEntity(new ByteArrayEntity(jsonpedidos.toString().getBytes("UTF8")));
        post.setEntity(new ByteArrayEntity(jsonpedidos.toString().getBytes()));
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Accept", "JSON");
                /* Ejecuto la petición, y guardo la respuesta */
        HttpResponse respuesta = cliente.execute(post);






                SQLiteDatabase dbpedido2 = usdbhpedido.getWritableDatabase();

                SQLiteDatabase dbpedidodetail2 = usdbhpedidodetail.getWritableDatabase();

                dbpedido2.execSQL("delete from Pedidos");
                dbpedidodetail2.execSQL("delete from Pedidosdetail");






            //JSONArray jsonarray = new JSONArray(result);


                Toast.makeText(activity, "Sincronizacion Exitosa", Toast.LENGTH_SHORT).show();
                return true;






    }catch(ClientProtocolException ex){
        Log.w("ClientProtocolException", ex.toString());
        return false;
    }catch(UnsupportedEncodingException ex){
        Log.w("UnsuportEncodinExceptio", ex.toString());
        return false;
    }catch(IOException ex){
        Log.w("IOException", ex.toString());
        return false;
    }
    catch(IllegalStateException ex){
        Log.w("IOException", ex.toString());
        return false;
    }
        /*}else{
            //Toast.makeText(getActivity(), "Campo vac'io !",Toast.LENGTH_LONG).show();
            return false;
        }*/
}
    //sincroniza ventas end


    private static StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        //Guardamos la dirección en un buffer de lectura
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        //Y la leemos toda hasta el final
        try{
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        }catch(IOException ex){
            Log.w("Aviso", ex.toString());
        }

        // Devolvemos todo lo leido
        return total;
    }

    private static String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}