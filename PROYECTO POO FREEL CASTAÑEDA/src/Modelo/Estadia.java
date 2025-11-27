package Modelo;

import java.time.LocalDate;

public class Estadia {
    private int idEstadia;
    private Reservacion reservacion; 
    private Habitacion habitacionAsignada; 
    private LocalDate fechaCheckIn;
    private LocalDate fechaCheckOut; 
    private String estado; 
    
    public Estadia(int idEstadia, Reservacion reservacion, Habitacion habitacionAsignada, 
                   LocalDate fechaCheckIn) {
        this.idEstadia = idEstadia;
        this.reservacion = reservacion;
        this.habitacionAsignada = habitacionAsignada;
        this.fechaCheckIn = fechaCheckIn;
        this.estado = "Activa"; 
    }

    // --- Getters ---
    public int getIdEstadia() {
        return idEstadia;
    }

    public Reservacion getReservacion() {
        return reservacion;
    }

    public Habitacion getHabitacionAsignada() {
        return habitacionAsignada;
    }
    
    public LocalDate getFechaCheckIn() {
        return fechaCheckIn;
    }
    
    public LocalDate getFechaCheckOut() {
        return fechaCheckOut;
    }

    public String getEstado() {
        return estado;
    }

    
    public void registrarCheckOut(LocalDate fechaCheckOut) {
        this.fechaCheckOut = fechaCheckOut;
        this.estado = "Finalizada";
    }

    
    public long calcularNochesReales() {
        if (fechaCheckOut != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(fechaCheckIn, fechaCheckOut);
        }
        return java.time.temporal.ChronoUnit.DAYS.between(fechaCheckIn, LocalDate.now());
    }
}