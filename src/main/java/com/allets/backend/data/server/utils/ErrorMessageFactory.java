package com.allets.backend.data.server.utils;



/**
 * A factory for creating ErrorMessage objects.
 *
 * @param <T> the generic type
 */
public interface ErrorMessageFactory<T extends Exception> {

	/**
	 * 에러 메세지 오브젝트.
	 *
	 * @param ex the ex
	 * @return the error message
	 */
	RestResponseExceptionObject getErrorMessage();

}
