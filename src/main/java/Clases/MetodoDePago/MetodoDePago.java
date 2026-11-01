package Clases.MetodoDePago;

public abstract class MetodoDePago {
//Hay que ver como implementarla, ademas en el uml dice que tarjeta de credito, transferencia bancara y billetera virtual
// extienden esta clase pero no aparece como abstracta, hay que arreglar eso en el uml -v

    public abstract void validarDatos();
    public abstract void ejecutarTransaccion();
    public abstract void notificarResultados();
}
