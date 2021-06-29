package edu.pe.idat.appminimarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import edu.pe.idat.appminimarket.Modelo.TipoDocumento;

public class RegistrarActivity extends AppCompatActivity {
    private TextView textlogin;
    private TextView nombrereg, apellidosreg, usuarioreg, passreg, numDoc, mensajeNew;
    private Button btnRegistrar;
    private Spinner TipoDocreg;

    private RequestQueue colaPeticiones;

    ArrayList<TipoDocumento> ItemsDato;
    TipoDocumento ItemDato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        nombrereg = findViewById(R.id.nombrereg);
        apellidosreg = findViewById(R.id.apellidosreg);
        usuarioreg = findViewById(R.id.usuarioreg);
        passreg = findViewById(R.id.passreg);
        numDoc = findViewById(R.id.numDoc);
        mensajeNew = findViewById(R.id.mensajeNew);

        TipoDocreg = findViewById(R.id.TipoDocreg);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        mensajeNew.setText("");
        btnRegistrar = findViewById(R.id.btnRegistrar);
        textlogin = findViewById(R.id.btnlogin);

        ItemsDato = new ArrayList<>();


        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(this);
        CargarListaTipoDocumentoVolley();

        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nombrereg.getText().toString().equals("")) {
                    validacion();
                } else {
                    if (apellidosreg.getText().toString().equals("")) {
                        validacion();
                    } else{
                        if(numDoc.getText().toString().equals("")){
                            validacion();
                        }else{
                            if(usuarioreg.getText().toString().equals("")){
                                validacion();
                            }else{
                                if(passreg.getText().toString().equals("")){
                                    validacion();
                                }else{

                                    RegistrarDatosClientesVolley();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                }
                            }
                        }
                    }

                }
            }
        });




    }

    private void CargarListaTipoDocumentoVolley() {

        String url = "http://192.168.0.18:8030/project/rest/tipoDoc/listar";

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


                            }


                            TipoDocreg.setAdapter(new ArrayAdapter<TipoDocumento>(RegistrarActivity.this,
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

    private void RegistrarDatosClientesVolley() {


        String url = "http://192.168.0.18:8030/project/rest/cliente/agregar";

        ClienteRegistrar clienteDb = new ClienteRegistrar();
        TipoDocumento tipodocumentoDb = new TipoDocumento();

        tipodocumentoDb.setId_tipo_doc(3);

        clienteDb.setNombre(nombrereg.getText().toString());
        clienteDb.setApellidos(apellidosreg.getText().toString());

        clienteDb.setTipodoc(tipodocumentoDb);
        clienteDb.setNum_doc(numDoc.getText().toString());


        clienteDb.setUsuario(usuarioreg.getText().toString());
        clienteDb.setPassword(passreg.getText().toString());

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




        System.out.println(String.valueOf(parametroJson));




        //** creo el objeto de requerimiento API
        JsonObjectRequest requerimiento = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parametroJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        //** codificar el resultado de la respuesta.
                        try {
                            mensajeNew.setText("registro correcto");
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
                        mensajeNew.setText(error.toString());
                        Log.e("Error  al conectar",error.getMessage().toString());

                    }
                }

        );

        // Adicionar e- requerimiento a la cola
        colaPeticiones.add(requerimiento);





    }


    public void guardarNew(View view) throws JSONException {
        //Que debe realizar cuando presionen guardar
        String url = "http://192.168.0.18:8030/project/rest/cliente/agregar";

        ClienteRegistrar clienteDb = new ClienteRegistrar();
        TipoDocumento tipodocumentoDb = new TipoDocumento();


        tipodocumentoDb.setId_tipo_doc(3);


        // validar los datos de ingreso
        mensajeNew.setText("");
        if (nombrereg.getText().toString().equals(""))
        {
            validacion();
        }else {
            if (apellidosreg.getText().toString().equals("")) {
                validacion();
            } else {
                if (usuarioreg.getText().toString().equals("")) {
                    validacion();
                } else {

                    if (passreg.getText().toString().equals("")) {
                        validacion();
                    }else
                    {

                        clienteDb.setNombre(nombrereg.getText().toString());
                        clienteDb.setApellidos(apellidosreg.getText().toString());
                        clienteDb.setNum_doc("");
                        clienteDb.setUsuario(usuarioreg.getText().toString());
                        clienteDb.setPassword(passreg.getText().toString());
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
                                Request.Method.POST,
                                url,
                                parametroJson,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {


                                        //** codificar el resultado de la respuesta.
                                        try {
                                            mensajeNew.setText("registro correcto");
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
                                        mensajeNew.setText(error.toString());
                                        Log.e("Error  al conectar",error.getMessage().toString());

                                    }
                                }

                        );

                        // Adicionar e- requerimiento a la cola
                        colaPeticiones.add(requerimiento);
                    }
                }
            }
        }
    }






    private void validacion() {

        if (nombrereg.getText().toString().equals("")) {
            nombrereg.setError("Requerido");
        } else if (apellidosreg.getText().toString().equals("")) {
            apellidosreg.setError("Requerido");

        } else if (numDoc.getText().toString().equals("")) {
            numDoc.setError("Requerido");

        } else if (usuarioreg.getText().toString().equals("")) {
            usuarioreg.setError("Requerido");

        } else if (passreg.getText().toString().equals("")) {
            passreg.setError("Requerido");

        }
    }

    private void limpiarcajas() {
        nombrereg.setText("");
        apellidosreg.setText("");
        numDoc.setText("");
        usuarioreg.setText("");
        passreg.setText("");
    }



}