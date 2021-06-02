package modelo.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    
    private Conexion(){}
    
    public static Connection getInstance(){
        Connection coneccon = null;
        try{
            if(coneccon==null){
                String bd = "Licoreria";
                String login ="root";
                String password ="";
                String url="jdbc:mysql://localhost/" +bd;
                Class.forName("com.mysql.jdbc.Driver");
                coneccon=DriverManager.getConnection(url,login,password);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return coneccon;
    }
    
}