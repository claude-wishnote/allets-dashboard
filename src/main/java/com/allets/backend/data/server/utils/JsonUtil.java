package com.allets.backend.data.server.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

/**
 * JSON 데이터를 처리하는 전용 서비스
 * 
 * <p>
 * 
 * <ul>
 * <li>Object to Json 마샬링
 * <li>Json to Object 언마샬링
 * </ul>.
 *
 * @author tktaeki.kim@partner.samsung.com
 * @since 2015. 2. 25
 */
public class JsonUtil {

	/**
	 * <p>
	 * 오브젝트를 JSON 형태로 마샬링 한다.
	 * </p>
	 * Marshalling json.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String marshallingJson(final Object object) throws Exception {

		return parseJson(false, object);
	}

	/**
	 * 오브젝트를 JSON 데이터로 사람이 보기 편한 형태로 마샬링 한다.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String marshallingJsonWithPretty(final Object object)
			throws Exception {
		return "\n"+parseJson(true, object);
	}

	/**
	 * 실제로 오브젝트를 Json 텍스트로 마샬링 한다.
	 *
	 * @param pretty
	 *            the pretty
	 * @param object
	 *            the object
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String parseJson(boolean pretty, Object object)
			throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.ALWAYS);

		DefaultSerializerProvider provider = new DefaultSerializerProvider.Impl();
		provider.setNullValueSerializer(new NullSerializer());
		objectMapper.setSerializerProvider(provider);

		// 보기 편하게 출력
		if (pretty) {
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		}

		return objectMapper.writeValueAsString(object);
	}

	/**
	 * <p>
	 * JSON 형태의 스트링을 특정 오브젝트로 언마샬링 한다.
	 * </p>
	 * Unmarshalling json.
	 * 
	 * @param jsonText
	 *            the json text
	 * @param valueType
	 *            the value type
	 * @return the t
	 * @throws Exception
	 *             the exception
	 */
	public static <T> T unmarshallingJson(final String jsonText,
			final Class<T> valueType) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String data;
		T t = null;

		data = jsonText.replaceAll("null", "\"\"");
		t = (T) objectMapper.readValue(data, valueType);

		return t;
	}

}
