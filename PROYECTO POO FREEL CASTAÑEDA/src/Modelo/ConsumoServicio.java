package Modelo;


import java.time.LocalDateTime; 

public class ConsumoServicio {
    private int idConsumo;
    private Estadia estadia; 
    private ServicioAdicional servicio; 
    private int cantidad;
    private double costoUnitario;
    private double costoTotal;
    private LocalDateTime fechaConsumo; 

    // Constructor
    public ConsumoServicio(int idConsumo, Estadia estadia, ServicioAdicional servicio, 
                           int cantidad, double costoUnitario) {
        this.idConsumo = idConsumo;
        this.estadia = estadia;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.costoTotal = calcularCostoTotal();
        this.fechaConsumo = LocalDateTime.now(); // Registra el momento de la creación
    }

    
    private double calcularCostoTotal() {
        return this.cantidad * this.costoUnitario;
    }
    
    // --- Getters ---
    public int getIdConsumo() {
        return idConsumo;
    }

    public Estadia getEstadia() {
        return estadia;
    }

    public ServicioAdicional getServicio() {
        return servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getCostoTotal() {
        return costoTotal;
    }
}