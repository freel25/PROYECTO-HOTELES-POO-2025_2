package Modelo;

public abstract class Persona {
    private String dni;
    private String nombres;
    private String apellidos;

    // Constructor
    public Persona(String dni, String nombres, String apellidos) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    // Métodos Getters y Setters (solo se muestran algunos por brevedad)
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres){
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    
    
    public abstract String obtenerRol(); 
}