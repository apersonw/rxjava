package top.rxjava.starter.web.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import top.rxjava.common.utils.JavaTimeModuleUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author happy
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT()));
    }
}
