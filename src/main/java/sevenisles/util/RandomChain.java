package sevenisles.util;

public class RandomChain {
	
	public static String randomChain (Integer length) {
		String caracteres;
        StringBuilder cadena;
        
        caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"; 
        cadena = new StringBuilder(length); 
        for (int i = 0; i < length; i++) { 
            int myindex = (int)(caracteres.length() * Math.random()); 
            cadena.append(caracteres.charAt(myindex)); 
        } 

        return cadena.toString(); 
    } 

}
