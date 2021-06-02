package modelo;

import modelo.util.Conexion;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDProducto {
    private final Connection con;
    
    public CRUDProducto() {
        con = Conexion.getInstance();
    }
    
    public void nuevoProducto(String nombre, String marca, float precio, int cantidad, int idProveedor){
        try {
            PreparedStatement pstm; 
            pstm = con.prepareStatement("insert into producto(nombre,marca,precio,cantidad,idProveedor) values(?,?,?,?,?)");
                pstm.setString(1,nombre);
                pstm.setString(2,marca);
                pstm.setFloat(3,precio);
                pstm.setInt(4,cantidad);
                pstm.setInt(5,idProveedor);
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
                Logger.getLogger(CRUDProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void editarProducto(String nombreA, Producto proveedor){
        try{
            PreparedStatement pstm;
            pstm = con.prepareStatement("UPDATE producto SET nombre=?, marca=?, precio=?, cantidad=?, idProveedor = ? WHERE nombre=?");
            pstm.setString(1,proveedor.getNombre());
            pstm.setString(2,proveedor.getMarca());
            pstm.setFloat(3,proveedor.getPrecio());
            pstm.setInt(4,proveedor.getCantidad());
            pstm.setInt(5,proveedor.getIdProveedor());
            pstm.setString(6,proveedor.getNombre());
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
                Logger.getLogger(CRUDProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  

    
}