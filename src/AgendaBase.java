import java.util.ArrayList;
import java.util.List;

public class AgendaBase {

    public static final int INICIO_EXPEDIENTE = 8;
    public static final int FIM_EXPEDIENTE = 18;
    public static final int DURACAO_MINIMA = 15;
    public static final int ULTIMO_HORARIO = 17;
    public static final int DURACAO_MAXIMA = 120;

    static class Atendimento {
        String paciente;
        String profissional;
        String data;
        int horario;
        int duracao;
        String status;

        Atendimento(String paciente, String profissional, String data, int horario, int duracao, String status) {
            this.paciente = paciente;
            this.profissional = profissional;
            this.data = data;
            this.horario = horario;
            this.duracao = duracao;
            this.status = status;
        }
    }

    static List<Atendimento> atendimentos = new ArrayList<>();

    public static void validarAgendamento(int horario, int duracao) throws HorarioInicioInvalidoException, DuracaoInvalidaException {

        if (horario <= INICIO_EXPEDIENTE || horario >= ULTIMO_HORARIO) {
            throw new HorarioInicioInvalidoException(horario);
        }

        if (duracao <= DURACAO_MINIMA || duracao > DURACAO_MAXIMA || (duracao % DURACAO_MINIMA) != 0) {
            throw new DuracaoInvalidaException(duracao);
        }

    }


    public static void checaDisponibilidade(String data, int horario, int duracao, String profissional) {
        float duracaoHoras = duracao / 60;

        if (horario + duracao > FIM_EXPEDIENTE) {

        }

//        for (Atendimento atendimento : atendimentos) {
//            boolean conflitoProfissionalDataHorario = atendimento.data.equals(data) && atendimento.horario == horario && atendimento.profissional.equals(profissional);
//            if (conflitoProfissionalDataHorario) {
//
//            }
//            if (conflitoProfissionalDataHorario ||)
//        }
//    }

    public static void marcarAtendimento(String paciente, String profissional, String data, int horario, int duracao) {
        try {
            validarAgendamento(horario, duracao);
            boolean ok = true;
            for (Atendimento atendimento : atendimentos) {
                if (atendimento.data.equals(data) && atendimento.horario == horario && atendimento.profissional.equals(profissional)) {
                    ok = false;
                }
            }
            if (ok) {
                atendimentos.add(new Atendimento(paciente, profissional, data, horario, duracao, "MARCADO"));
                System.out.println("Atendimento marcado para " + paciente);
            } else {
                System.out.println("Horario ocupado");
            }
        } catch (HorarioInicioInvalidoException | DuracaoInvalidaException e) {
            System.out.println("Não foi possível marcar o horário para " + paciente + "às " + horario + "horas de " + data + " .");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    public static void remarcarAtendimento(String paciente, String data, int horario, String novaData, int novoHorario) {
        for (Atendimento a : atendimentos) {
            if (a.paciente.equals(paciente) && a.data.equals(data) && a.horario == horario) {
                boolean ok = true;
                for (Atendimento atendimento : atendimentos) {
                    if (atendimento.data.equals(novaData) && atendimento.horario == novoHorario && atendimento.profissional.equals(a.profissional)) {
                        ok = false;
                    }
                }
                if (ok) {
                    a.data = novaData;
                    a.horario = novoHorario;
                    System.out.println("Remarcado");
                } else {
                    System.out.println("Horario ocupado");
                }
            }
        }
    }

    public static void listarAtendimentos(String data) {
        for (Atendimento atendimento : atendimentos) {
            if (atendimento.data.equals(data)) {
                System.out.println(atendimento.horario + " " + atendimento.paciente + " " + atendimento.profissional + " " + atendimento.status);
            }
        }
    }

    public static void main(String[] args) {
        marcarAtendimento("Ana", "Dr. Silva", "2026-10-05", 9, 30);
        marcarAtendimento("Bruno", "Dr. Silva", "2026-10-05", 9, 30);
        marcarAtendimento("Carla", "Dra. Souza", "2026-10-05", 14, 60);
        remarcarAtendimento("Ana", "2026-10-05", 9, "2026-10-06", 10);
        listarAtendimentos("2026-10-05");
    }
}
