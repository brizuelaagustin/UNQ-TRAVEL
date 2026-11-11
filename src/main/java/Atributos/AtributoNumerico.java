package Atributos;

public class AtributoNumerico extends Atributo{
    public AtributoNumerico(String nombre, boolean obligatorio, Object valor) {
        super(nombre,obligatorio,valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        try {
            Integer.valueOf(String.valueOf(valor));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
