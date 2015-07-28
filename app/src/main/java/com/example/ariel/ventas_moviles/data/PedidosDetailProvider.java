package com.example.ariel.ventas_moviles.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.SQLException;

/**
 * Created by ariel on 20/07/2015.
 */
public class PedidosDetailProvider extends ContentProvider {

    //Definición del CONTENT_URI
    private static final String uri = "content://com.example.ariel.ventas_moviles/pedidosdetail";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    @Override
    public boolean onCreate() {
        peddbh = new PedidosDetailDbHelper(
                getContext(), BD_NOMBRE, null, BD_VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == PEDIDOSDETAIL_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = peddbh.getWritableDatabase();

        Cursor c = db.query(TABLA_PEDIDOSDETAIL, projection, where,
                selectionArgs, null, null, sortOrder);

        return c;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case PEDIDOSDETAIL:
                return "vnd.android.cursor.dir/vnd.example.pedidodetail";
            case PEDIDOSDETAIL_ID:
                return "vnd.android.cursor.item/vnd.example.pedidodetail";
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //long regId = 1;
        Uri newUri = null;


        int match = uriMatcher.match(uri);

        SQLiteDatabase db = peddbh.getWritableDatabase();
        if (match == PEDIDOSDETAIL) {

            long regId = db.insert(TABLA_PEDIDOSDETAIL, null, values);
            //newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
            if (regId > 0) {
                newUri = ContentUris.withAppendedId(CONTENT_URI, regId);
            }


            else {
                try {
                    throw new SQLException("Failed to insert row into " + uri);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return newUri;
    }


    public int delete2(Uri uri, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == PEDIDOSDETAIL_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = peddbh.getWritableDatabase();

        //cont = db.delete(TABLA_CLIENTES, where, selectionArgs);
        cont = db.delete(TABLA_PEDIDOSDETAIL,"",null);


        return cont;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;
        int match = uriMatcher.match(uri);
        SQLiteDatabase db = peddbh.getWritableDatabase();
        int deleted;


        if (selection == null)
            selection = "1";





        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == PEDIDOSDETAIL_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        //SQLiteDatabase db = clidbh.getWritableDatabase();

        //cont = db.delete(TABLA_CLIENTES, where, selectionArgs);
        if (match == PEDIDOSDETAIL) {
            //deleted = db.delete(TABLA_CLIENTES, selection, selectionArgs);
            deleted = db.delete(TABLA_PEDIDOSDETAIL, "", null);
        } else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //cont = db.delete(TABLA_CLIENTES,"",null);

        if (deleted != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;
        int match = uriMatcher.match(uri);



        if (selection == null)
            selection = "1";

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == PEDIDOSDETAIL_ID){
            where = "_id=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = peddbh.getWritableDatabase();
        if (match == PEDIDOSDETAIL) {

            cont = db.update(TABLA_PEDIDOSDETAIL, values, where, selectionArgs);
        }
        else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (cont != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return cont;
    }


    //Clase interna para declarar las constantes de columna
    public static final class PedidosDetail implements BaseColumns {
        private PedidosDetail() {
        }

        //Nombres de columnas
        public static final String COL_IDPEDIDO = "idPedido";
        public static final String COL_IDITEM = "idItem";
        public static final String COL_CODIGOITEM = "codigoItem";
        public static final String COL_NOMBREITEM = "nombreItem";
        public static final String COL_PRECIOUNITARIO = "precioUnitario";
        public static final String COL_CANTIDAD = "cantidad";
        public static final String COL_TOTAL = "total";

    }
    //Base de datos
    private PedidosDetailDbHelper peddbh;
    private static final String BD_NOMBRE = "DBVentas";
    private static final int BD_VERSION = 1;
    private static final String TABLA_PEDIDOSDETAIL = "Pedidosdetail";



    //UriMatcher
    private static final int PEDIDOSDETAIL = 1;
    private static final int PEDIDOSDETAIL_ID = 2;
    private static final UriMatcher uriMatcher;

    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.ariel.ventas_moviles", "pedidosdetail", PEDIDOSDETAIL);
        uriMatcher.addURI("com.example.ariel.ventas_moviles", "pedidosdetail/#", PEDIDOSDETAIL_ID);
    }
    @Override
    public void shutdown() {
        peddbh.close();
        super.shutdown();
    }


}

