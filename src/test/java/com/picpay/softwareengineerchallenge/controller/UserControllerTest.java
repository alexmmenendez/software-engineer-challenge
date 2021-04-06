package com.picpay.softwareengineerchallenge.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.picpay.softwareengineerchallenge.configs.JwtAuthenticationEntryPointConfig;
import com.picpay.softwareengineerchallenge.controller.request.JwtRequest;
import com.picpay.softwareengineerchallenge.services.UserService;
import com.picpay.softwareengineerchallenge.services.auth.JwtAuthenticationService;
import com.picpay.softwareengineerchallenge.services.auth.UserDetailsServiceImpl;
import com.picpay.softwareengineerchallenge.utils.JwtTokenUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static com.picpay.softwareengineerchallenge.templates.Templates.BASE_PACKAGE;
import static com.picpay.softwareengineerchallenge.templates.JwtRequestTemplates.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenUtils jwtTokenUtils;
    @MockBean
    private JwtAuthenticationService jwtAuthenticationService;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPointConfig jwtAuthenticationEntryPointConfig;

    private static final Integer validPage = 1;
    private static final String validKeyword = "diether";

    @BeforeAll
    public static void beforeAll() {
        FixtureFactoryLoader.loadTemplates(BASE_PACKAGE);
    }

    @Test
    public void shouldSearchUserByKeywordSuccessfully() throws Exception {
        mockMvc.perform(get("/api/user")
                .param("keyword", validKeyword)
                .param("page", String.valueOf(validPage))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400SearchUserByKeywordBecauseMissingPageRequestParam() throws Exception {
        final MvcResult result = mockMvc.perform(get("/api/user")
                .param("keyword", validKeyword)
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(
                "{\"status\":400,\"message\":\"Required Integer parameter 'page' is not present\"}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void shouldReturn400SearchUserByKeywordBecauseMissingKeywordRequestParam() throws Exception {
        final MvcResult result = mockMvc.perform(get("/api/user")
                .param("page", String.valueOf(validPage))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(
                "{\"status\":400,\"message\":\"Required String parameter 'keyword' is not present\"}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void shouldReturn400SearchUserByKeywordBecauseInvalidPageParam() throws Exception {
        final Integer invalidPageNumber = 0;

        final MvcResult result = mockMvc.perform(get("/api/user")
                .param("keyword", validKeyword)
                .param("page", String.valueOf(invalidPageNumber))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(
                "{\"message\":\"Invalid fields [page: must be greater than or equal to 1]\",\"status\":400}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void shouldCreateAuthenticationTokenSuccessfully() throws Exception {
        mockMvc.perform(post("/api/user/authenticate")
                .content(new ObjectMapper().writeValueAsString(
                        Fixture.from(JwtRequest.class).gimme(VALID_JWT_REQUEST)))
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400CreateAuthenticationTokenSuccessfullyBecauseMissingUsernameAtribute() throws Exception {
        final MvcResult result = mockMvc.perform(post("/api/user/authenticate")
                .content(new ObjectMapper().writeValueAsString(
                        Fixture.from(JwtRequest.class).gimme(MISSING_USERNAME_JWT_REQUEST)))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(
                "{\"status\":400,\"message\":\"Invalid fields [username: must not be empty]\"}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void shouldReturn400CreateAuthenticationTokenSuccessfullyBecauseMissingPasswordAtribute() throws Exception {
        final MvcResult result = mockMvc.perform(post("/api/user/authenticate")
                .content(new ObjectMapper().writeValueAsString(
                        Fixture.from(JwtRequest.class).gimme(MISSING_PASSWORD_JWT_REQUEST)))
                .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(
                "{\"status\":400,\"message\":\"Invalid fields [password: must not be empty]\"}",
                result.getResponse().getContentAsString()
        );
    }

    @Test
    public void shouldReturn400CreateAuthenticationTokenSuccessfullyBecauseMissingRequestBody() throws Exception {
        mockMvc.perform(post("/api/user/authenticate")
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
