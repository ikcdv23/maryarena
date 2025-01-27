package modelo;

public class Usuario {

    // Atributos privados
    private String dni;
    private String nombre;
    private String apellido;
    private String contraseña;
    private rolUsuario rol;

    // Constructores
    public Usuario() {
        super();
    }

    public Usuario(String dni, String nombre, String apellido, String contraseña, rolUsuario rol) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public rolUsuario getRol() {
        return rol;
    }

    public void setRol(rolUsuario rol) {
        this.rol = rol;
    }
}
