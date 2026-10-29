package Clases;

import Atributos.Atributo;
import Excepciones.ServicioInvalidoPorAtributoObligatorio;
import Excepciones.servicioInvalidoPorAtributoExtra;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServicioIndividual extends ServicioTuristico{
    private Integer id;
    private String proveedor;
    private Categoria categoria;
    private float descuento;
    private float precioBase;
    private List<Atributo> atributosExtra = new ArrayList<>();

    public ServicioIndividual(Integer id, String nombre, String descripcion, String proveedor, float descuento, float preciobase, Categoria categoria) {
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

    public void agregarAtributoExtra(Atributo atributo){
        atributosExtra.add(atributo);
    }
    public void validarAtributos(){
        if (id == null || nombre == null){
            throw new ServicioInvalidoPorAtributoObligatorio("servicio invalido, por falta de nombre o codigo");
        }
        for (Atributo a : atributosExtra) {
            if(!a.esValido()){
              throw new servicioInvalidoPorAtributoExtra("atributo extra invalido");
            }
        }
    }
}