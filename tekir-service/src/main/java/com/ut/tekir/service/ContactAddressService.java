package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.dto.contact.ContactAddressDTO;
import com.ut.tekir.common.dto.contact.ContactAddressSaveRequest;
import com.ut.tekir.common.embeddable.Address;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactAddress;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactAddressRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.service.mapper.ContactMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactAddressService {

    private final ContactAddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactAddressDTO> findByContactId(Long contactId) {
        return addressRepository
            .findByOwnerIdOrderByDefaultAddressDescActiveAddressDesc(contactId)
            .stream()
            .map(contactMapper::toAddressDTO)
            .toList();
    }

    public ContactAddressDTO save(Long contactId, ContactAddressSaveRequest request) {
        ContactAddress address;

        if (request.id() != null) {
            address = addressRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("ContactAddress", request.id()));
        } else {
            address = new ContactAddress();
            Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact", contactId));
            address.setOwner(contact);
        }

        // Gömülü adres alanları
        Address embedded = address.getAddress() != null ? address.getAddress() : new Address();
        embedded.setStreet(request.street());
        embedded.setCity(request.city());
        embedded.setProvince(request.province());
        embedded.setCountry(request.country() != null ? request.country() : "Türkiye");
        embedded.setZip(request.zip());
        address.setAddress(embedded);

        // Adres tipi flag'leri (sadece biri aktif olabilir)
        address.setInvoiceAddress(Boolean.TRUE.equals(request.invoiceAddress()));
        address.setShippingAddress(Boolean.TRUE.equals(request.shippingAddress()));
        address.setHomeAddress(Boolean.TRUE.equals(request.homeAddress()));
        address.setWorkAddress(Boolean.TRUE.equals(request.workAddress()));
        address.setOtherAddress(Boolean.TRUE.equals(request.otherAddress()));

        // Meta
        address.setDefaultAddress(Boolean.TRUE.equals(request.defaultAddress()));
        address.setActiveAddress(request.activeAddress() == null || Boolean.TRUE.equals(request.activeAddress()));
        address.setRecipientName(request.recipientName());
        address.setRecipientSurName(request.recipientSurname());
        address.setInfo(request.info());

        return contactMapper.toAddressDTO(addressRepository.save(address));
    }

    public void delete(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new EntityNotFoundException("ContactAddress", id);
        }
        addressRepository.deleteById(id);
    }
}
