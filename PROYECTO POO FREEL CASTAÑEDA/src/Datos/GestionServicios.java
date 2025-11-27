package Datos;

import Modelo.ServicioAdicional;

public class GestionServicios{

    private ServicioAdicional[] servicios; 
    private int cantidadActual;             
    private final int MAX_SERVICIOS = 20;   

    public GestionServicios() {
        this.servicios = new ServicioAdicional[MAX_SERVICIOS]; 
        this.cantidadActual = 0;
        cargarDatosIniciales();
    }
    
    private void cargarDatosIniciales() {
        crear(new ServicioAdicional(1, "Lavandería", 15.00));
        crear(new ServicioAdicional(2, "Servicio a la habitación", 5.00));
        crear(new ServicioAdicional(3, "Frigobar", 10.00));
        crear(new ServicioAdicional(4, "Desayuno", 8.00));
    }


    public boolean crear(ServicioAdicional servicio) {
        // Validación de espacio
        if (cantidadActual < MAX_SERVICIOS) {
            this.servicios[cantidadActual] = servicio; 
            this.cantidadActual++;                   
            return true;
        }
        return false; 
    }

    public boolean modificar(ServicioAdicional servicioActualizado) {
        ServicioAdicional s = buscarPorId(String.valueOf(servicioActualizado.getIdServicio()));
        if (s != null) {
            s.setNombre(servicioActualizado.getNombre());
            s.setPrecio(servicioActualizado.getPrecio());
            return true;
        }
        return false;
    }

    public boolean eliminar(String id) {
        int idBusqueda = -1;
        try {
            idBusqueda = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }
        
        int indiceAEliminar = -1;
        
        for (int i = 0; i < cantidadActual; i++) {
            if (this.servicios[i].getIdServicio() == idBusqueda) {
                indiceAEliminar = i;
                break;
            }
        }
        
        if (indiceAEliminar != -1) {
            for (int i = indiceAEliminar; i < cantidadActual - 1; i++) {
                this.servicios[i] = this.servicios[i + 1];
            }
            
            this.servicios[cantidadActual - 1] = null;
            this.cantidadActual--;
            return true;
        }
        return false;
       }

    public ServicioAdicional buscarPorId(String id) {
        try {
                int idBusqueda = Integer.parseInt(id);
            for (int i = 0; i < cantidadActual; i++) { 
                if (this.servicios[i].getIdServicio() == idBusqueda) {
                    return this.servicios[i];
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("ID de servicio inválido.");
        }
        return null;
    }

    public ServicioAdicional[] listarTodos() { 
        ServicioAdicional[] arrayFinal = new ServicioAdicional[cantidadActual];
        
        System.arraycopy(this.servicios, 0, arrayFinal, 0, cantidadActual);
        
        return arrayFinal;
    }
}