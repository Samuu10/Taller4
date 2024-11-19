package com.example.taller4.ui.Almacenamiento;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.example.taller4.ui.Gestion.Producto;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Clase que se encarga de gestionar las preferencias de la aplicación del usuario
public class PreferencesManager {

    //Variables
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_PRODUCTOS = "productos";
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Context context;

    //Constructor
    public PreferencesManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    //Metodo para notificar la actualización del widget
    private void notifyWidgetUpdate() {
        // Notificar actualización del widget si es necesario
    }

    //Metodo para cargar los productos de SharedPreferences de forma sincrona
    public List<Producto> loadProductosSync() {
        String json = sharedPreferences.getString(KEY_PRODUCTOS, null);
        Type type = new TypeToken<ArrayList<Producto>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }

    //Metodo para guardar los productos en SharedPreferences
    public void saveProductos(List<Producto> productos) {
        new SaveProductosTask().execute(productos);
    }

    //Metodo para cargar los productos de SharedPreferences
    public void loadProductos(LoadProductosCallback callback) {
        new LoadProductosTask(callback).execute();
    }

    //Clase interna SaveProductosTask que extiende AsyncTask y guarda los productos en segundo plano
    private class SaveProductosTask extends AsyncTask<List<Producto>, Void, Void> {
        @Override
        protected Void doInBackground(List<Producto>... lists) {
            List<Producto> productos = lists[0];
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String json = gson.toJson(productos);
            editor.putString(KEY_PRODUCTOS, json);
            editor.apply();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Notificar actualización del widget si es necesario
        }
    }

    //Clase interna LoadProductosTask que extiende AsyncTask y carga los productos en segundo plano
    private class LoadProductosTask extends AsyncTask<Void, Void, List<Producto>> {
        private LoadProductosCallback callback;

        public LoadProductosTask(LoadProductosCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<Producto> doInBackground(Void... voids) {
            String json = sharedPreferences.getString(KEY_PRODUCTOS, null);
            Type type = new TypeToken<ArrayList<Producto>>() {}.getType();
            return json != null ? gson.fromJson(json, type) : new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<Producto> productos) {
            callback.onProductosLoaded(productos);
        }
    }

    //Interfaz LoadProductosCallback para manejar la carga de productos
    public interface LoadProductosCallback {
        void onProductosLoaded(List<Producto> productos);
    }
}