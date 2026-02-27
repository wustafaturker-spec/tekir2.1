package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.dto.contact.ContactPhoneDTO;
import com.ut.tekir.common.dto.contact.ContactPhoneSaveRequest;
import com.ut.tekir.common.embeddable.Phone;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactPhone;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactPhoneRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.service.mapper.ContactMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactPhoneService {

    private final ContactPhoneRepository phoneRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactPhoneDTO> findByContactId(Long contactId) {
        return phoneRepository
            .findByOwnerIdOrderByDefaultPhoneDescActivePhoneDesc(contactId)
            .stream()
            .map(contactMapper::toPhoneDTO)
            .toList();
    }

    public ContactPhoneDTO save(Long contactId, ContactPhoneSaveRequest request) {
        ContactPhone phone;

        if (request.id() != null) {
            phone = phoneRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("ContactPhone", request.id()));
        } else {
            phone = new ContactPhone();
            Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact", contactId));
            phone.setOwner(contact);
        }

        Phone embedded = phone.getPhone() != null ? phone.getPhone() : new Phone();
        embedded.setCountryCode(request.countryCode() != null ? request.countryCode() : "90");
        embedded.setAreaCode(request.areaCode());
        embedded.setNumber(request.phoneNumber());
        embedded.setExtention(request.extension());
        phone.setPhone(embedded);

        phone.setInfo(request.info());
        phone.setDefaultPhone(Boolean.TRUE.equals(request.defaultPhone()));
        phone.setActivePhone(request.activePhone() == null || Boolean.TRUE.equals(request.activePhone()));
        phone.setGsmPhone(Boolean.TRUE.equals(request.gsmPhone()));
        phone.setWorkPhone(Boolean.TRUE.equals(request.workPhone()));
        phone.setHomePhone(Boolean.TRUE.equals(request.homePhone()));
        phone.setFaxPhone(Boolean.TRUE.equals(request.faxPhone()));
        // diğer flag'leri sıfırla
        phone.setOtherPhone(false);
        phone.setVehiclePhone(false);
        phone.setCallerPhone(false);
        phone.setImmobilePhone(false);

        return contactMapper.toPhoneDTO(phoneRepository.save(phone));
    }

    public void delete(Long id) {
        if (!phoneRepository.existsById(id)) {
            throw new EntityNotFoundException("ContactPhone", id);
        }
        phoneRepository.deleteById(id);
    }
}
