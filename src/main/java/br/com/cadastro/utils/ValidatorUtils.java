package br.com.cadastro.utils;

/**
 * Classe para validações gerais.
 * @author felipe
 *
 */
 public class ValidatorUtils {
	 
	  public static boolean isEmpty(String campo) {
		 
		 if(campo == null )
			 return true;
		 else if(campo.trim().equals(""))
			 return true;
		 
		 return false;
	 }
	 
	  public static boolean isNotEmpty(String campo) {
		 
		 return campo != null && !campo.trim().equals("");
	 }

}
