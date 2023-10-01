package au.bzea.customeraddressbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import au.bzea.customeraddressbook.model.Contact;
import au.bzea.customeraddressbook.repository.ContactRespository;
import au.bzea.customeraddressbook.model.AddressBook;
import au.bzea.customeraddressbook.repository.AddressBookRespository;

@DataJpaTest
public class ContactRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  AddressBookRespository addressBookrepository;

  @Autowired
  ContactRespository repository;

  @Test
  public void should_find_no_addressbooks_if_repository_is_empty() {
    Iterable<Contact> contacts = repository.findAll();

    assertThat(contacts).isEmpty();
  }

  @Test
  public void should_store_a_addressbook() {
    AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));

    Contact contact = new Contact("Contact name", "0434123456");
    contact.setAddressBook(addressBook);
    Contact newContact = repository.save(contact);

    assertThat(newContact).hasFieldOrPropertyWithValue("name", "Contact name");
    assertThat(newContact).hasFieldOrPropertyWithValue("name", "Contact name");
  }

  @Test
  public void should_find_all_addressbooks() {
    AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));

    Contact con1 = new Contact("Contact#1", "0434123456");
    con1.setAddressBook(addressBook);
    entityManager.persist(con1);

    Contact con2 = new Contact("Contact#2", "1234");
    con2.setAddressBook(addressBook);
    entityManager.persist(con2);

    Contact con3 = new Contact("Contact#3", "5678");
    con3.setAddressBook(addressBook);
    entityManager.persist(con3);

    Iterable<Contact> contacts = repository.findAll();

    assertThat(contacts).hasSize(3).contains(con1, con2, con3);
  }

  @Test
  public void should_find_addressbook_by_id() {
    AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));

    Contact con1 = new Contact("Contact#1", "0434123456");
    con1.setAddressBook(addressBook);
    entityManager.persist(con1);

    Contact con2 = new Contact("Contact#2", "1234");
    con2.setAddressBook(addressBook);
    entityManager.persist(con2);

    Contact foundContact = repository.findById(con2.getId()).get();

    assertThat(foundContact).isEqualTo(con2);
  }

  @Test
  public void should_update_contact_by_id() {
    AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));

    Contact con1 = new Contact("Contact#1", "0434123456");
    con1.setAddressBook(addressBook);
    entityManager.persist(con1);

    Contact con2 = new Contact("Contact#2", "1234");
    con2.setAddressBook(addressBook);
    entityManager.persist(con2);

    Contact updatedContact = new Contact("updated Contact#2", "9876");

    Contact con = repository.findById(con2.getId()).get();
    con.setName(updatedContact.getName());
    repository.save(con);

    Contact checkContact = repository.findById(con2.getId()).get();
    
    assertThat(checkContact.getId()).isEqualTo(con2.getId());
    assertThat(checkContact.getName()).isEqualTo(updatedContact.getName());
  }

  @Test
  public void should_delete_contact_by_id() {
    AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));

    Contact con1 = new Contact("Contact#1", "0434123456");
    con1.setAddressBook(addressBook);
    entityManager.persist(con1);

    Contact con2 = new Contact("Contact#2", "1234");
    con2.setAddressBook(addressBook);
    entityManager.persist(con2);

    Contact con3 = new Contact("Contact#3", "5678");
    con3.setAddressBook(addressBook);
    entityManager.persist(con3);

    repository.deleteById(con2.getId());

    Iterable<Contact> contacts = repository.findAll();

    assertThat(contacts).hasSize(2).contains(con1, con3);
  }

  @Test
  public void should_delete_all_contacts() {
    AddressBook addressBook = addressBookrepository.save(new AddressBook("Book name"));

    Contact con1 = new Contact("Contact#1", "0434123456");
    con1.setAddressBook(addressBook);
    entityManager.persist(con1);

    Contact con2 = new Contact("Contact#2", "1234");
    con2.setAddressBook(addressBook);
    entityManager.persist(con2);

    repository.deleteAll();

    assertThat(repository.findAll()).isEmpty();
  }
}
