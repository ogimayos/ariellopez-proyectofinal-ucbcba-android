package com.example.ariel.ventas_moviles.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ariel on 20/07/2015.
 */
public class PedidosDetailDbHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Clientes
    //saldo real es el saldo del item al momento de la sincronizacion
    //saldo estimado es el saldo del item que se estima se tendra hasta el momento de concretar la venta o preparar el pedido que sera en los dias posteriores
    //id item central es el id del item en la base de datos central
    String sqlCreate = "CREATE TABLE Pedidosdetail " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " idPedido INTEGER, " +
            " idItem INTEGER, " +
            " codigoItem TEXT, " +
            " nombreItem TEXT, " +
            " precioUnitario REAL, " +
            " cantidad REAL, " +
            " total REAL )";

    public PedidosDetailDbHelper(Context contexto, String nombre,
                           SQLiteDatabase.CursorFactory factory, int version) {

        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(sqlCreate);

        //Insertamos 15 clientes de ejemplo
        for(int i=1; i<=1; i++)
        {
            //Generamos los datos de muestra
            Integer idPedido = 1;
            Integer idItem = i;
            String codigoItem = "codigo-prueba-";
            String nombreItem = "Nombre PRUEBA-";
            Double precioUnitario = 10.00;
            Double cantidad = 8.00;
            Double total = 80.00;

            //Insertamos los datos en la tabla Clientes

            db.execSQL("INSERT INTO Pedidosdetail (idPedido, idItem, codigoItem,nombreItem,precioUnitario,cantidad,total) " +
                    "VALUES (" + idPedido + ", " + idItem +", '"+codigoItem+"','"+nombreItem+"',"+precioUnitario+","+cantidad+","+total+")");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aqu� utilizamos directamente la opci�n de
        //      eliminar la tabla anterior y crearla de nuevo vac�a con el nuevo formato.
        //      Sin embargo lo normal ser� que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este m�todo deber�a ser m�s elaborado.

        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Pedidosdetail");

        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }
}

