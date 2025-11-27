package Vista;

import Datos.GestionReservasEstadias;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GUI_ReporteIngresos extends JFrame {
    
    private GestionReservasEstadias gestionReservasEstadias;
    
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JButton btnGenerarReporte;
    private JTextArea areaResultados;

    public GUI_ReporteIngresos(GestionReservasEstadias gre) {
        this.gestionReservasEstadias = gre;
        
        setTitle("Reporte de Ingresos por Fechas");
        setSize(700, 500); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        initComponents();
        addListeners(); 
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); 
        
        JLabel lblTitulo = new JLabel("Generación de Reporte de Ingresos", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(16.0f));
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel pnlControl = new JPanel(new GridLayout(2, 3, 10, 10));
        
        txtFechaInicio = new JTextField("AAAA-MM-DD");
        txtFechaFin = new JTextField("AAAA-MM-DD");
        btnGenerarReporte = new JButton("Generar Reporte");
        
        pnlControl.add(new JLabel("Fecha Inicio (AAAA-MM-DD):", SwingConstants.RIGHT));
        pnlControl.add(txtFechaInicio);
        pnlControl.add(new JLabel("")); // Espacio
        pnlControl.add(new JLabel("Fecha Fin (AAAA-MM-DD):", SwingConstants.RIGHT));
        pnlControl.add(txtFechaFin);
        pnlControl.add(btnGenerarReporte);

        add(pnlControl, BorderLayout.WEST); 

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaResultados);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    
    private void addListeners() {
        btnGenerarReporte.addActionListener(e -> generarReporte());
    }


    private void generarReporte() {
        String inicioStr = txtFechaInicio.getText();
        String finStr = txtFechaFin.getText();
        
        try {
            LocalDate fechaInicio = LocalDate.parse(inicioStr);
            LocalDate fechaFin = LocalDate.parse(finStr);

            if (fechaInicio.isAfter(fechaFin)) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser posterior a la fecha de fin.", "Error de Rango", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double[] resultados = gestionReservasEstadias.generarReporteIngresos(fechaInicio, fechaFin);
            
            double ingresosHabitaciones = resultados[0];
            double ingresosServicios = resultados[1];
            double total = ingresosHabitaciones + ingresosServicios;
            
            areaResultados.setText(""); 
            areaResultados.append("==================================================\n");
            areaResultados.append("  REPORTE DE INGRESOS GENERADOS\n");
            areaResultados.append("  PERÍODO: " + inicioStr + " hasta " + finStr + "\n");
            areaResultados.append("==================================================\n");
            areaResultados.append(String.format("  Ingresos por Habitaciones: %.2f\n", ingresosHabitaciones));
            areaResultados.append(String.format("  Ingresos por Servicios:    %.2f\n", ingresosServicios));
            areaResultados.append("--------------------------------------------------\n");
            areaResultados.append(String.format("  TOTAL NETO GENERADO:       %.2f\n", total));
            areaResultados.append("==================================================\n");
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use AAAA-MM-DD.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}