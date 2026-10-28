package exceptions;

public class HorarioExcedenteException extends RuntimeException {
    public HorarioExcedenteException(int horario, int duracao) {
        super("O estabelecimento só funciona até as 18hrs, a consulta não pode durar até as "+ horario+ "hrs e "+duracao+ " minutos.");
    }
}
