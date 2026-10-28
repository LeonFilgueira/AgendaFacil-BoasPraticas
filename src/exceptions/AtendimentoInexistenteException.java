package exceptions;

public class AtendimentoInexistenteException extends RuntimeException {
    public AtendimentoInexistenteException(String paciente, String data, int horario ) {
        super("Não existe um atendimento marcado para "+paciente+" em "+data+" às "+ horario+"hrs");
    }
}
