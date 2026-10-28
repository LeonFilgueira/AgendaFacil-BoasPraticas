package exceptions;

public class ConflitoHorarioException extends RuntimeException {
    public ConflitoHorarioException() {
        super("Horario ocupado");
    }
}
