package com.example.av2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.av2.repository.UserRepository;
import com.example.av2.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class AuthIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        userRepository.findByUsername("mestre").ifPresentOrElse(
                user -> { /* Já existe */ },
                () -> {
                    com.example.av2.model.User admin = new com.example.av2.model.User(null, "mestre", passwordEncoder.encode("topchave321"), "ADMIN", "(31)98829-6306");
                    userRepository.save(admin);
                }
        );
        userRepository.findByUsername("usuarioPadrao").ifPresentOrElse(
                user -> { /* Já existe */ },
                () -> {
                    com.example.av2.model.User regularUser = new com.example.av2.model.User(null, "usuarioPadrao", passwordEncoder.encode("padrao321"), "USER", "(31)98759-4202");
                    userRepository.save(regularUser);
                }
        );
    }

    @Test
    void testLoginSuccess() throws Exception {
        String token = mockMvc.perform(post("/auth/login")
                .param("username", "mestre")
                .param("password", "topchave321")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();

        assert jwtService.validateToken(token);
        org.assertj.core.api.Assertions.assertThat(token).isNotEmpty();
    }

    @Test
    void testLoginFailureInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                .param("username", "mestre")
                .param("password", "errada123")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isUnauthorized())
               .andExpect(content().string(containsString("Credenciais inválidas.")));
    }

    @Test
    void testProtectedEndpointAccessDeniedWithoutToken() throws Exception {
        mockMvc.perform(get("/api/hello"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void testProtectedEndpointAccessWithValidToken() throws Exception {
        String token = mockMvc.perform(post("/auth/login")
                .param("username", "usuarioPadrao")
                .param("password", "padrao321")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/api/hello")
                .header("Authorization", "Bearer " + token))
               .andExpect(status().isOk())
               .andExpect(content().string("Olá! Você acessou um endpoint protegido com sucesso!"));
    }

    @Test
    void testProtectedAdminEndpointAccessWithAdminToken() throws Exception {
        String adminToken = mockMvc.perform(post("/auth/login")
                .param("username", "mestre")
                .param("password", "topchave321")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/api/admin")
                .header("Authorization", "Bearer " + adminToken))
               .andExpect(status().isOk())
               .andExpect(content().string("Bem-vindo, Administrador! Este é um recurso restrito."));
    }

    @Test
    void testProtectedAdminEndpointAccessDeniedWithUserToken() throws Exception {
        String userToken = mockMvc.perform(post("/auth/login")
                .param("username", "usuarioPadrao")
                .param("password", "padrao321")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/api/admin")
                .header("Authorization", "Bearer " + userToken))
               .andExpect(status().isForbidden());
    }
}
