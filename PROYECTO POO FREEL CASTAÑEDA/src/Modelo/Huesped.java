package Modelo;


public class Huesped extends Persona {
    private String datosContacto; // Teléfono o Email
    
    // Constructor
    public Huesped(String dni, String nombres, String apellidos, String datosContacto) {
        super(dni, nombres, apellidos);
        this.datosContacto = datosContacto;
    }

    // Implementación del método abstracto de Persona
    @Override
    public String obtenerRol() {
        return "Huesped";
    }

    // Getters y Setters
    public String getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(String datosContacto) {
        this.datosContacto = datosContacto;
    }
}