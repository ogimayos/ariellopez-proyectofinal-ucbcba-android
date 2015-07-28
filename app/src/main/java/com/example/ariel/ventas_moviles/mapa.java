package com.example.ariel.ventas_moviles;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;


public class mapa extends ActionBarActivity {
    private GoogleMap mapa = null;
    private int vista = 0;


    //variables para el listener y la geoposicion
    private LocationManager locManager;
    private LocationListener locListener;
    private double latitude;
    private double longitude;
    //------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);



        //LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        LocationManager locManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> listProviders = locManager.getAllProviders();





//para mostrar las caracteristicas de un proveedor
        LocationProvider provider = locManager.getProvider(listProviders.get(2));
        int accuracy = provider.getAccuracy();
        boolean altitudeAvailable = provider.supportsAltitude();
        int powerRequirement = provider.getPowerRequirement();

        // Comprobamos si est� disponible el proveedor GPS.
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            Log.w("Consulta", "no esta habilitado el GPS");
//mostrarAvisoGpsDeshabilitado();
        }// end if.


        Log.w("Consulta", provider.toString());


       mapa = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        mapa.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition position) {
                Toast.makeText(
                        mapa.this,
                        "Cambio Camara\n" +
                                "Lat: " + position.target.latitude + "\n" +
                                "Lng: " + position.target.longitude + "\n" +
                                "Zoom: " + position.zoom + "\n" +
                                "Orientacion: " + position.bearing + "\n" +
                                "angulo: " + position.tilt,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                Projection proj = mapa.getProjection();
                Point coord = proj.toScreenLocation(point);

                Toast.makeText(
                        mapa.this,
                        "Click\n" +
                                "Lat: " + point.latitude + "\n" +
                                "Lng: " + point.longitude + "\n" +
                                "X: " + coord.x + " - Y: " + coord.y,
                        Toast.LENGTH_SHORT).show();
            }
        });

        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            public void onMapLongClick(LatLng point) {
                Projection proj = mapa.getProjection();
                Point coord = proj.toScreenLocation(point);

                Toast.makeText(
                        mapa.this,
                        "Click Largo\n" +
                                "Lat: " + point.latitude + "\n" +
                                "Lng: " + point.longitude + "\n" +
                                "X: " + coord.x + " - Y: " + coord.y,
                        Toast.LENGTH_SHORT).show();
            }
        });


        mapa.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(
                        mapa.this,
                        "Marcador pulsado:\n" +
                                marker.getTitle(),
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }

    private void mostrarMarcador(double lat, double lng)
    {
        mapa.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("Pais: Espa�a"));
    }



    private void launchLocator()
    {
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locListener = new LocationListener()
        {

            @Override
            public void onLocationChanged(Location location)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            }

            @Override
            public void onProviderDisabled(String provider)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras)
            {

            }

        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);

    }// end launchLocator.





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId())
        {
            case R.id.menu_vista:
                alternarVista();
                break;
            case R.id.menu_mover:
                //Centramos el mapa en Espa�a
                CameraUpdate camUpd1 =
                        CameraUpdateFactory.newLatLng(new LatLng(40.41, -3.69));
                mapa.moveCamera(camUpd1);
                break;
            case R.id.menu_animar:
                //Centramos el mapa en Espa�a y con nivel de zoom 5
                CameraUpdate camUpd2 =
                        CameraUpdateFactory.newLatLngZoom(new LatLng(40.41, -3.69), 5F);
                mapa.animateCamera(camUpd2);
                break;
            case R.id.menu_3d:
                LatLng madrid = new LatLng(40.417325, -3.683081);
                CameraPosition camPos = new CameraPosition.Builder()
                        .target(madrid)   //Centramos el mapa en Madrid
                        .zoom(19)         //Establecemos el zoom en 19
                        .bearing(45)      //Establecemos la orientaci�n con el noreste arriba
                        .tilt(70)         //Bajamos el punto de vista de la c�mara 70 grados
                        .build();

                CameraUpdate camUpd3 =
                        CameraUpdateFactory.newCameraPosition(camPos);

                mapa.animateCamera(camUpd3);
                break;
            case R.id.menu_posicion:
                CameraPosition camPos2 = mapa.getCameraPosition();
                LatLng pos = camPos2.target;
                Toast.makeText(mapa.this,
                        "Lat: " + pos.latitude + " - Lng: " + pos.longitude,
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.menu_marcadores:
                launchLocator();

                //mostrarMarcador(40.5, -3.5);
                mostrarMarcador(latitude, longitude);
                break;
            case R.id.menu_lineas:
                //mostrarLineas();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    private void alternarVista()
    {

      vista = (vista + 1) % 4;

        switch(vista)
        {
            case 0:
                mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 2:
                mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 3:
                mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }


}
