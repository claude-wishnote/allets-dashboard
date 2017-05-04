package com.allets.backend.data.server.config;

import com.allets.backend.data.server.utils.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 기본 REST 설정은 DefaultRestConfig 사용하고. 각 프로젝트 마다 @ComponentScan 재정의하고 프로젝트 추가적인
 * 부분만 @Bean으로 설정 한다. 또한 메서드를 수정할 경우 override 한다.
 */
@ComponentScan(includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)}, basePackages = {"com.allets.backend.data.server.web"})
@EnableWebMvc
@Import({DefaultPropertyConfig.class})
@EnableAspectJAutoProxy
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    final Logger log = LoggerFactory.getLogger(MVCConfig.class);

    /**
     * The Constant LOCAL_CHAGE_PARAM_NAME.
     */
    final static String LOCAL_CHAGE_PARAM_NAME = "lang";

    /**
     * The max upload size.
     */
    @Value("${backend.max.upload.size}")
    long maxUploadSize;

    /* config view translate */
    @Bean
    public DefaultRequestToViewNameTranslator viewNameTranslator() {
        return new DefaultRequestToViewNameTranslator();
    }


    /* config formatters */
    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new StringTrimConvert());
        registry.addConverter(new StringToIntegerConvert());
        registry.addConverter(new StringToBigIntegerConvert());
        registry.addConverter(new StringToBigDecimalConvert());

        super.addFormatters(registry);
    }

    /* config @ResponseBody message converter */
    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(Arrays
                .asList(new MediaType("application", "json", Charset
                        .forName("UTF-8"))));

        converters.add(stringHttpMessageConverter);
        converters.add(jacksonConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    MappingJackson2HttpMessageConverter jacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        converter.setSupportedMediaTypes(ImmutableList.<MediaType>builder()
                .add(MediaType.APPLICATION_JSON)
                .add(MediaType.parseMediaType("text/json"))
                .build());
        return converter;
    }

    /* Internationalization use accept-language */
    @Bean
    public LocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    /* add interceptor */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpLogInterceptor());
    }

    /* config static resources */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("/WEB-INF/resources/html/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/resources/assets/css/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("/WEB-INF/resources/assets/fonts/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/img/**")
                .addResourceLocations("/WEB-INF/resources/assets/img/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/resources/assets/js/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/sound/**")
                .addResourceLocations("/WEB-INF/resources/assets/sound/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/swf/**")
                .addResourceLocations("/WEB-INF/resources/assets/swf/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/jsonDoc/**")
                .addResourceLocations("/WEB-INF/resources/jsonDoc/")
                .setCachePeriod(31556926);
        registry.addResourceHandler("/i18n/**")
                .addResourceLocations("/WEB-INF/resources/assets/i18n/")
                .setCachePeriod(31556926);
    }

    /**
     * config multipart.
     *
     * @return the commons multipart resolver
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(maxUploadSize);
        return resolver;
    }

	/* config viewname resolver */

    @Bean
    public BeanNameViewResolver beanViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public ViewResolver contentNegotiatingViewResolver(
            ContentNegotiationManager manager) throws Exception {

        List<View> resolvers = new ArrayList<View>();

        // download view
        DownloadView downloadView = new DownloadView();
        downloadView.setContentType(MediaType.APPLICATION_OCTET_STREAM
                .toString());
        resolvers.add(downloadView);

        // json view
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        jsonView.setContentType(MediaType.APPLICATION_JSON.toString());
        ObjectMapper objectMapper = JacksonObjectMapperFactoryBean.createObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        jsonView.setObjectMapper(objectMapper);
        resolvers.add(jsonView);

        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setDefaultViews(resolvers);
        resolver.setContentNegotiationManager(manager);
        resolver.setOrder(2);
        return resolver;

    }

    @Bean
    public TilesConfigurer tilesConfigurer() {

        TilesConfigurer config = new TilesConfigurer();
        List<String> definitions = Lists.newArrayList();
        definitions.add("/WEB-INF/tiles/no.layout.xml");
        definitions.add("/WEB-INF/tiles/default.layout.xml");

        String[] array = new String[definitions.size()];

        int loop = 0;
        for (String value : definitions) {
            array[loop] = value;
            loop++;
        }

        config.setDefinitions(array);
        return config;
    }

    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver tilesViewResolver = new TilesViewResolver();
        tilesViewResolver.setOrder(3);
        return tilesViewResolver;
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(4);
        return resolver;
    }

    /* config view controller */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("main");
        registry.addViewController("/accessDeny").setViewName(
                "errors/accessDeny");
    }

    /* config international message properties */
    @Bean
    public List<String> bundleMessageSourceScannerFactory() throws Exception {
        BundleMessageSourceScanner scanner = new BundleMessageSourceScanner();
        return scanner.getResources("classpath*:/META-INF/message/**");
    }

    @Bean
    public ResourceBundleMessageSource messageSource() throws Exception {

        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setDefaultEncoding("UTF-8");
        List<String> bundles = bundleMessageSourceScannerFactory();

        if (bundles != null && bundles.size() > 0) {

            String[] array = new String[bundles.size()];

            int loop = 0;
            for (String element : bundles) {
                array[loop] = element;
                loop++;
            }
            source.setBasenames(array);
        }

        return source;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() throws Exception {
        MessageSourceAccessor accessor = new MessageSourceAccessor(
                messageSource());

        return accessor;
    }

    @Bean
    public MessageUtil messageUtil() throws Exception {

        MessageUtil util = new MessageUtil();
        util.setMessageSourceAccessor(messageSourceAccessor());
        return util;
    }

    /* exception */
    @Bean
    public HandlerExceptionResolver defaultExceptionResolver() {
        AbstractHandlerExceptionResolver resolver = new RestExceptionResolver();
        return resolver;
    }
}
