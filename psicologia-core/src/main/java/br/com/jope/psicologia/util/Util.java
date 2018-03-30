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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util implements Serializable {

	private static final long serialVersionUID = 2125689433135450051L;
	private static Logger logger = Logger.getLogger(Util.class.getName());
	private static final String ZERO = "0";
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String CARACTER = "!@#$%&*()_+";
	private static final String DIGITS = "0123456789";
	private static final String ALPHANUM = UPPER + DIGITS + CARACTER;
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
		return obj == null;
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
		}else if(obj.isEmpty()){
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
		}else  if(obj.isEmpty()){
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
            StringBuilder hashtext = new StringBuilder(number.toString(16));

            while (hashtext.length() < 32) {
            	hashtext.append("0").append(hashtext);
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String getRandomSenha() {
    	int length = TAMANHO_SENHA;
        StringBuilder result = new StringBuilder();
        Random rand = new Random();
        result.append(UPPER.charAt(rand.nextInt(UPPER.length())));
        while(length-1 > 0) {
            rand = new Random();
            result.append(String.valueOf(ALPHANUM.charAt(rand.nextInt(ALPHANUM.length()))).toLowerCase());
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
        	logger.log(Level.SEVERE, e.getMessage());
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
    
}
