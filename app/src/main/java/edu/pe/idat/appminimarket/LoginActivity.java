package edu.pe.idat.appminimarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.pe.idat.appminimarket.Modelo.VariableGlobal;

public class LoginActivity extends AppCompatActivity {

    private EditText usuarioLogin;
    private EditText contraseñaLogin;
    private Button btnlogin;
    private TextView mensajeLogin;
    private TextView textregistrar;


    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = findViewById(R.id.btnlogin);
        textregistrar= findViewById(R.id.textregistrar);
        usuarioLogin = findViewById(R.id.usuarioLogin);
        contraseñaLogin = findViewById(R.id.contraseñaLogin);
        mensajeLogin = findViewById(R.id.mensajeLogin);



        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(this);

        textregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this,RegistrarActivity.class);
                startActivity(intent2);
                finish();
            }
        });



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //** declarar variable de URL de Api
                String url = "http://192.168.0.18:8030/project/rest/cliente/validarLogin";

                //** creamos el variable para guarda datos tipo JSON
                Map<String, String> objjson = new HashMap<>();

                objjson.put("usuario", usuarioLogin.getText().toString());
                objjson.put("password", contraseñaLogin.getText().toString());


                //** se conviert la variable MAP en un objecto JSON
                JSONObject parametroJson = new JSONObject(objjson);

                //** creo el objeto de requerimiento API
                JsonObjectRequest requerimiento = new JsonObjectRequest(
                        Request.Method.POST   ,
                        url   ,
                        parametroJson  ,

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                //** codificar el resultado de la respuesta.
                                try {

                                    //Verificar respuesta de logeo
                                    if (response.getString("rpta").equals("true")) {


                                        VariableGlobal  mApp = ((VariableGlobal)getApplicationContext());

                                        // Acualizo la variable global con los datos del cliente logeado
                                        mApp.setUsuario(response.getString("usuario"));
                                        mApp.setId_cliente(Integer.parseInt(response.getString("id_cliente")));
                                        mApp.setNombre(response.getString("nombre"));
                                        mApp.setApellidos(response.getString("apellidos"));

                                        //aqui debo de tomar el codigo de cliente
                                        // y guardarlo en una variable global del sistema
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);

                                        startActivity(i);
                                        finish();




                                        //mApp
                                        mensajeLogin.setText("Ingreso correcto");
                                    } else {

                                        mensajeLogin.setText(response.getString("mensaje"));
                                    }

                                } catch (Exception ex) {
                                    Log.e("Error al ejecutar", ex.getMessage());
                                }
                            }
                        }   ,

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
        });

//yanela descansa tu yo me encargo ya anda a relajarte
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}