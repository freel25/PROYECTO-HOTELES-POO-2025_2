package Modelo;


import java.time.LocalDate;

public class Factura {
    private int idFactura;
    private Estadia estadia; 
    private double totalNoches; 
    private double totalServicios;
    private double totalFinal; 
    private LocalDate fechaEmision; 
    
    public Factura(int idFactura, Estadia estadia, double totalNoches, double totalServicios) {
        this.idFactura = idFactura;
        this.estadia = estadia;
        this.totalNoches = totalNoches;
        this.totalServicios = totalServicios;
        this.totalFinal = totalNoches + totalServicios; 
        this.fechaEmision = LocalDate.now();
    }

    // --- Getters ---
    public int getIdFactura() {
        return idFactura;
    }

    public Estadia getEstadia() {
        return estadia;
    }

    public double getTotalNoches() {
        return totalNoches;
    }

    public double getTotalServicios() {
        return totalServicios;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }
}