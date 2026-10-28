import exceptions.ConflitoHorarioException;
import exceptions.DuracaoInvalidaException;
import exceptions.HorarioExcedenteException;
import exceptions.HorarioInicioInvalidoException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class Validador {
    public static void validarAgendamento(int horarioInicio, int duracao) throws HorarioInicioInvalidoException, DuracaoInvalidaException, HorarioExcedenteException {

        if (horarioInicio < Constantes.INICIO_EXPEDIENTE || horarioInicio > Constantes.ULTIMO_HORARIO) {
            throw new HorarioInicioInvalidoException(horarioInicio);
        }

        if (duracao < Constantes.DURACAO_MINIMA || duracao > Constantes.DURACAO_MAXIMA || (duracao % Constantes.DURACAO_MINIMA) != 0) {
            throw new DuracaoInvalidaException(duracao);
        }

        int inicioMinutos = horarioInicio * Constantes.MINUTOS_POR_HORA;
        int fimMinutos = inicioMinutos + duracao;
        int fimExpedienteMinutos = Constantes.FIM_EXPEDIENTE * Constantes.MINUTOS_POR_HORA;

        if (fimMinutos > fimExpedienteMinutos) {
            throw new HorarioExcedenteException(horarioInicio, duracao);
        }

    }

    public static void validarData(String data) {
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);

        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Por favor informe uma data com formato AAAA-MM-DD.");
        }

        LocalDate.parse(data, FORMATTER);
    }


    public static void checaDisponibilidade(List<Atendimento> atendimentos, String data, int horarioInicio, int duracao, String profissional) throws ConflitoHorarioException {
        int inicioMinutos = horarioInicio * Constantes.MINUTOS_POR_HORA;
        int fimMinutos = inicioMinutos + duracao;
        int fimExpedienteMinutos = Constantes.FIM_EXPEDIENTE * Constantes.MINUTOS_POR_HORA;

        for (Atendimento atendimento : atendimentos) {
            boolean mesmoProfissionalEData = atendimento.data.equals(data) && atendimento.profissional.equals(profissional);
            if (mesmoProfissionalEData) {
                int inicioExistenteMinutos = atendimento.horario * Constantes.MINUTOS_POR_HORA;
                int fimExistenteMinutos = inicioExistenteMinutos + atendimento.duracao;

                if (inicioMinutos < fimExistenteMinutos && inicioExistenteMinutos < fimMinutos) {
                    throw new ConflitoHorarioException();
                }
            }
        }
    }
}
