package com.example.taller4.ui.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.example.taller4.R;
import com.example.taller4.ui.Activities.PantallaPrincipal;

//Clase que representa el widget de la aplicación
public class WidgetProducto extends AppWidgetProvider {

    //Metodo que actualiza el widget
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //Se recorre la lista de widgets
        for (int appWidgetId : appWidgetIds) {

            //Se crea un intent para abrir la aplicación al hacer clic en el botón del widget
            Intent intent = new Intent(context, PantallaPrincipal.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.widget_button_open_app, pendingIntent);

            //Se crea un intent para cargar los productos
            Intent serviceIntent = new Intent(context, WidgetService.class);
            views.setRemoteAdapter(R.id.widget_listview, serviceIntent);

            //Se actualiza el widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        //Se inicia la acción de actualización del widget
        WidgetUpdateService.startActionUpdateWidget(context);
    }

    //Metodo que se ejecuta al recibir un intent
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            WidgetUpdateService.startActionUpdateWidget(context);
        }
    }
}