package Modelo;

public class Recepcionista extends Empleado {

    public Recepcionista(String dni, String nombres, String apellidos, 
                         String login, String password) {
        super(dni, nombres, apellidos, login, password);
    }

    @Override
    public String obtenerRol() {
        return "Recepcionista";
    }
    
}