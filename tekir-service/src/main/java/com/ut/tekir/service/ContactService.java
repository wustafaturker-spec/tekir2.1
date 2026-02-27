package com.ut.tekir.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ut.tekir.common.dto.contact.ContactDetailDTO;
import com.ut.tekir.common.dto.contact.ContactFilterModel;
import com.ut.tekir.common.dto.contact.ContactListDTO;
import com.ut.tekir.common.dto.contact.ContactSaveRequest;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactCategory;
import com.ut.tekir.common.entity.Organization;
import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.EntityType;
import com.ut.tekir.common.exception.BusinessException;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactCategoryRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.repository.OrganizationRepository;
import com.ut.tekir.service.einvoice.TaxPayerCheckService;
import com.ut.tekir.service.mapper.ContactMapper;
import com.ut.tekir.service.spec.ContactSpecifications;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactCategoryRepository contactCategoryRepository;
    private final OrganizationRepository organizationRepository;
    private final ContactMapper contactMapper;
    private final SequenceService sequenceService;
    private final TaxPayerCheckService taxPayerCheckService;

    @Transactional(readOnly = true)
    public Page<ContactListDTO> search(ContactFilterModel filter, Pageable pageable) {
        return contactRepository
                .findAll(ContactSpecifications.withFilter(filter), pageable)
                .map(contactMapper::toListDTO);
    }

    @Transactional
    public ContactDetailDTO getById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact", id));

        // Automatic e-Invoice taxpayer check on contact view
        taxPayerCheckService.checkAndUpdateContact(contact);

        return contactMapper.toDetailDTO(contact);
    }

    public ContactDetailDTO save(ContactSaveRequest request) {
        Contact contact;

        if (request.id() != null) {
            contact = contactRepository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Contact", request.id()));

            if (contact.getAccountStatus() == AccountStatus.BLOCKED) {
                throw new BusinessException("account.blocked",
                        "Bu cari hesap blokeli durumda. Sebep: " + contact.getBlockReason());
            }
        } else {
            contact = new Contact();
            if (!StringUtils.hasText(request.code())) {
                contact.setCode(sequenceService.getNewNumber("contact", 6));
            }
        }

        contactMapper.updateEntity(contact, request);

        // Kategori
        if (request.categoryId() != null) {
            ContactCategory category = contactCategoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("ContactCategory", request.categoryId()));
            contact.setCategory(category);
        } else {
            contact.setCategory(null);
        }

        // Organizasyon
        if (request.organizationId() != null) {
            Organization org = organizationRepository.findById(request.organizationId())
                    .orElseThrow(() -> new EntityNotFoundException("Organization", request.organizationId()));
            contact.setOrganization(org);
        } else {
            contact.setOrganization(null);
        }

        validateContact(contact);

        contact = contactRepository.save(contact);
        return contactMapper.toDetailDTO(contact);
    }

    public ContactDetailDTO update(Long id, ContactSaveRequest request) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact", id));

        if (contact.getAccountStatus() == AccountStatus.BLOCKED) {
            throw new BusinessException("account.blocked",
                    "Bu cari hesap blokeli durumda. Sebep: " + contact.getBlockReason());
        }

        contactMapper.updateEntity(contact, request);

        // Kategori
        if (request.categoryId() != null) {
            ContactCategory category = contactCategoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("ContactCategory", request.categoryId()));
            contact.setCategory(category);
        } else {
            contact.setCategory(null);
        }

        // Organizasyon
        if (request.organizationId() != null) {
            Organization org = organizationRepository.findById(request.organizationId())
                    .orElseThrow(() -> new EntityNotFoundException("Organization", request.organizationId()));
            contact.setOrganization(org);
        } else {
            contact.setOrganization(null);
        }

        validateContact(contact);

        contact = contactRepository.save(contact);
        return contactMapper.toDetailDTO(contact);
    }

    public void delete(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new EntityNotFoundException("Contact", id);
        }
        contactRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public java.util.List<com.ut.tekir.common.dto.SuggestDTO> suggest(String query) {
        return contactRepository.findAll((root, q, cb) -> cb.or(
                cb.like(cb.lower(root.get("code")), query.toLowerCase() + "%"),
                cb.like(cb.lower(root.get("name")), "%" + query.toLowerCase() + "%")), Pageable.ofSize(10))
                .map(c -> new com.ut.tekir.common.dto.SuggestDTO(c.getId(), c.getCode(), c.getName()))
                .getContent();
    }

    private void validateContact(Contact contact) {
        if (contactRepository.existsByCodeAndIdNot(
                contact.getCode(),
                Optional.ofNullable(contact.getId()).orElse(-1L))) {
            throw new BusinessException("duplicate.code", "Bu cari kodu zaten kayıtlı: " + contact.getCode());
        }

        if (contact.getEntityType() == EntityType.CORPORATE
                && StringUtils.hasText(contact.getTaxNumber())) {
            validateVKN(contact.getTaxNumber());
        }

        if (contact.getEntityType() == EntityType.INDIVIDUAL
                && StringUtils.hasText(contact.getSsn())) {
            validateTCKN(contact.getSsn());
        }

        if (StringUtils.hasText(contact.getIban())) {
            validateIBAN(contact.getIban());
        }

        if (StringUtils.hasText(contact.getTaxNumber())) {
            contactRepository.findFirstByTaxNumber(contact.getTaxNumber())
                    .filter(c -> !c.getId().equals(contact.getId()))
                    .ifPresent(c -> {
                        throw new BusinessException("duplicate.taxNumber",
                                "Bu vergi numarası zaten kayıtlı: " + contact.getTaxNumber());
                    });
        }
    }

    static void validateVKN(String vkn) {
        if (!vkn.matches("^\\d{10}$")) {
            throw new BusinessException("invalid.vkn", "VKN 10 haneli olmalıdır");
        }
        int[] digits = new int[10];
        for (int i = 0; i < 10; i++) {
            digits[i] = vkn.charAt(i) - '0';
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int tmp = (digits[i] + (9 - i)) % 10;
            int v = (tmp * (int) Math.pow(2, 9 - i)) % 9;
            if (tmp != 0 && v == 0)
                v = 9;
            sum += v;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        if (checkDigit != digits[9]) {
            throw new BusinessException("invalid.vkn", "Geçersiz VKN kontrol hanesi");
        }
    }

    static void validateTCKN(String tckn) {
        if (!tckn.matches("^\\d{11}$")) {
            throw new BusinessException("invalid.tckn", "TCKN 11 haneli olmalıdır");
        }
        if (tckn.charAt(0) == '0') {
            throw new BusinessException("invalid.tckn", "TCKN sıfır ile başlayamaz");
        }
        int[] d = new int[11];
        for (int i = 0; i < 11; i++) {
            d[i] = tckn.charAt(i) - '0';
        }
        int oddSum = d[0] + d[2] + d[4] + d[6] + d[8];
        int evenSum = d[1] + d[3] + d[5] + d[7];
        int check10 = ((oddSum * 7) - evenSum) % 10;
        if (check10 < 0)
            check10 += 10;
        if (check10 != d[9]) {
            throw new BusinessException("invalid.tckn", "Geçersiz TCKN kontrol hanesi");
        }
        int totalSum = 0;
        for (int i = 0; i < 10; i++) {
            totalSum += d[i];
        }
        if (totalSum % 10 != d[10]) {
            throw new BusinessException("invalid.tckn", "Geçersiz TCKN kontrol hanesi");
        }
    }

    static void validateIBAN(String iban) {
        if (!iban.matches("^TR\\d{24}$")) {
            throw new BusinessException("invalid.iban",
                    "IBAN TR ile başlamalı ve 26 karakter olmalıdır");
        }
    }
}
