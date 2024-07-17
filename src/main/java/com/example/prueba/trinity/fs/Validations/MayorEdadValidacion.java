package com.example.prueba.trinity.fs.Validations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import com.example.prueba.trinity.fs.Entity.Cliente;

public class MayorEdadValidacion implements ConstraintValidator<MayorEdad, Cliente> {

    @Override
    public void initialize(MayorEdad constraintAnnotation) {
        // Método inicializador
    }

    @Override
    public boolean isValid(Cliente cliente, ConstraintValidatorContext context) {
        LocalDate fechaNacimiento = cliente.getFechaNacimiento();
        if (fechaNacimiento == null) {
            return false;
        }

        LocalDate fechaActual = LocalDate.now();
        return Period.between(fechaNacimiento, fechaActual).getYears() >= 18;
    }
}
