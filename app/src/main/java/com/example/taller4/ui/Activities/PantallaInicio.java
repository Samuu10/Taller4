package com.example.taller4.ui.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taller4.R;
import java.util.Calendar;

//Clase que representa la pantalla de inicio de la aplicación
public class PantallaInicio extends AppCompatActivity {

    //Metodo que se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewGreeting = findViewById(R.id.textViewGreeting);
        new ObtenerSaludoTask(textViewGreeting).execute();

        findViewById(R.id.boton_pantalla_principal).setOnClickListener(v -> {
            startActivity(new Intent(PantallaInicio.this, PantallaPrincipal.class));
        });
    }

    //Clase interna que implementa AsyncTask para obtener el saludo según la hora actual
    private static class ObtenerSaludoTask extends AsyncTask<Void, Void, String> {

        //Variable
        private final TextView textView;

        //Constructor
        public ObtenerSaludoTask(TextView textView) {
            this.textView = textView;
        }

        //Metodo que se ejecuta en segundo plano y obtiene el saludo personalizado según la hora actual
        @Override
        protected String doInBackground(Void... params) {
            //Obtenemos la hora actual y asignamos el saludo correspondiente
            int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            String greeting;
            if (currentHour >= 5 && currentHour <= 11) {
                greeting = "Buenos días";
            } else if (currentHour >= 12 && currentHour <= 20) {
                greeting = "Buenas tardes";
            } else {
                greeting = "Buenas noches";
            }
            return greeting;
        }

        //Metodo que se ejecuta en el hilo principal y actualiza el TextView con el saludo
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
}