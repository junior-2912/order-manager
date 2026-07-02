package exceptions;

public class PedidoDuplicadoException extends RuntimeException {
    public PedidoDuplicadoException(String message) {
        super(message);
    }
}
