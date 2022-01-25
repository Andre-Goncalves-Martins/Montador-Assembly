package montador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Montador {
    
    public static void main(String[] args) throws IOException {
        
        Scanner ler= new Scanner(System.in);
        String Saida;
        String Entrada;
       
        System.out.printf("Digite o arquivo de entrada com o diretorio completo: \n");
        Entrada=ler.nextLine();        
        System.out.printf("Digite o arquivo de saída com o diretorio completo: \n");
        Saida=ler.nextLine();
        if(Saida.isEmpty()){
            Saida="memoria.mif";
        }
        
        ArrayList <Integer> montado = new ArrayList();
        ArrayList <String> Fila = new ArrayList();
        ArrayList <Labels> labels = new ArrayList();
        
        Manipulacao Manip = new Manipulacao();
        Manip.Leitura (Entrada, Fila, labels);
        Instrucoes.verificando (Fila, montado, labels);
        Manip.Escrita (Saida, montado );
    }
}