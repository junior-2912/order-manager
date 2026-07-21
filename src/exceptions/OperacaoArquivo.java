package exceptions;

public class OperacaoArquivo extends RuntimeException {
    public OperacaoArquivo(String message) {
        super(message);
    }
}
