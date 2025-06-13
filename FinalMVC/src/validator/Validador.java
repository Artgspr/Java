/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package validator;

/**
 *
 * @author cti
 */
public interface Validador <T> {
    boolean validar(T objeto);
    String obterMensagemErro();
}
