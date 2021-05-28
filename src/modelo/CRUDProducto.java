package modelo;

import modelo.util.SingleConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDProducto {
    private final Connection con;
    
    public CRUDProducto() {
        con = SingleConnection.getInstance();
    }
    
    public void nuevoProducto(String nombre, String marca, float precio, int cantidad){
        try {
            PreparedStatement pstm; 
            pstm = con.prepareStatement("insert into producto(nombre,marca,precio,cantidad) values(?,?,?,?)");
                pstm.setString(1,nombre);
                pstm.setString(2,marca);
                pstm.setFloat(3,precio);
                pstm.setInt(4,cantidad);
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
    
    public void editarProducto(String nombreA, Producto pr){
        try{
            PreparedStatement pstm; 
            System.out.println("UPDATE producto SET nombre='"+pr.getNombre()+"', marca='"+pr.getMarca()+
                    "', precio="+pr.getPrecio()+", cantidad="+pr.getCantidad()+" WHERE nombre='"+nombreA+"'");
            pstm = con.prepareStatement("UPDATE producto SET nombre='"+pr.getNombre()+"', marca='"+pr.getMarca()+
                    "', precio="+pr.getPrecio()+", cantidad="+pr.getCantidad()+" WHERE nombre='"+nombreA+"'");
                pstm.execute();
                //cierro el pstm para evitar gasto de recursos
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