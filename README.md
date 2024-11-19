# Proyecto de Gestión de Productos

El objetivo de este proyecto es diseñar una aplicación para gestionar una lista de items.
Hay que hacer uso de fragmentos para crear vistas reutilizables y modulares que mejoren el rendimiento de la app y la interacción del usuario.
También hay que implementar un widget para poder acceder a la aplicación desde la pantalla principal del dispositivo.
Por último hay que hacer uso de sensores para detectar posibles movimientos en el dispositivo, y en base a ellos, realizar una acción determinada.

## Clases JAVA

1. **PantallaInicio**:
    - Controla la pantalla de inicio de la aplicación.
    - Muestra un saludo basado en la hora del día.
    - Cambia el color de fondo basado en los valores del acelerómetro.

2. **PantallaPrincipal**:
    - Controla la actividad principal de la aplicación.
    - Muestra la lista de productos y permite añadir o eliminar productos.
    - Maneja la navegación entre fragmentos.

3. **PreferencesManager**:
    - Gestiona las preferencias de la aplicación.
    - Guarda y carga la lista de productos usando `SharedPreferences`.
    - Notifica la actualización del widget.

4. **FragmentoDetalles**:
    - Muestra los detalles de un producto seleccionado.
    - Permite volver a la lista de productos.

5. **FragmentoLista**:
    - Muestra la lista de productos en un `RecyclerView`.
    - Carga los productos desde `PreferencesManager`.

6. **Producto**:
    - Representa un producto con nombre, descripción y precio.
    - Implementa `Parcelable` para ser enviado entre actividades.

7. **ProductoAdapter**:
    - Adaptador para mostrar la lista de productos en un `RecyclerView`.
    - Maneja los clics en los elementos de la lista.

8. **WidgetProducto**:
    - Controla el widget de la aplicación.
    - Actualiza el widget con la lista de productos.

9. **WidgetRemoteViews**:
    - Proporciona las vistas remotas para el widget.
    - Carga los productos desde `PreferencesManager`.

10. **WidgetService**:
    - Servicio para el widget que crea la fábrica de vistas remotas.

11. **WidgetUpdateService**:
    - Servicio que gestiona la actualización del widget en segundo plano.

12. **WidgetUpdateTask**:
    - Tarea en segundo plano que actualiza el widget con la lista de productos.

## Archivos XML

1. **pantalla_inicio.xml**:
    - Layout para la pantalla de inicio.
    - Contiene un `RelativeLayout` con un saludo y un botón para ir a la pantalla principal.

2. **pantalla_principal.xml**:
    - Layout para la pantalla principal.
    - Contiene un `FrameLayout` para los fragmentos y botones para añadir y eliminar productos.

3. **agregar_producto.xml**:
    - Layout para el diálogo de añadir producto.
    - Contiene `EditText` para ingresar el nombre, descripción y precio del producto.

4. **eliminar_producto.xml**:
    - Layout para el diálogo de eliminar producto.
    - Contiene un `EditText` para ingresar el nombre del producto a eliminar.

5. **fragmento_detalles.xml**:
    - Layout para el fragmento de detalles del producto.
    - Muestra el nombre, descripción y precio del producto.

6. **fragmento_lista.xml**:
    - Layout para el fragmento de lista de productos.
    - Contiene un `RecyclerView` para mostrar la lista de productos.

7. **item_producto.xml**:
    - Layout para cada elemento de la lista de productos.
    - Muestra el nombre del producto.

8. **widget.xml**:
    - Layout para el widget de la aplicación.
    - Contiene un `TextView` para el título, un `ListView` para la lista de productos y un botón para abrir la aplicación.

## Link al repositorio: https://github.com/Samuu10/Taller4.git
