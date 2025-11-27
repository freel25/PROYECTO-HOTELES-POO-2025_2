package Vista;

import Datos.*;
import Modelo.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class GUI_Login extends JFrame {

    private GestionEmpleados Empleados;
    private GestionReservasEstadias Reservas;
    private GestionConsumos Consumos;
    private GestionHabitaciones Habitaciones;
    private GestionHuespedes Huespedes;
    private GestionServicios Servicios;

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public GUI_Login(GestionEmpleados ge, GestionReservasEstadias gre, GestionServicios gse) {
        this.Empleados = ge;
        this.Reservas = gre;
        
        setTitle("Hotel POO - Iniciar Sesión");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setLayout(null); 
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setBounds(50, 30, 80, 25);
        add(lblUsuario);

        txtUsuario = new JTextField("admin"); 
        txtUsuario.setBounds(150, 30, 150, 25);
        add(txtUsuario);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 70, 80, 25);
        add(lblPassword);

        txtPassword = new JPasswordField("1234");
        txtPassword.setBounds(150, 70, 150, 25);
        add(txtPassword);

        btnLogin = new JButton("Ingresar");
        btnLogin.setBounds(150, 120, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(this::btnLoginActionPerformed);
    }

    private void btnLoginActionPerformed(ActionEvent evt) {
        String login = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        Empleado empleado = Empleados.autenticar(login, password);

        if (empleado != null) {
            JOptionPane.showMessageDialog(this, "Bienvenido, " + empleado.obtenerRol());
            
            GUI_MenuPrincipal m = new GUI_MenuPrincipal(empleado, Reservas, Empleados, Habitaciones, Huespedes, Servicios, Consumos);
            m.setVisible(true);
            
            this.dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}