package montador;

import java.util.ArrayList;

public class Instrucoes {
    
    public static String OP (String inst){
        String [] temp = inst.split (" ");
        return temp [0];
    }
        
    public static void verificando (ArrayList <String> l1, ArrayList <Integer> montado , ArrayList <Labels> labels){
        
        String inst, operacao;
        int resposta = 0;
        
        for(int j = 0; j < l1.size(); j++){
            
            inst = l1.get (j);
            operacao = OP (inst);
            if (PegarInstrucao (operacao) == 1){
                
                resposta = TipoR (operacao, inst);
                
            }else{
                
                if(PegarInstrucao (operacao) == 2)
                   resposta = TipoI (operacao, inst, l1, j);
                else
                    if(PegarInstrucao (operacao) == 3)
                      resposta = TipoJ (operacao, inst, labels);
            }
            if((PegarInstrucao (operacao) != 0 )){ 
              montado.add (resposta);
          }
        }
    }
    
    public static byte PegarInstrucao (String OP){
        
        if (OP.matches("jr") || OP.matches("and") || OP.matches("sll") || OP.matches("xor") || OP.matches("or") ||
            OP.matches("srl")   || OP.matches("sub") || OP.matches("slt") || OP.matches("add")){
            
                return 1;
        }else{
            if(OP.matches("bne")|| OP.matches("andi")|| OP.matches("lui")|| OP.matches("xori")|| OP.matches("ori")||
               OP.matches("sw")|| OP.matches("lw")|| OP.matches("addi")|| OP.matches("beq")|| OP.matches("slti")){
                
                return 2;
            }else{
                if(OP.matches("jal")|| OP.matches("j")){
                    
                    return 3;
                }
            }
        }
        return 0;
    }
    
    public static byte PegarNumFunc (String Op){
        
        switch(Op){
            
            case "sll":
                return 0;
                
            case "srl":
                return 2;
                
            case "jr":
                return 8;
                
            case "add":
                return 32;
                
            case "sub":
                return 34;
                
            case "and":
                return 36;
                
            case "or":
                return 37;
                
            case "xor":
                return 38;
                
            case "slt":
                return 42;
        }
        return -1;
    }
    
    public static byte PegarCodOP (String Op){
        
        switch(Op){
            
            case "j":
                return 2;
                
            case "jal":
                return 3;
                
            case "beq":
                return 4;
                
            case "bne":
                return 5;
                
            case "addi":
                return 8;
                
            case "slti":
                return 10;
                
            case "andi":
                return 12;
                
            case "ori":
                return 13;
                
            case "xori":
                return 14;
                
            case "lui":
                return 15;
                
            case "lw":
                return 35;
                
            case "sw":
                return 43;
        }
        return -1;
    }
    
    public static int Saltos( ArrayList <String> l1, String LabelName, int position){
        int temp = position + 1;
        int j = 0;
        
        while (!(l1.get (temp).matches (LabelName + ":"))){
            j++;
            if (l1.get (temp).endsWith (":"))
                j--;
            temp++;
        }
        return j;
    }
    
    public static int Endereco (String LabelName, ArrayList <Labels> labels){
            int temp = 0, temp2 = 0;
            for (int j = 0; j <labels.size (); j++){
               if(labels.get(j).getNome().matches (LabelName + ":")){
                   temp = labels.get(j).getPos();
                   temp2 = j;
               }    
            }
            if(temp == 0){
                return 0;
                
            }else{
               return (temp - temp2 );
               
            }
    }
    
    public static int TipoR (String OP, String inst){
        int funct = PegarNumFunc (OP), rd, rs, shamt, rt;
        int resposta = 0;
        inst = inst.replace (OP, "");
        
        if(OP.matches("srl") || OP.matches("sll")){
            
            inst = inst.replaceAll(" ", "");
            String[] Inst = inst.split(",");
            rs = 0;
            rd = Registrador.RegistradorEnd (Inst [0]);
            rt = Registrador.RegistradorEnd (Inst [1]);
            shamt = Integer.parseInt (Inst [2]);
        }else{
            
            if(OP.matches("jr")){
                
                shamt = rt = rd = 0;
                inst = inst.replaceAll (" ", "");
                inst = inst.trim ();
                rs = Registrador.RegistradorEnd (inst);
            }else{
                
                inst = inst.replaceAll(" ", "");
                String[] Inst = inst.split(",");
                shamt = 0;
                rd = Registrador.RegistradorEnd (Inst[0]);
                rs = Registrador.RegistradorEnd (Inst[1]);
                rt = Registrador.RegistradorEnd (Inst[2]);
                
            }
        }
        
       resposta = Registrador.TipoR (rs, rt, rd, shamt, funct);
       
       return resposta;
    }
    
    public static int TipoJ (String OP,String inst_label,ArrayList<Labels> labels){
        
        int opCode = PegarCodOP (OP), addr, resposta;
        inst_label = inst_label.replace (OP,"").replaceAll (" ","");
        addr = Endereco (inst_label, labels);
        resposta = Registrador.TipoJ (opCode, addr);
        return resposta;
        
    }
    
    public static int TipoI (String OP, String inst, ArrayList <String> l1, int position){
        
        int opCode = PegarCodOP (OP), rs, rt, imm, resposta;
        inst = inst.replace (OP, "");
        
        if (OP.matches ("lui")){
            
            rs = 0b00000;
            inst = inst.replaceAll (" ", "");
            String[] Inst = inst.split (",");
            rt = Registrador.RegistradorEnd (Inst [0]);
            imm = Integer.parseInt (Inst[1]);
            
        }else{
            
            if(OP.matches ("beq") || OP.matches ("bne")){
                inst = inst.replaceAll (" ", "");
                String[] Inst = inst.split (",");
                imm = Saltos (l1, Inst[2], position);
                rs = Registrador.RegistradorEnd (Inst [0]);
                rt = Registrador.RegistradorEnd (Inst [1]);
               
            }else{
                
                if(OP.matches ("sw") || OP.matches ("lw")){
                    
                    String temp;
                    inst = inst.replaceAll (" ",""); 
                    inst = inst.trim ();
                    String[] Inst = inst.split (",");
                    rt = Registrador.RegistradorEnd (Inst [0]);
                    
                    if(!(Inst[1].contains("("))){
                        rs = 0b00000;
                        imm = Integer.parseInt (Inst [1]);
                    }else{
                        
                        int first = Inst[1].indexOf("("), last = Inst[1].indexOf (")");
                        rs = Registrador.RegistradorEnd (Inst [1].substring(first+1, last));
                        temp = Inst[1].substring (first+1, last);
                        Inst[1] = Inst[1].replace (temp,"").replace ("(", "").replace (")","");
                        
                        if(Inst [1].isEmpty()){
                           imm = 0;
                        }else{
                           imm = Integer.parseInt (Inst [1]);
                        }    
                    }
                }else{
                    
                      inst = inst.replaceAll (" ", "");
                      String[] Inst = inst.split (",");
                      rt = Registrador.RegistradorEnd (Inst [0]);
                      rs = Registrador.RegistradorEnd (Inst [1]);
                      imm = Integer.parseInt (Inst [2]);
                    }
                }
            }
        
        resposta = Registrador.TipoI(opCode,rs,rt,imm);
        
        return resposta;
  }      
}