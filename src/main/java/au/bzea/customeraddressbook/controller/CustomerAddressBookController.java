package au.bzea.customeraddressbook.controller;

import au.bzea.customeraddressbook.model.AddressBook;
import au.bzea.customeraddressbook.model.Contact;
import au.bzea.customeraddressbook.repository.AddressBookRepository;
import au.bzea.customeraddressbook.repository.ContactRepository;
import au.bzea.customeraddressbook.service.ContactService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CustomerAddressBookController {

    @Autowired
    private AddressBookRepository addressBookRespository;

    @Autowired
    private ContactRepository contactRepository;

    public CustomerAddressBookController(AddressBookRepository addressBookRespository,
        ContactRepository contactRepository) {
        this.addressBookRespository = addressBookRespository;
        this.contactRepository = contactRepository;
    }

    // retrieve all address books
    @GetMapping("/addressBook")
    Iterable<AddressBook> addressBookAll() {
        return addressBookRespository.findAll();
    }

    // get address book by id
    @GetMapping("/addressBook/{id}")
    AddressBook addressBookById(@PathVariable Long id) {
        return addressBookRespository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    // create new address book
    @PostMapping("/addressBook")
    AddressBook addressBookSave(@RequestBody AddressBook addressBook) {
        return addressBookRespository.save(addressBook);
    }

    // retrieve all contacts or only for an address book
    //- Users should be able to print all contacts in an address book
    @GetMapping("/contact")
    Iterable<Contact> contactAll(@RequestParam(required = false) Long addressBookId) {
        if (addressBookId != null) {
            // find the address book and return only contacts for that address book
            AddressBook addressBook = addressBookRespository.findById(addressBookId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
            return contactRepository.findByAddressBook(addressBook);
        }
        return contactRepository.findAll();
    }

    // get contact by id
    @GetMapping("/contact/{id}")
    Contact contactById(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
        contact.setAddressBookId(contact.getAddressBook().getId());        
        return contact;
    }

    // create new contact
    //- Users should be able to add new contact entries
    @PostMapping("/contact")
    Contact contactCreate(@RequestBody Contact contact) {
        AddressBook addressBook = addressBookRespository
            .findById(contact.getAddressBookId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Address Book required for contact"));
        contact.setAddressBook(addressBook);
        return contactRepository.save(contact);
    }

    // update contact
    //- Users should be able to add new contact entries
    @PutMapping("/contact/{id}")
    Contact contactUpdate(@RequestBody Contact contact, @PathVariable Long id) {

        //update the contact
        return contactRepository.findById(contact.getAddressBookId())
            .map(contactUpdate -> {
                if (contact.getAddressBookId() != null 
                && !contactUpdate.getAddressBook().getId().equals(contact.getAddressBookId())) 
                    throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Address Book cant be updated for contact");
                contactUpdate.setName(contact.getName());
                contactUpdate.setPhoneNumber(contact.getPhoneNumber());
                return contactRepository.save(contactUpdate);
            })
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Contact not found"));
    }

    // update contact
    //- Users should be able to add new contact entries
    @PutMapping("/contact")
    List<Contact> contactUpdateMultiple(@RequestBody List<Contact> contacts) {
        ContactService service = new ContactService(contactRepository);

        // Check all contacts exist
        List<Contact> foundContacts = service.getContacts(contacts);
        if (foundContacts.isEmpty()) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "contacts not found");

        // Check all address books match
        if (!service.compareAddressBooks(foundContacts, contacts)) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "address book id not match");

        // Update each contact in class
        service.updateContacts(foundContacts, contacts);

        // Save each contact
        service.saveContacts(foundContacts);

        return foundContacts;
    }
    

    // delete a contact
    //- Users should be able to remove existing contact entries
    @DeleteMapping("/contact/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void contactDelete(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }
}
