package au.bzea.customeraddressbook.repository;

import au.bzea.customeraddressbook.model.AddressBook;
import au.bzea.customeraddressbook.model.Contact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByAddressBook(AddressBook addressBook);
}
