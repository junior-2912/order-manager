package exceptions;

public class PedidoFinalizadoException extends RuntimeException {
    public PedidoFinalizadoException(String message) {
        super(message);
    }
}
