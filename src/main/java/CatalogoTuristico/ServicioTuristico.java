package CatalogoTuristico;

import Atributos.Atributo;
import Excepciones.ServicioInvalidoPorAtributoExtra;
//import Reportes.Visitable;
//import Reportes.Visitor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public abstract class ServicioTuristico {
    public String nombre;
    public String descripcion;
    public CronogramaServicio cronogramaServicio;
    public List<Atributo> atributosExtra;


    public abstract float getPrecioBase();
    public abstract float getPrecioFinal();
    public abstract Boolean perteneceACategoria(Categoria categoria);
    public abstract boolean tieneDisponibilidad();
//    public abstract void aceptar(Visitor visitor);

    public void quitarCupos(Integer cantidadDeCupos, LocalDate fechaInicio, LocalDate fechaFin) {
        cronogramaServicio.quitarCupos(cantidadDeCupos, fechaInicio, fechaFin);
    }

    public boolean hayCuposEn(LocalDate fechaInicio, LocalDate fechaFin, Integer cantidadCupos) {
        return cronogramaServicio.hayCuposEn(fechaInicio, fechaFin, cantidadCupos);
    }

    public void devolverCupos(Integer cantidad, LocalDate fechaInicio, LocalDate fechaFin) {
        cronogramaServicio.devolverCupos(cantidad, fechaInicio, fechaFin);
    }

    public void agregarAtributoExtra(Atributo atributo){
        if(!atributo.esValido()){
            throw new ServicioInvalidoPorAtributoExtra("atributo extra invalido");
        } else {
            atributosExtra.add(atributo);
        }
    }


    public int cantidadVendidaEnPeriodo(LocalDate desde, LocalDate hasta) {
        int total = 0;
        return total;
    }


    public void agregarCupos(int cantCupos, LocalDate fechaInicio, LocalDate fechaFin) {
        cronogramaServicio.agregarCupo(cantCupos, fechaInicio, fechaFin);
    }
}


