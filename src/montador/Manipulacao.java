package montador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Manipulacao{
    
    public String TiraComentario(String l1){
        String[] temp = l1.split("#");
        return temp[0];         
    } 
    
    public void Leitura(String path, ArrayList <String> l1, ArrayList <Labels> labels) throws IOException{
        
        try (BufferedReader buff = new BufferedReader(new FileReader(path))) {
            
            String line = buff.readLine();
            int j = 0;
            do{
                line = TiraComentario(line);
                line = line.replaceAll("\t" , "");
                line = line.trim();
                
                if(!line.isEmpty () ){
                    if(line.contains(":")){
                        if(line.endsWith(":")){
                            l1.add(line);
                            labels.add(new Labels(line,j));
                        }else{
                            String aux1 = new String(line.substring(0, line.indexOf(":")+1));                            
                            l1.add(aux1);
                            j++;
                            String aux2 = new String(line.substring(line.indexOf(":")+1,line.length()));
                            aux2 = aux2.trim();                           
                            l1.add(aux2);
                        }
                    }else{
                        l1.add(line);
                    }
                    
                    j++;
                    
                }  
                line = buff.readLine();
                
            } 
            while(line != null);
        }
    }
       
    public void Escrita (String path, ArrayList <Integer> montado) throws IOException{
        try (FileWriter arquivo = new FileWriter(path)) {
            PrintWriter GravarArquivo = new PrintWriter(arquivo);
            String MIF;
            MIF = Registrador.MIF(montado);
            GravarArquivo.println(MIF);
        }
    }
}