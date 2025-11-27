package Datos;

import Modelo.Habitacion;

public class GestionHabitaciones{
    
    private Habitacion[] habitaciones; 
    private int cantidadActual; 
    private final int MAX_HABITACIONES = 50; 

    public GestionHabitaciones() {
        this.habitaciones = new Habitacion[MAX_HABITACIONES];
        this.cantidadActual = 0;
        cargarDatosIniciales(); 
    }
    
    private void cargarDatosIniciales() {
        crear(new Habitacion(101, 2, 80.00, "Simple", "Limpia"));
        crear(new Habitacion(102, 4, 120.00, "Doble", "Limpia"));
        crear(new Habitacion(201, 2, 90.00, "Simple", "Ocupada"));
        crear(new Habitacion(301, 6, 250.00, "Suite", "Limpia"));
    }



    public boolean crear(Habitacion habitacion) {
        if (cantidadActual < MAX_HABITACIONES) {
            this.habitaciones[cantidadActual] = habitacion;
            this.cantidadActual++;
            return true;
        }
        return false;
    }

    public Habitacion buscarPorId(String numero) {
        try {
            int num = Integer.parseInt(numero);
            for (int i = 0; i < cantidadActual; i++) {
                if (this.habitaciones[i].getNumero() == num) {
                    return this.habitaciones[i];
                }
            }
        } catch (NumberFormatException e) { }
        return null;
    }
    
    public boolean modificar(Habitacion habitacionActualizada) {
        Habitacion h = buscarPorId(String.valueOf(habitacionActualizada.getNumero()));
        if (h != null) {
            h.setEstado(habitacionActualizada.getEstado());
            return true;
        }
        return false;
    }

    public boolean eliminar(String numero) {
        int indiceAEliminar = -1;
        try {
            int numBusqueda = Integer.parseInt(numero);
            for (int i = 0; i < cantidadActual; i++) {
                if (this.habitaciones[i].getNumero() == numBusqueda) {
                    indiceAEliminar = i;
                    break;
                }
            }
        } catch (NumberFormatException e) { }
        
        if (indiceAEliminar != -1) {
            for (int i = indiceAEliminar; i < cantidadActual - 1; i++) {
                this.habitaciones[i] = this.habitaciones[i + 1];
            }
            
            this.habitaciones[cantidadActual - 1] = null;
            this.cantidadActual--;
            return true;
        }
        return false;
    }

    public Habitacion[] listarTodos() { 
        Habitacion[] arrayLleno = new Habitacion[cantidadActual];
        System.arraycopy(this.habitaciones, 0, arrayLleno, 0, cantidadActual);
        return arrayLleno;
    }
    
    
    public boolean hayHabitacionDisponiblePorTipo(String tipo) {
        for (int i = 0; i < cantidadActual; i++) {
            if (this.habitaciones[i].getTipo().equals(tipo) && this.habitaciones[i].estaDisponible()) {
                return true;
            }
        }
        return false;
    }
}