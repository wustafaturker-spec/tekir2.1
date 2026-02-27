package com.ut.tekir.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.ut.tekir.common.dto.contact.ContactAddressDTO;
import com.ut.tekir.common.dto.contact.ContactDetailDTO;
import com.ut.tekir.common.dto.contact.ContactListDTO;
import com.ut.tekir.common.dto.contact.ContactNetworkDTO;
import com.ut.tekir.common.dto.contact.ContactOpportunityDTO;
import com.ut.tekir.common.dto.contact.ContactPersonEntryDTO;
import com.ut.tekir.common.dto.contact.ContactPhoneDTO;
import com.ut.tekir.common.dto.contact.ContactSaveRequest;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactAddress;
import com.ut.tekir.common.entity.ContactNetwork;
import com.ut.tekir.common.entity.ContactOpportunity;
import com.ut.tekir.common.entity.ContactPersonEntry;
import com.ut.tekir.common.entity.ContactPhone;

/**
 * MapStruct mapper: Contact entity ↔ DTO dönüşümleri.
 *
 * Legacy alanlar (DB'de kalır ama DTO'ya çıkmaz):
 * midname, surname, fullname, company, legalName, displayName, person,
 * debitLimit, riskLimit, hasDiscount, hasWithholding, discount,
 * representative, exCode1, exCode2, title
 */
@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "categoryName", source = "category.code")
    @Mapping(target = "eInvoiceRegistered", source = "EInvoiceRegistered")
    ContactListDTO toListDTO(Contact entity);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.code")
    @Mapping(target = "organizationId", source = "organization.id")
    @Mapping(target = "organizationName", source = "organization.name")
    @Mapping(target = "addresses", source = "addressList")
    @Mapping(target = "phones", source = "phoneList")
    @Mapping(target = "networks", source = "networkList")
    @Mapping(target = "opportunities", source = "opportunityList")
    @Mapping(target = "personEntries", source = "personEntryList")
    @Mapping(target = "eInvoiceRegistered", source = "EInvoiceRegistered")
    ContactDetailDTO toDetailDTO(Contact entity);

    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "province", source = "address.province")
    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "zip", source = "address.zip")
    @Mapping(target = "recipientSurname", source = "recipientSurName")
    ContactAddressDTO toAddressDTO(ContactAddress entity);

    @Mapping(target = "countryCode", source = "phone.countryCode")
    @Mapping(target = "areaCode", source = "phone.areaCode")
    @Mapping(target = "phoneNumber", source = "phone.fullNumber")
    @Mapping(target = "extension", source = "phone.extention")
    ContactPhoneDTO toPhoneDTO(ContactPhone entity);

    @Mapping(target = "networkType", expression = "java(resolveNetworkType(entity))")
    ContactNetworkDTO toNetworkDTO(ContactNetwork entity);

    default String resolveNetworkType(ContactNetwork entity) {
        if (Boolean.TRUE.equals(entity.getEmail())) return "EMAIL";
        if (Boolean.TRUE.equals(entity.getWeb())) return "WEB";
        if (Boolean.TRUE.equals(entity.getTwitterNetwork())) return "TWITTER";
        if (Boolean.TRUE.equals(entity.getFacebookNetwork())) return "FACEBOOK";
        if (Boolean.TRUE.equals(entity.getSkypeNetwork())) return "SKYPE";
        return "OTHER";
    }

    @Mapping(target = "contactId", source = "contact.id")
    ContactOpportunityDTO toOpportunityDTO(ContactOpportunity entity);

    @Mapping(target = "contactId", source = "contact.id")
    ContactPersonEntryDTO toPersonEntryDTO(ContactPersonEntry entity);

    List<ContactAddressDTO> toAddressDTOList(List<ContactAddress> list);
    List<ContactPhoneDTO> toPhoneDTOList(List<ContactPhone> list);
    List<ContactNetworkDTO> toNetworkDTOList(List<ContactNetwork> list);
    List<ContactOpportunityDTO> toOpportunityDTOList(List<ContactOpportunity> list);
    List<ContactPersonEntryDTO> toPersonEntryDTOList(List<ContactPersonEntry> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "organization", ignore = true)
    @Mapping(target = "addressList", ignore = true)
    @Mapping(target = "phoneList", ignore = true)
    @Mapping(target = "networkList", ignore = true)
    @Mapping(target = "bankAccountList", ignore = true)
    @Mapping(target = "personelList", ignore = true)
    @Mapping(target = "opportunityList", ignore = true)
    @Mapping(target = "personEntryList", ignore = true)
    @Mapping(target = "ownCompany", ignore = true)
    @Mapping(target = "openDate", ignore = true)
    @Mapping(target = "paymentPlanId", ignore = true)
    @Mapping(target = "currencyRateType", ignore = true)
    @Mapping(target = "allType", ignore = true)
    @Mapping(target = "relatedType", ignore = true)
    @Mapping(target = "foundationType", ignore = true)
    @Mapping(target = "contactType", ignore = true)
    @Mapping(target = "system", ignore = true)
    @Mapping(target = "isPublic", ignore = true)
    // Legacy alanlar — DB'de kalır, API'dan kaldırıldı
    @Mapping(target = "midname", ignore = true)
    @Mapping(target = "surname", ignore = true)
    @Mapping(target = "fullname", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "legalName", ignore = true)
    @Mapping(target = "displayName", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "debitLimit", ignore = true)
    @Mapping(target = "riskLimit", ignore = true)
    @Mapping(target = "hasDiscount", ignore = true)
    @Mapping(target = "hasWithholding", ignore = true)
    @Mapping(target = "discount", ignore = true)
    @Mapping(target = "grossSalary", ignore = true)
    @Mapping(target = "representative", ignore = true)
    @Mapping(target = "exCode1", ignore = true)
    @Mapping(target = "exCode2", ignore = true)
    @Mapping(target = "title", ignore = true)
    // Personel alanları — ayrı sekme/modül kapsamında
    @Mapping(target = "startWorkingDate", ignore = true)
    @Mapping(target = "endWorkingDate", ignore = true)
    @Mapping(target = "activePersonel", ignore = true)
    @Mapping(target = "sgkNumber", ignore = true)
    @Mapping(target = "birthdate", ignore = true)
    @Mapping(target = "bloodGroup", ignore = true)
    @Mapping(target = "fatherName", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "education", ignore = true)
    @Mapping(target = "passportNo", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "spouseIncomeStatus", ignore = true)
    @Mapping(target = "childNumber", ignore = true)
    @Mapping(target = "dependents", ignore = true)
    // Audit
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @Mapping(target = "createUser", ignore = true)
    @Mapping(target = "updateUser", ignore = true)
    @Mapping(target = "EInvoiceRegistered", source = "eInvoiceRegistered")
    void updateEntity(@MappingTarget Contact entity, ContactSaveRequest request);
}
