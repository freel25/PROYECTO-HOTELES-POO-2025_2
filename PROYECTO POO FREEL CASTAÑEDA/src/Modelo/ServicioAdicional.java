package Modelo;

public class ServicioAdicional {
    private int idServicio;
    private String nombre;
    private double precio;

    // Constructor
    public ServicioAdicional(int idServicio, String nombre, double precio) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    
}