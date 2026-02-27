package com.ut.tekir.tenant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TenantRegistrationRequest(
    @NotBlank(message = "Sirket adi zorunludur")
    @Size(max = 255, message = "Sirket adi en fazla 255 karakter olabilir")
    String companyName,

    @NotBlank(message = "Slug zorunludur")
    @Size(min = 3, max = 50, message = "Slug 3-50 karakter arasinda olmalidir")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug sadece kucuk harf, rakam ve tire icermelidir")
    String slug,

    @NotBlank(message = "Admin adi zorunludur")
    @Size(max = 100, message = "Admin adi en fazla 100 karakter olabilir")
    String adminFullName,

    @NotBlank(message = "Admin e-posta zorunludur")
    @Email(message = "Gecerli bir e-posta adresi giriniz")
    String adminEmail,

    @NotBlank(message = "Admin sifresi zorunludur")
    @Size(min = 6, max = 100, message = "Sifre en az 6 karakter olmalidir")
    String adminPassword
) {}

