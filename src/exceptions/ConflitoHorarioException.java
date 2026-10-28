package exceptions;

public class ConflitoHorarioException extends Exception {
    public ConflitoHorarioException() {
        super("Horario ocupado");
    }
}
