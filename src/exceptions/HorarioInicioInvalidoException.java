package exceptions;

public class HorarioInicioInvalidoException extends RuntimeException {
    public HorarioInicioInvalidoException(int horario){
        super("O início do atendimento deve ser marcado entre 8h e 17h. "+horario+"h não é um horário de início válido.");
    }
}
