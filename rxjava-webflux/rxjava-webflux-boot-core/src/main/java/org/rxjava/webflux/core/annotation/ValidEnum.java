package org.rxjava.webflux.core.annotation;

import org.rxjava.webflux.core.exception.ErrorMessageException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 校验枚举值有效性
 *
 * @author happy on 2018-05-25.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEnum.Validator.class)
public @interface ValidEnum {

    String message() default "value is not enum";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> value();

    class Validator implements ConstraintValidator<ValidEnum, Object> {

        @Override
        public void initialize(ValidEnum enumValue) {
        }

        @Override
        public boolean isValid(Object data, ConstraintValidatorContext constraintValidatorContext) {
            if (null == data) {
                return false;
            }
            return !validEnum(data);
        }

        private boolean validEnum(Object data) {
            Class<?> valueClass = data.getClass();
            try {
                Method valueOf = data.getClass().getDeclaredMethod("valueOf", String.class);
                valueOf.invoke(valueClass, data.toString());
            } catch (IllegalAccessException e) {
                throw ErrorMessageException.of("notFoundEnumValue");
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                return true;
            }
            return false;
        }

    }
}