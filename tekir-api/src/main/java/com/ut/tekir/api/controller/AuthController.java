package com.ut.tekir.api.controller;

import com.ut.tekir.api.service.AuthService;
import com.ut.tekir.api.service.AuthService.AuthResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Kimlik doğrulama işlemleri")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Giriş yap", description = "Kullanıcı adı ve şifre ile JWT token alın")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request.username(), request.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Token yenile", description = "Refresh token ile yeni access token alın")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        AuthResponse response = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Şifremi unuttum", description = "Şifre sıfırlama bağlantısı gönderir")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Şifre sıfırla", description = "Token ile yeni şifre belirler")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok().build();
    }

    public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password
    ) {}

    public record RefreshRequest(
        @NotBlank String refreshToken
    ) {}

    public record ForgotPasswordRequest(
        @NotBlank String email
    ) {}

    public record ResetPasswordRequest(
        @NotBlank String token,
        @NotBlank String newPassword
    ) {}
}


