package com.example.taller4.ui.Activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taller4.R;

public class PantallaPrincipal extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);


        findViewById(R.id.boton_añadir).setOnClickListener(v -> mostrarDialogoAñadirProducto());
        findViewById(R.id.boton_eliminar).setOnClickListener(v -> mostrarDialogoEliminarProducto());
    }

    public void mostrarDialogoAñadirProducto() {

    }

    public void mostrarDialogoEliminarProducto() {

    }
}