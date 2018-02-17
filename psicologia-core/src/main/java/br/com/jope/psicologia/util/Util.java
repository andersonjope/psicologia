package br.com.jope.psicologia.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util implements Serializable {

	private static final long serialVersionUID = 2125689433135450051L;
	private static final String ZERO = "0";

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
	
	
}
