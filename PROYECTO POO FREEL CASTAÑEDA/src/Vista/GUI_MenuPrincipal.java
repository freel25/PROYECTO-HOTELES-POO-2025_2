package Vista;

import Datos.*;
import Modelo.*;
import javax.swing.*;
import java.awt.GridLayout; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_MenuPrincipal extends JFrame {

    private Empleado empleadoAutenticado;
    private GestionReservasEstadias gestionReservasEstadias;
    private GestionEmpleados gestionEmpleados;
    private GestionHabitaciones gestionHabitaciones;
    
    private GestionHuespedes gestionHuespedes;
    private GestionServicios gestionServicios;
    private GestionConsumos gestionConsumos;
    
    private JButton btnGestionEmpleados;
    private JButton btnGestionHabitaciones;
    private JButton btnGestionServicios;
    private JButton btnReporteIngresos;
    private JButton btnGestionHuespedes;
    private JButton btnCrearReserva;
    private JButton btnCheckIn;
    private JButton btnCheckOut;
    private JButton btnRegistrarConsumo;
    private JButton btnReporteOcupacion; 


    public GUI_MenuPrincipal(Empleado empleado, 
                             GestionReservasEstadias gre, 
                             GestionEmpleados ge, 
                             GestionHabitaciones geh,
                             GestionHuespedes gh, 
                             GestionServicios gs, 
                             GestionConsumos gc) { 
                                
        this.empleadoAutenticado = empleado;
        this.gestionReservasEstadias = gre;
        this.gestionEmpleados = ge;
        this.gestionHabitaciones = geh;
        this.gestionHuespedes = gh; 
        this.gestionServicios = gs; 
        this.gestionConsumos = gc;
        
        setTitle("Menú Principal - Rol: " + empleado.obtenerRol());
        setSize(1000, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10)); 
        setLocationRelativeTo(null);
        
        initComponents();
        addListeners();
    }
    
    private void initComponents() {
        // 
        add(new JLabel("Bienvenido, " + empleadoAutenticado.getNombres() + ". Tareas disponibles:", SwingConstants.CENTER));
        add(new JLabel(" ", SwingConstants.CENTER)); 
        

        if (empleadoAutenticado instanceof Administrador) {
            
            btnGestionEmpleados = new JButton("1. Gestión de Empleados");
            btnGestionHabitaciones = new JButton("2. Gestión de Habitaciones y Precios");
            btnGestionServicios = new JButton("3. Gestión de Servicios Adicionales");
            btnReporteIngresos = new JButton("4. Reporte de Ingresos");
            
            add(btnGestionEmpleados);
            add(btnGestionHabitaciones);
            add(btnGestionServicios);
            add(btnReporteIngresos);
        }
        
        if (empleadoAutenticado instanceof Recepcionista) {
            
            btnGestionHuespedes = new JButton("1. Gestión de Huéspedes (CRUD)");
            btnCrearReserva = new JButton("2. Crear Nueva Reserva");
            btnCheckIn = new JButton("3. Check-in (Ingreso de Huésped)");
            btnCheckOut = new JButton("4. Check-out y Facturación");
            btnRegistrarConsumo = new JButton("5. Registrar Consumo de Servicio");

            add(btnGestionHuespedes);
            add(btnCrearReserva);
            add(btnCheckIn);
            add(btnCheckOut);
            add(btnRegistrarConsumo);
        }
        
        btnReporteOcupacion = new JButton("Reporte de Ocupación de Habitaciones");
        add(btnReporteOcupacion);
    }
    
    private void addListeners() {
        
        if (empleadoAutenticado instanceof Administrador) {
            
            btnGestionEmpleados.addActionListener(e -> {
                new GUI_GestionEmpleados(gestionEmpleados).setVisible(true);
            });
            
            btnGestionHabitaciones.addActionListener(e -> {
                new GUI_GestionHabitaciones(gestionHabitaciones).setVisible(true); 
            });
            
            btnGestionServicios.addActionListener(e -> {
                new GUI_GestionServicios(gestionServicios).setVisible(true);
            });
            
            btnReporteIngresos.addActionListener(e -> {
                new GUI_ReporteIngresos(gestionReservasEstadias).setVisible(true);
            });
        }   
        };
    
}