package edu.pe.idat.appminimarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.pe.idat.appminimarket.Modelo.ClienteRegistrar;
import edu.pe.idat.appminimarket.Modelo.ProductoCV;
import edu.pe.idat.appminimarket.Modelo.TipoDocumento;
import edu.pe.idat.appminimarket.Modelo.VariableGlobal;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {

    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;

    View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    VariableGlobal mApp;
    private EditText codigoclienteEdi, nombreEdi, apellidoEdi, nrodocumentoEdi, direccionEdi, usuarioEdi, passwordEdi;
    private Spinner tipodocNew;
    private TextView mensajeLogin, tituloLogin;

    private Button btnregistrarEdi, btncancelarEdi;

    ArrayList<TipoDocumento> ItemsDato;
    TipoDocumento ItemDato;


    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
        v=inflater.inflate(R.layout.fragment_perfil, container, false);
        // direcciona variables trabajo del frame
        codigoclienteEdi = v.findViewById(R.id.codigoclienteEdi);
        nombreEdi = v.findViewById(R.id.nombreperfil);
        apellidoEdi = v.findViewById(R.id.apellidoperfil);
        tipodocNew = v.findViewById(R.id.TipoDocPerfil);
        nrodocumentoEdi = v.findViewById(R.id.numDocPerfil);
        direccionEdi = v.findViewById(R.id.direccionPerfil);
        usuarioEdi = v.findViewById(R.id.usuarioperfil);
        passwordEdi = v.findViewById(R.id.passperfil);
        mensajeLogin = v.findViewById(R.id.mensajeEdi);
        tituloLogin = v.findViewById(R.id.tituloUsuarioEdi);
        btnregistrarEdi  = v.findViewById(R.id.btnRegistrarPerfil);

        mensajeLogin.setText("");

        ItemsDato = new ArrayList<>();

        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(getActivity());


        CargarListaTipoDocumento();

        // Definir para leer variable global desde frament
        //mApp = ((VariableGlobal)getApplication());
        VariableGlobal mApp = ((VariableGlobal)getActivity().getApplicationContext());


        if (mApp.getUsuario().isEmpty())
        {
            tituloLogin.setText("Registrar Usuario");
        }
        else
        {
            tituloLogin.setText("Actualizar Usuario");
            CargarDatosCliente();
            //--usuarioNew.setText(mApp.getUsuario());
        }


        // Click en registrar
        btnregistrarEdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // validar los datos de ingreso
                mensajeLogin.setText("");
                if (nombreEdi.getText().toString().equals(""))
                {
                    mensajeLogin.setText("ingresar el nombre");
                }else {
                    if (apellidoEdi.getText().toString().equals("")) {
                        mensajeLogin.setText("ingresar el apellido");
                    } else {
                        if (nrodocumentoEdi.getText().toString().equals("")) {
                            mensajeLogin.setText("ingresar numero documento");
                        }else {
                            if (direccionEdi.getText().toString().equals("")) {
                                mensajeLogin.setText("ingresar dirección");
                            }else
                            {
                                if (usuarioEdi.getText().toString().equals("")) {
                                        mensajeLogin.setText("ingresar usuario");
                                    } else {

                                        if (passwordEdi.getText().toString().equals("")) {
                                            mensajeLogin.setText("ingresar contraseña");
                                        } else {

                                            ActualizarDatosClientes();

                                            Intent i = new Intent(getContext(), MainActivity.class);
                                            startActivity(i);

                                        }
                                    }
                                }
                            }
                        }
                    }
                }

        });


        return v;
    }

    private void ActualizarDatosClientes() {
        //Que debe realizar cuando presionen guardar
        String url = "http://192.168.0.18:8030/project/rest/cliente/editar/"+codigoclienteEdi.getText().toString();


        ClienteRegistrar clienteDb = new ClienteRegistrar();
        TipoDocumento tipodocumentoDb = new TipoDocumento();

        // debe tomar el dato del sppiner  aujn bsncado en youtube
        tipodocumentoDb.setId_tipo_doc(1);





        clienteDb.setNombre(nombreEdi.getText().toString());
        clienteDb.setApellidos(apellidoEdi.getText().toString());
        clienteDb.setTelefono("");
        clienteDb.setDireccion(direccionEdi.getText().toString());
        clienteDb.setNum_doc(nrodocumentoEdi.getText().toString());
        //clienteDb.setUsuario(usuarioEdi.getText().toString());
        clienteDb.setPassword(passwordEdi.getText().toString());

        clienteDb.setTipodoc(tipodocumentoDb);



        // Se convierte el objeto en JSON con libreria GSON
        Gson gson = new Gson();
        String JSON = gson.toJson(clienteDb);

        // mensajeLogin.setText(JSON);
        //** se conviert la variable MAP en un objecto JSON
        JSONObject parametroJson = null;
        try {
            parametroJson = new JSONObject(JSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // mensajeLogin.setText( parametroJson.getString("nombre"));


        //** creo el objeto de requerimiento API
        JsonObjectRequest requerimiento = new JsonObjectRequest(
                Request.Method.PUT  ,
                url   ,
                parametroJson  ,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        //** codificar el resultado de la respuesta.
                        try {
                            mensajeLogin.setText("registro correcto");

                            //Terminar y regresar a la ventana de gestion

                        } catch (Exception ex) {
                            Log.e("Error al ejecutar", ex.getMessage());
                        }
                    }
                }  ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Información que caso que ecista error de conexión
                        mensajeLogin.setText(error.toString());
                        Log.e("Error  al conectar", error.getMessage().toString());

                    }
                }

        );

        // Adicionar e- requerimiento a la cola
        colaPeticiones.add(requerimiento);

    }


    private void CargarListaTipoDocumento() {
        String url = "http://192.168.0.18:8030/project/rest/tipoDoc/listar";


        // ItemsDato.clear();
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
                                JSONObject objDocumento = response.getJSONObject(i);

                                ItemDato = new TipoDocumento();

                                ItemDato.setId_tipo_doc(objDocumento.getInt("id_tipo_doc"));
                                ItemDato.setDescripcion(objDocumento.getString("descripcion"));

                                ItemsDato.add(ItemDato);

                                /*
                                ItemsDato.add(new TipoDocumento(
                                        objDocumento.getInt("id_tipo_doc"),
                                        objDocumento.getString("descripcion")
                                ));
                                */

                            }

                            // Cargar tipo de documento al spinner
                            tipodocNew.setAdapter(new ArrayAdapter<TipoDocumento>(getActivity(),
                                    android.R.layout.simple_spinner_dropdown_item, ItemsDato));

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

    private void CargarDatosCliente() {

        VariableGlobal LmApp = ((VariableGlobal)getActivity().getApplicationContext());
        //** declarar variable de URL de Api
        // String url = "http://172.96.143.27/sistemacitas/validar_login.php";
        //String url = "http://172.96.143.27:8090/project/rest/cliente/buscar/"+mApp.getId_cliente();

        String url = "http://192.168.0.18:8030/project/rest/cliente/buscar/"+String.valueOf(LmApp.getId_cliente());



        // ItemsDato.clear();
        //** creo el objeto de requerimiento API
        // la solicitud debe ser un JsonRequest y JSONObject ya que retorna un solo registro
        //** creo el objeto de requerimiento API

        JsonObjectRequest requerimiento = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //** codificar el resultado de la respuesta.
                        try {

                            //Actualizo dato de la pantalla

                            codigoclienteEdi.setText(response.getString("id_cliente"));
                            nombreEdi.setText(response.getString("nombre"));
                            apellidoEdi.setText(response.getString("apellidos"));
                            nrodocumentoEdi.setText(response.getString("num_doc"));
                            direccionEdi.setText(response.getString("direccion"));
                            usuarioEdi.setText(response.getString("usuario"));
                            passwordEdi.setText(response.getString("password"));


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