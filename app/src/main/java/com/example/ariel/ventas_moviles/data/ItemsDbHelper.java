package com.example.ariel.ventas_moviles.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ariel on 15/07/2015.
 */

public class ItemsDbHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Clientes
    //saldo real es el saldo del item al momento de la sincronizacion
    //saldo estimado es el saldo del item que se estima se tendra hasta el momento de concretar la venta o preparar el pedido que sera en los dias posteriores
    //id item central es el id del item en la base de datos central
    String sqlCreate = "CREATE TABLE Items " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " iditemcentral INTEGER, " +
            " nombre TEXT, " +
            " codigo TEXT, " +
            " saldoreal REAL, " +
            " saldoestimado REAL, " +
            " precioventa REAL, " +
            " preciocosto REAL )";

    public ItemsDbHelper(Context contexto, String nombre,
                            SQLiteDatabase.CursorFactory factory, int version) {

        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);

        //Insertamos 15 clientes de ejemplo
        for(int i=1; i<=1; i++)
        {
            //Generamos los datos de muestra
            Integer iditemcentral = 100+i;
            String nombre = "itemPrueba" + i;
            String codigo = "codigo-Prueba" + i;
            Double saldoreal = 100.00;
            Double saldoestimado = 100.00;
            Double precioventa = 10.00;
            Double preciocosto = 8.00;

            //Insertamos los datos en la tabla Clientes

            db.execSQL("INSERT INTO Items (iditemcentral, nombre, codigo,saldoreal,saldoestimado,precioventa,preciocosto) " +
                    "VALUES (" + iditemcentral + ", '" + nombre +"', '" + codigo + "',"+saldoreal+","+saldoestimado+","+precioventa+","+preciocosto+")");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.

        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Items");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}

