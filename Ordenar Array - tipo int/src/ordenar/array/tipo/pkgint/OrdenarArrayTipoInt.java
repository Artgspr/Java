 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ordenar.array.tipo.pkgint;

import java.util.Arrays;

/**
 *
 * @author ra2357041
 */
public class OrdenarArrayTipoInt {

   
    public static void main(String[] args) {
        
        int[] numeros ={1,3,2,5,4};
        System.out.println("\nVetor na ordem de entrada");
        for (int num : numeros)
            System.out.println(num);
        
        Arrays.sort(numeros);
        System.out.println("\n\nVetor ordem numerica - Crescente");
        for (int num : numeros)
            System.out.println(num);
    }
    
}
