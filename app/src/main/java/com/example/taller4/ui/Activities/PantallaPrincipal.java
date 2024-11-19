package com.example.taller4.ui.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.taller4.R;
import com.example.taller4.ui.Almacenamiento.PreferencesManager;
import com.example.taller4.ui.Fragmentos.FragmentoDetalles;
import com.example.taller4.ui.Fragmentos.FragmentoLista;
import com.example.taller4.ui.Gestion.Producto;
import java.util.List;

//Clase que representa la pantalla principal de la aplicación
public class PantallaPrincipal extends AppCompatActivity implements PreferencesManager.LoadProductosCallback {

    //Variables
    private List<Producto> productos;
    private PreferencesManager preferencesManager;

    //Metodo que se ejecuta al crear la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencesManager = new PreferencesManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        preferencesManager.loadProductos(this);

        findViewById(R.id.boton_añadir).setOnClickListener(v -> mostrarDialogoAñadirProducto());
        findViewById(R.id.boton_eliminar).setOnClickListener(v -> mostrarDialogoEliminarProducto());

        findViewById(R.id.boton_incio).setOnClickListener(v -> {
            startActivity(new Intent(PantallaPrincipal.this, PantallaInicio.class));
        });

        loadFragment(new FragmentoLista(), "Lista de Productos");
    }

    //Metodo que se ejecuta cuando los productos han sido cargados
    @Override
    public void onProductosLoaded(List<Producto> loadedProductos) {
        productos = loadedProductos;
        actualizarFragmentos();
    }

    //Metodo para cargar un fragmento en el contenedor principal
    private void loadFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setTitle(title);
    }

    //Metodo para mostrar el dialogo de añadir un producto
    private void mostrarDialogoAñadirProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_producto, null);
        EditText editTextNombre = view.findViewById(R.id.editTextNombre);
        EditText editTextDescripcion = view.findViewById(R.id.editTextDescripcion);
        EditText editTextPrecio = view.findViewById(R.id.editTextPrecio);

        builder.setView(view)
                .setTitle("Añadir Producto a la Lista")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Añadir", null);

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            String descripcion = editTextDescripcion.getText().toString();
            String precio = editTextPrecio.getText().toString();

            if (nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
                Toast.makeText(PantallaPrincipal.this, "Debes completar todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                añadirProductoLista(nombre, descripcion, precio);
                dialog.dismiss();
            }
        });
    }

    //Metodo para mostrar el dialogo de eliminar un producto
    private void mostrarDialogoEliminarProducto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.eliminar_producto, null);
        EditText editTextNombre = view.findViewById(R.id.editTextTituloEliminar);

        builder.setView(view)
                .setTitle("Eliminar Producto de la Lista")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (dialog, id) -> {
                    String nombre = editTextNombre.getText().toString();
                    eliminarProductoLista(nombre);
                });
        builder.create().show();
    }

    //Metodo para añadir un producto a la lista
    private void añadirProductoLista(String nombre, String descripcion, String precio) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                Toast.makeText(PantallaPrincipal.this, "El producto ya está en la lista", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Producto nuevoProducto = new Producto(nombre, descripcion, precio);
        productos.add(nuevoProducto);
        preferencesManager.saveProductos(productos);
        actualizarFragmentos();
        Toast.makeText(PantallaPrincipal.this, "Producto añadido a la lista", Toast.LENGTH_SHORT).show();
    }

    //Metodo para eliminar un producto de la lista
    private void eliminarProductoLista(String nombre) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productos.remove(producto);
                preferencesManager.saveProductos(productos);
                actualizarFragmentos();
                Toast.makeText(PantallaPrincipal.this, "Producto eliminado de la lista", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(PantallaPrincipal.this, "Producto no encontrado en la lista", Toast.LENGTH_SHORT).show();
    }

    //Metodo para actualizar los fragmentos con los cambios en la lista
    private void actualizarFragmentos() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof FragmentoLista) {
            ((FragmentoLista) fragment).actualizarLista(productos);
        }
    }

    //Metodo para mostrar los detalles de un producto en un nuevo fragmento
    public void mostrarDetallesProducto(Producto producto) {
        FragmentoDetalles fragment = new FragmentoDetalles();
        Bundle args = new Bundle();
        args.putParcelable("producto", producto);
        fragment.setArguments(args);
        loadFragment(fragment, "Detalles del Producto");
    }
}