package Atributos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AtributoDeTipoFecha extends Atributo{
    public AtributoDeTipoFecha(String nombre, boolean obligatrio, Object valor) {
        super(nombre, obligatrio, valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        try {
            LocalDate.parse(String.valueOf(valor));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
