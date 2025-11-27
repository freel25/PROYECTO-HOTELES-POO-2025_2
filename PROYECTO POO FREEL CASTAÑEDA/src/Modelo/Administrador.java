package Modelo;

public class Administrador extends Empleado {

    public Administrador(String dni, String nombres, String apellidos, 
                         String login, String password) {
        super(dni, nombres, apellidos, login, password);
    }

    @Override
    public String obtenerRol() {
        return "Administrador";
    }
    
}