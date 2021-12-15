package top.rxjava.starter.webflux.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.support.DefaultMessageSourceResolvable;

@JsonIgnoreProperties("code")
class DefaultError extends DefaultMessageSourceResolvable {
    DefaultError(String code) {
        super(code);
    }

    public DefaultError() {
        super("");
    }
}
