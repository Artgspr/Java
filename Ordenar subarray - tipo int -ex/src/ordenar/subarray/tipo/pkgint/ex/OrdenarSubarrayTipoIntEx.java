/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ordenar.subarray.tipo.pkgint.ex;

import java.util.Arrays;

/**
 *
 * @author ra2357041
 */
public class OrdenarSubarrayTipoIntEx {

    
    public static void main(String[] args) {
        
        int[] numeros ={1,5,2,3,4};
        System.out.println("\nVetor na ordem de entrada");
        for (int num : numeros)
            System.out.println(num);
        
        Arrays.sort(numeros,0,3);
        System.out.println("\n\nSubArray ordenado");
        for (int num : numeros)
            System.out.println(num);
        
    }
    
}
