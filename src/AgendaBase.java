import exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class AgendaBase {


    public static List<Atendimento> atendimentos = new ArrayList<>();

    /**
     * @param paciente     o nome do paciente que solicitou o atendimento
     * @param profissional o nome do profissional que realizará o atendimento
     * @param data         a data do atendimento em formato AAAA-MM-DD
     * @param horario      o horário de início do atendimento
     * @param duracao      a duração do atendimento em minutos
     **/
    public static void marcarAtendimento(String paciente, String profissional, String data, int horario, int duracao) {
        try {
            Validador.validarAgendamento(paciente, profissional, data, horario, duracao);
            Validador.checarDisponibilidade(atendimentos, data, horario, duracao, profissional);

            atendimentos.add(new Atendimento(paciente, profissional, data, horario, duracao, "MARCADO"));
            System.out.println("Atendimento marcado para " + paciente);

        }
        // Exceções relacionadas a regra de negócio são capturadas separadamente para que seja exibida uma mensagem amigável ao usuário
        // explicando o problema e que a causa dele está relacionada ao funcionamento do negócio
        // ConflitoHorarioException é capturado no segundo catch para manter a saída do programa
        catch (HorarioInicioInvalidoException | DuracaoInvalidaException | HorarioExcedenteException e) {
            System.out.println("Não foi possível marcar o horário para " + paciente + " às " + horario + " horas de " + data + " .");
            System.out.println("Motivo: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param paciente    o nome do paciente que solicitou o atendimento
     * @param data        a data atual do atendimento marcado em formato AAAA-MM-DD
     * @param horario     o horário atual do atendimento marcado
     * @param novaData    a nova data para a qual se deseja remarcar o atendimento
     * @param novoHorario o novo horário para o qual se deseja remarcar o atendimento
     **/
    public static void remarcarAtendimento(String paciente, String data, int horario, String novaData, int novoHorario) {
        try {
            Atendimento atendimentoMarcado = Validador.buscarAtendimento(atendimentos, paciente, data, horario);
            String profissional = atendimentoMarcado.getProfissional();
            int duracao = atendimentoMarcado.getDuracao();

            Validador.validarAgendamento(paciente, profissional, novaData, novoHorario, duracao);
            Validador.checarDisponibilidade(atendimentos, novaData, novoHorario, duracao, profissional);

            atendimentoMarcado.setData(novaData);
            atendimentoMarcado.setHorario(novoHorario);
            System.out.println("Remarcado");


        } catch (HorarioInicioInvalidoException | DuracaoInvalidaException | HorarioExcedenteException e) {
            System.out.println("Não foi possível marcar o horário para " + paciente + " às " + horario + " horas de " + data + " .");
            System.out.println("Motivo: " + e);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param data a data pela qual se deseja filtrar os atendimentos
     **/
    public static void listarAtendimentosPorData(String data) {
        try {
            Validador.validarData(data);
            for (Atendimento atendimento : atendimentos) {
                if (atendimento.getData().equals(data)) {
                    System.out.println(atendimento.getHorario() + " " + atendimento.getPaciente() + " " + atendimento.getProfissional() + " " + atendimento.getStatus());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DataInvalidaException e) {
            System.out.println("Não foi possível buscar os atendimentos dessa data.");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        marcarAtendimento("Ana", "Dr. Silva", "2026-10-05", 9, 30);
        marcarAtendimento("Bruno", "Dr. Silva", "2026-10-05", 9, 30);
        marcarAtendimento("Carla", "Dra. Souza", "2026-10-05", 14, 60);
        remarcarAtendimento("Ana", "2026-10-05", 9, "2026-10-06", 10);
        listarAtendimentosPorData("2026-10-05");
    }
}

