package com.picpay.softwareengineerchallenge.services.auth;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.picpay.softwareengineerchallenge.domain.User;
import com.picpay.softwareengineerchallenge.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.picpay.softwareengineerchallenge.templates.Templates.BASE_PACKAGE;
import static com.picpay.softwareengineerchallenge.templates.UserTemplates.VALID_USER_1;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserDetailsServiceTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserService userService;

    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates(BASE_PACKAGE);
    }

    @Test
    public void shouldLoadUserByUsernameSuccessfully() {
        when(userService.findUserByUsername("diether.bein"))
                .thenReturn(Optional.of(Fixture.from(User.class).gimme(VALID_USER_1)));
        assertNotNull(userDetailsService.loadUserByUsername("diether.bein"));
    }

    @Test
    public void shouldThrowUsernameNotFoundExceptionInvokingLoadUserByUsername() {
        when(userService.findUserByUsername("alex.menendez")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("alex.menendez"));
    }

}
