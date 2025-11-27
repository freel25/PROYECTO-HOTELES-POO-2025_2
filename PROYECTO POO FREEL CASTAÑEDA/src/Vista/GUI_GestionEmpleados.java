package Vista;

import Datos.*;
import Modelo.Empleado;
import Modelo.Administrador;
import Modelo.Recepcionista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI_GestionEmpleados extends JFrame {
    
    private GestionEmpleados gestionEmpleados;
    private JTable tablaEmpleados; 
    private DefaultTableModel modeloTabla; 

    // --- ATRIBUTOS DE ENTRADA (Para Crear/Modificar) ---
    private JTextField txtDni;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtLogin;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRol;
    
    private JButton btnCrearEmpleado;
    private JButton btnModificarEmpleado; 
    private JButton btnEliminarEmpleado; 

    public GUI_GestionEmpleados(GestionEmpleados ge) {
        this.gestionEmpleados = ge;
        
        setTitle("Gestión de Empleados (CRUD)");
        setSize(1000, 500); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        initComponents();
        addListeners(); 
        cargarDatosATabla(); 
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10)); 
        
        JLabel lblTitulo = new JLabel("Módulo CRUD de Empleados", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(16.0f));
        add(lblTitulo, BorderLayout.NORTH);
        
        JPanel pnlDatos = new JPanel(new GridLayout(6, 2, 10, 10));
        pnlDatos.setBorder(BorderFactory.createTitledBorder("Registro / Modificación"));
        
        txtDni = new JTextField();
        txtNombres = new JTextField();
        txtApellidos = new JTextField();
        txtLogin = new JTextField();
        txtPassword = new JPasswordField();
        cmbRol = new JComboBox<>(new String[]{"Administrador", "Recepcionista"}); 
        
        pnlDatos.add(new JLabel("DNI:"));
        pnlDatos.add(txtDni);
        pnlDatos.add(new JLabel("Nombres:"));
        pnlDatos.add(txtNombres);
        pnlDatos.add(new JLabel("Apellidos:"));
        pnlDatos.add(txtApellidos);
        pnlDatos.add(new JLabel("Login:"));
        pnlDatos.add(txtLogin);
        pnlDatos.add(new JLabel("Contraseña:"));
        pnlDatos.add(txtPassword);
        pnlDatos.add(new JLabel("Rol:"));
        pnlDatos.add(cmbRol);

        add(pnlDatos, BorderLayout.WEST); 

        String[] columnas = {"DNI", "Nombres", "Apellidos", "Rol", "Login"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }; 
        tablaEmpleados = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel pnlAcciones = new JPanel();
        btnCrearEmpleado = new JButton("Crear Empleado");
        btnModificarEmpleado = new JButton("Modificar Empleado"); 
        btnEliminarEmpleado = new JButton("Eliminar Empleado");   
        
        pnlAcciones.add(btnCrearEmpleado);
        pnlAcciones.add(btnModificarEmpleado);
        pnlAcciones.add(btnEliminarEmpleado);
        add(pnlAcciones, BorderLayout.SOUTH);
    }
    
    
    private void addListeners() {
        btnCrearEmpleado.addActionListener(e -> crearNuevoEmpleado());
        
        btnModificarEmpleado.addActionListener(e -> modificarEmpleadoExistente());
        
        btnEliminarEmpleado.addActionListener(e -> eliminarEmpleadoSeleccionado()); // <-- CONECTADO
        
        tablaEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && tablaEmpleados.getSelectedRow() != -1) {
                    cargarCamposDesdeTabla();
                }
            }
        });
    }


    private void cargarCamposDesdeTabla() {
        int fila = tablaEmpleados.getSelectedRow();
        if (fila >= 0) {
            txtDni.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombres.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtApellidos.setText(modeloTabla.getValueAt(fila, 2).toString());
            cmbRol.setSelectedItem(modeloTabla.getValueAt(fila, 3).toString());
            txtLogin.setText(modeloTabla.getValueAt(fila, 4).toString());
            
            txtDni.setEnabled(false); 
        }
    }

    private void crearNuevoEmpleado() {
        String dni = txtDni.getText();
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String login = txtLogin.getText();
        String password = new String(txtPassword.getPassword());
        String rolSeleccionado = (String) cmbRol.getSelectedItem();
        
        if (dni.isEmpty() || nombres.isEmpty() || login.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar DNI, Nombres, Login y Contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado nuevoEmpleado = null;
        if (rolSeleccionado.equals("Administrador")) {
            nuevoEmpleado = new Administrador(dni, nombres, apellidos, login, password);
        } else if (rolSeleccionado.equals("Recepcionista")) {
            nuevoEmpleado = new Recepcionista(dni, nombres, apellidos, login, password);
        }

        if (nuevoEmpleado != null && gestionEmpleados.crear(nuevoEmpleado)) {
            JOptionPane.showMessageDialog(this, "Empleado registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosATabla(); 
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se pudo crear empleado. Posiblemente array lleno o DNI duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarEmpleadoExistente() {
        String dni = txtDni.getText(); 
        String nombres = txtNombres.getText();
        String apellidos = txtApellidos.getText();
        String login = txtLogin.getText();
        String password = new String(txtPassword.getPassword());
        String rolSeleccionado = (String) cmbRol.getSelectedItem();

        if (dni.isEmpty() || txtDni.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado de la tabla para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado empleadoActualizado = null;
        if (rolSeleccionado.equals("Administrador")) {
            empleadoActualizado = new Administrador(dni, nombres, apellidos, login, password);
        } else if (rolSeleccionado.equals("Recepcionista")) {
            empleadoActualizado = new Recepcionista(dni, nombres, apellidos, login, password);
        }

        if (empleadoActualizado != null && gestionEmpleados.modificar(empleadoActualizado)) {
            JOptionPane.showMessageDialog(this, "Empleado modificado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarDatosATabla(); 
            limpiarCampos();
            txtDni.setEnabled(true); 
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarEmpleadoSeleccionado() {
        String dni = txtDni.getText(); 

        if (dni.isEmpty() || txtDni.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado de la tabla para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea eliminar al empleado con DNI: " + dni + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestionEmpleados.eliminar(dni)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatosATabla(); 
                limpiarCampos();
                txtDni.setEnabled(true); // Desbloqueamos el DNI
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo encontrar o eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtLogin.setText("");
        txtPassword.setText("");
        cmbRol.setSelectedIndex(0);
        txtDni.setEnabled(true); 
    }

    private void cargarDatosATabla() {
        modeloTabla.setRowCount(0); 
        
        Empleado[] lista = gestionEmpleados.listarTodos();
        
        for (Empleado e : lista) {
            Object[] fila = new Object[5];
            fila[0] = e.getDni();
            fila[1] = e.getNombres();
            fila[2] = e.getApellidos();
            fila[3] = e.obtenerRol(); 
            fila[4] = e.getLogin();
            
            modeloTabla.addRow(fila);
        }
    }
}