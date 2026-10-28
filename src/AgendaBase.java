import exceptions.*;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class AgendaBase {





    public static List<Atendimento> atendimentos = new ArrayList<>();



    public static void marcarAtendimento(String paciente, String profissional, String data, int horario, int duracao) {
        try {
            Validador.validarAgendamento(horario, duracao);
            Validador.checaDisponibilidade(atendimentos, data, horario, duracao, profissional);

            atendimentos.add(new Atendimento(paciente, profissional, data, horario, duracao, "MARCADO"));
            System.out.println("Atendimento marcado para " + paciente);

        } catch (HorarioInicioInvalidoException | DuracaoInvalidaException | HorarioExcedenteException e) {
            System.out.println("Não foi possível marcar o horário para " + paciente + "às " + horario + "horas de " + data + " .");
            System.out.println("Motivo: " + e.getMessage());
        } catch (ConflitoHorarioException e) {
            System.out.println(e.getMessage());
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
        try{
            Validador.validarData(data);
            for (Atendimento atendimento : atendimentos) {
                if (atendimento.data.equals(data)) {
                    System.out.println(atendimento.horario + " " + atendimento.paciente + " " + atendimento.profissional + " " + atendimento.status);
                }
            }
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (DateTimeParseException e){
            System.out.println("Não foi possível buscar os atendimentos dessa data. '"+data+"' não é uma data válida." );
            System.out.println("A data deve ser inserida conforme o formato AAAA-MM-DD e deve existir");
        }
    }

    public static void main(String[] args) {
        marcarAtendimento("Ana", "Dr. Silva", "2026-10-05", 9, 30);
        marcarAtendimento("Bruno", "Dr. Silva", "2026-10-05", 9, 30);
        marcarAtendimento("Carla", "Dra. Souza", "2026-10-05", 14, 60);
        remarcarAtendimento("Ana", "2026-10-05", 9, "2026-10-06", 10);
        listarAtendimentos("2026-10-0");
    }
}

