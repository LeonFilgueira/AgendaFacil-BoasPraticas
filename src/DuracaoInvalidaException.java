public class DuracaoInvalidaException extends Exception {
    public DuracaoInvalidaException(int duracao) {
        super("A duração de um atendimento deve estar entre 15 e 120 minutos, em múltiplos de 15. "+ duracao + " não é uma duraçao válida.");
    }
}
