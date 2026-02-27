package com.ut.tekir.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ut.tekir.common.dto.contact.ContactNetworkDTO;
import com.ut.tekir.common.dto.contact.ContactNetworkSaveRequest;
import com.ut.tekir.common.entity.Contact;
import com.ut.tekir.common.entity.ContactNetwork;
import com.ut.tekir.common.exception.EntityNotFoundException;
import com.ut.tekir.repository.ContactNetworkRepository;
import com.ut.tekir.repository.ContactRepository;
import com.ut.tekir.service.mapper.ContactMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactNetworkService {

    private final ContactNetworkRepository networkRepository;
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactNetworkDTO> findByContactId(Long contactId) {
        return networkRepository
            .findByOwnerIdOrderByDefaultNetworkDescActiveNetworkDesc(contactId)
            .stream()
            .map(contactMapper::toNetworkDTO)
            .toList();
    }

    public ContactNetworkDTO save(Long contactId, ContactNetworkSaveRequest request) {
        ContactNetwork network;

        if (request.id() != null) {
            network = networkRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("ContactNetwork", request.id()));
        } else {
            network = new ContactNetwork();
            Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("Contact", contactId));
            network.setOwner(contact);
        }

        network.setValue(request.value());
        network.setInfo(request.info());
        network.setDefaultNetwork(Boolean.TRUE.equals(request.defaultNetwork()));
        network.setActiveNetwork(request.activeNetwork() == null || Boolean.TRUE.equals(request.activeNetwork()));

        // Tüm tip flag'lerini sıfırla, sonra seçileni aktif et
        network.setEmail(false);
        network.setWeb(false);
        network.setTwitterNetwork(false);
        network.setFacebookNetwork(false);
        network.setSkypeNetwork(false);
        network.setMsnNetwork(false);
        network.setGtalkNetwork(false);
        network.setJabberNetwork(false);
        network.setFriendFeedNetwork(false);
        network.setYahooNetwork(false);
        network.setMircNetwork(false);

        switch (request.networkType().toUpperCase()) {
            case "EMAIL"    -> network.setEmail(true);
            case "WEB"      -> network.setWeb(true);
            case "TWITTER"  -> network.setTwitterNetwork(true);
            case "FACEBOOK" -> network.setFacebookNetwork(true);
            case "SKYPE"    -> network.setSkypeNetwork(true);
            // OTHER: hiçbir flag set edilmez
        }

        return contactMapper.toNetworkDTO(networkRepository.save(network));
    }

    public void delete(Long id) {
        if (!networkRepository.existsById(id)) {
            throw new EntityNotFoundException("ContactNetwork", id);
        }
        networkRepository.deleteById(id);
    }
}
