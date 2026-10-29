package Clases;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServicioIndividual extends ServicioTuristico{
    private int id;
    private String proveedor;
    private Categoria categoria;
    private float descuento;
    private float precioBase;
    private List<Atributo> atributos = new ArrayList<>();

    public ServicioIndividual(int id, String nombre, String descripcion, String proveedor, float descuento, float preciobase, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
        this.precioBase = preciobase;
        this.categoria = categoria;
    }
    public float getPrecioFinal(){
        return this.precioBase -(this.precioBase * descuento);
    }
}