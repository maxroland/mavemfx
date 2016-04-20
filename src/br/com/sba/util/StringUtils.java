package br.com.sba.util;

/**
 * Utilitarios de Strings
 */
public class StringUtils {

	/**
	 * Verifica se a String a vazia ou composta apenas por espaaos em branco
	 * @param str String a ser verificada
	 * @return true se vazia; false caso contrario
	 */
	public static final boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}
		
		return str.trim().length() == 0;
	}
	
	/**
	 * Retorn o separador de linha usado pelo sistema operacional
	 * @return
	 */
	public static String newLine() {
		return System.getProperty("line.separator");
	}
}
