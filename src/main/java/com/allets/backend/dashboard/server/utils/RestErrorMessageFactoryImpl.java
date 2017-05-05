package com.allets.backend.dashboard.server.utils;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthenticatedException;

/**
 * 실제 Exception 메세지를 빌드 한다.
 */
public class RestErrorMessageFactoryImpl implements ErrorMessageFactory<Exception> {
	
	/** The exception. */
	Exception exception;
	
	String errorMessageKey;
	
	/**
	 * 생성자 함수.
	 *
	 * @param exception the exception
	 */
	public RestErrorMessageFactoryImpl(Exception exception) {
		this.exception = exception;
	}

	/* (non-Javadoc)
	 * @see com.allets.framework.exception.ErrorMessageFactory#getErrorMessage(java.lang.Exception)
	 *
	 *
	 * {
 	“status” : “401”,
 	“message” : “login failure”,
 	"errorMessage" : "you didn't get authentication token. please login",
 	“errorCode” : “A30201”
  }
	 */
	@Override
	public RestResponseExceptionObject getErrorMessage() {

		RestResponseExceptionObject exceptionObject = new RestResponseExceptionObject();
		exceptionObject.setStatus(HttpStatusCode.SC_500.getValue());
		exceptionObject.setHttpMessage(HttpStatusMessage.SC_500.toString());

		//사용자 정의 Exception 이면
		if (this.exception instanceof RestRuntimeException) {

			RestRuntimeException customException = (RestRuntimeException) this.exception;
 			exceptionObject.setStatus(customException.getErrorMessage().getHttpStatusCode().getValue());
			exceptionObject.setMessageKey(customException.getErrorMessage().getMessageKey());
 			HttpStatusCode statusCode = customException.getErrorMessage().getHttpStatusCode();

			if (HttpStatusCode.SC_400 == statusCode) {
				exceptionObject.setHttpMessage(HttpStatusMessage.SC_400.toString());
			} else if (HttpStatusCode.SC_401 == statusCode) {
                exceptionObject.setHttpMessage(HttpStatusMessage.SC_401.toString());
            } else if (HttpStatusCode.SC_403 == statusCode) {
                exceptionObject.setHttpMessage(HttpStatusMessage.SC_403.toString());
            } else if (HttpStatusCode.SC_404 == statusCode) {
                exceptionObject.setHttpMessage(HttpStatusMessage.SC_404.toString());
            } else if (HttpStatusCode.SC_410 == statusCode) {
				exceptionObject.setHttpMessage(HttpStatusMessage.SC_410.toString());
			} else if (HttpStatusCode.SC_500 == statusCode) {
				exceptionObject.setHttpMessage(HttpStatusMessage.SC_500.toString());
			}

		}else if (this.exception instanceof AuthenticationException || this.exception instanceof UnauthenticatedException){
            exceptionObject.setStatus(HttpStatusCode.SC_401.getValue());
            exceptionObject.setHttpMessage(HttpStatusMessage.SC_401.toString());
            exceptionObject.setErrorMessage(exception.getMessage());
        }
		
		return exceptionObject;
	}

	public void setErrorMessageKey(String errorMessageKey) {
		this.errorMessageKey = errorMessageKey;
	}
	
	
}
