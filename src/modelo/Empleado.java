package modelo;

import modelo.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Empleado {
    //INSERT INTO empleado (numTrabajador, nombre, apellidos, contraseña, tipo) VALUES ('000', 'Administrator', 'Synthesis', 'delunoal4', 1);
    private String numTrabajador;
    private String nombre;
    private String apellidos;
    private String contraseña;
    private String tipo;   //1= gerente, 0= trabajador
    
    public Empleado(){}
    
    public Empleado(String numTrabajador, String nombre, String apellidos, String contraseña, String tipo){
        this.numTrabajador = numTrabajador;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.tipo = tipo;
    }

    public void setNumTrabajador(String numTrabajador) {
        this.numTrabajador = numTrabajador;
    }
    public String getNumTrabajador() {
        return numTrabajador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getApellidos() {
        return apellidos;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public String getContraseña() {
        return contraseña;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String isTipo() {
        return tipo;
    }

    @Override
    public String toString(){
        return "Numero de empleado: "+numTrabajador+"\nNombre: "+nombre+"\nApellidos: "+apellidos+"\nTipo: "+tipo;
    }
    
    public Empleado obtenerEmpleado(String numTrabajador){
        Connection connection = Conexion.getInstance();
        Empleado empleado = null;
        try {
            PreparedStatement query = connection.prepareStatement("select * from empleado where numTrabajador = ?");
            query.setString(1, numTrabajador);
            
            ResultSet result = query.executeQuery();
            if(result.next()){
                empleado = new Empleado();
                empleado.setNumTrabajador(result.getString("numTrabajador"));
                empleado.setTipo(Empleado.obtenerTipoEmpleado(result.getBoolean("tipo")));
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleado;
    }
    
    public static String obtenerTipoEmpleado(Boolean tipo){
        return tipo?"Gerente":"Trabajador";
    }
    
    public Boolean isGerente(){
        return this.tipo.equals("Gerente");
    }
    
}