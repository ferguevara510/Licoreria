package modelo;

import modelo.util.Conexion;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;

public class CRUDEmpleado {
    private final Connection con;
    
    public CRUDEmpleado() {
        con = Conexion.getInstance();
    }
    
     public void nuevoEmpleado(String numTrabajador, String nombre, String apellidos, String contraseña,Boolean tipo){
        try {
            PreparedStatement pstm;
            pstm = con.prepareStatement("insert into empleado(numTrabajador,nombre,apellidos,contrasena,tipo) values(?,?,?,?,?)");
                pstm.setString(1,numTrabajador);
                pstm.setString(2,nombre);
                pstm.setString(3,apellidos);
                pstm.setString(4,contraseña);
                pstm.setBoolean(5,tipo);
                pstm.execute();
                pstm.close();
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            try {
                if(!con.isClosed()){
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    public void editarEmpleado(String numT, Empleado empleado){
        try{
            PreparedStatement query; 
            query = con.prepareStatement("UPDATE empleado SET nombre=?, apellidos= ?, contrasena= ?, tipo = ? WHERE numTrabajador= ?");
            query.setString(1, empleado.getNombre());
            query.setString(2, empleado.getApellidos());
            query.setString(3, empleado.getContraseña());
            query.setBoolean(4, empleado.isGerente());
            query.setString(5, empleado.getNumTrabajador());
                query.executeUpdate();
                query.close();
        } catch (SQLException e) {
            System.out.println(e);
        }finally{
            try {
                if(!con.isClosed()){
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDEmpleado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}