package controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encript {
	
	private static SecretKeySpec secretKey;
    private static byte[] key;
	
	public static String encriptarCifraCesar(int chave, String texto) {
	    StringBuilder textoCifrado = new StringBuilder();
	    int tamanhoTexto = texto.length();

	    for (int c = 0; c < tamanhoTexto; c++) {
	    	int letraCifradaASCII = ((int) texto.charAt(c)) + (chave - 1);

	        while (letraCifradaASCII > 126) {
	            letraCifradaASCII -= 94;
	        }

	        textoCifrado.append((char) letraCifradaASCII);
	    }

	    return textoCifrado.toString();
	}

	public static String decriptarCifraCesar(int chave, String textoCifrado) {
	    StringBuilder texto = new StringBuilder();
	    int tamanhoTexto = textoCifrado.length();

	    for (int c = 0; c < tamanhoTexto; c++) {
	        int letraDecifradaASCII = ((int) textoCifrado.charAt(c)) - (chave - 1);

	        while (letraDecifradaASCII < 32) {
	            letraDecifradaASCII += 94;
	        }

	        texto.append((char) letraDecifradaASCII);
	    }

	    return texto.toString();
	}
	
    public static String codificarCifraVigenere(String pass,String Mensagem) {
        String sair = "";
        String Abcedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chave = pass;
        char[] chaveEquals = new char[Mensagem.length()];
        char[] Msn = Mensagem.toUpperCase().toCharArray();
        int cont = 0;
        for (int c = 0; c < Mensagem.length(); c++) {
            if (Mensagem.charAt(c) == ' ') {
                c++;
            }
            chaveEquals[c] = chave.charAt(cont);
            cont++;
            if (cont == chave.length()) {
                cont = 0;
            }
        }
        int x = 0, y = 0, z;
        for (int c = 0; c < Mensagem.length(); c++) {
            if (Mensagem.charAt(c) == ' ') {
                sair += " ";
                c++;
            }
            for (int f = 0; f < Abcedario.length(); f++) {
                if (Msn[c] == Abcedario.charAt(f)) {
                    x = f;
                }
                if (chaveEquals[c] == Abcedario.charAt(f)) {
                    y = f;
                }
            }
            z = (x + y) % 26;
            sair += Abcedario.charAt(z);
        }
        return sair;
    }
    
    public static String decodificarCifraVigenere(String pass, String Mensagem) {
        String sair = "";
        String Abcedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String chave = pass;
        char[] chaveEquals = new char[Mensagem.length()];
        char[] Msg = Mensagem.toUpperCase().toCharArray();
        int cont = 0;
        for (int c = 0; c < Mensagem.length(); c++) {
            if (Mensagem.charAt(c) == ' ') {
                c++;
            }
            chaveEquals[c] = chave.charAt(cont);
            cont++;
            if (cont == chave.length()) {
                cont = 0;
            }
        }
        cont = 0;
        int x = 0, y = 0, z;
        for (int c = 0; c < Mensagem.length(); c++) {
            if (Mensagem.charAt(c) == ' ') {
                sair += " ";
                c++;
            }
            for (int f = 0; f < Abcedario.length(); f++) {
                if (Msg[c] == Abcedario.charAt(f)) {
                    x = f;
                }
                if (chaveEquals[c] == Abcedario.charAt(f)) {
                    y = f;
                }
            }
            z = (y - x);

            if (z <= 0) {
                if (z == 0) {
                    sair += "A";
                } else {
                    for (int j = 1; j <= Abcedario.length(); j++) {
                        cont++;
                        if (cont == (z * -1)) {
                            sair += Abcedario.charAt(j);
                            break;
                        }
                    }
                }
            } else {
                for (int i = 25; i >= 0; i--) {
                    cont++;
                    if (cont == z) {
                        sair += Abcedario.charAt(i);
                        break;
                    }
                }
            }

            cont = 0;
        }
        return sair;
    }
    
    //CRIPTOGRAFIA USANDO AES
    
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String codificarAES(String mensagem, String key){
        try
        {
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(mensagem.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decodificarAES(String mensagem, String key){
        try
        {
            setKey(key);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(mensagem)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
	
}
