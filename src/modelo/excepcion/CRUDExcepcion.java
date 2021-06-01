package modelo.excepcion;

public class CRUDExcepcion extends RuntimeException{
    public CRUDExcepcion() {
    }

    public CRUDExcepcion(String message) {
        super(message);
    }
}
