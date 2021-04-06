package com.picpay.softwareengineerchallenge.controller.validation;

import com.picpay.softwareengineerchallenge.exceptions.BadRequestException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomValidator {
    private final static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static void defaultValidateRequest(final Object object) {
        final Set<ConstraintViolation<Object>> contraingViolation = validator.validate(
                object
        );
        verifyContraingViolations(contraingViolation);
    }

    private static void verifyContraingViolations(
            final Set<ConstraintViolation<Object>> contraingViolation) {
        if (!contraingViolation.isEmpty()) {
            throw new BadRequestException(String.format(
                    "Invalid fields %s",
                    contraingViolation.stream().map(violation ->
                            violation.getPropertyPath().toString().concat(": ").concat(violation.getMessage())
                    ).distinct().collect(Collectors.toList())
            ));
        }
    }
}

