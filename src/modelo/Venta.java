package modelo;

import modelo.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Venta {
    private Date fechaVenta;
    private int idVenta;
    private List<Producto> productosVendidos;
    private float total;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public List<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(List<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }
    
    public Boolean guardarVenta(Venta venta){
        Boolean validacion = false;
        Connection conexion = Conexion.getInstance();
        DateFormat hourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            conexion.setAutoCommit(false);
            PreparedStatement insert = conexion.prepareStatement("insert into venta (fecha,total) values (?,?)",Statement.RETURN_GENERATED_KEYS);
            insert.setString(1,  hourDateFormat.format(venta.getFechaVenta()));
            insert.setFloat(2, venta.getTotal());
            insert.executeUpdate();
            ResultSet result = insert.getGeneratedKeys();
            int id = 0;
            if(result.next()){
                id = result.getInt(1);
            }else{
                throw new SQLException("Field was not insert");
            }
            
            insert.close();
            for(Producto producto: venta.getProductosVendidos()){
                PreparedStatement updateProducto = conexion.prepareStatement("update Producto set cantidad = ? where nombre = ?");
                
                producto.setCantidad(producto.getCantidad() - producto.getCantidadVendida());
                producto.setCantidadVendida(1);
                updateProducto.setInt(1, producto.getCantidad());
                updateProducto.setString(2, producto.getNombre());
                updateProducto.executeUpdate();
                updateProducto.close();
                
                PreparedStatement insertProducto = conexion.prepareStatement("insert into venta_producto values (?,?,?,?)");
                
                insertProducto.setInt(1, id);
                insertProducto.setString(2, producto.getNombre());
                insertProducto.setInt(3, producto.getCantidadVendida());
                insertProducto.setFloat(4, producto.getSubtotal());
                
                insertProducto.execute();
                insertProducto.close();
            }
            
            conexion.commit();
            validacion = true;
        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }finally{
            try {
                if(conexion != null && !conexion.isClosed()){
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
        return validacion;
    }
}
