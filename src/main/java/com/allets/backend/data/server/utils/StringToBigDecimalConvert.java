package com.allets.backend.data.server.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class StringToBigDecimalConvert implements Converter<String, BigDecimal>{

	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public BigDecimal convert(String source) {
		if(StringUtils.isEmpty(source)) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(source);
	}
	
}
