package com.picpay.softwareengineerchallenge.services;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.picpay.softwareengineerchallenge.controller.response.UserResponse;
import com.picpay.softwareengineerchallenge.domain.User;
import com.picpay.softwareengineerchallenge.repositories.UserMongoTemplate;
import com.picpay.softwareengineerchallenge.repositories.UserMongoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static br.com.six2six.fixturefactory.Fixture.from;
import static com.picpay.softwareengineerchallenge.templates.UserTemplates.*;
import static com.picpay.softwareengineerchallenge.templates.Templates.BASE_PACKAGE;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMongoTemplate userMongoTemplate;
    @Mock
    private UserMongoRepository userMongoRepository;

    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates(BASE_PACKAGE);
    }

    @Test
    public void shouldSearchUserByKeywordSuccessfully() {
        final List<User> users = Arrays.asList(
                from(User.class).gimme(VALID_USER_1),
                from(User.class).gimme(VALID_USER_2),
                from(User.class).gimme(VALID_USER_3)
        );

        when(userMongoTemplate.findByCriteria("diether", 0)).thenReturn(users);

        final List<UserResponse> response = userService.searchUserByKeyword("diether", 1);

        assertEquals("88e57169-1e16-4f52-92c0-3c3214481579", response.get(0).getUuid());
        assertEquals("Diether Bein", response.get(0).getName());
        assertEquals("diether.bein", response.get(0).getUsername());
        assertEquals(Integer.valueOf(2), response.get(0).getRelevanceLevel());

        assertEquals("99e4e36a-9aeb-4f2c-ba5e-7c9b4e03d616", response.get(1).getUuid());
        assertEquals("Diether Sampaio", response.get(1).getName());
        assertEquals("diether.sampaio", response.get(1).getUsername());
        assertEquals(Integer.valueOf(0), response.get(1).getRelevanceLevel());

        assertEquals("498ff55d-d6b6-48df-9f35-94304dc24020", response.get(2).getUuid());
        assertEquals("Diether Negreiros", response.get(2).getName());
        assertEquals("diether.negreiros", response.get(2).getUsername());
        assertEquals(Integer.valueOf(0), response.get(2).getRelevanceLevel());
    }

    @Test
    public void shouldSearchUserByKeywordAndReturnEmpty() {
        when(userMongoTemplate.findByCriteria("menendez", 0)).thenReturn(Collections.emptyList());
        final List<UserResponse> response = userService.searchUserByKeyword("menendez", 1);
        assertEquals(0, response.size());
    }

    @Test
    public void shouldFindUserByUsernameSuccessfully() {
        when(userMongoRepository.findFirstByUsername("diether.bein"))
                .thenReturn(Optional.of(from(User.class).gimme(VALID_USER_1)));
        final Optional<User> user = userService.findUserByUsername("diether.bein");
        assertTrue(user.isPresent());
        assertEquals("88e57169-1e16-4f52-92c0-3c3214481579", user.get().getUuid());
        assertEquals("Diether Bein", user.get().getName());
        assertEquals("diether.bein", user.get().getUsername());
        assertEquals(Integer.valueOf(2), user.get().getRelevanceLevel());
    }

    @Test
    public void shouldFindUserByUsernameAndReturnEmpty() {
        when(userMongoRepository.findFirstByUsername("alex.menendez")).thenReturn(Optional.empty());
        final Optional<User> user = userService.findUserByUsername("alex.menendez");
        assertTrue(user.isEmpty());
    }

}
