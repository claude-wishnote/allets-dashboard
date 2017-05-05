package com.allets.backend.dashboard.server.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 * The Class XSSUtil.
 */
public class XSSUtil {
	
	/**
	 * <p>
	 * description about class
	 * </p>
	 * Sanitize.
	 * 
	 * @param string the string
	 * @return the string
	 */
	public static String sanitize(String string) {

		if (string == null) {
			return "";
		}
		String value = "";
		try{
			value = string.replaceAll("(?i)<script.*?>.*?</script.*?>", "")
					.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "")
					.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
			value = escapeHTML(value);
		}catch(Exception e){
			e.printStackTrace();
		}
		return StringUtils.trimToEmpty(string);
	}
	
	/**
	 * <p>
	 * description about class
	 * </p>
	 * Escape html.
	 * 
	 * @param value the value
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String escapeHTML (String value) throws Exception {
		String str = value;
		str = StringUtils.replace(str, "<", "&lt;");
		str = StringUtils.replace(str, ">", "&gt;");
		return str;
	}
	
	/**
	 * <p>
	 * description about class
	 * </p>
	 * Unescape html.
	 * 
	 * @param value the value
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String unescapeHTML (String value) throws Exception {
		String str = value;
		str = StringUtils.replace(str, "&lt;", "<");
		str = StringUtils.replace(str, "&gt;", ">" );
		return str;
	}
	

	/**
	 * <p>
	 * description about class
	 * </p>
	 * .
	 * 
	 * @param vars the arguments
	 */
	public static void main(String[] vars) throws Exception {
		String str = "<p>체험판</p>";
		System.out.println(sanitize(str));
		System.out.println(unescapeHTML(sanitize(str)));
	}
	
	/**
	 * Builds the random id.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String buildRandomId () {
		
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
}
