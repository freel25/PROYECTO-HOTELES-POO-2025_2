package Datos;

import Modelo.ConsumoServicio;

public class GestionConsumos{

    private ConsumoServicio[] consumos; 
    private int cantidadActual;       
    private final int MAX_CONSUMOS = 1000; 

    public GestionConsumos() {
        this.consumos = new ConsumoServicio[MAX_CONSUMOS];
        this.cantidadActual = 0;
    }


    public boolean crear(ConsumoServicio consumo) {
        if (cantidadActual < MAX_CONSUMOS) {
            this.consumos[cantidadActual] = consumo;
            this.cantidadActual++;
            return true;
        }
        return false;
    }

    public boolean modificar(ConsumoServicio consumoActualizado) {
        return false;
    }

    public boolean eliminar(String id) {
        int indiceAEliminar = -1;
        try {
            int idBusqueda = Integer.parseInt(id);
            for (int i = 0; i < cantidadActual; i++) {
                if (this.consumos[i].getIdConsumo() == idBusqueda) {
                    indiceAEliminar = i;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        if (indiceAEliminar != -1) {
            for (int i = indiceAEliminar; i < cantidadActual - 1; i++) {
                this.consumos[i] = this.consumos[i + 1];
            }
            
            this.consumos[cantidadActual - 1] = null;
            this.cantidadActual--;
            return true;
        }
        return false;
    }

    public ConsumoServicio buscarPorId(String id) {
        try {
            int idBusqueda = Integer.parseInt(id);
        for (int i = 0; i < cantidadActual; i++) { 
            if (this.consumos[i].getIdConsumo() == idBusqueda) {
                    return this.consumos[i];
            }
        }
        } catch (NumberFormatException e) {
            System.err.println("ID de consumo inválido.");
    }
    return null;
    }

    public ConsumoServicio[] listarTodos() { 
        ConsumoServicio[] arrayFinal = new ConsumoServicio[cantidadActual];
        System.arraycopy(this.consumos, 0, arrayFinal, 0, cantidadActual);
        return arrayFinal;
    }
    
    public ConsumoServicio[] obtenerConsumosPorEstadia(int idEstadia) {
        int contador = 0;
        for (int i = 0; i < cantidadActual; i++) {
            if (this.consumos[i].getEstadia().getIdEstadia() == idEstadia) {
                contador++;
            }
        }
        
        ConsumoServicio[] consumosEstadia = new ConsumoServicio[contador];
        int indiceConsumo = 0;
        
        for (int i = 0; i < cantidadActual; i++) {
            if (this.consumos[i].getEstadia().getIdEstadia() == idEstadia) {
                consumosEstadia[indiceConsumo] = this.consumos[i];
                indiceConsumo++;
            }
        }
        return consumosEstadia;
    }
}