package Datos;

import Modelo.*;
import java.time.LocalDate;

public class GestionReservasEstadias{

        
    private Reservacion[] reservas;
    private int cantReservas;
    private final int MAX_RESERVAS = 200;
    
    private Estadia[] estadias;
    private int cantEstadias;
    private final int MAX_ESTADIAS = 200;
    
    private Factura[] facturas;
    private int cantFacturas;
    private final int MAX_FACTURAS = 200;

    private GestionHabitaciones gestionHabitaciones;
    private GestionConsumos gestionConsumos;

    private int nextReservaId = 1;
    private int nextEstadiaId = 1;
    private int nextFacturaId = 1;

    public GestionReservasEstadias(GestionHabitaciones gestionHabitaciones,
                GestionConsumos gestionConsumos) {
        this.reservas = new Reservacion[MAX_RESERVAS];
        this.cantReservas = 0;
        
        this.estadias = new Estadia[MAX_ESTADIAS];
        this.cantEstadias = 0;
        
        this.facturas = new Factura[MAX_FACTURAS];
        this.cantFacturas = 0;
        
        this.gestionHabitaciones = gestionHabitaciones;
        this.gestionConsumos = gestionConsumos;
    }

    public boolean crear(Reservacion reserva) {
        if (cantReservas < MAX_RESERVAS &&
            gestionHabitaciones.hayHabitacionDisponiblePorTipo(reserva.getTipoHabitacionSolicitada())) {
            reserva.setIdReservacion(nextReservaId++);
            this.reservas[cantReservas] = reserva; // Insertar en el array
            this.cantReservas++;
            return true;
        }
        return false;
    }

    public boolean modificar(Reservacion reservaActualizada) {
    
        return false; 
    }

    public boolean eliminar(String id) {
        return false;
    }

    public Reservacion buscarPorId(String id) {
        try {
            int idBusqueda = Integer.parseInt(id);
            for (int i = 0; i < cantReservas; i++) { 
                if (this.reservas[i].getIdReservacion() == idBusqueda) {
                    return this.reservas[i];
}
            }
        } catch (NumberFormatException e) { }
        return null;
    }

    public Reservacion[] listarTodos() { 
        Reservacion[] arrayFinal = new Reservacion[cantReservas];
        System.arraycopy(this.reservas, 0, arrayFinal, 0, cantReservas);
        return arrayFinal;
    }

    
    public Estadia realizarCheckIn(int idReserva, int numeroHabitacion) {
        if (cantEstadias >= MAX_ESTADIAS) return null; 
        
        Reservacion reserva = buscarPorId(String.valueOf(idReserva));
        Habitacion habitacion = gestionHabitaciones.buscarPorId(String.valueOf(numeroHabitacion));
        
        if (reserva == null || habitacion == null || !habitacion.estaDisponible()) return null;
        
        Estadia nuevaEstadia = new Estadia(nextEstadiaId++, reserva, habitacion, LocalDate.now());
        
        this.estadias[cantEstadias] = nuevaEstadia;
        this.cantEstadias++;
        
        habitacion.setEstado("Ocupada"); 
        reserva.setEstado("Confirmada"); 
        
        return nuevaEstadia;
    }
    
    public Estadia buscarEstadiaPorId(int idEstadia) {
        for (int i = 0; i < cantEstadias; i++) {
            if (this.estadias[i].getIdEstadia() == idEstadia) {
                return this.estadias[i];
            }
        }
        return null;
    }
    public Factura realizarCheckOut(int idEstadia) {
        Estadia estadia = buscarEstadiaPorId(idEstadia);
        if (estadia == null || estadia.getEstado().equals("Finalizada") || cantFacturas >= MAX_FACTURAS) return null;
        
        estadia.registrarCheckOut(LocalDate.now());
        estadia.getHabitacionAsignada().setEstado("Sucia");
        
        long noches = estadia.calcularNochesReales();
        double costoHabitacion = noches * estadia.getHabitacionAsignada().getPrecioPorNoche();

        ConsumoServicio[] consumosEstadia = gestionConsumos.obtenerConsumosPorEstadia(idEstadia);
        double costoServicios = 0.0;
        for (ConsumoServicio c : consumosEstadia) {
            costoServicios += c.getCostoTotal();
        }

        Factura factura = new Factura(nextFacturaId++, estadia, costoHabitacion, costoServicios);
        this.facturas[cantFacturas] = factura;
        this.cantFacturas++;
        
        return factura;
    }
    
    
    public Factura[] listarFacturas() {
        Factura[] arrayFinal = new Factura[cantFacturas];
        System.arraycopy(this.facturas, 0, arrayFinal, 0, cantFacturas);
        return arrayFinal;
    }
    
    public Estadia[] listarEstadias() {
        Estadia[] arrayFinal = new Estadia[cantEstadias];
        System.arraycopy(this.estadias, 0, arrayFinal, 0, cantEstadias);
        return arrayFinal;
    }
    
    public double[] generarReporteIngresos(LocalDate inicio, LocalDate fin) {
    double ingresosHabitaciones = 0.0;
    double ingresosServicios = 0.0;
    
    for (int i = 0; i < cantFacturas; i++) {
        Factura f = this.facturas[i];
        
        if (!f.getFechaEmision().isBefore(inicio) && !f.getFechaEmision().isAfter(fin)) {
            ingresosHabitaciones += f.getTotalNoches();
            ingresosServicios += f.getTotalServicios();
        }
    }
    
    return new double[]{ingresosHabitaciones, ingresosServicios}; 
}
}