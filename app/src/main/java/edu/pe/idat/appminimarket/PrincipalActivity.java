package edu.pe.idat.appminimarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.pe.idat.appminimarket.Modelo.VariableGlobal;

public class PrincipalActivity extends AppCompatActivity {
    private FloatingActionButton ebtnlogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        ebtnlogin = findViewById(R.id.fab);
        //declaracion en variable global
        VariableGlobal variableGlobal = ((VariableGlobal)getApplicationContext());
        variableGlobal.setId_cliente(0);
        variableGlobal.setNombre("");
        variableGlobal.setApellidos("");
        variableGlobal.setUsuario("");


        ebtnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalActivity.this,LoginActivity.class);

                startActivity(intent);
            }
        });
    }
}