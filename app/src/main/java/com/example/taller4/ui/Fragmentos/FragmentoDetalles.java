package com.example.taller4.ui.Fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.taller4.R;
import com.example.taller4.ui.Gestion.Producto;

//Clase FragmentoDetalles que extiende Fragment y se utiliza para mostrar los detalles de un producto
public class FragmentoDetalles extends Fragment {

    //Variable
    private Producto producto;

    //Metodo que se ejecuta al clicar en un producto de la lista
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragmento_detalles, container, false);
        TextView textViewNombre = view.findViewById(R.id.textViewNombreDetalle);
        TextView textViewDescripcion = view.findViewById(R.id.textViewDescripcionDetalle);
        TextView textViewPrecio = view.findViewById(R.id.textViewPrecioDetalle);

        //Obtener los datos del producto seleccionado
        if (getArguments() != null) {
            producto = getArguments().getParcelable("producto");
            if (producto != null) {
                textViewNombre.setText(producto.getNombre());
                textViewDescripcion.setText(producto.getDescripcion());
                textViewPrecio.setText(producto.getPrecio() + " €");
            }
        }

        //Configurar el boton de volver
        view.findViewById(R.id.boton_volver).setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}