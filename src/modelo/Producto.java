package modelo;

import modelo.util.Conexion;
import modelo.util.BusquedaProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producto {
    private String nombre;
    private String marca;
    private float precio;
    private Integer cantidad;
    private Integer cantidadVendida;
    private float subtotal;
    private int idProveedor;
    
    public Producto(){
        this.subtotal = 0;
        this.cantidad = 0;
        this.precio = 0;
        this.cantidadVendida = 1;
        this.idProveedor = 1;
    }
    
    public Producto(String nombre, String marca, float precio, int cantidad, int idProveedor){
        this.nombre= nombre;
        this.precio= precio;
        this.marca= marca;
        this.cantidad= cantidad;
        this.cantidadVendida = 1;
        this.idProveedor = idProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public String getMarca() {
        return marca;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString(){
        return "Nombre: "+this.nombre+"\nMarca: "+this.marca+"\nCantidad: "+this.cantidad+"\nPrecio: "+this.precio;
    }

    public Producto obtenerProducto(String nombre) {
        Connection connection = Conexion.getInstance();
        Producto producto = null;
        try {
            PreparedStatement query = connection.prepareStatement("select * from producto where nombre = ?");
            query.setString(1, nombre);
            
            ResultSet result = query.executeQuery();
            if(result.next()){
                producto = new Producto();
                producto.setNombre(result.getString("nombre"));
                
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return producto;
    }
    
    public List<Producto> buscarProductos(String nombre, BusquedaProducto busqueda){
        List<Producto> productos = new ArrayList();  
        Connection connection = Conexion.getInstance();
        Producto producto = null;
        try {
            PreparedStatement query = null;
            
            switch(busqueda){
                case ProductosPorNombre:
                    query = connection.prepareStatement("select * from producto where borrado = 0 and nombre LIKE ? ESCAPE '!'");
                    query.setString(1, "%"+nombre+"%");
                    break;
                case ProductosConInventario:
                    query = connection.prepareStatement("select * from producto where borrado = 0 and cantidad > 0");
                    break;
                case TodosLosProductos:
                    query = connection.prepareStatement("select * from producto");
                    break;
                case TodosLosProductosSinBorrar:
                    query = connection.prepareStatement("select * from producto where borrado = 0");
                    break;
            }
            
            ResultSet result = query.executeQuery();
            while (result.next()){
                producto = new Producto(result.getString("nombre"),result.getString("marca"),result.getFloat("precio"),result.getInt("cantidad"),result.getInt("idProveedor"));
                producto.setSubtotal(result.getFloat("precio"));
                productos.add(producto);
            }
            result.close();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productos;
    }
    
    public Boolean borrarProducto(String nombre){
        Boolean validacion = false;
        
        Connection connection = Conexion.getInstance();
        
        try {
            PreparedStatement deleteProducto = connection.prepareStatement("update producto set borrado = 1 where nombre = ?");
            deleteProducto.setString(1, nombre);
            
            deleteProducto.execute();
            validacion = true;
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                if(connection != null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return validacion;
    }
    
}