package au.bzea.customeraddressbook;

import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import au.bzea.customeraddressbook.model.AddressBook;
import au.bzea.customeraddressbook.model.Contact;
import au.bzea.customeraddressbook.repository.AddressBookRepository;
import au.bzea.customeraddressbook.repository.ContactRepository;
import au.bzea.customeraddressbook.service.ContactService;

@DataJpaTest
public class ContactServiceTest {
    private static Logger logger = Logger.getLogger(ContactServiceTest.class.getName());

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    AddressBookRepository addressBookrepository;

    @Autowired
    ContactRepository repository;

    @Test
    void should_return_list_of_contacts() {
        AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));
        List<Contact> contacts = new ArrayList<>(3);

        Contact con1 = new Contact("Contact#1", "0434123456");
        con1.setAddressBook(addressBook);
        entityManager.persist(con1);
        contacts.add(con1);

        Contact con2 = new Contact("Contact#2", "1234");
        con2.setAddressBook(addressBook);
        entityManager.persist(con2);
        contacts.add(con2);

        Contact con3 = new Contact("Contact#3", "5678");
        con3.setAddressBook(addressBook);
        entityManager.persist(con3);
        contacts.add(con3);

        ContactService service = new ContactService(repository);
        List<Contact> storedContacts  = service.getContacts(contacts);

        assertAll("Check all values match",
            () -> assertEquals(storedContacts.get(0).getName(),con1.getName()),
            () -> assertEquals(storedContacts.get(0).getPhoneNumber(), con1.getPhoneNumber()),
            () -> assertEquals(storedContacts.get(1).getName(),con2.getName()),
            () -> assertEquals(storedContacts.get(1).getPhoneNumber(), con2.getPhoneNumber()),
            () -> assertEquals(storedContacts.get(2).getName(),con3.getName()),
            () -> assertEquals(storedContacts.get(2).getPhoneNumber(), con3.getPhoneNumber())
        );
    }

    
    @Test
    void should_return_that_addressbooks_match() {
        List<Contact> oldContacts = new ArrayList<>(2);
        List<Contact> newContacts = new ArrayList<>(2);

        AddressBook addressBook1 = new AddressBook("Book1 name");
        AddressBook addressBook2 = new AddressBook("Book2 name");
        entityManager.persist(addressBook1);
        entityManager.persist(addressBook2);

        Contact con1 = new Contact("Contact#1", "0434123456");
        con1.setAddressBook(addressBook1);
        con1.setAddressBookId(addressBook1.getId());
        oldContacts.add(con1);
        newContacts.add(con1);

        Contact con2 = new Contact("Contact#2", "1234");
        con2.setAddressBook(addressBook2);
        con2.setAddressBookId(addressBook2.getId());
        oldContacts.add(con2);
        newContacts.add(con2);

        ContactService service = new ContactService(repository);
        assertTrue(service.compareAddressBooks(oldContacts, newContacts));
    }

    @Test
    void should_return_that_addressbooks_not_match() {
        List<Contact> oldContacts = new ArrayList<>(2);
        List<Contact> newContacts = new ArrayList<>(2);

        AddressBook addressBook1 = new AddressBook("Book1 name");
        AddressBook addressBook2 = new AddressBook("Book2 name");
        entityManager.persist(addressBook1);
        entityManager.persist(addressBook2);

        Contact con1 = new Contact("Contact#1", "0434123456");
        con1.setAddressBook(addressBook1);
        newContacts.add(con1);
        Contact con2 = new Contact("Contact#1", "0434123456");
        con2.setAddressBookId(addressBook2.getId());
        oldContacts.add(con2);

        Contact con3 = new Contact("Contact#2", "1234");
        con3.setAddressBook(addressBook1);
        newContacts.add(con3);
        Contact con4 = new Contact("Contact#2", "1234");
        con1.setAddressBookId(addressBook2.getId());
        oldContacts.add(con4);

        ContactService service = new ContactService(repository);
        service.updateContacts(oldContacts, newContacts);

        assertFalse(service.compareAddressBooks(oldContacts, newContacts));
    }
    
    @Test
    void should_return_contacts_updated() {
        List<Contact> oldContacts = new ArrayList<>(2);
        List<Contact> newContacts = new ArrayList<>(2);

        Contact con1 = new Contact("Contact#1", "0434123456");
        oldContacts.add(con1);
        Contact con2 = new Contact("Contact#1", "987654");
        newContacts.add(con2);

        Contact con3 = new Contact("Contact#2", "1234");
        oldContacts.add(con3);
        Contact con4 = new Contact("Contact#2", "987654");
        newContacts.add(con4);

        ContactService service = new ContactService(repository);
        service.updateContacts(oldContacts, newContacts);

        assertAll("Check all values match",
            () -> assertEquals(oldContacts.get(0).getPhoneNumber(), con2.getPhoneNumber()),
            () -> assertEquals(oldContacts.get(1).getPhoneNumber(), con4.getPhoneNumber())
        );

    }    
}
