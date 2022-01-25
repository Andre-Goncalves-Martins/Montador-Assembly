package montador;
import java.util.ArrayList;


public class Registrador {
    
    public static String intHex(int valor, int tam){
        
        String resp = "";
        
        for(int i = 0; i <tam; i++){
            int aux = (0x0f & valor);
            if(aux < 10){
                resp = ((char)(aux + '0')) + resp;
            }else{
                resp = ((char)(aux - 10 + 'A'))+ resp;
            }
            valor = valor >> 4;
        }
        return resp;
    }
    
    public static int TipoR (int rs, int rt, int rd, int shamt, int funct){
        int resposta = ((0x1f & rs)<<21)|((0x1f & rt)<<16)|((0x1f & rd)<<11)|((0x1f & shamt)<<6)|((0x3f & funct));
        
        return resposta;
    }
    
    public static int TipoJ (int op, int addr){
        int resposta = ((0x3f & op)<<26)|(0x3ffffff & addr);
        
        return resposta;
    }
    
    public static int TipoI (int op, int rs, int rt, int imm){
        int resposta = ((0x3f & op)<<26)|((0x1f &  rs)<<21)|((0x1f & rt)<<16)|(0xffff & imm);
        
        return resposta;
    }
    
    public static String MIF (ArrayList <Integer> instruction){
       
       String resp = "WITH = 8; \r\n"
                   + "DEPTH = 512; \r\n"
                   + "\n"
                   + "ADDRESS_RADIX = HEX; \r\n"
                   + "DATA_RADIX = HEX; \r\n"
                   + "TEXT_AT_ADDRESS 0; \r\n"
                   + "\r\n"
                   + "CONTENT BEGIN \r\n";
               
       for (int j = 0; j < 128; j++){
           
           if(instruction.size () > j){
               resp = resp + BigEnd (j, instruction.get(j));
           }else{
               resp = resp + BigEnd (j, 0);
           }
       }
       
       resp = resp + "END;";
       return resp;
    }
    
    public static String BigEnd(int id, int instruction){
        
        int vetor[] = new int [4];
        
        String resposta = "";
        
        for (int j = 0; j <4; j ++){
            
           vetor [j] = instruction & 0xff;
           instruction = instruction >> 8;
        }
        
        for(int i = 0; i < 4; i++){
            
            resposta += "\t" + intHex(id*4 +i,3) + "\t: " + intHex(vetor[3-i],2) + ";\r\n";
        }
        return resposta;
    }
    
    public static int RegistradorEnd(String regis){
        
        switch(regis){
            case "$zero":
                return 0;
                
            case "$at":
                return 1;
                
            case "$v0":
                return 2;
                
            case "$v1":
                return 3;
                
            case "$a0":
                return 4;
                
            case "$a1":
                return 5;
                
            case "$a2":
                return 6;
                
            case "$a3":
                return 7;
                
            case "$t0":
                return 8; 
                
            case "$t1":
                return 9;
                
            case "$t2":
                return 10;
                
            case "$t3":
                return 11;
                
            case "$t4":
                return 12;
                
            case "$t5":
                return 13;
                
            case "$t6":
                return 14;
                
            case "$t7":
                return 15; 
                
            case "$s0":
                return 16;
                
            case "$s1":
                return 17;
                
            case "$s2":
                return 18;
                
            case "$s3":
                return 19;
                
            case "$s4":
                return 20;
                
            case "$s5":
                return 21;
                
            case "$s6":
                return 22;
                
            case "$s7":
                return 23;
                
            case "$t8":
                return 24;
                
            case "$t9":
                return 25;
                
            case "$k0":
                return 26;
                
            case "$k1":
                return 27;
                
            case "$gp":
                return 28;
                
            case "$sp":
                return 29;
                
            case "$fp":
                return 30;
                
            case "$ra":
                return 31;
        }
        return -1;
    }
}
