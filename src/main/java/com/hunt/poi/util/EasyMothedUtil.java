package com.hunt.poi.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EasyMothedUtil {
	
	
	/**
	 * 
	 * @param str
	 * @param token
	 * @return
	 */
	public static String[] splitByChar(String str, char token) {
		
		if(str == null) {
			return null;
		}
		
		List<String> strlist = new ArrayList<String>();
		int fromIndex = 0;
		String tmpStr = str;
		
		while((fromIndex = tmpStr.indexOf(token, fromIndex)) > -1) {
			strlist.add(tmpStr.substring(0, fromIndex));
			tmpStr = tmpStr.substring(fromIndex + 1);
			fromIndex = 0;
		}
		
		strlist.add(tmpStr);
		
		return strlist.toArray(new String[strlist.size()]);
	}
	
	public static boolean isNumberStr(String num) {
		boolean retbool = Pattern.matches("\\d+", num);		
		return retbool;
	}
	
	public static void main(String[] args) {		
		System.out.println("ddd:" + isNumberStr("33444"));
		
	}

}
