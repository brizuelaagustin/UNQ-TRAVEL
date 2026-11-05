package CatalogoTuristico;

import Atributos.Atributo;
import Excepciones.ServicioInvalidoPorAtributoObligatorio;
import Excepciones.ServicioInvalidoPorAtributoExtra;
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
    private Integer cupoDisponible;

    public ServicioIndividual(Integer id, String nombre, String descripcion, String proveedor, float descuento, float preciobase, Categoria categoria, Integer cupoDisponible) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
        this.descripcion = descripcion;
        this.proveedor = proveedor;
        this.precioBase = preciobase;
        this.categoria = categoria;
        this.cupoDisponible = cupoDisponible;
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
              throw new ServicioInvalidoPorAtributoExtra("atributo extra invalido");
            }
        }
    }

    public Boolean perteneceACategoria(Categoria categoria){
        return this.getCategoria() == categoria;
    }

    @Override
    public boolean tieneDisponibilidad() {
        return this.cupoDisponible > 0;
    }
}