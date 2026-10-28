package exceptions;

public class DataInvalidaException extends RuntimeException {
    public DataInvalidaException(String data) {
        super("'"+data+"' não é uma data válida. A data deve ser inserida conforme o formato AAAA-MM-DD e deve existir");
    }
}
