package com.example.prueba.trinity.fs.Validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MayorEdadValidacion.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MayorEdad {

    String message() default "El cliente debe ser mayor de edad";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

