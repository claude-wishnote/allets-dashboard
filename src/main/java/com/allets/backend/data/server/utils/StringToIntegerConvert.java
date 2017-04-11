package com.allets.backend.data.server.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConvert implements Converter<String, Integer>{

	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public Integer convert(String source) {
		if(StringUtils.isEmpty(source)) {
			return new Integer(0);
		}
		return Integer.valueOf(source);
	}
	
}
