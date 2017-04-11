package com.allets.backend.data.server.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.math.BigInteger;

public class StringToBigIntegerConvert implements Converter<String, BigInteger>{

	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
	 */
	@Override
	public BigInteger convert(String source) {
		if(StringUtils.isEmpty(source)) {
			return BigInteger.ZERO;
		}
		return new BigInteger(source);
	}
	
}
