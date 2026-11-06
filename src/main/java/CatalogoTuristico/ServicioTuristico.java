package CatalogoTuristico;

import lombok.Getter;

@Getter
public abstract class ServicioTuristico {
    public String nombre;
    public String descripcion;
    private CronogramaServicio cronogramaServicio;

    public abstract float getPrecioBase();
    public abstract float getPrecioFinal();
    public abstract Boolean perteneceACategoria(Categoria categoria);
    public abstract boolean tieneDisponibilidad();


}
