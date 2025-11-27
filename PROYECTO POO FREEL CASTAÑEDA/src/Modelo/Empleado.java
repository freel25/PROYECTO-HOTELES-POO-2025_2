package Modelo;

public abstract class Empleado extends Persona {
    private String login;
    private String password;
    
    public Empleado(String dni, String nombres, String apellidos, 
                    String login, String password) {
        super(dni, nombres, apellidos); // Llama al constructor de Persona
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login){
        this.login = login;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public boolean autenticar(String user, String pass) {
        return this.login.equals(user) && this.password.equals(pass);
    }
}