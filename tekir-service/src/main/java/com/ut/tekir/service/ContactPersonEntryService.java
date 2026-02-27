package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.dto.contact.ContactPersonEntryDTO;
import com.ut.tekir.common.dto.contact.ContactPersonEntrySaveRequest;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactPersonEntry;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactPersonEntryRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.service.mapper.ContactMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactPersonEntryService {

    private final ContactPersonEntryRepository personEntryRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactPersonEntryDTO> findByContactId(Long contactId) {
        return personEntryRepository
            .findByContactIdOrderByIsDefaultDescFirstNameAsc(contactId)
            .stream()
            .map(contactMapper::toPersonEntryDTO)
            .toList();
    }

    public ContactPersonEntryDTO save(ContactPersonEntrySaveRequest request) {
        ContactPersonEntry entry;

        if (request.id() != null) {
            entry = personEntryRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("ContactPersonEntry", request.id()));
        } else {
            entry = new ContactPersonEntry();
            Contact contact = contactRepository.findById(request.contactId())
                .orElseThrow(() -> new EntityNotFoundException("Contact", request.contactId()));
            entry.setContact(contact);
        }

        entry.setFirstName(request.firstName());
        entry.setLastName(request.lastName());
        entry.setJobTitle(request.jobTitle());
        entry.setDepartment(request.department());
        entry.setEmail(request.email());
        entry.setPhone(request.phone());
        entry.setMobile(request.mobile());
        entry.setIsDefault(request.isDefault() != null ? request.isDefault() : Boolean.FALSE);
        entry.setNote(request.note());

        return contactMapper.toPersonEntryDTO(personEntryRepository.save(entry));
    }

    public void delete(Long id) {
        if (!personEntryRepository.existsById(id)) {
            throw new EntityNotFoundException("ContactPersonEntry", id);
        }
        personEntryRepository.deleteById(id);
    }
}
