package com.picpay.softwareengineerchallenge.controller.validation;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.picpay.softwareengineerchallenge.controller.request.JwtRequest;
import com.picpay.softwareengineerchallenge.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static br.com.six2six.fixturefactory.Fixture.from;
import static com.picpay.softwareengineerchallenge.templates.JwtRequestTemplates.*;
import static com.picpay.softwareengineerchallenge.templates.Templates.BASE_PACKAGE;
import static com.picpay.softwareengineerchallenge.controller.validation.CustomValidator.*;

@RunWith(SpringRunner.class)
public class CustomValidatorTest {

    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates(BASE_PACKAGE);
    }

    @Test
    public void shouldValidateJwtRequestSuccessfully() {
        defaultValidateRequest(from(JwtRequest.class).gimme(VALID_JWT_REQUEST));
    }

    @Test
    public void shouldThrowBadRequestExceptionForInvalidEmptyJwtRequest() {
        assertThrows(BadRequestException.class, () ->
                defaultValidateRequest(from(JwtRequest.class).gimme(EMPTY_JWT_REQUEST))
        );
    }

    @Test
    public void shouldThrowBadRequestExceptionForInvalidNullableJwtRequest2() {
        assertThrows(BadRequestException.class, () ->
                defaultValidateRequest(from(JwtRequest.class).gimme(NULLABLE_JWT_REQUEST))
        );
    }

    @Test
    public void mustThrowBadRequestForEmptyScheduleRequest() {
        assertThrows(BadRequestException.class, () -> defaultValidateRequest(JwtRequest.builder().build()));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForNullRequestBody() {
        assertThrows(IllegalArgumentException.class, () -> defaultValidateRequest(null));
    }

}
