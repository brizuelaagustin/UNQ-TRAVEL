package Atributos;

public class AtributoDeTexto extends Atributo {
    public AtributoDeTexto(String nombre, boolean obligatorio, Object valor) {
        super(nombre, obligatorio, valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) { //
        try {
            String texto = (String) valor;
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
