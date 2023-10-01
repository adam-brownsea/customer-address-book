package au.bzea.customeraddressbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import au.bzea.customeraddressbook.model.Contact;
import au.bzea.customeraddressbook.repository.ContactRepository;

public class ContactService {
    private static Logger logger = Logger.getLogger(ContactService.class.getName());

    @Autowired
    ContactRepository contactRepository;
    
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Check all contacts exist and retrieve with address book object
    public List<Contact> getContacts(List<Contact> contacts) {
        List<Contact> returnContacts = new ArrayList<>(contacts.size());

        for(Contact contact: contacts) {
            Optional<Contact> returnContact = contactRepository.findById(contact.getId());
            if (!returnContact.isPresent()) return new ArrayList<>();
            returnContacts.add(returnContact.get());
        }
        return returnContacts;
    }

    // Check all address books match
    public boolean compareAddressBooks(List<Contact> oldContacts, List<Contact> newContacts) {
        // Assuming lists in same order
        int i = 0;
        for(Contact oldContact: oldContacts) {
            logger.info(oldContact.getAddressBook().getId() +":"+ newContacts.get(i).getAddressBookId());
            logger.info(oldContact.getAddressBook().getId() +":"+ newContacts.get(i).getAddressBookId());
            if (!oldContact.getAddressBook().getId().equals(newContacts.get(i).getAddressBook().getId()))
                return false;
            i++;
        }
        return true;
    }

    // Update each contact in class
    // Check all address books match
    public void updateContacts(List<Contact> oldContacts, List<Contact> newContacts) {
        // Assuming lists in same order
        int i = 0;
        for(Contact oldContact: oldContacts) {
            Contact newContact = newContacts.get(i);
            newContact.setName(oldContact.getName());
            newContact.setPhoneNumber(oldContact.getPhoneNumber());
            i++;
        }
    }

    
    // Save each contact
    public List<Contact> saveContacts(List<Contact> contacts) {
        List<Contact> savedContacts = new ArrayList<>(contacts.size());
        for(Contact contact: contacts) {
            savedContacts.add(contactRepository.save(contact));
        }
        return savedContacts;

    }
    
}
