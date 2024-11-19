package com.example.taller4.ui.Gestion;

import android.os.Parcel;
import android.os.Parcelable;

//Clase Producto que representa un producto e implementa la interfaz Parcelable para poder ser enviado entre actividades
public class Producto implements Parcelable {

    //Variables que representan los atributos de un producto
    private String nombre;
    private String descripcion;
    private String precio;

    //Constructor sin parametros
    public Producto() {}

    //Constructor con parametros
    public Producto(String nombre, String descripcion, String precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    //Constructor que recibe un objeto Parcel y lo convierte en un objeto Producto
    protected Producto(Parcel in) {
        nombre = in.readString();
        descripcion = in.readString();
        precio = in.readString();
    }

    //Metodo estático que crea un objeto Producto a partir de un objeto Parcel
    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    //Getters & Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    //Metodo que devuelve un entero que representa el tipo de contenido
    @Override
    public int describeContents() {
        return 0;
    }

    //Metodo que convierte un objeto Producto en un Parcel
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(descripcion);
        parcel.writeString(precio);
    }
}