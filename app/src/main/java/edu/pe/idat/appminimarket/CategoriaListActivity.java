package edu.pe.idat.appminimarket;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.pe.idat.appminimarket.Adaptador.AdaptadorProducto;
import edu.pe.idat.appminimarket.Modelo.ProductoCV;

public class CategoriaListActivity extends AppCompatActivity {

    ArrayList<ProductoCV> ItemsDato;
    ProductoCV ItemDato;
    RecyclerView rvProductoList;
    // Definir variables que se envia por parametro
    String codigo_categoria;
    String titulo_categoria;

    TextView titulo_activity;
    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_list);

        // Obtener los datos de la categoria seleccionada

        codigo_categoria = getIntent().getStringExtra("codigo_categoria");
        titulo_categoria = getIntent().getStringExtra("dato_titulo");

        // Inicializar variables
        titulo_activity = findViewById(R.id.tituloproductoLis);
        titulo_activity.setText(titulo_categoria);

        //Amarrar el reclicer del carteview
        rvProductoList = findViewById(R.id.rvProductoList);
        rvProductoList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Crear array de items categoria
        ItemsDato = new ArrayList<ProductoCV>();

        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(this);
        // CargarListaCategoria();
        CargarListaProductoVolley();




    }

    private void CargarListaProductoVolley() {

        String url = "http://192.168.0.18:8030/project/rest/producto/buscarCategoria/"+codigo_categoria;

        //** creo el objeto de requerimiento API
        // la solicitud debe ser un JsonArrayRequest y JSONArray ya que retorna un listado de registros
        JsonArrayRequest requerimiento = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //** codificar el resultado de la respuesta.
                        try {

                            // Leer Registro por Registro

                            for (int i = 0; i < response.length();i++)
                            {
                                JSONObject objProducto = response.getJSONObject(i);
                                ItemDato = new ProductoCV();

                                ItemDato.setId_categoria(objProducto.getInt("id_categoria"));

                                ItemDato.setId_producto(objProducto.getInt("id_producto"));
                                ItemDato.setDescripcion(objProducto.getString("descripcion"));

                                ItemDato.setPrecio(objProducto.getDouble("precio"));
                                ItemDato.setImagenproducto(objProducto.getString("imagen"));

                                ItemsDato.add(ItemDato);


                            }

                            AdaptadorProducto adapter = new AdaptadorProducto(ItemsDato);
                            rvProductoList.setAdapter(adapter);

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


        colaPeticiones.add(requerimiento);
    }
}