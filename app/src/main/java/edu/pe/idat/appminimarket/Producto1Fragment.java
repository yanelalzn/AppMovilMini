package edu.pe.idat.appminimarket;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.pe.idat.appminimarket.Adaptador.AdaptadorCategoria;
import edu.pe.idat.appminimarket.Adaptador.AdaptadorProducto;
import edu.pe.idat.appminimarket.Modelo.CategoriaCV;
import edu.pe.idat.appminimarket.Modelo.ProductoCV;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Producto1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Producto1Fragment extends Fragment {

    ArrayList<ProductoCV> ItemsDato;
    ProductoCV ItemDato;
    EditText idBuscar;
    RecyclerView recycler;
    Button idbotonbuscar;

    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;


    View v;
    List<ProductoCV> mList;
    AdaptadorProducto adapter;
    AdaptadorProducto adaptadorProducto;
    String nombre_producto;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Producto1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Producto1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Producto1Fragment newInstance(String param1, String param2) {
        Producto1Fragment fragment = new Producto1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_producto1, container, false);
        // ocultar titulo y activar recycler
        recycler = v.findViewById(R.id.rviewProducto);
        idBuscar = v.findViewById(R.id.idBuscar);
        idbotonbuscar = v.findViewById(R.id.idbotonbuscar);
        recycler.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // Crear array de items categoria
        ItemsDato = new ArrayList<ProductoCV>();

        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(getActivity());

        CargarListaProductoVolley();


        // CargarListaCategoria();

        idbotonbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuscarListaProductoVolley(idBuscar.getText().toString());
            }
        });


        return v;
    }



    private void BuscarListaProductoVolley(String s) {

        String valueParameter = s;
        if(valueParameter.equals(""))
        {
            valueParameter = "todos";
        }
        String url = "http://192.168.0.18:8030/project/rest/producto/buscaNombre/"+valueParameter;

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
                            ItemsDato = new ArrayList<ProductoCV>();
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject objProducto = response.getJSONObject(i);
                                ItemDato = new ProductoCV();
                                ItemDato.setId_producto(objProducto.getInt("id_producto"));
                                ItemDato.setDescripcion(objProducto.getString("descripcion"));
                                ItemDato.setPrecio(objProducto.getDouble("precio"));
                                ItemDato.setImagenproducto(objProducto.getString("imagen"));
                                ItemsDato.add(ItemDato);

                            }

                            AdaptadorProducto adapter = new AdaptadorProducto(ItemsDato);
                            recycler.setAdapter(adapter);


                        } catch (Exception ex) {
                            Log.e("Error al ejecutar", ex.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Información que caso que ecista error de conexión
                        Log.e("Error  al conectar", error.getMessage());

                    }
                }

        );


        colaPeticiones.add(requerimiento);

    }


            private void CargarListaProductoVolley() {

                String url = "http://192.168.0.18:8030/project/rest/producto/listar";

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
                                    ItemsDato = new ArrayList<ProductoCV>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject objProducto = response.getJSONObject(i);
                                        ItemDato = new ProductoCV();
                                        ItemDato.setId_producto(objProducto.getInt("id_producto"));
                                        ItemDato.setDescripcion(objProducto.getString("descripcion"));
                                        ItemDato.setPrecio(objProducto.getDouble("precio"));
                                        ItemDato.setImagenproducto(objProducto.getString("imagen"));
                                        ItemsDato.add(ItemDato);

                                    }

                                    AdaptadorProducto adapter = new AdaptadorProducto(ItemsDato);
                                    recycler.setAdapter(adapter);


                                } catch (Exception ex) {
                                    Log.e("Error al ejecutar", ex.getMessage());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Información que caso que ecista error de conexión
                                Log.e("Error  al conectar", error.getMessage());

                            }
                        }

                );


                colaPeticiones.add(requerimiento);

            }
        }