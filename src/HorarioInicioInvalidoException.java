public class HorarioInicioInvalidoException extends Exception {
    public HorarioInicioInvalidoException(int horario){
        super("O início do atendimento deve ser marcado entre 8h e 17h. "+horario+"Não é um horário de início válido.");
    }
}
