package Atributos;

public class AtributoTipoBooleano extends Atributo {
    public AtributoTipoBooleano(String nombre, boolean obligatorio, Object valor) {
        super(nombre, obligatorio, valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        try {
            Boolean booleano = (Boolean) valor;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
