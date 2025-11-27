package Datos;

import Modelo.Huesped;

public class GestionHuespedes  {
    private Huesped[] huespedes; 
    private int cantidadActual;  
    private final int MAX_HUESPEDES = 500; 

    public GestionHuespedes() {
    this.huespedes = new Huesped[MAX_HUESPEDES];
        this.cantidadActual = 0;
        cargarDatosIniciales();
    }
    private void cargarDatosIniciales() {
        crear(new Huesped("12345678", "Laura", "Díaz", "laura@mail.com"));
        crear(new Huesped("87654321", "Miguel", "Rojas", "987654321"));
    }


    public boolean crear(Huesped huesped) {
        if (cantidadActual < MAX_HUESPEDES) {
            this.huespedes[cantidadActual] = huesped;
            this.cantidadActual++;
            return true;
        }
        return false;
    }

    public boolean modificar(Huesped huespedActualizado) {
        Huesped h = buscarPorId(huespedActualizado.getDni());
        if (h != null) {
            h.setNombres(huespedActualizado.getNombres());
            h.setApellidos(huespedActualizado.getApellidos());
            h.setDatosContacto(huespedActualizado.getDatosContacto());
            return true;
        }
        return false;
    }

    public boolean eliminar(String dni) {
        int indiceAEliminar = -1;
        
        for (int i = 0; i < cantidadActual; i++) {
            if (this.huespedes[i].getDni().equals(dni)) {
                indiceAEliminar = i;
                break;
            }
        }
        
        if (indiceAEliminar != -1) {
            for (int i = indiceAEliminar; i < cantidadActual - 1; i++) {
                this.huespedes[i] = this.huespedes[i + 1];
            }
            
            this.huespedes[cantidadActual - 1] = null;
            this.cantidadActual--;
            return true;
        }
        return false;
    }

    public Huesped buscarPorId(String dni) {
        for (int i = 0; i < cantidadActual; i++) {
            if (this.huespedes[i].getDni().equals(dni)) {
                return this.huespedes[i];
            }
        }
        return null;
    }

    public Huesped[] listarTodos() { 
        Huesped[] arrayFinal = new Huesped[cantidadActual];
        System.arraycopy(this.huespedes, 0, arrayFinal, 0, cantidadActual);
        return arrayFinal;
    }
}