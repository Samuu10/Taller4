package com.example.taller4.ui.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import com.example.taller4.R;
import com.example.taller4.ui.Activities.PantallaPrincipal;
import com.example.taller4.ui.Almacenamiento.PreferencesManager;
import com.example.taller4.ui.Gestion.Producto;
import java.util.ArrayList;
import java.util.List;

//Clase que gestiona la actualización del widget de la aplicación en segundo plano
public class WidgetUpdateTask extends AsyncTask<Void, Void, List<Producto>> {

    //Variable
    private Context context;

    //Constructor
    public WidgetUpdateTask(Context context) {
        this.context = context;
    }

    //Metodo que se ejecuta en segundo plano y carga los productos del usuario
    @Override
    protected List<Producto> doInBackground(Void... voids) {
        PreferencesManager preferencesManager = new PreferencesManager(context);
        List<Producto> productos = preferencesManager.loadProductosSync();
        if (productos == null) {
            productos = new ArrayList<>();
        }
        return productos;
    }

    //Metodo que actualiza el widget de la aplicación
    @Override
    protected void onPostExecute(List<Producto> productos) {

        //Si la lista de productos es nula, se crea una nueva lista
        if (productos == null) {
            productos = new ArrayList<>();
        }

        //Se obtiene el widget manager y las vistas del widget
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        //Se establece el título del widget y se crea un intent para cargar los productos
        views.setTextViewText(R.id.widget_title, "PRODUCTOS: ");
        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_listview, intent);

        //Se crea un intent para abrir la aplicación al hacer clic en el botón del widget
        Intent mainIntent = new Intent(context, PantallaPrincipal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_button_open_app, pendingIntent);

        //Se actualiza el widget
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProducto.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }
}