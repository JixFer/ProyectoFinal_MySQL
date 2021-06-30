/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_morajixon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author DELL-INS14
 */
public class Conexion {
    String driver="com.mysql.cj.jdbc.Driver";
    String url="jdbc:mysql://localhost:3306/CENTISEG";
    String login="root";
    String clave="";
    public Connection conectar(){
        Connection cn=null;
        try{
            Class.forName(driver);
            cn=DriverManager.getConnection(url, login, clave);
            if(cn!=null){
                System.out.println("Conexión");            
            }
        }
        catch(ClassNotFoundException | SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return cn;
    }
}
