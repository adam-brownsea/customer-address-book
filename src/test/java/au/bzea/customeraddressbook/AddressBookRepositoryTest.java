package au.bzea.customeraddressbook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import au.bzea.customeraddressbook.model.AddressBook;
import au.bzea.customeraddressbook.repository.AddressBookRespository;

@DataJpaTest
public class AddressBookRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  AddressBookRespository repository;

  @Test
  public void should_find_no_addressbooks_if_repository_is_empty() {
    Iterable<AddressBook> addressBooks = repository.findAll();

    assertThat(addressBooks).isEmpty();
  }

  @Test
  public void should_store_a_addressbook() {
    AddressBook addressBook = repository.save(new AddressBook("Book name"));

    assertThat(addressBook).hasFieldOrPropertyWithValue("name", "Book name");
  }

  @Test
  public void should_find_all_addressbooks() {
    AddressBook book1 = new AddressBook("Book#1");
    entityManager.persist(book1);

    AddressBook book2 = new AddressBook("Book#2");
    entityManager.persist(book2);

    AddressBook book3 = new AddressBook("Book#3");
    entityManager.persist(book3);

    Iterable<AddressBook> addressBooks = repository.findAll();

    assertThat(addressBooks).hasSize(3).contains(book1, book2, book3);
  }

  @Test
  public void should_find_addressbook_by_id() {
    AddressBook book1 = new AddressBook("Book#1");
    entityManager.persist(book1);

    AddressBook book2 = new AddressBook("Book#2");
    entityManager.persist(book2);

    AddressBook foundAddressBook = repository.findById(book2.getId()).get();

    assertThat(foundAddressBook).isEqualTo(book2);
  }

  @Test
  public void should_update_addressBook_by_id() {
    AddressBook book1 = new AddressBook("Book#1");
    entityManager.persist(book1);

    AddressBook book2 = new AddressBook("Book#2");
    entityManager.persist(book2);

    AddressBook updatedBook = new AddressBook("updated Book#2");

    AddressBook book = repository.findById(book2.getId()).get();
    book.setName(updatedBook.getName());
    repository.save(book);

    AddressBook checkBook = repository.findById(book2.getId()).get();
    
    assertThat(checkBook.getId()).isEqualTo(book2.getId());
    assertThat(checkBook.getName()).isEqualTo(updatedBook.getName());
  }

  @Test
  public void should_delete_addressBook_by_id() {
    AddressBook book1 = new AddressBook("Book#1");
    entityManager.persist(book1);

    AddressBook book2 = new AddressBook("Book#2");
    entityManager.persist(book2);

    AddressBook book3 = new AddressBook("Book#3");
    entityManager.persist(book3);

    repository.deleteById(book2.getId());

    Iterable<AddressBook> addressBooks = repository.findAll();

    assertThat(addressBooks).hasSize(2).contains(book1, book3);
  }

  @Test
  public void should_delete_all_addressBooks() {
    entityManager.persist(new AddressBook("Book#1"));
    entityManager.persist(new AddressBook("Book#2"));

    repository.deleteAll();

    assertThat(repository.findAll()).isEmpty();
  }
}
