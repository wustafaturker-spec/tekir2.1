package com.ut.tekir.service.mapper;

import com.ut.tekir.common.dto.contact.ContactAddressDTO;
import com.ut.tekir.common.dto.contact.ContactDetailDTO;
import com.ut.tekir.common.dto.contact.ContactListDTO;
import com.ut.tekir.common.dto.contact.ContactNetworkDTO;
import com.ut.tekir.common.dto.contact.ContactOpportunityDTO;
import com.ut.tekir.common.dto.contact.ContactPersonEntryDTO;
import com.ut.tekir.common.dto.contact.ContactPhoneDTO;
import com.ut.tekir.common.dto.contact.ContactSaveRequest;
import com.ut.tekir.common.embeddable.Address;
import com.ut.tekir.common.embeddable.Phone;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactAddress;
import com.ut.tekir.common.entity.ContactCategory;
import com.ut.tekir.common.entity.ContactNetwork;
import com.ut.tekir.common.entity.ContactOpportunity;
import com.ut.tekir.common.entity.ContactPersonEntry;
import com.ut.tekir.common.entity.ContactPhone;
import com.ut.tekir.common.entity.Organization;
import com.ut.tekir.common.enums.AccountStatus;
import com.ut.tekir.common.enums.EntityType;
import com.ut.tekir.common.enums.InvoiceScenario;
import com.ut.tekir.common.enums.OpportunityStage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T21:57:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ContactMapperImpl implements ContactMapper {

    @Override
    public ContactListDTO toListDTO(Contact entity) {
        if ( entity == null ) {
            return null;
        }

        String categoryName = null;
        Boolean eInvoiceRegistered = null;
        Long id = null;
        String code = null;
        String name = null;
        String taxNumber = null;
        String ssn = null;
        EntityType entityType = null;
        Boolean customerType = null;
        Boolean providerType = null;
        Boolean personnelType = null;
        Boolean active = null;
        AccountStatus accountStatus = null;
        String sector = null;
        String customerSegment = null;

        categoryName = entityCategoryCode( entity );
        eInvoiceRegistered = entity.getEInvoiceRegistered();
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        taxNumber = entity.getTaxNumber();
        ssn = entity.getSsn();
        entityType = entity.getEntityType();
        customerType = entity.getCustomerType();
        providerType = entity.getProviderType();
        personnelType = entity.getPersonnelType();
        active = entity.getActive();
        accountStatus = entity.getAccountStatus();
        sector = entity.getSector();
        customerSegment = entity.getCustomerSegment();

        ContactListDTO contactListDTO = new ContactListDTO( id, code, name, taxNumber, ssn, categoryName, entityType, customerType, providerType, personnelType, active, accountStatus, eInvoiceRegistered, sector, customerSegment );

        return contactListDTO;
    }

    @Override
    public ContactDetailDTO toDetailDTO(Contact entity) {
        if ( entity == null ) {
            return null;
        }

        Long categoryId = null;
        String categoryName = null;
        Long organizationId = null;
        String organizationName = null;
        List<ContactAddressDTO> addresses = null;
        List<ContactPhoneDTO> phones = null;
        List<ContactNetworkDTO> networks = null;
        List<ContactOpportunityDTO> opportunities = null;
        List<ContactPersonEntryDTO> personEntries = null;
        Boolean eInvoiceRegistered = null;
        Long id = null;
        String code = null;
        String name = null;
        EntityType entityType = null;
        String taxNumber = null;
        String taxOffice = null;
        String ssn = null;
        String mersisNo = null;
        String customerCode = null;
        Boolean active = null;
        Boolean customerType = null;
        Boolean providerType = null;
        Boolean agentType = null;
        Boolean personnelType = null;
        Boolean branchType = null;
        Boolean bankType = null;
        String defaultCurrency = null;
        String accountingCode = null;
        Integer paymentTermDays = null;
        BigDecimal discountRate = null;
        String iban = null;
        BigDecimal creditLimit = null;
        BigDecimal riskBalance = null;
        String pkAlias = null;
        InvoiceScenario invoiceScenario = null;
        AccountStatus accountStatus = null;
        String blockReason = null;
        String sector = null;
        String customerSegment = null;
        String salesRepresentative = null;
        LocalDate firstContactDate = null;
        LocalDate lastContactDate = null;
        String referralSource = null;
        String info = null;
        LocalDateTime createDate = null;
        String createUser = null;
        LocalDateTime updateDate = null;
        String updateUser = null;

        categoryId = entityCategoryId( entity );
        categoryName = entityCategoryCode( entity );
        organizationId = entityOrganizationId( entity );
        organizationName = entityOrganizationName( entity );
        addresses = toAddressDTOList( entity.getAddressList() );
        phones = toPhoneDTOList( entity.getPhoneList() );
        networks = toNetworkDTOList( entity.getNetworkList() );
        opportunities = toOpportunityDTOList( entity.getOpportunityList() );
        personEntries = toPersonEntryDTOList( entity.getPersonEntryList() );
        eInvoiceRegistered = entity.getEInvoiceRegistered();
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        entityType = entity.getEntityType();
        taxNumber = entity.getTaxNumber();
        taxOffice = entity.getTaxOffice();
        ssn = entity.getSsn();
        mersisNo = entity.getMersisNo();
        customerCode = entity.getCustomerCode();
        active = entity.getActive();
        customerType = entity.getCustomerType();
        providerType = entity.getProviderType();
        agentType = entity.getAgentType();
        personnelType = entity.getPersonnelType();
        branchType = entity.getBranchType();
        bankType = entity.getBankType();
        defaultCurrency = entity.getDefaultCurrency();
        accountingCode = entity.getAccountingCode();
        paymentTermDays = entity.getPaymentTermDays();
        discountRate = entity.getDiscountRate();
        iban = entity.getIban();
        creditLimit = entity.getCreditLimit();
        riskBalance = entity.getRiskBalance();
        pkAlias = entity.getPkAlias();
        invoiceScenario = entity.getInvoiceScenario();
        accountStatus = entity.getAccountStatus();
        blockReason = entity.getBlockReason();
        sector = entity.getSector();
        customerSegment = entity.getCustomerSegment();
        salesRepresentative = entity.getSalesRepresentative();
        firstContactDate = entity.getFirstContactDate();
        lastContactDate = entity.getLastContactDate();
        referralSource = entity.getReferralSource();
        info = entity.getInfo();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();
        updateDate = entity.getUpdateDate();
        updateUser = entity.getUpdateUser();

        ContactDetailDTO contactDetailDTO = new ContactDetailDTO( id, code, name, entityType, taxNumber, taxOffice, ssn, mersisNo, customerCode, active, customerType, providerType, agentType, personnelType, branchType, bankType, categoryId, categoryName, organizationId, organizationName, defaultCurrency, accountingCode, paymentTermDays, discountRate, iban, creditLimit, riskBalance, eInvoiceRegistered, pkAlias, invoiceScenario, accountStatus, blockReason, sector, customerSegment, salesRepresentative, firstContactDate, lastContactDate, referralSource, info, addresses, phones, networks, opportunities, personEntries, createDate, createUser, updateDate, updateUser );

        return contactDetailDTO;
    }

    @Override
    public ContactAddressDTO toAddressDTO(ContactAddress entity) {
        if ( entity == null ) {
            return null;
        }

        String street = null;
        String city = null;
        String province = null;
        String country = null;
        String zip = null;
        String recipientSurname = null;
        Long id = null;
        String info = null;
        Boolean activeAddress = null;
        Boolean defaultAddress = null;
        Boolean invoiceAddress = null;
        Boolean shippingAddress = null;
        Boolean homeAddress = null;
        Boolean workAddress = null;
        Boolean otherAddress = null;
        String recipientName = null;

        street = entityAddressStreet( entity );
        city = entityAddressCity( entity );
        province = entityAddressProvince( entity );
        country = entityAddressCountry( entity );
        zip = entityAddressZip( entity );
        recipientSurname = entity.getRecipientSurName();
        id = entity.getId();
        info = entity.getInfo();
        activeAddress = entity.getActiveAddress();
        defaultAddress = entity.getDefaultAddress();
        invoiceAddress = entity.getInvoiceAddress();
        shippingAddress = entity.getShippingAddress();
        homeAddress = entity.getHomeAddress();
        workAddress = entity.getWorkAddress();
        otherAddress = entity.getOtherAddress();
        recipientName = entity.getRecipientName();

        ContactAddressDTO contactAddressDTO = new ContactAddressDTO( id, info, street, city, province, country, zip, activeAddress, defaultAddress, invoiceAddress, shippingAddress, homeAddress, workAddress, otherAddress, recipientName, recipientSurname );

        return contactAddressDTO;
    }

    @Override
    public ContactPhoneDTO toPhoneDTO(ContactPhone entity) {
        if ( entity == null ) {
            return null;
        }

        String countryCode = null;
        String areaCode = null;
        String phoneNumber = null;
        String extension = null;
        Long id = null;
        String info = null;
        Boolean activePhone = null;
        Boolean defaultPhone = null;
        Boolean homePhone = null;
        Boolean workPhone = null;
        Boolean gsmPhone = null;
        Boolean faxPhone = null;

        countryCode = entityPhoneCountryCode( entity );
        areaCode = entityPhoneAreaCode( entity );
        phoneNumber = entityPhoneFullNumber( entity );
        extension = entityPhoneExtention( entity );
        id = entity.getId();
        info = entity.getInfo();
        activePhone = entity.getActivePhone();
        defaultPhone = entity.getDefaultPhone();
        homePhone = entity.getHomePhone();
        workPhone = entity.getWorkPhone();
        gsmPhone = entity.getGsmPhone();
        faxPhone = entity.getFaxPhone();

        ContactPhoneDTO contactPhoneDTO = new ContactPhoneDTO( id, info, countryCode, areaCode, phoneNumber, extension, activePhone, defaultPhone, homePhone, workPhone, gsmPhone, faxPhone );

        return contactPhoneDTO;
    }

    @Override
    public ContactNetworkDTO toNetworkDTO(ContactNetwork entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String info = null;
        String value = null;
        Boolean activeNetwork = null;
        Boolean defaultNetwork = null;

        id = entity.getId();
        info = entity.getInfo();
        value = entity.getValue();
        activeNetwork = entity.getActiveNetwork();
        defaultNetwork = entity.getDefaultNetwork();

        String networkType = resolveNetworkType(entity);

        ContactNetworkDTO contactNetworkDTO = new ContactNetworkDTO( id, info, value, networkType, activeNetwork, defaultNetwork );

        return contactNetworkDTO;
    }

    @Override
    public ContactOpportunityDTO toOpportunityDTO(ContactOpportunity entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        Long id = null;
        String title = null;
        OpportunityStage stage = null;
        BigDecimal expectedRevenue = null;
        String currency = null;
        Integer probability = null;
        LocalDate expectedCloseDate = null;
        LocalDate actualCloseDate = null;
        String note = null;
        String assignedTo = null;
        Boolean active = null;
        LocalDateTime createDate = null;
        String createUser = null;

        contactId = entityContactId( entity );
        id = entity.getId();
        title = entity.getTitle();
        stage = entity.getStage();
        expectedRevenue = entity.getExpectedRevenue();
        currency = entity.getCurrency();
        probability = entity.getProbability();
        expectedCloseDate = entity.getExpectedCloseDate();
        actualCloseDate = entity.getActualCloseDate();
        note = entity.getNote();
        assignedTo = entity.getAssignedTo();
        active = entity.getActive();
        createDate = entity.getCreateDate();
        createUser = entity.getCreateUser();

        ContactOpportunityDTO contactOpportunityDTO = new ContactOpportunityDTO( id, contactId, title, stage, expectedRevenue, currency, probability, expectedCloseDate, actualCloseDate, note, assignedTo, active, createDate, createUser );

        return contactOpportunityDTO;
    }

    @Override
    public ContactPersonEntryDTO toPersonEntryDTO(ContactPersonEntry entity) {
        if ( entity == null ) {
            return null;
        }

        Long contactId = null;
        Long id = null;
        String firstName = null;
        String lastName = null;
        String jobTitle = null;
        String department = null;
        String email = null;
        String phone = null;
        String mobile = null;
        Boolean isDefault = null;
        String note = null;

        contactId = entityContactId1( entity );
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        jobTitle = entity.getJobTitle();
        department = entity.getDepartment();
        email = entity.getEmail();
        phone = entity.getPhone();
        mobile = entity.getMobile();
        isDefault = entity.getIsDefault();
        note = entity.getNote();

        ContactPersonEntryDTO contactPersonEntryDTO = new ContactPersonEntryDTO( id, contactId, firstName, lastName, jobTitle, department, email, phone, mobile, isDefault, note );

        return contactPersonEntryDTO;
    }

    @Override
    public List<ContactAddressDTO> toAddressDTOList(List<ContactAddress> list) {
        if ( list == null ) {
            return null;
        }

        List<ContactAddressDTO> list1 = new ArrayList<ContactAddressDTO>( list.size() );
        for ( ContactAddress contactAddress : list ) {
            list1.add( toAddressDTO( contactAddress ) );
        }

        return list1;
    }

    @Override
    public List<ContactPhoneDTO> toPhoneDTOList(List<ContactPhone> list) {
        if ( list == null ) {
            return null;
        }

        List<ContactPhoneDTO> list1 = new ArrayList<ContactPhoneDTO>( list.size() );
        for ( ContactPhone contactPhone : list ) {
            list1.add( toPhoneDTO( contactPhone ) );
        }

        return list1;
    }

    @Override
    public List<ContactNetworkDTO> toNetworkDTOList(List<ContactNetwork> list) {
        if ( list == null ) {
            return null;
        }

        List<ContactNetworkDTO> list1 = new ArrayList<ContactNetworkDTO>( list.size() );
        for ( ContactNetwork contactNetwork : list ) {
            list1.add( toNetworkDTO( contactNetwork ) );
        }

        return list1;
    }

    @Override
    public List<ContactOpportunityDTO> toOpportunityDTOList(List<ContactOpportunity> list) {
        if ( list == null ) {
            return null;
        }

        List<ContactOpportunityDTO> list1 = new ArrayList<ContactOpportunityDTO>( list.size() );
        for ( ContactOpportunity contactOpportunity : list ) {
            list1.add( toOpportunityDTO( contactOpportunity ) );
        }

        return list1;
    }

    @Override
    public List<ContactPersonEntryDTO> toPersonEntryDTOList(List<ContactPersonEntry> list) {
        if ( list == null ) {
            return null;
        }

        List<ContactPersonEntryDTO> list1 = new ArrayList<ContactPersonEntryDTO>( list.size() );
        for ( ContactPersonEntry contactPersonEntry : list ) {
            list1.add( toPersonEntryDTO( contactPersonEntry ) );
        }

        return list1;
    }

    @Override
    public void updateEntity(Contact entity, ContactSaveRequest request) {
        if ( request == null ) {
            return;
        }

        entity.setEInvoiceRegistered( request.eInvoiceRegistered() );
        entity.setCode( request.code() );
        entity.setName( request.name() );
        entity.setInfo( request.info() );
        entity.setActive( request.active() );
        entity.setCustomerType( request.customerType() );
        entity.setProviderType( request.providerType() );
        entity.setAgentType( request.agentType() );
        entity.setPersonnelType( request.personnelType() );
        entity.setBranchType( request.branchType() );
        entity.setBankType( request.bankType() );
        entity.setCustomerCode( request.customerCode() );
        entity.setEntityType( request.entityType() );
        entity.setMersisNo( request.mersisNo() );
        entity.setDefaultCurrency( request.defaultCurrency() );
        entity.setAccountingCode( request.accountingCode() );
        entity.setPaymentTermDays( request.paymentTermDays() );
        entity.setDiscountRate( request.discountRate() );
        entity.setIban( request.iban() );
        entity.setPkAlias( request.pkAlias() );
        entity.setInvoiceScenario( request.invoiceScenario() );
        entity.setCreditLimit( request.creditLimit() );
        entity.setRiskBalance( request.riskBalance() );
        entity.setAccountStatus( request.accountStatus() );
        entity.setBlockReason( request.blockReason() );
        entity.setSector( request.sector() );
        entity.setCustomerSegment( request.customerSegment() );
        entity.setSalesRepresentative( request.salesRepresentative() );
        entity.setFirstContactDate( request.firstContactDate() );
        entity.setLastContactDate( request.lastContactDate() );
        entity.setReferralSource( request.referralSource() );
        entity.setTaxNumber( request.taxNumber() );
        entity.setTaxOffice( request.taxOffice() );
        entity.setSsn( request.ssn() );
    }

    private String entityCategoryCode(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        ContactCategory category = contact.getCategory();
        if ( category == null ) {
            return null;
        }
        String code = category.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private Long entityCategoryId(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        ContactCategory category = contact.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityOrganizationId(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        Organization organization = contact.getOrganization();
        if ( organization == null ) {
            return null;
        }
        Long id = organization.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityOrganizationName(Contact contact) {
        if ( contact == null ) {
            return null;
        }
        Organization organization = contact.getOrganization();
        if ( organization == null ) {
            return null;
        }
        String name = organization.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String entityAddressStreet(ContactAddress contactAddress) {
        if ( contactAddress == null ) {
            return null;
        }
        Address address = contactAddress.getAddress();
        if ( address == null ) {
            return null;
        }
        String street = address.getStreet();
        if ( street == null ) {
            return null;
        }
        return street;
    }

    private String entityAddressCity(ContactAddress contactAddress) {
        if ( contactAddress == null ) {
            return null;
        }
        Address address = contactAddress.getAddress();
        if ( address == null ) {
            return null;
        }
        String city = address.getCity();
        if ( city == null ) {
            return null;
        }
        return city;
    }

    private String entityAddressProvince(ContactAddress contactAddress) {
        if ( contactAddress == null ) {
            return null;
        }
        Address address = contactAddress.getAddress();
        if ( address == null ) {
            return null;
        }
        String province = address.getProvince();
        if ( province == null ) {
            return null;
        }
        return province;
    }

    private String entityAddressCountry(ContactAddress contactAddress) {
        if ( contactAddress == null ) {
            return null;
        }
        Address address = contactAddress.getAddress();
        if ( address == null ) {
            return null;
        }
        String country = address.getCountry();
        if ( country == null ) {
            return null;
        }
        return country;
    }

    private String entityAddressZip(ContactAddress contactAddress) {
        if ( contactAddress == null ) {
            return null;
        }
        Address address = contactAddress.getAddress();
        if ( address == null ) {
            return null;
        }
        String zip = address.getZip();
        if ( zip == null ) {
            return null;
        }
        return zip;
    }

    private String entityPhoneCountryCode(ContactPhone contactPhone) {
        if ( contactPhone == null ) {
            return null;
        }
        Phone phone = contactPhone.getPhone();
        if ( phone == null ) {
            return null;
        }
        String countryCode = phone.getCountryCode();
        if ( countryCode == null ) {
            return null;
        }
        return countryCode;
    }

    private String entityPhoneAreaCode(ContactPhone contactPhone) {
        if ( contactPhone == null ) {
            return null;
        }
        Phone phone = contactPhone.getPhone();
        if ( phone == null ) {
            return null;
        }
        String areaCode = phone.getAreaCode();
        if ( areaCode == null ) {
            return null;
        }
        return areaCode;
    }

    private String entityPhoneFullNumber(ContactPhone contactPhone) {
        if ( contactPhone == null ) {
            return null;
        }
        Phone phone = contactPhone.getPhone();
        if ( phone == null ) {
            return null;
        }
        String fullNumber = phone.getFullNumber();
        if ( fullNumber == null ) {
            return null;
        }
        return fullNumber;
    }

    private String entityPhoneExtention(ContactPhone contactPhone) {
        if ( contactPhone == null ) {
            return null;
        }
        Phone phone = contactPhone.getPhone();
        if ( phone == null ) {
            return null;
        }
        String extention = phone.getExtention();
        if ( extention == null ) {
            return null;
        }
        return extention;
    }

    private Long entityContactId(ContactOpportunity contactOpportunity) {
        if ( contactOpportunity == null ) {
            return null;
        }
        Contact contact = contactOpportunity.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityContactId1(ContactPersonEntry contactPersonEntry) {
        if ( contactPersonEntry == null ) {
            return null;
        }
        Contact contact = contactPersonEntry.getContact();
        if ( contact == null ) {
            return null;
        }
        Long id = contact.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
