package Vista;

import Datos.GestionHabitaciones;
import Modelo.Habitacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;  
import javax.swing.event.ListSelectionListener;


public class GUI_GestionHabitaciones extends JFrame {
    
    private GestionHabitaciones gestionHabitaciones;
    private JTable tablaHabitaciones; 
    private DefaultTableModel modeloTabla; 

    // --- ATRIBUTOS DE ENTRADA ---
    private JTextField txtNumero;
    private JTextField txtCapacidad;
    private JTextField txtPrecio;
    private JComboBox<String> cmbTipo;
    private JComboBox<String> cmbEstado;
    
    private JButton btnCrearHabitacion;
    private JButton btnModificarHabitacion; 
    private JButton btnEliminarHabitacion; 

    public GUI_GestionHabitaciones(GestionHabitaciones gh) {
        this.gestionHabitaciones = gh;
        
        setTitle("Gestión de Habitaciones (CRUD)");
        setSize(1000, 500); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        initComponents();
        addListeners(); 
        cargarDatosATabla(); 
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); 
        
        JLabel lblTitulo = new JLabel("Módulo CRUD de Habitaciones", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(16.0f));
        add(lblTitulo, BorderLayout.NORTH);
        
        // --- 1. PANEL DE ENTRADA DE DATOS (Izquierda) ---
        JPanel pnlDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Registro / Modificación"));
        
        txtNumero = new JTextField();
        txtCapacidad = new JTextField();
        txtPrecio = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{"Simple", "Doble", "Suite"}); 
        cmbEstado = new JComboBox<>(new String[]{"Limpia", "Sucia", "En Limpieza", "Ocupada"});
        
        pnlDatos.add(new JLabel("Número:"));
        pnlDatos.add(txtNumero);
        pnlDatos.add(new JLabel("Capacidad Máx:"));
        pnlDatos.add(txtCapacidad);
        pnlDatos.add(new JLabel("Precio por Noche:"));
        pnlDatos.add(txtPrecio);
        pnlDatos.add(new JLabel("Tipo:"));
        pnlDatos.add(cmbTipo);
        pnlDatos.add(new JLabel("Estado:"));
        pnlDatos.add(cmbEstado);

        add(pnlDatos, BorderLayout.WEST); 

        String[] columnas = {"Número", "Capacidad", "Precio/Noche", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }; 
        tablaHabitaciones = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel pnlAcciones = new JPanel();
        btnCrearHabitacion = new JButton("Crear Habitación");
        btnModificarHabitacion = new JButton("Modificar Habitación"); 
        btnEliminarHabitacion = new JButton("Eliminar Habitación");   
        
        pnlAcciones.add(btnCrearHabitacion);
        pnlAcciones.add(btnModificarHabitacion);
        pnlAcciones.add(btnEliminarHabitacion);
        add(pnlAcciones, BorderLayout.SOUTH);
    }
    
    
    private void addListeners() {
        btnCrearHabitacion.addActionListener(e -> crearNuevaHabitacion());
        
        btnModificarHabitacion.addActionListener(e -> modificarHabitacionExistente());
        
        btnEliminarHabitacion.addActionListener(e -> eliminarHabitacionSeleccionada()); 
        
        tablaHabitaciones.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && tablaHabitaciones.getSelectedRow() != -1) {
                    cargarCamposDesdeTabla();
                }
            }
        });
    }

    
    private void cargarCamposDesdeTabla() {
        int fila = tablaHabitaciones.getSelectedRow();
        if (fila >= 0) {
            txtNumero.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtCapacidad.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 2).toString());
            cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
            cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 4).toString());
            
            txtNumero.setEnabled(false); 
        }
    }

    private void crearNuevaHabitacion() {
        if (txtNumero.getText().isEmpty() || txtCapacidad.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int numero = Integer.parseInt(txtNumero.getText());
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            String tipo = (String) cmbTipo.getSelectedItem();
            String estado = (String) cmbEstado.getSelectedItem();
            
            Habitacion nuevaHabitacion = new Habitacion(numero, capacidad, precio, tipo, estado);

            if (gestionHabitaciones.crear(nuevaHabitacion)) {
                JOptionPane.showMessageDialog(this, "Habitación registrada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo crear. Array lleno o número de habitación duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Número, Capacidad y Precio deben ser valores numéricos válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarHabitacionExistente() {
        if (txtNumero.isEnabled() || txtNumero.getText().isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Seleccione una habitación de la tabla para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int numero = Integer.parseInt(txtNumero.getText());
            int capacidad = Integer.parseInt(txtCapacidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            String tipo = (String) cmbTipo.getSelectedItem();
            String estado = (String) cmbEstado.getSelectedItem();
            
            Habitacion habitacionActualizada = new Habitacion(numero, capacidad, precio, tipo, estado); 

            if (gestionHabitaciones.modificar(habitacionActualizada)) {
                JOptionPane.showMessageDialog(this, "Habitación modificada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar la habitación.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: Los campos numéricos deben ser válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarHabitacionSeleccionada() {
        String numeroStr = txtNumero.getText(); 

        if (txtNumero.isEnabled() || numeroStr.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Seleccione una habitación de la tabla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar la habitación número: " + numeroStr + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestionHabitaciones.eliminar(numeroStr)) { 
                JOptionPane.showMessageDialog(this, "Habitación eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo eliminar la habitación. Verifique que exista.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNumero.setText("");
        txtCapacidad.setText("");
        txtPrecio.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        txtNumero.setEnabled(true); 
    }

    private void cargarDatosATabla() {
        modeloTabla.setRowCount(0); 
        
        Habitacion[] lista = gestionHabitaciones.listarTodos();
        
        for (Habitacion h : lista) {
            Object[] fila = new Object[5];
            fila[0] = h.getNumero();
            fila[1] = h.getPrecioPorNoche();
            fila[2] = h.getTipo();
            fila[3] = h.getEstado();
            
            modeloTabla.addRow(fila);
        }
    }
}