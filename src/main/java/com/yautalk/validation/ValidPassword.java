package com.yautalk.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "La contraseña debe tener mínimo 8 caracteres, una mayúscula, un número y un carácter especial.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
