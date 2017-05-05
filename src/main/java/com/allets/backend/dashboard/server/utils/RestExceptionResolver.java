package com.allets.backend.dashboard.server.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ExceptionResolver를 커스터마이징. Exception이 발생을 하면 해당 Exception에 해당하는 메세지를 번들에서
 * 찾는다. 만약 없을 경우는 기본 exception 메세지를 출력한다. 그리고 만약 요청이 ajax일 경우는 json 형태로 메세지를 리턴
 * 한다. 기본 상태 코드는 500으로 하고 필요에 따라서 상태 코드를 부여 할수 있다.
 */
public class RestExceptionResolver extends AbstractHandlerExceptionResolver {

    /**
     * The log.
     */
    final Logger log = LoggerFactory.getLogger(RestExceptionResolver.class);

    /**
     * The Constant DEFAULT_ORDER.
     */
    static final int DEFAULT_ORDER = 0;

    static final String DEFAULT_EXCEPTION_MESSAGE_KEY = "backend.dashboard.server.default.exception";

    /**
     * The DEFAULT _ eNCODING.
     */
    static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * The DEFAULT _ cONTENT _ tYPE.
     */
    static final String DEFAULT_CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * The DEFAULT _ eXCEPTION _ mESSAGE _ kEY.
     */
    static final String DEFAULT_EXCEPTION_MESSAGE = "System Error!!";

    @Autowired
    private MessageUtil messageUtil;

    /**
     * Instantiates a new rest exception resolver.
     */
    public RestExceptionResolver() {
        setOrder(DEFAULT_ORDER);
    }

    /**
     * The Converter.
     */
    @Autowired
    MappingJackson2HttpMessageConverter converter;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver
     * #doResolveException(javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse, java.lang.Object,
     * java.lang.Exception)
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {

        // exception factory 생성 RestReponseOject를 생성 한다.
        ErrorMessageFactory<?> factory = new RestErrorMessageFactoryImpl(ex);


        // error print stack
        log.error(factory.getErrorMessage().getMessageKey(), ex);

        ModelAndView mav = new ModelAndView();

        // factory가 null 아닐 경우
        if (factory != null) {

            RestResponseExceptionObject restResponseObject = factory.getErrorMessage();
            response.setStatus(restResponseObject.getStatus());

            try {
                String errorMessage = messageUtil.getMessage(restResponseObject.getMessageKey());
                String errorCode = messageUtil.getMessage(restResponseObject.getMessageKey()+".code");
                restResponseObject.setErrorCode(errorCode);
                restResponseObject.setErrorMessage(errorMessage);
            } catch (Exception e) {
                restResponseObject.setErrorMessage(HttpStatusMessage.SC_500.toString());
            }

            try {

                String contentType = request.getContentType();
                //json일 경우
                if (StringUtils.contains(contentType, "application/json;")) {
                    //메세지 properties 파일에서 exception 메세지를 가져 온다.
                    return handleResponseBody(restResponseObject, request, response);
                } else {
                    mav.setViewName("errors/defaultError");
                    mav.addObject("status", restResponseObject.getStatus());
                    mav.addObject("message", restResponseObject.getHttpMessage());
                    mav.addObject("errorMessage", restResponseObject.getErrorMessage());
                    mav.addObject("errorCode", restResponseObject.getErrorCode());
                }

            } catch (Exception handlerException) {
                logger.warn("Handling of [" + ex.getClass().getName()
                        + "] resulted in Exception", handlerException);
            }
        }

        return mav;
    }

    /**
     * 실제로 Exception에 대하 후처리(동기/비동기 처리)를 한다. (sentry 전송, 로그 파일 생성, http 응답등).
     *
     * @param restResponseObject the rest response object
     * @param webRequest         the web request
     * @return the model and view
     * @throws javax.servlet.ServletException the servlet exception
     * @throws java.io.IOException      Signals that an I/O exception has occurred.
     */
    private ModelAndView handleResponseBody(
            RestResponseExceptionObject restResponseObject, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

        response.setCharacterEncoding(DEFAULT_ENCODING);
        response.setContentType(DEFAULT_CONTENT_TYPE);

        converter.write(restResponseObject, MediaType.APPLICATION_JSON, outputMessage);
        return new ModelAndView();
    }

}
