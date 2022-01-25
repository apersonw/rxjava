package top.rxjava.starter.test.reactive;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import top.rxjava.common.utils.JavaTimeModuleUtils;

import java.time.format.DateTimeFormatter;

/**
 * @author wugang
 */
public class ReactiveTestConfiguration {

    @Bean
    public FormattingConversionService formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
        conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_FORMAT()));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT()));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getTIME_FORMAT()));
        registrar.registerFormatters(conversionService);
        return conversionService;
    }
}
