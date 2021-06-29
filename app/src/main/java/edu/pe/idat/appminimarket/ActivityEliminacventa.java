package edu.pe.idat.appminimarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import edu.pe.idat.appminimarket.Modelo.CarritoDetalle;

public class ActivityEliminacventa extends AppCompatActivity {
    // Definir variables que se envia por parametro
    String codigo_carritoventa;
    String codigo_carritoventadetalle;
    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminacventa);

        codigo_carritoventa = getIntent().getStringExtra("codigo_carritoventa");
        codigo_carritoventadetalle = getIntent().getStringExtra("codigo_carritoventadetalle");
        colaPeticiones = Volley.newRequestQueue(this);

        // Guardar Información
        CarritoDetalle ItemDato = new CarritoDetalle();

        // Cabezera venta

        ItemDato.setId_carritoven(Integer.parseInt(codigo_carritoventa));

        ItemDato.setId_carritovendeta(Integer.parseInt (codigo_carritoventadetalle));

        eliminarDetalleCompraVolley(ItemDato);

        // Cerrar Ventana
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

    private void eliminarDetalleCompraVolley(CarritoDetalle itemDato) {


        String url = "http://192.168.0.18:8030/project/rest/carritodetalle/borrar/"+itemDato.getId_carritoven()+"/"+itemDato.getId_carritovendeta();

        //** creo el objeto de requerimiento API
        JsonObjectRequest requerimiento = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //** codificar el resultado de la respuesta.
                        try {

                        }catch (Exception ex)
                        {
                            Log.e("Error al ejecutar",ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Información que caso que ecista error de conexión
                        Log.e("Error  al conectar",error.getMessage());



                    }
                }

        );

        // Adicionar e- requerimiento a la cola
        colaPeticiones.add(requerimiento);



    }
}