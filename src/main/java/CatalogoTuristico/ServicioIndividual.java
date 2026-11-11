package CatalogoTuristico;
import Excepciones.ServicioInvalidoPorAtributoObligatorio;
//import Reportes.Visitor;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class ServicioIndividual extends ServicioTuristico{
    private Integer id;
    private String proveedor;
    private Categoria categoria;
    private float descuento;
    private float precioBase;

    public ServicioIndividual(Integer id, String nombre, String descripcion, String proveedor, float descuento,
                              float preciobase, Categoria categoria, CronogramaServicio cronogramaServicio) {
        if (id == null || nombre == null) {
            throw new ServicioInvalidoPorAtributoObligatorio("servicio invalido, por falta de nombre o codigo");
        } else {
            this.id = id;
            this.nombre = nombre;
            this.descuento = descuento;
            this.descripcion = descripcion;
            this.proveedor = proveedor;
            this.precioBase = preciobase;
            this.categoria = categoria;
            this.cronogramaServicio = cronogramaServicio;
            this.atributosExtra = new ArrayList<>();
        }
    }
    public float getPrecioFinal(){
        return this.precioBase -(this.precioBase * descuento);
    }

    public Boolean perteneceACategoria(Categoria categoria){
        return this.getCategoria() == categoria;
    }

    @Override
    public boolean tieneDisponibilidad() {
        return false;
    }

//    public void aceptar(Visitor visitor){
//        visitor.visitarServicioIndividual(this);
//    }
}