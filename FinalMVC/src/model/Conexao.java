package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 

public class Conexao {
    private static final String stringconexao = "jdbc:postgresql://localhost:5432/BcMVC";
    private static final String usuario = "postgres";
    private static final String senha = "postgres";
    
    public Connection conectaBD(){
        try{
            return DriverManager.getConnection(stringconexao,usuario,senha);
            
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
      