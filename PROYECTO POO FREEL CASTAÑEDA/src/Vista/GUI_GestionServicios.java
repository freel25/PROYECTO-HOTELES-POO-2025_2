package Vista;

import Datos.GestionServicios;
import Modelo.ServicioAdicional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

public class GUI_GestionServicios extends JFrame {
    
    private GestionServicios gestionServicios;
    private JTable tablaServicios; 
    private DefaultTableModel modeloTabla; 

    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    
    private JButton btnCrearServicio;
    private JButton btnModificarServicio; 
    private JButton btnEliminarServicio; 

    public GUI_GestionServicios(GestionServicios gs) {
        this.gestionServicios = gs;
        
        setTitle("Gestión de Servicios Adicionales (CRUD)");
        setSize(800, 500); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        initComponents();
        addListeners(); 
        cargarDatosATabla(); 
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); 
        
        JLabel lblTitulo = new JLabel("Módulo CRUD de Servicios Adicionales", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(16.0f));
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel pnlDatos = new JPanel(new GridLayout(3, 2, 10, 10));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Registro / Modificación"));
        
        txtId = new JTextField();
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        
        pnlDatos.add(new JLabel("ID Servicio:"));
        pnlDatos.add(txtId);
        pnlDatos.add(new JLabel("Nombre:"));
        pnlDatos.add(txtNombre);
        pnlDatos.add(new JLabel("Precio:"));
        pnlDatos.add(txtPrecio);

        add(pnlDatos, BorderLayout.WEST); 

        String[] columnas = {"ID", "Nombre", "Precio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }; 
        tablaServicios = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaServicios);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel pnlAcciones = new JPanel();
        btnCrearServicio = new JButton("Crear Servicio");
        btnModificarServicio = new JButton("Modificar Servicio"); 
        btnEliminarServicio = new JButton("Eliminar Servicio");   
        
        pnlAcciones.add(btnCrearServicio);
        pnlAcciones.add(btnModificarServicio);
        pnlAcciones.add(btnEliminarServicio);
        add(pnlAcciones, BorderLayout.SOUTH);
    }
    
    
    private void addListeners() {
        btnCrearServicio.addActionListener(e -> crearNuevoServicio());
        btnModificarServicio.addActionListener(e -> modificarServicioExistente());
        btnEliminarServicio.addActionListener(e -> eliminarServicioSeleccionado()); 
        
        tablaServicios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && tablaServicios.getSelectedRow() != -1) {
                    cargarCamposDesdeTabla();
                }
            }
        });
    }

    
    private void cargarCamposDesdeTabla() {
        int fila = tablaServicios.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtPrecio.setText(modeloTabla.getValueAt(fila, 2).toString());
            
            txtId.setEnabled(false); 
        }
    }

    private void crearNuevoServicio() {
        if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int id = Integer.parseInt(txtId.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            String nombre = txtNombre.getText();
            
            ServicioAdicional nuevoServicio = new ServicioAdicional(id, nombre, precio);

            if (gestionServicios.crear(nuevoServicio)) {
                JOptionPane.showMessageDialog(this, "Servicio registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo crear. Array lleno o ID duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: ID y Precio deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarServicioExistente() {
        if (txtId.isEnabled() || txtId.getText().isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            String nombre = txtNombre.getText();
            
            ServicioAdicional servicioActualizado = new ServicioAdicional(id, nombre, precio); 

            if (gestionServicios.modificar(servicioActualizado)) {
                JOptionPane.showMessageDialog(this, "Servicio modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error: ID y Precio deben ser números.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarServicioSeleccionado() {
        String idStr = txtId.getText(); 

        if (txtId.isEnabled() || idStr.isEmpty()) { 
            JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar el servicio ID: " + idStr + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestionServicios.eliminar(idStr)) { 
                JOptionPane.showMessageDialog(this, "Servicio eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo eliminar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtId.setEnabled(true); 
    }

    private void cargarDatosATabla() {
        modeloTabla.setRowCount(0); 
        
        ServicioAdicional[] lista = gestionServicios.listarTodos();
        
        for (ServicioAdicional s : lista) {
            Object[] fila = new Object[3];
            fila[0] = s.getIdServicio();
            fila[1] = s.getNombre();
            fila[2] = s.getPrecio();
            
            modeloTabla.addRow(fila);
        }
    }
}