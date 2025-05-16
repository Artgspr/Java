/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ra2357094
 */
public class Conexao {

    static String stringconexao = "jdbc:postgresql://localhost:5432/BcMVC";
    static String usuario = "postgres";
    static String senha = "caraicarai";

    public Connection getConecta() {
        try {
            return DriverManager.getConnection(stringconexao, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getStringconexao() {
        return stringconexao;
    }

    public static void setStringconexao(String stringconexao) {
        Conexao.stringconexao = stringconexao;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Conexao.usuario = usuario;
    }

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        Conexao.senha = senha;
    }

   
}
