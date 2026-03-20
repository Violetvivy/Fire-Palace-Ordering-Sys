package com.fire.firepalaceorderingsys.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

// 自定义JSON验证注解
@Documented
@Constraint(validatedBy = JsonFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidJson {
    String message() default "必须是有效的JSON格式";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
