/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ordenar_array;

import java.util.Arrays;
import java.util.Collections;


public class Ordenar_array {

    public static void main(String[] args) {
        String[] empresas={"Yahoo", "Samsung", "Oracle"};
        System.out.println("Vetor na ordem de entrada");
        for (String texto : empresas) // for each
            System.out.println(texto);
        
        Arrays.sort(empresas); //sort serve para ordenar os arrays
        System.out.println("\nVetor ordenado");
        for (String texto : empresas )
            System.out.println(texto);
        
        Arrays.sort(empresas, Collections.reverseOrder()); //collections.reverseOrder() serve para inverter a ordem dos arrays de tipo string *NAO FUNCIONA COM INT*.
        System.out.println("\nVetor ordem alfabética - Decrescente");
        for (String texto : empresas )
            System.out.println(texto);
    }
}
