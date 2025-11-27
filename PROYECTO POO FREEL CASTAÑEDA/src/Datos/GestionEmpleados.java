package Datos;

import Modelo.Empleado;
import Modelo.Administrador;
import Modelo.Recepcionista;

public class GestionEmpleados  {
    private Empleado[] empleados;
    private int cantidadActual;   
    private final int MAX_EMPLEADOS = 25; 

    public GestionEmpleados() {
        this.empleados = new Empleado[MAX_EMPLEADOS];
        this.cantidadActual = 0;
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
 
        crear(new Administrador("1001", "Ana", "Gomez", "admin", "1234"));
        crear(new Recepcionista("2002", "Carlos", "Perez", "recep", "1234"));
    }

    public boolean crear(Empleado empleado) {
        if (cantidadActual < MAX_EMPLEADOS) {
            this.empleados[cantidadActual] = empleado;
            this.cantidadActual++;
            return true;
        }
        return false;
    }

    public boolean modificar(Empleado empleadoActualizado) {
        Empleado e = buscarPorId(empleadoActualizado.getDni());
        if (e != null) {
            e.setNombres(empleadoActualizado.getNombres());
            e.setApellidos(empleadoActualizado.getApellidos());
            return true;
        }
        return false;
    }

    public boolean eliminar(String dni) {
        int indiceAEliminar = -1;
        
        for (int i = 0; i < cantidadActual; i++) {
            if (this.empleados[i].getDni().equals(dni)) {
                indiceAEliminar = i;
                break;
            }
        }
        
        if (indiceAEliminar != -1) {
            for (int i = indiceAEliminar; i < cantidadActual - 1; i++) {
                this.empleados[i] = this.empleados[i + 1];
            }
            
            this.empleados[cantidadActual - 1] = null;
            this.cantidadActual--;
            return true;
        }
        return false;
    }

    public Empleado buscarPorId(String dni) {
        for (int i = 0; i < cantidadActual; i++) {
            if (this.empleados[i].getDni().equals(dni)) {
                return this.empleados[i];
            }
        }
        return null;
}

    public Empleado[] listarTodos() { 
        Empleado[] arrayFinal = new Empleado[cantidadActual];
        System.arraycopy(this.empleados, 0, arrayFinal, 0, cantidadActual);
        return arrayFinal;
    }

    public Empleado autenticar(String login, String password) {
        for (int i = 0; i < cantidadActual; i++) {
            if (this.empleados[i].autenticar(login, password)) {
                return this.empleados[i]; 
            }
        }
        return null;
    }
}