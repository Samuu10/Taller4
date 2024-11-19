package com.example.taller4.ui.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.taller4.R;
import com.example.taller4.ui.Almacenamiento.PreferencesManager;
import com.example.taller4.ui.Gestion.Producto;
import java.util.ArrayList;
import java.util.List;

//Clase que representa la factoria de vistas remotas del widget de la aplicación
public class WidgetRemoteViews implements RemoteViewsService.RemoteViewsFactory {

    //Variables
    private Context context;
    private List<Producto> productos;

    //Constructor
    public WidgetRemoteViews(Context context) {
        this.context = context;
    }

    //Metodo que inicializa el widget
    @Override
    public void onCreate() {}

    //Metodo que se ejecuta al cambiar los datos del widget
    @Override
    public void onDataSetChanged() {

        //Se obtienen los productos del usuario
        PreferencesManager preferencesManager = new PreferencesManager(context);
        productos = preferencesManager.loadProductosSync();

        //Si la lista de productos es nula, se crea una nueva lista
        if (productos == null) {
            productos = new ArrayList<>();
        }
    }

    //Metodo que se ejecuta al destruir el widget
    @Override
    public void onDestroy() {
        if (productos != null) {
            productos.clear();
        }
    }

    //Metodo que devuelve el numero de elementos de la lista de productos
    @Override
    public int getCount() {
        return productos != null ? productos.size() : 0;
    }

    //Metodo que crea las vistas remotas del widget
    @Override
    public RemoteViews getViewAt(int position) {

        //Si la lista de productos es nula o está vacía, se devuelve nulo
        if (productos == null || productos.size() == 0) {
            return null;
        }

        //Se obtiene el producto de la lista
        Producto producto = productos.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_producto);
        views.setTextViewText(R.id.textViewNombre, producto.getNombre());

        //Se crea un intent para abrir la aplicación al hacer clic en un producto del widget
        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.item_producto_layout, fillInIntent);

        return views;
    }

    //Metodo que devuelve el id de la vista
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    //Metodo que devuelve el numero de tipos de vistas
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    //Metodo que devuelve el id del elemento de la lista
    @Override
    public long getItemId(int position) {
        return position;
    }

    //Metodo que devuelve si los ids son estables
    @Override
    public boolean hasStableIds() {
        return true;
    }
}