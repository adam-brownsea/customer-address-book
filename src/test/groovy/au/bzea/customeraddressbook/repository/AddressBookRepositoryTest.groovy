package au.bzea.customeraddressbook.repository

import au.bzea.customeraddressbook.model.AddressBook
import au.bzea.customeraddressbook.repository.AddressBookRespository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.junit.runner.RunWith
import spock.lang.Specification

@DataJpaTest
@RunWith(SpringRunner.class) 
@SpringBootTest(classes=AddressBookRespository.class)
class AddressBookRepositoryTest extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    AddressBookRespository repository

    def "should find no address books if repository is empty"() {
        //when: "we request all address books"
        Iterable<AddressBook> addressBooks = repository.findAll()

        //then: "the list of address books is empty"
        addressBooks.isEmpty()
    }

    def "should store an address book"() {
        //when: "we store an address book"
        AddressBook addressBook = repository.save(new AddressBook("book title"))

        //then: "the the returned book has the name we stored"
        addressBook.getName().equals("book title")
    }

    def "should find all address books"() {
        AddressBook book1 = new AddressBook("Address Book#1")
        entityManager.persist(book1)

        AddressBook book2 = new AddressBook("Address Book#2")
        entityManager.persist(book2)

        AddressBook book3 = new AddressBook("Address Book#3")
        entityManager.persist(book3)

        Iterable<AddressBook> addressBooks = repository.findAll()

        assertThat(addressBooks).hasSize(3).contains(book1, book2, book3)
    }

    def "should find address book by id"() {
        AddressBook book1 = new AddressBook("Address Book#1")
        entityManager.persist(book1)

        AddressBook book2 = new AddressBook("Address Book#2")
        entityManager.persist(book2)

        AddressBook foundAddressBook = repository.findById(book2.getId()).get()

        assertThat(foundAddressBook).isEqualTo(book2)
    }

}