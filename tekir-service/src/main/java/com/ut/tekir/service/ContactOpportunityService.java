package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.dto.contact.ContactOpportunityDTO;
import com.ut.tekir.common.dto.contact.ContactOpportunitySaveRequest;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactOpportunity;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactOpportunityRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.service.mapper.ContactMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactOpportunityService {

    private final ContactOpportunityRepository opportunityRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactOpportunityDTO> findByContactId(Long contactId) {
        return opportunityRepository
            .findByContactIdOrderByStageAscExpectedCloseDateAsc(contactId)
            .stream()
            .map(contactMapper::toOpportunityDTO)
            .toList();
    }

    @Transactional(readOnly = true)
    public ContactOpportunityDTO getById(Long id) {
        return contactMapper.toOpportunityDTO(
            opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ContactOpportunity", id))
        );
    }

    public ContactOpportunityDTO save(ContactOpportunitySaveRequest request) {
        ContactOpportunity opportunity;

        if (request.id() != null) {
            opportunity = opportunityRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("ContactOpportunity", request.id()));
        } else {
            opportunity = new ContactOpportunity();
            Contact contact = contactRepository.findById(request.contactId())
                .orElseThrow(() -> new EntityNotFoundException("Contact", request.contactId()));
            opportunity.setContact(contact);
        }

        opportunity.setTitle(request.title());
        opportunity.setStage(request.stage());
        opportunity.setExpectedRevenue(request.expectedRevenue());
        opportunity.setCurrency(request.currency() != null ? request.currency() : "TRY");
        opportunity.setProbability(request.probability() != null ? request.probability() : 0);
        opportunity.setExpectedCloseDate(request.expectedCloseDate());
        opportunity.setActualCloseDate(request.actualCloseDate());
        opportunity.setNote(request.note());
        opportunity.setAssignedTo(request.assignedTo());
        if (request.active() != null) {
            opportunity.setActive(request.active());
        }

        return contactMapper.toOpportunityDTO(opportunityRepository.save(opportunity));
    }

    public void delete(Long id) {
        if (!opportunityRepository.existsById(id)) {
            throw new EntityNotFoundException("ContactOpportunity", id);
        }
        opportunityRepository.deleteById(id);
    }
}
