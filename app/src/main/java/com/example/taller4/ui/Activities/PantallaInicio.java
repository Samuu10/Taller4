package com.example.taller4.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taller4.R;

public class PantallaInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        findViewById(R.id.boton_pantalla_principal).setOnClickListener(v -> {
            startActivity(new Intent(PantallaInicio.this, PantallaPrincipal.class));
        });
    }

}