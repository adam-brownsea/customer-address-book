package au.bzea.customeraddressbook.controller;

import au.bzea.customeraddressbook.model.AddressBook;
import au.bzea.customeraddressbook.model.Contact;
import au.bzea.customeraddressbook.repository.AddressBookRespository;
import au.bzea.customeraddressbook.repository.ContactRespository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CustomerAddressBookController {

    private AddressBookRespository addressBookRespository;
    private ContactRespository contactRespository;

    public CustomerAddressBookController(AddressBookRespository addressBookRespository,
        ContactRespository contactRespository) {
        this.addressBookRespository = addressBookRespository;
        this.contactRespository = contactRespository;
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

        // retrieve all contacts
    @GetMapping("/contact")
    Iterable<Contact> contactAll() {
        return contactRespository.findAll();
    }

    // get contact by id
    @GetMapping("/contact/{id}")
    Contact contactById(@PathVariable Long id) {
        return contactRespository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
    }

    //create new contact
    @PostMapping("/contact")
    Contact contactSave(@RequestBody Contact contact) {
        AddressBook addressBook = addressBookRespository.findById(contact.getAddressBookId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND));
        contact.setAddressBook(addressBook);
        return contactRespository.save(contact);
    }
}
