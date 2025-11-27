package Modelo;

import java.time.LocalDate; 

public class Reservacion {
    private int idReservacion;
    private Huesped huesped; 
    private String tipoHabitacionSolicitada; 
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; 

    // Constructor
    public Reservacion(int idReservacion, Huesped huesped, String tipoHabitacionSolicitada, 
                       LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.idReservacion = idReservacion;
        this.huesped = huesped;
        this.tipoHabitacionSolicitada = tipoHabitacionSolicitada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Getters
    public int getIdReservacion() {
        return idReservacion;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public String getTipoHabitacionSolicitada() {
        return tipoHabitacionSolicitada;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }
    
    // Setters (para modificar el estado, por ejemplo)
    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Método de negocio (calcular noches)
    public long calcularNoches() {
        // Calcula la diferencia de días entre la fecha de fin y la fecha de inicio
        return java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }
    
    public void setIdReservacion(int idReservacion) {
        this.idReservacion = idReservacion;
    }
}