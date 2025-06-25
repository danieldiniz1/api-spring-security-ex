package br.com.train.springsecurityexample.controller;

import br.com.train.springsecurityexample.model.dto.TokenDTO;
import br.com.train.springsecurityexample.model.form.AccountCredentialsForm;
import br.com.train.springsecurityexample.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "Auth", description = "Authentication and Authorization")
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private static  final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Autentica usuário e retorna token JWT",
        description = "Realiza autenticação do usuário. Retorna o token JWT se usuário e senha estiverem corretos."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário autenticado com sucesso",
            content = @Content(schema = @Schema(implementation = TokenDTO.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado - usuário ou senha inválidos",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno no servidor",
            content = @Content
        )
    })
    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signIn(@RequestBody AccountCredentialsForm credentialsForm) {
        log.debug("Iniciando autenticacao");
        TokenDTO token = authService.signIn(credentialsForm);
        if (Objects.isNull(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        log.debug("Autenticacao realizada com sucesso com token gerado: {}", token.token());
        return ResponseEntity.ok(token);
    }

    @Operation(
        summary = "Realiza refresh do token JWT",
        description = "Atualiza o token JWT do usuário. Necessário enviar o token atual no header Authorization."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário autenticado com sucesso",
                    content = @Content(schema = @Schema(implementation = TokenDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Não autorizado - usuário ou senha inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno no servidor",
                    content = @Content
            )
    })
    @PutMapping("/refresh/{username}")
    public ResponseEntity<TokenDTO> getToken(HttpServletRequest request,
                                             @PathVariable("username") String username) {
        log.debug("Iniciando refresh token para o usuário: {}", username);
        TokenDTO token = authService.refreshToken(request, username);
        if (Objects.isNull(token)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        log.debug("Refresh token realizado com sucesso para o usuário: {}", username);
        return ResponseEntity.ok(token);

    }
}
