package edu.pe.idat.appminimarket;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.pe.idat.appminimarket.Adaptador.AdaptadorCategoria;
import edu.pe.idat.appminimarket.Modelo.CategoriaCV;

public class CategoriaFragment extends Fragment {

    ArrayList<CategoriaCV> ItemsDato;
    CategoriaCV ItemDato;
    RecyclerView recycler;
    FloatingActionButton fab5;


    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;


    View v;
    List<CategoriaCV> mList;
    AdaptadorCategoria adaptadorCategoria;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public CategoriaFragment() {
        // Required empty public constructor
    }

    public static CategoriaFragment newInstance(String param1, String param2) {
        CategoriaFragment fragment = new CategoriaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v =inflater.inflate(R.layout.fragment_categoria, container, false);

        // ocultar titulo y activar recycler

        recycler = v.findViewById(R.id.rviewCategoria);
        fab5 = v.findViewById(R.id.fab5);

        recycler.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        // Crear array de items categoria
        ItemsDato = new ArrayList<CategoriaCV>();

        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(getActivity());

        //CargarListaCategoria();
        CargarListaCategoriaVolley();

/*
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ActivityDetallecventa.class);
                startActivity(i);

            }
        });*/

        return v;


    }

    private void CargarListaCategoriaVolley() {

        String url = "http://192.168.0.18:8030/project/rest/categoria/listar";

        //** creo el objeto de requerimiento API
        // la solicitud debe ser un JsonArrayRequest y JSONArray ya que retorna un listado de registros
        JsonArrayRequest requerimiento = new JsonArrayRequest(
                Request.Method.GET  ,
                url  ,
                null  ,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //** codificar el resultado de la respuesta.
                        try {


                            // Leer Registro por Registro

                            for (int i = 0; i < response.length();i++)
                            {
                                JSONObject objCategoria = response.getJSONObject(i);
                                ItemDato = new CategoriaCV();

                                ItemDato.setDescripcion(objCategoria.getString("descripcion"));
                                ItemDato.setId_categoria(objCategoria.getInt("id_categoria"));
                                ItemDato.setImagenproducto(objCategoria.getString("imagen"));
                                ItemsDato.add(ItemDato);



                            }

                            AdaptadorCategoria adapter = new AdaptadorCategoria(ItemsDato);
                            recycler.setAdapter(adapter);


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



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


}