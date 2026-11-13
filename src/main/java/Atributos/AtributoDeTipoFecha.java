package Atributos;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AtributoDeTipoFecha extends Atributo{
    public AtributoDeTipoFecha(String nombre, boolean obligatorio, Object valor) {
        super(nombre, obligatorio, valor);
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
