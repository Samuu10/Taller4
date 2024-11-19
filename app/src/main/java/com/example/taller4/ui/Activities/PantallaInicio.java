package com.example.taller4.ui.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.taller4.R;
import java.util.Calendar;

//Clase que representa la pantalla de inicio de la aplicación
public class PantallaInicio extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private RelativeLayout layout;
    private long lastUpdate = 0;

    //Metodo que se ejecuta al crear la actividad
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_inicio);

        layout = findViewById(R.id.main_layout);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewGreeting = findViewById(R.id.textViewGreeting);
        new ObtenerSaludoTask(textViewGreeting).execute();

        //Configurar el boton para ir a la pantalla principal
        findViewById(R.id.boton_pantalla_principal).setOnClickListener(v -> {
            startActivity(new Intent(PantallaInicio.this, PantallaPrincipal.class));
        });

        //Inicializar el sensor de acelerómetro
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    //Metodo que se ejecuta al reanudar la actividad y registra el sensor
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    //Metodo que se ejecuta al pausar la actividad y desregistra el sensor
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    //Metodo que simula el movimiento del dispositivo y cambia el color del layout
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //Simulamos un movimiento del dispositivo cada 2 segundos
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastUpdate) > 2000) {
                lastUpdate = currentTime;

                // Detecta si el dispositivo se ha movido y cambia el color del layout
                if (Math.abs(x) > 5 || Math.abs(y) > 5 || Math.abs(z) > 5) {
                    runOnUiThread(() -> {
                        int currentColor = ((ColorDrawable) layout.getBackground()).getColor();
                        int newColor = (currentColor == getResources().getColor(R.color.teal_200)) ?
                                getResources().getColor(R.color.teal_700) :
                                getResources().getColor(R.color.teal_200);
                        layout.setBackgroundColor(newColor);
                    });
                }
            }
        }
    }

    //Metodo que se ejecuta al cambiar la precisión del sensor (no se utiliza)
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

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
            if (currentHour >= 5 && currentHour < 12) {
                greeting = "Buenos Días";
            } else if (currentHour >= 12 && currentHour < 20) {
                greeting = "Buenas Tardes";
            } else {
                greeting = "Buenas Noches";
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