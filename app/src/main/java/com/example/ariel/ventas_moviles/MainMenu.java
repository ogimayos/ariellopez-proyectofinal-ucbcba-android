package com.example.ariel.ventas_moviles;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.print.pdf.PrintedPdfDocument;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;


public class MainMenu extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        switch (item.getItemId()) {

            //Boton de cerrar sesión
            case R.id.closessesion:
                //Borramos el usuario almacenado en preferencias y volvemos a la pantalla de login
                SharedPreferences settings = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", "");
                editor.putString("pass", "");

                //Confirmamos el almacenamiento.
                editor.commit();

                //Volvemos a la pantalla de Login
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }



        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);




            final Button clientes = (Button) rootView.findViewById(R.id.btnClientes);
            clientes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click

                        Intent intenClientes = new Intent(getActivity(), ListClientes.class);

                        startActivity(intenClientes);


                }
            });

            final Button items = (Button) rootView.findViewById(R.id.btnitems);
            items.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click

                    //Intent ventaPedido = new Intent(getActivity(), ventaPedidos.class);

                    //startActivity(ventaPedido);
                    Intent listaItems = new Intent(getActivity(), ListItems.class);
                    startActivity(listaItems);

                }
            });

            final Button ventas = (Button) rootView.findViewById(R.id.btnVentas);
            ventas.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent listaVentas = new Intent(getActivity(), ListVentaPedidos.class);
                    startActivity(listaVentas);
/*
                    // Create a shiny new (but blank) PDF document in memory
                    PdfDocument document = new PdfDocument();

                    // crate a page description
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 300, 1).create();

                    // create a new page from the PageInfo
                    PdfDocument.Page page = document.startPage(pageInfo);

                    // repaint the user's text into the page
                    View content = rootView.findViewById(R.id.textsample);
                    content.draw(page.getCanvas());

                    // do final processing of the page
                    document.finishPage(page);
*/

                }
            });

            //Intent mainmenu = new Intent(getActivity(), MainMenu.class);

            //startActivity(mainmenu);
            return rootView;
        }
        private void drawPage(PdfDocument.Page page) {
            Canvas canvas = page.getCanvas();

            // units are in points (1/72 of an inch)
            int titleBaseLine = 72;
            int leftMargin = 54;

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(36);
            canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);

            paint.setTextSize(11);
            canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25, paint);

            paint.setColor(Color.BLUE);
            canvas.drawRect(100, 100, 172, 172, paint);
        }
    }
}
