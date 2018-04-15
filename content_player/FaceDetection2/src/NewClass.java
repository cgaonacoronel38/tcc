
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author tokio
 */
public class NewClass {
    
    public static void main(String[] args) {
        List<Integer> original = new ArrayList<>();
        original.add(1);
        original.add(2);
        original.add(3);
        
        modificarDatos(original);
        
        System.out.println("Longitud " + original.size());
        for (int i = 0; i < original.size(); i++) {
            System.out.println("Valor " + i + ": " + original.get(i));
        }
    }
    
    public static void modificarDatos(List<Integer> lista) {
        lista.remove(1);
    }
}
