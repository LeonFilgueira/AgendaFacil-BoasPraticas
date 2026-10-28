import exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

public class Validador {

    /**
     * @param data          a data do atendimento em formato AAAA-MM-DD
     * @param horarioInicio o horário de início do atendimento
     * @param duracao       a duração do atendimento em minutos
     * @param paciente      o nome do paciente que solicitou o atendimento
     * @param profissional  o nome do profissional que realizará o atendimento
     * @throws IllegalArgumentException       se a data, o nome do paciente ou o nome do profissional estiverem vazios
     * @throws DataInvalidaException          se o formato da data for inválido ou ela não existir
     * @throws HorarioInicioInvalidoException se o horário de início estiver fora do horário permitido (8h-17h)
     * @throws HorarioExcedenteException      se o atendimento for exceder o horário do fim do expediente (18h)
     * @throws DuracaoInvalidaException       se a duração for menor que 15, maior que 120 ou não for múltiplo de 15
     *
     **/
    public static void validarAgendamento(String paciente, String profissional, String data, int horarioInicio, int duracao) {
            validarData(data);


        if (paciente.isBlank()) {
            throw new IllegalArgumentException("É necessário informar o nome do paciente.");
        }

        if (profissional.isBlank()) {
            throw new IllegalArgumentException("É necessário informar o nome do profissional.");
        }

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

    /**
     * @param data a data do atendimento em formato AAAA-MM-DD
     * @throws IllegalArgumentException se a string da data estiver vazia
     * @throws DataInvalidaException    se o formato da data for inválido ou ela não existir
     **/
    public static void validarData(String data) {
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);

        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("Data inválida: por favor informe uma data com formato AAAA-MM-DD.");
        }
        try {
            LocalDate.parse(data, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DataInvalidaException(data);
        }


    }

    /**
     * @param atendimentos  a lista de atendimentos registrados
     * @param data          a data do atendimento em formato AAAA-MM-DD
     * @param horarioInicio o horário de início do atendimento
     * @param duracao       a duração do atendimento em minutos
     * @param profissional  o nome do profissional que realizará o atendimento
     * @throws ConflitoHorarioException se o profissional estiver ocupado em outro atendimento no horário informado
     **/
    public static void checarDisponibilidade(List<Atendimento> atendimentos, String data, int horarioInicio, int duracao, String profissional) throws ConflitoHorarioException {
        int inicioMinutos = horarioInicio * Constantes.MINUTOS_POR_HORA;
        int fimMinutos = inicioMinutos + duracao;

        for (Atendimento atendimento : atendimentos) {
            boolean mesmoProfissionalEData = atendimento.getData().equals(data) && atendimento.getProfissional().equals(profissional);
            if (mesmoProfissionalEData) {
                int inicioExistenteMinutos = atendimento.getHorario() * Constantes.MINUTOS_POR_HORA;
                int fimExistenteMinutos = inicioExistenteMinutos + atendimento.getDuracao();

                if (inicioMinutos < fimExistenteMinutos && inicioExistenteMinutos < fimMinutos) {
                    throw new ConflitoHorarioException();
                }
            }
        }
    }

    /**
     * @param atendimentos  a lista de atendimentos registrados
     * @param paciente      o nome do paciente que solicitou o atendimento
     * @param data          a data do atendimento em formato AAAA-MM-DD
     * @param horarioInicio o horário de início do atendimento
     * @return o objeto do atendimento correspondente aos dados informados
     * @throws AtendimentoInexistenteException se não houver na lista um atendimento correspondente aos dados informados
     **/
    public static Atendimento buscarAtendimento(List<Atendimento> atendimentos, String paciente, String data, int horarioInicio) {
        for (Atendimento atendimento : atendimentos) {
            if (atendimento.getPaciente().equals(paciente) && atendimento.getData().equals(data) && atendimento.getHorario() == horarioInicio) {
                return atendimento;
            }

        }
        throw new AtendimentoInexistenteException(paciente, data, horarioInicio);

    }


}
