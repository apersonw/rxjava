package top.rxjava.starter.web.converter;

import org.springframework.core.convert.converter.Converter;
import top.rxjava.common.utils.JavaTimeModuleUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime source) {
        return source.format(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT()));
    }
}
