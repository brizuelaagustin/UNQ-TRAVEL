package Atributos;

import java.time.LocalDate;

public class AtributoDeTipoFecha extends Atributo{
    public AtributoDeTipoFecha(String nombre, boolean obligatrio, Object valor) {
        super(nombre, obligatrio, valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        return valor instanceof LocalDate;
    }
}
