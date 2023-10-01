package au.bzea.customeraddressbook.repository;

import au.bzea.customeraddressbook.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookRespository extends JpaRepository<AddressBook, Long> {
}
