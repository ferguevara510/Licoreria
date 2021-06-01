package modelo;

import modelo.excepcion.CRUDExcepcion;
import modelo.util.BusquedaProveedor;
import modelo.util.SingleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Proveedor {
    private int idProveedor;
    private String direccion;
    private String empresa;
    private String nombre;
    private String numTelefono;

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public void setNumTelefono(String numTelefono) {
        this.numTelefono = numTelefono;
    }

    public boolean registrarProvedor(Proveedor proveedor) throws CRUDExcepcion{
        boolean validacion = false;
        Connection conexion = SingleConnection.getInstance();
        try {
            PreparedStatement query = conexion.prepareStatement("insert into provedor(direccion,empresa,nombre,numTelefono) values(?,?,?,?)");
            query.setString(1,this.direccion);
            query.setString(2,this.empresa);
            query.setString(3,this.nombre);
            query.setString(4,this.numTelefono);
            query.execute();
            query.close();
            validacion = true;
        } catch (SQLException e) {
            throw new CRUDExcepcion("No se pudo guardar un proveedor");
        }finally{
            try {
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return validacion;
    }

    public boolean borrarProvedor(int idProvedor){
        boolean validacion = false;

        Connection conexion = SingleConnection.getInstance();

        try{
            conexion.setAutoCommit(false);
            PreparedStatement deleteProvedor = conexion.prepareStatement("update provedor set borrado = 1 where idProvedor = ?");
            deleteProvedor.setInt(1, idProvedor);
            deleteProvedor.execute();
            deleteProvedor.close();

            PreparedStatement deleteProducto = conexion.prepareStatement("update producto set borrado = 1 where idProvedor = ?");
            deleteProducto.setInt(1, idProvedor);
            deleteProducto.execute();
            deleteProducto.close();
            validacion = true;
            conexion.commit();
        }catch (SQLException exception){
            try {
                conexion.rollback();
            } catch (SQLException throwables) {
                throw new CRUDExcepcion("No se pudo borrar el provedor");
            }
            throw new CRUDExcepcion("No se pudo borrar el provedor");
        }finally {
            try {
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validacion;
    }

    public boolean editarProvedor(){
        boolean validacion;
        Connection conexion = SingleConnection.getInstance();
        try {
            PreparedStatement query = conexion.prepareStatement("update set provedor set direccion = ?,empresa = ?,nombre = ?,numTelefono = ? where idProvedor = ?");
            query.setString(1,this.direccion);
            query.setString(2,this.empresa);
            query.setString(3,this.nombre);
            query.setString(4,this.numTelefono);
            query.setInt(5,this.idProveedor);
            query.execute();
            query.close();
            validacion = true;
        } catch (SQLException e) {
            throw new CRUDExcepcion("No se pudo editar un provedor");
        }finally{
            try {
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return validacion;
    }

    public List<Proveedor> obtenerProvedores(String cadenaBusqueda, BusquedaProveedor busqueda){
        List<Proveedor> provedores = new ArrayList<>();

        Connection conexion = SingleConnection.getInstance();
        Proveedor proveedor = null;

        try {
            PreparedStatement query = null;

            switch (busqueda){
                case BUSCAR_CONTENGA_NOMBRE:
                    query = conexion.prepareStatement("select * from provedor where borrado != 0 and nombre LIKE ? ESCAPE '!'");
                    query.setString(1, "%"+cadenaBusqueda+"%");
                    break;
                default:
                    query = conexion.prepareStatement("select * from provedor");
                    break;
            }

            ResultSet result = query.executeQuery();
            while (result.next()){
                proveedor = new Proveedor();

                proveedor.setIdProveedor(result.getInt("idProvedor"));
                proveedor.setDireccion(result.getString("direccion"));
                proveedor.setEmpresa(result.getString("empresa"));
                proveedor.setNombre(result.getString("nombre"));
                proveedor.setNumTelefono(result.getString("numTelefono"));

                provedores.add(proveedor);
            }
            result.close();
        }catch (SQLException excepcion){
            throw new CRUDExcepcion("Error en la busqueda de los provedores");
        }finally {
            try {
                if(!conexion.isClosed()){
                    conexion.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return provedores;
    }
}
