package Principal;

import Datos.*;
import Vista.GUI_Login;

public class Main {

    public static void main(String[] args) {
        
        System.out.println("--- Inicializando Sistema de Gestión Hotelera POO ---");
        
        GestionHabitaciones geHabitaciones = new GestionHabitaciones();
        GestionEmpleados geEmpleados = new GestionEmpleados();
        GestionHuespedes geHuespedes = new GestionHuespedes();
        GestionServicios geServicios = new GestionServicios();
        GestionConsumos geConsumos = new GestionConsumos();
        
        
        GestionReservasEstadias geReservas= new GestionReservasEstadias(geHabitaciones, geConsumos); 

        System.out.println("Gestores de datos inicializados.");

        
        GUI_Login loginWindow = new GUI_Login(geEmpleados, geReservas, geServicios);
        loginWindow.setVisible(true);
        
        System.out.println("Ventana de Login iniciada.");
        
    }
}
