package com.example.taller4.ui.Gestion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taller4.R;
import java.util.List;

//Clase que extiende RecyclerView.Adapter y se utiliza para mostrar la lista de productos
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    //Atributos
    private Context context;
    private List<Producto> productos;
    private OnItemClickListener listener;

    //Constructor
    public ProductoAdapter(Context context, List<Producto> productos, OnItemClickListener listener) {
        this.context = context;
        this.productos = productos;
        this.listener = listener;
    }

    // Getters & Setters
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    public OnItemClickListener getListener() {
        return listener;
    }
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //Metodo para crear una nueva vista
    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    //Metodo para enlazar los datos de la lista con los elementos de la vista
    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.textViewNombre.setText(producto.getNombre());
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(producto));
        } else {
            holder.itemView.setOnClickListener(null);
        }
    }

    //Metodo para obtener el número de elementos en la lista
    @Override
    public int getItemCount() {
        return productos.size();
    }

    //Clase estática ProductoViewHolder que extiende RecyclerView.ViewHolder y se mantienen las referencias de los elementos de la vista
    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
        }
    }

    //Interfaz OnItemClickListener que se utiliza para gestionar los clics en los elementos de la lista
    public interface OnItemClickListener {
        void onItemClick(Producto producto);
    }
}