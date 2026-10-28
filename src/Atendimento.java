public class Atendimento {
    private String paciente;
    private String profissional;
    private String data;
    private int horario;
    private int duracao;
    private String status;

    public Atendimento(String paciente, String profissional, String data, int horario, int duracao, String status) {
        this.paciente = paciente;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.duracao = duracao;
        this.status = status;
    }

    public String getPaciente() {
        return paciente;
    }


    public String getProfissional() {
        return profissional;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getStatus() {
        return status;
    }

}
