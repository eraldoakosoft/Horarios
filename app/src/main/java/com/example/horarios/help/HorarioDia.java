package com.example.horarios.help;

public class HorarioDia {
    private String idHorarioData;
    private String horaEntrada;
    private String horaSaidaCafe;
    private String horaVoltaCafe;
    private String HoraSaidaAlmoco;
    private String horarioSaida;

    public HorarioDia(String idHorarioData) {
        this.idHorarioData = idHorarioData;
        this.horaEntrada = "00:00:00";
        this.horaSaidaCafe = "00:00:00";
        this.horaVoltaCafe = "00:00:00";
        this.HoraSaidaAlmoco = "00:00:00";
        this.horarioSaida = "00:00:00";
    }

    public String getIdHorarioData() {
        return idHorarioData;
    }

    public void setIdHorarioData(String idHorarioData) {
        this.idHorarioData = idHorarioData;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaidaCafe() {
        return horaSaidaCafe;
    }

    public void setHoraSaidaCafe(String horaSaidaCafe) {
        this.horaSaidaCafe = horaSaidaCafe;
    }

    public String getHoraVoltaCafe() {
        return horaVoltaCafe;
    }

    public void setHoraVoltaCafe(String horaVoltaCafe) {
        this.horaVoltaCafe = horaVoltaCafe;
    }

    public String getHoraSaidaAlmoco() {
        return HoraSaidaAlmoco;
    }

    public void setHoraSaidaAlmoco(String horaSaidaAlmoco) {
        HoraSaidaAlmoco = horaSaidaAlmoco;
    }

    public String getHorarioSaida() {
        return horarioSaida;
    }

    public void setHorarioSaida(String horarioSaida) {
        this.horarioSaida = horarioSaida;
    }
}
