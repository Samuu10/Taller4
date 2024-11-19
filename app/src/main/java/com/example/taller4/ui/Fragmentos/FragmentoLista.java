package com.example.taller4.ui.Fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taller4.R;
import com.example.taller4.ui.Activities.PantallaPrincipal;
import com.example.taller4.ui.Almacenamiento.PreferencesManager;
import com.example.taller4.ui.Gestion.Producto;
import com.example.taller4.ui.Gestion.ProductoAdapter;
import java.util.List;

//Clase FragmentoLista que extiende Fragment y se utiliza para mostrar la lista de productos
public class FragmentoLista extends Fragment implements PreferencesManager.LoadProductosCallback {

    //Variables
    private ProductoAdapter adapter;
    private RecyclerView recyclerView;
    private PreferencesManager preferencesManager;

    //Metodo que se ejecuta al crear la vista del fragmento
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragmento_lista, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        preferencesManager = new PreferencesManager(requireContext());
        preferencesManager.loadProductos(this);
        return view;
    }

    //Metodo que se ejecuta al cargar los productos del usuario
    @Override
    public void onProductosLoaded(List<Producto> loadedProductos) {
        adapter = new ProductoAdapter(getContext(), loadedProductos, producto -> {
            ((PantallaPrincipal) getActivity()).mostrarDetallesProducto(producto);
        });
        recyclerView.setAdapter(adapter);
    }

    //Metodo que se ejecuta al cargar los productos del usuario
    public void actualizarLista(List<Producto> nuevosProductos) {
        if (adapter != null) {
            adapter.setProductos(nuevosProductos);
            adapter.notifyDataSetChanged();
        }
    }
}