/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

/**
 *
 * @author ra2357041
 */

import model.dto.UsuarioDTO;

public interface IUsuarioValidator {
    boolean validar(UsuarioDTO usuario);
    String obterMensagemErro();
    boolean validarTermoPesquisa(String termo);
}