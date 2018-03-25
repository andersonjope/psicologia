package br.com.jope.psicologia.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Util implements Serializable {

	private static final long serialVersionUID = 2125689433135450051L;
	private static final String ZERO = "0";
	private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String lower = ""; //upper.toLowerCase();
	private static final String caracter = "!@#$%&*()_+";
	private static final String digits = "0123456789";
	private static final String alphanum = upper + lower + digits + caracter;
    private static final int TAMANHO_SENHA = 5;
    public static final String FORMATO_DATA_DIA_MES_ANO = "dd/MM/yyyy";

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		if(obj == null){
			return Boolean.TRUE;
		}else
		if(obj instanceof java.lang.String)
			return isEmptyString(obj.toString().trim());
		else
		if( obj instanceof java.lang.Integer)
			return isEmptyInteger(Integer.parseInt(obj.toString()));
		else
		if(obj instanceof java.lang.Double)
			return isEmptyDouble(Double.parseDouble(obj.toString().trim()));
		else
		if(obj instanceof java.lang.Float)
			return isEmptyFloat(Float.parseFloat(obj.toString()));
		else
		if(obj instanceof java.lang.Long)
			return isEmptyLong(Long.parseLong(obj.toString()));
		else
		if(obj instanceof java.math.BigDecimal){
			return isEmptyBigDecimal( retornaBigDecimal(obj) );
		}else
		if(obj instanceof java.util.Collection){
			return isEmptyCollection( ((Collection<?>)obj) );
		}else
		if(obj instanceof java.util.List){
			return isEmptyList( ((List<?>)obj) );
		}else
		if(obj instanceof java.util.Map){
			return isEmptyMap( ((Map<?,?>)obj) );
		}else
		if(obj instanceof java.util.HashMap){
			return isEmptyHashMap( ((HashMap<?,?>)obj) );
		}else
		if(obj instanceof Date){
			return isEmptyDate(obj);
		}
			return Boolean.FALSE;
	}
	
	
	
	private static boolean isEmptyDate(Object obj) {
		if(obj == null){
			return true;
		}
	return false;
}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyString(java.lang.String obj){
		if(obj == null)
			return Boolean.TRUE;
		else
		if(obj.length() == 0)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyInteger(java.lang.Integer obj){
		if(obj == null)
			return Boolean.TRUE;
		else
		if(obj == 0)
			return Boolean.TRUE;
		else
		if(obj < 0)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyDouble(java.lang.Double obj){
		if(obj == 0)
			return Boolean.TRUE;
		else
		if(obj < 0)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyLong(java.lang.Long obj){
		if(obj == 0L)
			return Boolean.TRUE;
		else
		if(obj < 0L)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyFloat(java.lang.Float obj){
		if(obj == 0)
			return Boolean.TRUE;
		else
		if(obj < 0)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}
	
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyBigDecimal(java.math.BigDecimal obj){			
		if (obj == null) {
			return Boolean.TRUE;
		}else if (obj.compareTo(new BigDecimal(ZERO)) == 0) {
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyCollection(java.util.Collection<?> obj){
		if(obj == null){
			return Boolean.TRUE;
		}else
		if(obj.size() == 0){
			return Boolean.TRUE;
		}else
		if(obj.isEmpty()){
			return Boolean.TRUE;
		}else
			return Boolean.FALSE;
	}
	
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyList(java.util.List<?> obj){
		if(obj == null){
			return Boolean.TRUE;
		}else
		if(obj.size() == 0){
			return Boolean.TRUE;
		}else
		if(obj.isEmpty()){
			return Boolean.TRUE;
		}else
			return Boolean.FALSE;
	}
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyMap(java.util.Map<?, ?> obj){
		if(obj == null){
			return Boolean.TRUE;
		}else
		if(obj.size() == 0){
			return Boolean.TRUE;
		}else
		if(obj.isEmpty()){
			return Boolean.TRUE;
		}else
			return Boolean.FALSE;
	}
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Boolean isEmptyHashMap(java.util.HashMap<?, ?> obj){
		if(obj == null){
			return Boolean.TRUE;
		}else
		if(obj.size() == 0){
			return Boolean.TRUE;
		}else
		if(obj.isEmpty()){
			return Boolean.TRUE;
		}else
			return Boolean.FALSE;
	}
	
	/**
	 * Tranforma um Object em BigDecimal.
	 * 
	 * @param obj
	 * @return
	 */
	private static BigDecimal retornaBigDecimal(Object obj){
		if(obj == null){
			return new BigDecimal(ZERO);
		}else{
			String o = obj.toString().replace(",", ".");
			
			if(new BigDecimal(o).compareTo( new BigDecimal(ZERO)) == 0){
				return new BigDecimal(ZERO);
			}else{
				return new BigDecimal(o);
			}
		}
	}

	public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getRandomSenha() {
    	int length = TAMANHO_SENHA;
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        result.append(upper.charAt(rand.nextInt(upper.length())));
        while(length-1 > 0) {
            rand = new Random();
            result.append(String.valueOf(alphanum.charAt(rand.nextInt(alphanum.length()))).toLowerCase());
            length--;
        }
        return result.toString();
     }	

    public static Date converteStringToDate(String formato, String dateInString) {
    	SimpleDateFormat formatter = new SimpleDateFormat(formato);
    	Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }	
        return date;
    }

    public static String removeCaracteres(String texto) {
		texto = texto.replace(".", "");
		texto = texto.replace("-", "");
		texto = texto.replace("/", "");
		texto = texto.replace("(", "");
		texto = texto.replace(")", "");
		texto = texto.replace(" ", "");
		texto = texto.replaceAll("_", "");
		return texto;
	}
    
    public static void main(String[] args) {
		System.out.println(encrypt("123"));
	}
    
}
